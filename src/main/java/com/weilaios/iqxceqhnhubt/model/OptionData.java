package com.weilaios.iqxceqhnhubt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * 选项集数据
 *
 * @author jyh
 * @since 2021-11-2
 */
public class OptionData extends Model<OptionData> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String uuid;
    private String optionUuid;
    private String projectUuid;
    private String data;
    private Date createTime;
    private Integer isDel;
    @TableField(exist = false)
    @Transient
    private String displayField;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOptionUuid() {
        return optionUuid;
    }

    public void setOptionUuid(String optionUuid) {
        this.optionUuid = optionUuid;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }


}
