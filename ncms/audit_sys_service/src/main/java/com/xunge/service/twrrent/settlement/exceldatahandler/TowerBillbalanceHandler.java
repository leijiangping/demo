package com.xunge.service.twrrent.settlement.exceldatahandler;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.google.common.collect.Maps;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.region.impl.SysRegionServiceImpl;

/**
 * @description 塔维租赁账单自定义数据处理
 * @author zhujj
 * @date 2017年7月10日 下午6:31:09 
 * @version 1.0.0 
 */
public class TowerBillbalanceHandler  extends ExcelDataHandlerDefaultImpl<TowerBillbalanceVO> {
	@Autowired
	private ISysRegionService sysRegionService;
	private List<com.xunge.model.system.region.SysRegionVO> addresslist;
	/**
	 * @description 塔维租赁账单自定义数据处理构造器
	 * @param addresslist 区域集合
	 * @author zhujj
	 */
	public TowerBillbalanceHandler(List<com.xunge.model.system.region.SysRegionVO> addresslist) {
		
		this.addresslist=addresslist;
	}

	/**
	 * @description 导出自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 * @author zhujj
	 */
	public Object exportHandler(TowerBillbalanceVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(PromptMessageComm.CARRIERS_REGION)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegId()!=null&&sysRegionVO.getRegId().equals(value.toString())){

					return sysRegionVO.getRegName();
				}
			}
		}
		return super.exportHandler(obj, name, value);
	};
	
	/**
	 * @description 导入自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 * @author zhujj
	 */
	public Object importHandler(TowerBillbalanceVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(PromptMessageComm.CARRIERS_REGION)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().trim().equals(value.toString().trim())){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
