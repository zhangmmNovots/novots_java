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
 * 用户-角色(中间表) 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_mzvr")
public class UvsvMzvr implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 用户-角色(中间表)-zksg
    */
    private String uvsvXfue;

    /**
    * 用户-角色(中间表)-pdwx
    */
    private String uvsvBkfp;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvMzvrId;




    public void setUvsvXfue(String uvsvXfue) {
        this.uvsvXfue = uvsvXfue;
    }

    public String getUvsvXfue() {
        return uvsvXfue;
    }

    public void setUvsvBkfp(String uvsvBkfp) {
        this.uvsvBkfp = uvsvBkfp;
    }

    public String getUvsvBkfp() {
        return uvsvBkfp;
    }

    public void setUvsvMzvrId(String uvsvMzvrId) {
        this.uvsvMzvrId = uvsvMzvrId;
    }

    public String getUvsvMzvrId() {
        return uvsvMzvrId;
    }



}