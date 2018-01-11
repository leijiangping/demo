package com.xunge.dao.selfrent.rebursepoint.impl;

import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.rebursepoint.IRentBillAccountContractDao;
import com.xunge.model.selfrent.rebursepoint.RentBillAccountContractVO;

public class RentBillAccountContractDaoImpl extends AbstractBaseDao implements
		IRentBillAccountContractDao {
	final String RentBillAccountContractNamespace = "com.xunge.mapping.RentBillAccountContractVOMapper.";//报账点表
	@Override
	public void insertBillAccountContract(
			RentBillAccountContractVO rentBillAccountContract) {
		this.getSqlSession().insert(RentBillAccountContractNamespace+"insertBillAccountContract", rentBillAccountContract);
	}
	@Override
	public int updateBillAccountContract(Map<String, Object> paraMap) {
		return this.getSqlSession().update(RentBillAccountContractNamespace+"updateBillAccountContract", paraMap);
		}
	@Override
	public void deleteBillAccountContract(String billAccountContractId) {
		this.getSqlSession().delete(RentBillAccountContractNamespace+"deleteBillAccountContract", billAccountContractId);
	}
	@Override
	public RentBillAccountContractVO queryContractBindBillacc(
			String rentcontractId) {
		return this.getSqlSession().selectOne(RentBillAccountContractNamespace+"queryContractBindBillacc",rentcontractId);
	}
}
