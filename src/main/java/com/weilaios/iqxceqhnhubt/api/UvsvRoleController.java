package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvRole;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 角色 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvRoleController {

    @Autowired
    private UvsvRoleService uvsvRoleService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvRole/{id}")
    public Result<UvsvRole> getById(@PathVariable String id) {
        UvsvRole uvsvRole = uvsvRoleService.getById(id);
        return Result.SUCCESS(uvsvRole);
    }


    /**
     * 通过 角色 属性字段查询单条数据详情
     *
     * @param uvsvRole 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvRole")
    public Result<UvsvRole> getByEntity(@RequestBody UvsvRole uvsvRole) {
        List<UvsvRole> uvsvRolelist = uvsvRoleService.list(new QueryWrapper<>(uvsvRole));
        if (ObjectUtils.isNotEmpty(uvsvRolelist)){
            //多条数据则只返回第一条
            UvsvRole uvsvRoleResult = uvsvRolelist.get(0);
            return Result.SUCCESS(uvsvRoleResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 角色 属性字段查询数据集列表
     *
     * @param uvsvRole 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvRole/list")
    public Result<List<UvsvRole>> list(@RequestBody UvsvRole uvsvRole) {
        List<UvsvRole> uvsvRoleList = uvsvRoleService.list(new QueryWrapper<>(uvsvRole));

        return Result.SUCCESS(uvsvRoleList);
    }


    /**
     * 通过 角色 属性字段 分页查询数据集列表
     *
     * @param uvsvRole 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvRole/list/page")
    public Result<Page<UvsvRole>> listWithPage(@RequestBody UvsvRole uvsvRole, @RequestParam long current, @RequestParam long size) {
        Page<UvsvRole> page = new Page<>(current, size);
        page = uvsvRoleService.page(page, new QueryWrapper<>(uvsvRole));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvRole 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvRole")
    public Result<Boolean> insert(@RequestBody UvsvRole uvsvRole){
        boolean isSuccess = uvsvRoleService.save(uvsvRole);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvRoles 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvRole/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvRole> uvsvRoles){
        boolean isSuccess = uvsvRoleService.saveBatch(uvsvRoles);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvRole 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvRole")
    public Result<Boolean> update(@RequestBody UvsvRole uvsvRole){
        boolean isSuccess = uvsvRoleService.saveOrUpdate(uvsvRole);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvRole 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvRole/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvRole uvsvRole){
        boolean isSuccess = uvsvRoleService.updateById(uvsvRole);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvRole/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvRoleService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvRole/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvRoleService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvRole 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvRole")
    public Result<Boolean> delete(@RequestBody UvsvRole uvsvRole){
        boolean isSuccess = uvsvRoleService.remove(new QueryWrapper<>(uvsvRole));
        return Result.SUCCESS(isSuccess);
    }

}