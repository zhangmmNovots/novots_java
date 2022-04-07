package com.weilaios.iqxceqhnhubt.bl.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.weilaios.iqxceqhnhubt.bl.*;
import com.weilaios.iqxceqhnhubt.model.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.*;
import org.apache.commons.lang3.StringUtils;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 环境变量实现类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
@Service
public class GlobalVariableServiceImpl implements GlobalVariableService {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisUtil redisService;
    @Autowired
    private ParamHandleService paramHandleService;


    @Override
    public void handelSetGlobalVariable(String paramsJson, JSONArray defGlobalVariableList) throws BusinessException{
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        DocumentContext paramsJsonDc = JsonPath.using(conf).parse(paramsJson);
        //处理全局变量存储
        //获取用户token，如果存在用户token的存储到用户信息里面，如果不存在用户信息，则不存储
        //判断当前用户信息 token
        String token = paramsJsonDc.read("$.data.system.token");
        if (!StringUtils.isEmpty(token)) {
            log.info("======存在用户信息，开始存储全局变量======");
            //判断用户登录信息是否超时
            try {
                if (null != defGlobalVariableList && defGlobalVariableList.size() > 0) {
                    JSONObject gvJson = new JSONObject();
                    //获取redis存储的全局变量
                    Object gvObj = redisService.get(token + "_GLOBALVARIABLE");
                    if (null != gvObj && !"".equals(gvObj)) {
                        gvJson = JSON.parseObject((String) gvObj);
                    }
                    for (Object defGv : defGlobalVariableList) {
                        JSONObject returnMap = (JSONObject) defGv;
                        String keyName = String.valueOf(returnMap.get("fieldNameKey"));
                        Object keyPath = returnMap.get("fieldValue");
                        String paramSourceType = String.valueOf(returnMap.get("paramSourceType"));
                        Object keyValue = paramHandleService.getParamValue(paramsJsonDc, keyPath, paramSourceType);
                        gvJson.put(keyName, keyValue);
                    }
                    redisService.set(token + "_GLOBALVARIABLE", gvJson.toJSONString(), Constants.TIME_OUT);
                }
            } catch (Exception e) {
                log.error("操作异常");
                throw new BusinessException("操作异常", e);
            }
        }
    }
}