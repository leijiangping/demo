package com.xunge.dao.selfelec;

import com.xunge.model.selfelec.VDatBasesite;
import com.xunge.model.selfelec.VDatBasesiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VDatBasesiteMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    int countByExample(VDatBasesiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    int deleteByExample(VDatBasesiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    int insert(VDatBasesite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    int insertSelective(VDatBasesite record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    List<VDatBasesite> selectByExample(VDatBasesiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    int updateByExampleSelective(@Param("record") VDatBasesite record, @Param("example") VDatBasesiteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table v_dat_basesite
     *
     * @mbggenerated Sun Jul 02 14:57:09 CST 2017
     */
    int updateByExample(@Param("record") VDatBasesite record, @Param("example") VDatBasesiteExample example);
}