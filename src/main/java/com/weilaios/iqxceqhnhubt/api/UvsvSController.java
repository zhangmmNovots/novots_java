package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvS;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvSService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 一级目录 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvSController {

    @Autowired
    private UvsvSService uvsvSService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvS/{id}")
    public Result<UvsvS> getById(@PathVariable String id) {
        UvsvS uvsvS = uvsvSService.getById(id);
        return Result.SUCCESS(uvsvS);
    }


    /**
     * 通过 一级目录 属性字段查询单条数据详情
     *
     * @param uvsvS 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvS")
    public Result<UvsvS> getByEntity(@RequestBody UvsvS uvsvS) {
        List<UvsvS> uvsvSlist = uvsvSService.list(new QueryWrapper<>(uvsvS));
        if (ObjectUtils.isNotEmpty(uvsvSlist)){
            //多条数据则只返回第一条
            UvsvS uvsvSResult = uvsvSlist.get(0);
            return Result.SUCCESS(uvsvSResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 一级目录 属性字段查询数据集列表
     *
     * @param uvsvS 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvS/list")
    public Result<List<UvsvS>> list(@RequestBody UvsvS uvsvS) {
        List<UvsvS> uvsvSList = uvsvSService.list(new QueryWrapper<>(uvsvS));

        return Result.SUCCESS(uvsvSList);
    }


    /**
     * 通过 一级目录 属性字段 分页查询数据集列表
     *
     * @param uvsvS 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvS/list/page")
    public Result<Page<UvsvS>> listWithPage(@RequestBody UvsvS uvsvS, @RequestParam long current, @RequestParam long size) {
        Page<UvsvS> page = new Page<>(current, size);
        page = uvsvSService.page(page, new QueryWrapper<>(uvsvS));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvS 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvS")
    public Result<Boolean> insert(@RequestBody UvsvS uvsvS){
        boolean isSuccess = uvsvSService.save(uvsvS);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvSs 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvS/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvS> uvsvSs){
        boolean isSuccess = uvsvSService.saveBatch(uvsvSs);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvS 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvS")
    public Result<Boolean> update(@RequestBody UvsvS uvsvS){
        boolean isSuccess = uvsvSService.saveOrUpdate(uvsvS);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvS 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvS/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvS uvsvS){
        boolean isSuccess = uvsvSService.updateById(uvsvS);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvS/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvSService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvS/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvSService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvS 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvS")
    public Result<Boolean> delete(@RequestBody UvsvS uvsvS){
        boolean isSuccess = uvsvSService.remove(new QueryWrapper<>(uvsvS));
        return Result.SUCCESS(isSuccess);
    }

}