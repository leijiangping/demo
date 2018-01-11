package com.xunge.dao.selfelec;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfelec.VDatBaseresource;
import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBillaccountbaseresource;
import com.xunge.model.selfelec.VEleBillaccountcontract;
import com.xunge.model.selfelec.VEleContract;

public interface EleBillaccountExpMapper {
	List<VEleContract> selectByContract(VEleBillaccountcontract contract);
	
	List<VDatBaseresource> selectByResource(VEleBillaccountbaseresource resource);
	
	List<VDatElectricmeter> selectByElectricmeter(VEleBillaccountbaseresource resource);
	
	List<Map<String , Object>> selectResourceRelations(String billaccountId);
	
	List<Map<String , Object>> selectResourceTree(VEleBillaccountbaseresource resource);
	
	/**
	 * 修改自维缴费记录汇总id
	 * @param map 
	 * @param map: paymentId 缴费记录id
	 * @param map: billamountId 汇总id
	 * @return
	 */
	public int updateBillamountIdByPaymentId(Map<String, Object> hashMaps);
}