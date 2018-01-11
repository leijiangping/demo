package com.xunge.model.basedata.ring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeterPerfVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MeterPerfVOExample() {
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

        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeEqualTo(Integer value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(Integer value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(Integer value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(Integer value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<Integer> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<Integer> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(Integer value1, Integer value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceCodeIsNull() {
            addCriterion("resource_code is null");
            return (Criteria) this;
        }

        public Criteria andResourceCodeIsNotNull() {
            addCriterion("resource_code is not null");
            return (Criteria) this;
        }

        public Criteria andResourceCodeEqualTo(String value) {
            addCriterion("resource_code =", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeNotEqualTo(String value) {
            addCriterion("resource_code <>", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeGreaterThan(String value) {
            addCriterion("resource_code >", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("resource_code >=", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeLessThan(String value) {
            addCriterion("resource_code <", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeLessThanOrEqualTo(String value) {
            addCriterion("resource_code <=", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeLike(String value) {
            addCriterion("resource_code like", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeNotLike(String value) {
            addCriterion("resource_code not like", value, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeIn(List<String> values) {
            addCriterion("resource_code in", values, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeNotIn(List<String> values) {
            addCriterion("resource_code not in", values, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeBetween(String value1, String value2) {
            addCriterion("resource_code between", value1, value2, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andResourceCodeNotBetween(String value1, String value2) {
            addCriterion("resource_code not between", value1, value2, "resourceCode");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIsNull() {
            addCriterion("code_type is null");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIsNotNull() {
            addCriterion("code_type is not null");
            return (Criteria) this;
        }

        public Criteria andCodeTypeEqualTo(Integer value) {
            addCriterion("code_type =", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotEqualTo(Integer value) {
            addCriterion("code_type <>", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeGreaterThan(Integer value) {
            addCriterion("code_type >", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("code_type >=", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLessThan(Integer value) {
            addCriterion("code_type <", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("code_type <=", value, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeIn(List<Integer> values) {
            addCriterion("code_type in", values, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotIn(List<Integer> values) {
            addCriterion("code_type not in", values, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeBetween(Integer value1, Integer value2) {
            addCriterion("code_type between", value1, value2, "codeType");
            return (Criteria) this;
        }

        public Criteria andCodeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("code_type not between", value1, value2, "codeType");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNull() {
            addCriterion("resource_name is null");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNotNull() {
            addCriterion("resource_name is not null");
            return (Criteria) this;
        }

        public Criteria andResourceNameEqualTo(String value) {
            addCriterion("resource_name =", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotEqualTo(String value) {
            addCriterion("resource_name <>", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThan(String value) {
            addCriterion("resource_name >", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("resource_name >=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThan(String value) {
            addCriterion("resource_name <", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThanOrEqualTo(String value) {
            addCriterion("resource_name <=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLike(String value) {
            addCriterion("resource_name like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotLike(String value) {
            addCriterion("resource_name not like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameIn(List<String> values) {
            addCriterion("resource_name in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotIn(List<String> values) {
            addCriterion("resource_name not in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameBetween(String value1, String value2) {
            addCriterion("resource_name between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotBetween(String value1, String value2) {
            addCriterion("resource_name not between", value1, value2, "resourceName");
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

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeIsNull() {
            addCriterion("stop_time is null");
            return (Criteria) this;
        }

        public Criteria andStopTimeIsNotNull() {
            addCriterion("stop_time is not null");
            return (Criteria) this;
        }

        public Criteria andStopTimeEqualTo(Date value) {
            addCriterion("stop_time =", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotEqualTo(Date value) {
            addCriterion("stop_time <>", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeGreaterThan(Date value) {
            addCriterion("stop_time >", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("stop_time >=", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLessThan(Date value) {
            addCriterion("stop_time <", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeLessThanOrEqualTo(Date value) {
            addCriterion("stop_time <=", value, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeIn(List<Date> values) {
            addCriterion("stop_time in", values, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotIn(List<Date> values) {
            addCriterion("stop_time not in", values, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeBetween(Date value1, Date value2) {
            addCriterion("stop_time between", value1, value2, "stopTime");
            return (Criteria) this;
        }

        public Criteria andStopTimeNotBetween(Date value1, Date value2) {
            addCriterion("stop_time not between", value1, value2, "stopTime");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeIsNull() {
            addCriterion("total_degree is null");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeIsNotNull() {
            addCriterion("total_degree is not null");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeEqualTo(Float value) {
            addCriterion("total_degree =", value, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeNotEqualTo(Float value) {
            addCriterion("total_degree <>", value, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeGreaterThan(Float value) {
            addCriterion("total_degree >", value, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeGreaterThanOrEqualTo(Float value) {
            addCriterion("total_degree >=", value, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeLessThan(Float value) {
            addCriterion("total_degree <", value, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeLessThanOrEqualTo(Float value) {
            addCriterion("total_degree <=", value, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeIn(List<Float> values) {
            addCriterion("total_degree in", values, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeNotIn(List<Float> values) {
            addCriterion("total_degree not in", values, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeBetween(Float value1, Float value2) {
            addCriterion("total_degree between", value1, value2, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalDegreeNotBetween(Float value1, Float value2) {
            addCriterion("total_degree not between", value1, value2, "totalDegree");
            return (Criteria) this;
        }

        public Criteria andTotalStateIsNull() {
            addCriterion("total_state is null");
            return (Criteria) this;
        }

        public Criteria andTotalStateIsNotNull() {
            addCriterion("total_state is not null");
            return (Criteria) this;
        }

        public Criteria andTotalStateEqualTo(Integer value) {
            addCriterion("total_state =", value, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateNotEqualTo(Integer value) {
            addCriterion("total_state <>", value, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateGreaterThan(Integer value) {
            addCriterion("total_state >", value, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_state >=", value, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateLessThan(Integer value) {
            addCriterion("total_state <", value, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateLessThanOrEqualTo(Integer value) {
            addCriterion("total_state <=", value, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateIn(List<Integer> values) {
            addCriterion("total_state in", values, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateNotIn(List<Integer> values) {
            addCriterion("total_state not in", values, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateBetween(Integer value1, Integer value2) {
            addCriterion("total_state between", value1, value2, "totalState");
            return (Criteria) this;
        }

        public Criteria andTotalStateNotBetween(Integer value1, Integer value2) {
            addCriterion("total_state not between", value1, value2, "totalState");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeIsNull() {
            addCriterion("equipment_degree is null");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeIsNotNull() {
            addCriterion("equipment_degree is not null");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeEqualTo(Float value) {
            addCriterion("equipment_degree =", value, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeNotEqualTo(Float value) {
            addCriterion("equipment_degree <>", value, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeGreaterThan(Float value) {
            addCriterion("equipment_degree >", value, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeGreaterThanOrEqualTo(Float value) {
            addCriterion("equipment_degree >=", value, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeLessThan(Float value) {
            addCriterion("equipment_degree <", value, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeLessThanOrEqualTo(Float value) {
            addCriterion("equipment_degree <=", value, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeIn(List<Float> values) {
            addCriterion("equipment_degree in", values, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeNotIn(List<Float> values) {
            addCriterion("equipment_degree not in", values, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeBetween(Float value1, Float value2) {
            addCriterion("equipment_degree between", value1, value2, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentDegreeNotBetween(Float value1, Float value2) {
            addCriterion("equipment_degree not between", value1, value2, "equipmentDegree");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateIsNull() {
            addCriterion("equipment_state is null");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateIsNotNull() {
            addCriterion("equipment_state is not null");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateEqualTo(Integer value) {
            addCriterion("equipment_state =", value, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateNotEqualTo(Integer value) {
            addCriterion("equipment_state <>", value, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateGreaterThan(Integer value) {
            addCriterion("equipment_state >", value, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("equipment_state >=", value, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateLessThan(Integer value) {
            addCriterion("equipment_state <", value, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateLessThanOrEqualTo(Integer value) {
            addCriterion("equipment_state <=", value, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateIn(List<Integer> values) {
            addCriterion("equipment_state in", values, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateNotIn(List<Integer> values) {
            addCriterion("equipment_state not in", values, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateBetween(Integer value1, Integer value2) {
            addCriterion("equipment_state between", value1, value2, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andEquipmentStateNotBetween(Integer value1, Integer value2) {
            addCriterion("equipment_state not between", value1, value2, "equipmentState");
            return (Criteria) this;
        }

        public Criteria andAcDegreeIsNull() {
            addCriterion("ac_degree is null");
            return (Criteria) this;
        }

        public Criteria andAcDegreeIsNotNull() {
            addCriterion("ac_degree is not null");
            return (Criteria) this;
        }

        public Criteria andAcDegreeEqualTo(Float value) {
            addCriterion("ac_degree =", value, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeNotEqualTo(Float value) {
            addCriterion("ac_degree <>", value, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeGreaterThan(Float value) {
            addCriterion("ac_degree >", value, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeGreaterThanOrEqualTo(Float value) {
            addCriterion("ac_degree >=", value, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeLessThan(Float value) {
            addCriterion("ac_degree <", value, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeLessThanOrEqualTo(Float value) {
            addCriterion("ac_degree <=", value, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeIn(List<Float> values) {
            addCriterion("ac_degree in", values, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeNotIn(List<Float> values) {
            addCriterion("ac_degree not in", values, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeBetween(Float value1, Float value2) {
            addCriterion("ac_degree between", value1, value2, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcDegreeNotBetween(Float value1, Float value2) {
            addCriterion("ac_degree not between", value1, value2, "acDegree");
            return (Criteria) this;
        }

        public Criteria andAcStateIsNull() {
            addCriterion("ac_state is null");
            return (Criteria) this;
        }

        public Criteria andAcStateIsNotNull() {
            addCriterion("ac_state is not null");
            return (Criteria) this;
        }

        public Criteria andAcStateEqualTo(Integer value) {
            addCriterion("ac_state =", value, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateNotEqualTo(Integer value) {
            addCriterion("ac_state <>", value, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateGreaterThan(Integer value) {
            addCriterion("ac_state >", value, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("ac_state >=", value, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateLessThan(Integer value) {
            addCriterion("ac_state <", value, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateLessThanOrEqualTo(Integer value) {
            addCriterion("ac_state <=", value, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateIn(List<Integer> values) {
            addCriterion("ac_state in", values, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateNotIn(List<Integer> values) {
            addCriterion("ac_state not in", values, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateBetween(Integer value1, Integer value2) {
            addCriterion("ac_state between", value1, value2, "acState");
            return (Criteria) this;
        }

        public Criteria andAcStateNotBetween(Integer value1, Integer value2) {
            addCriterion("ac_state not between", value1, value2, "acState");
            return (Criteria) this;
        }

        public Criteria andExTempretureIsNull() {
            addCriterion("ex_tempreture is null");
            return (Criteria) this;
        }

        public Criteria andExTempretureIsNotNull() {
            addCriterion("ex_tempreture is not null");
            return (Criteria) this;
        }

        public Criteria andExTempretureEqualTo(Float value) {
            addCriterion("ex_tempreture =", value, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureNotEqualTo(Float value) {
            addCriterion("ex_tempreture <>", value, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureGreaterThan(Float value) {
            addCriterion("ex_tempreture >", value, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureGreaterThanOrEqualTo(Float value) {
            addCriterion("ex_tempreture >=", value, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureLessThan(Float value) {
            addCriterion("ex_tempreture <", value, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureLessThanOrEqualTo(Float value) {
            addCriterion("ex_tempreture <=", value, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureIn(List<Float> values) {
            addCriterion("ex_tempreture in", values, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureNotIn(List<Float> values) {
            addCriterion("ex_tempreture not in", values, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureBetween(Float value1, Float value2) {
            addCriterion("ex_tempreture between", value1, value2, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andExTempretureNotBetween(Float value1, Float value2) {
            addCriterion("ex_tempreture not between", value1, value2, "exTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureIsNull() {
            addCriterion("in_tempreture is null");
            return (Criteria) this;
        }

        public Criteria andInTempretureIsNotNull() {
            addCriterion("in_tempreture is not null");
            return (Criteria) this;
        }

        public Criteria andInTempretureEqualTo(Float value) {
            addCriterion("in_tempreture =", value, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureNotEqualTo(Float value) {
            addCriterion("in_tempreture <>", value, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureGreaterThan(Float value) {
            addCriterion("in_tempreture >", value, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureGreaterThanOrEqualTo(Float value) {
            addCriterion("in_tempreture >=", value, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureLessThan(Float value) {
            addCriterion("in_tempreture <", value, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureLessThanOrEqualTo(Float value) {
            addCriterion("in_tempreture <=", value, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureIn(List<Float> values) {
            addCriterion("in_tempreture in", values, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureNotIn(List<Float> values) {
            addCriterion("in_tempreture not in", values, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureBetween(Float value1, Float value2) {
            addCriterion("in_tempreture between", value1, value2, "inTempreture");
            return (Criteria) this;
        }

        public Criteria andInTempretureNotBetween(Float value1, Float value2) {
            addCriterion("in_tempreture not between", value1, value2, "inTempreture");
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