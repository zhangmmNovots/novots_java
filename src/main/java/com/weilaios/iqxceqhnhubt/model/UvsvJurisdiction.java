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
 * 权限 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_jurisdiction")
public class UvsvJurisdiction implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 权限名称
    */
    private String permissionName;

    /**
    * 权限-zgdm
    */
    private String uvsvEsly;

    /**
    * 权限-角色
    */
    private String uvsvBoig;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvJurisdictionId;




    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setUvsvEsly(String uvsvEsly) {
        this.uvsvEsly = uvsvEsly;
    }

    public String getUvsvEsly() {
        return uvsvEsly;
    }

    public void setUvsvBoig(String uvsvBoig) {
        this.uvsvBoig = uvsvBoig;
    }

    public String getUvsvBoig() {
        return uvsvBoig;
    }

    public void setUvsvJurisdictionId(String uvsvJurisdictionId) {
        this.uvsvJurisdictionId = uvsvJurisdictionId;
    }

    public String getUvsvJurisdictionId() {
        return uvsvJurisdictionId;
    }



}