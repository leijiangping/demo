package com.xunge.service.towerrent.excelhandler;

import java.util.List;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.towerrent.TowerStopServerVO;


/**
 * @description 自定义数据处理
 * @author wangz
 * @date 2017-08-24 09:03:30
 * @version 1.0.0 
 */
public class TowerRentStopServerHandler  extends ExcelDataHandlerDefaultImpl<TowerStopServerVO> {
	
	private List<SysRegionVO> addresslist;
	/**
	 * @description  报账点信息自定义数据处理构造器
	 * @param addresslist 区域集合
	 * @author zhujj
	 */
	public TowerRentStopServerHandler(List<SysRegionVO> addresslist) {
		
		this.addresslist=addresslist;
	}
	
	/**
	 * @description 导入自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 * @author wangz
	 */
	public Object importHandler(TowerStopServerVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals("区县")){
			for (SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getTwrRegName() != null && sysRegionVO.getTwrRegName().trim().equals(value.toString().trim())){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
