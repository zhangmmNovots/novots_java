package com.weilaios.iqxceqhnhubt.utils;

/**
 * 元语常量类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class YuanYuConstant {

    public static final String CODE = "code";
    public static final String START = "start";
    public static final String END = "end";

    /**
     * YUAN_YU_500:介于 2021-06-29 15:30:00 到 2021-06-30 16:30:00
     * 2021-06-29 15:30:00 < time < 2021-06-30 16:30:00
     */
    public static final String YUAN_YU_500 = "500";

    /**
     * YUAN_YU_501:早于 2021-06-29 00:00:00
     * time < 2021-06-29 00:00:00
     */
    public static final String YUAN_YU_501 = "501";

    /**
     * YUAN_YU_502:晚于 2021-06-29 00:00:00
     * time > 2021-06-29 00:00:00
     */
    public static final String YUAN_YU_502 = "502";

    /**
     * YUAN_YU_503:不介于 2021-06-29 15:30:00 到 2021-06-30 16:30:00
     * time < 2021-06-29 15:30:00   time > 2021-06-30 16:30:00
     */
    public static final String YUAN_YU_503 = "503";
    
    /**
     * YUAN_YU_504:昨天
     * 2021-06-29 00:00:00 <= time <= 2021-06-29 23:59:59
     */
    public static final String YUAN_YU_504 = "504";
    
    /**
     * YUAN_YU_505:今天
     * 2021-06-30 00:00:00 <= time <= 2021-06-30 23:59:59
     */
    public static final String YUAN_YU_505 = "505";
    
    /**
     * YUAN_YU_506:明天
     * 2021-07-01 00:00:00 <= time <= 2021-7-01 23:59:59
     */
    public static final String YUAN_YU_506 = "506";

    /**
     * YUAN_YU_507:过去7天 (不包含今天)
     * 2021-6-23 00:00:00 <= time <= 2021-06-29:23:59:59
     */
    public static final String YUAN_YU_507 = "507";

    /**
     * YUAN_YU_508:过去30天 (不包含今天)
     * 2021-5-31 00:00:00 <= time <= 2021-06-29:23:59:59
     */
    public static final String YUAN_YU_508 = "508";

    /**
     * YUAN_YU_509:过去60天 (不包含今天)
     * 2021-5-01 00:00:00 <= time <= 2021-06-29 23:59:59
     */
    public static final String YUAN_YU_509 = "509";

    /**
     * YUAN_YU_510:过去90天 (不包含今天)
     * 2021-4-1 00:00:00 <= time <= 2021-06-29 23:59:59
     */
    public static final String YUAN_YU_510 = "510";

    /**
     * YUAN_YU_511:过去N天 N = 5 (不包含今天)
     * 2021-06-25 00:00:00 <= time <= 2020-06-29 23:59:59
     */
    public static final String YUAN_YU_511 = "511";

    /**
     * YUAN_YU_512:未来7天 (不包含今天)
     * 2021-07-01 00:00:00 <= time <= 2021-7-07 23:59:59
     */
    public static final String YUAN_YU_512 = "512";

    /**
     * YUAN_YU_513:未来30天 (不包含今天)
     * 2021-07-01 00:00:00 <= time <= 2021-7-30 23:59:59
     */
    public static final String YUAN_YU_513 = "513";

    /**
     * YUAN_YU_514:未来60天 (不包含今天)
     * 2021-07-01 00:00:00 <= time <= 2021-8-29 23:59:59
     */
    public static final String YUAN_YU_514 = "514";

    /**
     * YUAN_YU_515:未来90天 (不包含今天)
     * 2021-07-01 00:00:00 <= time <= 2021-9-28 23:59:59
     */
    public static final String YUAN_YU_515 = "515";

    /**
     * YUAN_YU_516:未来N天 N = 7 (不包含今天)
     * 2021-07-01 00:00:00 <= time <= 2021-7-07 23:59:59
     */
    public static final String YUAN_YU_516 = "516";

    /**
     * YUAN_YU_517:上周
     * 2021-06-21 00:00:00 <= time <= 2021-06-27 23:59:59
     */
    public static final String YUAN_YU_517 = "517";

    /**
     * YUAN_YU_518:本周
     * 2021-06-28 00:00:00 <= time <= 2021-07-04 23:59:59
     */
    public static final String YUAN_YU_518 = "518";

    /**
     * YUAN_YU_519:下周
     * 2021-07-05 00:00:00 <= time <= 2021-07-11 23:59:59
     */
    public static final String YUAN_YU_519 = "519";

    /**
     * YUAN_YU_520:本周及上周
     * 2021-06-21 00:00:00 <= time <= 2021-07-04 23:59:59
     */
    public static final String YUAN_YU_520 = "520";

    /**
     * YUAN_YU_521:本周及下周
     * 2021-06-28 00:00:00 <= time <= 2021-07-11 23:59:59
     */
    public static final String YUAN_YU_521 = "521";

    /**
     * YUAN_YU_522:过去N周 N = 2 (不包含本周)
     * 2021-06-14 00:00:00 <= time <= 2021-06-27 23:59:59
     */
    public static final String YUAN_YU_522 = "522";

    /**
     * YUAN_YU_523:未来N周 N = 2 (不包含本周)
     * 2021-07-05 00:00:00 <= time <= 2021-07-18 23:59:59
     */
    public static final String YUAN_YU_523 = "523";

    /**
     * YUAN_YU_524:上月(上个完整月份)
     * 2020-05-01 00:00:00 <= time <= 2021-05-31 23:59:59
     */
    public static final String YUAN_YU_524 = "524";

    /**
     * YUAN_YU_525:本月
     * 2020-06-01 00:00:00 <= time <= 2021-06-30 23:59:59
     */
    public static final String YUAN_YU_525 = "525";

    /**
     * YUAN_YU_526:本月和上月
     * 2020-05-01 00:00:00 <= time <= 2021-06-30 23:59:59
     */
    public static final String YUAN_YU_526 = "526";

    /**
     * YUAN_YU_527:本月和下月
     * 2020-06-01 00:00:00 <= time <= 2021-07-31 23:59:59
     */
    public static final String YUAN_YU_527 = "527";

    /**
     * YUAN_YU_528:过去N月 N = 2 (不包含本月)
     * 2020-04-01 00:00:00 <= time <= 2021-05-31 23:59:59
     */
    public static final String YUAN_YU_528 = "528";

    /**
     * YUAN_YU_529:未来N月 N = 2 (不包含本月)
     * 2020-07-01 00:00:00 <= time <= 2021-08-31 23:59:59
     */
    public static final String YUAN_YU_529 = "529";

    /**
     * YUAN_YU_530:去年
     * 2020-01-01 00:00:00 <= time <= 2020-12-31 23:59:59
     */
    public static final String YUAN_YU_530 = "530";

    /**
     * YUAN_YU_531:今年
     * 2021-01-01 00:00:00 <= time <= 2021-12-31 23:59:59
     */
    public static final String YUAN_YU_531 = "531";

    /**
     * YUAN_YU_532:明年
     * 2022-01-01 00:00:00 <= time <= 2022-12-31 23:59:59
     */
    public static final String YUAN_YU_532 = "532";

    /**
     * YUAN_YU_533:过去N年 N = 3 (不包含本年)
     * 2018-01-01 00:00:00 <= time <= 2020-12-31 23:59:59
     */
    public static final String YUAN_YU_533 = "533";

    /**
     * YUAN_YU_534:未来N年 N = 3 (不包含本年)
     * 2022-01-01 00:00:00 <= time <= 2024-12-31 23:59:59
     */
    public static final String YUAN_YU_534 = "534";

    /**
     * YUAN_YU_535:指定日期的当天
     * 2022-01-01 00:00:00 <= time <= 2022-01-01 23:59:59
     */
    public static final String YUAN_YU_535 = "535";

}
