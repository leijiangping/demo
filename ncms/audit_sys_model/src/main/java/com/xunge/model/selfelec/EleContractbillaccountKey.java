package com.xunge.model.selfelec;

public class EleContractbillaccountKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.billaccount_id
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    private String billaccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ele_contractbillaccount.elecontract_id
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    private String elecontractId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.billaccount_id
     *
     * @return the value of ele_contractbillaccount.billaccount_id
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
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
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public void setBillaccountId(String billaccountId) {
        this.billaccountId = billaccountId == null ? null : billaccountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ele_contractbillaccount.elecontract_id
     *
     * @return the value of ele_contractbillaccount.elecontract_id
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
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
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    public void setElecontractId(String elecontractId) {
        this.elecontractId = elecontractId == null ? null : elecontractId.trim();
    }
}