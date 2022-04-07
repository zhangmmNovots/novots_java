package com.weilaios.iqxceqhnhubt.api;

import com.alibaba.fastjson.JSONObject;
import com.weilaios.iqxceqhnhubt.utils.Result;
import com.weilaios.iqxceqhnhubt.da.service.WlosOptionSetService;
import com.weilaios.iqxceqhnhubt.model.WlosOptionSet;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : jyh
 * @content : 选项集省市区接口列表
 * @since : 2021/4/15  13:42
 */
@RestController
@RequestMapping("/dam")
public class WlosOptionSetController {

    @Autowired
    private WlosOptionSetService wlosOptionSetService;
  
    @PostMapping("/v1/wlosOptionSet/query")
    public Result query(@RequestBody String paramJson) {
        JSONObject jsonObject = JSONObject.parseObject(paramJson);
        String current_page = jsonObject.getString("current_page");
        String page_size = jsonObject.getString("page_size");
        String project_uuid = jsonObject.getString("project_uuid");
        String data_set_name = jsonObject.getString("data_set_name");
        Map<String, Object> map = wlosOptionSetService.querySetsAll(Integer.valueOf(current_page), Integer.valueOf(page_size), project_uuid, data_set_name);
        return Result.SUCCESS(map);
    }

    @GetMapping("/v1/wlosOptionSet/sys/province")
    public Result getProvince() {
        String project_uuid = "default";
        String data_set_name = "1dd48d3765494c17b145fd0856895287";
        Map<String, Object> map = wlosOptionSetService.querySetsAll(null, null, project_uuid, data_set_name);
        List<WlosOptionSet> result = (List<WlosOptionSet>) map.get("wlosOptionSets");
        return Result.SUCCESS(result);
    }
}