package com.xunge.model.selfelec.electricmeter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatElectricMeterVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DatElectricMeterVOExample() {
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

        public Criteria andMeterIdIsNull() {
            addCriterion("meter_id is null");
            return (Criteria) this;
        }

        public Criteria andMeterIdIsNotNull() {
            addCriterion("meter_id is not null");
            return (Criteria) this;
        }

        public Criteria andMeterIdEqualTo(String value) {
            addCriterion("meter_id =", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdNotEqualTo(String value) {
            addCriterion("meter_id <>", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdGreaterThan(String value) {
            addCriterion("meter_id >", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdGreaterThanOrEqualTo(String value) {
            addCriterion("meter_id >=", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdLessThan(String value) {
            addCriterion("meter_id <", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdLessThanOrEqualTo(String value) {
            addCriterion("meter_id <=", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdLike(String value) {
            addCriterion("meter_id like", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdNotLike(String value) {
            addCriterion("meter_id not like", value, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdIn(List<String> values) {
            addCriterion("meter_id in", values, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdNotIn(List<String> values) {
            addCriterion("meter_id not in", values, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdBetween(String value1, String value2) {
            addCriterion("meter_id between", value1, value2, "meterId");
            return (Criteria) this;
        }

        public Criteria andMeterIdNotBetween(String value1, String value2) {
            addCriterion("meter_id not between", value1, value2, "meterId");
            return (Criteria) this;
        }

        public Criteria andPrvIdIsNull() {
            addCriterion("prv_id is null");
            return (Criteria) this;
        }

        public Criteria andPrvIdIsNotNull() {
            addCriterion("prv_id is not null");
            return (Criteria) this;
        }

        public Criteria andPrvIdEqualTo(String value) {
            addCriterion("prv_id =", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdNotEqualTo(String value) {
            addCriterion("prv_id <>", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdGreaterThan(String value) {
            addCriterion("prv_id >", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdGreaterThanOrEqualTo(String value) {
            addCriterion("prv_id >=", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdLessThan(String value) {
            addCriterion("prv_id <", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdLessThanOrEqualTo(String value) {
            addCriterion("prv_id <=", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdLike(String value) {
            addCriterion("prv_id like", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdNotLike(String value) {
            addCriterion("prv_id not like", value, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdIn(List<String> values) {
            addCriterion("prv_id in", values, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdNotIn(List<String> values) {
            addCriterion("prv_id not in", values, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdBetween(String value1, String value2) {
            addCriterion("prv_id between", value1, value2, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvIdNotBetween(String value1, String value2) {
            addCriterion("prv_id not between", value1, value2, "prvId");
            return (Criteria) this;
        }

        public Criteria andPrvSnameIsNull() {
            addCriterion("prv_sname is null");
            return (Criteria) this;
        }

        public Criteria andPrvSnameIsNotNull() {
            addCriterion("prv_sname is not null");
            return (Criteria) this;
        }

        public Criteria andPrvSnameEqualTo(String value) {
            addCriterion("prv_sname =", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameNotEqualTo(String value) {
            addCriterion("prv_sname <>", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameGreaterThan(String value) {
            addCriterion("prv_sname >", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameGreaterThanOrEqualTo(String value) {
            addCriterion("prv_sname >=", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameLessThan(String value) {
            addCriterion("prv_sname <", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameLessThanOrEqualTo(String value) {
            addCriterion("prv_sname <=", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameLike(String value) {
            addCriterion("prv_sname like", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameNotLike(String value) {
            addCriterion("prv_sname not like", value, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameIn(List<String> values) {
            addCriterion("prv_sname in", values, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameNotIn(List<String> values) {
            addCriterion("prv_sname not in", values, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameBetween(String value1, String value2) {
            addCriterion("prv_sname between", value1, value2, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPrvSnameNotBetween(String value1, String value2) {
            addCriterion("prv_sname not between", value1, value2, "prvSname");
            return (Criteria) this;
        }

        public Criteria andPregIdIsNull() {
            addCriterion("preg_id is null");
            return (Criteria) this;
        }

        public Criteria andPregIdIsNotNull() {
            addCriterion("preg_id is not null");
            return (Criteria) this;
        }

        public Criteria andPregIdEqualTo(String value) {
            addCriterion("preg_id =", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdNotEqualTo(String value) {
            addCriterion("preg_id <>", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdGreaterThan(String value) {
            addCriterion("preg_id >", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdGreaterThanOrEqualTo(String value) {
            addCriterion("preg_id >=", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdLessThan(String value) {
            addCriterion("preg_id <", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdLessThanOrEqualTo(String value) {
            addCriterion("preg_id <=", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdLike(String value) {
            addCriterion("preg_id like", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdNotLike(String value) {
            addCriterion("preg_id not like", value, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdIn(List<String> values) {
            addCriterion("preg_id in", values, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdNotIn(List<String> values) {
            addCriterion("preg_id not in", values, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdBetween(String value1, String value2) {
            addCriterion("preg_id between", value1, value2, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregIdNotBetween(String value1, String value2) {
            addCriterion("preg_id not between", value1, value2, "pregId");
            return (Criteria) this;
        }

        public Criteria andPregNameIsNull() {
            addCriterion("preg_name is null");
            return (Criteria) this;
        }

        public Criteria andPregNameIsNotNull() {
            addCriterion("preg_name is not null");
            return (Criteria) this;
        }

        public Criteria andPregNameEqualTo(String value) {
            addCriterion("preg_name =", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameNotEqualTo(String value) {
            addCriterion("preg_name <>", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameGreaterThan(String value) {
            addCriterion("preg_name >", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameGreaterThanOrEqualTo(String value) {
            addCriterion("preg_name >=", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameLessThan(String value) {
            addCriterion("preg_name <", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameLessThanOrEqualTo(String value) {
            addCriterion("preg_name <=", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameLike(String value) {
            addCriterion("preg_name like", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameNotLike(String value) {
            addCriterion("preg_name not like", value, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameIn(List<String> values) {
            addCriterion("preg_name in", values, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameNotIn(List<String> values) {
            addCriterion("preg_name not in", values, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameBetween(String value1, String value2) {
            addCriterion("preg_name between", value1, value2, "pregName");
            return (Criteria) this;
        }

        public Criteria andPregNameNotBetween(String value1, String value2) {
            addCriterion("preg_name not between", value1, value2, "pregName");
            return (Criteria) this;
        }

        public Criteria andRegIdIsNull() {
            addCriterion("reg_id is null");
            return (Criteria) this;
        }

        public Criteria andRegIdIsNotNull() {
            addCriterion("reg_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegIdEqualTo(String value) {
            addCriterion("reg_id =", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdNotEqualTo(String value) {
            addCriterion("reg_id <>", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdGreaterThan(String value) {
            addCriterion("reg_id >", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdGreaterThanOrEqualTo(String value) {
            addCriterion("reg_id >=", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdLessThan(String value) {
            addCriterion("reg_id <", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdLessThanOrEqualTo(String value) {
            addCriterion("reg_id <=", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdLike(String value) {
            addCriterion("reg_id like", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdNotLike(String value) {
            addCriterion("reg_id not like", value, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdIn(List<String> values) {
            addCriterion("reg_id in", values, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdNotIn(List<String> values) {
            addCriterion("reg_id not in", values, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdBetween(String value1, String value2) {
            addCriterion("reg_id between", value1, value2, "regId");
            return (Criteria) this;
        }

        public Criteria andRegIdNotBetween(String value1, String value2) {
            addCriterion("reg_id not between", value1, value2, "regId");
            return (Criteria) this;
        }

        public Criteria andRegNameIsNull() {
            addCriterion("reg_name is null");
            return (Criteria) this;
        }

        public Criteria andRegNameIsNotNull() {
            addCriterion("reg_name is not null");
            return (Criteria) this;
        }

        public Criteria andRegNameEqualTo(String value) {
            addCriterion("reg_name =", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameNotEqualTo(String value) {
            addCriterion("reg_name <>", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameGreaterThan(String value) {
            addCriterion("reg_name >", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameGreaterThanOrEqualTo(String value) {
            addCriterion("reg_name >=", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameLessThan(String value) {
            addCriterion("reg_name <", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameLessThanOrEqualTo(String value) {
            addCriterion("reg_name <=", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameLike(String value) {
            addCriterion("reg_name like", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameNotLike(String value) {
            addCriterion("reg_name not like", value, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameIn(List<String> values) {
            addCriterion("reg_name in", values, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameNotIn(List<String> values) {
            addCriterion("reg_name not in", values, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameBetween(String value1, String value2) {
            addCriterion("reg_name between", value1, value2, "regName");
            return (Criteria) this;
        }

        public Criteria andRegNameNotBetween(String value1, String value2) {
            addCriterion("reg_name not between", value1, value2, "regName");
            return (Criteria) this;
        }

        public Criteria andMeterCodeIsNull() {
            addCriterion("meter_code is null");
            return (Criteria) this;
        }

        public Criteria andMeterCodeIsNotNull() {
            addCriterion("meter_code is not null");
            return (Criteria) this;
        }

        public Criteria andMeterCodeEqualTo(String value) {
            addCriterion("meter_code =", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeNotEqualTo(String value) {
            addCriterion("meter_code <>", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeGreaterThan(String value) {
            addCriterion("meter_code >", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeGreaterThanOrEqualTo(String value) {
            addCriterion("meter_code >=", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeLessThan(String value) {
            addCriterion("meter_code <", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeLessThanOrEqualTo(String value) {
            addCriterion("meter_code <=", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeLike(String value) {
            addCriterion("meter_code like", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeNotLike(String value) {
            addCriterion("meter_code not like", value, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeIn(List<String> values) {
            addCriterion("meter_code in", values, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeNotIn(List<String> values) {
            addCriterion("meter_code not in", values, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeBetween(String value1, String value2) {
            addCriterion("meter_code between", value1, value2, "meterCode");
            return (Criteria) this;
        }

        public Criteria andMeterCodeNotBetween(String value1, String value2) {
            addCriterion("meter_code not between", value1, value2, "meterCode");
            return (Criteria) this;
        }

        public Criteria andInitialValueIsNull() {
            addCriterion("initial_value is null");
            return (Criteria) this;
        }

        public Criteria andInitialValueIsNotNull() {
            addCriterion("initial_value is not null");
            return (Criteria) this;
        }

        public Criteria andInitialValueEqualTo(BigDecimal value) {
            addCriterion("initial_value =", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotEqualTo(BigDecimal value) {
            addCriterion("initial_value <>", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueGreaterThan(BigDecimal value) {
            addCriterion("initial_value >", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("initial_value >=", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueLessThan(BigDecimal value) {
            addCriterion("initial_value <", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("initial_value <=", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueIn(List<BigDecimal> values) {
            addCriterion("initial_value in", values, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotIn(List<BigDecimal> values) {
            addCriterion("initial_value not in", values, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("initial_value between", value1, value2, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("initial_value not between", value1, value2, "initialValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueIsNull() {
            addCriterion("upper_value is null");
            return (Criteria) this;
        }

        public Criteria andUpperValueIsNotNull() {
            addCriterion("upper_value is not null");
            return (Criteria) this;
        }

        public Criteria andUpperValueEqualTo(BigDecimal value) {
            addCriterion("upper_value =", value, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueNotEqualTo(BigDecimal value) {
            addCriterion("upper_value <>", value, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueGreaterThan(BigDecimal value) {
            addCriterion("upper_value >", value, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("upper_value >=", value, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueLessThan(BigDecimal value) {
            addCriterion("upper_value <", value, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("upper_value <=", value, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueIn(List<BigDecimal> values) {
            addCriterion("upper_value in", values, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueNotIn(List<BigDecimal> values) {
            addCriterion("upper_value not in", values, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("upper_value between", value1, value2, "upperValue");
            return (Criteria) this;
        }

        public Criteria andUpperValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("upper_value not between", value1, value2, "upperValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueIsNull() {
            addCriterion("flat_value is null");
            return (Criteria) this;
        }

        public Criteria andFlatValueIsNotNull() {
            addCriterion("flat_value is not null");
            return (Criteria) this;
        }

        public Criteria andFlatValueEqualTo(BigDecimal value) {
            addCriterion("flat_value =", value, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueNotEqualTo(BigDecimal value) {
            addCriterion("flat_value <>", value, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueGreaterThan(BigDecimal value) {
            addCriterion("flat_value >", value, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("flat_value >=", value, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueLessThan(BigDecimal value) {
            addCriterion("flat_value <", value, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("flat_value <=", value, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueIn(List<BigDecimal> values) {
            addCriterion("flat_value in", values, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueNotIn(List<BigDecimal> values) {
            addCriterion("flat_value not in", values, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flat_value between", value1, value2, "flatValue");
            return (Criteria) this;
        }

        public Criteria andFlatValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flat_value not between", value1, value2, "flatValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueIsNull() {
            addCriterion("peak_value is null");
            return (Criteria) this;
        }

        public Criteria andPeakValueIsNotNull() {
            addCriterion("peak_value is not null");
            return (Criteria) this;
        }

        public Criteria andPeakValueEqualTo(BigDecimal value) {
            addCriterion("peak_value =", value, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueNotEqualTo(BigDecimal value) {
            addCriterion("peak_value <>", value, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueGreaterThan(BigDecimal value) {
            addCriterion("peak_value >", value, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("peak_value >=", value, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueLessThan(BigDecimal value) {
            addCriterion("peak_value <", value, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("peak_value <=", value, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueIn(List<BigDecimal> values) {
            addCriterion("peak_value in", values, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueNotIn(List<BigDecimal> values) {
            addCriterion("peak_value not in", values, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("peak_value between", value1, value2, "peakValue");
            return (Criteria) this;
        }

        public Criteria andPeakValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("peak_value not between", value1, value2, "peakValue");
            return (Criteria) this;
        }

        public Criteria andTopValueIsNull() {
            addCriterion("top_value is null");
            return (Criteria) this;
        }

        public Criteria andTopValueIsNotNull() {
            addCriterion("top_value is not null");
            return (Criteria) this;
        }

        public Criteria andTopValueEqualTo(BigDecimal value) {
            addCriterion("top_value =", value, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueNotEqualTo(BigDecimal value) {
            addCriterion("top_value <>", value, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueGreaterThan(BigDecimal value) {
            addCriterion("top_value >", value, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("top_value >=", value, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueLessThan(BigDecimal value) {
            addCriterion("top_value <", value, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("top_value <=", value, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueIn(List<BigDecimal> values) {
            addCriterion("top_value in", values, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueNotIn(List<BigDecimal> values) {
            addCriterion("top_value not in", values, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("top_value between", value1, value2, "topValue");
            return (Criteria) this;
        }

        public Criteria andTopValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("top_value not between", value1, value2, "topValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueIsNull() {
            addCriterion("valley_value is null");
            return (Criteria) this;
        }

        public Criteria andValleyValueIsNotNull() {
            addCriterion("valley_value is not null");
            return (Criteria) this;
        }

        public Criteria andValleyValueEqualTo(BigDecimal value) {
            addCriterion("valley_value =", value, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueNotEqualTo(BigDecimal value) {
            addCriterion("valley_value <>", value, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueGreaterThan(BigDecimal value) {
            addCriterion("valley_value >", value, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("valley_value >=", value, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueLessThan(BigDecimal value) {
            addCriterion("valley_value <", value, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("valley_value <=", value, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueIn(List<BigDecimal> values) {
            addCriterion("valley_value in", values, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueNotIn(List<BigDecimal> values) {
            addCriterion("valley_value not in", values, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valley_value between", value1, value2, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andValleyValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valley_value not between", value1, value2, "valleyValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueIsNull() {
            addCriterion("flat_upper_value is null");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueIsNotNull() {
            addCriterion("flat_upper_value is not null");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueEqualTo(BigDecimal value) {
            addCriterion("flat_upper_value =", value, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueNotEqualTo(BigDecimal value) {
            addCriterion("flat_upper_value <>", value, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueGreaterThan(BigDecimal value) {
            addCriterion("flat_upper_value >", value, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("flat_upper_value >=", value, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueLessThan(BigDecimal value) {
            addCriterion("flat_upper_value <", value, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("flat_upper_value <=", value, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueIn(List<BigDecimal> values) {
            addCriterion("flat_upper_value in", values, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueNotIn(List<BigDecimal> values) {
            addCriterion("flat_upper_value not in", values, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flat_upper_value between", value1, value2, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andFlatUpperValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("flat_upper_value not between", value1, value2, "flatUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueIsNull() {
            addCriterion("peak_upper_value is null");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueIsNotNull() {
            addCriterion("peak_upper_value is not null");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueEqualTo(BigDecimal value) {
            addCriterion("peak_upper_value =", value, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueNotEqualTo(BigDecimal value) {
            addCriterion("peak_upper_value <>", value, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueGreaterThan(BigDecimal value) {
            addCriterion("peak_upper_value >", value, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("peak_upper_value >=", value, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueLessThan(BigDecimal value) {
            addCriterion("peak_upper_value <", value, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("peak_upper_value <=", value, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueIn(List<BigDecimal> values) {
            addCriterion("peak_upper_value in", values, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueNotIn(List<BigDecimal> values) {
            addCriterion("peak_upper_value not in", values, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("peak_upper_value between", value1, value2, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andPeakUpperValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("peak_upper_value not between", value1, value2, "peakUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueIsNull() {
            addCriterion("valley_upper_value is null");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueIsNotNull() {
            addCriterion("valley_upper_value is not null");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueEqualTo(BigDecimal value) {
            addCriterion("valley_upper_value =", value, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueNotEqualTo(BigDecimal value) {
            addCriterion("valley_upper_value <>", value, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueGreaterThan(BigDecimal value) {
            addCriterion("valley_upper_value >", value, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("valley_upper_value >=", value, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueLessThan(BigDecimal value) {
            addCriterion("valley_upper_value <", value, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("valley_upper_value <=", value, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueIn(List<BigDecimal> values) {
            addCriterion("valley_upper_value in", values, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueNotIn(List<BigDecimal> values) {
            addCriterion("valley_upper_value not in", values, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valley_upper_value between", value1, value2, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andValleyUpperValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("valley_upper_value not between", value1, value2, "valleyUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueIsNull() {
            addCriterion("top_upper_value is null");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueIsNotNull() {
            addCriterion("top_upper_value is not null");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueEqualTo(BigDecimal value) {
            addCriterion("top_upper_value =", value, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueNotEqualTo(BigDecimal value) {
            addCriterion("top_upper_value <>", value, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueGreaterThan(BigDecimal value) {
            addCriterion("top_upper_value >", value, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("top_upper_value >=", value, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueLessThan(BigDecimal value) {
            addCriterion("top_upper_value <", value, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("top_upper_value <=", value, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueIn(List<BigDecimal> values) {
            addCriterion("top_upper_value in", values, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueNotIn(List<BigDecimal> values) {
            addCriterion("top_upper_value not in", values, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("top_upper_value between", value1, value2, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andTopUpperValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("top_upper_value not between", value1, value2, "topUpperValue");
            return (Criteria) this;
        }

        public Criteria andMeterTypeIsNull() {
            addCriterion("meter_type is null");
            return (Criteria) this;
        }

        public Criteria andMeterTypeIsNotNull() {
            addCriterion("meter_type is not null");
            return (Criteria) this;
        }

        public Criteria andMeterTypeEqualTo(Integer value) {
            addCriterion("meter_type =", value, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeNotEqualTo(Integer value) {
            addCriterion("meter_type <>", value, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeGreaterThan(Integer value) {
            addCriterion("meter_type >", value, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("meter_type >=", value, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeLessThan(Integer value) {
            addCriterion("meter_type <", value, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeLessThanOrEqualTo(Integer value) {
            addCriterion("meter_type <=", value, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeIn(List<Integer> values) {
            addCriterion("meter_type in", values, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeNotIn(List<Integer> values) {
            addCriterion("meter_type not in", values, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeBetween(Integer value1, Integer value2) {
            addCriterion("meter_type between", value1, value2, "meterType");
            return (Criteria) this;
        }

        public Criteria andMeterTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("meter_type not between", value1, value2, "meterType");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyIsNull() {
            addCriterion("electricmeter_multiply is null");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyIsNotNull() {
            addCriterion("electricmeter_multiply is not null");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyEqualTo(BigDecimal value) {
            addCriterion("electricmeter_multiply =", value, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyNotEqualTo(BigDecimal value) {
            addCriterion("electricmeter_multiply <>", value, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyGreaterThan(BigDecimal value) {
            addCriterion("electricmeter_multiply >", value, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("electricmeter_multiply >=", value, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyLessThan(BigDecimal value) {
            addCriterion("electricmeter_multiply <", value, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("electricmeter_multiply <=", value, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyIn(List<BigDecimal> values) {
            addCriterion("electricmeter_multiply in", values, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyNotIn(List<BigDecimal> values) {
            addCriterion("electricmeter_multiply not in", values, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("electricmeter_multiply between", value1, value2, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andElectricmeterMultiplyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("electricmeter_multiply not between", value1, value2, "electricmeterMultiply");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIsNull() {
            addCriterion("account_number is null");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIsNotNull() {
            addCriterion("account_number is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNumberEqualTo(String value) {
            addCriterion("account_number =", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotEqualTo(String value) {
            addCriterion("account_number <>", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThan(String value) {
            addCriterion("account_number >", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberGreaterThanOrEqualTo(String value) {
            addCriterion("account_number >=", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThan(String value) {
            addCriterion("account_number <", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLessThanOrEqualTo(String value) {
            addCriterion("account_number <=", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberLike(String value) {
            addCriterion("account_number like", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotLike(String value) {
            addCriterion("account_number not like", value, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberIn(List<String> values) {
            addCriterion("account_number in", values, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotIn(List<String> values) {
            addCriterion("account_number not in", values, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberBetween(String value1, String value2) {
            addCriterion("account_number between", value1, value2, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andAccountNumberNotBetween(String value1, String value2) {
            addCriterion("account_number not between", value1, value2, "accountNumber");
            return (Criteria) this;
        }

        public Criteria andInstallDateIsNull() {
            addCriterion("install_date is null");
            return (Criteria) this;
        }

        public Criteria andInstallDateIsNotNull() {
            addCriterion("install_date is not null");
            return (Criteria) this;
        }

        public Criteria andInstallDateEqualTo(Date value) {
            addCriterion("install_date =", value, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateNotEqualTo(Date value) {
            addCriterion("install_date <>", value, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateGreaterThan(Date value) {
            addCriterion("install_date >", value, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateGreaterThanOrEqualTo(Date value) {
            addCriterion("install_date >=", value, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateLessThan(Date value) {
            addCriterion("install_date <", value, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateLessThanOrEqualTo(Date value) {
            addCriterion("install_date <=", value, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateIn(List<Date> values) {
            addCriterion("install_date in", values, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateNotIn(List<Date> values) {
            addCriterion("install_date not in", values, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateBetween(Date value1, Date value2) {
            addCriterion("install_date between", value1, value2, "installDate");
            return (Criteria) this;
        }

        public Criteria andInstallDateNotBetween(Date value1, Date value2) {
            addCriterion("install_date not between", value1, value2, "installDate");
            return (Criteria) this;
        }

        public Criteria andMeterStateIsNull() {
            addCriterion("meter_state is null");
            return (Criteria) this;
        }

        public Criteria andMeterStateIsNotNull() {
            addCriterion("meter_state is not null");
            return (Criteria) this;
        }

        public Criteria andMeterStateEqualTo(Integer value) {
            addCriterion("meter_state =", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotEqualTo(Integer value) {
            addCriterion("meter_state <>", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateGreaterThan(Integer value) {
            addCriterion("meter_state >", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("meter_state >=", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateLessThan(Integer value) {
            addCriterion("meter_state <", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateLessThanOrEqualTo(Integer value) {
            addCriterion("meter_state <=", value, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateIn(List<Integer> values) {
            addCriterion("meter_state in", values, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotIn(List<Integer> values) {
            addCriterion("meter_state not in", values, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateBetween(Integer value1, Integer value2) {
            addCriterion("meter_state between", value1, value2, "meterState");
            return (Criteria) this;
        }

        public Criteria andMeterStateNotBetween(Integer value1, Integer value2) {
            addCriterion("meter_state not between", value1, value2, "meterState");
            return (Criteria) this;
        }

        public Criteria andIsShareIsNull() {
            addCriterion("is_share is null");
            return (Criteria) this;
        }

        public Criteria andIsShareIsNotNull() {
            addCriterion("is_share is not null");
            return (Criteria) this;
        }

        public Criteria andIsShareEqualTo(Integer value) {
            addCriterion("is_share =", value, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareNotEqualTo(Integer value) {
            addCriterion("is_share <>", value, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareGreaterThan(Integer value) {
            addCriterion("is_share >", value, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_share >=", value, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareLessThan(Integer value) {
            addCriterion("is_share <", value, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareLessThanOrEqualTo(Integer value) {
            addCriterion("is_share <=", value, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareIn(List<Integer> values) {
            addCriterion("is_share in", values, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareNotIn(List<Integer> values) {
            addCriterion("is_share not in", values, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareBetween(Integer value1, Integer value2) {
            addCriterion("is_share between", value1, value2, "isShare");
            return (Criteria) this;
        }

        public Criteria andIsShareNotBetween(Integer value1, Integer value2) {
            addCriterion("is_share not between", value1, value2, "isShare");
            return (Criteria) this;
        }

        public Criteria andMeterNoteIsNull() {
            addCriterion("meter_note is null");
            return (Criteria) this;
        }

        public Criteria andMeterNoteIsNotNull() {
            addCriterion("meter_note is not null");
            return (Criteria) this;
        }

        public Criteria andMeterNoteEqualTo(String value) {
            addCriterion("meter_note =", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteNotEqualTo(String value) {
            addCriterion("meter_note <>", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteGreaterThan(String value) {
            addCriterion("meter_note >", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteGreaterThanOrEqualTo(String value) {
            addCriterion("meter_note >=", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteLessThan(String value) {
            addCriterion("meter_note <", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteLessThanOrEqualTo(String value) {
            addCriterion("meter_note <=", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteLike(String value) {
            addCriterion("meter_note like", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteNotLike(String value) {
            addCriterion("meter_note not like", value, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteIn(List<String> values) {
            addCriterion("meter_note in", values, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteNotIn(List<String> values) {
            addCriterion("meter_note not in", values, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteBetween(String value1, String value2) {
            addCriterion("meter_note between", value1, value2, "meterNote");
            return (Criteria) this;
        }

        public Criteria andMeterNoteNotBetween(String value1, String value2) {
            addCriterion("meter_note not between", value1, value2, "meterNote");
            return (Criteria) this;
        }

        public Criteria andDataFromIsNull() {
            addCriterion("data_from is null");
            return (Criteria) this;
        }

        public Criteria andDataFromIsNotNull() {
            addCriterion("data_from is not null");
            return (Criteria) this;
        }

        public Criteria andDataFromEqualTo(Integer value) {
            addCriterion("data_from =", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotEqualTo(Integer value) {
            addCriterion("data_from <>", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromGreaterThan(Integer value) {
            addCriterion("data_from >", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_from >=", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromLessThan(Integer value) {
            addCriterion("data_from <", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromLessThanOrEqualTo(Integer value) {
            addCriterion("data_from <=", value, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromIn(List<Integer> values) {
            addCriterion("data_from in", values, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotIn(List<Integer> values) {
            addCriterion("data_from not in", values, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromBetween(Integer value1, Integer value2) {
            addCriterion("data_from between", value1, value2, "dataFrom");
            return (Criteria) this;
        }

        public Criteria andDataFromNotBetween(Integer value1, Integer value2) {
            addCriterion("data_from not between", value1, value2, "dataFrom");
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