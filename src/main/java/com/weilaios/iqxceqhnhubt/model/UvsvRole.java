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
 * 角色 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_role")
public class UvsvRole implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 角色名称
    */
    private String roleName;

    /**
    * 角色-权限
    */
    private String uvsvBoig;

    /**
    * 角色-uaya
    */
    private String uvsvPhlr;

    /**
    * 角色-etkz
    */
    private String uvsvXfue;

    /**
    * 角色-用户
    */
    private String uvsvMzvr;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvRoleId;




    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setUvsvBoig(String uvsvBoig) {
        this.uvsvBoig = uvsvBoig;
    }

    public String getUvsvBoig() {
        return uvsvBoig;
    }

    public void setUvsvPhlr(String uvsvPhlr) {
        this.uvsvPhlr = uvsvPhlr;
    }

    public String getUvsvPhlr() {
        return uvsvPhlr;
    }

    public void setUvsvXfue(String uvsvXfue) {
        this.uvsvXfue = uvsvXfue;
    }

    public String getUvsvXfue() {
        return uvsvXfue;
    }

    public void setUvsvMzvr(String uvsvMzvr) {
        this.uvsvMzvr = uvsvMzvr;
    }

    public String getUvsvMzvr() {
        return uvsvMzvr;
    }

    public void setUvsvRoleId(String uvsvRoleId) {
        this.uvsvRoleId = uvsvRoleId;
    }

    public String getUvsvRoleId() {
        return uvsvRoleId;
    }



}