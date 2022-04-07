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
 * 一级目录 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_s_")
public class UvsvS implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 名称
    */
    private String name;

    /**
    * 路径
    */
    private String 路径;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvS_id;




    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void set路径(String 路径) {
        this.路径 = 路径;
    }

    public String get路径() {
        return 路径;
    }

    public void setUvsvS_id(String uvsvS_id) {
        this.uvsvS_id = uvsvS_id;
    }

    public String getUvsvS_id() {
        return uvsvS_id;
    }



}