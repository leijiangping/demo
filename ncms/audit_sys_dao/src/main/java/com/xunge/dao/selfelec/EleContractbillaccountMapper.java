package com.xunge.dao.selfelec;

import com.xunge.model.selfelec.EleContractbillaccount;
import com.xunge.model.selfelec.EleContractbillaccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EleContractbillaccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int countByExample(EleContractbillaccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int deleteByExample(EleContractbillaccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int deleteByPrimaryKey(String elebillaccountcontractId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int insert(EleContractbillaccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int insertSelective(EleContractbillaccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    List<EleContractbillaccount> selectByExample(EleContractbillaccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    EleContractbillaccount selectByPrimaryKey(String elebillaccountcontractId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int updateByExampleSelective(@Param("record") EleContractbillaccount record, @Param("example") EleContractbillaccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int updateByExample(@Param("record") EleContractbillaccount record, @Param("example") EleContractbillaccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int updateByPrimaryKeySelective(EleContractbillaccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ele_contractbillaccount
     *
     * @mbggenerated Sun Jul 23 12:57:37 CST 2017
     */
    int updateByPrimaryKey(EleContractbillaccount record);
}