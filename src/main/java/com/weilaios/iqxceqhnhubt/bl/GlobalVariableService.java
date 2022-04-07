package com.weilaios.iqxceqhnhubt.bl;

import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.alibaba.fastjson.JSONArray;

/**
 * 环境变量处理
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public interface GlobalVariableService {


    /**
    * 处理过程定义的全局变量
    * @param paramsJson
    * @param defGlobalVariableList
    * @author
    * @date
    * @return void
    */
    public void handelSetGlobalVariable(String paramsJson, JSONArray defGlobalVariableList) throws BusinessException;
}