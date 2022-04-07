package com.weilaios.iqxceqhnhubt.api;

import com.alibaba.fastjson.JSONObject;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.utils.ProcessUtil;
import com.weilaios.iqxceqhnhubt.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 环境变量
 * @author
 * @date Apr 7, 2022 3:06:39 PM
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class GlobalVariableController {
    private transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ParamHandleService paramHandleService;

    /**
     * 根据全局变量的key获取变量值
     * @param request
     * @param fieldNameKey
     * @return Result
     */
    @GetMapping(value = "/v1/queryGlobalValueByKey")
    public Result queryGlobalValueByKey(HttpServletRequest request, String fieldNameKey, String pageId) {
        Map resultMap = new HashMap();
        resultMap.put("fieldNameKey", fieldNameKey);
        resultMap.put("fieldNameValue", "");
        if (StringUtils.isEmpty(fieldNameKey)) {
            throw new BusinessException("操作异常：请传入变量id");
        }
        String token = ProcessUtil.getTokenByRequest(request);
        //获取全局变量信息
        //判断是否为系统环境变量中的项目编号
        if (Constants.GLOBAL_VARIABLE_KEY_PROJECT_UUID.equals(fieldNameKey)) {
            //根据pageId获取项目id
            String projectId = Constants.PROJECT_ID_VALUE;
            resultMap.put("fieldNameValue", projectId);
        } else {
            Object value = paramHandleService.getGlobalVariableValue(token, fieldNameKey, null);
            resultMap.put("fieldNameValue", null != value && "" != value ? value : "");
        }
        return Result.SUCCESS(resultMap);
    }
}