package com.weilaios.iqxceqhnhubt.api;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.weilaios.iqxceqhnhubt.bl.*;
import com.weilaios.iqxceqhnhubt.bl.impl.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.*;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 流程执行接口
 * @author
 * @date Apr 7, 2022 3:06:39 PM
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class QueryAuthorityController {
    private transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private QueryAuthorityService queryAuthorityService;

    /**
    * 查询权限
    *
    * paramsJson      所需要的参数
    * processJsonUUid 执行的流程id
    * @return
    */
    @PostMapping(value = "/v1/p62451569e4b02961f830338f")
    public Result p62451569e4b02961f830338fDo(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject params) {
        log.info("===============流程执行开始=======");
        Result result = new Result();
        try {
            JSONObject paramsJSONObject = params.getJSONObject("paramsJson");
            String processName = "查询权限";
            String paramsJson = null;
            if (null == paramsJSONObject || paramsJSONObject.keySet().size() < 1) {
                paramsJson = "{\"data\":{}}";
            } else {
                paramsJson = paramsJSONObject.toJSONString();
            }
            //校验请求参数
            //组装流程执行的系统默认参数
            String applicationId = params.getString("applicationId");
            String token = ProcessUtil.getTokenByRequest(request);
            JSONObject object = new JSONObject();
            object.put("process_name", processName);
            object.put("application_id", applicationId);
            object.put("token", token);
            object.put("create_time", new Timestamp(System.currentTimeMillis()).toString());
            String dataJson = ProcessUtil.jsonParamsAdd(paramsJson, "$.data", "system", object);

            //调用执行流程方法
            JSONObject returnDataJson = queryAuthorityService.processMainExecute(dataJson);
            //移除过程执行中的临时变量
            if (null != returnDataJson) {
                returnDataJson.remove(Constants.DOWN_FILE_FLAG_KEY);
                returnDataJson.remove(Constants.SYSTEM_RETURN_TOKEN);
            }
            result.setData(returnDataJson);
            result.setCode(HttpStatus.SC_OK);
            log.info("===============流程执行完成=======");
        } catch (BusinessException e) {
            log.error("处理流程异常", e);
            result.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            result.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("处理流程异常", e);
            result.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            result.setMsg("操作异常");
        }
        return result;
    }
}