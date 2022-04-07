package com.weilaios.iqxceqhnhubt.da.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;

import com.weilaios.iqxceqhnhubt.da.mapper.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.model.*;
import com.weilaios.iqxceqhnhubt.utils.DataTypeEnum;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TransformFieldService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EntityFieldMapper entityFieldMapper;
    @Autowired
    private OptionDataMapper optionDataMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private FormulaSettingMapper formulaSettingMapper;
    @Autowired
    private AutoincFieldSettingMapper autoincFieldSettingMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Date format mapping
     */
    private static Map<Integer, String> dateForamtMap = new HashMap<Integer, String>(){{
        put(1, "yyyy");
        put(2, "yyyy-MM");
        put(3, "yyyy-MM-dd");
        put(4, "yyyy-MM-dd HH:mm:ss");
        put(5, "yyyy-dd");
        put(6, "yyyy/MM");
        put(7, "yyyy/MM/dd");
        put(8, "yyyy/dd");
        put(9, "HH:mm:ss");
        put(10, "HH:mm");
        put(11, "yyyy-MM-dd HH:mm");
        put(12, "yyyy/MM/dd HH:mm:ss");
        put(13, "yyyy/MM/dd HH:mm");
    }};


    /**
     * 需要转为json字符串再存库的字段
     */
    public static final List<String> NEED_JSON_STRING = new ArrayList<>(Arrays.asList(
            "Json",
            "Address",
            "Image",
            "Video",
            "Audio",
            "File",
            "Geometry"
    ));


    /**
     * 插入数据转换
     * @param originMap 转换前参数
     * @param placeHolderDataMapList 占位符数据
     * @param modelUuid 表ID
     * @return
     */
    public Map<String, Object> transformInsert(Map<String,Object> originMap, List<HashMap<String, String>> placeHolderDataMapList, String modelUuid) {
        EntityField entityFieldQuery = new EntityField();
        entityFieldQuery.setEntityEnName(modelUuid);
        List<EntityField> entityFieldList = entityFieldMapper.selectList(new QueryWrapper<>(entityFieldQuery));
        Map<String, Object> paramMap = convertParamMap(originMap, placeHolderDataMapList);
        Map<String, Object> transformedMap = doInsert(originMap, placeHolderDataMapList, entityFieldList, paramMap);
        return transformedMap;
    }

    /**
     * 更新数据转换
     * @param originMap 转换前参数
     * @param placeHolderDataMapList 占位符数据
     * @param modelUuid 表ID
     * @return
     */
    public Map<String, Object> transformUpdate(Map<String,Object> originMap, List<HashMap<String, String>> placeHolderDataMapList, String modelUuid) {
        EntityField entityFieldQuery = new EntityField();
        entityFieldQuery.setEntityEnName(modelUuid);
        List<EntityField> entityFieldList = entityFieldMapper.selectList(new QueryWrapper<>(entityFieldQuery));
        Map<String, Object> transformedMap = doUpdate(originMap, placeHolderDataMapList, entityFieldList);
        return transformedMap;
    }

    /**
     * 查询数据转换
     * @param resultMapList 结果集数据
     * @param modelUuid 表ID
     * @return
     */
    public List<Map<String, Object>> transformQuery(List<Map<String, Object>> resultMapList, String modelUuid) {
        if (CollectionUtils.isEmpty(resultMapList)) {
            return new ArrayList<>();
        }
        EntityField entityFieldQuery = new EntityField();
        entityFieldQuery.setEntityEnName(modelUuid);
        List<EntityField> entityFieldList = entityFieldMapper.selectList(new QueryWrapper<>(entityFieldQuery));
        List<Map<String, Object>> transformedList = doQuery(resultMapList, entityFieldList);
        return transformedList;
    }

    /**
     * map转换
     * @param originMap
     * @param placeHolderDataMapList
     * @return
     */
    public Map<String, Object> convertParamMap(Map<String,Object> originMap, List<HashMap<String, String>> placeHolderDataMapList) {
        Map<String, Object> paramMap = new HashMap<>();
        placeHolderDataMapList.forEach(placeHolderDataMap -> {
            placeHolderDataMap.entrySet().forEach(entry -> {
                if (originMap.get(entry.getValue()) != null) {
                    paramMap.put(entry.getKey(), originMap.get(entry.getValue()));
                }
            });
        });
        return paramMap;
    }

    /**
     * 查询结果集转换逻辑
     * @param resultMapList
     * @param entityFieldList
     * @return
     */
    private List<Map<String, Object>> doQuery(List<Map<String, Object>> resultMapList, List<EntityField> entityFieldList) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> dateFormatStrList = Arrays.asList(DataTypeEnum.DATE.getValue(), DataTypeEnum.DATETIME.getValue(), DataTypeEnum.TIMESTAMP.getValue(),DataTypeEnum.TIME.getValue());
        List<EntityField> optionFormat = entityFieldList.stream().filter(item -> DataTypeEnum.OPTION.getValue().equals(item.getType())).collect(Collectors.toList());
        List<EntityField> dateFormat = entityFieldList.stream().filter(item -> dateFormatStrList.contains(item.getBaseType())).collect(Collectors.toList());
        List<String> optionFormatKeyList = optionFormat.stream().map(item -> item.getEnName()).collect(Collectors.toList());
        Map<String, Integer> dateFormatMap = new HashMap<>();
        for (EntityField entityField : dateFormat) {
            dateFormatMap.put(entityField.getEnName(), entityField.getDateFormat());
        }
        resultMapList.forEach(resultMap -> {
            Map<String, Object> transformMap = new HashMap<>();
            resultMap.forEach((fieldName, fieldValue) -> {
                Optional<EntityField> optional = entityFieldList.stream().filter(entityField -> entityField.getEnName().equals(fieldName)).findFirst();
                if (optional.isPresent()) {
                    EntityField entityField = optional.get();
                    if (fieldValue instanceof BigDecimal || fieldValue instanceof Float || fieldValue instanceof Double) {
                        if (null != entityField.getDecimalNumber() && entityField.getDecimalNumber() > 0) {
                            // 取保留小数
                            BigDecimal bd = new BigDecimal(String.valueOf(fieldValue)).setScale(entityField.getDecimalNumber());
                            transformMap.put(fieldName, bd);
                        } else {
                            transformMap.put(fieldName, fieldValue);
                        }
                    } else if (fieldValue instanceof Date) {
                        // 日期处理
                        Integer dateFormatValue = dateFormatMap.get(fieldName);
                        if (null == dateFormatValue) {
                            dateFormatValue = 4;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat(dateForamtMap.get(dateFormatValue));
                        String transferDate = sdf.format(fieldValue);
                        transformMap.put(fieldName, transferDate);
                    } else if (ObjectUtils.isNotEmpty(optionFormatKeyList) && optionFormatKeyList.contains(fieldName)) {
                        // 选项集
                        if (Objects.isNull(fieldValue)) {
                            transformMap.put(fieldName, null);
                        } else {
                            String[] valueList = fieldValue.toString().split(",");
                            List<String> valueIds = Arrays.asList(valueList);
                            List<OptionData> wlosOptionData = optionDataMapper.selectBatchIds(valueIds);
                            if (ObjectUtils.isNotEmpty(wlosOptionData)) {
                                OptionData optionData1 = wlosOptionData.get(0);
                                QueryWrapper<Entity> queryWrapper = new QueryWrapper<>();
                                queryWrapper.eq("en_name", optionData1.getOptionUuid());
                                Entity entity = entityMapper.selectOne(queryWrapper);
                                wlosOptionData.forEach(entry -> entry.setDisplayField(entity.getDisplayField()));
                            }

                            Map<String, Object> map = new HashMap<>();
                            map.put("option_uuids", valueList);
                            map.put("fields", wlosOptionData);
                            transformMap.put(fieldName, map);
                        }
                    } else if (DataTypeEnum.JSON.getValue().equals(entityField.getType())) {
                        JSONObject jsonValue = JSONObject.parseObject(String.valueOf(fieldValue));
                        if (ObjectUtils.isNotEmpty(jsonValue)) {
                            String usageValue = entityField.getUsageValue();
                            List<EntityField> jsonFields = JSONArray.parseArray(usageValue, EntityField.class);
                            Map<String, EntityField> jsonFieldsMap = new HashMap<>();
                            for (EntityField jsonEntityField : jsonFields) {
                                jsonFieldsMap.put(jsonEntityField.getEnName(), jsonEntityField);
                            }
                            for (String jsonKey : jsonValue.keySet()) {
                                EntityField jsonEntityField = jsonFieldsMap.get(jsonKey);
                                if (ObjectUtils.isNotEmpty(jsonEntityField)) {
                                    if (DataTypeEnum.BOOLEAN.getValue().equals(jsonEntityField.getType())) {
                                        Boolean booleanVal = BooleanUtils.toBooleanObject(String.valueOf(fieldValue));
                                        transformMap.put(fieldName, booleanVal);
                                    }
                                }
                            }
                        }
                        transformMap.put(fieldName, jsonValue);
                    } else if (NEED_JSON_STRING.contains(entityField.getType())) {
                        log.info("待解析的json数组:{}", fieldValue);
                        try {
                            transformMap.put(fieldName, JSONArray.parseArray(String.valueOf(fieldValue)));
                        } catch (JSONException e) {
                            try {
                                transformMap.put(fieldName, JSONObject.parseObject(String.valueOf(fieldValue)));
                            } catch (Exception e1) {
                                transformMap.put(fieldName, fieldValue);
                                log.error(e1.toString());
                            }
                        } catch (Exception e) {
                            transformMap.put(fieldName, fieldValue);
                            log.error(e.toString());
                        }
                    } else if (DataTypeEnum.BOOLEAN.getValue().equals(entityField.getType())) {
                        Boolean booleanVal = BooleanUtils.toBooleanObject(String.valueOf(fieldValue));
                        transformMap.put(fieldName, booleanVal);
                    } else {
                        transformMap.put(fieldName, fieldValue);
                    }
                } else {
                    throw new BusinessException("[" + fieldName + "]字段不存在，请同步结构");
                }
                resultList.add(transformMap);
            });
        });
        return resultList;
    }

    /**
     * 插入逻辑
     * @param originMap
     * @param placeHolderDataMapList
     * @param entityFieldList
     * @param paramMap
     * @return
     */
    private Map<String, Object> doInsert(Map<String, Object> originMap, List<HashMap<String, String>> placeHolderDataMapList,
                                         List<EntityField> entityFieldList, Map<String, Object> paramMap) {
        Map<String, Object> transformedMap = new HashMap<>();
        placeHolderDataMapList.forEach(placeHolderDataMap -> {
            Map<String, String> inverseMap = MapUtil.inverse(placeHolderDataMap);
            inverseMap.forEach((placeHolder, enName) -> {
                Optional<EntityField> optional = entityFieldList.stream().filter(entityField -> entityField.getEnName().equals(enName)).findFirst();
                String val = Objects.isNull(originMap.get(placeHolder))? "":originMap.get(placeHolder).toString();
                if (optional.isPresent()) {
                    EntityField entityField = optional.get();
                    log.info("transform field name [{}], field value [{}] begin...", entityField.getEnName(), val);
                    if (entityField.getIsNull() == 0) {
                        validField(entityField, val);
                    }
                    if (StringUtils.isBlank(val)) {
                        val = fillDefaultValue(entityField);
                    }
                    val = fillData(entityField, val, paramMap);
                    log.info("transform field name [{}], field value [{}] end.", entityField.getEnName(), val);
                    transformedMap.put(placeHolder, val);
                }
            });
        });
        originMap.forEach((originKey, originValue) -> {
            if (!transformedMap.containsKey(originKey)) {
                transformedMap.put(originKey, originValue);
            }
        });
        return transformedMap;
    }

    /**
     * 修改逻辑
     * @param originMap
     * @param placeHolderDataMapList
     * @param entityFieldList
     * @return
     */
    private Map<String, Object> doUpdate(Map<String, Object> originMap, List<HashMap<String, String>> placeHolderDataMapList, List<EntityField> entityFieldList) {
        Map<String, Object> transformedMap = new HashMap<>();
        placeHolderDataMapList.forEach(placeHolderDataMap -> {
            Map<String, String> inverseMap = MapUtil.inverse(placeHolderDataMap);
            inverseMap.forEach((placeHolder, enName) -> {
                Optional<EntityField> optional = entityFieldList.stream().filter(entityField -> entityField.getEnName().equals(enName)).findFirst();
                String val = Objects.isNull(originMap.get(placeHolder))? "":originMap.get(placeHolder).toString();
                if (optional.isPresent()) {
                    EntityField entityField = optional.get();
                    validField(entityField, val);
                    val = fillData(entityField, val, originMap);
                    transformedMap.put(placeHolder, val);
                }
            });
        });
        originMap.forEach((originKey, originValue) -> {
            if (!transformedMap.containsKey(originKey)) {
                transformedMap.put(originKey, originValue);
            }
        });
        return transformedMap;
    }

    /**
     *  校验数据
     * @param entityField
     * @param fieldValue
     */
    private void validField(EntityField entityField, String fieldValue) {
        DataTypeEnum dataTypeEnum = EnumUtils.getEnum(DataTypeEnum.class, entityField.getType().toUpperCase(), DataTypeEnum.UNKNOWN);
        String fieldLengthStr = StringUtils.isNotBlank(entityField.getLength())? entityField.getLength(): "255";
        Integer fieldLength = Integer.valueOf(fieldLengthStr);
        switch (dataTypeEnum) {
            case STRING:
            case TEXT:
            case RICHTEXT:
                Assert.isTrue(fieldValue.length() < fieldLength, "【"+ entityField.getEnName() + "】" + " 字段长度超长！");
                break;
            case EMAIL:
                Assert.isTrue(Validator.isEmail(fieldValue), "邮箱格式不正确！");
                break;
            case INTEGER:
                Assert.isTrue(NumberUtil.isInteger(fieldValue), "非法整数格式！");
                break;
            case BOOLEAN:
                Assert.isTrue((fieldValue != null) && fieldValue.equalsIgnoreCase("true") && fieldValue.equalsIgnoreCase("false"), "非法布尔值类型！");
                break;
            case DATE:
                try {
                    LocalDate.parse(fieldValue);
                    break;
                } catch (DateTimeParseException dateTimeParseException) {
                    throw new BusinessException("日期格式不正确！");
                }
            case TIME:
                try {
                    LocalTime.parse(fieldValue);
                    break;
                } catch (DateTimeParseException dateTimeParseException) {
                    throw new BusinessException("时间格式不正确！");
                }
            case DATETIME:
                try {
                    LocalDateTime.parse(fieldValue, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    break;
                } catch (DateTimeParseException dateTimeParseException) {
                    throw new BusinessException("日期时间格式不正确！");
                }
            case YEAR:
                try {
                    Year.parse(fieldValue);
                    break;
                } catch (DateTimeParseException e) {
                    throw new BusinessException("年格式不正确！");
                }
            case DOUBLE:
            case AMOUNT:
//            case PERCENTAGE:
//                Assert.isTrue(fieldValue.length() < fieldLength, "【"+ entityField.getEnName() + "】" + " 字段长度超长！");
//                Integer decimalNumber = entityField.getDecimalNumber();
//                BigDecimal decimal = new BigDecimal((fieldValue));
//                Assert.isTrue(decimal.scale() < decimalNumber, "小数位数不合法！");
                break;
            case TELEPHONE:
            case MOBILE:
                Assert.isTrue(Validator.isMobile(fieldValue), "非法座机/手机格式！");
                break;
            case IDNUMBER:
                Assert.isTrue(IdcardUtil.isValidCard(fieldValue), "身份证格式不正确！");
                break;
            case ZIPCODE:
                Assert.isTrue(Validator.isZipCode(fieldValue), "邮编格式不正确！");
                break;
            default:
                break;
        }
    }

    /**
     * 填充数据
     */
    private String fillData(EntityField entityField, String fieldValue, Map<String, Object> paramMap) {
        DataTypeEnum dataTypeEnum = EnumUtils.getEnum(DataTypeEnum.class, entityField.getType().toUpperCase(), DataTypeEnum.UNKNOWN);
        switch (dataTypeEnum) {
            case UUID:
                fieldValue = IdUtil.objectId();
                break;
//            case PRIMARYKEY:
//                if (!entityField.getBaseType().contains("int")) {
//                    fieldValue = IdUtil.objectId();
//                    break;
//                }
            case AUTOINC:
                //insert操作填充自动编号，update不填充
                if (StringUtils.isEmpty(fieldValue)) {
                    fieldValue = transformAutoincType(entityField);
                }
            case OPTION:
                fieldValue = transformOptionType(fieldValue);
                break;
            case FORMULA:
                fieldValue = transformFormulaType(entityField, paramMap);
                break;
            case JSON:
                fieldValue = transformJsonType(entityField, fieldValue);
                break;
            case BOOLEAN:
                Boolean booleanVal = BooleanUtils.toBooleanObject(fieldValue);
                fieldValue = BooleanUtils.toInteger(booleanVal) + "";
            case DOUBLE:
            case AMOUNT:
            case PERCENTAGE:
                try {
                    BigDecimal decimal = new BigDecimal(fieldValue);
                    decimal.setScale(entityField.getDecimalNumber());
                    fieldValue = decimal.toString();
                } catch (Exception e) {
                    //转换失败，暂不处理
                }
                break;
            default:
                break;
        }
        return fieldValue;
    }


    /**
     * 填充默认值
     */
    private String fillDefaultValue(EntityField entityField) {
        DataTypeEnum dataTypeEnum = EnumUtils.getEnum(DataTypeEnum.class, entityField.getType().toUpperCase(), DataTypeEnum.UNKNOWN);
        String defaultValStr = null;
        String fieldValue = null;
        if (ObjectUtils.isNotEmpty(entityField.getDefaultValue())) {
            defaultValStr = entityField.getDefaultValue().toString();
        }
        switch (dataTypeEnum) {
            case DATE:
                if (ObjectUtils.isNotEmpty(entityField.getDateFillMethod())) {
                    if ("1".equals(entityField.getDateFillMethod())) {
                        fieldValue = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    if ("2".equals(entityField.getDateFillMethod())) {
                        fieldValue = defaultValStr;
                    }
                    break;
                }
            case TIME:
                if (ObjectUtils.isNotEmpty(entityField.getDateFillMethod())) {
                    if ("1".equals(entityField.getDateFillMethod())) {
                        fieldValue = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    }
                    if ("2".equals(entityField.getDateFillMethod())) {
                        fieldValue = defaultValStr;
                    }
                    break;
                }
            case DATETIME:
                if (ObjectUtils.isNotEmpty(entityField.getDateFillMethod())) {
                    if ("1".equals(entityField.getDateFillMethod())) {
                        fieldValue = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    }
                    if ("2".equals(entityField.getDateFillMethod())) {
                        fieldValue = defaultValStr;
                    }
                    break;
                }
            case YEAR:
                if (ObjectUtils.isNotEmpty(entityField.getDateFillMethod())) {
                    if ("1".equals(entityField.getDateFillMethod())) {
                        fieldValue = Year.now().format(DateTimeFormatter.ofPattern("yyyy"));
                    }
                    if ("2".equals(entityField.getDateFillMethod())) {
                        fieldValue = defaultValStr;
                    }
                    break;
                }

            case OPTION:
                if (StringUtils.isNotBlank(defaultValStr) && defaultValStr.startsWith("[")) {
                    List<Object> defaultValueList = JSONArray.parseArray(defaultValStr, Object.class);
                    fieldValue = StringUtils.join(defaultValueList.toArray(), ",");
                }
                break;
            default:
                fieldValue = defaultValStr;
                break;

        }
        return fieldValue;
    }


    /**
     * json格式转换
     * @param entityField
     * @param fieldValue
     * @return
     */
    public String transformJsonType(EntityField entityField, String fieldValue) {
        String usageValue = entityField.getUsageValue();
        JSONObject jsonObject = JSONObject.parseObject(fieldValue);
        JSONObject object = new JSONObject();
        List<EntityField> jsonFields = JSONArray.parseArray(usageValue, EntityField.class);
        if (ObjectUtils.isNotEmpty(jsonFields)) {
            for (EntityField jsonEntityField : jsonFields) {
                String jsonFieldValue = jsonObject.getString(jsonEntityField.getEnName());
                if (ObjectUtils.isEmpty(jsonFieldValue)) {
                    jsonFieldValue = jsonEntityField.getDefaultValue().toString();
                }
                validField(jsonEntityField, jsonFieldValue);
                fillDefaultValue(jsonEntityField);
                object.put(jsonEntityField.getEnName(), jsonFieldValue);
            }
        } else {
            object.put(entityField.getEnName(), "");
        }
        return object.toJSONString();
    }

    /**
     * 处理自动计数
     * @param entityField
     * @return
     */
    public String transformAutoincType(EntityField entityField) {
        AutoincFieldSetting fieldSettingQuery = new AutoincFieldSetting();
        fieldSettingQuery.setEntityUuid(entityField.getEntityUuid());
        fieldSettingQuery.setFieldUuid(entityField.getUuid());
        fieldSettingQuery.setEnName(entityField.getEnName());
        AutoincFieldSetting fieldSetting = autoincFieldSettingMapper.selectOne(new QueryWrapper<>(fieldSettingQuery));
        if (ObjectUtils.isEmpty(fieldSetting)) {
            throw new BusinessException("计数规则不存在");
        }

        JSONArray jsonArray = JSONArray.parseArray(fieldSetting.getRule().toString());
        List<String> joinList = new ArrayList<>();
        jsonArray.forEach(jsonObject -> {
            JSONObject autoincObject = (JSONObject) jsonObject;
            String genType = autoincObject.getString("gen_type");
            Integer reset = fieldSetting.getReset();
            if ("auto".equalsIgnoreCase(genType)) {
                //自动计数
                long resetTime = getResetTime(reset);
                String number = autoIncrememt(fieldSetting.getFieldUuid().toUpperCase(), fieldSetting.getIncStart(), fieldSetting.getIncLength(), resetTime);
                joinList.add(number);
            } else if("string".equalsIgnoreCase(genType)) {
                //固定字符
                String str = autoincObject.getString("string");
                joinList.add(str);
            } else if("date".equalsIgnoreCase(genType)) {
                //创建日期
                String pattern = autoincObject.getString("patten");
                String formatDate = LocalDateTimeUtil.format(LocalDate.now(), pattern);
                joinList.add(formatDate);
            } else if ("property".equalsIgnoreCase(genType)) {
                //引用属性
            }
        });

        return String.join("-", joinList);
    }

    /**
     * 重复周期单位转换
     * @param reset
     * @return
     */
    public long getResetTime(Integer reset) {
        if (reset == 1) {
            return -1;
        } else if (reset == 2) {
            return 24l;
        } else if(reset == 3) {
            return 24*7l;
        } else if(reset == 4) {
            return 24*31l;
        } else if (reset == 5) {
            return 24*365l;
        }
        return 0;
    }


    /**
     * 自动计数
     * @param prefix 前缀key
     * @param incStart 初始值
     * @param incLength 计数位数
     * @param resetTime 重复周期
     * @return
     */
    public String autoIncrememt(String prefix, Integer incStart, Integer incLength, long resetTime) {
        String key = "FIELD_ID_" + prefix;
        String number = null;
        Long increment = null;
        try {
            if (!redisTemplate.hasKey(key)) {
                increment = redisTemplate.opsForValue().increment(key, incStart);
                if (resetTime > 0) {
                    redisTemplate.expire(key, resetTime, TimeUnit.HOURS);
                }
            } else {
                increment = redisTemplate.opsForValue().increment(key, incStart);
            }
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumIntegerDigits(incLength);
            formatter.setGroupingUsed(false);
            number = formatter.format(increment);
        } catch (Exception e) {
            throw new BusinessException("计数失败");
        }
        return number;
    }


    /**
     * 处理option类型
     * @param fieldValue
     * @return
     */
    public String transformOptionType(String fieldValue) {
        if (JSONObject.isValidObject(fieldValue)) {
            String realVal = "";
            JSONObject jsonObject = JSONObject.parseObject(fieldValue);
            Object option_uuids = jsonObject.get("option_uuids");
            List<String> optionUuids = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(option_uuids)) {
                if (option_uuids instanceof JSONArray) {
                    optionUuids = JSONArray.parseArray(JSON.toJSONString(option_uuids), String.class);
                } else {
                    List<String> asList = Arrays.asList(option_uuids.toString().split(","));
                    optionUuids.addAll(asList);
                }
            }
            if (ObjectUtils.isNotEmpty(optionUuids)) {
                realVal = String.join(",", optionUuids);
            }
            fieldValue = realVal;
        }
        return fieldValue;
    }

    /**
     * 公式计算
     * @param entityField
     * @param paramMap
     * @return
     */
    public String transformFormulaType(EntityField entityField, Map<String, Object> paramMap) {
        FormulaSetting formulaSettingQw = new FormulaSetting();
        formulaSettingQw.setProjectUuid(entityField.getProjectUuid());
        formulaSettingQw.setEntityUuid(entityField.getEntityUuid());
        formulaSettingQw.setEnName(entityField.getEnName());
        FormulaSetting formulaSetting = formulaSettingMapper.selectOne(new QueryWrapper<>(formulaSettingQw));
        String evalValue = null;
        if (ObjectUtils.isNotEmpty(formulaSetting)) {
            String params = formulaSetting.getParameters();
            if (StringUtils.isEmpty(params)) {
                throw new BusinessException("公式字段异常，参数列表为空!");
            }
            String[] paramList = params.split(",");
            String rule = formulaSetting.getRule();
            Map<String, Object> bindings = new HashMap<>();
            for (String paramValue : paramList) {
                bindings.put(paramValue, paramMap.get(paramValue));
            }
            try {
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("nashorn");
                Object eval = engine.eval(rule, new SimpleBindings(bindings));
                String realType = eval.getClass().getSimpleName();
                if (!realType.equalsIgnoreCase(formulaSetting.getResultType())) {
                    throw new BusinessException("公式返回类型与预期返回类型不匹配！");
                }
                if (ObjectUtils.isNotEmpty(eval) && "NaN".equals(eval.toString())) {
                    throw new BusinessException("公式参数不合法！");
                }
                evalValue = eval.toString();
            } catch (ScriptException e) {
                throw new BusinessException("公式字段计算失败，请检查规则及参数！");
            }
        }
        return evalValue;
    }
}