package com.xunge.service.twrrent.resourceinfo.exceldatahandler;

import java.util.List;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.tower.resourceinfo.TowerShareTypeComm;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;

/**
 * 塔维资源信息自定义数据处理
 * @author yuefy
 * @date 2017年7月14日 下午6:31:09 
 */
public class TowerResourceInfoHandler  extends ExcelDataHandlerDefaultImpl<TowerResourceInfoVO> {

	private List<SysRegionVO> addresslist;
	/**
	 * 塔维资源信息自定义数据处理构造器
	 * @param addresslist 区域集合
	 */
	public TowerResourceInfoHandler(List<SysRegionVO> addresslist) {
		this.addresslist=addresslist;
	}

	/**
	 * 导出自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 */
	public Object exportHandler(TowerResourceInfoVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(DateDisposeComm.REGION)){
			for (SysRegionVO sysRegionVO : addresslist) {
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
	
	public Object importHandler(TowerResourceInfoVO obj, String name, Object value) {
		//判断列表名并且判断值
		// 区县
		
		if((DateDisposeComm.REGION).equals(name)){
			for (SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().equals(value)){
					return sysRegionVO.getRegId();
				}
			}
		}
		// 共享信息
		if((DateDisposeComm.SHARE_INFO).equals(name)){
			String str="";
			if(value != null){
				str = (String)value;
			}
			if(TowerShareTypeComm.PARTY_FORMER_PROPERTY_RIGHT.equals(str)){
				return TowerShareTypeComm.PARTY_FORMER_PROPERTY_RIGHT_int;
			}else if(TowerShareTypeComm.BOTH_SHARED.equals(str)){
				return TowerShareTypeComm.BOTH_SHARED_int;
			}else if(TowerShareTypeComm.STOCK_CHANGE.equals(str)){
				return TowerShareTypeComm.STOCK_CHANGE_int;
			}else if(TowerShareTypeComm.STOCK_REFORM.equals(str)){
				return TowerShareTypeComm.STOCK_REFORM_int;
			}else if(TowerShareTypeComm.NEW_TOWER.equals(str)) {
				return TowerShareTypeComm.NEW_TOWER_int;
			}else{
				return "";
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
