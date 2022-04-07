package com.weilaios.iqxceqhnhubt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (WlosOptionSet)选项集实体
 *
 * @author jyh
 * @since 2021-04-15 16:11:39
 */
public class WlosOptionSet implements Serializable {
    private static final long serialVersionUID = 681799023338956109L;
    //实体  英文名称  系统级别  是否开放  新增 enName  isOpen sysLevel
    /**
     * 主键id
     */
    private String uuid;
    /**
     * 项目id
     */
    private String projectUuid;
    /**
     * 选项集名称
     */
    private String name;

    /**
     * 选项集英文名称
     */
    private String enName;

    /**
     * 编码
     */
    private String charset;

    /**
     * 描述
     */
    private String description;

    /**
     * 选项集名称
     */
    private String isOpen;

    /**
     * 平台等级  用户custorm 平台platform
     *
     * @mbggenerated
     */
    private String sysLevel;

    /**
     * 选项集类型 0 - 自定义，1 - 标准
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改时间
     */
    private Date updatedTime;
    /**
     * 是否删除
     */
    @JsonIgnore
    private Integer isDeleted;

    /***
     * 选项集下属子集列表
     */
    @TableField(exist = false)
    private List<WlosOptionDetail> wlosOptionDetailList;

    //排序方式
    @TableField(exist = false)
    private String createTimeSort;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(String sysLevel) {
        this.sysLevel = sysLevel;
    }

    public List<WlosOptionDetail> getWlosOptionDetailList() {
        return wlosOptionDetailList;
    }

    public void setWlosOptionDetailList(List<WlosOptionDetail> wlosOptionDetailList) {
        this.wlosOptionDetailList = wlosOptionDetailList;
    }

    public String getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(String createTimeSort) {
        this.createTimeSort = createTimeSort;
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

}