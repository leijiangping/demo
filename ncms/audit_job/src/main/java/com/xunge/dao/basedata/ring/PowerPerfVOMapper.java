package com.xunge.dao.basedata.ring;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVOExample;

public interface PowerPerfVOMapper {
	
    int countByExample(PowerPerfVOExample example);

    int deleteByExample(PowerPerfVOExample example);

    int deleteByPrimaryKey(String powerId);

    int insert(PowerPerfVO record);

    int insertSelective(PowerPerfVO record);

    List<PowerPerfVO> selectByExample(PowerPerfVOExample example);

    PowerPerfVO selectByPrimaryKey(String powerId);

    int updateByExampleSelective(@Param("record") PowerPerfVO record, @Param("example") PowerPerfVOExample example);

    int updateByExample(@Param("record") PowerPerfVO record, @Param("example") PowerPerfVOExample example);

    int updateByPrimaryKeySelective(PowerPerfVO record);

    int updateByPrimaryKey(PowerPerfVO record);
    
    boolean batchInsert(List<PowerPerfVO> datas);
    
	boolean delByCuidsAndPrvid(Map<String,Object> map);
	
	boolean batchUpdate(List<PowerPerfVO> datas);
	
	List<PowerPerfVO> selectByCondition(PowerPerfVO powerPerfVO);
	List<PowerPerfVO> selectForRedis(String prvID);
}