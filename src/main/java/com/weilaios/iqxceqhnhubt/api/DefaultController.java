package com.weilaios.iqxceqhnhubt.api;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.weilaios.iqxceqhnhubt.exception.BusinessException;
import com.weilaios.iqxceqhnhubt.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 处理文件下载
 * @author
 * @date Apr 7, 2022 3:06:39 PM
 */
@RestController
@RequestMapping(Constants.REQUEST_MAPPING_PREFIX)
public class DefaultController {

    private transient Logger log = LoggerFactory.getLogger(getClass());


    public void downReturnFile(HttpServletResponse response, JSONObject returnDataJson, String paramsJson) throws BusinessException {
        //处理文件
        try {
            String downDataKeyName = returnDataJson.getString("system_flow_down_file_key_value_name");
            Map fileMap = returnDataJson.getJSONObject(downDataKeyName);

            if (null != fileMap && fileMap.entrySet().size() > 0) {

                //获取文件下载的类型来源：excel下载还是文件下载节点下载
                String downFileFrom = (String) fileMap.get("downFileFrom");
                String fileName = (String) fileMap.get("fileName");
                List dataList = (List) fileMap.get("dataList");

                if (Constants.DOWN_FILE_FROM_TYPE_EXCEL.equals(downFileFrom)) {
                    String fileType = (String) fileMap.get("fileType");
                    //excel下载
                    //获取下载文件的参数
                    List<String> headersObj = (List) fileMap.get("headers");
                    String[] headers = null;
                    if (null != headersObj) {
                        headers = headersObj.toArray(new String[headersObj.size()]);
                    }
                    List<String> columnObj = (List) fileMap.get("columns");
                    String[] columns = null;
                    if (null != columnObj) {
                        columns = columnObj.toArray(new String[columnObj.size()]);
                    }
                    //下载文件：判断下载的文件的类型：xls/csv/json
                    if (Constants.DOWN_DATA_FILE_TYPE_CSV.equals(fileType)) {
                        //csv文件
                        POIExcelUtil.createCsvByMap(StringUtils.isNotBlank(fileName) ? fileName : "未命名", dataList, response, headers, columns);
                    } else if (Constants.DOWN_DATA_FILE_TYPE_JSON.equals(fileType)) {
                        //json文件
                        POIExcelUtil.createJsonByMap(StringUtils.isNotBlank(fileName) ? fileName : "未命名", dataList, response, headers, columns);
                    } else {
                        //xls文件
                        POIExcelUtil.createExcelByMap(StringUtils.isNotBlank(fileName) ? fileName : "未命名", dataList, response, headers, columns);
                    }
                } else {
                    //文件下载
                    //判断文件下载的数量，如果是单个文件，则单个下载，如果是多个文件，则打压缩包下载
                    if (null != dataList && dataList.size() > 0) {
                        if (dataList.size() == 1) {
                            Object fileObj = dataList.get(0);
                            if (fileObj instanceof Map || (fileObj instanceof List && ((List) fileObj).size() == 1)) {
                                Map fileItem = null;
                                if (fileObj instanceof Map) {
                                    //单个文件下载
                                    fileItem = (Map) dataList.get(0);
                                } else if (fileObj instanceof List) {
                                    fileItem = (Map) ((List) fileObj).get(0);
                                }
                                String fileUrl = (String) fileItem.get("url");
                                String title = (String) fileItem.get("title");
                                URL url = new URL(fileUrl);
                                String name = fileName + fileUrl.substring(fileUrl.lastIndexOf("."));
                                InputStream inputStream = url.openConnection().getInputStream();
                                OutputStream out = null;
                                response.setContentType("application/octet-stream;charset=utf-8");
                                response.setHeader("Content-Disposition", "attachment; filename=" + name);
                                out = response.getOutputStream();
                                byte[] buffer = new byte[1024];
                                int r = 0;
                                while ((r = inputStream.read(buffer)) != -1) {
                                    out.write(buffer, 0, r);
                                }
                                inputStream.close();
                                out.flush();
                                out.close();
                            } else if (fileObj instanceof List) {
                                List fileList = (List) fileObj;
                                if (null != fileList && fileList.size() > 0) {
                                    //多文件下载，打压缩包
                                    String downloadFilename = fileName + "." + "zip";
                                    downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
                                    response.setContentType("application/octet-stream;charset=utf-8");
                                    response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
                                    ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
                                    for (int j = 0; j < fileList.size(); j++) {
                                        Object fileObjTwo = fileList.get(j);
                                        if (fileObjTwo instanceof Map) {
                                            Map fileItem = (Map) fileObjTwo;
                                            String fileUrl = (String) fileItem.get("url");
                                            String title = (String) fileItem.get("title");
                                            URL url = new URL(fileUrl);
                                            String name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                                            zos.putNextEntry(new ZipEntry(name));
                                            InputStream fis = url.openConnection().getInputStream();
                                            byte[] buffer = new byte[1024];
                                            int r = 0;
                                            while ((r = fis.read(buffer)) != -1) {
                                                zos.write(buffer, 0, r);
                                            }
                                            fis.close();
                                        }
                                    }
                                    zos.flush();
                                    zos.close();
                                }else {
                                    downNullFile(response);
                                }
                            }
                        } else {
                            //多文件下载，打压缩包
                            String downloadFilename = fileName + "." + "zip";
                            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
                            response.setContentType("application/octet-stream;charset=utf-8");
                            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);
                            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
                            for (int i = 0; i < dataList.size(); i++) {
                                Object fileObj = dataList.get(i);
                                if (fileObj instanceof Map) {
                                    Map fileItem = (Map) fileObj;
                                    String fileUrl = (String) fileItem.get("url");
                                    String title = (String) fileItem.get("title");
                                    URL url = new URL(fileUrl);
                                    String name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                                    zos.putNextEntry(new ZipEntry(name));
                                    InputStream fis = url.openConnection().getInputStream();
                                    byte[] buffer = new byte[1024];
                                    int r = 0;
                                    while ((r = fis.read(buffer)) != -1) {
                                        zos.write(buffer, 0, r);
                                    }
                                    fis.close();
                                } else if (fileObj instanceof List) {
                                    List fileList = (List) fileObj;
                                    for (int j = 0; j < fileList.size(); j++) {
                                        Object fileObjTwo = fileList.get(j);
                                        if (fileObjTwo instanceof Map) {
                                            Map fileItem = (Map) fileObjTwo;
                                            String fileUrl = (String) fileItem.get("url");
                                            String title = (String) fileItem.get("title");
                                            URL url = new URL(fileUrl);
                                            String name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                                            zos.putNextEntry(new ZipEntry(name));
                                            InputStream fis = url.openConnection().getInputStream();
                                            byte[] buffer = new byte[1024];
                                            int r = 0;
                                            while ((r = fis.read(buffer)) != -1) {
                                                zos.write(buffer, 0, r);
                                            }
                                            fis.close();
                                        }
                                    }
                                }
                            }
                            zos.flush();
                            zos.close();
                        }
                    } else {
                        downNullFile(response);
                    }
                }
            }
        } catch (Exception e) {
            log.error("=====流程执行结束获取下载数据异常", e);
            throw new BusinessException("文件下载异常", e);
        }
    }

    public void downNullFile(HttpServletResponse response) {
        try {
            log.info("======无文件下载，执行空文件下载");
            OutputStream out = null;
            String downloadFilename = URLEncoder.encode("无文件", "UTF-8");
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + downloadFilename);
            out = response.getOutputStream();
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("=====下载空文件失败", e);
        }
    }
}