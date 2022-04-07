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
import com.weilaios.iqxceqhnhubt.da.service.UvsvSEmployeeTestService;
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
public class DeleteTestService {

    private transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamHandleService paramHandleService;
    @Autowired
    private GlobalVariableService globalVariableService;
    @Autowired
    private UvsvSEmployeeTestService uvsvSEmployeeTestService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject processMainExecute(String paramsJson) throws BusinessException {
        //调用开始方法
        if (StringUtils.isBlank(paramsJson)) {
            paramsJson = "{\"data\":{}}";
        }
        paramsJson = StartEvent_1wj0yk1p6232e575(paramsJson);
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

    public String StartEvent_1wj0yk1p6232e575(String paramsJson) throws BusinessException {
        log.info("========执行开始方法=======");
        String taskName = "开始事件";
        String processName = "删除测试";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try{
            paramsJson = Activity_1nv16yb(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("处理失败", e);
            throw e;
        } catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("开始过程处理失败" + errorSuffix, e);
        }
    }
    public String Activity_1nv16yb(String paramsJson) throws BusinessException {
        log.info("========执行数据删除方法=======");
        String taskName = "数据删除";
        String processName = "删除测试";
        String errorSuffix = "[" + processName + "-" + taskName + "]";
        try {
            DocumentContext jsonDataDocument = JsonPath.parse(paramsJson);
            //处理请求参数
            JSONObject paramMap = new JSONObject();
            Object paramColumn1ValuePath = "$.data.system.operator_uuid";
            String paramColumn1SourceType = "0";
            Object paramColumn1Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn1ValuePath, paramColumn1SourceType);
            paramMap.put("paramColumn1", paramColumn1Value);
            Object paramColumn2ValuePath = "$.data.test_user";
            String paramColumn2SourceType = "0";
            Object paramColumn2Value = paramHandleService.getParamValue(jsonDataDocument, paramColumn2ValuePath, paramColumn2SourceType);
            paramMap.put("paramColumn2", paramColumn2Value);
            //调用数据新增
            log.info("============dm params===" + paramMap.toJSONString());
            String returnStr = JSON.toJSONString(uvsvSEmployeeTestService.Activity_1nv16yb(paramMap));
            log.info("============dm return===" + returnStr);
            //判断dm处理成功还是失败
            JSONObject returnData = JSONObject.parseObject(returnStr);
            Integer dmCode = returnData.getInteger("code");
            if (!Constants.HTTP_STATUS_OK.equals(dmCode)) {
                throw new BusinessException("操作异常：" + returnData.get("msg") + errorSuffix);
            }
            //获取新增数据的返回值，将返回值存储到data里面
            //获取返回数据存在的key值
            String resultKey = "Activity_1nv16yb";
            paramsJson = Event_18ucyuc(paramsJson);
            return paramsJson;
        } catch (BusinessException e) {
            log.error("异常", e);
            throw e;
        } catch (Exception e) {
            log.error("异常", e);
            throw new BusinessException("删除数据失败" + errorSuffix + e.getMessage(), e);
        }
    }
    public String Event_18ucyuc(String paramsJson) throws BusinessException {
        log.info("========执行结束方法=======");
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        String taskName = "结束事件";
        String processName = "删除测试";
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