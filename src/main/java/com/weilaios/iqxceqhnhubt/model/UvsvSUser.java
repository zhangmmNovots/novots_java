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
 * 用户 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_s_user")
public class UvsvSUser implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 用户名
    */
    private String username;

    /**
    * 密码
    */
    private String password;

    /**
    * 用户-角色
    */
    private String uvsvMzvr;

    /**
    * 用户-qlvl
    */
    private String uvsvBkfp;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvSUserId;




    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUvsvMzvr(String uvsvMzvr) {
        this.uvsvMzvr = uvsvMzvr;
    }

    public String getUvsvMzvr() {
        return uvsvMzvr;
    }

    public void setUvsvBkfp(String uvsvBkfp) {
        this.uvsvBkfp = uvsvBkfp;
    }

    public String getUvsvBkfp() {
        return uvsvBkfp;
    }

    public void setUvsvSUserId(String uvsvSUserId) {
        this.uvsvSUserId = uvsvSUserId;
    }

    public String getUvsvSUserId() {
        return uvsvSUserId;
    }



}