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
 * 角色-权限(中间表) 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_boig")
public class UvsvBoig implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 角色-权限(中间表)-rrnn
    */
    private String uvsvPhlr;

    /**
    * 角色-权限(中间表)-swov
    */
    private String uvsvEsly;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvBoigId;




    public void setUvsvPhlr(String uvsvPhlr) {
        this.uvsvPhlr = uvsvPhlr;
    }

    public String getUvsvPhlr() {
        return uvsvPhlr;
    }

    public void setUvsvEsly(String uvsvEsly) {
        this.uvsvEsly = uvsvEsly;
    }

    public String getUvsvEsly() {
        return uvsvEsly;
    }

    public void setUvsvBoigId(String uvsvBoigId) {
        this.uvsvBoigId = uvsvBoigId;
    }

    public String getUvsvBoigId() {
        return uvsvBoigId;
    }



}