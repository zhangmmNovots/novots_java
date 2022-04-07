package com.weilaios.iqxceqhnhubt.utils;

import com.weilaios.iqxceqhnhubt.exception.BusinessException;

import java.util.HashMap;
import java.util.Map;

/**
 * 元语工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class YuanYuUtils {

    /**
     * 处理，翻译元语，变成流程中想要的数据结构
     * @param code 元语标识
     * @param value 元语附加值
     * @return
     */
    public static Map<String,Object> conductYuanYu(String code,String value) throws BusinessException {
        BeeUtils.isEmpty("code",code);
        Map<String,Object> resultMap = new HashMap<>();
        String start = "";
        String end = "";
        switch (code){
            case YuanYuConstant.YUAN_YU_501:
                //早于
                start = "";
                end = value;
                break;
            case YuanYuConstant.YUAN_YU_502:
                //晚于
                start = value;
                end = "";
                break;
            case YuanYuConstant.YUAN_YU_500:
                //介于
                String[] valuesJieYu = value.split(",");
                start = valuesJieYu[0] + " 00:00:00";
                end = valuesJieYu[1] + " 00:00:00";
                break;
            case YuanYuConstant.YUAN_YU_503:
                //不介于
                String[] values = value.split(",");
                start = values[0] + " 00:00:00";
                end = values[1] + " 00:00:00";
                break;
            case YuanYuConstant.YUAN_YU_504:
                //昨天
                value = "1";
                start = TimeUtils.getPastNDayStart(value);
                end = TimeUtils.getPastNDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_505:
                //今天
                start = TimeUtils.getToDayStart();
                end = TimeUtils.getToDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_506:
                //明天
                value = "1";
                start = TimeUtils.getFutureDayStart();
                end = TimeUtils.getFutureDayEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_507:
                //过去7天
                value = "7";
                start = TimeUtils.getPastNDayStart(value);
                end = TimeUtils.getPastNDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_508:
                //过去30天
                value = "30";
                start = TimeUtils.getPastNDayStart(value);
                end = TimeUtils.getPastNDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_509:
                //过去60天
                value = "60";
                start = TimeUtils.getPastNDayStart(value);
                end = TimeUtils.getPastNDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_510:
                //过去90天
                value = "90";
                start = TimeUtils.getPastNDayStart(value);
                end = TimeUtils.getPastNDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_511:
                //过去N天
                start = TimeUtils.getPastNDayStart(value);
                end = TimeUtils.getPastNDayEnd();
                break;
            case YuanYuConstant.YUAN_YU_512:
                //未来7天
                value = "7";
                start = TimeUtils.getFutureDayStart();
                end = TimeUtils.getFutureDayEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_513:
                //未来30天
                value = "30";
                start = TimeUtils.getFutureDayStart();
                end = TimeUtils.getFutureDayEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_514:
                //未来60天
                value = "60";
                start = TimeUtils.getFutureDayStart();
                end = TimeUtils.getFutureDayEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_515:
                //未来90天
                value = "90";
                start = TimeUtils.getFutureDayStart();
                end = TimeUtils.getFutureDayEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_516:
                //未来N天
                start = TimeUtils.getFutureDayStart();
                end = TimeUtils.getFutureDayEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_517:
                //上周
                value = "1";
                start = TimeUtils.getLastWeekStart(value);
                end = TimeUtils.getLastWeekEnd();
                break;
            case YuanYuConstant.YUAN_YU_518:
                //本周
                start = TimeUtils.getThisWeekStart();
                end = TimeUtils.getThisWeekEnd();
                break;
            case YuanYuConstant.YUAN_YU_519:
                //下周
                value = "1";
                start = TimeUtils.getNextWeekStart();
                end = TimeUtils.getNextWeekEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_520:
                //本周及上周
                value = "1";
                start = TimeUtils.getLastWeekStart(value);
                end = TimeUtils.getThisWeekEnd();
                break;
            case YuanYuConstant.YUAN_YU_521:
                //本周及下周
                value = "1";
                start = TimeUtils.getThisWeekStart();
                end = TimeUtils.getNextWeekEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_522:
                //过去几周
                start = TimeUtils.getLastWeekStart(value);
                end = TimeUtils.getLastWeekEnd();
                break;
            case YuanYuConstant.YUAN_YU_523:
                //未来几周
                start = TimeUtils.getNextWeekStart();
                end = TimeUtils.getNextWeekEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_524:
                //上月
                value = "1";
                start = TimeUtils.getLastMonthStart(value);
                end = TimeUtils.getLastMonthEnd();
                break;
            case YuanYuConstant.YUAN_YU_525:
                //本月
                start = TimeUtils.getThisMonthStart();
                end = TimeUtils.getThisMonthEnd();
                break;
            case YuanYuConstant.YUAN_YU_526:
                //上月和本月
                value = "1";
                start = TimeUtils.getLastMonthStart(value);
                end = TimeUtils.getThisMonthEnd();
                break;
            case YuanYuConstant.YUAN_YU_527:
                //本月和下月
                value = "1";
                start = TimeUtils.getThisMonthStart();
                end = TimeUtils.getNextMonthEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_528:
                //过去几月
                start = TimeUtils.getLastMonthStart(value);
                end = TimeUtils.getLastMonthEnd();
                break;
            case YuanYuConstant.YUAN_YU_529:
                //未来几月
                start = TimeUtils.getNextMonthStart();
                end = TimeUtils.getNextMonthEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_530:
                //去年
                value = "1";
                start = TimeUtils.getLastYearStart(value);
                end = TimeUtils.getLastYearEnd();
                break;
            case YuanYuConstant.YUAN_YU_531:
                //今年
                start = TimeUtils.getThisYearStart();
                end = TimeUtils.getThisYearEnd();
                break;
            case YuanYuConstant.YUAN_YU_532:
                //明年
                value = "1";
                start = TimeUtils.getNextYearStart();
                end = TimeUtils.getNextYearEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_533:
                //过去N年
                start = TimeUtils.getLastYearStart(value);
                end = TimeUtils.getLastYearEnd();
                break;
            case YuanYuConstant.YUAN_YU_534:
                //未来N年
                start = TimeUtils.getNextYearStart();
                end = TimeUtils.getNextYearEnd(value);
                break;
            case YuanYuConstant.YUAN_YU_535:
                // 指定日期的当天
                start = value + " 00:00:00";
                end = value + " 23:59:59";
            default:
                break;
        }
        resultMap.put(YuanYuConstant.START,start);
        resultMap.put(YuanYuConstant.END,end);
        resultMap.put(YuanYuConstant.CODE,code);
        return resultMap;
    }
}
