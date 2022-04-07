package com.weilaios.iqxceqhnhubt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (WlosOptionDetail)选项集子集实体
 *
 * @author jyh
 * @since 2021-04-15 16:11:30
 */
public class WlosOptionDetail implements Serializable {
    private static final long serialVersionUID = -24735665121854036L;
    //英文名称   编码    描述
    private String uuid;
    /**
     * 子集名称
     */
    private String name;

    /***
     * 编号
     */
    private String serialNumber;

    /**
     * 子集英文名称
     */
    private String enName;

    /**
     * 编码
     */
    private String charset;

    /**
     * 子集英文名称
     */
    private String description;

    /**
     * 选项集id
     */
    private String setUuid;
    /**
     * 父集id
     */
    private String parentUuid;
    /**
     * 是否默认选中
     */
    private Integer optionDefault;
    /**
     * 子集排序
     */
    private Integer rank;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date updatedTime;
    /**
     * 删除标识
     */
    @JsonIgnore
    private Integer isDeleted;

    /***
     * 子集
     */
    @TableField(exist = false)
    private List<WlosOptionDetail> children;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetUuid() {
        return setUuid;
    }

    public void setSetUuid(String setUuid) {
        this.setUuid = setUuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getOptionDefault() {
        return optionDefault;
    }

    public void setOptionDefault(Integer optionDefault) {
        this.optionDefault = optionDefault;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<WlosOptionDetail> getchildren() {
        return children;
    }

    public void setchildren(List<WlosOptionDetail> children) {
        this.children = children;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


}