package com.xunge.dao.basedata.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.basedata.SysRegionVOExample;

public interface SysRegionVOMapper {
    int countByExample(SysRegionVOExample example);

    int deleteByExample(SysRegionVOExample example);

    int deleteByPrimaryKey(String regId);

    int insert(SysRegionVO record);

    int insertSelective(SysRegionVO record);

    List<SysRegionVO> selectByExample(SysRegionVOExample example);

    SysRegionVO selectByPrimaryKey(String regId);

    int updateByExampleSelective(@Param("record") SysRegionVO record, @Param("example") SysRegionVOExample example);

    int updateByExample(@Param("record") SysRegionVO record, @Param("example") SysRegionVOExample example);

    int updateByPrimaryKeySelective(SysRegionVO record);

    int updateByPrimaryKey(SysRegionVO record);
    
    boolean batchInsert(List<SysRegionVO> datas);
    
	boolean deByCuids(String[] idArray);
	
	boolean batchUpdate(List<SysRegionVO> datas);
	
	List<SysRegionVO> getAddress(SysRegionVO record);
	
}