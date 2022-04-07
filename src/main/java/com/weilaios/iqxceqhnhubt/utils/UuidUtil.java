package com.weilaios.iqxceqhnhubt.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * uuid生成工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class UuidUtil {
    public UuidUtil() {
    }

    public static String get47UUID(String letter) {
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        Date date = new Date();
        return letter + uuid + date.getTime();
    }

    public static String get34UUID(String letter) {
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        return letter + uuid;
    }
    public static String get18RANID(String letter) {
        return letter + RandomStringUtils.randomAlphanumeric(16);
    }

    public static String getUNID(String letter) {
        return get18RANID(letter);
    }

    public static String getUNIDX(String letter, int num) {
        return letter + RandomStringUtils.randomAlphanumeric(num);
    }

    public static void main(String[] args) {
        System.out.println(getUNIDX("SP", 18));
    }
}