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
 * 后台用户 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_background_user")
public class UvsvBackgroundUser implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 手机号
    */
    private String cellNumber;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String password;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvBackgroundUserId;




    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUvsvBackgroundUserId(String uvsvBackgroundUserId) {
        this.uvsvBackgroundUserId = uvsvBackgroundUserId;
    }

    public String getUvsvBackgroundUserId() {
        return uvsvBackgroundUserId;
    }



}