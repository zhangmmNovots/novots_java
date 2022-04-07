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
 * 合同 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_contract")
public class UvsvContract implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 合同-项目
    */
    private String uvsvJxwj;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvContractId;




    public void setUvsvJxwj(String uvsvJxwj) {
        this.uvsvJxwj = uvsvJxwj;
    }

    public String getUvsvJxwj() {
        return uvsvJxwj;
    }

    public void setUvsvContractId(String uvsvContractId) {
        this.uvsvContractId = uvsvContractId;
    }

    public String getUvsvContractId() {
        return uvsvContractId;
    }



}