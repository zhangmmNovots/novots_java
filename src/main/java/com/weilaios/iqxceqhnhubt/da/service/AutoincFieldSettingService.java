package com.weilaios.iqxceqhnhubt.da.service;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;

import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.weilaios.iqxceqhnhubt.model.EntityField;
import com.weilaios.iqxceqhnhubt.model.Entity;
import com.weilaios.iqxceqhnhubt.model.EntityRelation;
import com.weilaios.iqxceqhnhubt.model.OptionData;

import com.weilaios.iqxceqhnhubt.model.AutoincFieldSetting;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.weilaios.iqxceqhnhubt.da.mapper.EntityMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.EntityFieldMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.EntityRelationMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.OptionDataMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.AutoincFieldSettingMapper;
import com.weilaios.iqxceqhnhubt.utils.DataOperateConstants;



/**
 * (WlosAutoincFieldSetting)表服务实现类
 *
 * @author jyh
 * @since 2021-04-22 19:02:03
 */
@Service
public class AutoincFieldSettingService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private AutoincFieldSettingMapper wlosAutoincFieldSettingMapper;
    @Autowired
    public StringRedisTemplate redisService;

    public static final Map<String, String> GEN_DATE_PATTERN = new HashMap<String, String>() {
        {
            put("YYYY", "yyyy");
            put("YYYYMM", "yyyyMM");
            put("YYYYMMDD", "yyyyMMdd");
            put("MMDD", "MMdd");
        }
    };

  

    /**
     * 通过ID查询单条数据
     *
     * @param uuid 主键
     * @return 实例对象
     */
    public AutoincFieldSetting queryById(String uuid) {
        return this.wlosAutoincFieldSettingMapper.selectById(uuid);
    }


    /**
     * 新增数据
     *
     * @param wlosAutoincFieldSetting 实例对象
     * @return 实例对象
     */
    public AutoincFieldSetting insert(AutoincFieldSetting wlosAutoincFieldSetting) {
        this.wlosAutoincFieldSettingMapper.insert(wlosAutoincFieldSetting);
        return wlosAutoincFieldSetting;
    }

    /**
     * 通过主键删除数据
     *
     * @param uuid 主键
     * @return 是否成功
     */
    public boolean deleteById(String uuid) {
        return this.wlosAutoincFieldSettingMapper.deleteById(uuid) > 0;
    }


    /**
     * 新的自动编号生成逻辑
     * @return
     */
    public List<Map<String, List<String>>> genNumber(String entityUuid, List<Map<String, Object>> result) throws BusinessException {
        // 获取实体所有的自动编号字段
        AutoincFieldSetting autoincFieldSetting = new AutoincFieldSetting();
        autoincFieldSetting.setEntityUuid(entityUuid);
        List<AutoincFieldSetting> wlosAutoincFieldSettings = wlosAutoincFieldSettingMapper.selectList(new QueryWrapper<>(autoincFieldSetting));
        // 存储最终的结果
        List<Map<String, List<String>>> autoNumber = new ArrayList<>();
        if (wlosAutoincFieldSettings == null || wlosAutoincFieldSettings.size() == 0) {
            return autoNumber;
        }
        // 自动计数需要的个数
        Integer size = result.size();
        for (AutoincFieldSetting wlosAutoincFieldSetting : wlosAutoincFieldSettings) {
            // 存储单个自动编号字段的结果映射
            Map<String, List<String>> singleAutoResult = new HashMap<>();
            // 存储自动编号结果数组
            List<String> resultList = new ArrayList<>();
            String rule = (String) wlosAutoincFieldSetting.getRule();
            List<Map<String, Object>> ruleList = JSONObject.parseObject(rule, List.class);
            // 存储自动计数的数据
            List<String> autoList = new ArrayList<>();
            // 存储需要lookup查询的属性计数
            // {"relationKey": {"relationKeyValue1": [{},{}]}}
            Map<String, Map<String, List<Map>>> lookupDataMap = new HashMap<>();
            ruleList.forEach(ruleMap -> {
                String genType = (String) ruleMap.get("gen_type");
                switch (genType) {
                    case "auto":
                        String fieldUuidStartNum = redisService.opsForValue().get(wlosAutoincFieldSetting.getFieldUuid());
                        if (fieldUuidStartNum == null) {
                            fieldUuidStartNum = "0";
                        }
                        redisService.opsForValue().increment(wlosAutoincFieldSetting.getFieldUuid(), size);
                        for (int i = 1; i <= size; i++) {
                            String auto = String.format("%0" + wlosAutoincFieldSetting.getIncLength() + "d", (Integer.parseInt(fieldUuidStartNum) + i));
                            autoList.add(auto);
                        }
                        break;

                    default:
                        break;
                }
            });
            // 自动编号拼接
            for (int i=0;i<size;i++) {
                Map<String, Object> singleResult = result.get(i);
                List ll = new LinkedList();
                int finalI = i;
                ruleList.forEach(map -> {
                    String genType = (String) map.get("gen_type");
                    switch (genType) {
                        case "auto":
                            ll.add(autoList.get(finalI));
                            break;
                        case "string":
                            String pattenStr = (String) map.get("string");
                            ll.add(pattenStr);
                            break;
                        case "date":
                            String patten = (String) map.get("patten");
                            String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(GEN_DATE_PATTERN.get(patten)));
                            ll.add(nowTime);
                            break;
                        default:
                         break;
                    }
                });
                resultList.add(String.join("-", ll));
            }
            singleAutoResult.put(wlosAutoincFieldSetting.getEnName(), resultList);
            autoNumber.add(singleAutoResult);
        }
        return autoNumber;
    }
}