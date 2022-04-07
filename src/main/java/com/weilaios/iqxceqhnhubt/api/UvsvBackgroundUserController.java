package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvBackgroundUser;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvBackgroundUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 后台用户 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvBackgroundUserController {

    @Autowired
    private UvsvBackgroundUserService uvsvBackgroundUserService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvBackgroundUser/{id}")
    public Result<UvsvBackgroundUser> getById(@PathVariable String id) {
        UvsvBackgroundUser uvsvBackgroundUser = uvsvBackgroundUserService.getById(id);
        return Result.SUCCESS(uvsvBackgroundUser);
    }


    /**
     * 通过 后台用户 属性字段查询单条数据详情
     *
     * @param uvsvBackgroundUser 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvBackgroundUser")
    public Result<UvsvBackgroundUser> getByEntity(@RequestBody UvsvBackgroundUser uvsvBackgroundUser) {
        List<UvsvBackgroundUser> uvsvBackgroundUserlist = uvsvBackgroundUserService.list(new QueryWrapper<>(uvsvBackgroundUser));
        if (ObjectUtils.isNotEmpty(uvsvBackgroundUserlist)){
            //多条数据则只返回第一条
            UvsvBackgroundUser uvsvBackgroundUserResult = uvsvBackgroundUserlist.get(0);
            return Result.SUCCESS(uvsvBackgroundUserResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 后台用户 属性字段查询数据集列表
     *
     * @param uvsvBackgroundUser 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvBackgroundUser/list")
    public Result<List<UvsvBackgroundUser>> list(@RequestBody UvsvBackgroundUser uvsvBackgroundUser) {
        List<UvsvBackgroundUser> uvsvBackgroundUserList = uvsvBackgroundUserService.list(new QueryWrapper<>(uvsvBackgroundUser));

        return Result.SUCCESS(uvsvBackgroundUserList);
    }


    /**
     * 通过 后台用户 属性字段 分页查询数据集列表
     *
     * @param uvsvBackgroundUser 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvBackgroundUser/list/page")
    public Result<Page<UvsvBackgroundUser>> listWithPage(@RequestBody UvsvBackgroundUser uvsvBackgroundUser, @RequestParam long current, @RequestParam long size) {
        Page<UvsvBackgroundUser> page = new Page<>(current, size);
        page = uvsvBackgroundUserService.page(page, new QueryWrapper<>(uvsvBackgroundUser));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvBackgroundUser 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvBackgroundUser")
    public Result<Boolean> insert(@RequestBody UvsvBackgroundUser uvsvBackgroundUser){
        boolean isSuccess = uvsvBackgroundUserService.save(uvsvBackgroundUser);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvBackgroundUsers 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvBackgroundUser/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvBackgroundUser> uvsvBackgroundUsers){
        boolean isSuccess = uvsvBackgroundUserService.saveBatch(uvsvBackgroundUsers);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvBackgroundUser 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvBackgroundUser")
    public Result<Boolean> update(@RequestBody UvsvBackgroundUser uvsvBackgroundUser){
        boolean isSuccess = uvsvBackgroundUserService.saveOrUpdate(uvsvBackgroundUser);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvBackgroundUser 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvBackgroundUser/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvBackgroundUser uvsvBackgroundUser){
        boolean isSuccess = uvsvBackgroundUserService.updateById(uvsvBackgroundUser);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvBackgroundUser/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvBackgroundUserService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvBackgroundUser/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvBackgroundUserService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvBackgroundUser 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvBackgroundUser")
    public Result<Boolean> delete(@RequestBody UvsvBackgroundUser uvsvBackgroundUser){
        boolean isSuccess = uvsvBackgroundUserService.remove(new QueryWrapper<>(uvsvBackgroundUser));
        return Result.SUCCESS(isSuccess);
    }

}