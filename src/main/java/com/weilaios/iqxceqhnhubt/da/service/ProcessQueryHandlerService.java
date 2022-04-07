package com.weilaios.iqxceqhnhubt.da.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import com.weilaios.iqxceqhnhubt.da.mapper.*;
import com.weilaios.iqxceqhnhubt.da.service.*;
import com.weilaios.iqxceqhnhubt.model.*;
import com.weilaios.iqxceqhnhubt.utils.DataOperateConstants;
import com.weilaios.iqxceqhnhubt.utils.Constants;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



@Service
public class ProcessQueryHandlerService {

    protected static final Logger log = LoggerFactory.getLogger(ProcessQueryHandlerService.class);
    private static Map<Integer, String> dateForamtMap = new HashMap<>();
    private static Map<Object, Boolean> booleanFormatMap = new HashMap<>();
    static {
        dateForamtMap.put(1, "yyyy");
        dateForamtMap.put(2, "yyyy-MM");
        dateForamtMap.put(3, "yyyy-MM-dd");
        dateForamtMap.put(4, "yyyy-MM-dd HH:mm:ss");
        dateForamtMap.put(5, "yyyy-dd");
        dateForamtMap.put(6, "yyyy/MM");
        dateForamtMap.put(7, "yyyy/MM/dd");
        dateForamtMap.put(8, "yyyy/dd");
        dateForamtMap.put(9, "HH:mm:ss");
        dateForamtMap.put(10, "HH:mm");
        dateForamtMap.put(11, "yyyy-MM-dd HH:mm");
        dateForamtMap.put(12, "yyyy/MM/dd HH:mm:ss");
        dateForamtMap.put(13, "yyyy/MM/dd HH:mm");
        booleanFormatMap.put("true", true);
        booleanFormatMap.put(true, true);
        booleanFormatMap.put("false", false);
        booleanFormatMap.put(false, false);
    }
    @Autowired
    private  OptionDataMapper optionDataMapper;
    @Autowired
    private  EntityMapper   entityMapper;



    public List<Map<String, Object>> coverDataToNewData(List<Map<String, Object>> resultData, List<EntityField> wlosEntityFields) {
        if (ObjectUtils.isEmpty(resultData)) {
            return new ArrayList<>();
        }
        Map<String, EntityField> wlosEntityFieldMap = new HashMap<>();
        for (EntityField wlosEntityField : wlosEntityFields) {
            wlosEntityFieldMap.put(wlosEntityField.getEnName(), wlosEntityField);
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Set<String> newField = new HashSet<>();
        // 获取当前实体的主键
        //查询
        List<String> lists = Arrays.asList(DataOperateConstants.DATE_TYPE, DataOperateConstants.DATETIME_TYPE, DataOperateConstants.TIMESTAMP_TYPE, DataOperateConstants.TIME_TYPE);
        List<EntityField> dateFormat = wlosEntityFields.stream().filter(item -> lists.contains(item.getBaseType())).collect(Collectors.toList());
        List<EntityField> optionFormat = wlosEntityFields.stream().filter(item -> DataOperateConstants.OPTION.equals(item.getType())).collect(Collectors.toList());
        List<String> optionFormatKeyList = optionFormat.stream().map(item -> item.getEnName()).collect(Collectors.toList());
        Set<String> existsFields = new HashSet<>();
        Map<String, Integer> dateFormatMap = new HashMap<>();
        for (EntityField wlosEntityField : dateFormat) {
            dateFormatMap.put(wlosEntityField.getEnName(), wlosEntityField.getDateFormat());
        }
        resultData.forEach(item -> {
            Map<String, Object> newData = new HashMap<>();
            item.forEach((key, value) -> {
                existsFields.add(key);
                EntityField keyField = wlosEntityFieldMap.get(key);
                if (ObjectUtils.isNotEmpty(keyField)) {

                    if (value instanceof BigDecimal || value instanceof Float || value instanceof Double) {
                        if (ObjectUtils.isNotEmpty(keyField) && null != keyField.getDecimalNumber() && keyField.getDecimalNumber() > 0) {
                            // 取保留小数
                            BigDecimal bd = new BigDecimal(String.valueOf(value)).setScale(keyField.getDecimalNumber());
                            newData.put(key, bd);
                        } else {
                            newData.put(key, value);
                        }
                    } else if (dateFormatMap.containsKey(key)) {
                        // 日期处理
                        Integer dateFormatValue = dateFormatMap.get(key);
                        if (null == dateFormatValue) {
                            dateFormatValue = 4;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat(dateForamtMap.get(dateFormatValue));
                        String transferDate = sdf.format(value);
                        newData.put(key, transferDate);
                    } else if (ObjectUtils.isNotEmpty(optionFormatKeyList) && optionFormatKeyList.contains(key)) {
                        // 选项集
                        if (null != value) {
                            String[] valueList = value.toString().split(",");
                            List<String> valueIds = Arrays.asList(valueList);
                            List<OptionData> wlosOptionData = optionDataMapper.selectBatchIds(valueIds);
                            if (ObjectUtils.isNotEmpty(wlosOptionData)) {
                                OptionData wlosOptionData1 = wlosOptionData.get(0);

                                Entity entityQuery = new Entity();
                                entityQuery.setEnName(wlosOptionData1.getOptionUuid());
                                Entity wlosEntity = entityMapper.selectOne(new QueryWrapper<>(entityQuery));
                                wlosOptionData.forEach(entry -> entry.setDisplayField(wlosEntity.getDisplayField()));
                            }

                            Map<String, Object> map = new HashMap<>();
                            map.put("option_uuids", valueList);
                            map.put("fields", wlosOptionData);
                            newData.put(key, map);
                        } else {
                            newData.put(key, value);
                        }
                    } else if (ObjectUtils.isNotEmpty(keyField) && DataOperateConstants.JSON.equals(keyField.getType())) {
                        JSONObject jsonValue = JSONObject.parseObject(String.valueOf(value));
                        if (ObjectUtils.isNotEmpty(jsonValue)) {
                            String usageValue = keyField.getUsageValue();
                            List<EntityField> jsonFields = JSONArray.parseArray(usageValue, EntityField.class);
                            Map<String, EntityField> jsonFieldsMap = new HashMap<>();
                            if(ObjectUtils.isNotEmpty(jsonFields)){
                                for (EntityField wlosEntityField : jsonFields) {
                                    jsonFieldsMap.put(wlosEntityField.getEnName(), wlosEntityField);
                                }
                            }
                            for (String jsonKey : jsonValue.keySet()) {
                                EntityField wlosEntityField = jsonFieldsMap.get(jsonKey);
                                if (ObjectUtils.isNotEmpty(wlosEntityField)) {
                                    if (DataOperateConstants.BOOLEAN.equals(wlosEntityField.getType())) {
                                        if (String.valueOf(jsonValue.get(jsonKey)).equals("1")) {
                                            jsonValue.put(jsonKey, true);
                                        } else {
                                            jsonValue.put(jsonKey, false);
                                        }
                                    }
                                }
                            }
                        }
                        newData.put(key, jsonValue);
                    } else if (DataOperateConstants.BOOLEAN.equals(keyField.getType())) {
                        if (String.valueOf(value).equals("1")) {
                            newData.put(key, true);
                        } else {
                            newData.put(key, false);
                        }
                    } else {
                        newData.put(key, value);
                    }
                }else {
                    newData.put(key, value);
                }
            });
            List<String> newFields = newField.stream().filter(ite -> !existsFields.contains(ite)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(newFields)) {
                newFields.forEach(itemField -> {
                    newData.put(itemField, "");
                });
            }
            resultList.add(newData);
        });
        return resultList;
    }



    public List<Map<String, Object>> queryProcessHandle(List<Map<String, Object>> resultList,MultiValueMap<String, String> relationKeys, String primaryKey) {
        //=================================================================方案一
        List<List<Map<String, Object>>> result = new ArrayList<>();
        //将list里数据，按字段名,mapKey进行拆分
        Map<String, Integer> subIndexMap = new HashMap<>();
        for (Map<String, Object> itemdata : resultList) {
            Map<String, Object> primaryMap = new HashMap<>();
            HashMap<String, HashMap<String, Object>> subMaps = new HashMap<>();

            relationKeys.forEach((key, value) -> {
                HashMap<String, Object> subItemMap = new HashMap<>();
                subMaps.put(key, subItemMap);
            });

            List<Map<String, Object>> tempList = new ArrayList<>();
            itemdata.forEach((key, value) -> {
                AtomicBoolean isPresent = new AtomicBoolean(false);
                relationKeys.forEach((key1, value1) -> {
                    if (key.startsWith(key1)) {
                        isPresent.set(true);
                        String subkey = key.substring(2);
                        HashMap<String, Object> subItemMap = subMaps.get(key1);
                        subItemMap.put(subkey, value);
                    }

                });
                if (!isPresent.get()) {
                    primaryMap.put(key, value);
                }

            });
            tempList.add(primaryMap);
            AtomicInteger initIndex = new AtomicInteger(0);
            relationKeys.forEach((key, value) -> {
                initIndex.getAndIncrement();
                subIndexMap.put(key, initIndex.get());
                tempList.add(subMaps.get(key));
            });
            result.add(tempList);
        }
        List<Integer> alreadyTakenIndex = new ArrayList<>();
        List<Map<String, Object>> finalResultList = new ArrayList<>();
        //将同组数据进行切片聚合处理
        for (int i = 0; i < result.size(); i++) {
            if (alreadyTakenIndex.contains(i)) {
                continue;
            }
            List<Map<String, Object>> maps = result.get(i);
            Map<String, Object> objectMap = maps.get(0);


            for (Map.Entry<String, List<String>> stringStringEntry : relationKeys.entrySet()) {
                Set<Map> data1 = new HashSet<>();
                String key = stringStringEntry.getKey();
                String relationKey = stringStringEntry.getValue().get(0);
                String relationTablePrimaryKey = stringStringEntry.getValue().get(1);

                Integer integer = subIndexMap.get(key);
                Map<String, Object> objectData = maps.get(integer);
                Map<String, Object> stringMapHashMap = new HashMap<>();
                //                for (String s : objectData.keySet()) {
                //                    if (s.startsWith(key)) {
                stringMapHashMap.put(Constants.COLUMNS, objectData);
                stringMapHashMap.put(Constants.UUID, objectData.get("" + relationTablePrimaryKey));
                data1.add(stringMapHashMap);
                Object uuid = objectMap.get(primaryKey);
                int next = i + 1;
                while (next < result.size()) {
                    List<Map<String, Object>> maps1 = result.get(next);
                    Map<String, Object> stringObjectMap = maps1.get(0);

                    int finalNext = next;

                    Map<String, Object> stringObjectMap1 = maps1.get(integer);
                    if (uuid.equals(stringObjectMap.get(primaryKey))) {
                        alreadyTakenIndex.add(finalNext);
                        Map<String, Object> stringMapHashMap1 = new HashMap<>();
                        //                                for (String s1 : stringObjectMap1.keySet()) {
                        //                                    if(s1.startsWith(key)){
                        stringMapHashMap1.put(Constants.COLUMNS, stringObjectMap1);
                        stringMapHashMap1.put(Constants.UUID, stringObjectMap1.get("" + relationTablePrimaryKey));
                        data1.add(stringMapHashMap1);


                    }

                    next++;
                }
                objectMap.put(relationKey, data1);
            }
            finalResultList.add(objectMap);

        }
        return finalResultList;
    }

}
