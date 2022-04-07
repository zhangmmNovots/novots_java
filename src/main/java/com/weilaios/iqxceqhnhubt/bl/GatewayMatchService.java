package com.weilaios.iqxceqhnhubt.bl;

import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;

/**
 * 网关判断
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public interface GatewayMatchService {

    /**
     * 网关判断处理
     **/
    public boolean branchConditionMatch(String paramsJson, JSONObject processJson) throws BusinessException;
   
}