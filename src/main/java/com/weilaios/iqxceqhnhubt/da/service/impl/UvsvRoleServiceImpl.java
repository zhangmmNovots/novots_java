package com.weilaios.iqxceqhnhubt.da.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilaios.iqxceqhnhubt.da.mapper.UvsvRoleMapper;
import com.weilaios.iqxceqhnhubt.model.UvsvRole;
import com.weilaios.iqxceqhnhubt.da.service.UvsvRoleService;
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
 * 角色 服务实现类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Service
public class UvsvRoleServiceImpl extends ServiceImpl<UvsvRoleMapper, UvsvRole> implements UvsvRoleService {

    @Autowired
    private UvsvRoleMapper uvsvRoleMapper;
    @Autowired
    private TransformFieldService transformFieldService;
    @Autowired
    private ProcessQueryHandlerService processQueryHandlerService;
    @Autowired
    private EntityFieldMapper entityFieldMapper;




}
