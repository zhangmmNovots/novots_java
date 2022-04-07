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
 * 收入确认申请 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_application_revenu")
public class UvsvApplicationRevenu implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 收入金额
    */
    private String fee;

    /**
    * 所属月份
    */
    private Date month;

    /**
    * 申请人
    */
    private String uvsvDzsp;

    /**
    * 项目
    */
    private String uvsvPsey;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvApplicationRevenuId;




    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setUvsvDzsp(String uvsvDzsp) {
        this.uvsvDzsp = uvsvDzsp;
    }

    public String getUvsvDzsp() {
        return uvsvDzsp;
    }

    public void setUvsvPsey(String uvsvPsey) {
        this.uvsvPsey = uvsvPsey;
    }

    public String getUvsvPsey() {
        return uvsvPsey;
    }

    public void setUvsvApplicationRevenuId(String uvsvApplicationRevenuId) {
        this.uvsvApplicationRevenuId = uvsvApplicationRevenuId;
    }

    public String getUvsvApplicationRevenuId() {
        return uvsvApplicationRevenuId;
    }



}