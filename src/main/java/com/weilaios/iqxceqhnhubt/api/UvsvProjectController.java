package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvProject;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 项目 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvProjectController {

    @Autowired
    private UvsvProjectService uvsvProjectService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvProject/{id}")
    public Result<UvsvProject> getById(@PathVariable String id) {
        UvsvProject uvsvProject = uvsvProjectService.getById(id);
        return Result.SUCCESS(uvsvProject);
    }


    /**
     * 通过 项目 属性字段查询单条数据详情
     *
     * @param uvsvProject 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvProject")
    public Result<UvsvProject> getByEntity(@RequestBody UvsvProject uvsvProject) {
        List<UvsvProject> uvsvProjectlist = uvsvProjectService.list(new QueryWrapper<>(uvsvProject));
        if (ObjectUtils.isNotEmpty(uvsvProjectlist)){
            //多条数据则只返回第一条
            UvsvProject uvsvProjectResult = uvsvProjectlist.get(0);
            return Result.SUCCESS(uvsvProjectResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 项目 属性字段查询数据集列表
     *
     * @param uvsvProject 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvProject/list")
    public Result<List<UvsvProject>> list(@RequestBody UvsvProject uvsvProject) {
        List<UvsvProject> uvsvProjectList = uvsvProjectService.list(new QueryWrapper<>(uvsvProject));

        return Result.SUCCESS(uvsvProjectList);
    }


    /**
     * 通过 项目 属性字段 分页查询数据集列表
     *
     * @param uvsvProject 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvProject/list/page")
    public Result<Page<UvsvProject>> listWithPage(@RequestBody UvsvProject uvsvProject, @RequestParam long current, @RequestParam long size) {
        Page<UvsvProject> page = new Page<>(current, size);
        page = uvsvProjectService.page(page, new QueryWrapper<>(uvsvProject));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvProject 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvProject")
    public Result<Boolean> insert(@RequestBody UvsvProject uvsvProject){
        boolean isSuccess = uvsvProjectService.save(uvsvProject);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvProjects 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvProject/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvProject> uvsvProjects){
        boolean isSuccess = uvsvProjectService.saveBatch(uvsvProjects);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvProject 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvProject")
    public Result<Boolean> update(@RequestBody UvsvProject uvsvProject){
        boolean isSuccess = uvsvProjectService.saveOrUpdate(uvsvProject);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvProject 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvProject/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvProject uvsvProject){
        boolean isSuccess = uvsvProjectService.updateById(uvsvProject);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvProject/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvProjectService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvProject/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvProjectService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvProject 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvProject")
    public Result<Boolean> delete(@RequestBody UvsvProject uvsvProject){
        boolean isSuccess = uvsvProjectService.remove(new QueryWrapper<>(uvsvProject));
        return Result.SUCCESS(isSuccess);
    }

}