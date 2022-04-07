package com.weilaios.iqxceqhnhubt.model;

import com.baomidou.mybatisplus.annotation.TableField;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

/**
 * (WlosFormulaSetting)实体类
 *
 * @author makejava
 * @since 2021-11-03 10:59:41
 */
public class FormulaSetting implements Serializable {
    private static final long serialVersionUID = 287763068355080844L;
    /**
     * 主键
     */
    @TableId
    private String uuid;
    /**
     * 项目id
     */
    private String projectUuid;
    /**
     * 实体id
     */
    private String entityUuid;
    /**
     * 实体英文名称
     */
    private String entityEnName;
    /**
     * 字段英文名称
     */
    private String enName;
    /**
     * 语意化的公式
     */
    private String semanticRule;
    /**
     * 公式
     */
    private String rule;
    /**
     * 结果类型
     */
    private String resultType;

    /**
     * 小数位数
     */
    private Integer decimalPlaces;
    /**
     * 公式需要的参数
     */
    private String parameters;

    /**
     * 公式字段中的参数数组
     */
    @TableField(exist = false)
    private List<String> parametersList;

    /**
     * 公式xml
     */
    private String blocklyXml;

    public String getBlocklyXml() {
        return blocklyXml;
    }

    public void setBlocklyXml(String blocklyXml) {
        this.blocklyXml = blocklyXml;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProjectUuid() {
        return projectUuid;
    }

    public void setProjectUuid(String projectUuid) {
        this.projectUuid = projectUuid;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public String getEntityEnName() {
        return entityEnName;
    }

    public void setEntityEnName(String entityEnName) {
        this.entityEnName = entityEnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getSemanticRule() {
        return semanticRule;
    }

    public void setSemanticRule(String semanticRule) {
        this.semanticRule = semanticRule;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }


    public List<String> getParametersList() {
        return parametersList;
    }

    public void setParametersList(List<String> parametersList) {
        this.parametersList = parametersList;
    }
}
