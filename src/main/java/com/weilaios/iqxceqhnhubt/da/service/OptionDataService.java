package com.weilaios.iqxceqhnhubt.da.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.weilaios.iqxceqhnhubt.model.EntityField;
import com.weilaios.iqxceqhnhubt.model.Entity;
import com.weilaios.iqxceqhnhubt.model.EntityRelation;
import com.weilaios.iqxceqhnhubt.model.OptionData;


import com.weilaios.iqxceqhnhubt.da.mapper.EntityMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.EntityFieldMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.EntityRelationMapper;
import com.weilaios.iqxceqhnhubt.da.mapper.OptionDataMapper;
import com.weilaios.iqxceqhnhubt.utils.DataOperateConstants;


import javax.annotation.Resource;
import java.util.*;

/**
 * (WlosOptionSet)表服务实现类
 *
 * @author jyh
 * @since 2021-04-15 16:11:41
 */
@Service
public class OptionDataService {

    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private OptionDataMapper optionDataMapper;

    @Resource
    private EntityMapper entityMapper;
    @Resource
    private EntityFieldMapper entityFieldMapper;

    @Resource
    private EntityRelationMapper entityRelationEntityMapper;



    /**
     * 通过ID查询单条数据
     *
     * @param uuid 主键
     * @return 实例对象
     */
    public OptionData queryById(String uuid) {
        OptionData optionData = optionDataMapper.selectById(uuid);
        return optionData;
    }




    /**
     * 查询列表
     *
     * @param optionData 实例对象
     * @return 对象列表
     * @see
     */
    public List<OptionData> queryAll(OptionData optionData,String type) {
        // pagehelper 分页
        // 1. 设置起始页,页数大小
        // 2. PageHelper.startPage(pageInfo.getPageNo(), pageInfo.getPageSize()) 此行代码后紧跟查询语句为分页查询   中间不能有其他代码,注意
        String optionUuid = optionData.getOptionUuid();
        Assert.hasLength(optionUuid, "选项集id不能为空");
        List<OptionData> wlosOptionData1 = optionDataMapper.selectList(new QueryWrapper<>(optionData));
        Entity entityQuery = new Entity();
        entityQuery.setEnName(optionUuid);
        Entity entity = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
        if (ObjectUtils.isEmpty(entity) || ObjectUtils.isEmpty(wlosOptionData1)) {
            return null;
        }

        EntityField entityField = new EntityField();
        entityField.setEntityEnName(optionUuid);
        entityField.setType("Lookup");
        List<EntityField> wlosEntityFields = entityFieldMapper.selectList(new QueryWrapper<>(entityField));
        if (ObjectUtils.isNotEmpty(wlosEntityFields)) {
            String displayField = entity.getDisplayField();
            for (OptionData optionData2 : wlosOptionData1) {
                String uuid = optionData2.getUuid();
                optionData2.setDisplayField(displayField);
                String data = optionData2.getData();
                Map<String, Object> map = JSONObject.parseObject(data, Map.class);
                for (EntityField entityField1 : wlosEntityFields) {
                    Map<String, Object> lookupMap = new HashMap<>(3);
                    String enName = entityField1.getEnName();
                    Object lookupValue = map.get(enName);
                    EntityRelation entityRelationQuery = new EntityRelation();
                    entityRelationQuery.setRelationKey(enName);
                    EntityRelation entityRelation = entityRelationEntityMapper.selectOne(new QueryWrapper<>(entityRelationQuery));
                    String leftEntityUuid = entityRelation.getLeftEntityUuid();
                    String rightEntityUuid = entityRelation.getRightEntityUuid();
                    String type1 = entityRelation.getType();
                    String relationEntityEnName = leftEntityUuid;
                    if (Objects.equals(leftEntityUuid, optionUuid)) {
                        relationEntityEnName = entityRelation.getRightEntityUuid();
                    }

                    EntityField wlosEntityField1 = new EntityField();
                    wlosEntityField1.setEntityEnName(relationEntityEnName);
                    List<EntityField> wlosEntityFields1 = entityFieldMapper.selectList(new QueryWrapper<>(wlosEntityField1));
                    lookupMap.put("columns", wlosEntityFields1);
                    lookupMap.put("data", Collections.emptyList());
                    lookupMap.put("relationUuid", Collections.emptyList());
                    switch (type1) {
                        case DataOperateConstants.ONE_TO_ONE:
                        case DataOperateConstants.ONE_TO_MANY:
                            if (leftEntityUuid.equals(optionUuid)) {
                                List<OptionData> wlosOptionDataRelations = new ArrayList<>();
                                List<String> wlosOptionDataRelationIds = new ArrayList<>();
                                OptionData wlosOptionData2 = new OptionData();
                                wlosOptionData2.setOptionUuid(relationEntityEnName);
                                List<OptionData> wlosOptionDataList = optionDataMapper.selectList(new QueryWrapper<>(wlosOptionData2));
                                if (ObjectUtils.isNotEmpty(wlosOptionDataList)) {
                                    wlosOptionDataList.forEach(iterable -> {
                                        String dataLookup = iterable.getData();
                                        JSONObject jsonObject = JSONObject.parseObject(dataLookup);
                                        Object lookupRelation = jsonObject.get(enName);
                                        if (lookupRelation instanceof JSONArray) {
                                            List<String> relationKeys = JSONArray.parseArray(JSON.toJSONString(lookupRelation), String.class);
                                            for (String relationKey : relationKeys) {
                                                if (Objects.equals(uuid, relationKey)) {
                                                    wlosOptionDataRelations.add(iterable);
                                                    wlosOptionDataRelationIds.add(iterable.getUuid());
                                                }
                                            }

                                        } else {
                                            if (Objects.equals(lookupRelation, uuid)) {
                                                wlosOptionDataRelations.add(iterable);
                                                wlosOptionDataRelationIds.add(iterable.getUuid());
                                            }

                                        }

                                    });
                                }
                                lookupMap.put("data", wlosOptionDataRelations);
                                lookupMap.put("relationUuid", wlosOptionDataRelationIds);
                            } else {
                                if (ObjectUtils.isNotEmpty(lookupValue)) {
                                    // 查询关系详情
                                    List<String> relationKeys = new ArrayList<>();
                                    String relationId = "";
                                    if (lookupValue instanceof JSONArray) {
                                        relationKeys = JSONArray.parseArray(JSON.toJSONString(lookupValue), String.class);
                                    } else {
                                        relationKeys.add((String) lookupValue);
                                    }
                                    relationId = relationKeys.get(0);
                                    OptionData wlosOptionDataLookup = optionDataMapper.selectById(relationId);
                                    lookupMap.put("data", wlosOptionDataLookup);
                                    lookupMap.put("relationUuid", lookupValue);
                                }
                            }
                            break;
                        case DataOperateConstants.MANY_TO_ONE:
                            if (Objects.equals(rightEntityUuid, optionUuid)) {
                                List<OptionData> wlosOptionDataRelations = new ArrayList<>();
                                List<String> wlosOptionDataRelationIds = new ArrayList<>();
                                OptionData wlosOptionData2 = new OptionData();
                                wlosOptionData2.setOptionUuid(relationEntityEnName);
                                List<OptionData> wlosOptionDataList = optionDataMapper.selectList(new QueryWrapper<>(wlosOptionData2));
                                if (ObjectUtils.isNotEmpty(wlosOptionDataList)) {
                                    wlosOptionDataList.forEach(iterable -> {
                                        String dataLookup = iterable.getData();
                                        JSONObject jsonObject = JSONObject.parseObject(dataLookup);
                                        Object lookupRelation = jsonObject.get(enName);
                                        if (Objects.equals(lookupRelation, uuid)) {
                                            wlosOptionDataRelations.add(iterable);
                                            wlosOptionDataRelationIds.add(iterable.getUuid());
                                        }

                                    });
                                }
                                lookupMap.put("data", wlosOptionDataRelations);
                                lookupMap.put("relationUuid", wlosOptionDataRelationIds);
                            } else {
                                if (ObjectUtils.isNotEmpty(lookupValue)) {
                                    // 查询关系详情
                                    List<String> relationKeys = new ArrayList<>();
                                    String relationId = "";
                                    if (lookupValue instanceof JSONArray) {
                                        relationKeys = JSONArray.parseArray(JSON.toJSONString(lookupValue), String.class);
                                    } else {
                                        relationKeys.add((String) lookupValue);
                                    }
                                    relationId = relationKeys.get(0);
                                    OptionData wlosOptionDataLookup = optionDataMapper.selectById(relationId);
                                    lookupMap.put("data", wlosOptionDataLookup);
                                    lookupMap.put("relationUuid", lookupValue);

                                }
                            }
                            break;
                        case DataOperateConstants.MANY_TO_MANY:
                            List<OptionData> wlosOptionDataRelations = new ArrayList<>();
                            List<String> wlosOptionDataRelationIds = new ArrayList<>();
                            OptionData wlosOptionData2 = new OptionData();
                            wlosOptionData2.setOptionUuid(relationEntityEnName);
                            List<OptionData> wlosOptionDataList = optionDataMapper.selectList(new QueryWrapper<>(wlosOptionData2));
                            if (ObjectUtils.isNotEmpty(wlosOptionDataList)) {
                                wlosOptionDataList.forEach(iterable -> {
                                    String dataLookup = iterable.getData();
                                    JSONObject jsonObject = JSONObject.parseObject(dataLookup);
                                    Object lookupRelation = jsonObject.get(enName);
                                    if (lookupRelation instanceof JSONArray) {
                                        List<String> relationKeys = JSONArray.parseArray(JSON.toJSONString(lookupRelation), String.class);
                                        for (String relationKey : relationKeys) {
                                            if (Objects.equals(uuid, relationKey)) {
                                                wlosOptionDataRelations.add(iterable);
                                                wlosOptionDataRelationIds.add(iterable.getUuid());
                                            }
                                        }

                                    } else {
                                        if (Objects.equals(lookupRelation, uuid)) {
                                            wlosOptionDataRelations.add(iterable);
                                            wlosOptionDataRelationIds.add(iterable.getUuid());
                                        }

                                    }

                                });
                            }
                            lookupMap.put("data", wlosOptionDataRelations);
                            lookupMap.put("relationUuid", wlosOptionDataRelationIds);
                            break;
                        default:
                            break;

                    }
                    map.put(enName, lookupMap);
                    optionData.setData(JSONObject.toJSONString(map));

                }
            }

        }
        return wlosOptionData1;
    }

    public List<OptionData> queryAll(OptionData optionData) {
        String optionUuid = optionData.getOptionUuid();
        Assert.hasLength(optionUuid, "选项集id不能为空");
        List<OptionData> wlosOptionData1 = optionDataMapper.selectList(new QueryWrapper<>(optionData));
        Entity entityQuery = new Entity();
        entityQuery.setEnName(optionUuid);
        Entity entity = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
        if (ObjectUtils.isEmpty(entity) || ObjectUtils.isEmpty(wlosOptionData1)) {
            return Collections.emptyList();
        }
        String displayField = entity.getDisplayField();
        wlosOptionData1.forEach(item -> item.setDisplayField(displayField));
        return wlosOptionData1;
    }


    public ArrayList<Object> staticEntityData(List<String> enNames) {

        ArrayList<Object> results = new ArrayList<>();
        for (String enName : enNames) {
            OptionData optionDataQuery = new OptionData();
            optionDataQuery.setOptionUuid(enName);
            List<OptionData> wlosOptionData1 = optionDataMapper.selectList(new QueryWrapper<>(optionDataQuery));
            Entity entityQuery = new Entity();
            entityQuery.setEnName(enName);
            Entity entity = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
            String displayField = entity.getDisplayField();
            HashMap<String, Object> itemMap = new HashMap<>();
            List<Map<String,Object>> preOpt = new ArrayList<>();
            if(ObjectUtils.isNotEmpty(wlosOptionData1)){
                for (OptionData optionData : wlosOptionData1) {
                    HashMap<String, Object> itemOpt = new HashMap<>();
                    itemOpt.put("id",optionData.getUuid());
                   if(StringUtils.isNotBlank(displayField)){
                       String data = optionData.getData();
                       JSONObject jsonObject = JSONObject.parseObject(data);
                       itemOpt.put("showColumn",jsonObject.get(displayField));
                   }else {
                       itemOpt.put("showColumn",optionData.getUuid());
                   }
                    preOpt.add(itemOpt);
                }
            }
            itemMap.put(enName, preOpt);
            results.add(itemMap);

        }
        return results;

    }




}