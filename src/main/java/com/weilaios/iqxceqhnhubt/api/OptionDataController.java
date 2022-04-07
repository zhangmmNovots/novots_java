package com.weilaios.iqxceqhnhubt.api;



import com.weilaios.iqxceqhnhubt.utils.Result;
import com.weilaios.iqxceqhnhubt.da.service.OptionDataService;
import com.weilaios.iqxceqhnhubt.model.OptionData;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jyh
 * @content : 选项集数据接口列表
 * @since : 2021/11/2  13:42
 */
@RestController
@RequestMapping("/dam")
public class OptionDataController {


    @Autowired
    private OptionDataService optionDataService;

    /***
     *
     * 根据id查询选项集详情
     * @param uuid id
     * @return
     */
    @GetMapping("/v1/wlosOptionData/{uuid}")
    public Result getById(@PathVariable String uuid) {
        OptionData wlosOptionData = optionDataService.queryById(uuid);
        return Result.SUCCESS(wlosOptionData);
    }

    /***
     * 查询选项集列表 带分页
     * @param wlosOptionData
     * @return
     */
    @GetMapping("/v1/wlosOptionData/list")
    public Result list(OptionData wlosOptionData) {
        return Result.SUCCESS(optionDataService.queryAll(wlosOptionData,"1"));

    }


    /***
     * 查询选项集列表 带分页
     * @param wlosOptionData
     * @return
     */
    @GetMapping("/v1/wlosOptionData/noPage/list")
    public Result listQuery(OptionData wlosOptionData) {
        return Result.SUCCESS(optionDataService.queryAll(wlosOptionData));
    }





    @PostMapping("/v1/wlosOptionData/staticEntity/query")
    public Result staticEntityData(@RequestBody List<String> entityEnNames) {
        ArrayList<Object> results = optionDataService.staticEntityData(entityEnNames);

        return Result.SUCCESS(results);
    }


}
