package com.xunge.dao.selfelec;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBillaccountcontract;

public interface VDatElectricmeterExpMapper {
	/**
	 * 根据报账点id 获取 电表列表
	 * @param billaccountId
	 * @return
	 */
    List<VDatElectricmeter> getVDatElectricmeterBybillaccountId(String billaccountId);
    
    /**
	 * 根据报账点id 获取 电表列表
	 * @param billaccountId
	 * @return
	 */
    List<VDatElectricmeter> getVDatElectricmeterBybillaccountIdShow(String billaccountId);
    
    /**
     * 根据报账点获取合同信息
     * @param billaccountId
     * @return
     */
    List<VEleBillaccountcontract> getContractBybillaccountId(String billaccountId);
    
    /**
     * 计算标杆信息
     * @param param
     * @return
     */
    void getBenchmark(Map<String,Object> param);
}