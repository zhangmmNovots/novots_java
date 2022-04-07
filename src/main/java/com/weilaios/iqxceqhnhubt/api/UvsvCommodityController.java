package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvCommodity;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvCommodityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 商品 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvCommodityController {

    @Autowired
    private UvsvCommodityService uvsvCommodityService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvCommodity/{id}")
    public Result<UvsvCommodity> getById(@PathVariable String id) {
        UvsvCommodity uvsvCommodity = uvsvCommodityService.getById(id);
        return Result.SUCCESS(uvsvCommodity);
    }


    /**
     * 通过 商品 属性字段查询单条数据详情
     *
     * @param uvsvCommodity 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvCommodity")
    public Result<UvsvCommodity> getByEntity(@RequestBody UvsvCommodity uvsvCommodity) {
        List<UvsvCommodity> uvsvCommoditylist = uvsvCommodityService.list(new QueryWrapper<>(uvsvCommodity));
        if (ObjectUtils.isNotEmpty(uvsvCommoditylist)){
            //多条数据则只返回第一条
            UvsvCommodity uvsvCommodityResult = uvsvCommoditylist.get(0);
            return Result.SUCCESS(uvsvCommodityResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 商品 属性字段查询数据集列表
     *
     * @param uvsvCommodity 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvCommodity/list")
    public Result<List<UvsvCommodity>> list(@RequestBody UvsvCommodity uvsvCommodity) {
        List<UvsvCommodity> uvsvCommodityList = uvsvCommodityService.list(new QueryWrapper<>(uvsvCommodity));

        return Result.SUCCESS(uvsvCommodityList);
    }


    /**
     * 通过 商品 属性字段 分页查询数据集列表
     *
     * @param uvsvCommodity 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvCommodity/list/page")
    public Result<Page<UvsvCommodity>> listWithPage(@RequestBody UvsvCommodity uvsvCommodity, @RequestParam long current, @RequestParam long size) {
        Page<UvsvCommodity> page = new Page<>(current, size);
        page = uvsvCommodityService.page(page, new QueryWrapper<>(uvsvCommodity));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvCommodity 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvCommodity")
    public Result<Boolean> insert(@RequestBody UvsvCommodity uvsvCommodity){
        boolean isSuccess = uvsvCommodityService.save(uvsvCommodity);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvCommoditys 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvCommodity/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvCommodity> uvsvCommoditys){
        boolean isSuccess = uvsvCommodityService.saveBatch(uvsvCommoditys);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvCommodity 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvCommodity")
    public Result<Boolean> update(@RequestBody UvsvCommodity uvsvCommodity){
        boolean isSuccess = uvsvCommodityService.saveOrUpdate(uvsvCommodity);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvCommodity 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvCommodity/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvCommodity uvsvCommodity){
        boolean isSuccess = uvsvCommodityService.updateById(uvsvCommodity);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvCommodity/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvCommodityService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvCommodity/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvCommodityService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvCommodity 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvCommodity")
    public Result<Boolean> delete(@RequestBody UvsvCommodity uvsvCommodity){
        boolean isSuccess = uvsvCommodityService.remove(new QueryWrapper<>(uvsvCommodity));
        return Result.SUCCESS(isSuccess);
    }

}