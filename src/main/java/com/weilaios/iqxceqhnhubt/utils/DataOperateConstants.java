package com.weilaios.iqxceqhnhubt.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @content : 数据操作常量类
 */
public interface DataOperateConstants {


    /***
     * 操作符
     */
    String EQ = "11";
    String NE = "10";
    String GTE = "111";
    String GT = "110";
    String LTE = "101";
    String LT = "100";
    String NONE = "201";
    String NOT_NONE = "200";
    String START_WITH = "300";
    String END_WITH = "301";
    String INCLUDE = "302";
    String NOT_INCLUDE = "303";
    String IN = "411";
    String NOT_IN = "401";

    String LOOKUP = "lookup";



    String RELATION_QUERY = "4";

    String OPERATOR_QUERY = "0";
    String OPERATOR_INSERT = "1";
    String OPERATOR_UPDATE = "2";
    String OPERATOR_DELETE = "3";
    String OPERATOR_CONDITION_QUERY = "4";


    String ONE_TO_ONE = "1";
    String ONE_TO_MANY = "2";
    String MANY_TO_ONE = "3";
    String MANY_TO_MANY = "4";


    String LEFT_TABLE = "kk12";
    String RIGHT_TABLE = "kk11";


   String TELEPHONE = "Telephone";
    String STRUCT = "Struct";
    String INTEGER = "Integer";
    String FILE = "File";
    String BOOLEAN = "Boolean";
    String ZIP_CODE = "ZipCode";
    String DATE_TIME = "Datetime";
    String YEAR = "Year";
    String JSON = "Json";
    String DOUBLE = "Double";
    String SIGNATURE = "Signature";
    String GEOMETRY = "Geometry";
    String URL = "URL";
    String RICH_TEXT = "RichText";
    String EMAIL = "Email";
    String AMOUNT = "Amount";
    String DATE = "Date";
    String FORMULA = "Formula";
    String HASHED_STRING = "HashedString";
    String BINARY = "Binary";
    String NULL = "Null";
    String ID_NUMBER = "IdNumber";
    String ADDRESS = "Address";
    String TIME = "Time";
    String OPTION = "Option";
    String LONG = "Long";
    String DECIMAL = "Decimal";
    String TEXT = "Text";
    String AUDIO = "Audio";
    String UUID = "Uuid";
    String MOBILE = "Mobile";
    String PERCENTAGE = "Percentage";
    String PRIMARYKEY = "Primarykey";
    String XML = "Xml";
    String STRING = "String";
    String PASSWORD = "Password";
    String IMAGE = "Image";
    String VIDEO = "Video";
    String CNAMOUNT = "CnAmount";
    String AUTO_INC = "Autoinc";
    String PARENT_NODE = "ParentNode";


    String INT_TYPE = "int";
    String DECIMAL_TYPE = "decimal";
    String DOUBLE_TYPE = "double";
    String LONG_TYPE = "long";
    String BOOLEAN_TYPE = "boolean";
    String DATE_TYPE = "date";
    String DATETIME_TYPE = "datetime";
    String TIMESTAMP_TYPE = "timestamp";
    String TIME_TYPE = "time";
    String VARCHAR_TYPE = "varchar";


 /**
     * 不拼接字段长度的集合
     */
    public static final List<String> NO_NEED_LENGTH = new ArrayList<>(Arrays.asList(
            "text",
            "datetime",
            "date",
            "time",
            "timestamp",
            "longtext",
            "tinytext",
            "mediumtext",
            "boolean"
    ));

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


    public static final ArrayList<String> RELATION_TABLE_ID_TEMP = new ArrayList(){{
        add("uuid");
        add("id");
    }};
}
