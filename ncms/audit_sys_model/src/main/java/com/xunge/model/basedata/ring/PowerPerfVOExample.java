package com.xunge.model.basedata.ring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PowerPerfVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PowerPerfVOExample() {
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

        public Criteria andPowerIdIsNull() {
            addCriterion("power_id is null");
            return (Criteria) this;
        }

        public Criteria andPowerIdIsNotNull() {
            addCriterion("power_id is not null");
            return (Criteria) this;
        }

        public Criteria andPowerIdEqualTo(String value) {
            addCriterion("power_id =", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdNotEqualTo(String value) {
            addCriterion("power_id <>", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdGreaterThan(String value) {
            addCriterion("power_id >", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdGreaterThanOrEqualTo(String value) {
            addCriterion("power_id >=", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdLessThan(String value) {
            addCriterion("power_id <", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdLessThanOrEqualTo(String value) {
            addCriterion("power_id <=", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdLike(String value) {
            addCriterion("power_id like", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdNotLike(String value) {
            addCriterion("power_id not like", value, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdIn(List<String> values) {
            addCriterion("power_id in", values, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdNotIn(List<String> values) {
            addCriterion("power_id not in", values, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdBetween(String value1, String value2) {
            addCriterion("power_id between", value1, value2, "powerId");
            return (Criteria) this;
        }

        public Criteria andPowerIdNotBetween(String value1, String value2) {
            addCriterion("power_id not between", value1, value2, "powerId");
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

        public Criteria andSmpsLossIsNull() {
            addCriterion("smps_loss is null");
            return (Criteria) this;
        }

        public Criteria andSmpsLossIsNotNull() {
            addCriterion("smps_loss is not null");
            return (Criteria) this;
        }

        public Criteria andSmpsLossEqualTo(Float value) {
            addCriterion("smps_loss =", value, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossNotEqualTo(Float value) {
            addCriterion("smps_loss <>", value, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossGreaterThan(Float value) {
            addCriterion("smps_loss >", value, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossGreaterThanOrEqualTo(Float value) {
            addCriterion("smps_loss >=", value, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossLessThan(Float value) {
            addCriterion("smps_loss <", value, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossLessThanOrEqualTo(Float value) {
            addCriterion("smps_loss <=", value, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossIn(List<Float> values) {
            addCriterion("smps_loss in", values, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossNotIn(List<Float> values) {
            addCriterion("smps_loss not in", values, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossBetween(Float value1, Float value2) {
            addCriterion("smps_loss between", value1, value2, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andSmpsLossNotBetween(Float value1, Float value2) {
            addCriterion("smps_loss not between", value1, value2, "smpsLoss");
            return (Criteria) this;
        }

        public Criteria andV1IsNull() {
            addCriterion("v1 is null");
            return (Criteria) this;
        }

        public Criteria andV1IsNotNull() {
            addCriterion("v1 is not null");
            return (Criteria) this;
        }

        public Criteria andV1EqualTo(Float value) {
            addCriterion("v1 =", value, "v1");
            return (Criteria) this;
        }

        public Criteria andV1NotEqualTo(Float value) {
            addCriterion("v1 <>", value, "v1");
            return (Criteria) this;
        }

        public Criteria andV1GreaterThan(Float value) {
            addCriterion("v1 >", value, "v1");
            return (Criteria) this;
        }

        public Criteria andV1GreaterThanOrEqualTo(Float value) {
            addCriterion("v1 >=", value, "v1");
            return (Criteria) this;
        }

        public Criteria andV1LessThan(Float value) {
            addCriterion("v1 <", value, "v1");
            return (Criteria) this;
        }

        public Criteria andV1LessThanOrEqualTo(Float value) {
            addCriterion("v1 <=", value, "v1");
            return (Criteria) this;
        }

        public Criteria andV1In(List<Float> values) {
            addCriterion("v1 in", values, "v1");
            return (Criteria) this;
        }

        public Criteria andV1NotIn(List<Float> values) {
            addCriterion("v1 not in", values, "v1");
            return (Criteria) this;
        }

        public Criteria andV1Between(Float value1, Float value2) {
            addCriterion("v1 between", value1, value2, "v1");
            return (Criteria) this;
        }

        public Criteria andV1NotBetween(Float value1, Float value2) {
            addCriterion("v1 not between", value1, value2, "v1");
            return (Criteria) this;
        }

        public Criteria andA1IsNull() {
            addCriterion("a1 is null");
            return (Criteria) this;
        }

        public Criteria andA1IsNotNull() {
            addCriterion("a1 is not null");
            return (Criteria) this;
        }

        public Criteria andA1EqualTo(Float value) {
            addCriterion("a1 =", value, "a1");
            return (Criteria) this;
        }

        public Criteria andA1NotEqualTo(Float value) {
            addCriterion("a1 <>", value, "a1");
            return (Criteria) this;
        }

        public Criteria andA1GreaterThan(Float value) {
            addCriterion("a1 >", value, "a1");
            return (Criteria) this;
        }

        public Criteria andA1GreaterThanOrEqualTo(Float value) {
            addCriterion("a1 >=", value, "a1");
            return (Criteria) this;
        }

        public Criteria andA1LessThan(Float value) {
            addCriterion("a1 <", value, "a1");
            return (Criteria) this;
        }

        public Criteria andA1LessThanOrEqualTo(Float value) {
            addCriterion("a1 <=", value, "a1");
            return (Criteria) this;
        }

        public Criteria andA1In(List<Float> values) {
            addCriterion("a1 in", values, "a1");
            return (Criteria) this;
        }

        public Criteria andA1NotIn(List<Float> values) {
            addCriterion("a1 not in", values, "a1");
            return (Criteria) this;
        }

        public Criteria andA1Between(Float value1, Float value2) {
            addCriterion("a1 between", value1, value2, "a1");
            return (Criteria) this;
        }

        public Criteria andA1NotBetween(Float value1, Float value2) {
            addCriterion("a1 not between", value1, value2, "a1");
            return (Criteria) this;
        }

        public Criteria andV2IsNull() {
            addCriterion("v2 is null");
            return (Criteria) this;
        }

        public Criteria andV2IsNotNull() {
            addCriterion("v2 is not null");
            return (Criteria) this;
        }

        public Criteria andV2EqualTo(Float value) {
            addCriterion("v2 =", value, "v2");
            return (Criteria) this;
        }

        public Criteria andV2NotEqualTo(Float value) {
            addCriterion("v2 <>", value, "v2");
            return (Criteria) this;
        }

        public Criteria andV2GreaterThan(Float value) {
            addCriterion("v2 >", value, "v2");
            return (Criteria) this;
        }

        public Criteria andV2GreaterThanOrEqualTo(Float value) {
            addCriterion("v2 >=", value, "v2");
            return (Criteria) this;
        }

        public Criteria andV2LessThan(Float value) {
            addCriterion("v2 <", value, "v2");
            return (Criteria) this;
        }

        public Criteria andV2LessThanOrEqualTo(Float value) {
            addCriterion("v2 <=", value, "v2");
            return (Criteria) this;
        }

        public Criteria andV2In(List<Float> values) {
            addCriterion("v2 in", values, "v2");
            return (Criteria) this;
        }

        public Criteria andV2NotIn(List<Float> values) {
            addCriterion("v2 not in", values, "v2");
            return (Criteria) this;
        }

        public Criteria andV2Between(Float value1, Float value2) {
            addCriterion("v2 between", value1, value2, "v2");
            return (Criteria) this;
        }

        public Criteria andV2NotBetween(Float value1, Float value2) {
            addCriterion("v2 not between", value1, value2, "v2");
            return (Criteria) this;
        }

        public Criteria andA2IsNull() {
            addCriterion("a2 is null");
            return (Criteria) this;
        }

        public Criteria andA2IsNotNull() {
            addCriterion("a2 is not null");
            return (Criteria) this;
        }

        public Criteria andA2EqualTo(Float value) {
            addCriterion("a2 =", value, "a2");
            return (Criteria) this;
        }

        public Criteria andA2NotEqualTo(Float value) {
            addCriterion("a2 <>", value, "a2");
            return (Criteria) this;
        }

        public Criteria andA2GreaterThan(Float value) {
            addCriterion("a2 >", value, "a2");
            return (Criteria) this;
        }

        public Criteria andA2GreaterThanOrEqualTo(Float value) {
            addCriterion("a2 >=", value, "a2");
            return (Criteria) this;
        }

        public Criteria andA2LessThan(Float value) {
            addCriterion("a2 <", value, "a2");
            return (Criteria) this;
        }

        public Criteria andA2LessThanOrEqualTo(Float value) {
            addCriterion("a2 <=", value, "a2");
            return (Criteria) this;
        }

        public Criteria andA2In(List<Float> values) {
            addCriterion("a2 in", values, "a2");
            return (Criteria) this;
        }

        public Criteria andA2NotIn(List<Float> values) {
            addCriterion("a2 not in", values, "a2");
            return (Criteria) this;
        }

        public Criteria andA2Between(Float value1, Float value2) {
            addCriterion("a2 between", value1, value2, "a2");
            return (Criteria) this;
        }

        public Criteria andA2NotBetween(Float value1, Float value2) {
            addCriterion("a2 not between", value1, value2, "a2");
            return (Criteria) this;
        }

        public Criteria andV3IsNull() {
            addCriterion("v3 is null");
            return (Criteria) this;
        }

        public Criteria andV3IsNotNull() {
            addCriterion("v3 is not null");
            return (Criteria) this;
        }

        public Criteria andV3EqualTo(Float value) {
            addCriterion("v3 =", value, "v3");
            return (Criteria) this;
        }

        public Criteria andV3NotEqualTo(Float value) {
            addCriterion("v3 <>", value, "v3");
            return (Criteria) this;
        }

        public Criteria andV3GreaterThan(Float value) {
            addCriterion("v3 >", value, "v3");
            return (Criteria) this;
        }

        public Criteria andV3GreaterThanOrEqualTo(Float value) {
            addCriterion("v3 >=", value, "v3");
            return (Criteria) this;
        }

        public Criteria andV3LessThan(Float value) {
            addCriterion("v3 <", value, "v3");
            return (Criteria) this;
        }

        public Criteria andV3LessThanOrEqualTo(Float value) {
            addCriterion("v3 <=", value, "v3");
            return (Criteria) this;
        }

        public Criteria andV3In(List<Float> values) {
            addCriterion("v3 in", values, "v3");
            return (Criteria) this;
        }

        public Criteria andV3NotIn(List<Float> values) {
            addCriterion("v3 not in", values, "v3");
            return (Criteria) this;
        }

        public Criteria andV3Between(Float value1, Float value2) {
            addCriterion("v3 between", value1, value2, "v3");
            return (Criteria) this;
        }

        public Criteria andV3NotBetween(Float value1, Float value2) {
            addCriterion("v3 not between", value1, value2, "v3");
            return (Criteria) this;
        }

        public Criteria andA3IsNull() {
            addCriterion("a3 is null");
            return (Criteria) this;
        }

        public Criteria andA3IsNotNull() {
            addCriterion("a3 is not null");
            return (Criteria) this;
        }

        public Criteria andA3EqualTo(Float value) {
            addCriterion("a3 =", value, "a3");
            return (Criteria) this;
        }

        public Criteria andA3NotEqualTo(Float value) {
            addCriterion("a3 <>", value, "a3");
            return (Criteria) this;
        }

        public Criteria andA3GreaterThan(Float value) {
            addCriterion("a3 >", value, "a3");
            return (Criteria) this;
        }

        public Criteria andA3GreaterThanOrEqualTo(Float value) {
            addCriterion("a3 >=", value, "a3");
            return (Criteria) this;
        }

        public Criteria andA3LessThan(Float value) {
            addCriterion("a3 <", value, "a3");
            return (Criteria) this;
        }

        public Criteria andA3LessThanOrEqualTo(Float value) {
            addCriterion("a3 <=", value, "a3");
            return (Criteria) this;
        }

        public Criteria andA3In(List<Float> values) {
            addCriterion("a3 in", values, "a3");
            return (Criteria) this;
        }

        public Criteria andA3NotIn(List<Float> values) {
            addCriterion("a3 not in", values, "a3");
            return (Criteria) this;
        }

        public Criteria andA3Between(Float value1, Float value2) {
            addCriterion("a3 between", value1, value2, "a3");
            return (Criteria) this;
        }

        public Criteria andA3NotBetween(Float value1, Float value2) {
            addCriterion("a3 not between", value1, value2, "a3");
            return (Criteria) this;
        }

        public Criteria andV4IsNull() {
            addCriterion("v4 is null");
            return (Criteria) this;
        }

        public Criteria andV4IsNotNull() {
            addCriterion("v4 is not null");
            return (Criteria) this;
        }

        public Criteria andV4EqualTo(Float value) {
            addCriterion("v4 =", value, "v4");
            return (Criteria) this;
        }

        public Criteria andV4NotEqualTo(Float value) {
            addCriterion("v4 <>", value, "v4");
            return (Criteria) this;
        }

        public Criteria andV4GreaterThan(Float value) {
            addCriterion("v4 >", value, "v4");
            return (Criteria) this;
        }

        public Criteria andV4GreaterThanOrEqualTo(Float value) {
            addCriterion("v4 >=", value, "v4");
            return (Criteria) this;
        }

        public Criteria andV4LessThan(Float value) {
            addCriterion("v4 <", value, "v4");
            return (Criteria) this;
        }

        public Criteria andV4LessThanOrEqualTo(Float value) {
            addCriterion("v4 <=", value, "v4");
            return (Criteria) this;
        }

        public Criteria andV4In(List<Float> values) {
            addCriterion("v4 in", values, "v4");
            return (Criteria) this;
        }

        public Criteria andV4NotIn(List<Float> values) {
            addCriterion("v4 not in", values, "v4");
            return (Criteria) this;
        }

        public Criteria andV4Between(Float value1, Float value2) {
            addCriterion("v4 between", value1, value2, "v4");
            return (Criteria) this;
        }

        public Criteria andV4NotBetween(Float value1, Float value2) {
            addCriterion("v4 not between", value1, value2, "v4");
            return (Criteria) this;
        }

        public Criteria andA4IsNull() {
            addCriterion("a4 is null");
            return (Criteria) this;
        }

        public Criteria andA4IsNotNull() {
            addCriterion("a4 is not null");
            return (Criteria) this;
        }

        public Criteria andA4EqualTo(Float value) {
            addCriterion("a4 =", value, "a4");
            return (Criteria) this;
        }

        public Criteria andA4NotEqualTo(Float value) {
            addCriterion("a4 <>", value, "a4");
            return (Criteria) this;
        }

        public Criteria andA4GreaterThan(Float value) {
            addCriterion("a4 >", value, "a4");
            return (Criteria) this;
        }

        public Criteria andA4GreaterThanOrEqualTo(Float value) {
            addCriterion("a4 >=", value, "a4");
            return (Criteria) this;
        }

        public Criteria andA4LessThan(Float value) {
            addCriterion("a4 <", value, "a4");
            return (Criteria) this;
        }

        public Criteria andA4LessThanOrEqualTo(Float value) {
            addCriterion("a4 <=", value, "a4");
            return (Criteria) this;
        }

        public Criteria andA4In(List<Float> values) {
            addCriterion("a4 in", values, "a4");
            return (Criteria) this;
        }

        public Criteria andA4NotIn(List<Float> values) {
            addCriterion("a4 not in", values, "a4");
            return (Criteria) this;
        }

        public Criteria andA4Between(Float value1, Float value2) {
            addCriterion("a4 between", value1, value2, "a4");
            return (Criteria) this;
        }

        public Criteria andA4NotBetween(Float value1, Float value2) {
            addCriterion("a4 not between", value1, value2, "a4");
            return (Criteria) this;
        }

        public Criteria andV5IsNull() {
            addCriterion("v5 is null");
            return (Criteria) this;
        }

        public Criteria andV5IsNotNull() {
            addCriterion("v5 is not null");
            return (Criteria) this;
        }

        public Criteria andV5EqualTo(Float value) {
            addCriterion("v5 =", value, "v5");
            return (Criteria) this;
        }

        public Criteria andV5NotEqualTo(Float value) {
            addCriterion("v5 <>", value, "v5");
            return (Criteria) this;
        }

        public Criteria andV5GreaterThan(Float value) {
            addCriterion("v5 >", value, "v5");
            return (Criteria) this;
        }

        public Criteria andV5GreaterThanOrEqualTo(Float value) {
            addCriterion("v5 >=", value, "v5");
            return (Criteria) this;
        }

        public Criteria andV5LessThan(Float value) {
            addCriterion("v5 <", value, "v5");
            return (Criteria) this;
        }

        public Criteria andV5LessThanOrEqualTo(Float value) {
            addCriterion("v5 <=", value, "v5");
            return (Criteria) this;
        }

        public Criteria andV5In(List<Float> values) {
            addCriterion("v5 in", values, "v5");
            return (Criteria) this;
        }

        public Criteria andV5NotIn(List<Float> values) {
            addCriterion("v5 not in", values, "v5");
            return (Criteria) this;
        }

        public Criteria andV5Between(Float value1, Float value2) {
            addCriterion("v5 between", value1, value2, "v5");
            return (Criteria) this;
        }

        public Criteria andV5NotBetween(Float value1, Float value2) {
            addCriterion("v5 not between", value1, value2, "v5");
            return (Criteria) this;
        }

        public Criteria andA5IsNull() {
            addCriterion("a5 is null");
            return (Criteria) this;
        }

        public Criteria andA5IsNotNull() {
            addCriterion("a5 is not null");
            return (Criteria) this;
        }

        public Criteria andA5EqualTo(Float value) {
            addCriterion("a5 =", value, "a5");
            return (Criteria) this;
        }

        public Criteria andA5NotEqualTo(Float value) {
            addCriterion("a5 <>", value, "a5");
            return (Criteria) this;
        }

        public Criteria andA5GreaterThan(Float value) {
            addCriterion("a5 >", value, "a5");
            return (Criteria) this;
        }

        public Criteria andA5GreaterThanOrEqualTo(Float value) {
            addCriterion("a5 >=", value, "a5");
            return (Criteria) this;
        }

        public Criteria andA5LessThan(Float value) {
            addCriterion("a5 <", value, "a5");
            return (Criteria) this;
        }

        public Criteria andA5LessThanOrEqualTo(Float value) {
            addCriterion("a5 <=", value, "a5");
            return (Criteria) this;
        }

        public Criteria andA5In(List<Float> values) {
            addCriterion("a5 in", values, "a5");
            return (Criteria) this;
        }

        public Criteria andA5NotIn(List<Float> values) {
            addCriterion("a5 not in", values, "a5");
            return (Criteria) this;
        }

        public Criteria andA5Between(Float value1, Float value2) {
            addCriterion("a5 between", value1, value2, "a5");
            return (Criteria) this;
        }

        public Criteria andA5NotBetween(Float value1, Float value2) {
            addCriterion("a5 not between", value1, value2, "a5");
            return (Criteria) this;
        }

        public Criteria andV6IsNull() {
            addCriterion("v6 is null");
            return (Criteria) this;
        }

        public Criteria andV6IsNotNull() {
            addCriterion("v6 is not null");
            return (Criteria) this;
        }

        public Criteria andV6EqualTo(Float value) {
            addCriterion("v6 =", value, "v6");
            return (Criteria) this;
        }

        public Criteria andV6NotEqualTo(Float value) {
            addCriterion("v6 <>", value, "v6");
            return (Criteria) this;
        }

        public Criteria andV6GreaterThan(Float value) {
            addCriterion("v6 >", value, "v6");
            return (Criteria) this;
        }

        public Criteria andV6GreaterThanOrEqualTo(Float value) {
            addCriterion("v6 >=", value, "v6");
            return (Criteria) this;
        }

        public Criteria andV6LessThan(Float value) {
            addCriterion("v6 <", value, "v6");
            return (Criteria) this;
        }

        public Criteria andV6LessThanOrEqualTo(Float value) {
            addCriterion("v6 <=", value, "v6");
            return (Criteria) this;
        }

        public Criteria andV6In(List<Float> values) {
            addCriterion("v6 in", values, "v6");
            return (Criteria) this;
        }

        public Criteria andV6NotIn(List<Float> values) {
            addCriterion("v6 not in", values, "v6");
            return (Criteria) this;
        }

        public Criteria andV6Between(Float value1, Float value2) {
            addCriterion("v6 between", value1, value2, "v6");
            return (Criteria) this;
        }

        public Criteria andV6NotBetween(Float value1, Float value2) {
            addCriterion("v6 not between", value1, value2, "v6");
            return (Criteria) this;
        }

        public Criteria andA6IsNull() {
            addCriterion("a6 is null");
            return (Criteria) this;
        }

        public Criteria andA6IsNotNull() {
            addCriterion("a6 is not null");
            return (Criteria) this;
        }

        public Criteria andA6EqualTo(Float value) {
            addCriterion("a6 =", value, "a6");
            return (Criteria) this;
        }

        public Criteria andA6NotEqualTo(Float value) {
            addCriterion("a6 <>", value, "a6");
            return (Criteria) this;
        }

        public Criteria andA6GreaterThan(Float value) {
            addCriterion("a6 >", value, "a6");
            return (Criteria) this;
        }

        public Criteria andA6GreaterThanOrEqualTo(Float value) {
            addCriterion("a6 >=", value, "a6");
            return (Criteria) this;
        }

        public Criteria andA6LessThan(Float value) {
            addCriterion("a6 <", value, "a6");
            return (Criteria) this;
        }

        public Criteria andA6LessThanOrEqualTo(Float value) {
            addCriterion("a6 <=", value, "a6");
            return (Criteria) this;
        }

        public Criteria andA6In(List<Float> values) {
            addCriterion("a6 in", values, "a6");
            return (Criteria) this;
        }

        public Criteria andA6NotIn(List<Float> values) {
            addCriterion("a6 not in", values, "a6");
            return (Criteria) this;
        }

        public Criteria andA6Between(Float value1, Float value2) {
            addCriterion("a6 between", value1, value2, "a6");
            return (Criteria) this;
        }

        public Criteria andA6NotBetween(Float value1, Float value2) {
            addCriterion("a6 not between", value1, value2, "a6");
            return (Criteria) this;
        }

        public Criteria andV7IsNull() {
            addCriterion("v7 is null");
            return (Criteria) this;
        }

        public Criteria andV7IsNotNull() {
            addCriterion("v7 is not null");
            return (Criteria) this;
        }

        public Criteria andV7EqualTo(Float value) {
            addCriterion("v7 =", value, "v7");
            return (Criteria) this;
        }

        public Criteria andV7NotEqualTo(Float value) {
            addCriterion("v7 <>", value, "v7");
            return (Criteria) this;
        }

        public Criteria andV7GreaterThan(Float value) {
            addCriterion("v7 >", value, "v7");
            return (Criteria) this;
        }

        public Criteria andV7GreaterThanOrEqualTo(Float value) {
            addCriterion("v7 >=", value, "v7");
            return (Criteria) this;
        }

        public Criteria andV7LessThan(Float value) {
            addCriterion("v7 <", value, "v7");
            return (Criteria) this;
        }

        public Criteria andV7LessThanOrEqualTo(Float value) {
            addCriterion("v7 <=", value, "v7");
            return (Criteria) this;
        }

        public Criteria andV7In(List<Float> values) {
            addCriterion("v7 in", values, "v7");
            return (Criteria) this;
        }

        public Criteria andV7NotIn(List<Float> values) {
            addCriterion("v7 not in", values, "v7");
            return (Criteria) this;
        }

        public Criteria andV7Between(Float value1, Float value2) {
            addCriterion("v7 between", value1, value2, "v7");
            return (Criteria) this;
        }

        public Criteria andV7NotBetween(Float value1, Float value2) {
            addCriterion("v7 not between", value1, value2, "v7");
            return (Criteria) this;
        }

        public Criteria andA7IsNull() {
            addCriterion("a7 is null");
            return (Criteria) this;
        }

        public Criteria andA7IsNotNull() {
            addCriterion("a7 is not null");
            return (Criteria) this;
        }

        public Criteria andA7EqualTo(Float value) {
            addCriterion("a7 =", value, "a7");
            return (Criteria) this;
        }

        public Criteria andA7NotEqualTo(Float value) {
            addCriterion("a7 <>", value, "a7");
            return (Criteria) this;
        }

        public Criteria andA7GreaterThan(Float value) {
            addCriterion("a7 >", value, "a7");
            return (Criteria) this;
        }

        public Criteria andA7GreaterThanOrEqualTo(Float value) {
            addCriterion("a7 >=", value, "a7");
            return (Criteria) this;
        }

        public Criteria andA7LessThan(Float value) {
            addCriterion("a7 <", value, "a7");
            return (Criteria) this;
        }

        public Criteria andA7LessThanOrEqualTo(Float value) {
            addCriterion("a7 <=", value, "a7");
            return (Criteria) this;
        }

        public Criteria andA7In(List<Float> values) {
            addCriterion("a7 in", values, "a7");
            return (Criteria) this;
        }

        public Criteria andA7NotIn(List<Float> values) {
            addCriterion("a7 not in", values, "a7");
            return (Criteria) this;
        }

        public Criteria andA7Between(Float value1, Float value2) {
            addCriterion("a7 between", value1, value2, "a7");
            return (Criteria) this;
        }

        public Criteria andA7NotBetween(Float value1, Float value2) {
            addCriterion("a7 not between", value1, value2, "a7");
            return (Criteria) this;
        }

        public Criteria andV8IsNull() {
            addCriterion("v8 is null");
            return (Criteria) this;
        }

        public Criteria andV8IsNotNull() {
            addCriterion("v8 is not null");
            return (Criteria) this;
        }

        public Criteria andV8EqualTo(Float value) {
            addCriterion("v8 =", value, "v8");
            return (Criteria) this;
        }

        public Criteria andV8NotEqualTo(Float value) {
            addCriterion("v8 <>", value, "v8");
            return (Criteria) this;
        }

        public Criteria andV8GreaterThan(Float value) {
            addCriterion("v8 >", value, "v8");
            return (Criteria) this;
        }

        public Criteria andV8GreaterThanOrEqualTo(Float value) {
            addCriterion("v8 >=", value, "v8");
            return (Criteria) this;
        }

        public Criteria andV8LessThan(Float value) {
            addCriterion("v8 <", value, "v8");
            return (Criteria) this;
        }

        public Criteria andV8LessThanOrEqualTo(Float value) {
            addCriterion("v8 <=", value, "v8");
            return (Criteria) this;
        }

        public Criteria andV8In(List<Float> values) {
            addCriterion("v8 in", values, "v8");
            return (Criteria) this;
        }

        public Criteria andV8NotIn(List<Float> values) {
            addCriterion("v8 not in", values, "v8");
            return (Criteria) this;
        }

        public Criteria andV8Between(Float value1, Float value2) {
            addCriterion("v8 between", value1, value2, "v8");
            return (Criteria) this;
        }

        public Criteria andV8NotBetween(Float value1, Float value2) {
            addCriterion("v8 not between", value1, value2, "v8");
            return (Criteria) this;
        }

        public Criteria andA8IsNull() {
            addCriterion("a8 is null");
            return (Criteria) this;
        }

        public Criteria andA8IsNotNull() {
            addCriterion("a8 is not null");
            return (Criteria) this;
        }

        public Criteria andA8EqualTo(Float value) {
            addCriterion("a8 =", value, "a8");
            return (Criteria) this;
        }

        public Criteria andA8NotEqualTo(Float value) {
            addCriterion("a8 <>", value, "a8");
            return (Criteria) this;
        }

        public Criteria andA8GreaterThan(Float value) {
            addCriterion("a8 >", value, "a8");
            return (Criteria) this;
        }

        public Criteria andA8GreaterThanOrEqualTo(Float value) {
            addCriterion("a8 >=", value, "a8");
            return (Criteria) this;
        }

        public Criteria andA8LessThan(Float value) {
            addCriterion("a8 <", value, "a8");
            return (Criteria) this;
        }

        public Criteria andA8LessThanOrEqualTo(Float value) {
            addCriterion("a8 <=", value, "a8");
            return (Criteria) this;
        }

        public Criteria andA8In(List<Float> values) {
            addCriterion("a8 in", values, "a8");
            return (Criteria) this;
        }

        public Criteria andA8NotIn(List<Float> values) {
            addCriterion("a8 not in", values, "a8");
            return (Criteria) this;
        }

        public Criteria andA8Between(Float value1, Float value2) {
            addCriterion("a8 between", value1, value2, "a8");
            return (Criteria) this;
        }

        public Criteria andA8NotBetween(Float value1, Float value2) {
            addCriterion("a8 not between", value1, value2, "a8");
            return (Criteria) this;
        }

        public Criteria andV9IsNull() {
            addCriterion("v9 is null");
            return (Criteria) this;
        }

        public Criteria andV9IsNotNull() {
            addCriterion("v9 is not null");
            return (Criteria) this;
        }

        public Criteria andV9EqualTo(Float value) {
            addCriterion("v9 =", value, "v9");
            return (Criteria) this;
        }

        public Criteria andV9NotEqualTo(Float value) {
            addCriterion("v9 <>", value, "v9");
            return (Criteria) this;
        }

        public Criteria andV9GreaterThan(Float value) {
            addCriterion("v9 >", value, "v9");
            return (Criteria) this;
        }

        public Criteria andV9GreaterThanOrEqualTo(Float value) {
            addCriterion("v9 >=", value, "v9");
            return (Criteria) this;
        }

        public Criteria andV9LessThan(Float value) {
            addCriterion("v9 <", value, "v9");
            return (Criteria) this;
        }

        public Criteria andV9LessThanOrEqualTo(Float value) {
            addCriterion("v9 <=", value, "v9");
            return (Criteria) this;
        }

        public Criteria andV9In(List<Float> values) {
            addCriterion("v9 in", values, "v9");
            return (Criteria) this;
        }

        public Criteria andV9NotIn(List<Float> values) {
            addCriterion("v9 not in", values, "v9");
            return (Criteria) this;
        }

        public Criteria andV9Between(Float value1, Float value2) {
            addCriterion("v9 between", value1, value2, "v9");
            return (Criteria) this;
        }

        public Criteria andV9NotBetween(Float value1, Float value2) {
            addCriterion("v9 not between", value1, value2, "v9");
            return (Criteria) this;
        }

        public Criteria andA9IsNull() {
            addCriterion("a9 is null");
            return (Criteria) this;
        }

        public Criteria andA9IsNotNull() {
            addCriterion("a9 is not null");
            return (Criteria) this;
        }

        public Criteria andA9EqualTo(Float value) {
            addCriterion("a9 =", value, "a9");
            return (Criteria) this;
        }

        public Criteria andA9NotEqualTo(Float value) {
            addCriterion("a9 <>", value, "a9");
            return (Criteria) this;
        }

        public Criteria andA9GreaterThan(Float value) {
            addCriterion("a9 >", value, "a9");
            return (Criteria) this;
        }

        public Criteria andA9GreaterThanOrEqualTo(Float value) {
            addCriterion("a9 >=", value, "a9");
            return (Criteria) this;
        }

        public Criteria andA9LessThan(Float value) {
            addCriterion("a9 <", value, "a9");
            return (Criteria) this;
        }

        public Criteria andA9LessThanOrEqualTo(Float value) {
            addCriterion("a9 <=", value, "a9");
            return (Criteria) this;
        }

        public Criteria andA9In(List<Float> values) {
            addCriterion("a9 in", values, "a9");
            return (Criteria) this;
        }

        public Criteria andA9NotIn(List<Float> values) {
            addCriterion("a9 not in", values, "a9");
            return (Criteria) this;
        }

        public Criteria andA9Between(Float value1, Float value2) {
            addCriterion("a9 between", value1, value2, "a9");
            return (Criteria) this;
        }

        public Criteria andA9NotBetween(Float value1, Float value2) {
            addCriterion("a9 not between", value1, value2, "a9");
            return (Criteria) this;
        }

        public Criteria andV10IsNull() {
            addCriterion("v10 is null");
            return (Criteria) this;
        }

        public Criteria andV10IsNotNull() {
            addCriterion("v10 is not null");
            return (Criteria) this;
        }

        public Criteria andV10EqualTo(Float value) {
            addCriterion("v10 =", value, "v10");
            return (Criteria) this;
        }

        public Criteria andV10NotEqualTo(Float value) {
            addCriterion("v10 <>", value, "v10");
            return (Criteria) this;
        }

        public Criteria andV10GreaterThan(Float value) {
            addCriterion("v10 >", value, "v10");
            return (Criteria) this;
        }

        public Criteria andV10GreaterThanOrEqualTo(Float value) {
            addCriterion("v10 >=", value, "v10");
            return (Criteria) this;
        }

        public Criteria andV10LessThan(Float value) {
            addCriterion("v10 <", value, "v10");
            return (Criteria) this;
        }

        public Criteria andV10LessThanOrEqualTo(Float value) {
            addCriterion("v10 <=", value, "v10");
            return (Criteria) this;
        }

        public Criteria andV10In(List<Float> values) {
            addCriterion("v10 in", values, "v10");
            return (Criteria) this;
        }

        public Criteria andV10NotIn(List<Float> values) {
            addCriterion("v10 not in", values, "v10");
            return (Criteria) this;
        }

        public Criteria andV10Between(Float value1, Float value2) {
            addCriterion("v10 between", value1, value2, "v10");
            return (Criteria) this;
        }

        public Criteria andV10NotBetween(Float value1, Float value2) {
            addCriterion("v10 not between", value1, value2, "v10");
            return (Criteria) this;
        }

        public Criteria andA10IsNull() {
            addCriterion("a10 is null");
            return (Criteria) this;
        }

        public Criteria andA10IsNotNull() {
            addCriterion("a10 is not null");
            return (Criteria) this;
        }

        public Criteria andA10EqualTo(Float value) {
            addCriterion("a10 =", value, "a10");
            return (Criteria) this;
        }

        public Criteria andA10NotEqualTo(Float value) {
            addCriterion("a10 <>", value, "a10");
            return (Criteria) this;
        }

        public Criteria andA10GreaterThan(Float value) {
            addCriterion("a10 >", value, "a10");
            return (Criteria) this;
        }

        public Criteria andA10GreaterThanOrEqualTo(Float value) {
            addCriterion("a10 >=", value, "a10");
            return (Criteria) this;
        }

        public Criteria andA10LessThan(Float value) {
            addCriterion("a10 <", value, "a10");
            return (Criteria) this;
        }

        public Criteria andA10LessThanOrEqualTo(Float value) {
            addCriterion("a10 <=", value, "a10");
            return (Criteria) this;
        }

        public Criteria andA10In(List<Float> values) {
            addCriterion("a10 in", values, "a10");
            return (Criteria) this;
        }

        public Criteria andA10NotIn(List<Float> values) {
            addCriterion("a10 not in", values, "a10");
            return (Criteria) this;
        }

        public Criteria andA10Between(Float value1, Float value2) {
            addCriterion("a10 between", value1, value2, "a10");
            return (Criteria) this;
        }

        public Criteria andA10NotBetween(Float value1, Float value2) {
            addCriterion("a10 not between", value1, value2, "a10");
            return (Criteria) this;
        }

        public Criteria andV11IsNull() {
            addCriterion("v11 is null");
            return (Criteria) this;
        }

        public Criteria andV11IsNotNull() {
            addCriterion("v11 is not null");
            return (Criteria) this;
        }

        public Criteria andV11EqualTo(Float value) {
            addCriterion("v11 =", value, "v11");
            return (Criteria) this;
        }

        public Criteria andV11NotEqualTo(Float value) {
            addCriterion("v11 <>", value, "v11");
            return (Criteria) this;
        }

        public Criteria andV11GreaterThan(Float value) {
            addCriterion("v11 >", value, "v11");
            return (Criteria) this;
        }

        public Criteria andV11GreaterThanOrEqualTo(Float value) {
            addCriterion("v11 >=", value, "v11");
            return (Criteria) this;
        }

        public Criteria andV11LessThan(Float value) {
            addCriterion("v11 <", value, "v11");
            return (Criteria) this;
        }

        public Criteria andV11LessThanOrEqualTo(Float value) {
            addCriterion("v11 <=", value, "v11");
            return (Criteria) this;
        }

        public Criteria andV11In(List<Float> values) {
            addCriterion("v11 in", values, "v11");
            return (Criteria) this;
        }

        public Criteria andV11NotIn(List<Float> values) {
            addCriterion("v11 not in", values, "v11");
            return (Criteria) this;
        }

        public Criteria andV11Between(Float value1, Float value2) {
            addCriterion("v11 between", value1, value2, "v11");
            return (Criteria) this;
        }

        public Criteria andV11NotBetween(Float value1, Float value2) {
            addCriterion("v11 not between", value1, value2, "v11");
            return (Criteria) this;
        }

        public Criteria andA11IsNull() {
            addCriterion("a11 is null");
            return (Criteria) this;
        }

        public Criteria andA11IsNotNull() {
            addCriterion("a11 is not null");
            return (Criteria) this;
        }

        public Criteria andA11EqualTo(Float value) {
            addCriterion("a11 =", value, "a11");
            return (Criteria) this;
        }

        public Criteria andA11NotEqualTo(Float value) {
            addCriterion("a11 <>", value, "a11");
            return (Criteria) this;
        }

        public Criteria andA11GreaterThan(Float value) {
            addCriterion("a11 >", value, "a11");
            return (Criteria) this;
        }

        public Criteria andA11GreaterThanOrEqualTo(Float value) {
            addCriterion("a11 >=", value, "a11");
            return (Criteria) this;
        }

        public Criteria andA11LessThan(Float value) {
            addCriterion("a11 <", value, "a11");
            return (Criteria) this;
        }

        public Criteria andA11LessThanOrEqualTo(Float value) {
            addCriterion("a11 <=", value, "a11");
            return (Criteria) this;
        }

        public Criteria andA11In(List<Float> values) {
            addCriterion("a11 in", values, "a11");
            return (Criteria) this;
        }

        public Criteria andA11NotIn(List<Float> values) {
            addCriterion("a11 not in", values, "a11");
            return (Criteria) this;
        }

        public Criteria andA11Between(Float value1, Float value2) {
            addCriterion("a11 between", value1, value2, "a11");
            return (Criteria) this;
        }

        public Criteria andA11NotBetween(Float value1, Float value2) {
            addCriterion("a11 not between", value1, value2, "a11");
            return (Criteria) this;
        }

        public Criteria andV12IsNull() {
            addCriterion("v12 is null");
            return (Criteria) this;
        }

        public Criteria andV12IsNotNull() {
            addCriterion("v12 is not null");
            return (Criteria) this;
        }

        public Criteria andV12EqualTo(Float value) {
            addCriterion("v12 =", value, "v12");
            return (Criteria) this;
        }

        public Criteria andV12NotEqualTo(Float value) {
            addCriterion("v12 <>", value, "v12");
            return (Criteria) this;
        }

        public Criteria andV12GreaterThan(Float value) {
            addCriterion("v12 >", value, "v12");
            return (Criteria) this;
        }

        public Criteria andV12GreaterThanOrEqualTo(Float value) {
            addCriterion("v12 >=", value, "v12");
            return (Criteria) this;
        }

        public Criteria andV12LessThan(Float value) {
            addCriterion("v12 <", value, "v12");
            return (Criteria) this;
        }

        public Criteria andV12LessThanOrEqualTo(Float value) {
            addCriterion("v12 <=", value, "v12");
            return (Criteria) this;
        }

        public Criteria andV12In(List<Float> values) {
            addCriterion("v12 in", values, "v12");
            return (Criteria) this;
        }

        public Criteria andV12NotIn(List<Float> values) {
            addCriterion("v12 not in", values, "v12");
            return (Criteria) this;
        }

        public Criteria andV12Between(Float value1, Float value2) {
            addCriterion("v12 between", value1, value2, "v12");
            return (Criteria) this;
        }

        public Criteria andV12NotBetween(Float value1, Float value2) {
            addCriterion("v12 not between", value1, value2, "v12");
            return (Criteria) this;
        }

        public Criteria andA12IsNull() {
            addCriterion("a12 is null");
            return (Criteria) this;
        }

        public Criteria andA12IsNotNull() {
            addCriterion("a12 is not null");
            return (Criteria) this;
        }

        public Criteria andA12EqualTo(Float value) {
            addCriterion("a12 =", value, "a12");
            return (Criteria) this;
        }

        public Criteria andA12NotEqualTo(Float value) {
            addCriterion("a12 <>", value, "a12");
            return (Criteria) this;
        }

        public Criteria andA12GreaterThan(Float value) {
            addCriterion("a12 >", value, "a12");
            return (Criteria) this;
        }

        public Criteria andA12GreaterThanOrEqualTo(Float value) {
            addCriterion("a12 >=", value, "a12");
            return (Criteria) this;
        }

        public Criteria andA12LessThan(Float value) {
            addCriterion("a12 <", value, "a12");
            return (Criteria) this;
        }

        public Criteria andA12LessThanOrEqualTo(Float value) {
            addCriterion("a12 <=", value, "a12");
            return (Criteria) this;
        }

        public Criteria andA12In(List<Float> values) {
            addCriterion("a12 in", values, "a12");
            return (Criteria) this;
        }

        public Criteria andA12NotIn(List<Float> values) {
            addCriterion("a12 not in", values, "a12");
            return (Criteria) this;
        }

        public Criteria andA12Between(Float value1, Float value2) {
            addCriterion("a12 between", value1, value2, "a12");
            return (Criteria) this;
        }

        public Criteria andA12NotBetween(Float value1, Float value2) {
            addCriterion("a12 not between", value1, value2, "a12");
            return (Criteria) this;
        }

        public Criteria andV13IsNull() {
            addCriterion("v13 is null");
            return (Criteria) this;
        }

        public Criteria andV13IsNotNull() {
            addCriterion("v13 is not null");
            return (Criteria) this;
        }

        public Criteria andV13EqualTo(Float value) {
            addCriterion("v13 =", value, "v13");
            return (Criteria) this;
        }

        public Criteria andV13NotEqualTo(Float value) {
            addCriterion("v13 <>", value, "v13");
            return (Criteria) this;
        }

        public Criteria andV13GreaterThan(Float value) {
            addCriterion("v13 >", value, "v13");
            return (Criteria) this;
        }

        public Criteria andV13GreaterThanOrEqualTo(Float value) {
            addCriterion("v13 >=", value, "v13");
            return (Criteria) this;
        }

        public Criteria andV13LessThan(Float value) {
            addCriterion("v13 <", value, "v13");
            return (Criteria) this;
        }

        public Criteria andV13LessThanOrEqualTo(Float value) {
            addCriterion("v13 <=", value, "v13");
            return (Criteria) this;
        }

        public Criteria andV13In(List<Float> values) {
            addCriterion("v13 in", values, "v13");
            return (Criteria) this;
        }

        public Criteria andV13NotIn(List<Float> values) {
            addCriterion("v13 not in", values, "v13");
            return (Criteria) this;
        }

        public Criteria andV13Between(Float value1, Float value2) {
            addCriterion("v13 between", value1, value2, "v13");
            return (Criteria) this;
        }

        public Criteria andV13NotBetween(Float value1, Float value2) {
            addCriterion("v13 not between", value1, value2, "v13");
            return (Criteria) this;
        }

        public Criteria andA13IsNull() {
            addCriterion("a13 is null");
            return (Criteria) this;
        }

        public Criteria andA13IsNotNull() {
            addCriterion("a13 is not null");
            return (Criteria) this;
        }

        public Criteria andA13EqualTo(Float value) {
            addCriterion("a13 =", value, "a13");
            return (Criteria) this;
        }

        public Criteria andA13NotEqualTo(Float value) {
            addCriterion("a13 <>", value, "a13");
            return (Criteria) this;
        }

        public Criteria andA13GreaterThan(Float value) {
            addCriterion("a13 >", value, "a13");
            return (Criteria) this;
        }

        public Criteria andA13GreaterThanOrEqualTo(Float value) {
            addCriterion("a13 >=", value, "a13");
            return (Criteria) this;
        }

        public Criteria andA13LessThan(Float value) {
            addCriterion("a13 <", value, "a13");
            return (Criteria) this;
        }

        public Criteria andA13LessThanOrEqualTo(Float value) {
            addCriterion("a13 <=", value, "a13");
            return (Criteria) this;
        }

        public Criteria andA13In(List<Float> values) {
            addCriterion("a13 in", values, "a13");
            return (Criteria) this;
        }

        public Criteria andA13NotIn(List<Float> values) {
            addCriterion("a13 not in", values, "a13");
            return (Criteria) this;
        }

        public Criteria andA13Between(Float value1, Float value2) {
            addCriterion("a13 between", value1, value2, "a13");
            return (Criteria) this;
        }

        public Criteria andA13NotBetween(Float value1, Float value2) {
            addCriterion("a13 not between", value1, value2, "a13");
            return (Criteria) this;
        }

        public Criteria andV14IsNull() {
            addCriterion("v14 is null");
            return (Criteria) this;
        }

        public Criteria andV14IsNotNull() {
            addCriterion("v14 is not null");
            return (Criteria) this;
        }

        public Criteria andV14EqualTo(Float value) {
            addCriterion("v14 =", value, "v14");
            return (Criteria) this;
        }

        public Criteria andV14NotEqualTo(Float value) {
            addCriterion("v14 <>", value, "v14");
            return (Criteria) this;
        }

        public Criteria andV14GreaterThan(Float value) {
            addCriterion("v14 >", value, "v14");
            return (Criteria) this;
        }

        public Criteria andV14GreaterThanOrEqualTo(Float value) {
            addCriterion("v14 >=", value, "v14");
            return (Criteria) this;
        }

        public Criteria andV14LessThan(Float value) {
            addCriterion("v14 <", value, "v14");
            return (Criteria) this;
        }

        public Criteria andV14LessThanOrEqualTo(Float value) {
            addCriterion("v14 <=", value, "v14");
            return (Criteria) this;
        }

        public Criteria andV14In(List<Float> values) {
            addCriterion("v14 in", values, "v14");
            return (Criteria) this;
        }

        public Criteria andV14NotIn(List<Float> values) {
            addCriterion("v14 not in", values, "v14");
            return (Criteria) this;
        }

        public Criteria andV14Between(Float value1, Float value2) {
            addCriterion("v14 between", value1, value2, "v14");
            return (Criteria) this;
        }

        public Criteria andV14NotBetween(Float value1, Float value2) {
            addCriterion("v14 not between", value1, value2, "v14");
            return (Criteria) this;
        }

        public Criteria andA14IsNull() {
            addCriterion("a14 is null");
            return (Criteria) this;
        }

        public Criteria andA14IsNotNull() {
            addCriterion("a14 is not null");
            return (Criteria) this;
        }

        public Criteria andA14EqualTo(Float value) {
            addCriterion("a14 =", value, "a14");
            return (Criteria) this;
        }

        public Criteria andA14NotEqualTo(Float value) {
            addCriterion("a14 <>", value, "a14");
            return (Criteria) this;
        }

        public Criteria andA14GreaterThan(Float value) {
            addCriterion("a14 >", value, "a14");
            return (Criteria) this;
        }

        public Criteria andA14GreaterThanOrEqualTo(Float value) {
            addCriterion("a14 >=", value, "a14");
            return (Criteria) this;
        }

        public Criteria andA14LessThan(Float value) {
            addCriterion("a14 <", value, "a14");
            return (Criteria) this;
        }

        public Criteria andA14LessThanOrEqualTo(Float value) {
            addCriterion("a14 <=", value, "a14");
            return (Criteria) this;
        }

        public Criteria andA14In(List<Float> values) {
            addCriterion("a14 in", values, "a14");
            return (Criteria) this;
        }

        public Criteria andA14NotIn(List<Float> values) {
            addCriterion("a14 not in", values, "a14");
            return (Criteria) this;
        }

        public Criteria andA14Between(Float value1, Float value2) {
            addCriterion("a14 between", value1, value2, "a14");
            return (Criteria) this;
        }

        public Criteria andA14NotBetween(Float value1, Float value2) {
            addCriterion("a14 not between", value1, value2, "a14");
            return (Criteria) this;
        }

        public Criteria andV15IsNull() {
            addCriterion("v15 is null");
            return (Criteria) this;
        }

        public Criteria andV15IsNotNull() {
            addCriterion("v15 is not null");
            return (Criteria) this;
        }

        public Criteria andV15EqualTo(Float value) {
            addCriterion("v15 =", value, "v15");
            return (Criteria) this;
        }

        public Criteria andV15NotEqualTo(Float value) {
            addCriterion("v15 <>", value, "v15");
            return (Criteria) this;
        }

        public Criteria andV15GreaterThan(Float value) {
            addCriterion("v15 >", value, "v15");
            return (Criteria) this;
        }

        public Criteria andV15GreaterThanOrEqualTo(Float value) {
            addCriterion("v15 >=", value, "v15");
            return (Criteria) this;
        }

        public Criteria andV15LessThan(Float value) {
            addCriterion("v15 <", value, "v15");
            return (Criteria) this;
        }

        public Criteria andV15LessThanOrEqualTo(Float value) {
            addCriterion("v15 <=", value, "v15");
            return (Criteria) this;
        }

        public Criteria andV15In(List<Float> values) {
            addCriterion("v15 in", values, "v15");
            return (Criteria) this;
        }

        public Criteria andV15NotIn(List<Float> values) {
            addCriterion("v15 not in", values, "v15");
            return (Criteria) this;
        }

        public Criteria andV15Between(Float value1, Float value2) {
            addCriterion("v15 between", value1, value2, "v15");
            return (Criteria) this;
        }

        public Criteria andV15NotBetween(Float value1, Float value2) {
            addCriterion("v15 not between", value1, value2, "v15");
            return (Criteria) this;
        }

        public Criteria andA15IsNull() {
            addCriterion("a15 is null");
            return (Criteria) this;
        }

        public Criteria andA15IsNotNull() {
            addCriterion("a15 is not null");
            return (Criteria) this;
        }

        public Criteria andA15EqualTo(Float value) {
            addCriterion("a15 =", value, "a15");
            return (Criteria) this;
        }

        public Criteria andA15NotEqualTo(Float value) {
            addCriterion("a15 <>", value, "a15");
            return (Criteria) this;
        }

        public Criteria andA15GreaterThan(Float value) {
            addCriterion("a15 >", value, "a15");
            return (Criteria) this;
        }

        public Criteria andA15GreaterThanOrEqualTo(Float value) {
            addCriterion("a15 >=", value, "a15");
            return (Criteria) this;
        }

        public Criteria andA15LessThan(Float value) {
            addCriterion("a15 <", value, "a15");
            return (Criteria) this;
        }

        public Criteria andA15LessThanOrEqualTo(Float value) {
            addCriterion("a15 <=", value, "a15");
            return (Criteria) this;
        }

        public Criteria andA15In(List<Float> values) {
            addCriterion("a15 in", values, "a15");
            return (Criteria) this;
        }

        public Criteria andA15NotIn(List<Float> values) {
            addCriterion("a15 not in", values, "a15");
            return (Criteria) this;
        }

        public Criteria andA15Between(Float value1, Float value2) {
            addCriterion("a15 between", value1, value2, "a15");
            return (Criteria) this;
        }

        public Criteria andA15NotBetween(Float value1, Float value2) {
            addCriterion("a15 not between", value1, value2, "a15");
            return (Criteria) this;
        }

        public Criteria andV16IsNull() {
            addCriterion("v16 is null");
            return (Criteria) this;
        }

        public Criteria andV16IsNotNull() {
            addCriterion("v16 is not null");
            return (Criteria) this;
        }

        public Criteria andV16EqualTo(Float value) {
            addCriterion("v16 =", value, "v16");
            return (Criteria) this;
        }

        public Criteria andV16NotEqualTo(Float value) {
            addCriterion("v16 <>", value, "v16");
            return (Criteria) this;
        }

        public Criteria andV16GreaterThan(Float value) {
            addCriterion("v16 >", value, "v16");
            return (Criteria) this;
        }

        public Criteria andV16GreaterThanOrEqualTo(Float value) {
            addCriterion("v16 >=", value, "v16");
            return (Criteria) this;
        }

        public Criteria andV16LessThan(Float value) {
            addCriterion("v16 <", value, "v16");
            return (Criteria) this;
        }

        public Criteria andV16LessThanOrEqualTo(Float value) {
            addCriterion("v16 <=", value, "v16");
            return (Criteria) this;
        }

        public Criteria andV16In(List<Float> values) {
            addCriterion("v16 in", values, "v16");
            return (Criteria) this;
        }

        public Criteria andV16NotIn(List<Float> values) {
            addCriterion("v16 not in", values, "v16");
            return (Criteria) this;
        }

        public Criteria andV16Between(Float value1, Float value2) {
            addCriterion("v16 between", value1, value2, "v16");
            return (Criteria) this;
        }

        public Criteria andV16NotBetween(Float value1, Float value2) {
            addCriterion("v16 not between", value1, value2, "v16");
            return (Criteria) this;
        }

        public Criteria andA16IsNull() {
            addCriterion("a16 is null");
            return (Criteria) this;
        }

        public Criteria andA16IsNotNull() {
            addCriterion("a16 is not null");
            return (Criteria) this;
        }

        public Criteria andA16EqualTo(Float value) {
            addCriterion("a16 =", value, "a16");
            return (Criteria) this;
        }

        public Criteria andA16NotEqualTo(Float value) {
            addCriterion("a16 <>", value, "a16");
            return (Criteria) this;
        }

        public Criteria andA16GreaterThan(Float value) {
            addCriterion("a16 >", value, "a16");
            return (Criteria) this;
        }

        public Criteria andA16GreaterThanOrEqualTo(Float value) {
            addCriterion("a16 >=", value, "a16");
            return (Criteria) this;
        }

        public Criteria andA16LessThan(Float value) {
            addCriterion("a16 <", value, "a16");
            return (Criteria) this;
        }

        public Criteria andA16LessThanOrEqualTo(Float value) {
            addCriterion("a16 <=", value, "a16");
            return (Criteria) this;
        }

        public Criteria andA16In(List<Float> values) {
            addCriterion("a16 in", values, "a16");
            return (Criteria) this;
        }

        public Criteria andA16NotIn(List<Float> values) {
            addCriterion("a16 not in", values, "a16");
            return (Criteria) this;
        }

        public Criteria andA16Between(Float value1, Float value2) {
            addCriterion("a16 between", value1, value2, "a16");
            return (Criteria) this;
        }

        public Criteria andA16NotBetween(Float value1, Float value2) {
            addCriterion("a16 not between", value1, value2, "a16");
            return (Criteria) this;
        }

        public Criteria andV17IsNull() {
            addCriterion("v17 is null");
            return (Criteria) this;
        }

        public Criteria andV17IsNotNull() {
            addCriterion("v17 is not null");
            return (Criteria) this;
        }

        public Criteria andV17EqualTo(Float value) {
            addCriterion("v17 =", value, "v17");
            return (Criteria) this;
        }

        public Criteria andV17NotEqualTo(Float value) {
            addCriterion("v17 <>", value, "v17");
            return (Criteria) this;
        }

        public Criteria andV17GreaterThan(Float value) {
            addCriterion("v17 >", value, "v17");
            return (Criteria) this;
        }

        public Criteria andV17GreaterThanOrEqualTo(Float value) {
            addCriterion("v17 >=", value, "v17");
            return (Criteria) this;
        }

        public Criteria andV17LessThan(Float value) {
            addCriterion("v17 <", value, "v17");
            return (Criteria) this;
        }

        public Criteria andV17LessThanOrEqualTo(Float value) {
            addCriterion("v17 <=", value, "v17");
            return (Criteria) this;
        }

        public Criteria andV17In(List<Float> values) {
            addCriterion("v17 in", values, "v17");
            return (Criteria) this;
        }

        public Criteria andV17NotIn(List<Float> values) {
            addCriterion("v17 not in", values, "v17");
            return (Criteria) this;
        }

        public Criteria andV17Between(Float value1, Float value2) {
            addCriterion("v17 between", value1, value2, "v17");
            return (Criteria) this;
        }

        public Criteria andV17NotBetween(Float value1, Float value2) {
            addCriterion("v17 not between", value1, value2, "v17");
            return (Criteria) this;
        }

        public Criteria andA17IsNull() {
            addCriterion("a17 is null");
            return (Criteria) this;
        }

        public Criteria andA17IsNotNull() {
            addCriterion("a17 is not null");
            return (Criteria) this;
        }

        public Criteria andA17EqualTo(Float value) {
            addCriterion("a17 =", value, "a17");
            return (Criteria) this;
        }

        public Criteria andA17NotEqualTo(Float value) {
            addCriterion("a17 <>", value, "a17");
            return (Criteria) this;
        }

        public Criteria andA17GreaterThan(Float value) {
            addCriterion("a17 >", value, "a17");
            return (Criteria) this;
        }

        public Criteria andA17GreaterThanOrEqualTo(Float value) {
            addCriterion("a17 >=", value, "a17");
            return (Criteria) this;
        }

        public Criteria andA17LessThan(Float value) {
            addCriterion("a17 <", value, "a17");
            return (Criteria) this;
        }

        public Criteria andA17LessThanOrEqualTo(Float value) {
            addCriterion("a17 <=", value, "a17");
            return (Criteria) this;
        }

        public Criteria andA17In(List<Float> values) {
            addCriterion("a17 in", values, "a17");
            return (Criteria) this;
        }

        public Criteria andA17NotIn(List<Float> values) {
            addCriterion("a17 not in", values, "a17");
            return (Criteria) this;
        }

        public Criteria andA17Between(Float value1, Float value2) {
            addCriterion("a17 between", value1, value2, "a17");
            return (Criteria) this;
        }

        public Criteria andA17NotBetween(Float value1, Float value2) {
            addCriterion("a17 not between", value1, value2, "a17");
            return (Criteria) this;
        }

        public Criteria andV18IsNull() {
            addCriterion("v18 is null");
            return (Criteria) this;
        }

        public Criteria andV18IsNotNull() {
            addCriterion("v18 is not null");
            return (Criteria) this;
        }

        public Criteria andV18EqualTo(Float value) {
            addCriterion("v18 =", value, "v18");
            return (Criteria) this;
        }

        public Criteria andV18NotEqualTo(Float value) {
            addCriterion("v18 <>", value, "v18");
            return (Criteria) this;
        }

        public Criteria andV18GreaterThan(Float value) {
            addCriterion("v18 >", value, "v18");
            return (Criteria) this;
        }

        public Criteria andV18GreaterThanOrEqualTo(Float value) {
            addCriterion("v18 >=", value, "v18");
            return (Criteria) this;
        }

        public Criteria andV18LessThan(Float value) {
            addCriterion("v18 <", value, "v18");
            return (Criteria) this;
        }

        public Criteria andV18LessThanOrEqualTo(Float value) {
            addCriterion("v18 <=", value, "v18");
            return (Criteria) this;
        }

        public Criteria andV18In(List<Float> values) {
            addCriterion("v18 in", values, "v18");
            return (Criteria) this;
        }

        public Criteria andV18NotIn(List<Float> values) {
            addCriterion("v18 not in", values, "v18");
            return (Criteria) this;
        }

        public Criteria andV18Between(Float value1, Float value2) {
            addCriterion("v18 between", value1, value2, "v18");
            return (Criteria) this;
        }

        public Criteria andV18NotBetween(Float value1, Float value2) {
            addCriterion("v18 not between", value1, value2, "v18");
            return (Criteria) this;
        }

        public Criteria andA18IsNull() {
            addCriterion("a18 is null");
            return (Criteria) this;
        }

        public Criteria andA18IsNotNull() {
            addCriterion("a18 is not null");
            return (Criteria) this;
        }

        public Criteria andA18EqualTo(Float value) {
            addCriterion("a18 =", value, "a18");
            return (Criteria) this;
        }

        public Criteria andA18NotEqualTo(Float value) {
            addCriterion("a18 <>", value, "a18");
            return (Criteria) this;
        }

        public Criteria andA18GreaterThan(Float value) {
            addCriterion("a18 >", value, "a18");
            return (Criteria) this;
        }

        public Criteria andA18GreaterThanOrEqualTo(Float value) {
            addCriterion("a18 >=", value, "a18");
            return (Criteria) this;
        }

        public Criteria andA18LessThan(Float value) {
            addCriterion("a18 <", value, "a18");
            return (Criteria) this;
        }

        public Criteria andA18LessThanOrEqualTo(Float value) {
            addCriterion("a18 <=", value, "a18");
            return (Criteria) this;
        }

        public Criteria andA18In(List<Float> values) {
            addCriterion("a18 in", values, "a18");
            return (Criteria) this;
        }

        public Criteria andA18NotIn(List<Float> values) {
            addCriterion("a18 not in", values, "a18");
            return (Criteria) this;
        }

        public Criteria andA18Between(Float value1, Float value2) {
            addCriterion("a18 between", value1, value2, "a18");
            return (Criteria) this;
        }

        public Criteria andA18NotBetween(Float value1, Float value2) {
            addCriterion("a18 not between", value1, value2, "a18");
            return (Criteria) this;
        }

        public Criteria andV19IsNull() {
            addCriterion("v19 is null");
            return (Criteria) this;
        }

        public Criteria andV19IsNotNull() {
            addCriterion("v19 is not null");
            return (Criteria) this;
        }

        public Criteria andV19EqualTo(Float value) {
            addCriterion("v19 =", value, "v19");
            return (Criteria) this;
        }

        public Criteria andV19NotEqualTo(Float value) {
            addCriterion("v19 <>", value, "v19");
            return (Criteria) this;
        }

        public Criteria andV19GreaterThan(Float value) {
            addCriterion("v19 >", value, "v19");
            return (Criteria) this;
        }

        public Criteria andV19GreaterThanOrEqualTo(Float value) {
            addCriterion("v19 >=", value, "v19");
            return (Criteria) this;
        }

        public Criteria andV19LessThan(Float value) {
            addCriterion("v19 <", value, "v19");
            return (Criteria) this;
        }

        public Criteria andV19LessThanOrEqualTo(Float value) {
            addCriterion("v19 <=", value, "v19");
            return (Criteria) this;
        }

        public Criteria andV19In(List<Float> values) {
            addCriterion("v19 in", values, "v19");
            return (Criteria) this;
        }

        public Criteria andV19NotIn(List<Float> values) {
            addCriterion("v19 not in", values, "v19");
            return (Criteria) this;
        }

        public Criteria andV19Between(Float value1, Float value2) {
            addCriterion("v19 between", value1, value2, "v19");
            return (Criteria) this;
        }

        public Criteria andV19NotBetween(Float value1, Float value2) {
            addCriterion("v19 not between", value1, value2, "v19");
            return (Criteria) this;
        }

        public Criteria andA19IsNull() {
            addCriterion("a19 is null");
            return (Criteria) this;
        }

        public Criteria andA19IsNotNull() {
            addCriterion("a19 is not null");
            return (Criteria) this;
        }

        public Criteria andA19EqualTo(Float value) {
            addCriterion("a19 =", value, "a19");
            return (Criteria) this;
        }

        public Criteria andA19NotEqualTo(Float value) {
            addCriterion("a19 <>", value, "a19");
            return (Criteria) this;
        }

        public Criteria andA19GreaterThan(Float value) {
            addCriterion("a19 >", value, "a19");
            return (Criteria) this;
        }

        public Criteria andA19GreaterThanOrEqualTo(Float value) {
            addCriterion("a19 >=", value, "a19");
            return (Criteria) this;
        }

        public Criteria andA19LessThan(Float value) {
            addCriterion("a19 <", value, "a19");
            return (Criteria) this;
        }

        public Criteria andA19LessThanOrEqualTo(Float value) {
            addCriterion("a19 <=", value, "a19");
            return (Criteria) this;
        }

        public Criteria andA19In(List<Float> values) {
            addCriterion("a19 in", values, "a19");
            return (Criteria) this;
        }

        public Criteria andA19NotIn(List<Float> values) {
            addCriterion("a19 not in", values, "a19");
            return (Criteria) this;
        }

        public Criteria andA19Between(Float value1, Float value2) {
            addCriterion("a19 between", value1, value2, "a19");
            return (Criteria) this;
        }

        public Criteria andA19NotBetween(Float value1, Float value2) {
            addCriterion("a19 not between", value1, value2, "a19");
            return (Criteria) this;
        }

        public Criteria andV20IsNull() {
            addCriterion("v20 is null");
            return (Criteria) this;
        }

        public Criteria andV20IsNotNull() {
            addCriterion("v20 is not null");
            return (Criteria) this;
        }

        public Criteria andV20EqualTo(Float value) {
            addCriterion("v20 =", value, "v20");
            return (Criteria) this;
        }

        public Criteria andV20NotEqualTo(Float value) {
            addCriterion("v20 <>", value, "v20");
            return (Criteria) this;
        }

        public Criteria andV20GreaterThan(Float value) {
            addCriterion("v20 >", value, "v20");
            return (Criteria) this;
        }

        public Criteria andV20GreaterThanOrEqualTo(Float value) {
            addCriterion("v20 >=", value, "v20");
            return (Criteria) this;
        }

        public Criteria andV20LessThan(Float value) {
            addCriterion("v20 <", value, "v20");
            return (Criteria) this;
        }

        public Criteria andV20LessThanOrEqualTo(Float value) {
            addCriterion("v20 <=", value, "v20");
            return (Criteria) this;
        }

        public Criteria andV20In(List<Float> values) {
            addCriterion("v20 in", values, "v20");
            return (Criteria) this;
        }

        public Criteria andV20NotIn(List<Float> values) {
            addCriterion("v20 not in", values, "v20");
            return (Criteria) this;
        }

        public Criteria andV20Between(Float value1, Float value2) {
            addCriterion("v20 between", value1, value2, "v20");
            return (Criteria) this;
        }

        public Criteria andV20NotBetween(Float value1, Float value2) {
            addCriterion("v20 not between", value1, value2, "v20");
            return (Criteria) this;
        }

        public Criteria andA20IsNull() {
            addCriterion("a20 is null");
            return (Criteria) this;
        }

        public Criteria andA20IsNotNull() {
            addCriterion("a20 is not null");
            return (Criteria) this;
        }

        public Criteria andA20EqualTo(Float value) {
            addCriterion("a20 =", value, "a20");
            return (Criteria) this;
        }

        public Criteria andA20NotEqualTo(Float value) {
            addCriterion("a20 <>", value, "a20");
            return (Criteria) this;
        }

        public Criteria andA20GreaterThan(Float value) {
            addCriterion("a20 >", value, "a20");
            return (Criteria) this;
        }

        public Criteria andA20GreaterThanOrEqualTo(Float value) {
            addCriterion("a20 >=", value, "a20");
            return (Criteria) this;
        }

        public Criteria andA20LessThan(Float value) {
            addCriterion("a20 <", value, "a20");
            return (Criteria) this;
        }

        public Criteria andA20LessThanOrEqualTo(Float value) {
            addCriterion("a20 <=", value, "a20");
            return (Criteria) this;
        }

        public Criteria andA20In(List<Float> values) {
            addCriterion("a20 in", values, "a20");
            return (Criteria) this;
        }

        public Criteria andA20NotIn(List<Float> values) {
            addCriterion("a20 not in", values, "a20");
            return (Criteria) this;
        }

        public Criteria andA20Between(Float value1, Float value2) {
            addCriterion("a20 between", value1, value2, "a20");
            return (Criteria) this;
        }

        public Criteria andA20NotBetween(Float value1, Float value2) {
            addCriterion("a20 not between", value1, value2, "a20");
            return (Criteria) this;
        }

        public Criteria andV21IsNull() {
            addCriterion("v21 is null");
            return (Criteria) this;
        }

        public Criteria andV21IsNotNull() {
            addCriterion("v21 is not null");
            return (Criteria) this;
        }

        public Criteria andV21EqualTo(Float value) {
            addCriterion("v21 =", value, "v21");
            return (Criteria) this;
        }

        public Criteria andV21NotEqualTo(Float value) {
            addCriterion("v21 <>", value, "v21");
            return (Criteria) this;
        }

        public Criteria andV21GreaterThan(Float value) {
            addCriterion("v21 >", value, "v21");
            return (Criteria) this;
        }

        public Criteria andV21GreaterThanOrEqualTo(Float value) {
            addCriterion("v21 >=", value, "v21");
            return (Criteria) this;
        }

        public Criteria andV21LessThan(Float value) {
            addCriterion("v21 <", value, "v21");
            return (Criteria) this;
        }

        public Criteria andV21LessThanOrEqualTo(Float value) {
            addCriterion("v21 <=", value, "v21");
            return (Criteria) this;
        }

        public Criteria andV21In(List<Float> values) {
            addCriterion("v21 in", values, "v21");
            return (Criteria) this;
        }

        public Criteria andV21NotIn(List<Float> values) {
            addCriterion("v21 not in", values, "v21");
            return (Criteria) this;
        }

        public Criteria andV21Between(Float value1, Float value2) {
            addCriterion("v21 between", value1, value2, "v21");
            return (Criteria) this;
        }

        public Criteria andV21NotBetween(Float value1, Float value2) {
            addCriterion("v21 not between", value1, value2, "v21");
            return (Criteria) this;
        }

        public Criteria andA21IsNull() {
            addCriterion("a21 is null");
            return (Criteria) this;
        }

        public Criteria andA21IsNotNull() {
            addCriterion("a21 is not null");
            return (Criteria) this;
        }

        public Criteria andA21EqualTo(Float value) {
            addCriterion("a21 =", value, "a21");
            return (Criteria) this;
        }

        public Criteria andA21NotEqualTo(Float value) {
            addCriterion("a21 <>", value, "a21");
            return (Criteria) this;
        }

        public Criteria andA21GreaterThan(Float value) {
            addCriterion("a21 >", value, "a21");
            return (Criteria) this;
        }

        public Criteria andA21GreaterThanOrEqualTo(Float value) {
            addCriterion("a21 >=", value, "a21");
            return (Criteria) this;
        }

        public Criteria andA21LessThan(Float value) {
            addCriterion("a21 <", value, "a21");
            return (Criteria) this;
        }

        public Criteria andA21LessThanOrEqualTo(Float value) {
            addCriterion("a21 <=", value, "a21");
            return (Criteria) this;
        }

        public Criteria andA21In(List<Float> values) {
            addCriterion("a21 in", values, "a21");
            return (Criteria) this;
        }

        public Criteria andA21NotIn(List<Float> values) {
            addCriterion("a21 not in", values, "a21");
            return (Criteria) this;
        }

        public Criteria andA21Between(Float value1, Float value2) {
            addCriterion("a21 between", value1, value2, "a21");
            return (Criteria) this;
        }

        public Criteria andA21NotBetween(Float value1, Float value2) {
            addCriterion("a21 not between", value1, value2, "a21");
            return (Criteria) this;
        }

        public Criteria andV22IsNull() {
            addCriterion("v22 is null");
            return (Criteria) this;
        }

        public Criteria andV22IsNotNull() {
            addCriterion("v22 is not null");
            return (Criteria) this;
        }

        public Criteria andV22EqualTo(Float value) {
            addCriterion("v22 =", value, "v22");
            return (Criteria) this;
        }

        public Criteria andV22NotEqualTo(Float value) {
            addCriterion("v22 <>", value, "v22");
            return (Criteria) this;
        }

        public Criteria andV22GreaterThan(Float value) {
            addCriterion("v22 >", value, "v22");
            return (Criteria) this;
        }

        public Criteria andV22GreaterThanOrEqualTo(Float value) {
            addCriterion("v22 >=", value, "v22");
            return (Criteria) this;
        }

        public Criteria andV22LessThan(Float value) {
            addCriterion("v22 <", value, "v22");
            return (Criteria) this;
        }

        public Criteria andV22LessThanOrEqualTo(Float value) {
            addCriterion("v22 <=", value, "v22");
            return (Criteria) this;
        }

        public Criteria andV22In(List<Float> values) {
            addCriterion("v22 in", values, "v22");
            return (Criteria) this;
        }

        public Criteria andV22NotIn(List<Float> values) {
            addCriterion("v22 not in", values, "v22");
            return (Criteria) this;
        }

        public Criteria andV22Between(Float value1, Float value2) {
            addCriterion("v22 between", value1, value2, "v22");
            return (Criteria) this;
        }

        public Criteria andV22NotBetween(Float value1, Float value2) {
            addCriterion("v22 not between", value1, value2, "v22");
            return (Criteria) this;
        }

        public Criteria andA22IsNull() {
            addCriterion("a22 is null");
            return (Criteria) this;
        }

        public Criteria andA22IsNotNull() {
            addCriterion("a22 is not null");
            return (Criteria) this;
        }

        public Criteria andA22EqualTo(Float value) {
            addCriterion("a22 =", value, "a22");
            return (Criteria) this;
        }

        public Criteria andA22NotEqualTo(Float value) {
            addCriterion("a22 <>", value, "a22");
            return (Criteria) this;
        }

        public Criteria andA22GreaterThan(Float value) {
            addCriterion("a22 >", value, "a22");
            return (Criteria) this;
        }

        public Criteria andA22GreaterThanOrEqualTo(Float value) {
            addCriterion("a22 >=", value, "a22");
            return (Criteria) this;
        }

        public Criteria andA22LessThan(Float value) {
            addCriterion("a22 <", value, "a22");
            return (Criteria) this;
        }

        public Criteria andA22LessThanOrEqualTo(Float value) {
            addCriterion("a22 <=", value, "a22");
            return (Criteria) this;
        }

        public Criteria andA22In(List<Float> values) {
            addCriterion("a22 in", values, "a22");
            return (Criteria) this;
        }

        public Criteria andA22NotIn(List<Float> values) {
            addCriterion("a22 not in", values, "a22");
            return (Criteria) this;
        }

        public Criteria andA22Between(Float value1, Float value2) {
            addCriterion("a22 between", value1, value2, "a22");
            return (Criteria) this;
        }

        public Criteria andA22NotBetween(Float value1, Float value2) {
            addCriterion("a22 not between", value1, value2, "a22");
            return (Criteria) this;
        }

        public Criteria andV23IsNull() {
            addCriterion("v23 is null");
            return (Criteria) this;
        }

        public Criteria andV23IsNotNull() {
            addCriterion("v23 is not null");
            return (Criteria) this;
        }

        public Criteria andV23EqualTo(Float value) {
            addCriterion("v23 =", value, "v23");
            return (Criteria) this;
        }

        public Criteria andV23NotEqualTo(Float value) {
            addCriterion("v23 <>", value, "v23");
            return (Criteria) this;
        }

        public Criteria andV23GreaterThan(Float value) {
            addCriterion("v23 >", value, "v23");
            return (Criteria) this;
        }

        public Criteria andV23GreaterThanOrEqualTo(Float value) {
            addCriterion("v23 >=", value, "v23");
            return (Criteria) this;
        }

        public Criteria andV23LessThan(Float value) {
            addCriterion("v23 <", value, "v23");
            return (Criteria) this;
        }

        public Criteria andV23LessThanOrEqualTo(Float value) {
            addCriterion("v23 <=", value, "v23");
            return (Criteria) this;
        }

        public Criteria andV23In(List<Float> values) {
            addCriterion("v23 in", values, "v23");
            return (Criteria) this;
        }

        public Criteria andV23NotIn(List<Float> values) {
            addCriterion("v23 not in", values, "v23");
            return (Criteria) this;
        }

        public Criteria andV23Between(Float value1, Float value2) {
            addCriterion("v23 between", value1, value2, "v23");
            return (Criteria) this;
        }

        public Criteria andV23NotBetween(Float value1, Float value2) {
            addCriterion("v23 not between", value1, value2, "v23");
            return (Criteria) this;
        }

        public Criteria andA23IsNull() {
            addCriterion("a23 is null");
            return (Criteria) this;
        }

        public Criteria andA23IsNotNull() {
            addCriterion("a23 is not null");
            return (Criteria) this;
        }

        public Criteria andA23EqualTo(Float value) {
            addCriterion("a23 =", value, "a23");
            return (Criteria) this;
        }

        public Criteria andA23NotEqualTo(Float value) {
            addCriterion("a23 <>", value, "a23");
            return (Criteria) this;
        }

        public Criteria andA23GreaterThan(Float value) {
            addCriterion("a23 >", value, "a23");
            return (Criteria) this;
        }

        public Criteria andA23GreaterThanOrEqualTo(Float value) {
            addCriterion("a23 >=", value, "a23");
            return (Criteria) this;
        }

        public Criteria andA23LessThan(Float value) {
            addCriterion("a23 <", value, "a23");
            return (Criteria) this;
        }

        public Criteria andA23LessThanOrEqualTo(Float value) {
            addCriterion("a23 <=", value, "a23");
            return (Criteria) this;
        }

        public Criteria andA23In(List<Float> values) {
            addCriterion("a23 in", values, "a23");
            return (Criteria) this;
        }

        public Criteria andA23NotIn(List<Float> values) {
            addCriterion("a23 not in", values, "a23");
            return (Criteria) this;
        }

        public Criteria andA23Between(Float value1, Float value2) {
            addCriterion("a23 between", value1, value2, "a23");
            return (Criteria) this;
        }

        public Criteria andA23NotBetween(Float value1, Float value2) {
            addCriterion("a23 not between", value1, value2, "a23");
            return (Criteria) this;
        }

        public Criteria andV24IsNull() {
            addCriterion("v24 is null");
            return (Criteria) this;
        }

        public Criteria andV24IsNotNull() {
            addCriterion("v24 is not null");
            return (Criteria) this;
        }

        public Criteria andV24EqualTo(Float value) {
            addCriterion("v24 =", value, "v24");
            return (Criteria) this;
        }

        public Criteria andV24NotEqualTo(Float value) {
            addCriterion("v24 <>", value, "v24");
            return (Criteria) this;
        }

        public Criteria andV24GreaterThan(Float value) {
            addCriterion("v24 >", value, "v24");
            return (Criteria) this;
        }

        public Criteria andV24GreaterThanOrEqualTo(Float value) {
            addCriterion("v24 >=", value, "v24");
            return (Criteria) this;
        }

        public Criteria andV24LessThan(Float value) {
            addCriterion("v24 <", value, "v24");
            return (Criteria) this;
        }

        public Criteria andV24LessThanOrEqualTo(Float value) {
            addCriterion("v24 <=", value, "v24");
            return (Criteria) this;
        }

        public Criteria andV24In(List<Float> values) {
            addCriterion("v24 in", values, "v24");
            return (Criteria) this;
        }

        public Criteria andV24NotIn(List<Float> values) {
            addCriterion("v24 not in", values, "v24");
            return (Criteria) this;
        }

        public Criteria andV24Between(Float value1, Float value2) {
            addCriterion("v24 between", value1, value2, "v24");
            return (Criteria) this;
        }

        public Criteria andV24NotBetween(Float value1, Float value2) {
            addCriterion("v24 not between", value1, value2, "v24");
            return (Criteria) this;
        }

        public Criteria andA24IsNull() {
            addCriterion("a24 is null");
            return (Criteria) this;
        }

        public Criteria andA24IsNotNull() {
            addCriterion("a24 is not null");
            return (Criteria) this;
        }

        public Criteria andA24EqualTo(Float value) {
            addCriterion("a24 =", value, "a24");
            return (Criteria) this;
        }

        public Criteria andA24NotEqualTo(Float value) {
            addCriterion("a24 <>", value, "a24");
            return (Criteria) this;
        }

        public Criteria andA24GreaterThan(Float value) {
            addCriterion("a24 >", value, "a24");
            return (Criteria) this;
        }

        public Criteria andA24GreaterThanOrEqualTo(Float value) {
            addCriterion("a24 >=", value, "a24");
            return (Criteria) this;
        }

        public Criteria andA24LessThan(Float value) {
            addCriterion("a24 <", value, "a24");
            return (Criteria) this;
        }

        public Criteria andA24LessThanOrEqualTo(Float value) {
            addCriterion("a24 <=", value, "a24");
            return (Criteria) this;
        }

        public Criteria andA24In(List<Float> values) {
            addCriterion("a24 in", values, "a24");
            return (Criteria) this;
        }

        public Criteria andA24NotIn(List<Float> values) {
            addCriterion("a24 not in", values, "a24");
            return (Criteria) this;
        }

        public Criteria andA24Between(Float value1, Float value2) {
            addCriterion("a24 between", value1, value2, "a24");
            return (Criteria) this;
        }

        public Criteria andA24NotBetween(Float value1, Float value2) {
            addCriterion("a24 not between", value1, value2, "a24");
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