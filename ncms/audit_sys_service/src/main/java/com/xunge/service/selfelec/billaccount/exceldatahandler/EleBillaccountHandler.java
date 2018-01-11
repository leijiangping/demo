package com.xunge.service.selfelec.billaccount.exceldatahandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.model.selfelec.VEleBillaccount;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.system.region.ISysRegionService;

/**
 * @description 报账点信息自定义数据处理
 * @author wangz
 * @date 2017-08-24 09:03:30
 * @version 1.0.0 
 */
public class EleBillaccountHandler  extends ExcelDataHandlerDefaultImpl<VEleBillaccount> {
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	private List<SysRegionVO> addresslist;
	/**
	 * @description  报账点信息自定义数据处理构造器
	 * @param addresslist 区域集合
	 * @author zhujj
	 */
	public EleBillaccountHandler(List<SysRegionVO> addresslist) {
		
		this.addresslist = addresslist;
	}
	
	/**
	 * @description 导入自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 * @author wangz
	 */
	public Object importHandler(VEleBillaccount obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(DateDisposeComm.BELONG_CITY)){
			for (SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getPregName() != null && sysRegionVO.getPregName().trim().equals(value.toString().trim())){
					return sysRegionVO.getPregId();
				}
			}
		}
		if(name.equals(DateDisposeComm.BELONG_REGION)){
			for (SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().trim().equals(value.toString().trim())){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
