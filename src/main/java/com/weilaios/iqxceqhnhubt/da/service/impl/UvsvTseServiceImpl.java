package com.weilaios.iqxceqhnhubt.da.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilaios.iqxceqhnhubt.da.mapper.UvsvTseMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvTse;
import com.weilaios.iqxceqhnhubt.da.service.UvsvTseService;
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
 * uvsvTse 服务实现类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Service
public class UvsvTseServiceImpl extends ServiceImpl<UvsvTseMapper, UvsvTse> implements UvsvTseService {

    @Autowired
    private UvsvTseMapper uvsvTseMapper;
    @Autowired
    private TransformFieldService transformFieldService;
    @Autowired
    private ProcessQueryHandlerService processQueryHandlerService;
    @Autowired
    private EntityFieldMapper entityFieldMapper;


                

	@Override
	public Result Activity_0856co3(Map<String,Object> paramMap) throws Exception{
		List<Map<String,Object>> resultList = uvsvTseMapper.Activity_0856co3(paramMap);
		EntityField entityField = new EntityField();
		entityField.setEntityEnName("uvsv_tse");
		List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
		List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
		HashMap<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(Constants.PAGINATION,null);
		resultMap.put(Constants.RESULT_DATA,coveredDataList);
		return Result.SUCCESS(resultMap);
	}

                

	@Override
	public Result Activity_1rxifnj(Map<String,Object> paramMap) throws Exception{
		List<Map<String, Object>> resutlList = new ArrayList<>();
		paramMap.put("uvsv_tse_id0",IdUtil.objectId());paramMap.put("middleId",IdUtil.objectId());
		Map<String, Object> transformData = transformFieldService.transformInsert(paramMap, ACTIVITY_1RXIFNJ_LIST,ACTIVITY_1RXIFNJ_MODEL_UUID);
		uvsvTseMapper.Activity_1rxifnj(transformData);
		
		ACTIVITY_1RXIFNJ_LIST.forEach(item->{
			Map<String, Object> resutlMap = new HashMap<>();
			item.forEach((key,value)->{
				resutlMap.put(key,paramMap.get(value));
			});
			resutlList.add(resutlMap);
		});
		return Result.SUCCESS(resutlList);
	}
	private static final String ACTIVITY_1RXIFNJ_MODEL_UUID = "uvsv_tse";

	/***
	 * Activity_1rxifnj use static constantsPool
	 */
	private static final HashMap<String, String> ACTIVITY_1RXIFNJ_MAP0 = new HashMap<String, String>() {{
		 put("entryTime", "paramColumn3");
		 put("telephone", "paramColumn2");
		 put("userName", "paramColumn1");
		 put("uvsv_tse_id","uvsv_tse_id0");
	}};

	/***
	 * Activity_1rxifnj use static constantsPool list
	 */
	private static final List<HashMap<String, String>> ACTIVITY_1RXIFNJ_LIST = new ArrayList<HashMap<String, String>>(){{
		add(ACTIVITY_1RXIFNJ_MAP0);
	}};
	

                

	@Override
	public Result Activity_0ljcgdd(Map<String,Object> paramMap) throws Exception{
		paramMap.put("middleId",IdUtil.objectId());
		uvsvTseMapper.Activity_0ljcgdd(paramMap);
		return Result.SUCCESS();
	}

                

	@Override
	public Result Activity_19afm91(Map<String,Object> paramMap) throws Exception{
		List<Map<String,Object>> resultList = uvsvTseMapper.Activity_19afm91(paramMap);
		EntityField entityField = new EntityField();
		entityField.setEntityEnName("uvsv_tse");
		List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
		List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
		HashMap<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(Constants.PAGINATION,null);
		resultMap.put(Constants.RESULT_DATA,coveredDataList);
		return Result.SUCCESS(resultMap);
	}



}
