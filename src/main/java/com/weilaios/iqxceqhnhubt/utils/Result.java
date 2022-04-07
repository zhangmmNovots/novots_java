package com.weilaios.iqxceqhnhubt.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.NotNull;
import org.apache.http.HttpStatus;

import java.io.Serializable;

/**
 * 返回值工具类
 * @author
 * @date Apr 7, 2022 3:06:41 PM
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /***
     *    200 成功
     *   -1 token失效/需重新登录  附带msg 错误信息
     *   !200 系统异常/业务异常
     *   -3 跳转注册页
     */
    private int code;

    /***
     * 提示信息息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    /***
     * 提示信息信息
     */
    private String status;

    /***
     * 当前时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /***
     * 数据容器
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String exception;


    /**
     * 构造函数
     */
    public Result() {
    }

    /**
     * 构造函数
     *
     * @param data 数据
     */
    private Result(T data) {
        this.data = data;
    }

    @NotNull
    public static <T> Result<?> tokenInvalid() {
        Result<?> result = new Result();
        result.setStatus(Status.ERROR.getMsg());
        result.setCode(-1);
        result.setMsg("请登录后重试!");
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }


    /**
     * 成功
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> SUCCESS() {
        return new Result().success();
    }


    /**
     * 成功 自定义 Code & 提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> SUCCESS(Integer code, String message) {
        return new Result().success(code, message);
    }

    /**
     * 成功 返回结果
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> SUCCESS(T data) {
        return new Result(data).success();
    }

    /**
     * 成功 返回结果 & 自定义提示信息
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> SUCCESS(T data, String message) {
        return new Result(data).success(message);
    }

    /**
     * 失败
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> FAIL() {
        return new Result().failure();
    }

    /**
     * 失败 自定义提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> FAIL(String message) {
        return new Result().failure(message);
    }

    /**
     * 失败 自定义 Code & 提示信息
     *
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> FAIL(Integer code, String message) {
        return new Result().failure(code, message);
    }

    /**
     * 失败 返回结果
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> FAIL(T data) {
        return new Result(data).failure();
    }

    /**
     * 失败 返回结果 & 自定义提示信息
     *
     * @param data 返回结果
     * @return Response
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> FAIL(T data, String message) {
        return new Result(data).failure(message);
    }


    /**
     * 成功
     *
     * @return Response
     */
    private Result success() {
        this.status = Status.SUCCESS.getMsg();
        this.code = Code.OK.getCode();
        return this;
    }

    /**
     * 成功 自定义提示信息
     *
     * @param message 成功提示信息
     * @return Response
     */
    private Result success(String message) {
        this.status = Status.SUCCESS.getMsg();
        this.code = Code.OK.getCode();
        this.msg = message;
        return this;
    }

    /**
     * 成功 自定义提示信息
     *
     * @param code    Code
     * @param message 成功提示信息
     * @return Response
     */
    private Result success(int code, String message) {
        this.status = Status.SUCCESS.getMsg();
        this.code = code;
        this.timestamp = System.currentTimeMillis();
        this.msg = message;
        return this;
    }


    /**
     * 失败
     *
     * @return Response
     */
    private Result failure() {
        this.status = Status.ERROR.getMsg();
        this.code = Code.FAILURE.getCode();
        return this;
    }

    /**
     * 失败 自定义提示信息
     *
     * @param message 错误提示信息
     * @return Response
     */
    private Result failure(String message) {
        this.status = Status.ERROR.getMsg();
        this.code = Code.FAILURE.getCode();
        this.msg = message;
        return this;
    }

    /**
     * 失败 自定义提示信息
     *
     * @param code    Code
     * @param message 错误提示信息
     * @return Response
     */
    private Result failure(int code, String message) {
        this.status = Status.ERROR.getMsg();
        this.code = code;
        this.msg = message;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getException() {
        return this.exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public enum Code {
        OK(HttpStatus.SC_OK), FAILURE(HttpStatus.SC_INTERNAL_SERVER_ERROR), NotFound(HttpStatus.SC_NOT_FOUND);

        private int code;

        public int getCode() {
            return code;
        }

        Code(int code) {
            this.code = code;
        }
    }

    public enum Status {
        SUCCESS("success"), ERROR("error");

        private String msg;

        public String getMsg() {
            return msg;
        }

        Status(String code) {
            this.msg = code;
        }
    }
}
