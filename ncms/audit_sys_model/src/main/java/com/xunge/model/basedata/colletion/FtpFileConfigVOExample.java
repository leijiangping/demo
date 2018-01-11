package com.xunge.model.basedata.colletion;

import java.util.ArrayList;
import java.util.List;

public class FtpFileConfigVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FtpFileConfigVOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("file_id like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("file_id not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFilePerfixIsNull() {
            addCriterion("file_perfix is null");
            return (Criteria) this;
        }

        public Criteria andFilePerfixIsNotNull() {
            addCriterion("file_perfix is not null");
            return (Criteria) this;
        }

        public Criteria andFilePerfixEqualTo(String value) {
            addCriterion("file_perfix =", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixNotEqualTo(String value) {
            addCriterion("file_perfix <>", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixGreaterThan(String value) {
            addCriterion("file_perfix >", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixGreaterThanOrEqualTo(String value) {
            addCriterion("file_perfix >=", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixLessThan(String value) {
            addCriterion("file_perfix <", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixLessThanOrEqualTo(String value) {
            addCriterion("file_perfix <=", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixLike(String value) {
            addCriterion("file_perfix like", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixNotLike(String value) {
            addCriterion("file_perfix not like", value, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixIn(List<String> values) {
            addCriterion("file_perfix in", values, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixNotIn(List<String> values) {
            addCriterion("file_perfix not in", values, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixBetween(String value1, String value2) {
            addCriterion("file_perfix between", value1, value2, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFilePerfixNotBetween(String value1, String value2) {
            addCriterion("file_perfix not between", value1, value2, "filePerfix");
            return (Criteria) this;
        }

        public Criteria andFieldConfigIsNull() {
            addCriterion("field_config is null");
            return (Criteria) this;
        }

        public Criteria andFieldConfigIsNotNull() {
            addCriterion("field_config is not null");
            return (Criteria) this;
        }

        public Criteria andFieldConfigEqualTo(Integer value) {
            addCriterion("field_config =", value, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigNotEqualTo(Integer value) {
            addCriterion("field_config <>", value, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigGreaterThan(Integer value) {
            addCriterion("field_config >", value, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigGreaterThanOrEqualTo(Integer value) {
            addCriterion("field_config >=", value, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigLessThan(Integer value) {
            addCriterion("field_config <", value, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigLessThanOrEqualTo(Integer value) {
            addCriterion("field_config <=", value, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigIn(List<Integer> values) {
            addCriterion("field_config in", values, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigNotIn(List<Integer> values) {
            addCriterion("field_config not in", values, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigBetween(Integer value1, Integer value2) {
            addCriterion("field_config between", value1, value2, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFieldConfigNotBetween(Integer value1, Integer value2) {
            addCriterion("field_config not between", value1, value2, "fieldConfig");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeIsNull() {
            addCriterion("file_data_type is null");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeIsNotNull() {
            addCriterion("file_data_type is not null");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeEqualTo(String value) {
            addCriterion("file_data_type =", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeNotEqualTo(String value) {
            addCriterion("file_data_type <>", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeGreaterThan(String value) {
            addCriterion("file_data_type >", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("file_data_type >=", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeLessThan(String value) {
            addCriterion("file_data_type <", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeLessThanOrEqualTo(String value) {
            addCriterion("file_data_type <=", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeLike(String value) {
            addCriterion("file_data_type like", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeNotLike(String value) {
            addCriterion("file_data_type not like", value, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeIn(List<String> values) {
            addCriterion("file_data_type in", values, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeNotIn(List<String> values) {
            addCriterion("file_data_type not in", values, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeBetween(String value1, String value2) {
            addCriterion("file_data_type between", value1, value2, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andFileDataTypeNotBetween(String value1, String value2) {
            addCriterion("file_data_type not between", value1, value2, "fileDataType");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(String value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(String value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(String value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(String value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(String value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLike(String value) {
            addCriterion("task_id like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotLike(String value) {
            addCriterion("task_id not like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<String> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<String> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(String value1, String value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(String value1, String value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}