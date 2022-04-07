package com.weilaios.iqxceqhnhubt.model.vo;


import java.util.List;

/**
 * @author : jyh
 * @content : 关联查询vo对象
 * @since : 2021/5/24  10:46
 */
public class RelationConditionVO {

    private String left_entity;
    private String right_entity;
    private String join_type;
    private List<condition> join_conditions;


    public String getLeft_entity() {
        return left_entity;
    }

    public void setLeft_entity(String left_entity) {
        this.left_entity = left_entity;
    }

    public String getRight_entity() {
        return right_entity;
    }

    public void setRight_entity(String right_entity) {
        this.right_entity = right_entity;
    }

    public String getJoin_type() {
        return join_type;
    }

    public void setJoin_type(String join_type) {
        this.join_type = join_type;
    }

    public List<condition> getJoin_conditions() {
        return join_conditions;
    }

    public void setJoin_conditions(List<condition> join_conditions) {
        this.join_conditions = join_conditions;
    }

    /**
     * @author : jyh
     * @content : 关联查询条件对象
     * @since : 2021/5/24  10:46
     */
    public static class condition {
        private String left;
        private String right;


        public condition() {
        }

        public condition(String left, String right) {
            this.left = left;
            this.right = right;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "condition{" +
                    "left='" + left + '\'' +
                    ", right='" + right + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RelationConditionVO{" +
                "left_entity='" + left_entity + '\'' +
                ", right_entity='" + right_entity + '\'' +
                ", join_type='" + join_type + '\'' +
                ", join_conditions=" + join_conditions +
                '}';
    }
}
