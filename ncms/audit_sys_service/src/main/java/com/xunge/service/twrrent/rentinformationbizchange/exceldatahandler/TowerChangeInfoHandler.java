package com.xunge.service.twrrent.rentinformationbizchange.exceldatahandler;

import java.util.List;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;

/**
 * 塔维变更信息自定义数据处理
 * @author yuefy
 * @date 2017年7月18日 上午10:31:09 
 */
public class TowerChangeInfoHandler  extends ExcelDataHandlerDefaultImpl<TowerRentinformationBizchangeVO> {

	private List<com.xunge.model.system.region.SysRegionVO> addresslist;
	/**
	 * 塔维资源信息自定义数据处理构造器
	 * @param addresslist 区域集合
	 */
	public TowerChangeInfoHandler(List<com.xunge.model.system.region.SysRegionVO> addresslist) {
		
		this.addresslist=addresslist;
	}

	/**
	 * 导出自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 */
	public Object exportHandler(TowerRentinformationBizchangeVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(PromptMessageComm.CARRIERS_CTIY)||name.equals(PromptMessageComm.NEED_CITY)||name.equals(PromptMessageComm.SITE_CITY)||name.equals(DateDisposeComm.REGION)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegId()!=null&&sysRegionVO.getRegId().equals(value)){
					return sysRegionVO.getRegName();
				}
			}
		}
		return super.exportHandler(obj, name, value);
	};
	
	/**
	 * 导入自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 */
	
	public Object importHandler(TowerRentinformationBizchangeVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(PromptMessageComm.CARRIERS_CTIY)||name.equals(PromptMessageComm.NEED_CITY)||name.equals(PromptMessageComm.SITE_CITY)||name.equals(DateDisposeComm.REGION)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().equals(value)){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
