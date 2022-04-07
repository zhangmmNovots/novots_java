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
public class ExportEmployeeTableService {

    private transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamHandleService paramHandleService;
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
        paramsJson = StartEvent_1wj0yk1p623d8107(paramsJson);
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

    public String StartEvent_1wj0yk1p623d8107(String paramsJson) throws BusinessException {
        log.info("========执行开始方法=======");
        String taskName = "开始事件";
        String processName = "导出员工表";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            paramsJson = Activity_0f2xovj(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("开始过程处理失败" + errorSuffix, e);
        }
    }
    public String Activity_0f2xovj(String paramsJson) throws BusinessException {
        log.info("========执行数据查询方法=======");
        String taskName = "数据查询";
        String processName = "导出员工表";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
            DocumentContext jsonDataDocument = JsonPath.using(conf).parse(paramsJson);
            //获取返回数据存在的key值
            String resultKey = "Activity_0f2xovj";
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
            //调用数据查询
            log.info("==============执行查询");
            log.info("============dm params===" + paramMap.toJSONString());
            String returnStr = JSON.toJSONString(uvsvApplicationRevenuService.Activity_0f2xovj(paramMap));
            log.info("============dm return===" + returnStr);
            //判断dm处理成功还是失败
            JSONObject returnData = JSONObject.parseObject(returnStr);
            Integer dmCode = returnData.getInteger("code");
            if (!Constants.HTTP_STATUS_OK.equals(dmCode)) {
                throw new BusinessException("操作异常：" + returnData.get("msg") + errorSuffix);
            }
            //获取查询数据的返回值，将返回值存储到data里面
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, JsonPath.parse(returnStr).read("$.data"));
            paramsJson = Activity_033ud26(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("异常", e);
            throw e;
        } catch (Exception e) {
            log.error("异常", e);
            throw new BusinessException("查询数据失败" + errorSuffix + e.getMessage(), e);
        }
    }
    public String Activity_033ud26(String paramsJson) throws BusinessException {
        log.info("========执行导出数据方法=======");
        String taskName = "导出数据";
        String processName = "导出员工表";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            DocumentContext paramsJsonContext = JsonPath.parse(paramsJson);
            //导出的数据内容key
            String downDataKey = "$.data.Activity_0f2xovj.resultData";
            if (StringUtils.isBlank(downDataKey)) {
                throw new BusinessException("操作异常：未选择导出数据" + errorSuffix);
            }
            String downDataType = "list";
            String downFileName = "收入确认";
            String downFileType = "1";
            JSONArray columns = null;
            columns = new JSONArray();
            JSONObject jsonObject0 = new JSONObject();
            jsonObject0.put("column", "fee");
            jsonObject0.put("columnDesc", "收入金额");
            jsonObject0.put("dataType", "String");
            jsonObject0.put("lookupFlag", "");
            jsonObject0.put("lookupUuid", "");
            columns.add(jsonObject0);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("column", "month");
            jsonObject1.put("columnDesc", "所属月份");
            jsonObject1.put("dataType", "Date");
            jsonObject1.put("lookupFlag", "");
            jsonObject1.put("lookupUuid", "");
            columns.add(jsonObject1);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("column", "uvsv_dzsp");
            jsonObject2.put("columnDesc", "申请人");
            jsonObject2.put("dataType", "Lookup");
            jsonObject2.put("lookupFlag", "");
            jsonObject2.put("lookupUuid", "");
            columns.add(jsonObject2);
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("column", "userName");
            jsonObject3.put("columnDesc", "用户名");
            jsonObject3.put("dataType", "");
            jsonObject3.put("lookupFlag", "lookup");
            jsonObject3.put("lookupUuid", "uvsv_dzsp");
            columns.add(jsonObject3);
            JSONObject jsonObject4 = new JSONObject();
            jsonObject4.put("column", "telephone");
            jsonObject4.put("columnDesc", "电话");
            jsonObject4.put("dataType", "");
            jsonObject4.put("lookupFlag", "lookup");
            jsonObject4.put("lookupUuid", "uvsv_dzsp");
            columns.add(jsonObject4);
            JSONObject jsonObject5 = new JSONObject();
            jsonObject5.put("column", "entryTime");
            jsonObject5.put("columnDesc", "入职时间");
            jsonObject5.put("dataType", "");
            jsonObject5.put("lookupFlag", "lookup");
            jsonObject5.put("lookupUuid", "uvsv_dzsp");
            columns.add(jsonObject5);
            JSONObject jsonObject6 = new JSONObject();
            jsonObject6.put("column", "idNumber");
            jsonObject6.put("columnDesc", "身份证号");
            jsonObject6.put("dataType", "");
            jsonObject6.put("lookupFlag", "lookup");
            jsonObject6.put("lookupUuid", "uvsv_dzsp");
            columns.add(jsonObject6);
            JSONObject jsonObject7 = new JSONObject();
            jsonObject7.put("column", "address");
            jsonObject7.put("columnDesc", "住址");
            jsonObject7.put("dataType", "");
            jsonObject7.put("lookupFlag", "lookup");
            jsonObject7.put("lookupUuid", "uvsv_dzsp");
            columns.add(jsonObject7);
            JSONObject jsonObject8 = new JSONObject();
            jsonObject8.put("column", "uvsv_psey");
            jsonObject8.put("columnDesc", "项目");
            jsonObject8.put("dataType", "Lookup");
            jsonObject8.put("lookupFlag", "");
            jsonObject8.put("lookupUuid", "");
            columns.add(jsonObject8);
            JSONObject jsonObject9 = new JSONObject();
            jsonObject9.put("column", "project_id");
            jsonObject9.put("columnDesc", "项目ID");
            jsonObject9.put("dataType", "");
            jsonObject9.put("lookupFlag", "lookup");
            jsonObject9.put("lookupUuid", "uvsv_psey");
            columns.add(jsonObject9);
            JSONObject jsonObject10 = new JSONObject();
            jsonObject10.put("column", "entry_name");
            jsonObject10.put("columnDesc", "项目名称");
            jsonObject10.put("dataType", "");
            jsonObject10.put("lookupFlag", "lookup");
            jsonObject10.put("lookupUuid", "uvsv_psey");
            columns.add(jsonObject10);
            List<Map<String, Object>> dataList = new ArrayList<>();
            //获取导出数据
            if ("list".equals(downDataType)) {
                List<Map<String, String>> downDataList = null;
                try {
                    downDataList = paramsJsonContext.read(downDataKey);
                } catch (Exception e) {
                    log.error("异常处理导出数据的数据获取", e);
                }
                if (null != downDataList && downDataList.size() > 0) {
                    for (Map dataMap : downDataList) {
                        Map map = new HashMap();
                        //子表单最大数据
                        Integer maxSubListSize = 1;
                        for (int i = 0; i < columns.size(); i++) {
                            JSONObject columnMap = columns.getJSONObject(i);
                            //字段
                            String column = columnMap.getString("column");
                            //数据类型
                            String data_type= Constants.DM_DATA_TYPE_STRING;
                            try {
                                data_type = columnMap.getString("dataType");
                            } catch (Exception e) {
                            }
                            Object columnValue = "";
                            String lookupFlag = "";
                            //判断是否是lookup查询里的字段
                            try {
                                lookupFlag = columnMap.getString("lookupFlag");
                            } catch (Exception e) {
                            }
                            if ("lookup".equals(lookupFlag)) {
                                String lookupUuid = columnMap.getString("lookupUuid");
                                //从lookup查询的结果中获取子查询的字段值
                                try {
                                    columnValue = JsonPath.read(JSON.toJSONString(dataMap), "$." + lookupUuid +"[*]"+ ".columns." + column);
                                    if (null != columnValue && columnValue instanceof List && ((List<?>) columnValue).size() > 0) {
                                        maxSubListSize = ((List<?>) columnValue).size();
                                    }
                                } catch (Exception e) {
                                }
                            } else {
                                columnValue = dataMap.get(column);
                            }
                            //根据不同的文件类型，处理需要返回的数据：文件类型处理，选项集数据处理
                            columnValue = ProcessUtil.handleDownDataByDataType(columnValue, data_type);
                            map.put(column, columnValue);
                        }
                        map.put("maxSubListSize", maxSubListSize);
                        dataList.add(map);
                    }
                }
            }
            // 列头
            String[] headers ={"收入金额","所属月份","申请人","用户名","电话","入职时间","身份证号","住址","项目","项目ID","项目名称"};
            String[] fileColumns = {"fee","month","uvsv_dzsp","userName","telephone","entryTime","idNumber","address","uvsv_psey","project_id","entry_name"};
            // 导出模板
            Map downFileMap = new HashMap();
            //标识是文件下载
            downFileMap.put(Constants.DOWN_FILE_FLAG_KEY, Constants.DOWN_FILE_FLAG_VALUE_1);
            downFileMap.put("downFileFrom", Constants.DOWN_FILE_FROM_TYPE_EXCEL);
            downFileMap.put("fileName", downFileName);
            downFileMap.put("fileType", downFileType);
            downFileMap.put("headers", headers);
            downFileMap.put("columns", fileColumns);
            downFileMap.put("dataList", dataList);
            String resultKey = "Activity_033ud26";
            paramsJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", resultKey, downFileMap);
            paramsJson = Event_18ucyuc(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("导出数据处理失败" + errorSuffix, e);
        }
    }
    public String Event_18ucyuc(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "结束事件";
        String processName = "导出员工表";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            String resultKey = "Event_18ucyuc";
            //返回数据处理
            JSONObject jsonObject = new JSONObject();
            //添加标识符：当前的结果属于哪个结束节点选择出来的
            jsonObject.put("system_returnBelong", resultKey);
            //标识是是否是文件下载
            jsonObject.put(Constants.DOWN_FILE_FLAG_KEY, Constants.DOWN_FILE_FLAG_VALUE_0);
            boolean checkFile = false;
            JSONArray returnDataList = new JSONArray();
            JSONObject jsonObject0 = new JSONObject();
            jsonObject0.put("keyName", "application_revenue");
            jsonObject0.put("keyPath", "$.data.Activity_033ud26");
            jsonObject0.put("paramSourceType", "0");
            returnDataList.add(jsonObject0);
            for (int i = 0; i < returnDataList.size(); i++) {
                JSONObject returnMap = returnDataList.getJSONObject(i);
                String keyName = returnMap.getString("keyName");
                Object keyPath = returnMap.get("keyPath");
                String paramSourceType = returnMap.getString("paramSourceType");
                Object keyValue = paramHandleService.getParamValue(paramsJsonDc, keyPath, paramSourceType);
                boolean isFileValue = ProcessUtil.checkReturnFile(keyValue);
                //判断获取的值是否为文件下载的数据
                if (isFileValue) {
                    checkFile = true;
                    jsonObject.put(Constants.DOWN_FILE_KEY_PATH, keyPath);
                    jsonObject.put("system_flow_down_file_key_value_name", keyName);
                }
                jsonObject.put(keyName, keyValue);
            }
            if (checkFile) {
                jsonObject.put(Constants.DOWN_FILE_FLAG_KEY, Constants.DOWN_FILE_FLAG_VALUE_1);
            }
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