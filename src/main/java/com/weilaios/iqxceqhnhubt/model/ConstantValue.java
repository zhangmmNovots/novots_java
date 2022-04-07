package com.weilaios.iqxceqhnhubt.model;

import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import javax.persistence.Id;

/**
 * (constantValue)实体类
 *
 * @author makejava
 * @since 2021-11-18 18:42:46
 */
public class ConstantValue implements Serializable {
    private static final long serialVersionUID = -60113935787057866L;
      /**
     * 主键
     */
    @Id
    @TableId
    private String uuid;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 项目id
     */
    private String projectUuid;
    /**
     * 英文名
     */
    private String enName;
    /**
     * 中文名
     */
    private String cnName;
    /**
     * 标识符
     */
    private String suffix;
    /**
     * 是否可编辑：1-不可编辑0-可编辑
     */
    private Integer isDefault;
    /**
     * mysql基本类型
     */
    private String baseType;
    /**
     * 平台内部类型
     */
    private String dataType;
    /**
     * 字段允许长度
     */
    private String length;
    /**
     * 小数保留位数
     */
    private Integer decimalNumber;
    /**
     * 地址类型 1- 省市区  2- 省市区-详细地址
     */
    private Integer addressFormat;
    /**
     * 日期类型的展示格式
     */
    private Integer dateFormat;
    /**
     * 日期默认写入方式  1-当前时间 2-指定时间
     */
    private Integer dateFillMethod;
    /**
     * 常量的值
     */
    private String value;
    /**
     * 描述
     */
    private String description;

    private String identifyId;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getDecimalNumber() {
        return decimalNumber;
    }

    public void setDecimalNumber(Integer decimalNumber) {
        this.decimalNumber = decimalNumber;
    }

    public Integer getAddressFormat() {
        return addressFormat;
    }

    public void setAddressFormat(Integer addressFormat) {
        this.addressFormat = addressFormat;
    }

    public Integer getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(Integer dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getDateFillMethod() {
        return dateFillMethod;
    }

    public void setDateFillMethod(Integer dateFillMethod) {
        this.dateFillMethod = dateFillMethod;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

}
