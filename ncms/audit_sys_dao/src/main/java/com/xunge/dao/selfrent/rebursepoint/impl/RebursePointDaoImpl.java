package com.xunge.dao.selfrent.rebursepoint.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.rebursepoint.IRebursePointDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.model.selfrent.resource.DatBaseResourceVO;

/**
 * 报账点DAO
 * @author lpw
 *
 */
@SuppressWarnings("unchecked")
public class RebursePointDaoImpl extends AbstractBaseDao implements IRebursePointDao {
	
	final String RebursePointNamespace = "com.xunge.mapping.RebursePointVOMapper.";//报账点表
	//资源表
	final String DatResourceNamespace = "com.xunge.mapping.DatResourceVOMapper.";
	//主合同表
	final String DatContractNamespace = "com.xunge.mapping.DatContractVOMapper.";
	//房屋租赁合同表
	final String RentContractNamespace = "com.xunge.mapping.RentContractVOMapper.";
	@Override                           
	public Page<List<RentBillaccountVO>> queryRembursePointInfo(
			Map<String, Object> pMap, int pageNumber, int pageSize){
		
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(RebursePointNamespace+"queryRembursePointInfo",pMap);
		return PageInterceptor.endPage();
	}
	@Override
	public Page<List<RentContractVO>> queryContractAgreement(Map<String, Object> pMap,
			int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(RentContractNamespace+"queryContractAgreement",pMap);
		return PageInterceptor.endPage(); 
	}
	@Override
	public Page<List<DatBaseResourceVO>> queryResourceInfo(Map<String, Object> pMap, int pageNumber,
			int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(DatResourceNamespace+"queryResourceInfo",pMap);
		return PageInterceptor.endPage(); 
	}
	@Override
	public void insertBillAcount(RentBillaccountVO rentBillaccount) {
		
		this.getSqlSession().insert(RebursePointNamespace+"insertBillAcount", rentBillaccount);
	}
	@Override
	public void updateBillAcount(RentBillaccountVO billaccount) {
		this.getSqlSession().update(RebursePointNamespace+"updateBillAcount", billaccount);
	}
	@Override
	public void deleteBillAcount(String billAccountId) {
		this.getSqlSession().delete(RebursePointNamespace+"deleteBillAcount", billAccountId);
		}
	@Override
	public List<DatBaseResourceVO> queryContractByResourceId(
			Map<String, Object> paraMap) {		
		return this.getSqlSession().selectList(DatResourceNamespace+"queryContractByResourceId",paraMap);
	}
	@Override
	public RentContractVO queryContractById(Map<String, Object> paraMap) {
		
		return this.getSqlSession().selectOne(RentContractNamespace+"queryContractById",paraMap);
	}
	@Override
	public List<DatBaseResourceVO> queryResource(Map<String, Object> pMap) {
		return this.getSqlSession().selectList(DatResourceNamespace+"queryResource",pMap);
	}
	@Override
	public RentBillaccountVO queryBillAccountById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RebursePointNamespace+"queryBillAccountById",paraMap);
	}
	@Override
	public int billAccountSubmitAudit(Map<String, Object> map) {
		return this.getSqlSession().update(RebursePointNamespace+"billAccountSubmitAudit",map);
		
	}
	@Override
	public Page<RentBillaccountVO> queryRembursePointVO(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(RebursePointNamespace+"queryRembursePointVO",paraMap);
		return PageInterceptor.endPage();
	}
	@Override
	public RentBillaccountVO queryPaymentMethod(String billAccountId) {
		return this.getSqlSession().selectOne(RebursePointNamespace+"queryPaymentMethod",billAccountId);
	}
	@Override
	public List<RentBillaccountVO> queryRembursePointInfo(
			Map<String, Object> map) {
		return this.getSqlSession().selectList(RebursePointNamespace+"queryRembursePointInfo",map);
	}
	@Override
	public int insertBatchSelective(List<RentBillaccountVO> list) {
		return  this.getSqlSession().insert(RebursePointNamespace+"insertBatchSelective",list);
		
	}
}
