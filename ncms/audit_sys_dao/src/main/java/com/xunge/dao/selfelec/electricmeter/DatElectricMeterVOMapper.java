package com.xunge.dao.selfelec.electricmeter;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.selfelec.electricmeter.DatElectricMeterVO;
import com.xunge.model.selfelec.electricmeter.DatElectricMeterVOExample;

public interface DatElectricMeterVOMapper {
    int countByExample(DatElectricMeterVOExample example);

    boolean deleteByExample(DatElectricMeterVOExample example);

    boolean deleteByPrimaryKey(String meterId);

    boolean insert(DatElectricMeterVO record);

    boolean insertSelective(DatElectricMeterVO record);

    List<DatElectricMeterVO> selectByExample(DatElectricMeterVOExample example);

    DatElectricMeterVO selectByPrimaryKey(String meterId);

    boolean updateByExampleSelective(@Param("record") DatElectricMeterVO record, @Param("example") DatElectricMeterVOExample example);

    boolean updateByExample(@Param("record") DatElectricMeterVO record, @Param("example") DatElectricMeterVOExample example);

    boolean updateByPrimaryKeySelective(DatElectricMeterVO record);

    boolean updateByPrimaryKey(DatElectricMeterVO record);
    
	List<DatElectricMeterVO> selectByCondition(DatElectricMeterVO datElectricMeterVO);
	
	boolean delDatElectricMeter(String[] ids);
	
	List<DatElectricMeterVO> selectByMeterCode(String meterCode);
	
	DatElectricMeterVO queryElecMeterId(String meterId);
	
	List<DatElectricMeterVO> queryMeterByResourceId(Map<String,Object> map);
	
	DatElectricMeterVO queryMeterByMeterId(Map<String,Object> map);
	
	List<Map<String, String>> queryMeterNumByPrvid(Map<String,Object> map);
	
	int selectLinkedElecMeter(Map<String,Object> map);
	int selectAllElecMeter(Map<String,Object> map);
	
	/**
	 * @description 根据meterCode以及省份编码查询电表
	 * @author yuefy
	 * @date 创建时间：2017年10月17日
	 */
	List<DatElectricMeterVO> selectByMeterCodeMap(Map<String,Object> map);
	
	/**
	 * @description 修改电表状态
	 * @author yuefy
	 * @date 创建时间：2017年10月18日
	 */
	boolean updateMeterState(Map<String,Object> map);
}