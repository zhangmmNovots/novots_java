package com.weilaios.iqxceqhnhubt.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 项目 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_project")
public class UvsvProject implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 项目ID
    */
    private String projectId;

    /**
    * 项目名称
    */
    private String entryName;

    /**
    * 项目-合同
    */
    private String uvsvJxwj;

    /**
    * 项目-收入确认申请
    */
    private String uvsvPsey;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvProjectId;




    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setUvsvJxwj(String uvsvJxwj) {
        this.uvsvJxwj = uvsvJxwj;
    }

    public String getUvsvJxwj() {
        return uvsvJxwj;
    }

    public void setUvsvPsey(String uvsvPsey) {
        this.uvsvPsey = uvsvPsey;
    }

    public String getUvsvPsey() {
        return uvsvPsey;
    }

    public void setUvsvProjectId(String uvsvProjectId) {
        this.uvsvProjectId = uvsvProjectId;
    }

    public String getUvsvProjectId() {
        return uvsvProjectId;
    }



}