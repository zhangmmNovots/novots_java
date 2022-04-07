package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvUserTable;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvUserTableService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 用户 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvUserTableController {

    @Autowired
    private UvsvUserTableService uvsvUserTableService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvUserTable/{id}")
    public Result<UvsvUserTable> getById(@PathVariable String id) {
        UvsvUserTable uvsvUserTable = uvsvUserTableService.getById(id);
        return Result.SUCCESS(uvsvUserTable);
    }


    /**
     * 通过 用户 属性字段查询单条数据详情
     *
     * @param uvsvUserTable 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvUserTable")
    public Result<UvsvUserTable> getByEntity(@RequestBody UvsvUserTable uvsvUserTable) {
        List<UvsvUserTable> uvsvUserTablelist = uvsvUserTableService.list(new QueryWrapper<>(uvsvUserTable));
        if (ObjectUtils.isNotEmpty(uvsvUserTablelist)){
            //多条数据则只返回第一条
            UvsvUserTable uvsvUserTableResult = uvsvUserTablelist.get(0);
            return Result.SUCCESS(uvsvUserTableResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 用户 属性字段查询数据集列表
     *
     * @param uvsvUserTable 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvUserTable/list")
    public Result<List<UvsvUserTable>> list(@RequestBody UvsvUserTable uvsvUserTable) {
        List<UvsvUserTable> uvsvUserTableList = uvsvUserTableService.list(new QueryWrapper<>(uvsvUserTable));

        return Result.SUCCESS(uvsvUserTableList);
    }


    /**
     * 通过 用户 属性字段 分页查询数据集列表
     *
     * @param uvsvUserTable 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvUserTable/list/page")
    public Result<Page<UvsvUserTable>> listWithPage(@RequestBody UvsvUserTable uvsvUserTable, @RequestParam long current, @RequestParam long size) {
        Page<UvsvUserTable> page = new Page<>(current, size);
        page = uvsvUserTableService.page(page, new QueryWrapper<>(uvsvUserTable));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvUserTable 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvUserTable")
    public Result<Boolean> insert(@RequestBody UvsvUserTable uvsvUserTable){
        boolean isSuccess = uvsvUserTableService.save(uvsvUserTable);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvUserTables 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvUserTable/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvUserTable> uvsvUserTables){
        boolean isSuccess = uvsvUserTableService.saveBatch(uvsvUserTables);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvUserTable 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvUserTable")
    public Result<Boolean> update(@RequestBody UvsvUserTable uvsvUserTable){
        boolean isSuccess = uvsvUserTableService.saveOrUpdate(uvsvUserTable);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvUserTable 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvUserTable/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvUserTable uvsvUserTable){
        boolean isSuccess = uvsvUserTableService.updateById(uvsvUserTable);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvUserTable/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvUserTableService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvUserTable/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvUserTableService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvUserTable 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvUserTable")
    public Result<Boolean> delete(@RequestBody UvsvUserTable uvsvUserTable){
        boolean isSuccess = uvsvUserTableService.remove(new QueryWrapper<>(uvsvUserTable));
        return Result.SUCCESS(isSuccess);
    }

}