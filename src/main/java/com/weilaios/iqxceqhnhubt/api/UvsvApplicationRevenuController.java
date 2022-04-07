package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvApplicationRevenu;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvApplicationRevenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 收入确认申请 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvApplicationRevenuController {

    @Autowired
    private UvsvApplicationRevenuService uvsvApplicationRevenuService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvApplicationRevenu/{id}")
    public Result<UvsvApplicationRevenu> getById(@PathVariable String id) {
        UvsvApplicationRevenu uvsvApplicationRevenu = uvsvApplicationRevenuService.getById(id);
        return Result.SUCCESS(uvsvApplicationRevenu);
    }


    /**
     * 通过 收入确认申请 属性字段查询单条数据详情
     *
     * @param uvsvApplicationRevenu 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvApplicationRevenu")
    public Result<UvsvApplicationRevenu> getByEntity(@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu) {
        List<UvsvApplicationRevenu> uvsvApplicationRevenulist = uvsvApplicationRevenuService.list(new QueryWrapper<>(uvsvApplicationRevenu));
        if (ObjectUtils.isNotEmpty(uvsvApplicationRevenulist)){
            //多条数据则只返回第一条
            UvsvApplicationRevenu uvsvApplicationRevenuResult = uvsvApplicationRevenulist.get(0);
            return Result.SUCCESS(uvsvApplicationRevenuResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 收入确认申请 属性字段查询数据集列表
     *
     * @param uvsvApplicationRevenu 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvApplicationRevenu/list")
    public Result<List<UvsvApplicationRevenu>> list(@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu) {
        List<UvsvApplicationRevenu> uvsvApplicationRevenuList = uvsvApplicationRevenuService.list(new QueryWrapper<>(uvsvApplicationRevenu));

        return Result.SUCCESS(uvsvApplicationRevenuList);
    }


    /**
     * 通过 收入确认申请 属性字段 分页查询数据集列表
     *
     * @param uvsvApplicationRevenu 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvApplicationRevenu/list/page")
    public Result<Page<UvsvApplicationRevenu>> listWithPage(@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu, @RequestParam long current, @RequestParam long size) {
        Page<UvsvApplicationRevenu> page = new Page<>(current, size);
        page = uvsvApplicationRevenuService.page(page, new QueryWrapper<>(uvsvApplicationRevenu));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvApplicationRevenu 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvApplicationRevenu")
    public Result<Boolean> insert(@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu){
        boolean isSuccess = uvsvApplicationRevenuService.save(uvsvApplicationRevenu);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvApplicationRevenus 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvApplicationRevenu/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvApplicationRevenu> uvsvApplicationRevenus){
        boolean isSuccess = uvsvApplicationRevenuService.saveBatch(uvsvApplicationRevenus);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvApplicationRevenu 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvApplicationRevenu")
    public Result<Boolean> update(@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu){
        boolean isSuccess = uvsvApplicationRevenuService.saveOrUpdate(uvsvApplicationRevenu);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvApplicationRevenu 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvApplicationRevenu/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu){
        boolean isSuccess = uvsvApplicationRevenuService.updateById(uvsvApplicationRevenu);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvApplicationRevenu/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvApplicationRevenuService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvApplicationRevenu/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvApplicationRevenuService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvApplicationRevenu 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvApplicationRevenu")
    public Result<Boolean> delete(@RequestBody UvsvApplicationRevenu uvsvApplicationRevenu){
        boolean isSuccess = uvsvApplicationRevenuService.remove(new QueryWrapper<>(uvsvApplicationRevenu));
        return Result.SUCCESS(isSuccess);
    }

}