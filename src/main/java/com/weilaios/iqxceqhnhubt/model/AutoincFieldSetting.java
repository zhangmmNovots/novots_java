package com.weilaios.iqxceqhnhubt.model;

import java.io.Serializable;
import java.util.Date;

/**
 * (AutoincFieldSetting)实体类
 *
 * @author jyh
 * @since 2021-04-23 10:11:42
 */
public class AutoincFieldSetting implements Serializable {
    private static final long serialVersionUID = -30725046122065488L;

    private String uuid;

    private Date createTime;

    private String fieldUuid;

    private Object rule;

    private Integer reset;

    private Integer incStart;

    private Integer incLength;

    private String entityUuid;

    private String enName;

    private String entityEnName;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFieldUuid() {
        return fieldUuid;
    }

    public void setFieldUuid(String fieldUuid) {
        this.fieldUuid = fieldUuid;
    }

    public Object getRule() {
        return rule;
    }

    public void setRule(Object rule) {
        this.rule = rule;
    }

    public Integer getReset() {
        return reset;
    }

    public void setReset(Integer reset) {
        this.reset = reset;
    }

    public Integer getIncStart() {
        return incStart;
    }

    public void setIncStart(Integer incStart) {
        this.incStart = incStart;
    }

    public Integer getIncLength() {
        return incLength;
    }

    public void setIncLength(Integer incLength) {
        this.incLength = incLength;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
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

}