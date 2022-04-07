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
@TableName(value = "uvsv_user_table")
public class UvsvUserTable implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvUserTableId;




    public void setUvsvUserTableId(String uvsvUserTableId) {
        this.uvsvUserTableId = uvsvUserTableId;
    }

    public String getUvsvUserTableId() {
        return uvsvUserTableId;
    }



}