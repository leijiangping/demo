package com.xunge.model.towerrent.stopserver;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
/**
 * @description 铁塔终止服务期始 期终 导入 导出时间数据
 * @author yuefy
 * @date 创建时间：2017年8月28日
 */
public class TowerStopServerDateVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2077478092218497512L;

    //起始时间
    @Excel(name="起始时间",orderNum="13",format="yyyy-MM-dd")
    private Date startDate;
    
    //截止时间
    @Excel(name="截止时间",orderNum="14",format="yyyy-MM-dd")
    private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
    
}