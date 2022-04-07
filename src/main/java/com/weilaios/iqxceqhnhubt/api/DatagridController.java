package com.weilaios.iqxceqhnhubt.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weilaios.iqxceqhnhubt.bl.DatagridService;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.jayway.jsonpath.JsonPath;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;

/**
 * 数据表单
 *
 * @author 
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class DatagridController {

    private transient Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private DatagridService datagridService;
    @Autowired
    private ParamHandleService paramHandleService;

    /**
     * 数据表格---新增实体数据
     *
     * @param data --需要新增的参数的json
     * @return com.fos.framework.utils.Result
     * @author 
     * @date 2020/11/11 16:31
     */
    @PostMapping(value = "/v1/datagridSave")
    public Result datagridSave(HttpServletRequest request, @RequestBody String data) {
        log.info("===============datagridSave=====" + data);
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            return Result.FAIL("操作异常：请传入新增数据内容");
        }
        data = JSON.toJSONString(JsonPath.read(data, "$.data"));
        String token = ProcessUtil.getTokenByRequest(request);
        JSONObject object = new JSONObject();
        object.put("token", token);
        //存储当前操作人
        JSONObject userinfo = paramHandleService.getUserSession(token);

        if (null != userinfo) {
            String currentUserId = userinfo.getString(UserSession.USER_UUID);
            object.put("operator_uuid", currentUserId);
        }

        String dataJson = ProcessUtil.jsonParamsAdd(data, "$", "system", object);
        datagridService.datagridSave(dataJson);
        return Result.SUCCESS();
    }

    /**
     * 数据表格---获取单条信息
     *
     * @param data
     * @return com.fos.framework.utils.Result
     * @author 
     * @date 2020/11/24 16:43
     */
    @PostMapping(value = "/v1/datagridInfo")
    public Result datagridInfo(HttpServletRequest request,  @RequestBody String data) {
        log.info("===============datagridInfo=====" + data);
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询数据的内容");
        }
        data = JSON.toJSONString(JsonPath.read(data, "$.data"));
        String token = ProcessUtil.getTokenByRequest(request);
        JSONObject jsonObject = this.queryHandle(data, token, "info");
        return Result.SUCCESS(jsonObject);

    }

    /**
     * 数据表格---修改
     *
     * @param data
     * @return com.fos.framework.utils.Result
     * @author 
     * @date 2020/11/24 16:43
     */
    @PostMapping(value = "/v1/datagridUpdate")
    public Result datagridUpdate(HttpServletRequest request,  @RequestBody String data) {

        log.info("===============datagridUpdate=====" + data);
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            return Result.FAIL("操作异常：请传入修改数据的内容");
        }
        data = JSON.toJSONString(JsonPath.read(data, "$.data"));
        String token = ProcessUtil.getTokenByRequest(request);
        JSONObject object = new JSONObject();
        object.put("token", token);
        //存储当前操作人
        JSONObject userinfo = paramHandleService.getUserSession(token);
        if (null != userinfo) {
            String currentUserId = String.valueOf(userinfo.get(UserSession.USER_UUID));
            object.put("operator_uuid", currentUserId);
        }
        String dataJson = ProcessUtil.jsonParamsAdd(data, "$", "system", object);
        datagridService.datagridUpdate(dataJson);
        return Result.SUCCESS();            
    }

    /**
     * 数据表格--删除
     *
     * @param data
     * @return com.fos.framework.utils.Result
     * @author 
     * @date 2020/11/24 16:44
     */
    @PostMapping(value = "/v1/datagridDelete")
    public Result datagridDelete(HttpServletRequest request,  @RequestBody String data) {

        log.info("===============datagridDelete=====" + data);
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            return Result.FAIL("操作异常：请传入删除数据的内容");
        }
        data = JSON.toJSONString(JsonPath.read(data, "$.data"));
        String token = ProcessUtil.getTokenByRequest(request);
        JSONObject object = new JSONObject();
        object.put("token", token);
        //存储当前操作人
        JSONObject userinfo = paramHandleService.getUserSession(token);
        if (null != userinfo) {
            //用户端信息
            String currentUserId = String.valueOf(userinfo.get(UserSession.USER_UUID));
            object.put("operator_uuid", currentUserId);
        }
        String dataJson = ProcessUtil.jsonParamsAdd(data, "$", "system", object);
        datagridService.datagridDelete(dataJson);
        return Result.SUCCESS();            
    }

    /**
     * 数据表格查询列表
     *
     * @param data
     * @return com.fos.framework.utils.Result
     * @author 
     * @date 2020/11/24 16:44
     */
    @PostMapping(value = "/v1/datagridQuery")
    public Result datagridQuery(HttpServletRequest request,  @RequestBody String data) {
        if (StringUtils.isEmpty(data)) {
            throw new BusinessException("操作异常：请传入查询数据的内容");
        }
        String applicationUuid = null;
        try{
            applicationUuid = JsonPath.read(data, "$.applicationUuid");
        } catch(Exception e){
        }
        data = JSON.toJSONString(JsonPath.read(data, "$.data"));
        data = ProcessUtil.jsonParamsAdd(data, "$", "application_uuid", applicationUuid);
        String token = ProcessUtil.getTokenByRequest(request);
        JSONObject jsonObject = this.queryHandle(data, token, "list");
        return Result.SUCCESS(jsonObject);            
    }

    public JSONObject queryHandle(String data, String token, String queryFlag) throws BusinessException {
        JSONObject object = new JSONObject();
        object.put("token", token);
        //存储当前操作人
        JSONObject userinfo = paramHandleService.getUserSession(token);
        if (null != userinfo) {
            //用户端信息
            String currentUserId = String.valueOf(userinfo.get(UserSession.USER_UUID));
            object.put("operator_uuid", currentUserId);
        }
        String dataJson = ProcessUtil.jsonParamsAdd(data, "$", "system", object);
        JSONObject jsonObject = datagridService.datagridQuery(dataJson, queryFlag);
        return jsonObject;
    }

    /**
     * 根据实体和主键编号查询数据
     *
     * @param data: id-数据id  tableUuid--表id
     * @return com.fos.framework.utils.Result
     * @author 
     * @date 2020/12/18 18:25
     */
    @GetMapping(value = "/v1/datagridQueryById")
    public Result datagridQueryById(String data, String applicationUuid) {
        //校验参数是否为空
        if (StringUtils.isEmpty(data)) {
            return Result.FAIL("操作异常：请传入查询数据");
        }
        data = JSON.toJSONString(JsonPath.read(data, "$.data"));
        data = ProcessUtil.jsonParamsAdd(data, "$", "application_uuid", applicationUuid);
        JSONObject jsonObject = datagridService.datagridQueryById(data);
        return Result.SUCCESS(jsonObject);     
    }

}
