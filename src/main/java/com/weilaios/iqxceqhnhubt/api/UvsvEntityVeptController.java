package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvEntityVept;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvEntityVeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 实体_2vept 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvEntityVeptController {

    @Autowired
    private UvsvEntityVeptService uvsvEntityVeptService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvEntityVept/{id}")
    public Result<UvsvEntityVept> getById(@PathVariable String id) {
        UvsvEntityVept uvsvEntityVept = uvsvEntityVeptService.getById(id);
        return Result.SUCCESS(uvsvEntityVept);
    }


    /**
     * 通过 实体_2vept 属性字段查询单条数据详情
     *
     * @param uvsvEntityVept 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvEntityVept")
    public Result<UvsvEntityVept> getByEntity(@RequestBody UvsvEntityVept uvsvEntityVept) {
        List<UvsvEntityVept> uvsvEntityVeptlist = uvsvEntityVeptService.list(new QueryWrapper<>(uvsvEntityVept));
        if (ObjectUtils.isNotEmpty(uvsvEntityVeptlist)){
            //多条数据则只返回第一条
            UvsvEntityVept uvsvEntityVeptResult = uvsvEntityVeptlist.get(0);
            return Result.SUCCESS(uvsvEntityVeptResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 实体_2vept 属性字段查询数据集列表
     *
     * @param uvsvEntityVept 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvEntityVept/list")
    public Result<List<UvsvEntityVept>> list(@RequestBody UvsvEntityVept uvsvEntityVept) {
        List<UvsvEntityVept> uvsvEntityVeptList = uvsvEntityVeptService.list(new QueryWrapper<>(uvsvEntityVept));

        return Result.SUCCESS(uvsvEntityVeptList);
    }


    /**
     * 通过 实体_2vept 属性字段 分页查询数据集列表
     *
     * @param uvsvEntityVept 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvEntityVept/list/page")
    public Result<Page<UvsvEntityVept>> listWithPage(@RequestBody UvsvEntityVept uvsvEntityVept, @RequestParam long current, @RequestParam long size) {
        Page<UvsvEntityVept> page = new Page<>(current, size);
        page = uvsvEntityVeptService.page(page, new QueryWrapper<>(uvsvEntityVept));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvEntityVept 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvEntityVept")
    public Result<Boolean> insert(@RequestBody UvsvEntityVept uvsvEntityVept){
        boolean isSuccess = uvsvEntityVeptService.save(uvsvEntityVept);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvEntityVepts 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvEntityVept/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvEntityVept> uvsvEntityVepts){
        boolean isSuccess = uvsvEntityVeptService.saveBatch(uvsvEntityVepts);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvEntityVept 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvEntityVept")
    public Result<Boolean> update(@RequestBody UvsvEntityVept uvsvEntityVept){
        boolean isSuccess = uvsvEntityVeptService.saveOrUpdate(uvsvEntityVept);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvEntityVept 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvEntityVept/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvEntityVept uvsvEntityVept){
        boolean isSuccess = uvsvEntityVeptService.updateById(uvsvEntityVept);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvEntityVept/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvEntityVeptService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvEntityVept/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvEntityVeptService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvEntityVept 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvEntityVept")
    public Result<Boolean> delete(@RequestBody UvsvEntityVept uvsvEntityVept){
        boolean isSuccess = uvsvEntityVeptService.remove(new QueryWrapper<>(uvsvEntityVept));
        return Result.SUCCESS(isSuccess);
    }

}