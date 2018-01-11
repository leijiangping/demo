package com.xunge.model.selfelec;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EleContractbillaccount {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.elebillaccountcontract_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    private String elebillaccountcontractId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.elecontract_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    private String elecontractId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.billaccount_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    private String billaccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.relation_state
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    private Integer relationState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.relation_startdate
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    private Date relationStartdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.relation_enddate
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    private Date relationEnddate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.elebillaccountcontract_id
     *
     * @return the value of ele_contractbillaccount.elebillaccountcontract_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public String getElebillaccountcontractId() {
        return elebillaccountcontractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ele_contractbillaccount.elebillaccountcontract_id
     *
     * @param elebillaccountcontractId the value for ele_contractbillaccount.elebillaccountcontract_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public void setElebillaccountcontractId(String elebillaccountcontractId) {
        this.elebillaccountcontractId = elebillaccountcontractId == null ? null : elebillaccountcontractId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.elecontract_id
     *
     * @return the value of ele_contractbillaccount.elecontract_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public String getElecontractId() {
        return elecontractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ele_contractbillaccount.elecontract_id
     *
     * @param elecontractId the value for ele_contractbillaccount.elecontract_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public void setElecontractId(String elecontractId) {
        this.elecontractId = elecontractId == null ? null : elecontractId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.billaccount_id
     *
     * @return the value of ele_contractbillaccount.billaccount_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public String getBillaccountId() {
        return billaccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ele_contractbillaccount.billaccount_id
     *
     * @param billaccountId the value for ele_contractbillaccount.billaccount_id
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public void setBillaccountId(String billaccountId) {
        this.billaccountId = billaccountId == null ? null : billaccountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.relation_state
     *
     * @return the value of ele_contractbillaccount.relation_state
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public Integer getRelationState() {
        return relationState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ele_contractbillaccount.relation_state
     *
     * @param relationState the value for ele_contractbillaccount.relation_state
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public void setRelationState(Integer relationState) {
        this.relationState = relationState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.relation_startdate
     *
     * @return the value of ele_contractbillaccount.relation_startdate
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getRelationStartdate() {
        return relationStartdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ele_contractbillaccount.relation_startdate
     *
     * @param relationStartdate the value for ele_contractbillaccount.relation_startdate
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public void setRelationStartdate(Date relationStartdate) {
        this.relationStartdate = relationStartdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.relation_enddate
     *
     * @return the value of ele_contractbillaccount.relation_enddate
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getRelationEnddate() {
        return relationEnddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ele_contractbillaccount.relation_enddate
     *
     * @param relationEnddate the value for ele_contractbillaccount.relation_enddate
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    public void setRelationEnddate(Date relationEnddate) {
        this.relationEnddate = relationEnddate;
    }
}