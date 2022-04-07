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
 * 实体_2vept 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_entity_vept")
public class UvsvEntityVept implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvEntityVeptId;




    public void setUvsvEntityVeptId(String uvsvEntityVeptId) {
        this.uvsvEntityVeptId = uvsvEntityVeptId;
    }

    public String getUvsvEntityVeptId() {
        return uvsvEntityVeptId;
    }



}