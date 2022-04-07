package com.weilaios.iqxceqhnhubt.exception;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import com.weilaios.iqxceqhnhubt.utils.Result;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author : jyh
 * @content : 统一异常处理
 * @since : 2021/3/22  13:16
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private Environment environment;
    @Value("${spring.application.name}")
    private String applicationName;

    /***
     * 未知异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> defaultErrorHandler(Exception e, HttpServletRequest request) {
        printErrorLog(e, request, CommonCode.FAIL.message());
        return Result.FAIL(CommonCode.FAIL.code(), CommonCode.FAIL.message());
    }


    /***
     * 空指针异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?>  nullPointerException(NullPointerException e, HttpServletRequest request) {
        printErrorLog(e, request, CommonCode.NPE_FAIL.message());
        return Result.FAIL(CommonCode.NPE_FAIL.code(), CommonCode.NPE_FAIL.message());
    }


    /***
     * 参数 校验
     * @param e 异常信息
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?>  illegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数,{}", e);
        return Result.FAIL(CommonCode.INVALID_PARAM.code(), e.getMessage());
    }

    /**
     * 捕获BusinessException此类异常
     * 扩展了自定义异常信息的捕捉，不限于CommonCode枚举类中的异常信息
     * result.setCode(businessException.resultCode.code());
     * result.setMsg(businessException.resultCode.message());
     *
     * @param businessException 异常信息
     * @param request           请求上下文
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?>  businessException(BusinessException businessException, HttpServletRequest request) {
        Result<?>  result = new Result();
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        String jsonBody = "";
        String method = request.getMethod();
        if (request != null && request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            jsonBody = StringUtils.toEncodedString(wrapper.getContentAsByteArray(), Charset.forName(wrapper.getCharacterEncoding()));
        }
        log.info("==========业务异常=======>请求地址：{},请求方式：[{}]参数：[{}]", request.getRequestURL(), method, jsonBody == "" ? paramMap : jsonBody);
        log.error("==============业务异常=======>，catch exception:{}", businessException.getMessage());
        if (businessException.resultCode != null) {
            result=  Result.FAIL(businessException.resultCode.code(),businessException.resultCode.message());
        } else {
            result= Result.FAIL(businessException.getErrorCode(),businessException.getErrorMsg());
        }
        return result;
    }


    /***
     *
     * 参数 校验
     * @param e 异常信息
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class, MissingServletRequestParameterException.class})
    public Result<?>  bindResultExceptionHandler(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }
        StringBuilder errorMessage = new StringBuilder();
        if (bindingResult != null) {
            bindingResult.getFieldErrors().parallelStream().forEach(ex -> errorMessage.append(ex.getDefaultMessage()).append("!"));
        }
        return Result.FAIL(CommonCode.INVALID_PARAM.code(), errorMessage.toString());
    }


    /***
     * 入参格式不正确异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public Result<?>  httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        return Result.FAIL(CommonCode.REQUEST_PARAM_TYPE_NOT_SUPPORTED.code(), CommonCode.REQUEST_PARAM_TYPE_NOT_SUPPORTED.message());
    }

    /***
     * 请求格式不正确异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Result<?>  httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return Result.FAIL(CommonCode.REQUEST_METHOD_NOT_SUPPORTED.code(), CommonCode.REQUEST_METHOD_NOT_SUPPORTED.message());
    }


    /***
     * 数据为正常返回，客户端问题，不归属异常
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {ClientAbortException.class})
    public Result clientAbortException(ClientAbortException e, HttpServletRequest request) {
        return Result.FAIL(CommonCode.BROKEN_PIPE.code(), CommonCode.BROKEN_PIPE.message());
    }


    /***
     * sql错误异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public Result dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        Throwable cause = e.getCause();
        log.error("数据库操作失败：{},{}", e.getMessage(), e);
        StringBuilder messageError = new StringBuilder();
        if (cause instanceof MysqlDataTruncation) {
            messageError.append("字段超出长度:[").append(cause.getMessage()).append("]");
        } else {
            messageError.append("数据库字段不匹配:[").append(cause.getMessage()).append("]");
        }
        return Result.FAIL(CommonCode.DATA_SOURCE_EXCEPTION.code(), messageError.toString());
    }


    /***
     * sql错误异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {UncategorizedSQLException.class})
    public Result uncategorizedSQLException(UncategorizedSQLException e, HttpServletRequest request) {
        Throwable cause = e.getCause();
        log.error("数据库操作失败：{},{}", e.getMessage(), e);
        StringBuilder messageError = new StringBuilder();
        messageError.append("数据库字段类型不匹配:[" + cause.getMessage() + "]");
        return Result.FAIL(CommonCode.DATA_SOURCE_EXCEPTION.code(), messageError.toString());
    }


    /***
     * sql错误异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {DuplicateKeyException.class})
    public Result duplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        Throwable cause = e.getCause();
        log.error("数据库操作失败：{},{}", e.getMessage(), e);
        StringBuilder messageError = new StringBuilder();
        messageError.append("主键冲突:[" + cause.getMessage() + "]");
        return Result.FAIL(CommonCode.DATA_SOURCE_EXCEPTION.code(), messageError.toString());
    }


    /***
     * sql错误异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {MyBatisSystemException.class})
    public Result tooManyResultsException(MyBatisSystemException e, HttpServletRequest request) {
        log.error("数据库操作失败：{},{}", e.getMessage(), e);
        StringBuilder messageError = new StringBuilder();
        Throwable cause = e.getCause();
        if (null != cause) {
            if (cause instanceof TooManyResultsException) {
                messageError.append("数据错误:[请清除无效错误数据]后重试");
            } else {
                messageError.append("数据错误:[" + cause.getMessage() + "]");
            }
        }
        return Result.FAIL(CommonCode.DATA_INCORRECT.code(), messageError.toString());
    }


    /***
     * sql错误异常处理
     * @param e 异常信息
     * @param request 请求上下文
     * @return
     */
    @ExceptionHandler(value = {BadSqlGrammarException.class})
    public Result badSqlGrammarException(BadSqlGrammarException e, HttpServletRequest request) {
        Throwable cause = e.getCause();
        log.error("数据库操作失败：{},{}", e.getMessage(), e);
        StringBuilder messageError = new StringBuilder();
        if (cause instanceof SQLSyntaxErrorException) {
            messageError.append("缺少字段，请同步表结构:[" + cause.getMessage() + "]");
        } else {
            messageError.append("数据库字段不匹配，请同步:[" + cause.getMessage() + "]");
        }
        return Result.FAIL(CommonCode.DATA_SOURCE_EXCEPTION.code(), messageError.toString());
    }


    /***
     * 获取当前环境
     * @return
     */
    public String getEnv() {
        String[] activeProfiles = environment.getActiveProfiles();
        String activeEnv = "";
        if (ArrayUtils.isEmpty(activeProfiles)) {
            activeEnv = "dev";
        } else {
            activeEnv = activeProfiles[0];
        }
        return activeEnv;
    }


    /***
     * 打印错误信息
     * @param e
     * @param request
     */
    private void printErrorLog(Exception e, HttpServletRequest request, String errorTitle) {
        String method = request.getMethod();
///        String token = request.getHeader("TOKEN");
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        String jsonBody = null;
        if (request != null && request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            jsonBody = StringUtils.toEncodedString(wrapper.getContentAsByteArray(), Charset.forName(wrapper.getCharacterEncoding()));
        }
        StringBuilder errorBuilder = new StringBuilder("【" + applicationName + "】");
        errorBuilder.append("\n ").append(errorTitle).append(" 【").append(getEnv()).append("】");
        errorBuilder.append("\n 请求地址：").append(request.getRequestURL());
        errorBuilder.append("\n 请求方式：[").append(method).append("]");
        errorBuilder.append("\n 请求时间：").append(DateUtil.now());
        /// 暂不打印token  输出错误信息过长
//        errorBuilder.append("\n TOKEN：[").append(token).append("]");
        errorBuilder.append("\n 请求参数：");
        if (StringUtils.isBlank(jsonBody)) errorBuilder.append("\n " + JSONUtil.toJsonStr(paramMap));
        else errorBuilder.append("\n " + jsonBody);
        String fullStackTrace = ExceptionUtils.getFullStackTrace(e);
        if (fullStackTrace.length() > 3000) {
            fullStackTrace = fullStackTrace.substring(0, 3000);
        }
        errorBuilder.append("\n 异常信息: " + fullStackTrace);
        log.error(errorBuilder.toString(), e);
        Map<String, Object> map = new HashMap<>(2);
        map.put("text", errorBuilder.toString());
        Map<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("msg_type", "text");
        resultMap.put("content", map);

    }
}
