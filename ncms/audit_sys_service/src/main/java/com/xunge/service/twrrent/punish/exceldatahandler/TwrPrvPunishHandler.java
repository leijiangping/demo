package com.xunge.service.twrrent.punish.exceldatahandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.model.towerrent.checkmanage.TwrPrvCheckIndexFineVO;
import com.xunge.service.system.region.ISysRegionService;

/**
 * 地市扣罚定义数据处理
 * @author jiacy
 * @date 2017年7月10日 下午6:31:09 
 * @version 1.0.0 
 */
public class TwrPrvPunishHandler  extends ExcelDataHandlerDefaultImpl<TwrPrvCheckIndexFineVO> {
	@Autowired
	private ISysRegionService sysRegionService;
	private List<com.xunge.model.system.region.SysRegionVO> addresslist;
	/**
	 * 导入自定义数据处理构造器
	 * @param addresslist 区域集合
	 */
	public TwrPrvPunishHandler(List<com.xunge.model.system.region.SysRegionVO> addresslist) {
		
		this.addresslist=addresslist;
	}

	/**
	 * 导出自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 */
	public Object exportHandler(TwrPrvCheckIndexFineVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(DateDisposeComm.BELONG_CITY)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegId()!=null&&sysRegionVO.getRegId().equals(value.toString())){

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
	public Object importHandler(TwrPrvCheckIndexFineVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(DateDisposeComm.BELONG_CITY)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().trim().equals(value.toString().trim())){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
