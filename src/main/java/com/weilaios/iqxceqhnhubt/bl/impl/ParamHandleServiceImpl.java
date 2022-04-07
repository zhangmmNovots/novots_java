package com.weilaios.iqxceqhnhubt.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.weilaios.iqxceqhnhubt.da.service.ConstantValueService;
import com.weilaios.iqxceqhnhubt.model.ConstantValue;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.jayway.jsonpath.DocumentContext;
import org.apache.commons.lang3.StringUtils;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程执行参数获取处理实现类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
@Service
public class ParamHandleServiceImpl implements ParamHandleService{
    private transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisUtil redisService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ConstantValueService constantValueService;

    @Override
    public Object getParamValue(DocumentContext paramsJsonContext, Object paramKey, String paramSourceType) throws BusinessException {
        log.info("==================获取参数值开始=====paramKey:"+paramKey+"=======paramSourceType:"+paramSourceType);
        //如果取值的key为空，则直接返回空值，避免空key取值异常
        if (null == paramKey || "".equals(paramKey)) {
            return null;
        }

        Object paramValue = null;
        if (Constants.INPUT_PARAM_SOURCE_TYPE_0.equals(paramSourceType)) {
            //传入的参数
            try {
                //此处处理路径读取值时，路径不存在的问题
                paramValue = paramsJsonContext.read(String.valueOf(paramKey));
            } catch (Exception e) {
                paramValue = null;
            }
        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_1.equals(paramSourceType)) {
            //循环参数体
        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_2.equals(paramSourceType)) {
            //数据字典
            //todo
        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_3.equals(paramSourceType)) {
            //固定值，写死的，则直接key获取到的就是值
            paramValue = paramKey;
            //处理常量中被xml转义的特殊字符：暂时判断只有当为字符串的时候才处理
            paramValue = this.handelParamSpecialCharacters(paramValue);
        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_5.equals(paramSourceType)
                ||Constants.INPUT_PARAM_SOURCE_TYPE_6.equals(paramSourceType)) {
            //固定值，写死的，则直接key获取到的就是值
            paramValue = paramKey;
        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_4.equals(paramSourceType)
                || Constants.INPUT_PARAM_SOURCE_TYPE_7.equals(paramSourceType)) {
            //预设全局变量--用户信息
            //判断当前用户信息 token
            String token = null;
            try {
                token = paramsJsonContext.read("$.data.system.token");
            } catch (Exception e) {
                log.error("捕获的异常", e);
            }
            paramValue = this.getGlobalVariableValue(token, paramKey, paramSourceType);

        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_8.equals(paramSourceType)) {
            paramValue = this.getWlosEntityFieldById(paramKey);
        } else if (Constants.INPUT_PARAM_SOURCE_TYPE_9.equals(paramSourceType)) {
            paramValue = this.getBlocklyValue(paramsJsonContext, paramKey);
        }

        log.info("======获取参数值结束=====paramKey:"+paramKey+"=====paramSourceType:"+paramSourceType+"=====获取到的值=="+paramValue);
        if (null == paramValue || "".equals(paramValue)) {
            return null;
        }
        return paramValue;
    }

    /**
     * 获取blockly执行结果
     * @param paramsJsonContext
     * @param paramKey
     * @return
     * @throws BusinessException
     */
    @Override
    public Object getBlocklyValue(DocumentContext paramsJsonContext, Object paramKey) throws BusinessException{
        try {
            //blockly
            JSONObject paramKeyJson = (JSONObject) paramKey;
            //获取执行脚本
            String script=paramKeyJson.getString("script");
            if (StringUtils.isBlank(script)) {
                return null;
            }
            //获取绑定参数
            JSONArray bindParams = paramKeyJson.getJSONArray("bindParam");
            Map<String, Object> bindings = new HashMap<>();
            Map<String, Object> bindingsListMap = new HashMap<>();
            if (null != bindParams && bindParams.size() > 0) {
                for (int i = 0; i < bindParams.size(); i++) {
                    JSONObject bindParamJson = bindParams.getJSONObject(i);
                    String bindKey = bindParamJson.getString("bindKey");
                    //循环外部参数
                    Object bindValueKey = bindParamJson.get("bindValue");
                    String paramSourceType = bindParamJson.getString("paramSourceType");
                    Object bindValue = this.getParamValue(paramsJsonContext, bindValueKey, paramSourceType);
                    log.info("bindKey===" + bindKey + "=====bindValue====" + bindValue);
                    //获取参数类型：根据参数的类型转换值：如果值类型是字符串的话，运算会a+b会变成字符串的拼接而不是数值运算
                    String bindType = bindParamJson.getString("bindType");
                    bindValue = ProcessUtil.transValueType(bindValue, bindType);
                    //判断参数类型，如果是数组或者集合参数，则拼接到script中传入，非以上类型，则bingings类型传入
                    if (null!=bindValue && (bindValue instanceof List || bindValue.getClass().isArray())) {
                        bindingsListMap.put(bindKey, bindValue);
                    } else {
                        if (null == bindValue || "".equals(bindValue)) {
                            if (Constants.DM_DATA_TYPE_AMOUNT.equals(bindType)
                                    || Constants.DM_DATA_TYPE_DECIMAL.equals(bindType)
                                    || Constants.DM_DATA_TYPE_DOUBLE.equals(bindType)
                                    || Constants.DM_DATA_TYPE_INTEGER.equals(bindType)
                            ) {
                                bindValue = BigDecimal.ZERO;
                            } else {
                                bindValue = "";
                            }
                        }
                        bindings.put(bindKey, bindValue);
                    }
                }
            }

            //执行脚本语言
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("nashorn");
            Object evalValue = engine.eval(bindingsListMap + ";" + script, new SimpleBindings(bindings));
            log.info("=====blockly js执行输出的结果====" + JSON.toJSONString(evalValue));
            boolean isArray = false;
            //js执行返回的结果是ScriptObjectMirror类型
            if (evalValue instanceof ScriptObjectMirror) {
                ScriptObjectMirror som = (ScriptObjectMirror) evalValue;
                if (som.isArray()) {
                    isArray = true;
                }
            }
            if (isArray) {
                List<Object> list = new ArrayList<>();
                ScriptObjectMirror mirror = (ScriptObjectMirror) evalValue;
                for (Map.Entry<String, Object> entry : mirror.entrySet()) {
                    Object result = entry.getValue();
                    if (result instanceof ScriptObjectMirror) {
                        list.add(result);
                    } else {
                        list.add(result);
                    }
                }
                evalValue = list;
            }
            return evalValue;
        } catch (Exception e) {
            log.error("操作异常", "运算失败");
            throw new BusinessException("赋值blockly类型处理失败", e);
        }
    }

    /**
    *
    * @param paramValue
    * @return
    */
    public Object handelParamSpecialCharacters(Object paramValue){
        try {
            if (null == paramValue || "".equals(paramValue)) {
                return paramValue;
            }
            if (paramValue instanceof String) {
                String value = String.valueOf(paramValue);
                value = value.replace("&lt;", "<");
                value = value.replace("&gt;", ">");
                value = value.replace("&amp;", "&");
                value = value.replace("&apos;", "'");
                value = value.replace("&quot;", "\"");
                paramValue = value;
            }
        } catch (Exception e) {
            log.debug("处理特殊字符失败", e);
        }
        return paramValue;
    }

    @Override
    public Object getGlobalVariableValue(String token, Object paramKey, String paramSourceType) {
        log.info("==================获取参数值开始=====paramKey:"+paramKey+"=======paramSourceType:"+paramSourceType);
        Object paramValue = null;
        //预设全局变量--用户信息
        if ("currentdate".equals(paramKey)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            paramValue = formatter.format(new Date());
        } else if ("currenttime".equals(paramKey)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paramValue = formatter.format(new Date());
        } else if ("currentTimeMillis".equals(paramKey)) {
            paramValue = System.currentTimeMillis();
            System.currentTimeMillis();
        } else if ("timestampSeconds".equals(paramKey)) {
            paramValue = System.currentTimeMillis()/1000;
        } else if (Constants.GLOBAL_VARIABLE_KEY_PROJECT_UUID.equals(paramKey)) {
            paramValue = Constants.PROJECT_ID_VALUE;
        } else {
            //自定义环境变量
            //系统数据--登录用户的参数：存储再redis里的
            //判断当前用户信息 token
            if (StringUtils.isNotEmpty(token)) {
                log.info("=====存在用户token,开始获取全局变量=======");
                Map<String, Object> globalVariable = JSON.parseObject((String) redisService.get(token + "_GLOBALVARIABLE"));
                log.info("====globalVariable===="+ JSON.toJSONString(globalVariable));
                if (null != globalVariable) {
                    //获取用户信息
                    if (globalVariable.containsKey(paramKey)) {
                        paramValue = globalVariable.get(paramKey);
                    }
                }
            }
        }
        log.info("======获取参数值结束=====paramKey:"+paramKey+"=====paramSourceType:"+paramSourceType+"=====获取到的值=="+paramValue);
        return paramValue;
    }

    @Override
    public JSONObject getUserSession(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        Map<String, Object> jwtResult = null;
        try {
            jwtResult = jwtUtil.checkToken(token);
        } catch (Exception e) {
            log.debug("获取登录用户信息失败", e);
        }
        if (null == jwtResult) {
            return null;
        }
        String flag = String.valueOf(jwtResult.remove(Constants.JWT_FLAG));
        if (Objects.equals(Constants.RESULT_FAIL, flag)) {
            log.debug("登录用户信息失效");
            return null;
        }

        JSONObject userinfo = new JSONObject(jwtResult);
        return userinfo;
    }

	public Object getWlosEntityFieldById(Object uuid) throws BusinessException{
        try {
            if (null==uuid || "".equals(uuid)) {
                return null;
            }
            Result result = constantValueService.getById(String.valueOf(uuid));

            //解析信息
            Integer dmCode = result.getCode();
            if (!Constants.HTTP_STATUS_OK.equals(dmCode)) {
                String msg = result.getMsg();
                throw new BusinessException("获取单一选项信息失败：" + msg);
            }
            ConstantValue data = (ConstantValue) result.getData();
            if (null == data) {
                throw new BusinessException("获取单一选项信息失败");
            }
            Object fieldValue = data.getValue();
            return fieldValue;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("获取单一选项信息失败", e);
        }
    }
}