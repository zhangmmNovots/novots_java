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
 * s_employee_test 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_s_employee_test")
public class UvsvSEmployeeTest implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 电话
    */
    private String telephone;

    /**
    * 邮箱
    */
    private String mailbox;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvSEmployeeTestId;




    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setUvsvSEmployeeTestId(String uvsvSEmployeeTestId) {
        this.uvsvSEmployeeTestId = uvsvSEmployeeTestId;
    }

    public String getUvsvSEmployeeTestId() {
        return uvsvSEmployeeTestId;
    }



}