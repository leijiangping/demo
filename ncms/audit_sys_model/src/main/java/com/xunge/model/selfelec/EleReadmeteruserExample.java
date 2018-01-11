package com.xunge.model.selfelec;

import java.util.ArrayList;
import java.util.List;

public class EleReadmeteruserExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public EleReadmeteruserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
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

        public Criteria andRuserIdIsNull() {
            addCriterion("ruser_id is null");
            return (Criteria) this;
        }

        public Criteria andRuserIdIsNotNull() {
            addCriterion("ruser_id is not null");
            return (Criteria) this;
        }

        public Criteria andRuserIdEqualTo(String value) {
            addCriterion("ruser_id =", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdNotEqualTo(String value) {
            addCriterion("ruser_id <>", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdGreaterThan(String value) {
            addCriterion("ruser_id >", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdGreaterThanOrEqualTo(String value) {
            addCriterion("ruser_id >=", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdLessThan(String value) {
            addCriterion("ruser_id <", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdLessThanOrEqualTo(String value) {
            addCriterion("ruser_id <=", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdLike(String value) {
            addCriterion("ruser_id like", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdNotLike(String value) {
            addCriterion("ruser_id not like", value, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdIn(List<String> values) {
            addCriterion("ruser_id in", values, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdNotIn(List<String> values) {
            addCriterion("ruser_id not in", values, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdBetween(String value1, String value2) {
            addCriterion("ruser_id between", value1, value2, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserIdNotBetween(String value1, String value2) {
            addCriterion("ruser_id not between", value1, value2, "ruserId");
            return (Criteria) this;
        }

        public Criteria andRuserCodeIsNull() {
            addCriterion("ruser_code is null");
            return (Criteria) this;
        }

        public Criteria andRuserCodeIsNotNull() {
            addCriterion("ruser_code is not null");
            return (Criteria) this;
        }

        public Criteria andRuserCodeEqualTo(String value) {
            addCriterion("ruser_code =", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeNotEqualTo(String value) {
            addCriterion("ruser_code <>", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeGreaterThan(String value) {
            addCriterion("ruser_code >", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ruser_code >=", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeLessThan(String value) {
            addCriterion("ruser_code <", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeLessThanOrEqualTo(String value) {
            addCriterion("ruser_code <=", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeLike(String value) {
            addCriterion("ruser_code like", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeNotLike(String value) {
            addCriterion("ruser_code not like", value, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeIn(List<String> values) {
            addCriterion("ruser_code in", values, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeNotIn(List<String> values) {
            addCriterion("ruser_code not in", values, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeBetween(String value1, String value2) {
            addCriterion("ruser_code between", value1, value2, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserCodeNotBetween(String value1, String value2) {
            addCriterion("ruser_code not between", value1, value2, "ruserCode");
            return (Criteria) this;
        }

        public Criteria andRuserNameIsNull() {
            addCriterion("ruser_name is null");
            return (Criteria) this;
        }

        public Criteria andRuserNameIsNotNull() {
            addCriterion("ruser_name is not null");
            return (Criteria) this;
        }

        public Criteria andRuserNameEqualTo(String value) {
            addCriterion("ruser_name =", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameNotEqualTo(String value) {
            addCriterion("ruser_name <>", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameGreaterThan(String value) {
            addCriterion("ruser_name >", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameGreaterThanOrEqualTo(String value) {
            addCriterion("ruser_name >=", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameLessThan(String value) {
            addCriterion("ruser_name <", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameLessThanOrEqualTo(String value) {
            addCriterion("ruser_name <=", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameLike(String value) {
            addCriterion("ruser_name like", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameNotLike(String value) {
            addCriterion("ruser_name not like", value, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameIn(List<String> values) {
            addCriterion("ruser_name in", values, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameNotIn(List<String> values) {
            addCriterion("ruser_name not in", values, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameBetween(String value1, String value2) {
            addCriterion("ruser_name between", value1, value2, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserNameNotBetween(String value1, String value2) {
            addCriterion("ruser_name not between", value1, value2, "ruserName");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneIsNull() {
            addCriterion("ruser_telphone is null");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneIsNotNull() {
            addCriterion("ruser_telphone is not null");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneEqualTo(String value) {
            addCriterion("ruser_telphone =", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneNotEqualTo(String value) {
            addCriterion("ruser_telphone <>", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneGreaterThan(String value) {
            addCriterion("ruser_telphone >", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneGreaterThanOrEqualTo(String value) {
            addCriterion("ruser_telphone >=", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneLessThan(String value) {
            addCriterion("ruser_telphone <", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneLessThanOrEqualTo(String value) {
            addCriterion("ruser_telphone <=", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneLike(String value) {
            addCriterion("ruser_telphone like", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneNotLike(String value) {
            addCriterion("ruser_telphone not like", value, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneIn(List<String> values) {
            addCriterion("ruser_telphone in", values, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneNotIn(List<String> values) {
            addCriterion("ruser_telphone not in", values, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneBetween(String value1, String value2) {
            addCriterion("ruser_telphone between", value1, value2, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserTelphoneNotBetween(String value1, String value2) {
            addCriterion("ruser_telphone not between", value1, value2, "ruserTelphone");
            return (Criteria) this;
        }

        public Criteria andRuserNoteIsNull() {
            addCriterion("ruser_note is null");
            return (Criteria) this;
        }

        public Criteria andRuserNoteIsNotNull() {
            addCriterion("ruser_note is not null");
            return (Criteria) this;
        }

        public Criteria andRuserNoteEqualTo(String value) {
            addCriterion("ruser_note =", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteNotEqualTo(String value) {
            addCriterion("ruser_note <>", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteGreaterThan(String value) {
            addCriterion("ruser_note >", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteGreaterThanOrEqualTo(String value) {
            addCriterion("ruser_note >=", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteLessThan(String value) {
            addCriterion("ruser_note <", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteLessThanOrEqualTo(String value) {
            addCriterion("ruser_note <=", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteLike(String value) {
            addCriterion("ruser_note like", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteNotLike(String value) {
            addCriterion("ruser_note not like", value, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteIn(List<String> values) {
            addCriterion("ruser_note in", values, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteNotIn(List<String> values) {
            addCriterion("ruser_note not in", values, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteBetween(String value1, String value2) {
            addCriterion("ruser_note between", value1, value2, "ruserNote");
            return (Criteria) this;
        }

        public Criteria andRuserNoteNotBetween(String value1, String value2) {
            addCriterion("ruser_note not between", value1, value2, "ruserNote");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated do_not_delete_during_merge Sun Jul 02 03:52:13 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ele_readmeteruser
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

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

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}