package com.weilaios.iqxceqhnhubt.da.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilaios.iqxceqhnhubt.da.mapper.UvsvApplicationRevenuMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvApplicationRevenu;
import com.weilaios.iqxceqhnhubt.da.service.UvsvApplicationRevenuService;
import com.weilaios.iqxceqhnhubt.da.mapper.EntityFieldMapper;
import com.weilaios.iqxceqhnhubt.da.service.TransformFieldService;
import com.weilaios.iqxceqhnhubt.da.service.ProcessQueryHandlerService;
import com.weilaios.iqxceqhnhubt.da.service.ProcessQueryHandlerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilaios.iqxceqhnhubt.utils.Result;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.ObjectUtils;
import com.weilaios.iqxceqhnhubt.model.*;

import java.util.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * uvsvApplicationRevenu 服务实现类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Service
public class UvsvApplicationRevenuServiceImpl extends ServiceImpl<UvsvApplicationRevenuMapper, UvsvApplicationRevenu> implements UvsvApplicationRevenuService {

    @Autowired
    private UvsvApplicationRevenuMapper uvsvApplicationRevenuMapper;
    @Autowired
    private TransformFieldService transformFieldService;
    @Autowired
    private ProcessQueryHandlerService processQueryHandlerService;
    @Autowired
    private EntityFieldMapper entityFieldMapper;


                

	@Override
	public Result Activity_0u8fygp(Map<String,Object> paramMap) throws Exception{
		paramMap.put("middleId",IdUtil.objectId());
		uvsvApplicationRevenuMapper.Activity_0u8fygp(paramMap);
		return Result.SUCCESS();
	}

                

	@Override
	public Result Activity_0f3s7ye(Map<String,Object> paramMap) throws Exception{
		List<Map<String,Object>> resultList = uvsvApplicationRevenuMapper.Activity_0f3s7ye(paramMap);
		EntityField entityField = new EntityField();
		entityField.setEntityEnName("uvsv_application_revenu");
		List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
		List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
		HashMap<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(Constants.PAGINATION,null);
		resultMap.put(Constants.RESULT_DATA,coveredDataList);
		return Result.SUCCESS(resultMap);
	}

    @Override
    public Result<Map<String, Object>> Activity_0f2xovj(Map<String, Object> paramMap) throws Exception {
        List<Map<String, Object>> resultList = uvsvApplicationRevenuMapper.Activity_0f2xovj(paramMap);
        EntityField entityField = new EntityField();
        entityField.setEntityEnName("uvsv_application_revenu");
        List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
        List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
        List<Map<String, Object>>  finalResultList = processQueryHandlerService.queryProcessHandle(coveredDataList,ACTIVITY_0F2XOVJ_RELATION_TABLE_MAP,"uvsv_application_revenu_id");
        HashMap<String, Object> resultMap = new HashMap<>(3);
        resultMap.put(Constants.PAGINATION,null);
        resultMap.put(Constants.RESULT_DATA,finalResultList);
        return Result.SUCCESS(resultMap);

    }
                    

	/***
	 * Activity_0f2xovj use static constantsPool list
	 */
	private static final MultiValueMap<String, String> ACTIVITY_0F2XOVJ_RELATION_TABLE_MAP = new LinkedMultiValueMap<String, String>() {{
		add("u_","uvsv_dzsp");add("u_","uvsv_tse_id");
		add("n_","uvsv_psey");add("n_","uvsv_project_id");
	}};




}
