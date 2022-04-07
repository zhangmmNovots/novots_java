package com.weilaios.iqxceqhnhubt.api;


import com.weilaios.iqxceqhnhubt.model.UvsvContract;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import com.weilaios.iqxceqhnhubt.da.service.UvsvContractService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.weilaios.iqxceqhnhubt.utils.Result;
import java.util.*;


/**
 * 合同 接口列表
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:39 PM
 * @since v0.1
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class UvsvContractController {

    @Autowired
    private UvsvContractService uvsvContractService;


    /**
     * 通过id查询单条数据
     *
     * @param id 主键
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvContract/{id}")
    public Result<UvsvContract> getById(@PathVariable String id) {
        UvsvContract uvsvContract = uvsvContractService.getById(id);
        return Result.SUCCESS(uvsvContract);
    }


    /**
     * 通过 合同 属性字段查询单条数据详情
     *
     * @param uvsvContract 数据对象
     * @return 单条数据结果
     */
    @GetMapping("/v1/uvsvContract")
    public Result<UvsvContract> getByEntity(@RequestBody UvsvContract uvsvContract) {
        List<UvsvContract> uvsvContractlist = uvsvContractService.list(new QueryWrapper<>(uvsvContract));
        if (ObjectUtils.isNotEmpty(uvsvContractlist)){
            //多条数据则只返回第一条
            UvsvContract uvsvContractResult = uvsvContractlist.get(0);
            return Result.SUCCESS(uvsvContractResult);
        }
        return Result.SUCCESS();
    }


    /**
     * 通过 合同 属性字段查询数据集列表
     *
     * @param uvsvContract 数据对象
     * @return 数据集合
     */
    @GetMapping("/v1/uvsvContract/list")
    public Result<List<UvsvContract>> list(@RequestBody UvsvContract uvsvContract) {
        List<UvsvContract> uvsvContractList = uvsvContractService.list(new QueryWrapper<>(uvsvContract));

        return Result.SUCCESS(uvsvContractList);
    }


    /**
     * 通过 合同 属性字段 分页查询数据集列表
     *
     * @param uvsvContract 数据对象
     * @param current 当前页
     * @param size 数量
     * @return 带分页信息数据集合
     */
    @GetMapping("/v1/uvsvContract/list/page")
    public Result<Page<UvsvContract>> listWithPage(@RequestBody UvsvContract uvsvContract, @RequestParam long current, @RequestParam long size) {
        Page<UvsvContract> page = new Page<>(current, size);
        page = uvsvContractService.page(page, new QueryWrapper<>(uvsvContract));
        return Result.SUCCESS(page);
    }


    /**
     * 新增数据
     *
     * @param uvsvContract 数据对象
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvContract")
    public Result<Boolean> insert(@RequestBody UvsvContract uvsvContract){
        boolean isSuccess = uvsvContractService.save(uvsvContract);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 批量新增数据
     *
     * @param uvsvContracts 数据对象集
     * @return 新增结果
     */
    @PostMapping("/v1/uvsvContract/batch")
    public Result<Boolean> insert(@RequestBody List<UvsvContract> uvsvContracts){
        boolean isSuccess = uvsvContractService.saveBatch(uvsvContracts);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 保存或修改数据，根据传入对象数据全量更新
     *
     * @param uvsvContract 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvContract")
    public Result<Boolean> update(@RequestBody UvsvContract uvsvContract){
        boolean isSuccess = uvsvContractService.saveOrUpdate(uvsvContract);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据id修改数据
     *
     * @param id 主键id
     * @param uvsvContract 数据对象
     * @return 修改结果
     */
    @PutMapping("/v1/uvsvContract/{id}")
    public Result<Boolean> update(@PathVariable String id,@RequestBody UvsvContract uvsvContract){
        boolean isSuccess = uvsvContractService.updateById(uvsvContract);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除单条数据
     *
     * @param id 主键id
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvContract/{id}")
    public Result<Boolean> deleteOne(@PathVariable String id){
        boolean isSuccess = uvsvContractService.removeById(id);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 删除多条数据
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvContract/batch")
    public Result<Boolean> deleteBatch(@RequestBody List<String> ids){
        boolean isSuccess = uvsvContractService.removeByIds(ids);
        return Result.SUCCESS(isSuccess);
    }


    /**
     * 根据对象内属性删除数据
     *
     * @param uvsvContract 数据对象
     * @return 删除结果
     */
    @DeleteMapping("/v1/uvsvContract")
    public Result<Boolean> delete(@RequestBody UvsvContract uvsvContract){
        boolean isSuccess = uvsvContractService.remove(new QueryWrapper<>(uvsvContract));
        return Result.SUCCESS(isSuccess);
    }

}