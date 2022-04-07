package com.weilaios.iqxceqhnhubt.utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * http请求工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class HttpUtils {

	protected static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	/**
     * http请求
     * @param url
     * @param paramsMap
     * @param headerMap
     * @param contentType
     * @param requestMethod
     * @return
     * @throws
     */
	public static String doHttp(String url, Map paramsMap, Map headerMap, String contentType, String requestMethod) throws BusinessException{
        JSONObject data = null;
        if (Constants.CONTENT_TYPE_APPLICATION_JSON.equals(contentType)) {
            if (null != paramsMap) {
                data = new JSONObject(paramsMap);
            }
        }
        if (requestMethod.toLowerCase().equals("get")) {
            if (Constants.CONTENT_TYPE_APPLICATION_JSON.equals(contentType)) {
                return HttpRequest.get(url).addHeaders(headerMap).body(data.toJSONString(), contentType).execute().body();
            } else {
                return HttpRequest.get(url).addHeaders(headerMap).form(paramsMap).execute().body();
            }
        } else if (requestMethod.toLowerCase().equals("put")) {
            if (Constants.CONTENT_TYPE_APPLICATION_JSON.equals(contentType)) {
                return HttpRequest.put(url).addHeaders(headerMap).body(data.toJSONString(), contentType).execute().body();
            } else {
                return HttpRequest.put(url).addHeaders(headerMap).form(paramsMap).execute().body();
            }
        } else if (requestMethod.toLowerCase().equals("delete")) {
            if (Constants.CONTENT_TYPE_APPLICATION_JSON.equals(contentType)) {
                return HttpRequest.delete(url).addHeaders(headerMap).body(data.toJSONString(), contentType).execute().body();
            } else {
                return HttpRequest.delete(url).addHeaders(headerMap).form(paramsMap).execute().body();
            }
        } else {
            if (Constants.CONTENT_TYPE_APPLICATION_JSON.equals(contentType)) {
                return HttpRequest.post(url).addHeaders(headerMap).body(data.toJSONString(), contentType).execute().body();
            } else {
                return HttpRequest.post(url).addHeaders(headerMap).form(paramsMap).execute().body();
            }
        }
    }
}
