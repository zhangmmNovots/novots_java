package com.weilaios.iqxceqhnhubt.da.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilaios.iqxceqhnhubt.da.mapper.UvsvSMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvS;
import com.weilaios.iqxceqhnhubt.da.service.UvsvSService;
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
 * uvsvS 服务实现类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Service
public class UvsvSServiceImpl extends ServiceImpl<UvsvSMapper, UvsvS> implements UvsvSService {

    @Autowired
    private UvsvSMapper uvsvSMapper;
    @Autowired
    private TransformFieldService transformFieldService;
    @Autowired
    private ProcessQueryHandlerService processQueryHandlerService;
    @Autowired
    private EntityFieldMapper entityFieldMapper;


                

	@Override
	public Result Activity_0cc1d0p(Map<String,Object> paramMap) throws Exception{
		List<Map<String,Object>> resultList = uvsvSMapper.Activity_0cc1d0p(paramMap);
		EntityField entityField = new EntityField();
		entityField.setEntityEnName("uvsv_s_");
		List<EntityField> entityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
		List<Map<String, Object>>  coveredDataList = processQueryHandlerService.coverDataToNewData(resultList,entityFields);
		HashMap<String, Object> resultMap = new HashMap<>(3);
		resultMap.put(Constants.PAGINATION,null);
		resultMap.put(Constants.RESULT_DATA,coveredDataList);
		return Result.SUCCESS(resultMap);
	}



}
