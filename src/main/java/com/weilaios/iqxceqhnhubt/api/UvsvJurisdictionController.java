package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvJurisdiction;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvJurisdictionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 权限 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvJurisdictionController {

    @Autowired
    private UvsvJurisdictionService uvsvJurisdictionService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvJurisdiction/{id}")
    public Result<UvsvJurisdiction> getById(@PathVariable String id) {
        UvsvJurisdiction uvsvJurisdiction = uvsvJurisdictionService.getById(id);
        return Result.SUCCESS(uvsvJurisdiction);
    }


    /**
     * 通过 权限 属性字段查询单条数据详情
     *
     * @param uvsvJurisdiction 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvJurisdiction")
    public Result<UvsvJurisdiction> getByEntity(@RequestBody UvsvJurisdiction uvsvJurisdiction) {
        List<UvsvJurisdiction> uvsvJurisdictionlist = uvsvJurisdictionService.list(new QueryWrapper<>(uvsvJurisdiction));
        if (ObjectUtils.isNotEmpty(uvsvJurisdictionlist)){
            //多条数据则只返回第一条
            UvsvJurisdiction uvsvJurisdictionResult = uvsvJurisdictionlist.get(0);
            return Result.SUCCESS(uvsvJurisdictionResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 权限 属性字段查询数据集列表
     *
     * @param uvsvJurisdiction 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvJurisdiction/list")
    public Result<List<UvsvJurisdiction>> list(@RequestBody UvsvJurisdiction uvsvJurisdiction) {
        List<UvsvJurisdiction> uvsvJurisdictionList = uvsvJurisdictionService.list(new QueryWrapper<>(uvsvJurisdiction));

        return Result.SUCCESS(uvsvJurisdictionList);
    }


    /**
     * 通过 权限 属性字段 分页查询数据集列表
     *
     * @param uvsvJurisdiction 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvJurisdiction/list/page")
    public Result<Page<UvsvJurisdiction>> listWithPage(@RequestBody UvsvJurisdiction uvsvJurisdiction, @RequestParam long current, @RequestParam long size) {
        Page<UvsvJurisdiction> page = new Page<>(current, size);
        page = uvsvJurisdictionService.page(page, new QueryWrapper<>(uvsvJurisdiction));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvJurisdiction 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvJurisdiction")
    public Result<Boolean> insert(@RequestBody UvsvJurisdiction uvsvJurisdiction){
        boolean isSuccess = uvsvJurisdictionService.save(uvsvJurisdiction);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvJurisdictions 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvJurisdiction/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvJurisdiction> uvsvJurisdictions){
        boolean isSuccess = uvsvJurisdictionService.saveBatch(uvsvJurisdictions);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvJurisdiction 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvJurisdiction")
    public Result<Boolean> update(@RequestBody UvsvJurisdiction uvsvJurisdiction){
        boolean isSuccess = uvsvJurisdictionService.saveOrUpdate(uvsvJurisdiction);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvJurisdiction 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvJurisdiction/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvJurisdiction uvsvJurisdiction){
        boolean isSuccess = uvsvJurisdictionService.updateById(uvsvJurisdiction);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvJurisdiction/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvJurisdictionService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvJurisdiction/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvJurisdictionService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvJurisdiction 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvJurisdiction")
    public Result<Boolean> delete(@RequestBody UvsvJurisdiction uvsvJurisdiction){
        boolean isSuccess = uvsvJurisdictionService.remove(new QueryWrapper<>(uvsvJurisdiction));
        return Result.SUCCESS(isSuccess);
    }

}