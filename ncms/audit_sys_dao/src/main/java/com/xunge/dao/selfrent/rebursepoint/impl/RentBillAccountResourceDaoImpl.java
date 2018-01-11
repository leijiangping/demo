package com.xunge.dao.selfrent.rebursepoint.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.rebursepoint.IRentBillAccountResourceDao;
import com.xunge.model.selfrent.billAccount.RentBillAccountResourceVO;

public class RentBillAccountResourceDaoImpl extends AbstractBaseDao implements
		IRentBillAccountResourceDao {
	final String RentBillAccountResourceNamespace = "com.xunge.mapping.RentBillAccountResourceVOMapper.";//报账点表
	@Override
	public void insertBillAccountResource(
			List<RentBillAccountResourceVO> rentResourceList) {
		this.getSqlSession().insert(RentBillAccountResourceNamespace+"insertBillAccountResource", rentResourceList);
	}
	@Override
	public void updateBillAccountResource(Map<String, Object> paramMap) {
		this.getSqlSession().update(RentBillAccountResourceNamespace+"updateBillAccountResource", paramMap);
		}
	@Override
	public void deleteBillAccountResource(String billAccountId) {
		this.getSqlSession().delete(RentBillAccountResourceNamespace+"deleteBillAccountResource", billAccountId);
	}
	@Override
	public List<RentBillAccountResourceVO> queryResourceBindBillacc(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(RentBillAccountResourceNamespace+"queryResourceBindBillacc",paraMap);
	}
	@Override
	public int deleteResourcePoint(String baseresourceId) {
		return this.getSqlSession().delete(RentBillAccountResourceNamespace+"deleteResourcePoint", baseresourceId);
	}
}
