package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvTse;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvTseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * s_employee 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvTseController {

    @Autowired
    private UvsvTseService uvsvTseService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvTse/{id}")
    public Result<UvsvTse> getById(@PathVariable String id) {
        UvsvTse uvsvTse = uvsvTseService.getById(id);
        return Result.SUCCESS(uvsvTse);
    }


    /**
     * 通过 s_employee 属性字段查询单条数据详情
     *
     * @param uvsvTse 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvTse")
    public Result<UvsvTse> getByEntity(@RequestBody UvsvTse uvsvTse) {
        List<UvsvTse> uvsvTselist = uvsvTseService.list(new QueryWrapper<>(uvsvTse));
        if (ObjectUtils.isNotEmpty(uvsvTselist)){
            //多条数据则只返回第一条
            UvsvTse uvsvTseResult = uvsvTselist.get(0);
            return Result.SUCCESS(uvsvTseResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 s_employee 属性字段查询数据集列表
     *
     * @param uvsvTse 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvTse/list")
    public Result<List<UvsvTse>> list(@RequestBody UvsvTse uvsvTse) {
        List<UvsvTse> uvsvTseList = uvsvTseService.list(new QueryWrapper<>(uvsvTse));

        return Result.SUCCESS(uvsvTseList);
    }


    /**
     * 通过 s_employee 属性字段 分页查询数据集列表
     *
     * @param uvsvTse 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvTse/list/page")
    public Result<Page<UvsvTse>> listWithPage(@RequestBody UvsvTse uvsvTse, @RequestParam long current, @RequestParam long size) {
        Page<UvsvTse> page = new Page<>(current, size);
        page = uvsvTseService.page(page, new QueryWrapper<>(uvsvTse));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvTse 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvTse")
    public Result<Boolean> insert(@RequestBody UvsvTse uvsvTse){
        boolean isSuccess = uvsvTseService.save(uvsvTse);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvTses 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvTse/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvTse> uvsvTses){
        boolean isSuccess = uvsvTseService.saveBatch(uvsvTses);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvTse 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvTse")
    public Result<Boolean> update(@RequestBody UvsvTse uvsvTse){
        boolean isSuccess = uvsvTseService.saveOrUpdate(uvsvTse);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvTse 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvTse/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvTse uvsvTse){
        boolean isSuccess = uvsvTseService.updateById(uvsvTse);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvTse/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvTseService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvTse/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvTseService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvTse 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvTse")
    public Result<Boolean> delete(@RequestBody UvsvTse uvsvTse){
        boolean isSuccess = uvsvTseService.remove(new QueryWrapper<>(uvsvTse));
        return Result.SUCCESS(isSuccess);
    }

}