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
 * 商品 实体类
 *
 * @author 阿旺
 * @date Apr 7, 2022 3:06:40 PM
 * @since v0.1
 */
@Entity
@Table
@TableName(value = "uvsv_commodity")
public class UvsvCommodity implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
    * 商品名称
    */
    private String tradeName;

    /**
    * 商品描述
    */
    private String 商品;

    /**
    * 商品配图
    */
    private String commodityMap;

    /**
    * 单价
    */
    private String unitprice;

    /**
    * 主键
    */
    @Id
    @TableId
    private String uvsvCommodityId;




    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void set商品(String 商品) {
        this.商品 = 商品;
    }

    public String get商品() {
        return 商品;
    }

    public void setCommodityMap(String commodityMap) {
        this.commodityMap = commodityMap;
    }

    public String getCommodityMap() {
        return commodityMap;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUvsvCommodityId(String uvsvCommodityId) {
        this.uvsvCommodityId = uvsvCommodityId;
    }

    public String getUvsvCommodityId() {
        return uvsvCommodityId;
    }



}