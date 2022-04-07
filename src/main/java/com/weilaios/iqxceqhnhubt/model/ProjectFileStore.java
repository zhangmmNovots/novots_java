package com.weilaios.iqxceqhnhubt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * (WlosProjectFileStore)表实体类
 *
 * @author jyh
 * @since 2022-03-14 17:03:22
 */
@SuppressWarnings("serial")
public class ProjectFileStore extends Model<ProjectFileStore> {

    @TableId
    private String uuid;
    private String name;

    private Date createdTime;
    
    private Date updatedTime;
    //1本地，2七牛
    private Integer type;
    //本地保存路径
    private String savePath;
    //七牛Accesskey
    private String qnStoreSpace;
    //七牛密钥
    private String qnAccessKey;
    //七牛密钥
    private String qnSecretKey;
    //七牛密钥
    private String qnBucket;
    //项目id
    private String qnDomainUrl;
    private String projectUuid;
    private Integer isDel;
    
    private String fileName;


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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getQnStoreSpace() {
        return qnStoreSpace;
    }

    public void setQnStoreSpace(String qnStoreSpace) {
        this.qnStoreSpace = qnStoreSpace;
    }

    public String getQnAccessKey() {
        return qnAccessKey;
    }

    public void setQnAccessKey(String qnAccessKey) {
        this.qnAccessKey = qnAccessKey;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getQnSecretKey() {
        return qnSecretKey;
    }

    public void setQnSecretKey(String qnSecretKey) {
        this.qnSecretKey = qnSecretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQnBucket() {
        return qnBucket;
    }

    public void setQnBucket(String qnBucket) {
        this.qnBucket = qnBucket;
    }

    public String getQnDomainUrl() {
        return qnDomainUrl;
    }

    public void setQnDomainUrl(String qnDomainUrl) {
        this.qnDomainUrl = qnDomainUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.uuid;
    }
    }

