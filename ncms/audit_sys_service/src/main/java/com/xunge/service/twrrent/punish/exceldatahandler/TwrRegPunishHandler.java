package com.xunge.service.twrrent.punish.exceldatahandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;
import com.xunge.service.system.region.ISysRegionService;

/**
 * @description 地市扣罚自定义数据处理
 * @author zhujj
 * @date 2017-7-10 6:31:09 
 * @version 1.0.0 
 */
public class TwrRegPunishHandler  extends ExcelDataHandlerDefaultImpl<TwrRegPunishVO> {
	@Autowired
	private ISysRegionService sysRegionService;
	private List<com.xunge.model.system.region.SysRegionVO> addresslist;
	/**
	 * @description 导入自定义数据处理构造器
	 * @param addresslist 区域集合
	 * @author zhujj
	 * @date 2017-7-10 6:31:09 
	 */
	public TwrRegPunishHandler(List<com.xunge.model.system.region.SysRegionVO> addresslist) {
		
		this.addresslist=addresslist;
	}

	/**
	 * @description 导出自定义数据处理对象
	 * @param obj 实体类
	 * @param name 当前字段名称
	 * @param value 当前字段值
	 * @return
	 * @author zhujj
	 * @date 2017-7-10 6:31:09 
	 */
	public Object exportHandler(TwrRegPunishVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(DateDisposeComm.BELONG_REGION)){
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
	 * @author zhujj
	 * @date 2017-7-10 6:31:09 
	 * @return
	 */
	public Object importHandler(TwrRegPunishVO obj, String name, Object value) {
		//判断列表名并且判断值
		if(name.equals(DateDisposeComm.BELONG_REGION)){
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : addresslist) {
				if(sysRegionVO.getRegName()!=null&&sysRegionVO.getRegName().trim().equals(value.toString().trim())){
					return sysRegionVO.getRegId();
				}
			}
		}
		return super.importHandler(obj, name, value);
	};
	
}
