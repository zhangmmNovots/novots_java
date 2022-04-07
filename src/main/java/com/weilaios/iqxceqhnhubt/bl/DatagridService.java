package com.weilaios.iqxceqhnhubt.bl;

import com.alibaba.fastjson.JSONObject;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;

public interface DatagridService {

    /**
     * 数据表格保存a
     *
     * @param data
     * @return com.alibaba.fastjson.JSONObject
     * @author zjj
     * @date 2020/12/18 18:26
     */
    void datagridSave(String data) throws BusinessException;

    /**
     * 数据列表查询
     *
     * @param data
     * @return com.alibaba.fastjson.JSONObject
     * @author zjj
     * @date 2020/12/18 18:27
     */
    JSONObject datagridQuery(String data, String queryFlag) throws BusinessException;

    /**
     * 根据表名，主键id值查询数据 id-数据id  tableUuid--表id {"tableUuid":"","id":""}
     *
     * @param data
     * @return com.alibaba.fastjson.JSONObject
     * @author zjj
     * @date 2020/12/18 18:27
     */
    JSONObject datagridQueryById(String data) throws BusinessException;

    /**
     * 数据表格查询详情：根据约定的查询json来查询
     *
     * @param data
     * @return com.alibaba.fastjson.JSONObject
     * @author zjj
     * @date 2020/12/18 18:28
     */
    JSONObject datagridInfo(String data) throws BusinessException;

    /**
     * 数据表格修改
     *
     * @param data
     * @return void
     * @author zjj
     * @date 2020/12/18 18:28
     */
    void datagridUpdate(String data) throws BusinessException;

    /**
     * 数据表格删除
     *
     * @param data
     * @return void
     * @author zjj
     * @date 2020/12/18 18:28
     */
    void datagridDelete(String data) throws BusinessException;


}
