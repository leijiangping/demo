package com.xunge.dao.selfelec;

import com.xunge.model.selfelec.VEleBillaccountbaseresource;
import com.xunge.model.selfelec.VEleBillaccountbaseresourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VEleBillaccountbaseresourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    int countByExample(VEleBillaccountbaseresourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    int deleteByExample(VEleBillaccountbaseresourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    int insert(VEleBillaccountbaseresource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    int insertSelective(VEleBillaccountbaseresource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    List<VEleBillaccountbaseresource> selectByExample(VEleBillaccountbaseresourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    int updateByExampleSelective(@Param("record") VEleBillaccountbaseresource record, @Param("example") VEleBillaccountbaseresourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_ele_billaccountbaseresource
     *
     * @mbggenerated Sun Jul 02 03:52:13 CST 2017
     */
    int updateByExample(@Param("record") VEleBillaccountbaseresource record, @Param("example") VEleBillaccountbaseresourceExample example);
}