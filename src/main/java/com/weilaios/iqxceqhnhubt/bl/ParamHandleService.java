package com.weilaios.iqxceqhnhubt.bl;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;

/**
 * 流程执行参数服务
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public interface ParamHandleService {

    public Object getParamValue(DocumentContext paramsJsonContext, Object paramKey, String paramSourceType) throws BusinessException;
    public Object getGlobalVariableValue(String token, Object paramKey, String paramSourceType);
    public JSONObject getUserSession(String token);
    public Object getBlocklyValue(DocumentContext paramsJsonContext, Object paramKey) throws BusinessException;
}