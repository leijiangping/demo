package com.xunge.dao.basedata;

import java.util.ArrayList;
import java.util.List;

public class DatSupplierVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DatSupplierVOExample() {
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

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("supplier_id like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("supplier_id not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
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

        public Criteria andIsDownshareIsNull() {
            addCriterion("is_downshare is null");
            return (Criteria) this;
        }

        public Criteria andIsDownshareIsNotNull() {
            addCriterion("is_downshare is not null");
            return (Criteria) this;
        }

        public Criteria andIsDownshareEqualTo(Integer value) {
            addCriterion("is_downshare =", value, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareNotEqualTo(Integer value) {
            addCriterion("is_downshare <>", value, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareGreaterThan(Integer value) {
            addCriterion("is_downshare >", value, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_downshare >=", value, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareLessThan(Integer value) {
            addCriterion("is_downshare <", value, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareLessThanOrEqualTo(Integer value) {
            addCriterion("is_downshare <=", value, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareIn(List<Integer> values) {
            addCriterion("is_downshare in", values, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareNotIn(List<Integer> values) {
            addCriterion("is_downshare not in", values, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareBetween(Integer value1, Integer value2) {
            addCriterion("is_downshare between", value1, value2, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andIsDownshareNotBetween(Integer value1, Integer value2) {
            addCriterion("is_downshare not between", value1, value2, "isDownshare");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNull() {
            addCriterion("supplier_code is null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIsNotNull() {
            addCriterion("supplier_code is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeEqualTo(String value) {
            addCriterion("supplier_code =", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotEqualTo(String value) {
            addCriterion("supplier_code <>", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThan(String value) {
            addCriterion("supplier_code >", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_code >=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThan(String value) {
            addCriterion("supplier_code <", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLessThanOrEqualTo(String value) {
            addCriterion("supplier_code <=", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeLike(String value) {
            addCriterion("supplier_code like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotLike(String value) {
            addCriterion("supplier_code not like", value, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeIn(List<String> values) {
            addCriterion("supplier_code in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotIn(List<String> values) {
            addCriterion("supplier_code not in", values, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeBetween(String value1, String value2) {
            addCriterion("supplier_code between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierCodeNotBetween(String value1, String value2) {
            addCriterion("supplier_code not between", value1, value2, "supplierCode");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteIsNull() {
            addCriterion("supplier_site is null");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteIsNotNull() {
            addCriterion("supplier_site is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteEqualTo(String value) {
            addCriterion("supplier_site =", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteNotEqualTo(String value) {
            addCriterion("supplier_site <>", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteGreaterThan(String value) {
            addCriterion("supplier_site >", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_site >=", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteLessThan(String value) {
            addCriterion("supplier_site <", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteLessThanOrEqualTo(String value) {
            addCriterion("supplier_site <=", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteLike(String value) {
            addCriterion("supplier_site like", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteNotLike(String value) {
            addCriterion("supplier_site not like", value, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteIn(List<String> values) {
            addCriterion("supplier_site in", values, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteNotIn(List<String> values) {
            addCriterion("supplier_site not in", values, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteBetween(String value1, String value2) {
            addCriterion("supplier_site between", value1, value2, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierSiteNotBetween(String value1, String value2) {
            addCriterion("supplier_site not between", value1, value2, "supplierSite");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressIsNull() {
            addCriterion("supplier_address is null");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressIsNotNull() {
            addCriterion("supplier_address is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressEqualTo(String value) {
            addCriterion("supplier_address =", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotEqualTo(String value) {
            addCriterion("supplier_address <>", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressGreaterThan(String value) {
            addCriterion("supplier_address >", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_address >=", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressLessThan(String value) {
            addCriterion("supplier_address <", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressLessThanOrEqualTo(String value) {
            addCriterion("supplier_address <=", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressLike(String value) {
            addCriterion("supplier_address like", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotLike(String value) {
            addCriterion("supplier_address not like", value, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressIn(List<String> values) {
            addCriterion("supplier_address in", values, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotIn(List<String> values) {
            addCriterion("supplier_address not in", values, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressBetween(String value1, String value2) {
            addCriterion("supplier_address between", value1, value2, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierAddressNotBetween(String value1, String value2) {
            addCriterion("supplier_address not between", value1, value2, "supplierAddress");
            return (Criteria) this;
        }

        public Criteria andSupplierContactIsNull() {
            addCriterion("supplier_contact is null");
            return (Criteria) this;
        }

        public Criteria andSupplierContactIsNotNull() {
            addCriterion("supplier_contact is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierContactEqualTo(String value) {
            addCriterion("supplier_contact =", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactNotEqualTo(String value) {
            addCriterion("supplier_contact <>", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactGreaterThan(String value) {
            addCriterion("supplier_contact >", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_contact >=", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactLessThan(String value) {
            addCriterion("supplier_contact <", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactLessThanOrEqualTo(String value) {
            addCriterion("supplier_contact <=", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactLike(String value) {
            addCriterion("supplier_contact like", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactNotLike(String value) {
            addCriterion("supplier_contact not like", value, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactIn(List<String> values) {
            addCriterion("supplier_contact in", values, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactNotIn(List<String> values) {
            addCriterion("supplier_contact not in", values, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactBetween(String value1, String value2) {
            addCriterion("supplier_contact between", value1, value2, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierContactNotBetween(String value1, String value2) {
            addCriterion("supplier_contact not between", value1, value2, "supplierContact");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneIsNull() {
            addCriterion("supplier_telephone is null");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneIsNotNull() {
            addCriterion("supplier_telephone is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneEqualTo(String value) {
            addCriterion("supplier_telephone =", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneNotEqualTo(String value) {
            addCriterion("supplier_telephone <>", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneGreaterThan(String value) {
            addCriterion("supplier_telephone >", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_telephone >=", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneLessThan(String value) {
            addCriterion("supplier_telephone <", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneLessThanOrEqualTo(String value) {
            addCriterion("supplier_telephone <=", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneLike(String value) {
            addCriterion("supplier_telephone like", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneNotLike(String value) {
            addCriterion("supplier_telephone not like", value, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneIn(List<String> values) {
            addCriterion("supplier_telephone in", values, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneNotIn(List<String> values) {
            addCriterion("supplier_telephone not in", values, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneBetween(String value1, String value2) {
            addCriterion("supplier_telephone between", value1, value2, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTelephoneNotBetween(String value1, String value2) {
            addCriterion("supplier_telephone not between", value1, value2, "supplierTelephone");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeIsNull() {
            addCriterion("supplier_type is null");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeIsNotNull() {
            addCriterion("supplier_type is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeEqualTo(Integer value) {
            addCriterion("supplier_type =", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeNotEqualTo(Integer value) {
            addCriterion("supplier_type <>", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeGreaterThan(Integer value) {
            addCriterion("supplier_type >", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("supplier_type >=", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeLessThan(Integer value) {
            addCriterion("supplier_type <", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeLessThanOrEqualTo(Integer value) {
            addCriterion("supplier_type <=", value, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeIn(List<Integer> values) {
            addCriterion("supplier_type in", values, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeNotIn(List<Integer> values) {
            addCriterion("supplier_type not in", values, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeBetween(Integer value1, Integer value2) {
            addCriterion("supplier_type between", value1, value2, "supplierType");
            return (Criteria) this;
        }

        public Criteria andSupplierTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("supplier_type not between", value1, value2, "supplierType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNull() {
            addCriterion("account_type is null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIsNotNull() {
            addCriterion("account_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccountTypeEqualTo(Integer value) {
            addCriterion("account_type =", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotEqualTo(Integer value) {
            addCriterion("account_type <>", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThan(Integer value) {
            addCriterion("account_type >", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_type >=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThan(Integer value) {
            addCriterion("account_type <", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeLessThanOrEqualTo(Integer value) {
            addCriterion("account_type <=", value, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeIn(List<Integer> values) {
            addCriterion("account_type in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotIn(List<Integer> values) {
            addCriterion("account_type not in", values, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeBetween(Integer value1, Integer value2) {
            addCriterion("account_type between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andAccountTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("account_type not between", value1, value2, "accountType");
            return (Criteria) this;
        }

        public Criteria andBankUserIsNull() {
            addCriterion("bank_user is null");
            return (Criteria) this;
        }

        public Criteria andBankUserIsNotNull() {
            addCriterion("bank_user is not null");
            return (Criteria) this;
        }

        public Criteria andBankUserEqualTo(String value) {
            addCriterion("bank_user =", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotEqualTo(String value) {
            addCriterion("bank_user <>", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserGreaterThan(String value) {
            addCriterion("bank_user >", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserGreaterThanOrEqualTo(String value) {
            addCriterion("bank_user >=", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserLessThan(String value) {
            addCriterion("bank_user <", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserLessThanOrEqualTo(String value) {
            addCriterion("bank_user <=", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserLike(String value) {
            addCriterion("bank_user like", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotLike(String value) {
            addCriterion("bank_user not like", value, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserIn(List<String> values) {
            addCriterion("bank_user in", values, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotIn(List<String> values) {
            addCriterion("bank_user not in", values, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserBetween(String value1, String value2) {
            addCriterion("bank_user between", value1, value2, "bankUser");
            return (Criteria) this;
        }

        public Criteria andBankUserNotBetween(String value1, String value2) {
            addCriterion("bank_user not between", value1, value2, "bankUser");
            return (Criteria) this;
        }

        public Criteria andDepositBankIsNull() {
            addCriterion("deposit_bank is null");
            return (Criteria) this;
        }

        public Criteria andDepositBankIsNotNull() {
            addCriterion("deposit_bank is not null");
            return (Criteria) this;
        }

        public Criteria andDepositBankEqualTo(String value) {
            addCriterion("deposit_bank =", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankNotEqualTo(String value) {
            addCriterion("deposit_bank <>", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankGreaterThan(String value) {
            addCriterion("deposit_bank >", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankGreaterThanOrEqualTo(String value) {
            addCriterion("deposit_bank >=", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankLessThan(String value) {
            addCriterion("deposit_bank <", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankLessThanOrEqualTo(String value) {
            addCriterion("deposit_bank <=", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankLike(String value) {
            addCriterion("deposit_bank like", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankNotLike(String value) {
            addCriterion("deposit_bank not like", value, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankIn(List<String> values) {
            addCriterion("deposit_bank in", values, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankNotIn(List<String> values) {
            addCriterion("deposit_bank not in", values, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankBetween(String value1, String value2) {
            addCriterion("deposit_bank between", value1, value2, "depositBank");
            return (Criteria) this;
        }

        public Criteria andDepositBankNotBetween(String value1, String value2) {
            addCriterion("deposit_bank not between", value1, value2, "depositBank");
            return (Criteria) this;
        }

        public Criteria andBankAccountIsNull() {
            addCriterion("bank_account is null");
            return (Criteria) this;
        }

        public Criteria andBankAccountIsNotNull() {
            addCriterion("bank_account is not null");
            return (Criteria) this;
        }

        public Criteria andBankAccountEqualTo(String value) {
            addCriterion("bank_account =", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotEqualTo(String value) {
            addCriterion("bank_account <>", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountGreaterThan(String value) {
            addCriterion("bank_account >", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountGreaterThanOrEqualTo(String value) {
            addCriterion("bank_account >=", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountLessThan(String value) {
            addCriterion("bank_account <", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountLessThanOrEqualTo(String value) {
            addCriterion("bank_account <=", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountLike(String value) {
            addCriterion("bank_account like", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotLike(String value) {
            addCriterion("bank_account not like", value, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountIn(List<String> values) {
            addCriterion("bank_account in", values, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotIn(List<String> values) {
            addCriterion("bank_account not in", values, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountBetween(String value1, String value2) {
            addCriterion("bank_account between", value1, value2, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andBankAccountNotBetween(String value1, String value2) {
            addCriterion("bank_account not between", value1, value2, "bankAccount");
            return (Criteria) this;
        }

        public Criteria andSupplierStateIsNull() {
            addCriterion("supplier_state is null");
            return (Criteria) this;
        }

        public Criteria andSupplierStateIsNotNull() {
            addCriterion("supplier_state is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierStateEqualTo(Integer value) {
            addCriterion("supplier_state =", value, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateNotEqualTo(Integer value) {
            addCriterion("supplier_state <>", value, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateGreaterThan(Integer value) {
            addCriterion("supplier_state >", value, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("supplier_state >=", value, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateLessThan(Integer value) {
            addCriterion("supplier_state <", value, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateLessThanOrEqualTo(Integer value) {
            addCriterion("supplier_state <=", value, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateIn(List<Integer> values) {
            addCriterion("supplier_state in", values, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateNotIn(List<Integer> values) {
            addCriterion("supplier_state not in", values, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateBetween(Integer value1, Integer value2) {
            addCriterion("supplier_state between", value1, value2, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierStateNotBetween(Integer value1, Integer value2) {
            addCriterion("supplier_state not between", value1, value2, "supplierState");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteIsNull() {
            addCriterion("supplier_note is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteIsNotNull() {
            addCriterion("supplier_note is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteEqualTo(String value) {
            addCriterion("supplier_note =", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteNotEqualTo(String value) {
            addCriterion("supplier_note <>", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteGreaterThan(String value) {
            addCriterion("supplier_note >", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_note >=", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteLessThan(String value) {
            addCriterion("supplier_note <", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteLessThanOrEqualTo(String value) {
            addCriterion("supplier_note <=", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteLike(String value) {
            addCriterion("supplier_note like", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteNotLike(String value) {
            addCriterion("supplier_note not like", value, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteIn(List<String> values) {
            addCriterion("supplier_note in", values, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteNotIn(List<String> values) {
            addCriterion("supplier_note not in", values, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteBetween(String value1, String value2) {
            addCriterion("supplier_note between", value1, value2, "supplierNote");
            return (Criteria) this;
        }

        public Criteria andSupplierNoteNotBetween(String value1, String value2) {
            addCriterion("supplier_note not between", value1, value2, "supplierNote");
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