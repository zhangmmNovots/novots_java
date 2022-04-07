package com.weilaios.iqxceqhnhubt.exception;

import javax.validation.Payload;

/**
 * @author : jyh
 * @content : 错误码及信息，定义类
 * @since : 2021/3/22  13:16
 */
public enum CommonCode implements ResultCodeArc, Payload {



    /***
     * 空指针异常
     */
    NPE_FAIL(10001, "系统繁忙,请稍后重试！"),

    /***
     * 未知异常
     */
    FAIL(10002, "系统繁忙,请稍后重试！"),

    /***
     * 参数非法
     */
    INVALID_PARAM(10003, "非法参数！"),

    /***
     * sql 错误异常
     */
    BAD_SQL_GRAMMAR(10004,"系统繁忙,请稍后重试！"),

    /***
     * 请求参数格式不匹配异常
     */
    REQUEST_PARAM_TYPE_NOT_SUPPORTED(10005,"请求参数格式不匹配！"),

    /***
     * 请求方式不匹配
     */
    REQUEST_METHOD_NOT_SUPPORTED(10006,"请求方式不匹配！"),


    /***
     * 管道破裂，数据未正常返回
     */
    BROKEN_PIPE(10007,"数据未正常响应，请等待！"),

    /***
     * 数据库sql数据异常
     */
    DATA_SOURCE_EXCEPTION(10008,"数据未正常响应，请等待！"),



    /***
     *
     */
    SERVICE_NOT_FOUND(10009,"数据未正常响应，请等待！"),





    /***
     *
     */
    DATA_INCORRECT(10010,"数据未正常响应，请等待！")





    ;

    /***
     * 响应码
     */
    Integer code;
    /***
     * 响应信息
     */
    String message;

    CommonCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "CommonCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
