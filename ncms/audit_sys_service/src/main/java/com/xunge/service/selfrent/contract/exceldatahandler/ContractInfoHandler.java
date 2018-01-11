package com.xunge.service.selfrent.contract.exceldatahandler;

import java.util.List;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.model.selfrent.contract.RentContractVO;

/**
 * 租费合同信息自定义数据处理
 * @author yuefy
 * @date 2017年7月18日 上午10:31:09 
 */
public class ContractInfoHandler  extends ExcelDataHandlerDefaultImpl<RentContractVO> {

	private List<com.xunge.model.system.region.SysRegionVO> addresslist;
	/**
	 * 塔维资源信息自定义数据处理构造器
	 * @param addresslist 区域集合
	 */
	public ContractInfoHandler(List<com.xunge.model.system.region.SysRegionVO> addresslist) {
		
		this.addresslist=addresslist;
	}

	/**
	 * 导出自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 */
	public Object exportHandler(RentContractVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(DateDisposeComm.BELONG_REGION.equals(name)){
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
	
	public Object importHandler(RentContractVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(DateDisposeComm.BELONG_REGION.equals(name)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().equals(value)){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
