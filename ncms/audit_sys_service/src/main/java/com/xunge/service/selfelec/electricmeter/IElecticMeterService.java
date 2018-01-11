package com.xunge.service.selfelec.electricmeter;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xunge.model.selfelec.electricmeter.DatElectricMeterVO;

public interface IElecticMeterService {
	
	/**
	 * 获取电表信息
	 * @return
	 */
	public PageInfo<DatElectricMeterVO> findElectricMeter(List<String> regIds,String userId,String prvid,String pregid,String regid,String meterCode,String meterState,String meterType,String relateResState,int pageNumber, int pageSize);
	
	/**
	 * 导出电表信息
	 * @return
	 */
	public String exportElectricMeter(List<String> regIds,String userId,String prvid,String path,String pregid,String regid,String meterCode,String meterState,String meterType,String relateResState);
	
	/**
	 * 导入电表信息
	 * @param prvId
	 * @param file
	 * @return
	 */
	public Object[] importElectricMeter(String prvId,MultipartFile file) ;
	
	/**
	 * 添加电表
	 * @param datElectricMeterVO
	 * @return
	 */
	public boolean addDatElectricMeter(DatElectricMeterVO datElectricMeterVO);
	
	/**
	 * 编辑电表
	 * @param datElectricMeterVO
	 * @return
	 */
	public boolean editDatElectricMeter(DatElectricMeterVO datElectricMeterVO);
	
	/**
	 * 删除电表
	 * @param ids
	 * @return
	 */
	public boolean delDatElectricMeter(String[] ids);
	
	/**
	 * 判断是否存在电表编码的电表
	 * @param meterCode
	 * @return
	 */
	public boolean isExistMeterCode(String meterCode);
	
    public DatElectricMeterVO queryElecMeterId(String elecMeterId);
    /**
     * 判断电表是否被关联
     * @param map
     * @return
     */
    public DatElectricMeterVO queryMeterByMeterId(Map<String,Object> map);
    /**
     * 全省电表数
     * @param map
     * @return
     */
    public List<Map<String, String>> queryAllMeterNumByPrvid(Map<String,Object> map);
    /**
     * 全省电表数
     * @param map
     * @return
     */
    public Map<String,Object> queryNolinkMeter(Map<String,Object> map);
    
    /**
	 * @description 修改电表状态
	 * @author yuefy
	 * @date 创建时间：2017年10月18日
	 */
	boolean updateMeterState(Map<String,Object> map);
}
