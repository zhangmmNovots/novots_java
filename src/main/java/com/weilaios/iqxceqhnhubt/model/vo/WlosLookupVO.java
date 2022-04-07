package com.weilaios.iqxceqhnhubt.model.vo;



import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author : jyh
 * @since : 2021/5/24  10:46
 */
public class WlosLookupVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> columns;
    private String table_uuid;
    private String alias;
    private String relationKey;

    private  LookupRelation lookup_relation;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getTable_uuid() {
        return table_uuid;
    }

    public void setTable_uuid(String table_uuid) {
        this.table_uuid = table_uuid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRelationKey() {
        return relationKey;
    }

    public void setRelationKey(String relationKey) {
        this.relationKey = relationKey;
    }


    public static  class LookupRelation{
        private  String type;
        private  String leftEntityUuid;
        private  String rightEntityUuid;
        private  String leftRelationField;
        private  String rightRelationField;
        private  String relationKey;

        public String getType() {

            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLeftEntityUuid() {
            return leftEntityUuid;
        }

        public void setLeftEntityUuid(String leftEntityUuid) {
            this.leftEntityUuid = leftEntityUuid;
        }

        public String getRightEntityUuid() {
            return rightEntityUuid;
        }

        public void setRightEntityUuid(String rightEntityUuid) {
            this.rightEntityUuid = rightEntityUuid;
        }

        public String getLeftRelationField() {
            return leftRelationField;
        }

        public void setLeftRelationField(String leftRelationField) {
            this.leftRelationField = leftRelationField;
        }

        public String getRightRelationField() {
            return rightRelationField;
        }

        public void setRightRelationField(String rightRelationField) {
            this.rightRelationField = rightRelationField;
        }

        public String getRelationKey() {
            return relationKey;
        }

        public void setRelationKey(String relationKey) {
            this.relationKey = relationKey;
        }
    }

    public LookupRelation getLookup_relation() {
        return lookup_relation;
    }

    public void setLookup_relation(LookupRelation lookup_relation) {
        this.lookup_relation = lookup_relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WlosLookupVO)) return false;
        WlosLookupVO that = (WlosLookupVO) o;
        return Objects.equals(getColumns(), that.getColumns()) && Objects.equals(getTable_uuid(), that.getTable_uuid()) && Objects.equals(getAlias(), that.getAlias()) && Objects.equals(getRelationKey(), that.getRelationKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColumns(), getTable_uuid(), getAlias(), getRelationKey());
    }
}
