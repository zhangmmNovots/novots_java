package com.weilaios.iqxceqhnhubt.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

public class Entity  {
    /**
     * 主键
     *
     * @mbggenerated
     */
    @TableId
    private String uuid;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Timestamp createdTime;

    /**
     * 是否删除 0 否 1 是
     *
     * @mbggenerated
     */
    private String isDeleted;

    /**
     * 中文表名
     *
     * @mbggenerated
     */
    private String cnName;

    /**
     * 存储引擎  mongo
     *
     * @mbggenerated
     */
    private String dbEngine;

    /***
     * 选项集显示字段
     */
    private String displayField;

    /**
     * 存储  
     *
     * @mbggenerated
     */
    private String dbStore;

    /**
     * db版本 
     *
     * @mbggenerated
     */
    private String dbVersion;

    /**
     * 定义模式
     *
     * @mbggenerated
     */
    private Integer defineMode;

    /**
     * 描述
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 英文表名
     *
     * @mbggenerated
     */
    private String enName;

    /**
     * 是否是租户
     *
     * @mbggenerated
     */
    private Boolean isMultiTenant;

    /**
     * 是否公开 默认是true
     *
     * @mbggenerated
     */
    private Boolean isOpen;

    /**
     * 是否是模版 1 是 0 否
     *
     * @mbggenerated
     */
    private Integer isTemplate;

    /**
     * unique_flag
     *
     * @mbggenerated
     */
    private String projectPrefix;

    /**
     * 项目id
     *
     * @mbggenerated
     */
    private String projectUuid;

    /**
     * 存储类型
     *
     * @mbggenerated
     */
    private String storageType;

    /**
     * 平台等级  用户custorm 平台platform
     *
     * @mbggenerated
     */
    private String sysLevel;

    /**
     * 模版uuid
     *
     * @mbggenerated
     */
    private String templateUuid;

    /**
     * 租户id
     *
     * @mbggenerated
     */
    private String tenantUuid;

    /***
     * 字段标识
     */
    private String suffix;

    private String version;

    /***
     * 使用类型，0为普通实体，1为选项级实体,
     */
    private Integer usageType;

    /**
     * 关联数据库
     */
    private String associatedDatabase;

    /**
     * 创建方式
     */
    private Integer createMethod;
    private String icon;

    /**
     * 数据库表的名称
     */
    @TableField(exist = false)
    private String tableName;

    /**
     * 数据源名称
     */
    @TableField(exist = false)
    private String datasourceName;


    /**
     * 实体中文名，前端使用
     */
    @TableField(exist = false)
    private String model_name;

    /**
     * 所属分组的id
     */
    @TableField(exist = false)
    private String groupUuid;

    public String getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(String groupUuid) {
        this.groupUuid = groupUuid;
    }


    public Integer getUsageType() {
        return usageType;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getDbEngine() {
        return dbEngine;
    }

    public void setDbEngine(String dbEngine) {
        this.dbEngine = dbEngine;
    }

    public String getDbStore() {
        return dbStore;
    }

    public void setDbStore(String dbStore) {
        this.dbStore = dbStore;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Integer getDefineMode() {
        return defineMode;
    }

    public void setDefineMode(Integer defineMode) {
        this.defineMode = defineMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Boolean getMultiTenant() {
        return isMultiTenant;
    }

    public void setMultiTenant(Boolean multiTenant) {
        isMultiTenant = multiTenant;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Integer getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(Integer isTemplate) {
        this.isTemplate = isTemplate;
    }

    public String getProjectPrefix() {
        return projectPrefix;
    }

    public void setProjectPrefix(String projectPrefix) {
        this.projectPrefix = projectPrefix;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(String sysLevel) {
        this.sysLevel = sysLevel;
    }

    public String getTemplateUuid() {
        return templateUuid;
    }

    public void setTemplateUuid(String templateUuid) {
        this.templateUuid = templateUuid;
    }

    public String getTenantUuid() {
        return tenantUuid;
    }

    public void setTenantUuid(String tenantUuid) {
        this.tenantUuid = tenantUuid;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }


    public String getAssociatedDatabase() {
        return associatedDatabase;
    }

    public void setAssociatedDatabase(String associatedDatabase) {
        this.associatedDatabase = associatedDatabase;
    }

    public Integer getCreateMethod() {
        return createMethod;
    }

    public void setCreateMethod(Integer createMethod) {
        this.createMethod = createMethod;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }


    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}