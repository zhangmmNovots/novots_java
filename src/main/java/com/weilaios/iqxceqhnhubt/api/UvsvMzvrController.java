package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvMzvr;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvMzvrService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 用户-角色(中间表) 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvMzvrController {

    @Autowired
    private UvsvMzvrService uvsvMzvrService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvMzvr/{id}")
    public Result<UvsvMzvr> getById(@PathVariable String id) {
        UvsvMzvr uvsvMzvr = uvsvMzvrService.getById(id);
        return Result.SUCCESS(uvsvMzvr);
    }


    /**
     * 通过 用户-角色(中间表) 属性字段查询单条数据详情
     *
     * @param uvsvMzvr 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvMzvr")
    public Result<UvsvMzvr> getByEntity(@RequestBody UvsvMzvr uvsvMzvr) {
        List<UvsvMzvr> uvsvMzvrlist = uvsvMzvrService.list(new QueryWrapper<>(uvsvMzvr));
        if (ObjectUtils.isNotEmpty(uvsvMzvrlist)){
            //多条数据则只返回第一条
            UvsvMzvr uvsvMzvrResult = uvsvMzvrlist.get(0);
            return Result.SUCCESS(uvsvMzvrResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 用户-角色(中间表) 属性字段查询数据集列表
     *
     * @param uvsvMzvr 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvMzvr/list")
    public Result<List<UvsvMzvr>> list(@RequestBody UvsvMzvr uvsvMzvr) {
        List<UvsvMzvr> uvsvMzvrList = uvsvMzvrService.list(new QueryWrapper<>(uvsvMzvr));

        return Result.SUCCESS(uvsvMzvrList);
    }


    /**
     * 通过 用户-角色(中间表) 属性字段 分页查询数据集列表
     *
     * @param uvsvMzvr 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvMzvr/list/page")
    public Result<Page<UvsvMzvr>> listWithPage(@RequestBody UvsvMzvr uvsvMzvr, @RequestParam long current, @RequestParam long size) {
        Page<UvsvMzvr> page = new Page<>(current, size);
        page = uvsvMzvrService.page(page, new QueryWrapper<>(uvsvMzvr));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvMzvr 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvMzvr")
    public Result<Boolean> insert(@RequestBody UvsvMzvr uvsvMzvr){
        boolean isSuccess = uvsvMzvrService.save(uvsvMzvr);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvMzvrs 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvMzvr/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvMzvr> uvsvMzvrs){
        boolean isSuccess = uvsvMzvrService.saveBatch(uvsvMzvrs);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvMzvr 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvMzvr")
    public Result<Boolean> update(@RequestBody UvsvMzvr uvsvMzvr){
        boolean isSuccess = uvsvMzvrService.saveOrUpdate(uvsvMzvr);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvMzvr 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvMzvr/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvMzvr uvsvMzvr){
        boolean isSuccess = uvsvMzvrService.updateById(uvsvMzvr);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvMzvr/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvMzvrService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvMzvr/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvMzvrService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvMzvr 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvMzvr")
    public Result<Boolean> delete(@RequestBody UvsvMzvr uvsvMzvr){
        boolean isSuccess = uvsvMzvrService.remove(new QueryWrapper<>(uvsvMzvr));
        return Result.SUCCESS(isSuccess);
    }

}