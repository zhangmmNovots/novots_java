package com.weilaios.iqxceqhnhubt.da.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilaios.iqxceqhnhubt.da.mapper.UvsvSUserMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvSUser;
import com.weilaios.iqxceqhnhubt.da.service.UvsvSUserService;
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
 * uvsvSUser 服务实现类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Service
public class UvsvSUserServiceImpl extends ServiceImpl<UvsvSUserMapper, UvsvSUser> implements UvsvSUserService {

    @Autowired
    private UvsvSUserMapper uvsvSUserMapper;
    @Autowired
    private TransformFieldService transformFieldService;
    @Autowired
    private ProcessQueryHandlerService processQueryHandlerService;
    @Autowired
    private EntityFieldMapper entityFieldMapper;


                

	@Override
	public Result Activity_13wix3e(Map<String,Object> paramMap) throws Exception{
		List<Map<String,Object>> resultList = uvsvSUserMapper.Activity_13wix3e(paramMap);
		EntityField entityField = new EntityField();
		entityField.setEntityEnName("uvsv_s_user");
		List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
		List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
		HashMap<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(Constants.PAGINATION,null);
		resultMap.put(Constants.RESULT_DATA,coveredDataList);
		return Result.SUCCESS(resultMap);
	}

                

	@Override
	public Result Activity_1w6xhnt(Map<String,Object> paramMap) throws Exception{
		List<Map<String,Object>> resultList = uvsvSUserMapper.Activity_1w6xhnt(paramMap);
		EntityField entityField = new EntityField();
		entityField.setEntityEnName("uvsv_s_user");
		List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
		List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
		HashMap<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(Constants.PAGINATION,null);
		resultMap.put(Constants.RESULT_DATA,coveredDataList);
		return Result.SUCCESS(resultMap);
	}

    @Override
    public Result<Map<String, Object>> Activity_1f93dbi(Map<String, Object> paramMap) throws Exception {
        List<Map<String, Object>> resultList = uvsvSUserMapper.Activity_1f93dbi(paramMap);
        EntityField entityField = new EntityField();
        entityField.setEntityEnName("uvsv_s_user");
        List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
        List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
        List<Map<String, Object>>  finalResultList = processQueryHandlerService.queryProcessHandle(coveredDataList,ACTIVITY_1F93DBI_RELATION_TABLE_MAP,"uvsv_s_user_id");
        HashMap<String, Object> resultMap = new HashMap<>(3);
        resultMap.put(Constants.PAGINATION,null);
        resultMap.put(Constants.RESULT_DATA,finalResultList);
        return Result.SUCCESS(resultMap);

    }
                    

	/***
	 * Activity_1f93dbi use static constantsPool list
	 */
	private static final MultiValueMap<String, String> ACTIVITY_1F93DBI_RELATION_TABLE_MAP = new LinkedMultiValueMap<String, String>() {{
		add("j_","uvsv_bkfp");add("j_","uvsv_mzvr_id");
		add("q_","uvsv_mzvr");add("q_","uvsv_role_id");
	}};




}
