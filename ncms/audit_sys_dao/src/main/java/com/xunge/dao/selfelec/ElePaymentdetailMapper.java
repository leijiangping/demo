package com.xunge.dao.selfelec;

import com.xunge.model.selfelec.ElePaymentdetail;
import com.xunge.model.selfelec.ElePaymentdetailExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ElePaymentdetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int countByExample(ElePaymentdetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int deleteByExample(ElePaymentdetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int deleteByPrimaryKey(String paymentdetailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int insert(ElePaymentdetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int insertSelective(ElePaymentdetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    List<ElePaymentdetail> selectByExample(ElePaymentdetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    ElePaymentdetail selectByPrimaryKey(String paymentdetailId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int updateByExampleSelective(@Param("record") ElePaymentdetail record, @Param("example") ElePaymentdetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int updateByExample(@Param("record") ElePaymentdetail record, @Param("example") ElePaymentdetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int updateByPrimaryKeySelective(ElePaymentdetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_paymentdetail
     *
     * @mbggenerated Sun Jul 23 05:05:58 CST 2017
     */
    int updateByPrimaryKey(ElePaymentdetail record);
    /**
	 * 根据报账点缴费编码查询报账点信息
	 * @param billaccountpaymentdetailId
	 * @return
	 *//*
	public ElePaymentdetail queryPaymentDetailInfoById(String billaccountpaymentdetailId);*/
}