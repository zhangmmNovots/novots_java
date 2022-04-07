package com.weilaios.iqxceqhnhubt.utils;

/**
 * 数据类型枚举
 */
public enum DataTypeEnum {
    STRING("字符串", "String"),
    UUID("UUID", "Uuid"),
    URL("URL", "Url"),
    EMAIL("电子邮箱", "Email"),
    INTEGER("整数", "Integer"),
    BOOLEAN("布尔值", "Boolean"),
    DATE("日期", "Date"),
    TIME("时间", "Time"),
    DATETIME("日期时间", "Datetime"),
    TIMESTAMP("时间戳", "Timestamp"),
    DOUBLE("小数", "Double"),
    AMOUNT("金额", "Amount"),
    IMAGE("一组图片", "Image"),
    FILE("一组文件", "File"),
    AUTOINC("自动编号", "Autoinc"),
    TELEPHONE("座机号", "Telephone"),
    MOBILE("手机号", "Mobile"),
    IDNUMBER("身份证号", "IdNumber"),
    ADDRESS("地址", "Address"),
    ZIPCODE("邮编", "ZipCode"),
    PERCENTAGE("百分比", "Percentage"),
    AUDIO("一组音频", "Audio"),
    VIDEO("一组视频", "Video"),
    TEXT("长文本", "Text"),
    RICHTEXT("富文本", "RichText"),
    GEOMETRY("经纬度", "Geometry"),
    OPTION("静态实体（枚举）", "Option"),
    FORMULA("公式", "Formula"),
    JSON("JSON", "Json"),
    YEAR("年", "Year"),
    PRIMARYKEY("主键", "Primarykey"),
    UNKNOWN("未知", "unknown");


    private String name;
    private String value;

    DataTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
