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
 * 申请表 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_application")
public class UvsvApplication implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 审批状态
    */
    private String approvalStatus;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvApplicationId;




    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setUvsvApplicationId(String uvsvApplicationId) {
        this.uvsvApplicationId = uvsvApplicationId;
    }

    public String getUvsvApplicationId() {
        return uvsvApplicationId;
    }



}