package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvApplication;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 申请表 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvApplicationController {

    @Autowired
    private UvsvApplicationService uvsvApplicationService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvApplication/{id}")
    public Result<UvsvApplication> getById(@PathVariable String id) {
        UvsvApplication uvsvApplication = uvsvApplicationService.getById(id);
        return Result.SUCCESS(uvsvApplication);
    }


    /**
     * 通过 申请表 属性字段查询单条数据详情
     *
     * @param uvsvApplication 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvApplication")
    public Result<UvsvApplication> getByEntity(@RequestBody UvsvApplication uvsvApplication) {
        List<UvsvApplication> uvsvApplicationlist = uvsvApplicationService.list(new QueryWrapper<>(uvsvApplication));
        if (ObjectUtils.isNotEmpty(uvsvApplicationlist)){
            //多条数据则只返回第一条
            UvsvApplication uvsvApplicationResult = uvsvApplicationlist.get(0);
            return Result.SUCCESS(uvsvApplicationResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 申请表 属性字段查询数据集列表
     *
     * @param uvsvApplication 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvApplication/list")
    public Result<List<UvsvApplication>> list(@RequestBody UvsvApplication uvsvApplication) {
        List<UvsvApplication> uvsvApplicationList = uvsvApplicationService.list(new QueryWrapper<>(uvsvApplication));

        return Result.SUCCESS(uvsvApplicationList);
    }


    /**
     * 通过 申请表 属性字段 分页查询数据集列表
     *
     * @param uvsvApplication 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvApplication/list/page")
    public Result<Page<UvsvApplication>> listWithPage(@RequestBody UvsvApplication uvsvApplication, @RequestParam long current, @RequestParam long size) {
        Page<UvsvApplication> page = new Page<>(current, size);
        page = uvsvApplicationService.page(page, new QueryWrapper<>(uvsvApplication));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvApplication 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvApplication")
    public Result<Boolean> insert(@RequestBody UvsvApplication uvsvApplication){
        boolean isSuccess = uvsvApplicationService.save(uvsvApplication);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvApplications 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvApplication/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvApplication> uvsvApplications){
        boolean isSuccess = uvsvApplicationService.saveBatch(uvsvApplications);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvApplication 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvApplication")
    public Result<Boolean> update(@RequestBody UvsvApplication uvsvApplication){
        boolean isSuccess = uvsvApplicationService.saveOrUpdate(uvsvApplication);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvApplication 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvApplication/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvApplication uvsvApplication){
        boolean isSuccess = uvsvApplicationService.updateById(uvsvApplication);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvApplication/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvApplicationService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvApplication/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvApplicationService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvApplication 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvApplication")
    public Result<Boolean> delete(@RequestBody UvsvApplication uvsvApplication){
        boolean isSuccess = uvsvApplicationService.remove(new QueryWrapper<>(uvsvApplication));
        return Result.SUCCESS(isSuccess);
    }

}