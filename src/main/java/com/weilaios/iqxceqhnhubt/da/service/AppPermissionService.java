package com.weilaios.iqxceqhnhubt.da.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilaios.iqxceqhnhubt.da.mapper.AppPermissionMapper;
import com.weilaios.iqxceqhnhubt.model.AppPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (AppPermission)表服务实现类
 *
 * @author jyh
 * @since 2022-03-02 15:26:12
 */
@Service
public class AppPermissionService{

    @Resource
    private AppPermissionMapper appPermissionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public AppPermission queryById(String id) {
        return this.appPermissionMapper.selectById(id);
    }
    
    /**
     * 通过对象查询单条数据
     *
     * @param  appPermission 实例对象
     * @return 实例对象
     */
    public AppPermission queryByEntity(AppPermission appPermission) {
        return this.appPermissionMapper.selectOne(new QueryWrapper<>(appPermission));
    }
  
    /**
     * 查询列表
     *
     * @param appPermission 实例对象
     * @return 对象列表
     */
    public List<AppPermission> queryAll(AppPermission appPermission) {
         return this.appPermissionMapper.selectList(new QueryWrapper<>(appPermission));
    }

    /**
     * 新增数据
     *
     * @param appPermission 实例对象
     * @return 实例对象
     */
    public AppPermission insert(AppPermission appPermission) {
        LambdaQueryWrapper<AppPermission> appPermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        appPermissionLambdaQueryWrapper.eq(AppPermission::getBindingId, appPermission.getBindingId());
        appPermissionMapper.delete(appPermissionLambdaQueryWrapper);
        appPermission.setId(IdUtil.objectId());
        appPermission.setCreatedTime(new Date());
        this.appPermissionMapper.insert(appPermission);
        return appPermission;
    }

    /**
     * 修改数据
     *
     * @param appPermission 实例对象
     * @return 实例对象
     */
    public AppPermission update(AppPermission appPermission) {
        this.appPermissionMapper.updateById(appPermission);
        return this.queryById(appPermission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id) {
        return this.appPermissionMapper.deleteById(id) > 0;
    }
    
    /**
     * 通过id batch删除数据
     *
     * @param list 主键集
     * @return 影响行数
     */
    public int deleteByIds(List<String> list){
        return this.appPermissionMapper.deleteBatchIds(list);
    }

}
