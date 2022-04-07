package com.weilaios.iqxceqhnhubt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.weilaios.iqxceqhnhubt.model.FormulaSetting;

import com.weilaios.iqxceqhnhubt.model.AutoincFieldSetting;

/**
 * (WlosEntityField)实体类
 *
 * @author jyh
 * @since 2021-04-22 13:59:07
 */
public class EntityField implements Serializable {
    private static final long serialVersionUID = -57450023907909219L;

    @TableId
    private String uuid;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 是否删除-0 未删除 1 删除
     */
    private Integer isDeleted;
    /**
     * 地址类型 1- 省市区  2- 省市区-详细地址
     */
    private Integer addressFormat;
    /**
     * 金额单位   1-元 2-万元
     */
    private Integer amountUnit;
    /**
     * 基础类型
     */
    private String baseType;
    /**
     * 是否展示 1-展示 0-不展示
     */
    private Integer canDisplay;
    /**
     * 图片，文件， 音频、视频 是否允许上传多个  0 - 不允许 1-允许
     */
    private Integer canMultiUploads;
    private String icon;
    /**
     * 是否搜索 1-允许 0不允许
     */
    private Integer canSearch;
    /**
     * 中文名
     */
    private String cnName;

    @TableField(exist = false)
    private String entityCnName;
    @TableField(exist = false)
    private Integer displayField;
    /**
     * 字段分类 1-自定义 2-预设
     */
    private String conceptLevel;
    /**
     * 日期默认写入方式  1-当前时间 2-指定时间
     */
    private Integer dateFillMethod;
    /**
     * 4
     */
    private Integer dateFormat;
    /**
     * 金额字段保留位数
     */
    private Integer decimalNumber;
    /***
     * 字段标识符,后缀
     */
    private String suffix;

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 默认值
     */
    private Object defaultValue;
    /**
     * 描述
     */
    private String description;
    /**
     * 字段名称：自动生成
     */
    private String enName;
    /**
     * 实体英文名 实体自动生成
     */
    private String entityEnName;
    /**
     * 实体uuid
     */
    private String entityUuid;
    /**
     * 图片类型显示样式 1-图标 2-缩略图
     */
    private Integer imageDisplay;
    /**
     * 是否有默认值0无 1有
     */
    private Integer isDefault;
    /**
     * 是否允许为空 0不允许 1允许
     */
    private Integer isNull;
    /**
     * 是否对外开放0不开放1开放
     */
    private Integer isOpen;
    /**
     * 是否是主键 0不是1是
     */
    private Integer isPrimary;
    /**
     * 是否唯一 1是
     */
    private Integer isUnique;
    /**
     * 长度
     */
    private String length;
    /**
     * 选项集名称
     */
    private String optionSetEnName;
    /**
     * 选项集id
     */
    private String optionSetUuid;
    /**
     * 无业务
     */
    private String subType;
    /**
     * platform - 平台级，customer - 用户级
     */
    private String sysLevel;
    /**
     * 是否是模板必填字段0   1是
     */
    private Integer templateRequired;
    /**
     * 无业务租户id
     */
    private String tenantUuid;
    /**
     * DataType
     */
    private String type;
    /**
     * 项目id
     */
    private String projectUuid;
    /***
     * 字段自增setting
     */
    @TableField(exist = false)
    private Map relEntity = new HashMap();


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /***
     * 字段使用类型，0：默认实体字段，1:单选项2:多选项集,3json字段
     */
    private Integer usageType;
    /***
     * 使用类型为1 ，则为单选项值，为3 则为json字段描述信息
     */
    private String usageValue;


    public List<EntityField> getChildren() {
        return children;
    }

    @TableField(exist = false)
    private Boolean isMultiple;

    public Integer getPreseted() {
        return preseted;
    }

    public void setPreseted(Integer preseted) {
        this.preseted = preseted;
    }

    /**
     * 是否是系统预设字段
     */
    private Integer preseted;


    /**
     * 是否需要递归查询
     */
    @TableField(exist = false)
    private Boolean deep = false;

    public void setChildren(List<EntityField> children) {
        this.children = children;
    }

    /**
     * 子集
     */
    @TableField(exist = false)
    private List<EntityField> children;

    /**
     * json字段的属性数组
     */
    @TableField(exist = false)
    private List<EntityField> jsonAttribute;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Map<String, Object> getDataTypeProps() {
        return new HashMap<>();
    }

    public void setDataTypeProps(Map<String, Object> dataTypeProps) {
        this.dataTypeProps = dataTypeProps;
    }

    /**
     * 平台定义的类型
     */
    @TableField(exist = false)
    private String dataType;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 指定类型的附加属性
     */
    @TableField(exist = false)
    private Map<String, Object> dataTypeProps;

    @TableField(exist = false)
    private String identifier;

    public List<EntityField> getJsonAttribute() {
        return jsonAttribute;
    }

    public void setJsonAttribute(List<EntityField> jsonAttribute) {
        this.jsonAttribute = jsonAttribute;
    }

    public void setDeep(Boolean deep) {
        this.deep = deep;
    }

    public Boolean getDeep() {
        return deep;
    }

    public void setMultiple(Boolean multiple) {
        isMultiple = multiple;
    }


    public Boolean getMultiple() {
        return isMultiple;
    }


    public Map getRelEntity() {
        return relEntity;
    }

    public void setRelEntity(Map relEntity) {
        this.relEntity = relEntity;
    }

    @TableField(exist = false)
    private AutoincFieldSetting wlosAutoincFieldSetting;

    @TableField(exist = false)
    private FormulaSetting wlosFormulaSetting;

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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getAddressFormat() {
        return addressFormat;
    }

    public void setAddressFormat(Integer addressFormat) {
        this.addressFormat = addressFormat;
    }

    public Integer getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(Integer amountUnit) {
        this.amountUnit = amountUnit;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public Integer getCanDisplay() {
        return canDisplay;
    }

    public void setCanDisplay(Integer canDisplay) {
        this.canDisplay = canDisplay;
    }

    public Integer getCanMultiUploads() {
        return canMultiUploads;
    }

    public void setCanMultiUploads(Integer canMultiUploads) {
        this.canMultiUploads = canMultiUploads;
    }

    public Integer getCanSearch() {
        return canSearch;
    }

    public void setCanSearch(Integer canSearch) {
        this.canSearch = canSearch;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getConceptLevel() {
        return conceptLevel;
    }

    public void setConceptLevel(String conceptLevel) {
        this.conceptLevel = conceptLevel;
    }

    public Integer getDateFillMethod() {
        return dateFillMethod;
    }

    public void setDateFillMethod(Integer dateFillMethod) {
        this.dateFillMethod = dateFillMethod;
    }

    public Integer getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(Integer dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Integer getDecimalNumber() {
        return decimalNumber;
    }

    public void setDecimalNumber(Integer decimalNumber) {
        this.decimalNumber = decimalNumber;
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

    public String getEntityEnName() {
        return entityEnName;
    }

    public void setEntityEnName(String entityEnName) {
        this.entityEnName = entityEnName;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public Integer getImageDisplay() {
        return imageDisplay;
    }

    public void setImageDisplay(Integer imageDisplay) {
        this.imageDisplay = imageDisplay;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsNull() {
        return isNull;
    }

    public void setIsNull(Integer isNull) {
        this.isNull = isNull;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public void setEntityCnName(String entityCnName) {
        this.entityCnName = entityCnName;
    }

    public String getEntityCnName() {
        return entityCnName;
    }

    public Integer getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getIsUnique() {
        return isUnique;
    }

    public void setIsUnique(Integer isUnique) {
        this.isUnique = isUnique;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getOptionSetEnName() {
        return optionSetEnName;
    }

    public void setOptionSetEnName(String optionSetEnName) {
        this.optionSetEnName = optionSetEnName;
    }

    public String getOptionSetUuid() {
        return optionSetUuid;
    }

    public void setOptionSetUuid(String optionSetUuid) {
        this.optionSetUuid = optionSetUuid;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSysLevel() {
        return sysLevel;
    }

    public void setSysLevel(String sysLevel) {
        this.sysLevel = sysLevel;
    }

    public Integer getTemplateRequired() {
        return templateRequired;
    }

    public void setTemplateRequired(Integer templateRequired) {
        this.templateRequired = templateRequired;
    }

    public String getTenantUuid() {
        return tenantUuid;
    }

    public void setTenantUuid(String tenantUuid) {
        this.tenantUuid = tenantUuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public AutoincFieldSetting getWlosAutoincFieldSetting() {
        return wlosAutoincFieldSetting;
    }

    public void setWlosAutoincFieldSetting(AutoincFieldSetting wlosAutoincFieldSetting) {
        this.wlosAutoincFieldSetting = wlosAutoincFieldSetting;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getUsageType() {


        return usageType;
    }

    public Integer getDisplayField() {
        return displayField;
    }

    public void setDisplayField(Integer displayField) {
        this.displayField = displayField;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }

    public String getUsageValue() {
        return usageValue;
    }

    public void setUsageValue(String usageValue) {
        this.usageValue = usageValue;
    }


    public FormulaSetting getWlosFormulaSetting() {
        return wlosFormulaSetting;
    }

    public void setWlosFormulaSetting(FormulaSetting wlosFormulaSetting) {
        this.wlosFormulaSetting = wlosFormulaSetting;
    }

}