package com.weilaios.iqxceqhnhubt.bl.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import com.weilaios.iqxceqhnhubt.bl.GatewayMatchService;
import com.weilaios.iqxceqhnhubt.bl.ParamHandleService;
import com.weilaios.iqxceqhnhubt.utils.*;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import org.springframework.stereotype.Service;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 网管判断实现类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
@Service
public class GatewayMatchServiceImpl implements GatewayMatchService{
    private transient Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamHandleService paramHandleService;

    @Override
    public boolean branchConditionMatch(String paramsJson, JSONObject conditionJson) throws BusinessException{
        try {
            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
            DocumentContext paramsJsonContext = JsonPath.parse(paramsJson);
            //匹配状态：如果全部未匹配，则需要执行默认状态
            boolean matchState = false;
            //获取分支的or节点
            JSONArray subOrList = conditionJson.getJSONArray("or");
            //获取or里的and节点，or分支里的满足一个and节点为true，则当前分支满足条件
            for (Object subOrObj : subOrList) {
                JSONObject subOr = (JSONObject) subOrObj;
                //获取and节点里的多个and条件
                JSONArray andList = subOr.getJSONArray("and");
                boolean andTrueFlag = true; //标识and判断的最终结果是true 还是false
                //处理and的条件，and的条件必须全部为true
                for (Object subAndObj : andList) {
                    //处理单个条件
                    JSONObject subAnd = (JSONObject) subAndObj;
                    boolean matchResult = handleMatch(subAnd, paramsJsonContext);
                    if (!matchResult) {
                        andTrueFlag = false;
                        break;
                    }
                }
                if (andTrueFlag) {
                    matchState = true;
                    break;
                }
            }
            log.info("=====matchState==="+matchState);
            return matchState;
        } catch (BusinessException e) {
            log.error("处理失败",e);
            return false;
        } catch (Exception e) {
            log.error("处理失败",e);
            return false;
        }
    }

    public boolean handleMatch(JSONObject map, DocumentContext context) throws BusinessException{
        //从流程中获取参数名称
        Object variableKey = map.get("variable");
        String variableParamSourceType = map.getString("variableParamSourceType");
        //参数的数据类型：如果是选项集类型的话，需要获取到选项集的code码：
        // 如果是从其他节点的返回结果里面输出出来的话，需要特殊处理-datamodel 对选项集返回的结果做了封装处理，
        // 如果是非底层的话，获取到的直接就是code码
        String variableParamsType = map.getString("paramsType");
        Object variableValue = paramHandleService.getParamValue(context, variableKey, variableParamSourceType);
        log.info("===do in handleMatch=====variableParamsType:"+variableParamsType+"====variableValue:"+JSON.toJSONString(variableValue));

        if ("Option".equals(variableParamsType)) {
            //如果是选项集类型的，则特殊处理
            variableValue = this.getOptionValue(variableValue);
        }

        //获取匹配类型
        String matchType = map.getString("matchType");
        //匹配的值：存在匹配值为空的情况：是否空校验，正则校验等
        Object matchValue = null;
        try {
            if (map.containsKey("matchValue")) {
                Object matchValuePath = map.get("matchValue");
                if (null!=matchValuePath && !"".equals(matchValuePath)) {
                    String matchValueSourceType = map.getString("matchValueSourceType");
                    matchValue = paramHandleService.getParamValue(context, matchValuePath, matchValueSourceType);
                    log.info("===do in handleMatch=====variableParamsType:" + variableParamsType + "====matchValue:" + JSON.toJSONString(matchValue));
                    if ("Option".equals(variableParamsType)) {
                        //如果是选项集类型的，则特殊处理
                        matchValue = this.getOptionValue(matchValue);
                    }
                }

                //元语：介于/不介于，为区间值
                if (YuanYuConstant.YUAN_YU_500.equals(matchType) || YuanYuConstant.YUAN_YU_503.equals(matchType)) {
                    try {
                        Object matchValuePathTwo = map.get("matchValueTwo");
                        if (null!=matchValuePathTwo && !"".equals(matchValuePathTwo)) {
                            String matchValueSourceTypeTwo = map.getString("matchValueSourceTypeTwo");
                            Object matchValueTwo = paramHandleService.getParamValue(context, matchValuePathTwo, matchValueSourceTypeTwo);
                            //组装区间值规则，统一格式为逗号拼接
                            matchValue = null != matchValue ? matchValue : "";
                            matchValueTwo = null != matchValueTwo ? matchValueTwo : "";
                            matchValue = matchValue + "," + matchValueTwo;
                        }
                    }  catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            log.debug("捕获异常", e);
        }
        return choiceMatch(variableValue, matchType, matchValue,variableParamsType);
    }

    /**
    * 处理选项集，获取选项集的code值
    * @param value
    * @return
    */
    public Object getOptionValue(Object value){
        try {
            if (null != value && !"".equals(value)) {
                if (value instanceof Map) {
                    JSONObject vjson = JSON.parseObject(JSON.toJSONString(value));
                    JSONArray optionUuids = vjson.getJSONArray("option_uuids");
                    value = optionUuids;
                } else {
                    //如果类型是选项集，但是不是map类型，则根据逗号分割：前端传入的多选选项集是逗号拼接的
                    value = String.valueOf(value).split(",");
                }
            }
        } catch (Exception e) {
            log.info("variableValue兼容获取选项集类型值", e);
        }
        return value;
    }

    public boolean choiceMatch(Object variable, String matchType, Object matchValue, String dataType) throws BusinessException {
        log.info("======in choiceMatch=====variable:{}===matchType:{}===matchValue:{}===dataType:{}", variable, matchType, matchValue, dataType);
        try {
            //判断如果不是判断为空的，如果匹配前的值为空，则直接返回false
            if (!Constants.MATCH_TYPE_NULL_201.equals(matchType) && (null==variable || "".equals(variable))) {
                return false;
            }

            String variableStr = String.valueOf(variable);
            String matchValueStr = String.valueOf(matchValue);

            //判断匹配类型
            switch (matchType) {
                case Constants.MATCH_TYPE_EQ_11:
                    //等于/是
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.eq(new BigDecimal(variableStr), new BigDecimal(matchValueStr));
                    } else if (Constants.DM_DATA_TYPE_OPTION.equals(dataType)) {
                        //如果是选项集
                        return this.handleOptionMatch(variable, matchValue, matchType);
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return DateUtils.isDateEqual(variableStr, matchValueStr);
                    }
                    return variableStr.equals(matchValueStr);
                case Constants.MATCH_TYPE_NE_10:
                    //不是
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.ne(new BigDecimal(variableStr), new BigDecimal(matchValueStr));
                    } else if (Constants.DM_DATA_TYPE_OPTION.equals(dataType)) {
                        return this.handleOptionMatch(variable, matchValue, matchType);
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return !DateUtils.isDateEqual(variableStr, matchValueStr);
                    }

                    return !variableStr.equals(matchValueStr);
                case Constants.MATCH_TYPE_GE_111:
                    //大于等于
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.ge(new BigDecimal(variableStr), new BigDecimal(matchValueStr));
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return !DateUtils.isDateGe(variableStr, matchValueStr);
                    }
                    return false;
                case Constants.MATCH_TYPE_GT_110:
                    //大于
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.gt(new BigDecimal(variableStr), new BigDecimal(matchValueStr));
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return !DateUtils.isDateGt(variableStr, matchValueStr);
                    }
                    return false;
                case Constants.MATCH_TYPE_LE_101:
                    //小于等于
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.le(new BigDecimal(variableStr), new BigDecimal(matchValueStr));
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return !DateUtils.isDateLe(variableStr, matchValueStr);
                    }
                    return false;
                case Constants.MATCH_TYPE_LT_100:
                    //小于
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.lt(new BigDecimal(variableStr), new BigDecimal(matchValueStr));
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return !DateUtils.isDateLt(variableStr, matchValueStr);
                    }
                    return false;
                case Constants.MATCH_TYPE_NULL_201:
                    //为空
                    return null==variable || "".equals(variable);
                case Constants.MATCH_TYPE_NOT_NULL_200:
                    //不为空
                    return null!=variable && !"".equals(variable);
                case Constants.MATCH_TYPE_START_WITH_300:
                    //以...开始
                    return variableStr.startsWith(matchValueStr);
                case Constants.MATCH_TYPE_END_WITH_301:
                    //以...结束
                    return variableStr.endsWith(matchValueStr);
                case Constants.MATCH_TYPE_CONTAIN_302:
                    //包含
                    //如果是选项集
                    if (Constants.DM_DATA_TYPE_OPTION.equals(dataType)) {
                        return this.handleOptionMatch(variable, matchValue, matchType);
                    }

                    if (variable instanceof List || variable.getClass().isArray()) {

                        return Arrays.asList(variable).contains(matchValue);
                    }
                    return variableStr.contains(matchValueStr);
                case Constants.MATCH_TYPE_NOT_CONTAIN_303:
                    //不包含
                    //如果是选项集
                    if (Constants.DM_DATA_TYPE_OPTION.equals(dataType)) {
                        return this.handleOptionMatch(variable, matchValue, matchType);
                    }

                    if (variable instanceof List || variable.getClass().isArray()) {

                        return !Arrays.asList(variable).contains(matchValue);
                    }
                    return !variableStr.contains(matchValueStr);

                case YuanYuConstant.YUAN_YU_501:
                    //时间，日期时间 --早于
                    if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return DateUtils.isDateLt(variableStr, matchValueStr);
                    }
                    return false;
                case YuanYuConstant.YUAN_YU_502:
                    //时间，日期时间 --晚于
                    if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return DateUtils.isDateGt(variableStr, matchValueStr);
                    }
                    return false;
                case YuanYuConstant.YUAN_YU_500: {
                    //时间，日期时间 --介于
                    if (StringUtils.isBlank(variableStr) || StringUtils.isBlank(matchValueStr)) {
                        return false;
                    }
                    Map yuanYuMap = YuanYuUtils.conductYuanYu(YuanYuConstant.YUAN_YU_500, matchValueStr);
                    String start = (String) yuanYuMap.get(YuanYuConstant.START);
                    String end = (String) yuanYuMap.get(YuanYuConstant.END);
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.ge(new BigDecimal(variableStr), new BigDecimal(start)) && DecimalUtil.le(new BigDecimal(variableStr), new BigDecimal(end));
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return DateUtils.isDateGe(variableStr, start) && DateUtils.isDateLe(variableStr, end);
                    }
                    return false;
                }
                case YuanYuConstant.YUAN_YU_503: {
                    //时间，日期时间 --不介于
                    if (StringUtils.isBlank(variableStr) || StringUtils.isBlank(matchValueStr)) {
                        return false;
                    }

                    Map yuanYuMap = YuanYuUtils.conductYuanYu(YuanYuConstant.YUAN_YU_503, matchValueStr);
                    String start = (String) yuanYuMap.get(YuanYuConstant.START);
                    String end = (String) yuanYuMap.get(YuanYuConstant.END);
                    if (Constants.DM_DATA_TYPE_INTEGER.equals(dataType)
                            || Constants.DM_DATA_TYPE_AMOUNT.equals(dataType)
                            || Constants.DM_DATA_TYPE_DECIMAL.equals(dataType)
                            || Constants.DM_DATA_TYPE_DOUBLE.equals(dataType)) {
                        //如果是金额数值类型，则转换后判断
                        return DecimalUtil.lt(new BigDecimal(variableStr), new BigDecimal(start)) || DecimalUtil.gt(new BigDecimal(variableStr), new BigDecimal(end));
                    } else if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        //日期时间类型
                        return DateUtils.isDateLt(variableStr, start) || DateUtils.isDateGt(variableStr, end);
                    }
                    return false;
                }
                case YuanYuConstant.YUAN_YU_511:
                case YuanYuConstant.YUAN_YU_516:
                case YuanYuConstant.YUAN_YU_522:
                case YuanYuConstant.YUAN_YU_523:
                case YuanYuConstant.YUAN_YU_528:
                case YuanYuConstant.YUAN_YU_529:
                case YuanYuConstant.YUAN_YU_533:
                case YuanYuConstant.YUAN_YU_534: {
                    //时间，日期时间 --N
                    if (StringUtils.isBlank(variableStr) || StringUtils.isBlank(matchValueStr)) {
                        return false;
                    }
                    if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        Map yuanYuMap = YuanYuUtils.conductYuanYu(matchType, matchValueStr);
                        String startTime = (String) yuanYuMap.get(YuanYuConstant.START);
                        String endTime = (String) yuanYuMap.get(YuanYuConstant.END);
                        //日期时间类型
                        return DateUtils.isDateGe(variableStr, startTime) && DateUtils.isDateLe(variableStr, endTime);
                    }
                    return false;
                }
                case YuanYuConstant.YUAN_YU_504:
                case YuanYuConstant.YUAN_YU_505:
                case YuanYuConstant.YUAN_YU_506:
                case YuanYuConstant.YUAN_YU_507:
                case YuanYuConstant.YUAN_YU_508:
                case YuanYuConstant.YUAN_YU_509:
                case YuanYuConstant.YUAN_YU_510:
                case YuanYuConstant.YUAN_YU_512:
                case YuanYuConstant.YUAN_YU_513:
                case YuanYuConstant.YUAN_YU_514:
                case YuanYuConstant.YUAN_YU_515:
                case YuanYuConstant.YUAN_YU_517:
                case YuanYuConstant.YUAN_YU_518:
                case YuanYuConstant.YUAN_YU_519:
                case YuanYuConstant.YUAN_YU_520:
                case YuanYuConstant.YUAN_YU_521:
                case YuanYuConstant.YUAN_YU_524:
                case YuanYuConstant.YUAN_YU_525:
                case YuanYuConstant.YUAN_YU_526:
                case YuanYuConstant.YUAN_YU_527:
                case YuanYuConstant.YUAN_YU_530:
                case YuanYuConstant.YUAN_YU_531:
                case YuanYuConstant.YUAN_YU_532: {

                    //时间，日期时间 过去 /未来
                    if (StringUtils.isBlank(variableStr)) {
                        return false;
                    }

                    if (Constants.DM_DATA_TYPE_DATE.equals(dataType) || Constants.DM_DATA_TYPE_DATE_TIME.equals(dataType)) {
                        Map yuanYuMap = YuanYuUtils.conductYuanYu(matchType, null);
                        String startTime = (String) yuanYuMap.get(YuanYuConstant.START);
                        String endTime = (String) yuanYuMap.get(YuanYuConstant.END);
                        //日期时间类型
                        return DateUtils.isDateGe(variableStr, startTime) && DateUtils.isDateLe(variableStr, endTime);
                    }
                    return false;
                }
                default:
                return false;
            }
        } catch (Exception e) {
            log.error("分支处理失败", e);
            throw new BusinessException("分支处理失败", e);
        }
    }

    /**
    * 处理选项集匹配的情况：由于选项集是可以多选的，故选项集的转换成list后
    * @param variable
    * @param matchValue
    * @param matchType
    * @return
    */
    public boolean handleOptionMatch(Object variable,Object matchValue,String matchType){
        try {
            //如果是选项集
            //如果匹配相等的情况，都为空，则认为相等，一个为空，一个不为空，则认为不相等
            if (Constants.MATCH_TYPE_EQ_11.equals(matchType)) {
                if (null == variable && null == matchValue) {
                    return true;
                }
                if (null == variable && null != matchValue) {
                    return false;
                }
                if (null != variable && null == matchValue) {
                    return false;
                }
            }

            //如果匹配相等的情况，都为空，则认为不相等，一个为空，一个不为空，则认为相等
            if (Constants.MATCH_TYPE_NE_10.equals(matchType)) {
                if (null == variable && null == matchValue) {
                    return false;
                }
                if (null == variable && null != matchValue) {
                    return true;
                }
                if (null != variable && null == matchValue) {
                    return true;
                }
            }

            List variableList = new ArrayList();
            List matchValueList =new ArrayList();
            //数组转集合
            if (variable instanceof Object[]) {
                Object[] vv = (Object[]) variable;
                for (int i = 0; i < vv.length; i++) {
                    variableList.add(vv[i]);
                }
            }
            if (variable instanceof List) {
                variableList = (List) variable;
            }

            if (matchValue instanceof Object[]) {
                Object[] mm = (Object[]) matchValue;
                for (int i = 0; i < mm.length; i++) {
                    matchValueList.add(mm[i]);
                }
            }
            if (matchValue instanceof List) {
                matchValueList = (List) matchValue;
            }

            if (Constants.MATCH_TYPE_EQ_11.equals(matchType)) {
                return variableList.stream().sorted().collect(Collectors.joining())
                        .equals(matchValueList.stream().sorted().collect(Collectors.joining()));
            } else if (Constants.MATCH_TYPE_NE_10.equals(matchType)) {
                return !variableList.stream().sorted().collect(Collectors.joining())
                        .equals(matchValueList.stream().sorted().collect(Collectors.joining()));
            } else if (Constants.MATCH_TYPE_CONTAIN_302.equals(matchType)) {
                return variableList.contains(matchValueList);
            } else if (Constants.MATCH_TYPE_NOT_CONTAIN_303.equals(matchType)) {
                return !variableList.contains(matchValueList);
            }
        } catch (Exception e) {
            log.error("处理分支选项集类型匹配失败", e);
        }
        return false;
    }
}