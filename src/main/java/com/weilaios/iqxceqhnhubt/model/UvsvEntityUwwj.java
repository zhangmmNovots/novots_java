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
 * 收入确认审批状态对应表 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_entity_uwwj")
public class UvsvEntityUwwj implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 代码
    */
    private String code;

    /**
    * 状态
    */
    private String state;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvEntityUwwjId;




    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setUvsvEntityUwwjId(String uvsvEntityUwwjId) {
        this.uvsvEntityUwwjId = uvsvEntityUwwjId;
    }

    public String getUvsvEntityUwwjId() {
        return uvsvEntityUwwjId;
    }



}