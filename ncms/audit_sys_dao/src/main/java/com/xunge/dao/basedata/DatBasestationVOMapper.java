package com.xunge.dao.basedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatBasestationVOExample;
import com.xunge.model.basedata.DatDevicefactoryVO;

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

	/**
	 * @description 查询设备制造商 
	 * @author yuefy
	 * @date 创建时间：2017年9月20日
	 */
	List<DatDevicefactoryVO> selectDivice(Map<String, Object> map);
	/**
	 * 根据资源id集合查询设备信息
	 * @param map
	 * @return
	 */
	List<DatBasestationVO>queryBasestationById(Map<String, Object> map);
}