package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvSEmployeeTest;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvSEmployeeTestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * s_employee_test 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvSEmployeeTestController {

    @Autowired
    private UvsvSEmployeeTestService uvsvSEmployeeTestService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvSEmployeeTest/{id}")
    public Result<UvsvSEmployeeTest> getById(@PathVariable String id) {
        UvsvSEmployeeTest uvsvSEmployeeTest = uvsvSEmployeeTestService.getById(id);
        return Result.SUCCESS(uvsvSEmployeeTest);
    }


    /**
     * 通过 s_employee_test 属性字段查询单条数据详情
     *
     * @param uvsvSEmployeeTest 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvSEmployeeTest")
    public Result<UvsvSEmployeeTest> getByEntity(@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest) {
        List<UvsvSEmployeeTest> uvsvSEmployeeTestlist = uvsvSEmployeeTestService.list(new QueryWrapper<>(uvsvSEmployeeTest));
        if (ObjectUtils.isNotEmpty(uvsvSEmployeeTestlist)){
            //多条数据则只返回第一条
            UvsvSEmployeeTest uvsvSEmployeeTestResult = uvsvSEmployeeTestlist.get(0);
            return Result.SUCCESS(uvsvSEmployeeTestResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 s_employee_test 属性字段查询数据集列表
     *
     * @param uvsvSEmployeeTest 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvSEmployeeTest/list")
    public Result<List<UvsvSEmployeeTest>> list(@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest) {
        List<UvsvSEmployeeTest> uvsvSEmployeeTestList = uvsvSEmployeeTestService.list(new QueryWrapper<>(uvsvSEmployeeTest));

        return Result.SUCCESS(uvsvSEmployeeTestList);
    }


    /**
     * 通过 s_employee_test 属性字段 分页查询数据集列表
     *
     * @param uvsvSEmployeeTest 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvSEmployeeTest/list/page")
    public Result<Page<UvsvSEmployeeTest>> listWithPage(@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest, @RequestParam long current, @RequestParam long size) {
        Page<UvsvSEmployeeTest> page = new Page<>(current, size);
        page = uvsvSEmployeeTestService.page(page, new QueryWrapper<>(uvsvSEmployeeTest));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvSEmployeeTest 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvSEmployeeTest")
    public Result<Boolean> insert(@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest){
        boolean isSuccess = uvsvSEmployeeTestService.save(uvsvSEmployeeTest);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvSEmployeeTests 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvSEmployeeTest/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvSEmployeeTest> uvsvSEmployeeTests){
        boolean isSuccess = uvsvSEmployeeTestService.saveBatch(uvsvSEmployeeTests);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvSEmployeeTest 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvSEmployeeTest")
    public Result<Boolean> update(@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest){
        boolean isSuccess = uvsvSEmployeeTestService.saveOrUpdate(uvsvSEmployeeTest);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvSEmployeeTest 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvSEmployeeTest/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest){
        boolean isSuccess = uvsvSEmployeeTestService.updateById(uvsvSEmployeeTest);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvSEmployeeTest/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvSEmployeeTestService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvSEmployeeTest/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvSEmployeeTestService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvSEmployeeTest 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvSEmployeeTest")
    public Result<Boolean> delete(@RequestBody UvsvSEmployeeTest uvsvSEmployeeTest){
        boolean isSuccess = uvsvSEmployeeTestService.remove(new QueryWrapper<>(uvsvSEmployeeTest));
        return Result.SUCCESS(isSuccess);
    }

}