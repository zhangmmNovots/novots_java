package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvSUser;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvSUserService;
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
public class UvsvSUserController {

    @Autowired
    private UvsvSUserService uvsvSUserService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvSUser/{id}")
    public Result<UvsvSUser> getById(@PathVariable String id) {
        UvsvSUser uvsvSUser = uvsvSUserService.getById(id);
        return Result.SUCCESS(uvsvSUser);
    }


    /**
     * 通过 用户 属性字段查询单条数据详情
     *
     * @param uvsvSUser 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvSUser")
    public Result<UvsvSUser> getByEntity(@RequestBody UvsvSUser uvsvSUser) {
        List<UvsvSUser> uvsvSUserlist = uvsvSUserService.list(new QueryWrapper<>(uvsvSUser));
        if (ObjectUtils.isNotEmpty(uvsvSUserlist)){
            //多条数据则只返回第一条
            UvsvSUser uvsvSUserResult = uvsvSUserlist.get(0);
            return Result.SUCCESS(uvsvSUserResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 用户 属性字段查询数据集列表
     *
     * @param uvsvSUser 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvSUser/list")
    public Result<List<UvsvSUser>> list(@RequestBody UvsvSUser uvsvSUser) {
        List<UvsvSUser> uvsvSUserList = uvsvSUserService.list(new QueryWrapper<>(uvsvSUser));

        return Result.SUCCESS(uvsvSUserList);
    }


    /**
     * 通过 用户 属性字段 分页查询数据集列表
     *
     * @param uvsvSUser 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvSUser/list/page")
    public Result<Page<UvsvSUser>> listWithPage(@RequestBody UvsvSUser uvsvSUser, @RequestParam long current, @RequestParam long size) {
        Page<UvsvSUser> page = new Page<>(current, size);
        page = uvsvSUserService.page(page, new QueryWrapper<>(uvsvSUser));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvSUser 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvSUser")
    public Result<Boolean> insert(@RequestBody UvsvSUser uvsvSUser){
        boolean isSuccess = uvsvSUserService.save(uvsvSUser);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvSUsers 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvSUser/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvSUser> uvsvSUsers){
        boolean isSuccess = uvsvSUserService.saveBatch(uvsvSUsers);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvSUser 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvSUser")
    public Result<Boolean> update(@RequestBody UvsvSUser uvsvSUser){
        boolean isSuccess = uvsvSUserService.saveOrUpdate(uvsvSUser);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvSUser 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvSUser/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvSUser uvsvSUser){
        boolean isSuccess = uvsvSUserService.updateById(uvsvSUser);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvSUser/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvSUserService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvSUser/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvSUserService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvSUser 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvSUser")
    public Result<Boolean> delete(@RequestBody UvsvSUser uvsvSUser){
        boolean isSuccess = uvsvSUserService.remove(new QueryWrapper<>(uvsvSUser));
        return Result.SUCCESS(isSuccess);
    }

}