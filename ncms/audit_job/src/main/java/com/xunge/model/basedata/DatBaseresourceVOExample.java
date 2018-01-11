package com.xunge.model.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatBaseresourceVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DatBaseresourceVOExample() {
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

        public Criteria andBaseresourceIdIsNull() {
            addCriterion("baseresource_id is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdIsNotNull() {
            addCriterion("baseresource_id is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdEqualTo(String value) {
            addCriterion("baseresource_id =", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdNotEqualTo(String value) {
            addCriterion("baseresource_id <>", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdGreaterThan(String value) {
            addCriterion("baseresource_id >", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdGreaterThanOrEqualTo(String value) {
            addCriterion("baseresource_id >=", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdLessThan(String value) {
            addCriterion("baseresource_id <", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdLessThanOrEqualTo(String value) {
            addCriterion("baseresource_id <=", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdLike(String value) {
            addCriterion("baseresource_id like", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdNotLike(String value) {
            addCriterion("baseresource_id not like", value, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdIn(List<String> values) {
            addCriterion("baseresource_id in", values, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdNotIn(List<String> values) {
            addCriterion("baseresource_id not in", values, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdBetween(String value1, String value2) {
            addCriterion("baseresource_id between", value1, value2, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBaseresourceIdNotBetween(String value1, String value2) {
            addCriterion("baseresource_id not between", value1, value2, "baseresourceId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdIsNull() {
            addCriterion("basesite_id is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdIsNotNull() {
            addCriterion("basesite_id is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdEqualTo(String value) {
            addCriterion("basesite_id =", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdNotEqualTo(String value) {
            addCriterion("basesite_id <>", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdGreaterThan(String value) {
            addCriterion("basesite_id >", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdGreaterThanOrEqualTo(String value) {
            addCriterion("basesite_id >=", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdLessThan(String value) {
            addCriterion("basesite_id <", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdLessThanOrEqualTo(String value) {
            addCriterion("basesite_id <=", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdLike(String value) {
            addCriterion("basesite_id like", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdNotLike(String value) {
            addCriterion("basesite_id not like", value, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdIn(List<String> values) {
            addCriterion("basesite_id in", values, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdNotIn(List<String> values) {
            addCriterion("basesite_id not in", values, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdBetween(String value1, String value2) {
            addCriterion("basesite_id between", value1, value2, "basesiteId");
            return (Criteria) this;
        }

        public Criteria andBasesiteIdNotBetween(String value1, String value2) {
            addCriterion("basesite_id not between", value1, value2, "basesiteId");
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

        public Criteria andBaseresourceTypeIsNull() {
            addCriterion("baseresource_type is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeIsNotNull() {
            addCriterion("baseresource_type is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeEqualTo(Integer value) {
            addCriterion("baseresource_type =", value, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeNotEqualTo(Integer value) {
            addCriterion("baseresource_type <>", value, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeGreaterThan(Integer value) {
            addCriterion("baseresource_type >", value, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("baseresource_type >=", value, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeLessThan(Integer value) {
            addCriterion("baseresource_type <", value, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeLessThanOrEqualTo(Integer value) {
            addCriterion("baseresource_type <=", value, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeIn(List<Integer> values) {
            addCriterion("baseresource_type in", values, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeNotIn(List<Integer> values) {
            addCriterion("baseresource_type not in", values, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_type between", value1, value2, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_type not between", value1, value2, "baseresourceType");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryIsNull() {
            addCriterion("baseresource_category is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryIsNotNull() {
            addCriterion("baseresource_category is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryEqualTo(Integer value) {
            addCriterion("baseresource_category =", value, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryNotEqualTo(Integer value) {
            addCriterion("baseresource_category <>", value, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryGreaterThan(Integer value) {
            addCriterion("baseresource_category >", value, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("baseresource_category >=", value, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryLessThan(Integer value) {
            addCriterion("baseresource_category <", value, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("baseresource_category <=", value, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryIn(List<Integer> values) {
            addCriterion("baseresource_category in", values, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryNotIn(List<Integer> values) {
            addCriterion("baseresource_category not in", values, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_category between", value1, value2, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_category not between", value1, value2, "baseresourceCategory");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidIsNull() {
            addCriterion("baseresource_cuid is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidIsNotNull() {
            addCriterion("baseresource_cuid is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidEqualTo(String value) {
            addCriterion("baseresource_cuid =", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidNotEqualTo(String value) {
            addCriterion("baseresource_cuid <>", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidGreaterThan(String value) {
            addCriterion("baseresource_cuid >", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidGreaterThanOrEqualTo(String value) {
            addCriterion("baseresource_cuid >=", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidLessThan(String value) {
            addCriterion("baseresource_cuid <", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidLessThanOrEqualTo(String value) {
            addCriterion("baseresource_cuid <=", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidLike(String value) {
            addCriterion("baseresource_cuid like", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidNotLike(String value) {
            addCriterion("baseresource_cuid not like", value, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidIn(List<String> values) {
            addCriterion("baseresource_cuid in", values, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidNotIn(List<String> values) {
            addCriterion("baseresource_cuid not in", values, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidBetween(String value1, String value2) {
            addCriterion("baseresource_cuid between", value1, value2, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCuidNotBetween(String value1, String value2) {
            addCriterion("baseresource_cuid not between", value1, value2, "baseresourceCuid");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeIsNull() {
            addCriterion("baseresource_code is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeIsNotNull() {
            addCriterion("baseresource_code is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeEqualTo(String value) {
            addCriterion("baseresource_code =", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeNotEqualTo(String value) {
            addCriterion("baseresource_code <>", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeGreaterThan(String value) {
            addCriterion("baseresource_code >", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("baseresource_code >=", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeLessThan(String value) {
            addCriterion("baseresource_code <", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeLessThanOrEqualTo(String value) {
            addCriterion("baseresource_code <=", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeLike(String value) {
            addCriterion("baseresource_code like", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeNotLike(String value) {
            addCriterion("baseresource_code not like", value, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeIn(List<String> values) {
            addCriterion("baseresource_code in", values, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeNotIn(List<String> values) {
            addCriterion("baseresource_code not in", values, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeBetween(String value1, String value2) {
            addCriterion("baseresource_code between", value1, value2, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceCodeNotBetween(String value1, String value2) {
            addCriterion("baseresource_code not between", value1, value2, "baseresourceCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameIsNull() {
            addCriterion("baseresource_name is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameIsNotNull() {
            addCriterion("baseresource_name is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameEqualTo(String value) {
            addCriterion("baseresource_name =", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameNotEqualTo(String value) {
            addCriterion("baseresource_name <>", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameGreaterThan(String value) {
            addCriterion("baseresource_name >", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("baseresource_name >=", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameLessThan(String value) {
            addCriterion("baseresource_name <", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameLessThanOrEqualTo(String value) {
            addCriterion("baseresource_name <=", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameLike(String value) {
            addCriterion("baseresource_name like", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameNotLike(String value) {
            addCriterion("baseresource_name not like", value, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameIn(List<String> values) {
            addCriterion("baseresource_name in", values, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameNotIn(List<String> values) {
            addCriterion("baseresource_name not in", values, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameBetween(String value1, String value2) {
            addCriterion("baseresource_name between", value1, value2, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNameNotBetween(String value1, String value2) {
            addCriterion("baseresource_name not between", value1, value2, "baseresourceName");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressIsNull() {
            addCriterion("baseresource_address is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressIsNotNull() {
            addCriterion("baseresource_address is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressEqualTo(String value) {
            addCriterion("baseresource_address =", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressNotEqualTo(String value) {
            addCriterion("baseresource_address <>", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressGreaterThan(String value) {
            addCriterion("baseresource_address >", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressGreaterThanOrEqualTo(String value) {
            addCriterion("baseresource_address >=", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressLessThan(String value) {
            addCriterion("baseresource_address <", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressLessThanOrEqualTo(String value) {
            addCriterion("baseresource_address <=", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressLike(String value) {
            addCriterion("baseresource_address like", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressNotLike(String value) {
            addCriterion("baseresource_address not like", value, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressIn(List<String> values) {
            addCriterion("baseresource_address in", values, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressNotIn(List<String> values) {
            addCriterion("baseresource_address not in", values, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressBetween(String value1, String value2) {
            addCriterion("baseresource_address between", value1, value2, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAddressNotBetween(String value1, String value2) {
            addCriterion("baseresource_address not between", value1, value2, "baseresourceAddress");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaIsNull() {
            addCriterion("baseresource_area is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaIsNotNull() {
            addCriterion("baseresource_area is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaEqualTo(BigDecimal value) {
            addCriterion("baseresource_area =", value, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaNotEqualTo(BigDecimal value) {
            addCriterion("baseresource_area <>", value, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaGreaterThan(BigDecimal value) {
            addCriterion("baseresource_area >", value, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("baseresource_area >=", value, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaLessThan(BigDecimal value) {
            addCriterion("baseresource_area <", value, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaLessThanOrEqualTo(BigDecimal value) {
            addCriterion("baseresource_area <=", value, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaIn(List<BigDecimal> values) {
            addCriterion("baseresource_area in", values, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaNotIn(List<BigDecimal> values) {
            addCriterion("baseresource_area not in", values, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("baseresource_area between", value1, value2, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceAreaNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("baseresource_area not between", value1, value2, "baseresourceArea");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceIsNull() {
            addCriterion("baseresource_maintenance is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceIsNotNull() {
            addCriterion("baseresource_maintenance is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceEqualTo(Integer value) {
            addCriterion("baseresource_maintenance =", value, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceNotEqualTo(Integer value) {
            addCriterion("baseresource_maintenance <>", value, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceGreaterThan(Integer value) {
            addCriterion("baseresource_maintenance >", value, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("baseresource_maintenance >=", value, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceLessThan(Integer value) {
            addCriterion("baseresource_maintenance <", value, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceLessThanOrEqualTo(Integer value) {
            addCriterion("baseresource_maintenance <=", value, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceIn(List<Integer> values) {
            addCriterion("baseresource_maintenance in", values, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceNotIn(List<Integer> values) {
            addCriterion("baseresource_maintenance not in", values, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_maintenance between", value1, value2, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andBaseresourceMaintenanceNotBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_maintenance not between", value1, value2, "baseresourceMaintenance");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeIsNull() {
            addCriterion("tower_site_code is null");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeIsNotNull() {
            addCriterion("tower_site_code is not null");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeEqualTo(String value) {
            addCriterion("tower_site_code =", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeNotEqualTo(String value) {
            addCriterion("tower_site_code <>", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeGreaterThan(String value) {
            addCriterion("tower_site_code >", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("tower_site_code >=", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeLessThan(String value) {
            addCriterion("tower_site_code <", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeLessThanOrEqualTo(String value) {
            addCriterion("tower_site_code <=", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeLike(String value) {
            addCriterion("tower_site_code like", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeNotLike(String value) {
            addCriterion("tower_site_code not like", value, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeIn(List<String> values) {
            addCriterion("tower_site_code in", values, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeNotIn(List<String> values) {
            addCriterion("tower_site_code not in", values, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeBetween(String value1, String value2) {
            addCriterion("tower_site_code between", value1, value2, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andTowerSiteCodeNotBetween(String value1, String value2) {
            addCriterion("tower_site_code not between", value1, value2, "towerSiteCode");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateIsNull() {
            addCriterion("baseresource_opendate is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateIsNotNull() {
            addCriterion("baseresource_opendate is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateEqualTo(Date value) {
            addCriterion("baseresource_opendate =", value, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateNotEqualTo(Date value) {
            addCriterion("baseresource_opendate <>", value, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateGreaterThan(Date value) {
            addCriterion("baseresource_opendate >", value, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateGreaterThanOrEqualTo(Date value) {
            addCriterion("baseresource_opendate >=", value, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateLessThan(Date value) {
            addCriterion("baseresource_opendate <", value, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateLessThanOrEqualTo(Date value) {
            addCriterion("baseresource_opendate <=", value, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateIn(List<Date> values) {
            addCriterion("baseresource_opendate in", values, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateNotIn(List<Date> values) {
            addCriterion("baseresource_opendate not in", values, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateBetween(Date value1, Date value2) {
            addCriterion("baseresource_opendate between", value1, value2, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceOpendateNotBetween(Date value1, Date value2) {
            addCriterion("baseresource_opendate not between", value1, value2, "baseresourceOpendate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateIsNull() {
            addCriterion("baseresource_stopdate is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateIsNotNull() {
            addCriterion("baseresource_stopdate is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateEqualTo(Date value) {
            addCriterion("baseresource_stopdate =", value, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateNotEqualTo(Date value) {
            addCriterion("baseresource_stopdate <>", value, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateGreaterThan(Date value) {
            addCriterion("baseresource_stopdate >", value, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateGreaterThanOrEqualTo(Date value) {
            addCriterion("baseresource_stopdate >=", value, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateLessThan(Date value) {
            addCriterion("baseresource_stopdate <", value, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateLessThanOrEqualTo(Date value) {
            addCriterion("baseresource_stopdate <=", value, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateIn(List<Date> values) {
            addCriterion("baseresource_stopdate in", values, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateNotIn(List<Date> values) {
            addCriterion("baseresource_stopdate not in", values, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateBetween(Date value1, Date value2) {
            addCriterion("baseresource_stopdate between", value1, value2, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStopdateNotBetween(Date value1, Date value2) {
            addCriterion("baseresource_stopdate not between", value1, value2, "baseresourceStopdate");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerIsNull() {
            addCriterion("room_owner is null");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerIsNotNull() {
            addCriterion("room_owner is not null");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerEqualTo(Integer value) {
            addCriterion("room_owner =", value, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerNotEqualTo(Integer value) {
            addCriterion("room_owner <>", value, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerGreaterThan(Integer value) {
            addCriterion("room_owner >", value, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_owner >=", value, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerLessThan(Integer value) {
            addCriterion("room_owner <", value, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerLessThanOrEqualTo(Integer value) {
            addCriterion("room_owner <=", value, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerIn(List<Integer> values) {
            addCriterion("room_owner in", values, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerNotIn(List<Integer> values) {
            addCriterion("room_owner not in", values, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerBetween(Integer value1, Integer value2) {
            addCriterion("room_owner between", value1, value2, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomOwnerNotBetween(Integer value1, Integer value2) {
            addCriterion("room_owner not between", value1, value2, "roomOwner");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyIsNull() {
            addCriterion("room_property is null");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyIsNotNull() {
            addCriterion("room_property is not null");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyEqualTo(Integer value) {
            addCriterion("room_property =", value, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyNotEqualTo(Integer value) {
            addCriterion("room_property <>", value, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyGreaterThan(Integer value) {
            addCriterion("room_property >", value, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_property >=", value, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyLessThan(Integer value) {
            addCriterion("room_property <", value, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyLessThanOrEqualTo(Integer value) {
            addCriterion("room_property <=", value, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyIn(List<Integer> values) {
            addCriterion("room_property in", values, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyNotIn(List<Integer> values) {
            addCriterion("room_property not in", values, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyBetween(Integer value1, Integer value2) {
            addCriterion("room_property between", value1, value2, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomPropertyNotBetween(Integer value1, Integer value2) {
            addCriterion("room_property not between", value1, value2, "roomProperty");
            return (Criteria) this;
        }

        public Criteria andRoomShareIsNull() {
            addCriterion("room_share is null");
            return (Criteria) this;
        }

        public Criteria andRoomShareIsNotNull() {
            addCriterion("room_share is not null");
            return (Criteria) this;
        }

        public Criteria andRoomShareEqualTo(Integer value) {
            addCriterion("room_share =", value, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareNotEqualTo(Integer value) {
            addCriterion("room_share <>", value, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareGreaterThan(Integer value) {
            addCriterion("room_share >", value, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareGreaterThanOrEqualTo(Integer value) {
            addCriterion("room_share >=", value, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareLessThan(Integer value) {
            addCriterion("room_share <", value, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareLessThanOrEqualTo(Integer value) {
            addCriterion("room_share <=", value, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareIn(List<Integer> values) {
            addCriterion("room_share in", values, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareNotIn(List<Integer> values) {
            addCriterion("room_share not in", values, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareBetween(Integer value1, Integer value2) {
            addCriterion("room_share between", value1, value2, "roomShare");
            return (Criteria) this;
        }

        public Criteria andRoomShareNotBetween(Integer value1, Integer value2) {
            addCriterion("room_share not between", value1, value2, "roomShare");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeIsNull() {
            addCriterion("baseresource_longitude is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeIsNotNull() {
            addCriterion("baseresource_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeEqualTo(BigDecimal value) {
            addCriterion("baseresource_longitude =", value, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("baseresource_longitude <>", value, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeGreaterThan(BigDecimal value) {
            addCriterion("baseresource_longitude >", value, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("baseresource_longitude >=", value, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeLessThan(BigDecimal value) {
            addCriterion("baseresource_longitude <", value, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("baseresource_longitude <=", value, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeIn(List<BigDecimal> values) {
            addCriterion("baseresource_longitude in", values, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("baseresource_longitude not in", values, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("baseresource_longitude between", value1, value2, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("baseresource_longitude not between", value1, value2, "baseresourceLongitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeIsNull() {
            addCriterion("baseresource_latitude is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeIsNotNull() {
            addCriterion("baseresource_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeEqualTo(BigDecimal value) {
            addCriterion("baseresource_latitude =", value, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("baseresource_latitude <>", value, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeGreaterThan(BigDecimal value) {
            addCriterion("baseresource_latitude >", value, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("baseresource_latitude >=", value, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeLessThan(BigDecimal value) {
            addCriterion("baseresource_latitude <", value, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("baseresource_latitude <=", value, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeIn(List<BigDecimal> values) {
            addCriterion("baseresource_latitude in", values, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("baseresource_latitude not in", values, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("baseresource_latitude between", value1, value2, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andBaseresourceLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("baseresource_latitude not between", value1, value2, "baseresourceLatitude");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerIsNull() {
            addCriterion("airconditioner_power is null");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerIsNotNull() {
            addCriterion("airconditioner_power is not null");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerEqualTo(BigDecimal value) {
            addCriterion("airconditioner_power =", value, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerNotEqualTo(BigDecimal value) {
            addCriterion("airconditioner_power <>", value, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerGreaterThan(BigDecimal value) {
            addCriterion("airconditioner_power >", value, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("airconditioner_power >=", value, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerLessThan(BigDecimal value) {
            addCriterion("airconditioner_power <", value, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerLessThanOrEqualTo(BigDecimal value) {
            addCriterion("airconditioner_power <=", value, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerIn(List<BigDecimal> values) {
            addCriterion("airconditioner_power in", values, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerNotIn(List<BigDecimal> values) {
            addCriterion("airconditioner_power not in", values, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("airconditioner_power between", value1, value2, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andAirconditionerPowerNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("airconditioner_power not between", value1, value2, "airconditionerPower");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateIsNull() {
            addCriterion("baseresource_state is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateIsNotNull() {
            addCriterion("baseresource_state is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateEqualTo(Integer value) {
            addCriterion("baseresource_state =", value, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateNotEqualTo(Integer value) {
            addCriterion("baseresource_state <>", value, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateGreaterThan(Integer value) {
            addCriterion("baseresource_state >", value, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("baseresource_state >=", value, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateLessThan(Integer value) {
            addCriterion("baseresource_state <", value, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateLessThanOrEqualTo(Integer value) {
            addCriterion("baseresource_state <=", value, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateIn(List<Integer> values) {
            addCriterion("baseresource_state in", values, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateNotIn(List<Integer> values) {
            addCriterion("baseresource_state not in", values, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_state between", value1, value2, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceStateNotBetween(Integer value1, Integer value2) {
            addCriterion("baseresource_state not between", value1, value2, "baseresourceState");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteIsNull() {
            addCriterion("baseresource_note is null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteIsNotNull() {
            addCriterion("baseresource_note is not null");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteEqualTo(String value) {
            addCriterion("baseresource_note =", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteNotEqualTo(String value) {
            addCriterion("baseresource_note <>", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteGreaterThan(String value) {
            addCriterion("baseresource_note >", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteGreaterThanOrEqualTo(String value) {
            addCriterion("baseresource_note >=", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteLessThan(String value) {
            addCriterion("baseresource_note <", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteLessThanOrEqualTo(String value) {
            addCriterion("baseresource_note <=", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteLike(String value) {
            addCriterion("baseresource_note like", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteNotLike(String value) {
            addCriterion("baseresource_note not like", value, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteIn(List<String> values) {
            addCriterion("baseresource_note in", values, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteNotIn(List<String> values) {
            addCriterion("baseresource_note not in", values, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteBetween(String value1, String value2) {
            addCriterion("baseresource_note between", value1, value2, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andBaseresourceNoteNotBetween(String value1, String value2) {
            addCriterion("baseresource_note not between", value1, value2, "baseresourceNote");
            return (Criteria) this;
        }

        public Criteria andAuditingStateIsNull() {
            addCriterion("auditing_state is null");
            return (Criteria) this;
        }

        public Criteria andAuditingStateIsNotNull() {
            addCriterion("auditing_state is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingStateEqualTo(Integer value) {
            addCriterion("auditing_state =", value, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateNotEqualTo(Integer value) {
            addCriterion("auditing_state <>", value, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateGreaterThan(Integer value) {
            addCriterion("auditing_state >", value, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("auditing_state >=", value, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateLessThan(Integer value) {
            addCriterion("auditing_state <", value, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateLessThanOrEqualTo(Integer value) {
            addCriterion("auditing_state <=", value, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateIn(List<Integer> values) {
            addCriterion("auditing_state in", values, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateNotIn(List<Integer> values) {
            addCriterion("auditing_state not in", values, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateBetween(Integer value1, Integer value2) {
            addCriterion("auditing_state between", value1, value2, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingStateNotBetween(Integer value1, Integer value2) {
            addCriterion("auditing_state not between", value1, value2, "auditingState");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIsNull() {
            addCriterion("auditing_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIsNotNull() {
            addCriterion("auditing_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdEqualTo(String value) {
            addCriterion("auditing_user_id =", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotEqualTo(String value) {
            addCriterion("auditing_user_id <>", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdGreaterThan(String value) {
            addCriterion("auditing_user_id >", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("auditing_user_id >=", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLessThan(String value) {
            addCriterion("auditing_user_id <", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLessThanOrEqualTo(String value) {
            addCriterion("auditing_user_id <=", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLike(String value) {
            addCriterion("auditing_user_id like", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotLike(String value) {
            addCriterion("auditing_user_id not like", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIn(List<String> values) {
            addCriterion("auditing_user_id in", values, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotIn(List<String> values) {
            addCriterion("auditing_user_id not in", values, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdBetween(String value1, String value2) {
            addCriterion("auditing_user_id between", value1, value2, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotBetween(String value1, String value2) {
            addCriterion("auditing_user_id not between", value1, value2, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingDateIsNull() {
            addCriterion("auditing_date is null");
            return (Criteria) this;
        }

        public Criteria andAuditingDateIsNotNull() {
            addCriterion("auditing_date is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingDateEqualTo(Date value) {
            addCriterion("auditing_date =", value, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateNotEqualTo(Date value) {
            addCriterion("auditing_date <>", value, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateGreaterThan(Date value) {
            addCriterion("auditing_date >", value, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateGreaterThanOrEqualTo(Date value) {
            addCriterion("auditing_date >=", value, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateLessThan(Date value) {
            addCriterion("auditing_date <", value, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateLessThanOrEqualTo(Date value) {
            addCriterion("auditing_date <=", value, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateIn(List<Date> values) {
            addCriterion("auditing_date in", values, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateNotIn(List<Date> values) {
            addCriterion("auditing_date not in", values, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateBetween(Date value1, Date value2) {
            addCriterion("auditing_date between", value1, value2, "auditingDate");
            return (Criteria) this;
        }

        public Criteria andAuditingDateNotBetween(Date value1, Date value2) {
            addCriterion("auditing_date not between", value1, value2, "auditingDate");
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