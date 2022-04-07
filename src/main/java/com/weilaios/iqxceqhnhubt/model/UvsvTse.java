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
 * s_employee 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_tse")
public class UvsvTse implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 用户名
    */
    private String username;

    /**
    * 电话
    */
    private String telephone;

    /**
    * 入职时间
    */
    private Date entrytime;

    /**
    * 身份证号
    */
    private String idnumber;

    /**
    * 住址
    */
    private String address;

    /**
    * s_employee-收入确认申请
    */
    private String uvsvDzsp;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvTseId;




    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
    }

    public Date getEntrytime() {
        return entrytime;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setUvsvDzsp(String uvsvDzsp) {
        this.uvsvDzsp = uvsvDzsp;
    }

    public String getUvsvDzsp() {
        return uvsvDzsp;
    }

    public void setUvsvTseId(String uvsvTseId) {
        this.uvsvTseId = uvsvTseId;
    }

    public String getUvsvTseId() {
        return uvsvTseId;
    }



}