package com.weilaios.iqxceqhnhubt.exception;

import org.apache.http.HttpStatus;

/**
* @content : 业务异常类
*/
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误编码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 消息是否为属性文件中的Key
     */
    private boolean propertiesKey = true;

    //错误代码
    public  ResultCodeArc resultCode;

    public BusinessException(ResultCodeArc resultCode){
        this.resultCode = resultCode;
    }
    public ResultCodeArc getResultCode(){
        return resultCode;
    }

    /**
     * 构造一个基本异常.
     * @param message       信息描述
     */
    public BusinessException(String message) {
        super(message);
        errorCode= HttpStatus.SC_INTERNAL_SERVER_ERROR;
        errorMsg=message;
    }
    /**
     * 构造一个基本异常.
     * @param errorCode     错误编码
     * @param message       信息描述
     */
    public BusinessException(Integer errorCode, String message) {
        this(errorCode, message, true);
    }

    /**
     * 构造一个基本异常.
     * @param errorCode     错误编码
     * @param message       信息描述
     * @param cause         根异常类（可以存入任何异常）
     */
    public BusinessException(Integer errorCode, String message, Throwable cause) {
        this(errorCode, message, cause, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode         错误编码
     * @param message           信息描述
     * @param propertiesKey     消息是否为属性文件中的Key
     */
    public BusinessException(Integer errorCode, String message, boolean propertiesKey) {
        super(message);
        this.setErrorMsg(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode         错误编码
     * @param message           信息描述
     * @param cause             根异常类（可以存入任何异常）
     * @param propertiesKey     消息是否为属性文件中的Key
     */
    public BusinessException(Integer errorCode, String message, Throwable cause, boolean propertiesKey) {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message       信息描述
     * @param cause         根异常类（可以存入任何异常）
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.setErrorMsg(message);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
