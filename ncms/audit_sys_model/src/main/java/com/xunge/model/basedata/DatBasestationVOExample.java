package com.xunge.model.basedata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatBasestationVOExample implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4022227726751142527L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DatBasestationVOExample() {
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

        public Criteria andBasestationIdIsNull() {
            addCriterion("basestation_id is null");
            return (Criteria) this;
        }

        public Criteria andBasestationIdIsNotNull() {
            addCriterion("basestation_id is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationIdEqualTo(String value) {
            addCriterion("basestation_id =", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdNotEqualTo(String value) {
            addCriterion("basestation_id <>", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdGreaterThan(String value) {
            addCriterion("basestation_id >", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdGreaterThanOrEqualTo(String value) {
            addCriterion("basestation_id >=", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdLessThan(String value) {
            addCriterion("basestation_id <", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdLessThanOrEqualTo(String value) {
            addCriterion("basestation_id <=", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdLike(String value) {
            addCriterion("basestation_id like", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdNotLike(String value) {
            addCriterion("basestation_id not like", value, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdIn(List<String> values) {
            addCriterion("basestation_id in", values, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdNotIn(List<String> values) {
            addCriterion("basestation_id not in", values, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdBetween(String value1, String value2) {
            addCriterion("basestation_id between", value1, value2, "basestationId");
            return (Criteria) this;
        }

        public Criteria andBasestationIdNotBetween(String value1, String value2) {
            addCriterion("basestation_id not between", value1, value2, "basestationId");
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
        
        public Criteria andPregIdIn(List<String> values) {
            addCriterion("preg_id in", values, "pregId");
            return (Criteria) this;
        }
        
        public Criteria andRegIdIn(List<String> values) {
            addCriterion("reg_id in", values, "regId");
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

        public Criteria andBasestationCodeIsNull() {
            addCriterion("basestation_code is null");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeIsNotNull() {
            addCriterion("basestation_code is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeEqualTo(String value) {
            addCriterion("basestation_code =", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeNotEqualTo(String value) {
            addCriterion("basestation_code <>", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeGreaterThan(String value) {
            addCriterion("basestation_code >", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("basestation_code >=", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeLessThan(String value) {
            addCriterion("basestation_code <", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeLessThanOrEqualTo(String value) {
            addCriterion("basestation_code <=", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeLike(String value) {
            addCriterion("basestation_code like", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeNotLike(String value) {
            addCriterion("basestation_code not like", value, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeIn(List<String> values) {
            addCriterion("basestation_code in", values, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeNotIn(List<String> values) {
            addCriterion("basestation_code not in", values, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeBetween(String value1, String value2) {
            addCriterion("basestation_code between", value1, value2, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationCodeNotBetween(String value1, String value2) {
            addCriterion("basestation_code not between", value1, value2, "basestationCode");
            return (Criteria) this;
        }

        public Criteria andBasestationNameIsNull() {
            addCriterion("basestation_name is null");
            return (Criteria) this;
        }

        public Criteria andBasestationNameIsNotNull() {
            addCriterion("basestation_name is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationNameEqualTo(String value) {
            addCriterion("basestation_name =", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameNotEqualTo(String value) {
            addCriterion("basestation_name <>", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameGreaterThan(String value) {
            addCriterion("basestation_name >", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameGreaterThanOrEqualTo(String value) {
            addCriterion("basestation_name >=", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameLessThan(String value) {
            addCriterion("basestation_name <", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameLessThanOrEqualTo(String value) {
            addCriterion("basestation_name <=", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameLike(String value) {
            addCriterion("basestation_name like", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameNotLike(String value) {
            addCriterion("basestation_name not like", value, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameIn(List<String> values) {
            addCriterion("basestation_name in", values, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameNotIn(List<String> values) {
            addCriterion("basestation_name not in", values, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameBetween(String value1, String value2) {
            addCriterion("basestation_name between", value1, value2, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationNameNotBetween(String value1, String value2) {
            addCriterion("basestation_name not between", value1, value2, "basestationName");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryIsNull() {
            addCriterion("basestation_category is null");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryIsNotNull() {
            addCriterion("basestation_category is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryEqualTo(Integer value) {
            addCriterion("basestation_category =", value, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryNotEqualTo(Integer value) {
            addCriterion("basestation_category <>", value, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryGreaterThan(Integer value) {
            addCriterion("basestation_category >", value, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("basestation_category >=", value, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryLessThan(Integer value) {
            addCriterion("basestation_category <", value, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("basestation_category <=", value, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryIn(List<Integer> values) {
            addCriterion("basestation_category in", values, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryNotIn(List<Integer> values) {
            addCriterion("basestation_category not in", values, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryBetween(Integer value1, Integer value2) {
            addCriterion("basestation_category between", value1, value2, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("basestation_category not between", value1, value2, "basestationCategory");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeIsNull() {
            addCriterion("basestation_type is null");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeIsNotNull() {
            addCriterion("basestation_type is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeEqualTo(Integer value) {
            addCriterion("basestation_type =", value, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeNotEqualTo(Integer value) {
            addCriterion("basestation_type <>", value, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeGreaterThan(Integer value) {
            addCriterion("basestation_type >", value, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("basestation_type >=", value, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeLessThan(Integer value) {
            addCriterion("basestation_type <", value, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("basestation_type <=", value, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeIn(List<Integer> values) {
            addCriterion("basestation_type in", values, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeNotIn(List<Integer> values) {
            addCriterion("basestation_type not in", values, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeBetween(Integer value1, Integer value2) {
            addCriterion("basestation_type between", value1, value2, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("basestation_type not between", value1, value2, "basestationType");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateIsNull() {
            addCriterion("basestation_opendate is null");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateIsNotNull() {
            addCriterion("basestation_opendate is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateEqualTo(Date value) {
            addCriterion("basestation_opendate =", value, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateNotEqualTo(Date value) {
            addCriterion("basestation_opendate <>", value, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateGreaterThan(Date value) {
            addCriterion("basestation_opendate >", value, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateGreaterThanOrEqualTo(Date value) {
            addCriterion("basestation_opendate >=", value, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateLessThan(Date value) {
            addCriterion("basestation_opendate <", value, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateLessThanOrEqualTo(Date value) {
            addCriterion("basestation_opendate <=", value, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateIn(List<Date> values) {
            addCriterion("basestation_opendate in", values, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateNotIn(List<Date> values) {
            addCriterion("basestation_opendate not in", values, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateBetween(Date value1, Date value2) {
            addCriterion("basestation_opendate between", value1, value2, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationOpendateNotBetween(Date value1, Date value2) {
            addCriterion("basestation_opendate not between", value1, value2, "basestationOpendate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateIsNull() {
            addCriterion("basestation_stopdate is null");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateIsNotNull() {
            addCriterion("basestation_stopdate is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateEqualTo(Date value) {
            addCriterion("basestation_stopdate =", value, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateNotEqualTo(Date value) {
            addCriterion("basestation_stopdate <>", value, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateGreaterThan(Date value) {
            addCriterion("basestation_stopdate >", value, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateGreaterThanOrEqualTo(Date value) {
            addCriterion("basestation_stopdate >=", value, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateLessThan(Date value) {
            addCriterion("basestation_stopdate <", value, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateLessThanOrEqualTo(Date value) {
            addCriterion("basestation_stopdate <=", value, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateIn(List<Date> values) {
            addCriterion("basestation_stopdate in", values, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateNotIn(List<Date> values) {
            addCriterion("basestation_stopdate not in", values, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateBetween(Date value1, Date value2) {
            addCriterion("basestation_stopdate between", value1, value2, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationStopdateNotBetween(Date value1, Date value2) {
            addCriterion("basestation_stopdate not between", value1, value2, "basestationStopdate");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorIsNull() {
            addCriterion("basestation_vendor is null");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorIsNotNull() {
            addCriterion("basestation_vendor is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorEqualTo(Integer value) {
            addCriterion("basestation_vendor =", value, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorNotEqualTo(Integer value) {
            addCriterion("basestation_vendor <>", value, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorGreaterThan(Integer value) {
            addCriterion("basestation_vendor >", value, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorGreaterThanOrEqualTo(Integer value) {
            addCriterion("basestation_vendor >=", value, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorLessThan(Integer value) {
            addCriterion("basestation_vendor <", value, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorLessThanOrEqualTo(Integer value) {
            addCriterion("basestation_vendor <=", value, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorIn(List<Integer> values) {
            addCriterion("basestation_vendor in", values, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorNotIn(List<Integer> values) {
            addCriterion("basestation_vendor not in", values, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorBetween(Integer value1, Integer value2) {
            addCriterion("basestation_vendor between", value1, value2, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationVendorNotBetween(Integer value1, Integer value2) {
            addCriterion("basestation_vendor not between", value1, value2, "basestationVendor");
            return (Criteria) this;
        }

        public Criteria andBasestationModelIsNull() {
            addCriterion("basestation_model is null");
            return (Criteria) this;
        }

        public Criteria andBasestationModelIsNotNull() {
            addCriterion("basestation_model is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationModelEqualTo(String value) {
            addCriterion("basestation_model =", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelNotEqualTo(String value) {
            addCriterion("basestation_model <>", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelGreaterThan(String value) {
            addCriterion("basestation_model >", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelGreaterThanOrEqualTo(String value) {
            addCriterion("basestation_model >=", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelLessThan(String value) {
            addCriterion("basestation_model <", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelLessThanOrEqualTo(String value) {
            addCriterion("basestation_model <=", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelLike(String value) {
            addCriterion("basestation_model like", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelNotLike(String value) {
            addCriterion("basestation_model not like", value, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelIn(List<String> values) {
            addCriterion("basestation_model in", values, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelNotIn(List<String> values) {
            addCriterion("basestation_model not in", values, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelBetween(String value1, String value2) {
            addCriterion("basestation_model between", value1, value2, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationModelNotBetween(String value1, String value2) {
            addCriterion("basestation_model not between", value1, value2, "basestationModel");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerIsNull() {
            addCriterion("basestation_power is null");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerIsNotNull() {
            addCriterion("basestation_power is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerEqualTo(Long value) {
            addCriterion("basestation_power =", value, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerNotEqualTo(Long value) {
            addCriterion("basestation_power <>", value, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerGreaterThan(Long value) {
            addCriterion("basestation_power >", value, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerGreaterThanOrEqualTo(Long value) {
            addCriterion("basestation_power >=", value, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerLessThan(Long value) {
            addCriterion("basestation_power <", value, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerLessThanOrEqualTo(Long value) {
            addCriterion("basestation_power <=", value, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerIn(List<Long> values) {
            addCriterion("basestation_power in", values, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerNotIn(List<Long> values) {
            addCriterion("basestation_power not in", values, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerBetween(Long value1, Long value2) {
            addCriterion("basestation_power between", value1, value2, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationPowerNotBetween(Long value1, Long value2) {
            addCriterion("basestation_power not between", value1, value2, "basestationPower");
            return (Criteria) this;
        }

        public Criteria andBasestationStateIsNull() {
            addCriterion("basestation_state is null");
            return (Criteria) this;
        }

        public Criteria andBasestationStateIsNotNull() {
            addCriterion("basestation_state is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationStateEqualTo(Integer value) {
            addCriterion("basestation_state =", value, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateNotEqualTo(Integer value) {
            addCriterion("basestation_state <>", value, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateGreaterThan(Integer value) {
            addCriterion("basestation_state >", value, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("basestation_state >=", value, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateLessThan(Integer value) {
            addCriterion("basestation_state <", value, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateLessThanOrEqualTo(Integer value) {
            addCriterion("basestation_state <=", value, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateIn(List<Integer> values) {
            addCriterion("basestation_state in", values, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateNotIn(List<Integer> values) {
            addCriterion("basestation_state not in", values, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateBetween(Integer value1, Integer value2) {
            addCriterion("basestation_state between", value1, value2, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationStateNotBetween(Integer value1, Integer value2) {
            addCriterion("basestation_state not between", value1, value2, "basestationState");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierIsNull() {
            addCriterion("basestation_carrier is null");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierIsNotNull() {
            addCriterion("basestation_carrier is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierEqualTo(Integer value) {
            addCriterion("basestation_carrier =", value, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierNotEqualTo(Integer value) {
            addCriterion("basestation_carrier <>", value, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierGreaterThan(Integer value) {
            addCriterion("basestation_carrier >", value, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierGreaterThanOrEqualTo(Integer value) {
            addCriterion("basestation_carrier >=", value, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierLessThan(Integer value) {
            addCriterion("basestation_carrier <", value, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierLessThanOrEqualTo(Integer value) {
            addCriterion("basestation_carrier <=", value, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierIn(List<Integer> values) {
            addCriterion("basestation_carrier in", values, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierNotIn(List<Integer> values) {
            addCriterion("basestation_carrier not in", values, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierBetween(Integer value1, Integer value2) {
            addCriterion("basestation_carrier between", value1, value2, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCarrierNotBetween(Integer value1, Integer value2) {
            addCriterion("basestation_carrier not between", value1, value2, "basestationCarrier");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeIsNull() {
            addCriterion("basestation_covertype is null");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeIsNotNull() {
            addCriterion("basestation_covertype is not null");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeEqualTo(Integer value) {
            addCriterion("basestation_covertype =", value, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeNotEqualTo(Integer value) {
            addCriterion("basestation_covertype <>", value, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeGreaterThan(Integer value) {
            addCriterion("basestation_covertype >", value, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("basestation_covertype >=", value, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeLessThan(Integer value) {
            addCriterion("basestation_covertype <", value, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeLessThanOrEqualTo(Integer value) {
            addCriterion("basestation_covertype <=", value, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeIn(List<Integer> values) {
            addCriterion("basestation_covertype in", values, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeNotIn(List<Integer> values) {
            addCriterion("basestation_covertype not in", values, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeBetween(Integer value1, Integer value2) {
            addCriterion("basestation_covertype between", value1, value2, "basestationCovertype");
            return (Criteria) this;
        }

        public Criteria andBasestationCovertypeNotBetween(Integer value1, Integer value2) {
            addCriterion("basestation_covertype not between", value1, value2, "basestationCovertype");
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