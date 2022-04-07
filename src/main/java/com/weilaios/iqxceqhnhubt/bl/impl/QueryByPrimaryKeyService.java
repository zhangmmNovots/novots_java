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
import com.weilaios.iqxceqhnhubt.da.service.UvsvApplicationRevenuService;
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
public class QueryByPrimaryKeyService {

    private transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamHandleService paramHandleService;
    @Autowired
    private GatewayMatchService gatewayMatchService;
    @Autowired
    private GlobalVariableService globalVariableService;
    @Autowired
    private UvsvApplicationRevenuService uvsvApplicationRevenuService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject processMainExecute(String paramsJson) throws BusinessException {
        //调用开始方法
        if (StringUtils.isBlank(paramsJson)) {
            paramsJson = "{\"data\":{}}";
        }
        paramsJson = StartEvent_1wj0yk1p62394177(paramsJson);
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

    public boolean Flow_1g5859b(String paramsJson) throws BusinessException {
    log.info("========执行顺序流分支判断方法=======");
        try {
            JSONObject conditionJson = new JSONObject();
            JSONArray orArray = new JSONArray();
            JSONObject orJson0 = new JSONObject();
            JSONArray andArray0 = new JSONArray();
            JSONObject andJson00 = new JSONObject();
            andJson00.put("paramsType", "Primarykey");
            andJson00.put("variable", "$.data.Activity_0f3s7ye.resultData[0].uvsv_application_revenu_id");
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
    public String StartEvent_1wj0yk1p62394177(String paramsJson) throws BusinessException {
        log.info("========执行开始方法=======");
        String taskName = "开始事件";
        String processName = "根据主键查询";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            paramsJson = Activity_0f3s7ye(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("开始过程处理失败" + errorSuffix, e);
        }
    }
    public String Activity_0f3s7ye(String paramsJson) throws BusinessException {
        log.info("========执行数据查询方法=======");
        String taskName = "数据查询";
        String processName = "根据主键查询";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
            DocumentContext jsonDataDocument = JsonPath.using(conf).parse(paramsJson);
            //获取返回数据存在的key值
            String resultKey = "Activity_0f3s7ye";
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
            Object paramColumn1ValuePath = "$.data.id";
            String paramColumn1SourceType = "0";
            Object paramColumn1Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn1ValuePath, paramColumn1SourceType);
            paramMap.put("paramColumn1", paramColumn1Value);
            //调用数据查询
            log.info("==============执行查询");
            log.info("============dm params===" + paramMap.toJSONString());
            String returnStr = JSON.toJSONString(uvsvApplicationRevenuService.Activity_0f3s7ye(paramMap));
            log.info("============dm return===" + returnStr);
            //判断dm处理成功还是失败
            JSONObject returnData = JSONObject.parseObject(returnStr);
            Integer dmCode = returnData.getInteger("code");
            if (!Constants.HTTP_STATUS_OK.equals(dmCode)) {
                throw new BusinessException("操作异常：" + returnData.get("msg") + errorSuffix);
            }
            //获取查询数据的返回值，将返回值存储到data里面
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, JsonPath.parse(returnStr).read("$.data"));
            paramsJson = Gateway_1j3osnn(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("异常", e);
            throw e;
        } catch (Exception e) {
            log.error("异常", e);
            throw new BusinessException("查询数据失败" + errorSuffix + e.getMessage(), e);
        }
    }
    public String Gateway_1j3osnn(String paramsJson) throws BusinessException {
        log.info("========执行分支方法=======");
        String taskName = "";
        String processName = "根据主键查询";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            boolean hasNext = false;
            if(Flow_1g5859b(paramsJson)){
                hasNext = true;
                paramsJson = Event_18ucyuc(paramsJson);
            }
            else {
                hasNext = true;
                paramsJson = Event_0ijjayu(paramsJson);
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
    public String Event_18ucyuc(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "主键不存在";
        String processName = "根据主键查询";
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
    public String Event_0ijjayu(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "主键存在";
        String processName = "根据主键查询";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            String resultKey = "Event_0ijjayu";
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