package com.weilaios.iqxceqhnhubt.bl.impl;

import com.weilaios.iqxceqhnhubt.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.weilaios.iqxceqhnhubt.bl.GlobalVariableService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.weilaios.iqxceqhnhubt.bl.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import java.io.IOException;
import java.math.BigDecimal;
import java.io.ByteArrayInputStream;
import java.util.*;
import org.apache.http.HttpStatus;

/**
* 流程过程执行
* @author
* @date Apr 7, 2022 3:06:41 PM
*/
@Service
public class DefaultProcessSpovService {

    private transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamHandleService paramHandleService;
    @Autowired
    private GlobalVariableService globalVariableService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject processMainExecute(String paramsJson) throws BusinessException {
        //调用开始方法
        if (StringUtils.isBlank(paramsJson)) {
            paramsJson = "{\"data\":{}}";
        }
        paramsJson = StartEvent_1wj0yk1p62451b14(paramsJson);
        //获取流程返回的结果
        String returnDataKey = JsonPath.read(paramsJson, "$." + Constants.SYSTEM_RETURN_KEY_NAME);
        JSONObject returnDataJson = new JSONObject(JsonPath.read(paramsJson, "$.data." + returnDataKey));
        //获取当前流程执行返回的token
        String subToken = null;
        try{
            subToken = JsonPath.read(paramsJson, "$.data.system.token");
        } catch (Exception e) {
        }
        returnDataJson.put(Constants.SYSTEM_RETURN_TOKEN, subToken);
        return returnDataJson;
    }

    public String StartEvent_1wj0yk1p62451b14(String paramsJson) throws BusinessException {
        log.info("========执行开始方法=======");
        String taskName = "开始事件";
        String processName = "默认流程1sPOV";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            paramsJson = Activity_0mbjdnj(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("开始过程处理失败" + errorSuffix, e);
        }
    }
    public String Activity_0mbjdnj(String paramsJson) throws BusinessException {
        log.info("========执行权限校验方法=======");
        String taskName = "权限";
        String processName = "默认流程1sPOV";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            DocumentContext paramsJsonContext = JsonPath.parse(paramsJson);
            //权限检查返回值：权限检查是是否通过：如果未登录状态，则返回false
            boolean checkPrivilege = false;
            //判断当前用户信息 token
            String token = paramsJsonContext.read("$.data.system.token");
            if (StringUtils.isNotBlank(token)) {
                //判断用户登录信息是否超时
                //判断当前用户信息来源：大平台端，则不执行权限检查 ，用户端执行权限检查
                JSONObject userinfo = paramHandleService.getUserSession(token);
                if (null != userinfo) {
                    //非超级管理员，做权限检查
                    String privilegesStr = "[true,{\"privilegeName\":\"\",\"isArr\":true,\"privilegeId\":\"\"}]";
                    JSONArray privileges = JSON.parseArray(privilegesStr);
                    if (null != privileges && privileges.size() > 0) {
                        //获取or里的and节点，or里的满足一个and节点为true，则当前满足条件
                        for (int i = 0; i < privileges.size(); i++) {
                            JSONObject subOrJson = privileges.getJSONObject(i);
                            Object andListObj = subOrJson.get("and");
                            //获取and节点里的多个and条件
                            List<Map<String, String>> andList = JsonUtils.object2List(andListObj);
                            boolean andTrueFlag = true; //标识and判断的最终结果是true 还是false
                            //处理and的条件，and的条件必须全部为true
                            for (int j = 0; j < andList.size(); j++) {
                                JSONObject subAndJson = privileges.getJSONObject(j);
                                //处理单个条件
                                String privilegeId = subAndJson.getString("privilegeId");
                                //判断当前用户是否存在此权限
                                String checkReturn = ""; //pageBuilderRemote.checkProcessPrivilege(token, privilegeId);
                                log.info("======校验权限返回的数据" + checkReturn);
                                Result result = (Result) JSON.parseObject(checkReturn, Result.class);
                                if (!Constants.HTTP_STATUS_OK.equals(result.getCode())) {
                                    throw new BusinessException("操作异常：权限校验失败");
                                }

                                Map dataMap = (Map) result.getData();
                                boolean checkPrivilegeFlag = (boolean) dataMap.get("hasPrivilege");

                                if (!checkPrivilegeFlag) {
                                    andTrueFlag = false;
                                    break;
                                }
                            }
                            if (andTrueFlag) {
                                checkPrivilege = true;
                                break;
                            }
                        }
                    } else {
                        //如果是空节点，则
                        checkPrivilege = true;
                    }
                }
            }
            //将返回结果存储到流程环境变量中
            String resultKey = "Activity_0mbjdnj";
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, checkPrivilege);
            paramsJson = Event_18ucyuc(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("权限校验失败" + errorSuffix, e);
        }
    }
    public String Event_18ucyuc(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "结束事件";
        String processName = "默认流程1sPOV";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            String resultKey = "Event_18ucyuc";
            //返回数据处理
            JSONObject jsonObject = new JSONObject();
            //添加标识符：当前的结果属于哪个结束节点选择出来的
            jsonObject.put("system_returnBelong", resultKey);
            //标识是是否是文件下载
            jsonObject.put(Constants.DOWN_FILE_FLAG_KEY, Constants.DOWN_FILE_FLAG_VALUE_0);
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, jsonObject);
            //存储返回的结束节点
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$", Constants.SYSTEM_RETURN_KEY_NAME, resultKey);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("返回处理失败", e);
            throw new BusinessException("处理异常" + errorSuffix, e);
        }
    }
}