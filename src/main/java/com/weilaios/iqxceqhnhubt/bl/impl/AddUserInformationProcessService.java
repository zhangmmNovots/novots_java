package com.weilaios.iqxceqhnhubt.bl.impl;

import com.weilaios.iqxceqhnhubt.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.weilaios.iqxceqhnhubt.bl.GatewayMatchService;
import com.weilaios.iqxceqhnhubt.bl.GlobalVariableService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.weilaios.iqxceqhnhubt.da.service.UvsvSUserService;
import com.weilaios.iqxceqhnhubt.da.service.UvsvTseService;
import com.weilaios.iqxceqhnhubt.bl.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
public class AddUserInformationProcessService {

    private transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamHandleService paramHandleService;
    @Autowired
    private GatewayMatchService gatewayMatchService;
    @Autowired
    private GlobalVariableService globalVariableService;
    @Autowired
    private RedisUtil redisService;
    @Autowired
    private UvsvSUserService uvsvSUserService;
    @Autowired
    private UvsvTseService uvsvTseService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject processMainExecute(String paramsJson) throws BusinessException {
        //调用开始方法
        if (StringUtils.isBlank(paramsJson)) {
            paramsJson = "{\"data\":{}}";
        }
        paramsJson = StartEvent_1wj0yk1p622ffb86(paramsJson);
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

    public boolean Flow_00d4l4y(String paramsJson) throws BusinessException {
    log.info("========执行顺序流分支判断方法=======");
        try {
            JSONObject conditionJson = new JSONObject();
            JSONArray orArray = new JSONArray();
            JSONObject orJson0 = new JSONObject();
            JSONArray andArray0 = new JSONArray();
            JSONObject andJson00 = new JSONObject();
            andJson00.put("paramsType", "Date");
            andJson00.put("variable", "$.data.joinDate");
            andJson00.put("variableParamSourceType", "0");
            andJson00.put("matchType", "201");
            andJson00.put("matchValue", null);
            andJson00.put("matchValueSourceType", "");
            andJson00.put("matchValueTwo", null);
            andJson00.put("matchValueSourceTypeTwo", "");
            andArray0.add(andJson00);
            orJson0.put("and", andArray0);
            orArray.add(orJson0);
            conditionJson.put("or", orArray);
            boolean matchState = gatewayMatchService.branchConditionMatch(paramsJson, conditionJson);
            log.info("=====gateway matchState===" + matchState);
            return matchState;
        } catch (BusinessException e) {
            log.error("处理失败",e);
            return false;
        } catch (Exception e) {
            log.error("处理失败",e);
            return false;
        }
    }
    public boolean Flow_0mxcwhs(String paramsJson) throws BusinessException {
    log.info("========执行顺序流分支判断方法=======");
        try {
            JSONObject conditionJson = new JSONObject();
            JSONArray orArray = new JSONArray();
            JSONObject orJson0 = new JSONObject();
            JSONArray andArray0 = new JSONArray();
            JSONObject andJson00 = new JSONObject();
            andJson00.put("paramsType", "Primarykey");
            andJson00.put("variable", "$.data.Activity_1rxifnj.uvsv_tse_id");
            andJson00.put("variableParamSourceType", "0");
            andJson00.put("matchType", "200");
            andJson00.put("matchValue", null);
            andJson00.put("matchValueSourceType", "");
            andJson00.put("matchValueTwo", null);
            andJson00.put("matchValueSourceTypeTwo", "");
            andArray0.add(andJson00);
            orJson0.put("and", andArray0);
            orArray.add(orJson0);
            conditionJson.put("or", orArray);
            boolean matchState = gatewayMatchService.branchConditionMatch(paramsJson, conditionJson);
            log.info("=====gateway matchState===" + matchState);
            return matchState;
        } catch (BusinessException e) {
            log.error("处理失败",e);
            return false;
        } catch (Exception e) {
            log.error("处理失败",e);
            return false;
        }
    }
    public boolean Flow_0l38oit(String paramsJson) throws BusinessException {
    log.info("========执行顺序流分支判断方法=======");
        try {
            JSONObject conditionJson = new JSONObject();
            JSONArray orArray = new JSONArray();
            JSONObject orJson0 = new JSONObject();
            JSONArray andArray0 = new JSONArray();
            JSONObject andJson00 = new JSONObject();
            andJson00.put("paramsType", "Primarykey");
            andJson00.put("variable", "$.data.Activity_13wix3e.resultData[0].uvsv_s_user_id");
            andJson00.put("variableParamSourceType", "0");
            andJson00.put("matchType", "201");
            andJson00.put("matchValue", null);
            andJson00.put("matchValueSourceType", "");
            andJson00.put("matchValueTwo", null);
            andJson00.put("matchValueSourceTypeTwo", "");
            andArray0.add(andJson00);
            orJson0.put("and", andArray0);
            orArray.add(orJson0);
            conditionJson.put("or", orArray);
            boolean matchState = gatewayMatchService.branchConditionMatch(paramsJson, conditionJson);
            log.info("=====gateway matchState===" + matchState);
            return matchState;
        } catch (BusinessException e) {
            log.error("处理失败",e);
            return false;
        } catch (Exception e) {
            log.error("处理失败",e);
            return false;
        }
    }
    public String StartEvent_1wj0yk1p622ffb86(String paramsJson) throws BusinessException {
        log.info("========执行开始方法=======");
        String taskName = "开始事件";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            paramsJson = Activity_13wix3e(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("开始过程处理失败" + errorSuffix, e);
        }
    }
    public String Activity_13wix3e(String paramsJson) throws BusinessException {
        log.info("========执行数据查询方法=======");
        String taskName = "数据查询";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
            DocumentContext jsonDataDocument = JsonPath.using(conf).parse(paramsJson);
            //获取返回数据存在的key值
            String resultKey = "Activity_13wix3e";
            //处理请求参数
            JSONObject paramMap = new JSONObject();
            //是否开启查询构造器
            boolean openQueryBuilder = false;
            //获取当前流程查询是否开启查询构造器
            String queryBuilderFlag = jsonDataDocument.read("$.data.System_QueryBuilderFlag");
            //如果存在值，且设置为开启查询构造器
            if (!StringUtils.isEmpty(queryBuilderFlag) && queryBuilderFlag.equals("1")) {
                openQueryBuilder = true;
            }
            //组装查询构造器的查询条件
            if (openQueryBuilder) {
                log.info("===============开启查询构造器=============");
                //获取当前节点的id
                String currentTaskId = resultKey;
                //获取查询构造器的作用节点
                String belongTask = jsonDataDocument.read("$.data.System_QueryBuilder.BelongTask");
                //判断当前节点是否与查询构造器的节点名称相同，如果相同的话，则表示，该节点要开启查询构造器查询
                if (currentTaskId.equals(belongTask)) {
                    JSONObject queryBuilderConditionOr = new JSONObject();
                    queryBuilderConditionOr.put("or", new JSONArray());
                    //确认为当前节点需要开启查询构造器
                    //查询构造器与流程节点里的输入参数取并集
                    //or节点
                    List<Map<String, Object>> orList = new ArrayList<Map<String, Object>>();
                    try {
                        //处理参数路径不存在的异常
                        orList = jsonDataDocument.read("$.data.System_QueryBuilder.InputPath.Or.*");
                    } catch (Exception e) {

                    }
                    for (Map<String, Object> subOr : orList) {
                        //处理and节点
                        List<Map<String, Object>> andList = new ArrayList<Map<String, Object>>();
                        try {
                            //处理参数路径不存在的异常
                            andList = (List<Map<String, Object>>) subOr.get(Constants.MATCH_TYPE_AND);
                        } catch (Exception e) {

                        }
                        JSONObject andCondition = new JSONObject();  //JSONObject.parseObject(singleCondition.toJSONString());
                        //标识同组and判断里面的条件都设置上了，如果有一个条件为空，则该组and条件废弃
                        boolean subAndParamTrue = true;
                        for (Map<String, Object> subAnd : andList) {
                            String tableUuid = (String) subAnd.get("table_uuid");
                            if (StringUtils.isEmpty(tableUuid)) {
                                continue;
                            }
                            String tableColumn = (String) subAnd.get("table_column");
                            String condition = (String) subAnd.get("condition");
                            Object tableColumnValue = subAnd.get("value"); //查询构造器里传入的就是值，无须再处理

                            //过滤查询条件：查询条件不为空的  或者  查询条件为空且查询是查询xxx是空的情况才拼接
                            if (null != tableColumnValue ||
                            (null == tableColumnValue
                            && ("201".equals(condition) || "200".equals(condition)
                            || "411".equals(condition) || "410".equals(condition)))) {

                                //如果值为空的话，则不添加
                                JSONArray jsonBuilderArray = new JSONArray();
                                if (andCondition.containsKey(condition)) {
                                    jsonBuilderArray = andCondition.getJSONArray(condition);
                                }
                                JSONObject conditionBuilderJson = new JSONObject();
                                conditionBuilderJson.put("left", tableUuid + "." + tableColumn);
                                conditionBuilderJson.put("left_type", "entity");
                                conditionBuilderJson.put("right", tableColumnValue);
                                conditionBuilderJson.put("right_type", "value");
                                jsonBuilderArray.add(conditionBuilderJson);
                                andCondition.put(condition, jsonBuilderArray);
                            } else {
                                subAndParamTrue = false;
                            }
                        }
                        if (subAndParamTrue && andCondition.keySet().size() > 0) {
                            queryBuilderConditionOr.getJSONArray("or").add(andCondition);
                        }
                    }

                    if (null != queryBuilderConditionOr.getJSONArray("or") && queryBuilderConditionOr.getJSONArray("or").size() > 0) {
                        //查询条件
                        paramMap.put(Constants.DM_SQL_KEYWORDS_QUERY_BUILDER, queryBuilderConditionOr);
                    }
                }
            }
            Object paramColumn1ValuePath = "$.data.user";
            String paramColumn1SourceType = "0";
            Object paramColumn1Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn1ValuePath, paramColumn1SourceType);
            paramMap.put("paramColumn1", paramColumn1Value);
            //调用数据查询
            log.info("==============执行查询");
            log.info("============dm params===" + paramMap.toJSONString());
            String returnStr = JSON.toJSONString(uvsvSUserService.Activity_13wix3e(paramMap));
            log.info("============dm return===" + returnStr);
            //判断dm处理成功还是失败
            JSONObject returnData = JSONObject.parseObject(returnStr);
            Integer dmCode = returnData.getInteger("code");
            if (!Constants.HTTP_STATUS_OK.equals(dmCode)) {
                throw new BusinessException("操作异常：" + returnData.get("msg") + errorSuffix);
            }
            //获取查询数据的返回值，将返回值存储到data里面
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, JsonPath.parse(returnStr).read("$.data"));
            paramsJson = Gateway_0nas19t(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("异常", e);
            throw e;
        } catch (Exception e) {
            log.error("异常", e);
            throw new BusinessException("查询数据失败" + errorSuffix + e.getMessage(), e);
        }
    }
    public String Activity_1rxifnj(String paramsJson) throws BusinessException {
        log.info("========执行数据新增方法=======");
        String taskName = "数据新增";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            DocumentContext jsonDataDocument = JsonPath.parse(paramsJson);
            JSONArray paramArray = new JSONArray();
            //处理请求参数
            JSONObject paramMap = new JSONObject();
            Object paramColumn1ValuePath = "$.data.user";
            String paramColumn1SourceType = "0";
            Object paramColumn1Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn1ValuePath, paramColumn1SourceType);
            paramMap.put("paramColumn1", paramColumn1Value);
            Object paramColumn2ValuePath = "$.data.tele";
            String paramColumn2SourceType = "0";
            Object paramColumn2Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn2ValuePath, paramColumn2SourceType);
            paramMap.put("paramColumn2", paramColumn2Value);
            Object paramColumn3ValuePath = "$.data.joinDate";
            String paramColumn3SourceType = "0";
            Object paramColumn3Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn3ValuePath, paramColumn3SourceType);
            paramMap.put("paramColumn3", paramColumn3Value);
            //调用数据新增
            log.info("============dm params===" + paramMap.toJSONString());
            String returnStr = JSON.toJSONString(uvsvTseService.Activity_1rxifnj(paramMap));
            log.info("============dm return===" + returnStr);
            //判断dm处理成功还是失败
            JSONObject returnData = JSONObject.parseObject(returnStr);
            Integer dmCode = returnData.getInteger("code");
            if (!Constants.HTTP_STATUS_OK.equals(dmCode)) {
                throw new BusinessException("操作异常：" + returnData.get("msg") + errorSuffix);
            }
            //获取新增数据的返回值，将返回值存储到data里面
            //获取返回数据存在的key值
            String resultKey = "Activity_1rxifnj";
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, JsonPath.parse(returnStr).read("$.data[0]"));
            paramsJson = Gateway_1lw1trf(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("异常", e);
            throw e;
        } catch (Exception e) {
            log.error("异常", e);
            throw new BusinessException("新增数据失败" + errorSuffix + e.getMessage(), e);
        }
    }
    public String Activity_1wywk8j(String paramsJson) throws BusinessException {
        log.info("========执行变量赋值方法=======");
        String taskName = "变量赋值";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            DocumentContext paramsJsonContext = JsonPath.parse(paramsJson);
            JSONArray params = new JSONArray();
            JSONObject jsonObject0 = new JSONObject();
            jsonObject0.put("paramsName", "joinDate");
            jsonObject0.put("paramsValue", "currentdate");
            jsonObject0.put("paramSourceType", "4");
            jsonObject0.put("paramNameFromType", "");
            params.add(jsonObject0);
            JSONObject globalVariableJson = new JSONObject();
            for (int i = 0; i < params.size(); i++) {
                JSONObject beanMap = params.getJSONObject(i);
                if (null == beanMap || beanMap.keySet().size() < 1) {
                    continue;
                }
                String bindKey = beanMap.getString("paramsName");
                if (StringUtils.isBlank(bindKey)) {
                    continue;
                }
                Object bindValueKey = beanMap.get("paramsValue");
                String paramSourceType = beanMap.getString("paramSourceType");
                Object bindValue = paramHandleService.getParamValue(paramsJsonContext,bindValueKey,paramSourceType);
                //获取是否是给环境变量赋值标识：1-给环境变量赋值，0-非环境变量赋值
                String paramNameFromType = beanMap.getString("paramNameFromType");
                if ("1".equals(paramNameFromType)) {
                    //环境变量设置值
                    globalVariableJson.put(bindKey, bindValue);
                } else {
                    //非环境变量设置值
                    paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", bindKey, bindValue);
                }
            }
	        //将环境变量的赋值信息存储到redis中
            if (null != globalVariableJson && globalVariableJson.keySet().size() > 0) {
                String token = paramsJsonContext.read("$.data.system.token");
                if (!StringUtils.isEmpty(token)) {
                    JSONObject gvJson = new JSONObject();
                    //获取redis存储的全局变量
                    Object gvObj = redisService.get(token + "_GLOBALVARIABLE");
                    if (null != gvObj && !"".equals(gvObj)) {
                        gvJson = JSON.parseObject((String) gvObj);
                    }
                    gvJson.putAll(globalVariableJson);
                    redisService.set(token + "_GLOBALVARIABLE", gvJson.toJSONString(), Constants.TIME_OUT);
                }
            }
            paramsJson = Activity_1rxifnj(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("变量赋值处理失败" + errorSuffix, e);
        }
    }
    public String Gateway_0nas19t(String paramsJson) throws BusinessException {
        log.info("========执行分支方法=======");
        String taskName = "";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            boolean hasNext = false;
            if(Flow_0l38oit(paramsJson)){
                hasNext = true;
                paramsJson = Gateway_1nbarvk(paramsJson);
            }
            else {
                hasNext = true;
                paramsJson = Event_0zonndn(paramsJson);
            }
            //当前过程未匹配到下一执行过程
            if (!hasNext) {
                throw new BusinessException("未找到下一执行过程" + errorSuffix);
            }
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("分支处理失败" + errorSuffix, e);
        }
    }
    public String Gateway_1nbarvk(String paramsJson) throws BusinessException {
        log.info("========执行分支方法=======");
        String taskName = "";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            boolean hasNext = false;
            if(Flow_00d4l4y(paramsJson)){
                hasNext = true;
                paramsJson = Activity_1wywk8j(paramsJson);
            }
            else {
                hasNext = true;
                paramsJson = Activity_1rxifnj(paramsJson);
            }
            //当前过程未匹配到下一执行过程
            if (!hasNext) {
                throw new BusinessException("未找到下一执行过程" + errorSuffix);
            }
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("分支处理失败" + errorSuffix, e);
        }
    }
    public String Gateway_1lw1trf(String paramsJson) throws BusinessException {
        log.info("========执行分支方法=======");
        String taskName = "";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            boolean hasNext = false;
            if(Flow_0mxcwhs(paramsJson)){
                hasNext = true;
                paramsJson = Event_0sbhk59(paramsJson);
            }
            else {
                hasNext = true;
                paramsJson = Event_1108jht(paramsJson);
            }
            //当前过程未匹配到下一执行过程
            if (!hasNext) {
                throw new BusinessException("未找到下一执行过程" + errorSuffix);
            }
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("分支处理失败" + errorSuffix, e);
        }
    }
    public String Event_0zonndn(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "此用户名已存在";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            String resultKey = "Event_0zonndn";
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
    public String Event_0sbhk59(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "用户新增成功";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            String resultKey = "Event_0sbhk59";
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
    public String Event_1108jht(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "用户新增失败";
        String processName = "添加用户信息流程";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            String resultKey = "Event_1108jht";
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