package com.weilaios.iqxceqhnhubt.api;

import com.weilaios.iqxceqhnhubt.model.AppPermission;
import com.weilaios.iqxceqhnhubt.da.service.AppPermissionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.List;

/**
 *  AppPermission 接口列表
 *
 * @author jyh
 * @since 2022-03-02 15:26:08
 */
@RestController
@RequestMapping("/page")
public class AppPermissionController {
    
    @Autowired
    private AppPermissionService appPermissionService;

    @GetMapping("/v1/wlosAppPermission/{id}")
    public Result getById(@PathVariable String id) {
        AppPermission appPermission = appPermissionService.queryById(id);
        return Result.SUCCESS(appPermission);
    }

    @GetMapping("/v1/wlosAppPermission")
    public Result getByEntity(AppPermission appPermission) {
        AppPermission appPermissionResult =  appPermissionService.queryByEntity(appPermission);
          return Result.SUCCESS(appPermissionResult);
    }

    @GetMapping("/v1/wlosAppPermission/list")
    public Result list(AppPermission appPermission) {
        List<AppPermission> appPermissionList = appPermissionService.queryAll(appPermission);
        return Result.SUCCESS(appPermissionList);
    }

    @PostMapping("/v1/wlosAppPermission")
    public Result insert(@RequestBody AppPermission appPermission){
        appPermissionService.insert(appPermission);
        return Result.SUCCESS();
    }

    @PutMapping("/v1/wlosAppPermission")
    public Result update(@RequestBody AppPermission appPermission){
         appPermissionService.update(appPermission);
         return Result.SUCCESS();
    }

    @DeleteMapping("/v1/wlosAppPermission/{id}")
    public Result deleteOne(@PathVariable String id){
        appPermissionService.deleteById(id);
        return Result.SUCCESS();
    }

    @DeleteMapping("/v1/wlosAppPermission")
    public Result deleteBatch(@RequestBody List<String> ids){
       appPermissionService.deleteByIds(ids);
        return Result.SUCCESS();
    }

}
