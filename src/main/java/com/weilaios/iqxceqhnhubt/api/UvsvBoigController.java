package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvBoig;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvBoigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 角色-权限(中间表) 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvBoigController {

    @Autowired
    private UvsvBoigService uvsvBoigService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvBoig/{id}")
    public Result<UvsvBoig> getById(@PathVariable String id) {
        UvsvBoig uvsvBoig = uvsvBoigService.getById(id);
        return Result.SUCCESS(uvsvBoig);
    }


    /**
     * 通过 角色-权限(中间表) 属性字段查询单条数据详情
     *
     * @param uvsvBoig 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvBoig")
    public Result<UvsvBoig> getByEntity(@RequestBody UvsvBoig uvsvBoig) {
        List<UvsvBoig> uvsvBoiglist = uvsvBoigService.list(new QueryWrapper<>(uvsvBoig));
        if (ObjectUtils.isNotEmpty(uvsvBoiglist)){
            //多条数据则只返回第一条
            UvsvBoig uvsvBoigResult = uvsvBoiglist.get(0);
            return Result.SUCCESS(uvsvBoigResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 角色-权限(中间表) 属性字段查询数据集列表
     *
     * @param uvsvBoig 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvBoig/list")
    public Result<List<UvsvBoig>> list(@RequestBody UvsvBoig uvsvBoig) {
        List<UvsvBoig> uvsvBoigList = uvsvBoigService.list(new QueryWrapper<>(uvsvBoig));

        return Result.SUCCESS(uvsvBoigList);
    }


    /**
     * 通过 角色-权限(中间表) 属性字段 分页查询数据集列表
     *
     * @param uvsvBoig 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvBoig/list/page")
    public Result<Page<UvsvBoig>> listWithPage(@RequestBody UvsvBoig uvsvBoig, @RequestParam long current, @RequestParam long size) {
        Page<UvsvBoig> page = new Page<>(current, size);
        page = uvsvBoigService.page(page, new QueryWrapper<>(uvsvBoig));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvBoig 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvBoig")
    public Result<Boolean> insert(@RequestBody UvsvBoig uvsvBoig){
        boolean isSuccess = uvsvBoigService.save(uvsvBoig);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvBoigs 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvBoig/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvBoig> uvsvBoigs){
        boolean isSuccess = uvsvBoigService.saveBatch(uvsvBoigs);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvBoig 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvBoig")
    public Result<Boolean> update(@RequestBody UvsvBoig uvsvBoig){
        boolean isSuccess = uvsvBoigService.saveOrUpdate(uvsvBoig);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvBoig 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvBoig/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvBoig uvsvBoig){
        boolean isSuccess = uvsvBoigService.updateById(uvsvBoig);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvBoig/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvBoigService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvBoig/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvBoigService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvBoig 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvBoig")
    public Result<Boolean> delete(@RequestBody UvsvBoig uvsvBoig){
        boolean isSuccess = uvsvBoigService.remove(new QueryWrapper<>(uvsvBoig));
        return Result.SUCCESS(isSuccess);
    }

}