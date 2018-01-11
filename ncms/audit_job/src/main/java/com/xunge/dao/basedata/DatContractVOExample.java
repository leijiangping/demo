package com.xunge.dao.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DatContractVOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DatContractVOExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andContractIdIsNull() {
            addCriterion("contract_id is null");
            return (Criteria) this;
        }

        public Criteria andContractIdIsNotNull() {
            addCriterion("contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andContractIdEqualTo(String value) {
            addCriterion("contract_id =", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotEqualTo(String value) {
            addCriterion("contract_id <>", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdGreaterThan(String value) {
            addCriterion("contract_id >", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdGreaterThanOrEqualTo(String value) {
            addCriterion("contract_id >=", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLessThan(String value) {
            addCriterion("contract_id <", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLessThanOrEqualTo(String value) {
            addCriterion("contract_id <=", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdLike(String value) {
            addCriterion("contract_id like", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotLike(String value) {
            addCriterion("contract_id not like", value, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdIn(List<String> values) {
            addCriterion("contract_id in", values, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotIn(List<String> values) {
            addCriterion("contract_id not in", values, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdBetween(String value1, String value2) {
            addCriterion("contract_id between", value1, value2, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractIdNotBetween(String value1, String value2) {
            addCriterion("contract_id not between", value1, value2, "contractId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdIsNull() {
            addCriterion("contractsys_id is null");
            return (Criteria) this;
        }

        public Criteria andContractsysIdIsNotNull() {
            addCriterion("contractsys_id is not null");
            return (Criteria) this;
        }

        public Criteria andContractsysIdEqualTo(String value) {
            addCriterion("contractsys_id =", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdNotEqualTo(String value) {
            addCriterion("contractsys_id <>", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdGreaterThan(String value) {
            addCriterion("contractsys_id >", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdGreaterThanOrEqualTo(String value) {
            addCriterion("contractsys_id >=", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdLessThan(String value) {
            addCriterion("contractsys_id <", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdLessThanOrEqualTo(String value) {
            addCriterion("contractsys_id <=", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdLike(String value) {
            addCriterion("contractsys_id like", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdNotLike(String value) {
            addCriterion("contractsys_id not like", value, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdIn(List<String> values) {
            addCriterion("contractsys_id in", values, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdNotIn(List<String> values) {
            addCriterion("contractsys_id not in", values, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdBetween(String value1, String value2) {
            addCriterion("contractsys_id between", value1, value2, "contractsysId");
            return (Criteria) this;
        }

        public Criteria andContractsysIdNotBetween(String value1, String value2) {
            addCriterion("contractsys_id not between", value1, value2, "contractsysId");
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

        public Criteria andSysDepIdIsNull() {
            addCriterion("sys_dep_id is null");
            return (Criteria) this;
        }

        public Criteria andSysDepIdIsNotNull() {
            addCriterion("sys_dep_id is not null");
            return (Criteria) this;
        }

        public Criteria andSysDepIdEqualTo(String value) {
            addCriterion("sys_dep_id =", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdNotEqualTo(String value) {
            addCriterion("sys_dep_id <>", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdGreaterThan(String value) {
            addCriterion("sys_dep_id >", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdGreaterThanOrEqualTo(String value) {
            addCriterion("sys_dep_id >=", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdLessThan(String value) {
            addCriterion("sys_dep_id <", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdLessThanOrEqualTo(String value) {
            addCriterion("sys_dep_id <=", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdLike(String value) {
            addCriterion("sys_dep_id like", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdNotLike(String value) {
            addCriterion("sys_dep_id not like", value, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdIn(List<String> values) {
            addCriterion("sys_dep_id in", values, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdNotIn(List<String> values) {
            addCriterion("sys_dep_id not in", values, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdBetween(String value1, String value2) {
            addCriterion("sys_dep_id between", value1, value2, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andSysDepIdNotBetween(String value1, String value2) {
            addCriterion("sys_dep_id not between", value1, value2, "sysDepId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentIsNull() {
            addCriterion("manager_department is null");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentIsNotNull() {
            addCriterion("manager_department is not null");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentEqualTo(String value) {
            addCriterion("manager_department =", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentNotEqualTo(String value) {
            addCriterion("manager_department <>", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentGreaterThan(String value) {
            addCriterion("manager_department >", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentGreaterThanOrEqualTo(String value) {
            addCriterion("manager_department >=", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentLessThan(String value) {
            addCriterion("manager_department <", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentLessThanOrEqualTo(String value) {
            addCriterion("manager_department <=", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentLike(String value) {
            addCriterion("manager_department like", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentNotLike(String value) {
            addCriterion("manager_department not like", value, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentIn(List<String> values) {
            addCriterion("manager_department in", values, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentNotIn(List<String> values) {
            addCriterion("manager_department not in", values, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentBetween(String value1, String value2) {
            addCriterion("manager_department between", value1, value2, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerDepartmentNotBetween(String value1, String value2) {
            addCriterion("manager_department not between", value1, value2, "managerDepartment");
            return (Criteria) this;
        }

        public Criteria andManagerUserIsNull() {
            addCriterion("manager_user is null");
            return (Criteria) this;
        }

        public Criteria andManagerUserIsNotNull() {
            addCriterion("manager_user is not null");
            return (Criteria) this;
        }

        public Criteria andManagerUserEqualTo(String value) {
            addCriterion("manager_user =", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotEqualTo(String value) {
            addCriterion("manager_user <>", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserGreaterThan(String value) {
            addCriterion("manager_user >", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserGreaterThanOrEqualTo(String value) {
            addCriterion("manager_user >=", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserLessThan(String value) {
            addCriterion("manager_user <", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserLessThanOrEqualTo(String value) {
            addCriterion("manager_user <=", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserLike(String value) {
            addCriterion("manager_user like", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotLike(String value) {
            addCriterion("manager_user not like", value, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserIn(List<String> values) {
            addCriterion("manager_user in", values, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotIn(List<String> values) {
            addCriterion("manager_user not in", values, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserBetween(String value1, String value2) {
            addCriterion("manager_user between", value1, value2, "managerUser");
            return (Criteria) this;
        }

        public Criteria andManagerUserNotBetween(String value1, String value2) {
            addCriterion("manager_user not between", value1, value2, "managerUser");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNull() {
            addCriterion("contract_code is null");
            return (Criteria) this;
        }

        public Criteria andContractCodeIsNotNull() {
            addCriterion("contract_code is not null");
            return (Criteria) this;
        }

        public Criteria andContractCodeEqualTo(String value) {
            addCriterion("contract_code =", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotEqualTo(String value) {
            addCriterion("contract_code <>", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThan(String value) {
            addCriterion("contract_code >", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeGreaterThanOrEqualTo(String value) {
            addCriterion("contract_code >=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThan(String value) {
            addCriterion("contract_code <", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLessThanOrEqualTo(String value) {
            addCriterion("contract_code <=", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeLike(String value) {
            addCriterion("contract_code like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotLike(String value) {
            addCriterion("contract_code not like", value, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeIn(List<String> values) {
            addCriterion("contract_code in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotIn(List<String> values) {
            addCriterion("contract_code not in", values, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeBetween(String value1, String value2) {
            addCriterion("contract_code between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractCodeNotBetween(String value1, String value2) {
            addCriterion("contract_code not between", value1, value2, "contractCode");
            return (Criteria) this;
        }

        public Criteria andContractNameIsNull() {
            addCriterion("contract_name is null");
            return (Criteria) this;
        }

        public Criteria andContractNameIsNotNull() {
            addCriterion("contract_name is not null");
            return (Criteria) this;
        }

        public Criteria andContractNameEqualTo(String value) {
            addCriterion("contract_name =", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotEqualTo(String value) {
            addCriterion("contract_name <>", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameGreaterThan(String value) {
            addCriterion("contract_name >", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameGreaterThanOrEqualTo(String value) {
            addCriterion("contract_name >=", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameLessThan(String value) {
            addCriterion("contract_name <", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameLessThanOrEqualTo(String value) {
            addCriterion("contract_name <=", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameLike(String value) {
            addCriterion("contract_name like", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotLike(String value) {
            addCriterion("contract_name not like", value, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameIn(List<String> values) {
            addCriterion("contract_name in", values, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotIn(List<String> values) {
            addCriterion("contract_name not in", values, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameBetween(String value1, String value2) {
            addCriterion("contract_name between", value1, value2, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractNameNotBetween(String value1, String value2) {
            addCriterion("contract_name not between", value1, value2, "contractName");
            return (Criteria) this;
        }

        public Criteria andContractTypeIsNull() {
            addCriterion("contract_type is null");
            return (Criteria) this;
        }

        public Criteria andContractTypeIsNotNull() {
            addCriterion("contract_type is not null");
            return (Criteria) this;
        }

        public Criteria andContractTypeEqualTo(Integer value) {
            addCriterion("contract_type =", value, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeNotEqualTo(Integer value) {
            addCriterion("contract_type <>", value, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeGreaterThan(Integer value) {
            addCriterion("contract_type >", value, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("contract_type >=", value, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeLessThan(Integer value) {
            addCriterion("contract_type <", value, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeLessThanOrEqualTo(Integer value) {
            addCriterion("contract_type <=", value, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeIn(List<Integer> values) {
            addCriterion("contract_type in", values, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeNotIn(List<Integer> values) {
            addCriterion("contract_type not in", values, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeBetween(Integer value1, Integer value2) {
            addCriterion("contract_type between", value1, value2, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("contract_type not between", value1, value2, "contractType");
            return (Criteria) this;
        }

        public Criteria andContractStartdateIsNull() {
            addCriterion("contract_startdate is null");
            return (Criteria) this;
        }

        public Criteria andContractStartdateIsNotNull() {
            addCriterion("contract_startdate is not null");
            return (Criteria) this;
        }

        public Criteria andContractStartdateEqualTo(Date value) {
            addCriterionForJDBCDate("contract_startdate =", value, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("contract_startdate <>", value, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateGreaterThan(Date value) {
            addCriterionForJDBCDate("contract_startdate >", value, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_startdate >=", value, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateLessThan(Date value) {
            addCriterionForJDBCDate("contract_startdate <", value, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_startdate <=", value, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateIn(List<Date> values) {
            addCriterionForJDBCDate("contract_startdate in", values, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("contract_startdate not in", values, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_startdate between", value1, value2, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractStartdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_startdate not between", value1, value2, "contractStartdate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateIsNull() {
            addCriterion("contract_enddate is null");
            return (Criteria) this;
        }

        public Criteria andContractEnddateIsNotNull() {
            addCriterion("contract_enddate is not null");
            return (Criteria) this;
        }

        public Criteria andContractEnddateEqualTo(Date value) {
            addCriterionForJDBCDate("contract_enddate =", value, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateNotEqualTo(Date value) {
            addCriterionForJDBCDate("contract_enddate <>", value, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateGreaterThan(Date value) {
            addCriterionForJDBCDate("contract_enddate >", value, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_enddate >=", value, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateLessThan(Date value) {
            addCriterionForJDBCDate("contract_enddate <", value, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_enddate <=", value, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateIn(List<Date> values) {
            addCriterionForJDBCDate("contract_enddate in", values, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateNotIn(List<Date> values) {
            addCriterionForJDBCDate("contract_enddate not in", values, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_enddate between", value1, value2, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractEnddateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_enddate not between", value1, value2, "contractEnddate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateIsNull() {
            addCriterion("contract_signdate is null");
            return (Criteria) this;
        }

        public Criteria andContractSigndateIsNotNull() {
            addCriterion("contract_signdate is not null");
            return (Criteria) this;
        }

        public Criteria andContractSigndateEqualTo(Date value) {
            addCriterionForJDBCDate("contract_signdate =", value, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateNotEqualTo(Date value) {
            addCriterionForJDBCDate("contract_signdate <>", value, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateGreaterThan(Date value) {
            addCriterionForJDBCDate("contract_signdate >", value, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_signdate >=", value, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateLessThan(Date value) {
            addCriterionForJDBCDate("contract_signdate <", value, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_signdate <=", value, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateIn(List<Date> values) {
            addCriterionForJDBCDate("contract_signdate in", values, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateNotIn(List<Date> values) {
            addCriterionForJDBCDate("contract_signdate not in", values, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_signdate between", value1, value2, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractSigndateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_signdate not between", value1, value2, "contractSigndate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateIsNull() {
            addCriterion("contract_changeenddate is null");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateIsNotNull() {
            addCriterion("contract_changeenddate is not null");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changeenddate =", value, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateNotEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changeenddate <>", value, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateGreaterThan(Date value) {
            addCriterionForJDBCDate("contract_changeenddate >", value, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changeenddate >=", value, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateLessThan(Date value) {
            addCriterionForJDBCDate("contract_changeenddate <", value, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changeenddate <=", value, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateIn(List<Date> values) {
            addCriterionForJDBCDate("contract_changeenddate in", values, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateNotIn(List<Date> values) {
            addCriterionForJDBCDate("contract_changeenddate not in", values, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_changeenddate between", value1, value2, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangeenddateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_changeenddate not between", value1, value2, "contractChangeenddate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateIsNull() {
            addCriterion("contract_changedate is null");
            return (Criteria) this;
        }

        public Criteria andContractChangedateIsNotNull() {
            addCriterion("contract_changedate is not null");
            return (Criteria) this;
        }

        public Criteria andContractChangedateEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changedate =", value, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateNotEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changedate <>", value, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateGreaterThan(Date value) {
            addCriterionForJDBCDate("contract_changedate >", value, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changedate >=", value, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateLessThan(Date value) {
            addCriterionForJDBCDate("contract_changedate <", value, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("contract_changedate <=", value, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateIn(List<Date> values) {
            addCriterionForJDBCDate("contract_changedate in", values, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateNotIn(List<Date> values) {
            addCriterionForJDBCDate("contract_changedate not in", values, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_changedate between", value1, value2, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractChangedateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("contract_changedate not between", value1, value2, "contractChangedate");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityIsNull() {
            addCriterion("contract_yearquantity is null");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityIsNotNull() {
            addCriterion("contract_yearquantity is not null");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityEqualTo(BigDecimal value) {
            addCriterion("contract_yearquantity =", value, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityNotEqualTo(BigDecimal value) {
            addCriterion("contract_yearquantity <>", value, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityGreaterThan(BigDecimal value) {
            addCriterion("contract_yearquantity >", value, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_yearquantity >=", value, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityLessThan(BigDecimal value) {
            addCriterion("contract_yearquantity <", value, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_yearquantity <=", value, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityIn(List<BigDecimal> values) {
            addCriterion("contract_yearquantity in", values, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityNotIn(List<BigDecimal> values) {
            addCriterion("contract_yearquantity not in", values, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_yearquantity between", value1, value2, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractYearquantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_yearquantity not between", value1, value2, "contractYearquantity");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1IsNull() {
            addCriterion("contract_checkname1 is null");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1IsNotNull() {
            addCriterion("contract_checkname1 is not null");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1EqualTo(String value) {
            addCriterion("contract_checkname1 =", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1NotEqualTo(String value) {
            addCriterion("contract_checkname1 <>", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1GreaterThan(String value) {
            addCriterion("contract_checkname1 >", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1GreaterThanOrEqualTo(String value) {
            addCriterion("contract_checkname1 >=", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1LessThan(String value) {
            addCriterion("contract_checkname1 <", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1LessThanOrEqualTo(String value) {
            addCriterion("contract_checkname1 <=", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1Like(String value) {
            addCriterion("contract_checkname1 like", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1NotLike(String value) {
            addCriterion("contract_checkname1 not like", value, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1In(List<String> values) {
            addCriterion("contract_checkname1 in", values, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1NotIn(List<String> values) {
            addCriterion("contract_checkname1 not in", values, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1Between(String value1, String value2) {
            addCriterion("contract_checkname1 between", value1, value2, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname1NotBetween(String value1, String value2) {
            addCriterion("contract_checkname1 not between", value1, value2, "contractCheckname1");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2IsNull() {
            addCriterion("contract_checkname2 is null");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2IsNotNull() {
            addCriterion("contract_checkname2 is not null");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2EqualTo(String value) {
            addCriterion("contract_checkname2 =", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2NotEqualTo(String value) {
            addCriterion("contract_checkname2 <>", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2GreaterThan(String value) {
            addCriterion("contract_checkname2 >", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2GreaterThanOrEqualTo(String value) {
            addCriterion("contract_checkname2 >=", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2LessThan(String value) {
            addCriterion("contract_checkname2 <", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2LessThanOrEqualTo(String value) {
            addCriterion("contract_checkname2 <=", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2Like(String value) {
            addCriterion("contract_checkname2 like", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2NotLike(String value) {
            addCriterion("contract_checkname2 not like", value, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2In(List<String> values) {
            addCriterion("contract_checkname2 in", values, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2NotIn(List<String> values) {
            addCriterion("contract_checkname2 not in", values, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2Between(String value1, String value2) {
            addCriterion("contract_checkname2 between", value1, value2, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andContractCheckname2NotBetween(String value1, String value2) {
            addCriterion("contract_checkname2 not between", value1, value2, "contractCheckname2");
            return (Criteria) this;
        }

        public Criteria andOldContractIdIsNull() {
            addCriterion("old_contract_id is null");
            return (Criteria) this;
        }

        public Criteria andOldContractIdIsNotNull() {
            addCriterion("old_contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andOldContractIdEqualTo(String value) {
            addCriterion("old_contract_id =", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdNotEqualTo(String value) {
            addCriterion("old_contract_id <>", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdGreaterThan(String value) {
            addCriterion("old_contract_id >", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdGreaterThanOrEqualTo(String value) {
            addCriterion("old_contract_id >=", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdLessThan(String value) {
            addCriterion("old_contract_id <", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdLessThanOrEqualTo(String value) {
            addCriterion("old_contract_id <=", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdLike(String value) {
            addCriterion("old_contract_id like", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdNotLike(String value) {
            addCriterion("old_contract_id not like", value, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdIn(List<String> values) {
            addCriterion("old_contract_id in", values, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdNotIn(List<String> values) {
            addCriterion("old_contract_id not in", values, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdBetween(String value1, String value2) {
            addCriterion("old_contract_id between", value1, value2, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractIdNotBetween(String value1, String value2) {
            addCriterion("old_contract_id not between", value1, value2, "oldContractId");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeIsNull() {
            addCriterion("old_contract_code is null");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeIsNotNull() {
            addCriterion("old_contract_code is not null");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeEqualTo(String value) {
            addCriterion("old_contract_code =", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeNotEqualTo(String value) {
            addCriterion("old_contract_code <>", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeGreaterThan(String value) {
            addCriterion("old_contract_code >", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeGreaterThanOrEqualTo(String value) {
            addCriterion("old_contract_code >=", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeLessThan(String value) {
            addCriterion("old_contract_code <", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeLessThanOrEqualTo(String value) {
            addCriterion("old_contract_code <=", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeLike(String value) {
            addCriterion("old_contract_code like", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeNotLike(String value) {
            addCriterion("old_contract_code not like", value, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeIn(List<String> values) {
            addCriterion("old_contract_code in", values, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeNotIn(List<String> values) {
            addCriterion("old_contract_code not in", values, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeBetween(String value1, String value2) {
            addCriterion("old_contract_code between", value1, value2, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andOldContractCodeNotBetween(String value1, String value2) {
            addCriterion("old_contract_code not between", value1, value2, "oldContractCode");
            return (Criteria) this;
        }

        public Criteria andContractFlowIsNull() {
            addCriterion("contract_flow is null");
            return (Criteria) this;
        }

        public Criteria andContractFlowIsNotNull() {
            addCriterion("contract_flow is not null");
            return (Criteria) this;
        }

        public Criteria andContractFlowEqualTo(String value) {
            addCriterion("contract_flow =", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowNotEqualTo(String value) {
            addCriterion("contract_flow <>", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowGreaterThan(String value) {
            addCriterion("contract_flow >", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowGreaterThanOrEqualTo(String value) {
            addCriterion("contract_flow >=", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowLessThan(String value) {
            addCriterion("contract_flow <", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowLessThanOrEqualTo(String value) {
            addCriterion("contract_flow <=", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowLike(String value) {
            addCriterion("contract_flow like", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowNotLike(String value) {
            addCriterion("contract_flow not like", value, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowIn(List<String> values) {
            addCriterion("contract_flow in", values, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowNotIn(List<String> values) {
            addCriterion("contract_flow not in", values, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowBetween(String value1, String value2) {
            addCriterion("contract_flow between", value1, value2, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractFlowNotBetween(String value1, String value2) {
            addCriterion("contract_flow not between", value1, value2, "contractFlow");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionIsNull() {
            addCriterion("contract_introduction is null");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionIsNotNull() {
            addCriterion("contract_introduction is not null");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionEqualTo(String value) {
            addCriterion("contract_introduction =", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionNotEqualTo(String value) {
            addCriterion("contract_introduction <>", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionGreaterThan(String value) {
            addCriterion("contract_introduction >", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionGreaterThanOrEqualTo(String value) {
            addCriterion("contract_introduction >=", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionLessThan(String value) {
            addCriterion("contract_introduction <", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionLessThanOrEqualTo(String value) {
            addCriterion("contract_introduction <=", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionLike(String value) {
            addCriterion("contract_introduction like", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionNotLike(String value) {
            addCriterion("contract_introduction not like", value, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionIn(List<String> values) {
            addCriterion("contract_introduction in", values, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionNotIn(List<String> values) {
            addCriterion("contract_introduction not in", values, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionBetween(String value1, String value2) {
            addCriterion("contract_introduction between", value1, value2, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractIntroductionNotBetween(String value1, String value2) {
            addCriterion("contract_introduction not between", value1, value2, "contractIntroduction");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceIsNull() {
            addCriterion("contract_spaceresource is null");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceIsNotNull() {
            addCriterion("contract_spaceresource is not null");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceEqualTo(String value) {
            addCriterion("contract_spaceresource =", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceNotEqualTo(String value) {
            addCriterion("contract_spaceresource <>", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceGreaterThan(String value) {
            addCriterion("contract_spaceresource >", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceGreaterThanOrEqualTo(String value) {
            addCriterion("contract_spaceresource >=", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceLessThan(String value) {
            addCriterion("contract_spaceresource <", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceLessThanOrEqualTo(String value) {
            addCriterion("contract_spaceresource <=", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceLike(String value) {
            addCriterion("contract_spaceresource like", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceNotLike(String value) {
            addCriterion("contract_spaceresource not like", value, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceIn(List<String> values) {
            addCriterion("contract_spaceresource in", values, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceNotIn(List<String> values) {
            addCriterion("contract_spaceresource not in", values, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceBetween(String value1, String value2) {
            addCriterion("contract_spaceresource between", value1, value2, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractSpaceresourceNotBetween(String value1, String value2) {
            addCriterion("contract_spaceresource not between", value1, value2, "contractSpaceresource");
            return (Criteria) this;
        }

        public Criteria andContractStateIsNull() {
            addCriterion("contract_state is null");
            return (Criteria) this;
        }

        public Criteria andContractStateIsNotNull() {
            addCriterion("contract_state is not null");
            return (Criteria) this;
        }

        public Criteria andContractStateEqualTo(Integer value) {
            addCriterion("contract_state =", value, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateNotEqualTo(Integer value) {
            addCriterion("contract_state <>", value, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateGreaterThan(Integer value) {
            addCriterion("contract_state >", value, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("contract_state >=", value, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateLessThan(Integer value) {
            addCriterion("contract_state <", value, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateLessThanOrEqualTo(Integer value) {
            addCriterion("contract_state <=", value, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateIn(List<Integer> values) {
            addCriterion("contract_state in", values, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateNotIn(List<Integer> values) {
            addCriterion("contract_state not in", values, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateBetween(Integer value1, Integer value2) {
            addCriterion("contract_state between", value1, value2, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractStateNotBetween(Integer value1, Integer value2) {
            addCriterion("contract_state not between", value1, value2, "contractState");
            return (Criteria) this;
        }

        public Criteria andContractNoteIsNull() {
            addCriterion("contract_note is null");
            return (Criteria) this;
        }

        public Criteria andContractNoteIsNotNull() {
            addCriterion("contract_note is not null");
            return (Criteria) this;
        }

        public Criteria andContractNoteEqualTo(String value) {
            addCriterion("contract_note =", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteNotEqualTo(String value) {
            addCriterion("contract_note <>", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteGreaterThan(String value) {
            addCriterion("contract_note >", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteGreaterThanOrEqualTo(String value) {
            addCriterion("contract_note >=", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteLessThan(String value) {
            addCriterion("contract_note <", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteLessThanOrEqualTo(String value) {
            addCriterion("contract_note <=", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteLike(String value) {
            addCriterion("contract_note like", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteNotLike(String value) {
            addCriterion("contract_note not like", value, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteIn(List<String> values) {
            addCriterion("contract_note in", values, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteNotIn(List<String> values) {
            addCriterion("contract_note not in", values, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteBetween(String value1, String value2) {
            addCriterion("contract_note between", value1, value2, "contractNote");
            return (Criteria) this;
        }

        public Criteria andContractNoteNotBetween(String value1, String value2) {
            addCriterion("contract_note not between", value1, value2, "contractNote");
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