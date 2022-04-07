package com.weilaios.iqxceqhnhubt.utils;

import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

/**
 * json处理工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class JsonUtils {

    public static String xml2jsonString(String xml) throws Exception {

        JSONObject json = null;
        try {
            json = XML.toJSONObject(xml);
            return json.toString();
        } catch (Exception e) {
            throw new Exception("xml格式数据转换失败");
        }
    }

    public static List object2List(Object object){
        if (null == object || "".equals(object)) {

            return new ArrayList();
        }
        List list = new ArrayList();
        if (object instanceof List) {
            list = (List) object;
        } else {
            list.add(object);
        }
        return list;
    }

}

