package com.xunge.dao.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatBasesiteVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DatBasesiteVOExample() {
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

        public Criteria andBasesiteCodeIsNull() {
            addCriterion("basesite_code is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeIsNotNull() {
            addCriterion("basesite_code is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeEqualTo(String value) {
            addCriterion("basesite_code =", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeNotEqualTo(String value) {
            addCriterion("basesite_code <>", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeGreaterThan(String value) {
            addCriterion("basesite_code >", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeGreaterThanOrEqualTo(String value) {
            addCriterion("basesite_code >=", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeLessThan(String value) {
            addCriterion("basesite_code <", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeLessThanOrEqualTo(String value) {
            addCriterion("basesite_code <=", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeLike(String value) {
            addCriterion("basesite_code like", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeNotLike(String value) {
            addCriterion("basesite_code not like", value, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeIn(List<String> values) {
            addCriterion("basesite_code in", values, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeNotIn(List<String> values) {
            addCriterion("basesite_code not in", values, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeBetween(String value1, String value2) {
            addCriterion("basesite_code between", value1, value2, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteCodeNotBetween(String value1, String value2) {
            addCriterion("basesite_code not between", value1, value2, "basesiteCode");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameIsNull() {
            addCriterion("basesite_name is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameIsNotNull() {
            addCriterion("basesite_name is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameEqualTo(String value) {
            addCriterion("basesite_name =", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameNotEqualTo(String value) {
            addCriterion("basesite_name <>", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameGreaterThan(String value) {
            addCriterion("basesite_name >", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("basesite_name >=", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameLessThan(String value) {
            addCriterion("basesite_name <", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameLessThanOrEqualTo(String value) {
            addCriterion("basesite_name <=", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameLike(String value) {
            addCriterion("basesite_name like", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameNotLike(String value) {
            addCriterion("basesite_name not like", value, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameIn(List<String> values) {
            addCriterion("basesite_name in", values, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameNotIn(List<String> values) {
            addCriterion("basesite_name not in", values, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameBetween(String value1, String value2) {
            addCriterion("basesite_name between", value1, value2, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteNameNotBetween(String value1, String value2) {
            addCriterion("basesite_name not between", value1, value2, "basesiteName");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressIsNull() {
            addCriterion("basesite_address is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressIsNotNull() {
            addCriterion("basesite_address is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressEqualTo(String value) {
            addCriterion("basesite_address =", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressNotEqualTo(String value) {
            addCriterion("basesite_address <>", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressGreaterThan(String value) {
            addCriterion("basesite_address >", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressGreaterThanOrEqualTo(String value) {
            addCriterion("basesite_address >=", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressLessThan(String value) {
            addCriterion("basesite_address <", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressLessThanOrEqualTo(String value) {
            addCriterion("basesite_address <=", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressLike(String value) {
            addCriterion("basesite_address like", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressNotLike(String value) {
            addCriterion("basesite_address not like", value, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressIn(List<String> values) {
            addCriterion("basesite_address in", values, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressNotIn(List<String> values) {
            addCriterion("basesite_address not in", values, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressBetween(String value1, String value2) {
            addCriterion("basesite_address between", value1, value2, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteAddressNotBetween(String value1, String value2) {
            addCriterion("basesite_address not between", value1, value2, "basesiteAddress");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeIsNull() {
            addCriterion("basesite_type is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeIsNotNull() {
            addCriterion("basesite_type is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeEqualTo(Integer value) {
            addCriterion("basesite_type =", value, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeNotEqualTo(Integer value) {
            addCriterion("basesite_type <>", value, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeGreaterThan(Integer value) {
            addCriterion("basesite_type >", value, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("basesite_type >=", value, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeLessThan(Integer value) {
            addCriterion("basesite_type <", value, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeLessThanOrEqualTo(Integer value) {
            addCriterion("basesite_type <=", value, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeIn(List<Integer> values) {
            addCriterion("basesite_type in", values, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeNotIn(List<Integer> values) {
            addCriterion("basesite_type not in", values, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeBetween(Integer value1, Integer value2) {
            addCriterion("basesite_type between", value1, value2, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("basesite_type not between", value1, value2, "basesiteType");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateIsNull() {
            addCriterion("basesite_state is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateIsNotNull() {
            addCriterion("basesite_state is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateEqualTo(Integer value) {
            addCriterion("basesite_state =", value, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateNotEqualTo(Integer value) {
            addCriterion("basesite_state <>", value, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateGreaterThan(Integer value) {
            addCriterion("basesite_state >", value, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("basesite_state >=", value, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateLessThan(Integer value) {
            addCriterion("basesite_state <", value, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateLessThanOrEqualTo(Integer value) {
            addCriterion("basesite_state <=", value, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateIn(List<Integer> values) {
            addCriterion("basesite_state in", values, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateNotIn(List<Integer> values) {
            addCriterion("basesite_state not in", values, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateBetween(Integer value1, Integer value2) {
            addCriterion("basesite_state between", value1, value2, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteStateNotBetween(Integer value1, Integer value2) {
            addCriterion("basesite_state not between", value1, value2, "basesiteState");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceIsNull() {
            addCriterion("basesite_maintenance is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceIsNotNull() {
            addCriterion("basesite_maintenance is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceEqualTo(Integer value) {
            addCriterion("basesite_maintenance =", value, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceNotEqualTo(Integer value) {
            addCriterion("basesite_maintenance <>", value, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceGreaterThan(Integer value) {
            addCriterion("basesite_maintenance >", value, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("basesite_maintenance >=", value, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceLessThan(Integer value) {
            addCriterion("basesite_maintenance <", value, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceLessThanOrEqualTo(Integer value) {
            addCriterion("basesite_maintenance <=", value, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceIn(List<Integer> values) {
            addCriterion("basesite_maintenance in", values, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceNotIn(List<Integer> values) {
            addCriterion("basesite_maintenance not in", values, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceBetween(Integer value1, Integer value2) {
            addCriterion("basesite_maintenance between", value1, value2, "basesiteMaintenance");
            return (Criteria) this;
        }

        public Criteria andBasesiteMaintenanceNotBetween(Integer value1, Integer value2) {
            addCriterion("basesite_maintenance not between", value1, value2, "basesiteMaintenance");
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

        public Criteria andBasesiteOpendateIsNull() {
            addCriterion("basesite_opendate is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateIsNotNull() {
            addCriterion("basesite_opendate is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateEqualTo(Date value) {
            addCriterion("basesite_opendate =", value, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateNotEqualTo(Date value) {
            addCriterion("basesite_opendate <>", value, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateGreaterThan(Date value) {
            addCriterion("basesite_opendate >", value, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateGreaterThanOrEqualTo(Date value) {
            addCriterion("basesite_opendate >=", value, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateLessThan(Date value) {
            addCriterion("basesite_opendate <", value, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateLessThanOrEqualTo(Date value) {
            addCriterion("basesite_opendate <=", value, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateIn(List<Date> values) {
            addCriterion("basesite_opendate in", values, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateNotIn(List<Date> values) {
            addCriterion("basesite_opendate not in", values, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateBetween(Date value1, Date value2) {
            addCriterion("basesite_opendate between", value1, value2, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteOpendateNotBetween(Date value1, Date value2) {
            addCriterion("basesite_opendate not between", value1, value2, "basesiteOpendate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateIsNull() {
            addCriterion("basesite_stopdate is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateIsNotNull() {
            addCriterion("basesite_stopdate is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateEqualTo(Date value) {
            addCriterion("basesite_stopdate =", value, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateNotEqualTo(Date value) {
            addCriterion("basesite_stopdate <>", value, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateGreaterThan(Date value) {
            addCriterion("basesite_stopdate >", value, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateGreaterThanOrEqualTo(Date value) {
            addCriterion("basesite_stopdate >=", value, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateLessThan(Date value) {
            addCriterion("basesite_stopdate <", value, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateLessThanOrEqualTo(Date value) {
            addCriterion("basesite_stopdate <=", value, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateIn(List<Date> values) {
            addCriterion("basesite_stopdate in", values, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateNotIn(List<Date> values) {
            addCriterion("basesite_stopdate not in", values, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateBetween(Date value1, Date value2) {
            addCriterion("basesite_stopdate between", value1, value2, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteStopdateNotBetween(Date value1, Date value2) {
            addCriterion("basesite_stopdate not between", value1, value2, "basesiteStopdate");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongIsNull() {
            addCriterion("basesite_belong is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongIsNotNull() {
            addCriterion("basesite_belong is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongEqualTo(Integer value) {
            addCriterion("basesite_belong =", value, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongNotEqualTo(Integer value) {
            addCriterion("basesite_belong <>", value, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongGreaterThan(Integer value) {
            addCriterion("basesite_belong >", value, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongGreaterThanOrEqualTo(Integer value) {
            addCriterion("basesite_belong >=", value, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongLessThan(Integer value) {
            addCriterion("basesite_belong <", value, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongLessThanOrEqualTo(Integer value) {
            addCriterion("basesite_belong <=", value, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongIn(List<Integer> values) {
            addCriterion("basesite_belong in", values, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongNotIn(List<Integer> values) {
            addCriterion("basesite_belong not in", values, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongBetween(Integer value1, Integer value2) {
            addCriterion("basesite_belong between", value1, value2, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesiteBelongNotBetween(Integer value1, Integer value2) {
            addCriterion("basesite_belong not between", value1, value2, "basesiteBelong");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyIsNull() {
            addCriterion("basesite_property is null");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyIsNotNull() {
            addCriterion("basesite_property is not null");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyEqualTo(Integer value) {
            addCriterion("basesite_property =", value, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyNotEqualTo(Integer value) {
            addCriterion("basesite_property <>", value, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyGreaterThan(Integer value) {
            addCriterion("basesite_property >", value, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyGreaterThanOrEqualTo(Integer value) {
            addCriterion("basesite_property >=", value, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyLessThan(Integer value) {
            addCriterion("basesite_property <", value, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyLessThanOrEqualTo(Integer value) {
            addCriterion("basesite_property <=", value, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyIn(List<Integer> values) {
            addCriterion("basesite_property in", values, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyNotIn(List<Integer> values) {
            addCriterion("basesite_property not in", values, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyBetween(Integer value1, Integer value2) {
            addCriterion("basesite_property between", value1, value2, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesitePropertyNotBetween(Integer value1, Integer value2) {
            addCriterion("basesite_property not between", value1, value2, "basesiteProperty");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareIsNull() {
            addCriterion("basesite_share is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareIsNotNull() {
            addCriterion("basesite_share is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareEqualTo(Integer value) {
            addCriterion("basesite_share =", value, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareNotEqualTo(Integer value) {
            addCriterion("basesite_share <>", value, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareGreaterThan(Integer value) {
            addCriterion("basesite_share >", value, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareGreaterThanOrEqualTo(Integer value) {
            addCriterion("basesite_share >=", value, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareLessThan(Integer value) {
            addCriterion("basesite_share <", value, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareLessThanOrEqualTo(Integer value) {
            addCriterion("basesite_share <=", value, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareIn(List<Integer> values) {
            addCriterion("basesite_share in", values, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareNotIn(List<Integer> values) {
            addCriterion("basesite_share not in", values, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareBetween(Integer value1, Integer value2) {
            addCriterion("basesite_share between", value1, value2, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteShareNotBetween(Integer value1, Integer value2) {
            addCriterion("basesite_share not between", value1, value2, "basesiteShare");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeIsNull() {
            addCriterion("basesite_longitude is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeIsNotNull() {
            addCriterion("basesite_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeEqualTo(BigDecimal value) {
            addCriterion("basesite_longitude =", value, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("basesite_longitude <>", value, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeGreaterThan(BigDecimal value) {
            addCriterion("basesite_longitude >", value, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("basesite_longitude >=", value, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeLessThan(BigDecimal value) {
            addCriterion("basesite_longitude <", value, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("basesite_longitude <=", value, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeIn(List<BigDecimal> values) {
            addCriterion("basesite_longitude in", values, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("basesite_longitude not in", values, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("basesite_longitude between", value1, value2, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("basesite_longitude not between", value1, value2, "basesiteLongitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeIsNull() {
            addCriterion("basesite_latitude is null");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeIsNotNull() {
            addCriterion("basesite_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeEqualTo(BigDecimal value) {
            addCriterion("basesite_latitude =", value, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("basesite_latitude <>", value, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeGreaterThan(BigDecimal value) {
            addCriterion("basesite_latitude >", value, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("basesite_latitude >=", value, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeLessThan(BigDecimal value) {
            addCriterion("basesite_latitude <", value, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("basesite_latitude <=", value, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeIn(List<BigDecimal> values) {
            addCriterion("basesite_latitude in", values, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("basesite_latitude not in", values, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("basesite_latitude between", value1, value2, "basesiteLatitude");
            return (Criteria) this;
        }

        public Criteria andBasesiteLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("basesite_latitude not between", value1, value2, "basesiteLatitude");
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