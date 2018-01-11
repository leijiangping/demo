package com.xunge.service.basedata.device;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatDevicefactoryVO;
import com.xunge.model.basedata.vo.DeviceQueryVO;

public interface IDeviceService {

	PageInfo<DatBasestationVO> getAll(DeviceQueryVO vo);
	
	DatBasestationVO get(String id);
	
	int insert(DatBasestationVO record);
		
	int update(DatBasestationVO record);
		
	int delete(String id);
	
	int deleteBatch(List<DatBasestationVO> list);
	
	String export(DeviceQueryVO vo, String path);

	/**
	 * @description 查询设备制造商 
	 * @author yuefy
	 * @date 创建时间：2017年9月20日
	 */
	public List<DatDevicefactoryVO> selectDevice(Map<String,Object> map);
}
