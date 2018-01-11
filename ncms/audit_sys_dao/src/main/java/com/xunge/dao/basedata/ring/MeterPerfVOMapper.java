package com.xunge.dao.basedata.ring;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.MeterPerfVOExample;

public interface MeterPerfVOMapper {
	
    int countByExample(MeterPerfVOExample example);

    int deleteByExample(MeterPerfVOExample example);

    int deleteByPrimaryKey(String meterId);

    int insert(MeterPerfVO record);

    int insertSelective(MeterPerfVO record);

    List<MeterPerfVO> selectByExample(MeterPerfVOExample example);

    MeterPerfVO selectByPrimaryKey(String meterId);

    int updateByExampleSelective(@Param("record") MeterPerfVO record, @Param("example") MeterPerfVOExample example);

    int updateByExample(@Param("record") MeterPerfVO record, @Param("example") MeterPerfVOExample example);

    int updateByPrimaryKeySelective(MeterPerfVO record);

    int updateByPrimaryKey(MeterPerfVO record);
    
    boolean batchInsert(List<MeterPerfVO> datas);
    
	boolean delByCuidsAndPrvid(Map<String,Object> map);
	
	boolean batchUpdate(List<MeterPerfVO> datas);
	
	List<MeterPerfVO> selectByCondition(MeterPerfVO mseterPerfVO);
}