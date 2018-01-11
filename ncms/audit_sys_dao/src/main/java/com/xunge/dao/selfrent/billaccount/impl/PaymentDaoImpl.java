package com.xunge.dao.selfrent.billaccount.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billaccount.IVPaymentDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfrent.billAccount.VPaymentVO;

public class PaymentDaoImpl extends AbstractBaseDao implements IVPaymentDao {

	final String Namespace="com.xunge.mapping.VPaymentVOMapper.";
	/*final String Namespace1="com.xunge.mapping.DatBaseresourceVOMapper.";
	final String Namespace2="com.xunge.dao.RentPaymentdetailVOMapper.";

	@Override
	public RentPaymentdetailVO queryPayMentDetailByBaseId(Map<String,Object> map) {
		return this.getSqlSession().selectOne(Namespace2+"queryPayMentDetailByBaseId",map);
	}
	@Override
	public List<DatBaseresourceVO> queryDatBaseresourceByBillAccountId(String billAccountId) {
		return this.getSqlSession().selectList(Namespace1+"queryDatBaseresourceByBillAccountId",billAccountId);
	}
	*/
	@Override
	public Page<VPaymentVO> queryPayment(Map<String, Object> hashMaps,
			int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryPayment",hashMaps);
		return PageInterceptor.endPage();  
	}
	
	@Override
	public Page<List<VPaymentVO>> queryAllPayment(Map<String, Object> hashMaps,
			int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryAllPayment",hashMaps);
		return PageInterceptor.endPage();  
	}
	
	@Override
	public List<VPaymentVO> queryAllPayment(Map<String, Object> hashMaps) {
		return this.getSqlSession().selectList(Namespace+"queryAllPayment",hashMaps); 
	}
	@Override
	public Page<List<VPaymentVO>> queryPaymentContract(Map<String, Object> hashMaps,
			int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryPaymentContract",hashMaps);
		return PageInterceptor.endPage();  
	}
	
	@Override
	public List<VPaymentVO> queryContractPayment(
			Map<String, Object> hashMaps) {
		return this.getSqlSession().selectList(Namespace+"queryContractPayment",hashMaps);
	}

	@Override
	public int updateActivityCommit(Map<String, Object> hashMaps) {
		return this.getSqlSession().update(Namespace+"updateState",hashMaps);
	}
	@Override
	public int updateState(Map<String, Object> hashMaps) {
		return this.getSqlSession().update(Namespace+"updatePaymentState",hashMaps);
	}
	public int updateBillamountIdByPaymentId(Map<String, Object> hashMaps) {
		int count=this.getSqlSession().update(Namespace+"updateBillamountIdByPaymentId",hashMaps);
		return count;
	}
	
	@Override
	public Page<List<VPaymentVO>> queryContractPaymentByNoAmount(Map<String, Object> hashMaps) {
		// TODO Auto-generated method stub
		if(StrUtil.isNotBlank(hashMaps.get("pageNumber").toString())&&StrUtil.isNotBlank(hashMaps.get("pageSize").toString()))
		PageInterceptor.startPage(Integer.parseInt(hashMaps.get("pageNumber").toString()),Integer.parseInt(hashMaps.get("pageSize").toString()));
		this.getSqlSession().selectList(Namespace+"queryContractPaymentByNoAmount",hashMaps);
		return PageInterceptor.endPage();
	}
	@Override
	public List<VPaymentVO> queryContractPaymentByNoAmountList(Map<String, Object> hashMaps) {
		// TODO Auto-generated method stub
		List<VPaymentVO> list=this.getSqlSession().selectList(Namespace+"queryContractPaymentByNoAmount",hashMaps);
		return list;
	}

	@Override
	public VPaymentVO queryPaymentContractById(String id) {
		return this.getSqlSession().selectOne(Namespace+"queryPaymentContractById",id);
	}
	
}
