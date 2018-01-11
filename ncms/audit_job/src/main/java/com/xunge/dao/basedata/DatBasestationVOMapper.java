package com.xunge.dao.basedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatBasestationVOExample;

public interface DatBasestationVOMapper {
    int countByExample(DatBasestationVOExample example);

    int deleteByExample(DatBasestationVOExample example);

    int deleteByPrimaryKey(String basestationId);

    int insert(DatBasestationVO record);

    int insertSelective(DatBasestationVO record);

    List<DatBasestationVO> selectByExample(DatBasestationVOExample example);

    DatBasestationVO selectByPrimaryKey(String basestationId);
    /**
     * 根据资源点id查询基站信息
     * @param baseresourceId
     * @return
     * @author xup
     */
    List<DatBasestationVO> queryById(String baseresourceId);
    
    int updateByExampleSelective(@Param("record") DatBasestationVO record, @Param("example") DatBasestationVOExample example);

    int updateByExample(@Param("record") DatBasestationVO record, @Param("example") DatBasestationVOExample example);

    int updateByPrimaryKeySelective(DatBasestationVO record);

    int updateByPrimaryKey(DatBasestationVO record);
    
    boolean batchInsert(List<DatBasestationVO> datas);
    
	boolean delByCuidsAndPrvid(Map<String,Object> map);
	
	boolean batchUpdate(List<DatBasestationVO> datas);
}