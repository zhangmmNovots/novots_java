package com.weilaios.iqxceqhnhubt.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 流程工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class ProcessUtil {

    protected  static final Logger log = LoggerFactory.getLogger(ProcessUtil.class);
    /**
     * ker   解析获得开始流程
     * @param processJson
     */
    public static String startAtHandleGet(String processJson){
        return JsonPath.read(processJson, "$.StartAt").toString();
    }

    /**
     * ker   解析获得下一步的流程
     * @param processJson
     */
    public static String nextHandleGet(String processJson){
        return JsonPath.read(processJson, "$.Next").toString();
    }

    /**
     * ker   解析获取单个流程全部过程
     * @param processJson
     */
    public static String methodHandleGet(String processJson,String statesName){
        Map state  = JsonPath.read(processJson, "$.States."+statesName);
        JSONObject json = new JSONObject(state);
        return json.toString();
    }

    /**
     * ker   修改参数 路径必须存在
     * @param paramsJson
     * @param path
     * @param value
     */
    public static String jsonParamsEdit(String paramsJson,String path, String value){
        return JsonPath.parse(paramsJson).set(path, value).jsonString();
    }

    /**
     * ker   新增参数 路径存在为修改，路径不存在为新增
     * @param paramsJson
     * @param path
     * @param value
     */
    public static String jsonParamsAdd(String paramsJson,String path, String key, Object value){
        return JsonPath.parse(paramsJson).put(path,key,value).jsonString();
    }

    //转换request中的请求参数
    public static Map getParameterMap(Map properties) {
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    if (values[i].equals("null")) {
                        value = ",";
                    } else {
                        value = values[i] + ",";
                    }
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
                if (value.equals("null")) {
                    value = "";
                }
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * 校验参数是否允许为空
     * @param nullable --是否必填：1-必填 0-非必填
     * @param paramsDesc --参数描述
     * @param paramsValue --参数值
     * @param taskName --流程节点名称
     * @author 
     * @date
     * @return void
     */
    public static void checkParamNullable(String nullable, String paramsDesc, Object paramsValue, String taskName) throws Exception {

        if ("1".equals(nullable)) {
            //必填
            if (null == paramsValue || "".equals(paramsValue)) {
                if (StringUtils.isNotBlank(taskName)) {
                    throw new Exception("操作异常：参数" + paramsDesc + "不能为空" + "[" + taskName + "]");
                } else {
                    throw new Exception("操作异常：参数" + paramsDesc + "不能为空");
                }
            }
        }
    }

    /**
     * 校验参数类型是否与参数值匹配
     * @param dataType --参数的数据类型：见参数展示类型文件
     * @param paramsDesc --参数描述
     * @param paramsValue --参数值
     * @param taskName --流程节点名称
     * @author 
     * @date
     * @return void
     */
    public static void checkParamDataType(String dataType, String paramsDesc, Object paramsValue, String taskName) throws Exception {

        //校验参数类型
        if (!StringUtils.isEmpty(dataType) && null != paramsValue && !"".equals(paramsValue)) {

            switch (dataType) {
                case "Mobile":{
                    //校验手机号类型
                    //boolean flag = BeeUtils.checkMobileNumber(String.valueOf(paramsValue));
                    boolean flag = true;
                    if (!flag) {
                        String errorMsg = "";
                        if (!StringUtils.isEmpty(taskName)) {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确" + "[" + taskName + "]";
                        } else {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确";
                        }
                        throw new Exception(errorMsg);
                    }
                    break;
                }
                case "Email":{
                    //校验邮箱类型
                    //boolean flag = BeeUtils.checkEmail(String.valueOf(paramsValue));
                    boolean flag = true;
                    if (!flag) {
                        String errorMsg = "";
                        if (!StringUtils.isEmpty(taskName)) {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确" + "[" + taskName + "]";
                        } else {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确";
                        }
                        throw new Exception(errorMsg);
                    }
                    break;
                }

                case "Integer":{
                    //校验邮箱类型
                    Pattern pattern = Pattern
                            .compile("^(([1-9]{0}\\d{0,12})(\\.(\\d){1,6})?)|(0\\.0[1-9]{1,5})|(0\\.[1-9]{1,5}(\\d{1,5})?)$"); // 判断小数点后2位的数字的正则表达式
                    Matcher match = pattern.matcher(String.valueOf(paramsValue));
                    boolean flag = match.matches();
                    if (!flag) {
                        String errorMsg = "";
                        if (!StringUtils.isEmpty(taskName)) {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确" + "[" + taskName + "]";
                        } else {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确";
                        }
                        throw new Exception(errorMsg);
                    }
                    break;
                }

                case "Amount":{
                    //校验邮箱类型
                    Pattern pattern = Pattern
                            .compile("^(([1-9]{0}\\d{0,12})(\\.(\\d){1,6})?)|(0\\.0[1-9]{1,5})|(0\\.[1-9]{1,5}(\\d{1,5})?)$"); // 判断小数点后2位的数字的正则表达式
                    Matcher match = pattern.matcher(String.valueOf(paramsValue));
                    boolean flag = match.matches();
                    if (!flag) {
                        String errorMsg = "";
                        if (!StringUtils.isEmpty(taskName)) {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确" + "[" + taskName + "]";
                        } else {
                            errorMsg = "操作异常：参数" + paramsDesc + "格式不正确";
                        }
                        throw new Exception(errorMsg);
                    }
                    break;
                }
                default:{
                    break;
                }
            }

        }
    }

    /**
    * 校验流程入参
    * @param params
    * @param pageJsonRule
    * @throws BusinessException
    */
    public static void checkProcessParams(String params,String pageJsonRule) throws BusinessException {
        try {
            //获取请求参数列表
            List<Map> paramList = JsonPath.parse(pageJsonRule).read("$.request.*");
            //判断是否有请求参数：如果有请求参数，则判断是否为空，判断参数类型是否匹配
            if (null != paramList && paramList.size() > 0) {

                Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
                DocumentContext dataJsonDocument = JsonPath.using(conf).parse(params);
                for (Map map : paramList) {

                    //获取参数名
                    String paramsName = String.valueOf(map.get("paramsName"));
                    //判断参数名是否为空，如果为空，则不处理，不为空，则处理
                    if(org.apache.commons.lang3.StringUtils.isNotBlank(paramsName)){
                        //校验参数是否必填：1-必填 0-非必填
                        String requireFlag = String.valueOf(map.get("requireFlag"));
                        //参数描述
                        String paramsExplain = String.valueOf(map.get("paramsExplain"));
                        //获取参数值
                        Object value = dataJsonDocument.read("$.data." + paramsName);

                        ProcessUtil.checkParamNullable(requireFlag, paramsExplain, value, null);

                        //参数类型
                        String paramsType = String.valueOf(map.get("paramsType"));
                        ProcessUtil.checkParamDataType(paramsType, paramsExplain, value, null);
                    }
                }
            }

        } catch (BusinessException e) {
            log.error("校验异常",e);
            throw e;
        } catch (Exception e) {
            log.error("校验异常",e);
            throw new BusinessException("请求参数校验失败", e);
        }
    }

    /**
     * 转换类型
     * @param value
     * @param type
     * @author 
     * @date
     * @return java.lang.Object
     */
    public static Object transValueType(Object value, String type) {
        if (null == value || "".equals(value)) {
            return null;
        }

        if (Constants.DM_DATA_TYPE_AMOUNT.equals(type)
                || Constants.DM_DATA_TYPE_DECIMAL.equals(type)
                || Constants.DM_DATA_TYPE_DOUBLE.equals(type)
        ) {
            if (null!=value && !"".equals(value)) {

                if (value instanceof List || value.getClass().isArray()) {

                    if (value.getClass().isArray()) {
                        value= Arrays.asList(value);
                    }

                    if (value instanceof List) {
                        List temp = new ArrayList();
                        ((List) value).forEach(a -> {
                            if (null!=a) {
                                temp.add(getBigDecimal(a));
                            }
                        });
                        value = temp;
                    }

                } else {

                    value = getBigDecimal(value);
                }
            }

        } else if (Constants.DM_DATA_TYPE_INTEGER.equals(type)) {
            if (null!=value && !"".equals(value)) {
                //整形类型转换
                if (value instanceof List || value.getClass().isArray()) {
                    if (value.getClass().isArray()) {
                        value=Arrays.asList(value);
                    }

                    if (value instanceof List) {
                        List temp = new ArrayList();
                        ((List) value).forEach(a -> {
                            if (null!=a) {
                                temp.add(Double.valueOf(String.valueOf(a)).intValue());
                            }
                        });
                        value = temp;
                    }
                } else {
                    if (!(value instanceof Integer)) {

                        value = Double.valueOf(String.valueOf(value)).intValue();
                    }
                }
            }
        } else if (Constants.DM_DATA_TYPE_BOOLEAN.equals(type)) {
            value = Boolean.valueOf(value.toString());
        }
        return value;
    }

    public static String getTokenByRequest(HttpServletRequest request){
        String token = null;
        //先从header中获取token
        token = request.getHeader("TOKEN");
        //如果header中不存在，则从请求参数中获取
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("TOKEN");
        }
        return token;
    }

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null && !"".equals(value)) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
                        + " into a BigDecimal.");
            }
        }
        return ret;
    }

    /**
     * 判断字符串是不是json格式
     * @param content
     * @return
     */
    public static boolean isJson(Object content) {
        try {
            if (null == content) {
                return false;
            }
            boolean isJsonObject = false;
            try {
                isJsonObject = true;
            } catch (Exception e) {

            }
            JSON.toJSON(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 通过url地址获取文件的byte格式
     * @param path
     * @return
     */
    public static Map<String, Object> readNetFile(String path) {
        InputStream inputFile = null;
        try {
            URL url = new URL(path); // 创建URL
            URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码
            urlconn.setReadTimeout(50000);
            urlconn.connect();
            HttpURLConnection httpconn = (HttpURLConnection) urlconn;
            int httpResult = httpconn.getResponseCode();
            if (httpResult != HttpURLConnection.HTTP_OK) // 不等于HTTP_OK说明连接不成功
                log.error("无法连接到");
            else {
                inputFile = urlconn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];

                int len = 0;
                while ((len = inputFile.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                inputFile.close();
                byte[] fileData = outStream.toByteArray();
                outStream.flush();
                outStream.close();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("contentType", httpconn.getContentType());
                map.put("byteFile", fileData);

                return map;
            }
        } catch (Exception e) {
            log.error("解析异常", e);
            return null;

        } finally {
            if (inputFile != null)
                try {
                    inputFile.close();
                } catch (IOException e) {
                    log.error("解析异常", e);
                    return null;

                }

        }
        return null;
    }

    /**
     * 替换内容中的占位符
     * @param model
     * @param content
     * @return
     */
    public static String handleReplace(Map model,String content){

        VelocityEngine engine = new VelocityEngine();
        VelocityContext vc = new VelocityContext(model);
        StringWriter sw = new StringWriter();
        engine.evaluate(vc, sw, "", content);
        return sw.toString();
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b
     *            字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message
     *            消息
     * @param secret
     *            秘钥
     * @return 加密后字符串
     */
    public static String encryptHmacSHA(String message, String secret,String encryptType) {
        String hash = "";
        try {
            Mac mac = Mac.getInstance(encryptType);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), encryptType);
            mac.init(secret_key);
            byte[] bytes = mac.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            log.error("Error HmacSHA1 ===========" + e.getMessage(), e);
        }
        return hash;
    }

    /**
     * 判断返回值是否为文件输出
     * @param value
     * @return
     */
    public static boolean checkReturnFile(Object value) {
        boolean isReturnFile = false;
        try {
            //判断存储的文件是否为文件下载节点输出的数据：文件下载节点输出的数据格式为json格式，且存在特殊标识符 DOWN_FILE_FLAG_KEY
            if (null != value && !"".equals(value) && value instanceof Map) {
                Map jsonObject = (HashMap) value;
                if (jsonObject.containsKey(Constants.DOWN_FILE_FLAG_KEY)
                        && jsonObject.get(Constants.DOWN_FILE_FLAG_KEY).equals(Constants.DOWN_FILE_FLAG_VALUE_1)) {
                    isReturnFile = true;
                }
            }
        } catch (Exception e) {
            log.error("操作异常", e);
        }
        return isReturnFile;
    }

    public static String getFileUrl(Object filePath, String errorSuffix) throws BusinessException {
        try {
            String uploadFile = null;
            //判断上传文件地址的类型：单个字符串格式或者数组-[{"url":"https:...","title":"file name"}]
            if (filePath instanceof List) {
                //如果是数组格式，则获取第一个上传的文件地址
                List<Map> uploadFileList = (List<Map>) filePath;
                if (null != uploadFileList && uploadFileList.size() > 0) {
                    uploadFile = String.valueOf(uploadFileList.get(0).get("url"));
                }
            } else if (filePath instanceof String) {
                //如果不是数组格式，则判断是否为数组格式字符串或者单纯的url字符串
                boolean isArray = false;
                try {
                    Object file = JSON.parse(String.valueOf(filePath));
                    if (file instanceof List) {
                        //如果是数组格式，则获取第一个上传的文件地址
                        List<Map> uploadFileList = (List<Map>) file;
                        if (null != uploadFileList && uploadFileList.size() > 0) {
                            uploadFile = String.valueOf(uploadFileList.get(0).get("url"));
                        }
                        isArray = true;
                    }
                } catch (Exception e) {

                }
                if (!isArray) {
                    uploadFile = String.valueOf(filePath);
                }
            } else {
                uploadFile = String.valueOf(filePath);
            }
            return uploadFile;
        }  catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("文件解析失败" + errorSuffix, e);
        }
    }

    public static byte[] getFileByteByUrl(String fileUrl, String errorSuffix) throws BusinessException {
        try {
            URL url = new URL(fileUrl);
            DataInputStream inputStream = new DataInputStream(url.openStream());
            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
            byte b[] = bos.toByteArray();
            return b;
        }  catch (Exception e) {
            log.error("处理失败", e);
            throw new BusinessException("文件解析失败" + errorSuffix, e);
        }
    }

    /**
     * md5加密
     * @param encryptType
     * @param encryptContent
     * @param errorSuffix
     * @return
     * @throws BusinessException
     */
    public static String handleMd5Encode(String encryptType, Object encryptContent, String errorSuffix) throws BusinessException {
        try {
            //encryptType 加密格式：1-16位 2-16位小写  3-32位大写  4- 32位小写
            //md5 32位小写
            String md5Value = CipherUtil.encryptMD5(String.valueOf(encryptContent), true);
            //判断数据解析方式
            if ("1".equals(encryptType)) {
                md5Value = md5Value.substring(8, 24).toUpperCase();
            } else if ("2".equals(encryptType)) {
                md5Value = md5Value.substring(8, 24);
            } else if ("3".equals(encryptType)) {
                md5Value = md5Value.toUpperCase();
            }
            return md5Value;
        } catch (Exception e) {
            log.error("加密失败", e);
            throw new BusinessException("加密失败" + errorSuffix, e);
        }
    }

    public static Object handleAnalysisJsonByJson(Object analysisContent, JSONArray bindParams, String errorSuffix) throws BusinessException {
        try {
            Object getJson = null;
            try {
                if (analysisContent instanceof String) {
                    boolean isJson = false;
                    try {
                        getJson = JSON.parseObject((String) analysisContent);
                        isJson = true;
                    } catch (Exception e) {
                        log.debug("捕获异常");
                    }
                    //兼容获取到的数据为数组的
                    try {
                        getJson = JSON.parseArray((String) analysisContent);
                        isJson = true;
                    } catch (Exception e) {
                        log.debug("捕获异常");
                    }
                    if (!isJson) {
                        throw new BusinessException("操作异常：解析的数据非json格式" + errorSuffix);
                    }
                } else if (analysisContent instanceof Map) {
                    getJson = new JSONObject((Map) analysisContent);
                } else if (analysisContent instanceof List) {
                    getJson = new JSONArray((List<Object>) analysisContent);
                } else {
                    getJson = (JSONObject) analysisContent;
                }
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                throw new BusinessException("操作异常：解析的数据非json格式" + errorSuffix);
            }

            return getJson;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("解析json处理失败" + errorSuffix, e);
        }
    }

    public static Object handleDownDataByDataType(Object columnValue, String data_type) {
        try {
            if (null == columnValue || "".equals(columnValue)) {
                return null;
            }
            if (Constants.DM_DATA_TYPE_FILE.equals(data_type)
                    || Constants.DM_DATA_TYPE_IMAGE.equals(data_type)) {
                //文件类型返回数据处理
                if (columnValue instanceof List) {
                    List<Map> uploadFileList = (List<Map>) JSON.toJSON(columnValue);
                    if (null != uploadFileList && uploadFileList.size() > 0) {
                        String urls = "";
                        for (int i = 0; i < uploadFileList.size(); i++) {

                            Map urlMap = uploadFileList.get(i);
                            urls += urlMap.get("url") + "";
                            if (i + 1 != uploadFileList.size()) {
                                urls = urls + ",";
                            }
                        }
                        columnValue = urls;
                    }
                }
            } else if (Constants.DM_DATA_TYPE_OPTION.equals(data_type)) {
                //选项集类型返回数据处理
                //如果是选项集类型的，则特殊处理
                try {
                    //如果是集合数据的话，特殊处理：lookup获取到的数据可能存在多条数据
                    if (columnValue instanceof List) {
                        columnValue = ((List<?>) columnValue)
                                .stream()
                                .map(mm -> (JSON.parseObject(JSONObject.toJSONString(mm))).getString("fields"))
                                .collect(Collectors.toList());
                    } else {
                        //非数组的话，则当一条数据来处理
                        if (columnValue instanceof Map) {
                            JSONObject vjson = JSON.parseObject(JSON.toJSONString(columnValue));
                            columnValue = vjson.get("fields");
                        }
                    }
                } catch (Exception e) {
                    //log.info("variableValue兼容获取选项集类型值", e);
                }
            } else if (Constants.DM_DATA_TYPE_ADDRESS.equals(data_type)){
                //地址类型：地址存储的是数组格式：需要区分是数组格式存储的地址，还是地址文本格式
                if (columnValue instanceof List) {
                    columnValue = ((List<?>) columnValue)
                            .stream()
                            .map(mm -> (JSON.parseObject(JSONObject.toJSONString(mm))).getString("name"))
                            .collect(Collectors.toList());
                    List<String> addressList = ((List<String>) columnValue);
                    String address = "";
                    for (int i = 0; i < addressList.size(); i++) {
                        address += addressList.get(i);
                    }
                    columnValue = address;
                }
            } else if (Constants.DM_DATA_TYPE_DATE_TIME.equals(data_type)) {
                //时间格式
                Long timeLong = Long.parseLong(String.valueOf(columnValue));
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date date = sdf.parse(sdf.format(timeLong));
                columnValue = sdf.format(date);
            }
        } catch (Exception e) {
        }
        return columnValue;
    }

    public static String encryptDES(String message, String secret, String encryptType) throws BusinessException {
        try {
            SecretKey secretKey = getSecretKey(secret, encryptType);
            Cipher cipher = Cipher.getInstance(encryptType + "/ECB/PKCS5Padding");
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            // 加密，结果保存进cipherByte
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(bytes);
        } catch (Exception e) {
            log.error("加密失败", e);
            throw new BusinessException("加密失败");
        }
    }

    protected static SecretKey getSecretKey(String key, String encryptType) {
        try {
            KeySpec keySpec = null;
            if (Constants.KEY_DES.equals(encryptType)) {
                keySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            } else if (Constants.KEY_AES.equals(encryptType)) {
                return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), encryptType);
            } else if (Constants.KEY_3DES.equals(encryptType)) {
                keySpec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
            }

            SecretKeyFactory factory = SecretKeyFactory.getInstance(encryptType);
            SecretKey secretKey = factory.generateSecret(keySpec);
            return secretKey;
        } catch (Exception e) {
            log.error("失败", e);
            throw new BusinessException("ErrorCodeEnum.ERROR_10034");
        }
    }

    public static String decryptDES(String message, String secret, String encryptType) throws BusinessException {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            SecretKey secretKey = getSecretKey(secret, encryptType);
            Cipher cipher = Cipher.getInstance(encryptType + "/ECB/PKCS5Padding");
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(decoder.decodeBuffer(message));
            // 加密，结果保存进cipherByte
            return new String(bytes);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new BusinessException("解密失败");
        }
    }

    public static String encryptRAS(String message, String secret, String encryptType) throws BusinessException {
        try {
            //获取公钥
            byte[] decoded = Base64.getDecoder().decode(secret);
            KeyFactory keyFactory = KeyFactory.getInstance(encryptType);
            Key secretKey = keyFactory.generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = new NullCipher();
            if (encryptType.equals(Constants.KEY_RSA)) {
                cipher =  Cipher.getInstance(encryptType);
            }
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
            // 加密，结果保存进cipherByte
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error("加密失败", e);
            throw new BusinessException("加密失败");
        }
    }

    public static String decryptRAS(String message, String secret, String encryptType) throws BusinessException {
        try {
            byte[] decoded = Base64.getDecoder().decode(secret);
            KeyFactory keyFactory = KeyFactory.getInstance(encryptType);
            Key secretKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = new NullCipher();
            if (encryptType.equals(Constants.KEY_RSA)) {
                cipher =  Cipher.getInstance(encryptType);
            }
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(message));
            // 加密，结果保存进cipherByte
            return new String(bytes);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new BusinessException("解密失败");
        }
    }

    /***
     * 下划线转驼峰 & 首字母大写
     * @param str 字符串
     * @return 首字母大写驼峰
     */
    public static String underlineToCamelFirstWordUpper(String str) {
        String toCamel = com.baomidou.mybatisplus.core.toolkit.StringUtils.underlineToCamel(str);

        char[] chars = toCamel.toCharArray();
        chars[0] -= 32;
        String upperModelName = String.valueOf(chars);
        return upperModelName;
    }

    /**
     * 根据规则生成随机字符串
     * @param totalLength 长度
     * @param rule ：生成规则(多选)：1-数字 2-小写字母 3-大写字母
     * @param isDistinct ：是否去重
     * @return
     * @throws BusinessException
     */
    public static String randomByRule(Integer totalLength, int[] rule, boolean isDistinct) throws BusinessException {
        StringBuilder sb = new StringBuilder();
        //产生16位的强随机数
        Random rd = new Random();
        int randomLength = 0;
        while (randomLength < totalLength) {
            //产生0-2的3位随机数
            int index = rd.nextInt(rule.length);
            int type = rule[index];
            Object str = null;
            switch (type){
                case 1:
                    //0-9的随机数
                    str = String.valueOf(rd.nextInt(10));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    str = (char) (rd.nextInt(25) + 97);
                    break;
                case 3:
                    //ASCII在65-90之间为大写,获取大写随机
                    str = (char) (rd.nextInt(25) + 65);
                    break;
                default:
                    break;
            }
            if (null!=str && (!isDistinct || (isDistinct && !sb.toString().contains(String.valueOf(str))))) {
                sb.append(str);
                randomLength++;
            }
        }
        return sb.toString();
    }
}
