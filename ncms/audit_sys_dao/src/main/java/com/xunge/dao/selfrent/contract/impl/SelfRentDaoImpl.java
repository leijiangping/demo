package com.xunge.dao.selfrent.contract.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.contract.ISelfRentDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfrent.contract.DatContractVO;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;

public class SelfRentDaoImpl extends AbstractBaseDao implements ISelfRentDao {
	//主合同表
	final String DatContractNamespace = "com.xunge.mapping.DatContractVOMapper.";
	//房屋租赁合同表
	final String RentContractNamespace = "com.xunge.mapping.RentContractVOMapper.";
	//付款供应商表
	final String DatSupplierNamespace = "com.xunge.mapping.DatSupplierVOMapper.";
	
	@Override
	public int insertDatContractVO(DatContractVO datContractVO) {
		return this.getSqlSession().insert(DatContractNamespace+"insertDatContractVO",datContractVO);
	}

	@Override
	public int updateDatContractVO(DatContractVO datContractVO) {
		return this.getSqlSession().update(DatContractNamespace+"updateDatContractVO",datContractVO);
	}

	@Override
	public int insertRentContractVO(RentContractVO rentContractVO) {
		return this.getSqlSession().insert(RentContractNamespace+"insertRentContractVO",rentContractVO);
	}

	@Override
	public int updateRentContractVO(RentContractVO rentContractVO) {
		return this.getSqlSession().update(RentContractNamespace+"updateRentContractVO",rentContractVO);
	}

	@Override
	public int insertDatSupplierVO(DatSupplierVO datSupplierVO) {
		return this.getSqlSession().insert(DatSupplierNamespace+"insertDatSupplierVO",datSupplierVO);
	}

	@Override
	public int updateDatSupplierVO(DatSupplierVO datSupplierVO) {
		return this.getSqlSession().update(DatSupplierNamespace+"updateDatSupplierVO",datSupplierVO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<RentContractVO> queryRentContractVO(Map<String,Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(RentContractNamespace+"queryRentContractVO",paraMap);
		return PageInterceptor.endPage();  
	}
	
	@Override
	public List<RentContractVO> queryRentContractVO(Map<String,Object> paraMap) {
		return this.getSqlSession().selectList(RentContractNamespace+"queryRentContractVO",paraMap);
	}

	@Override
	public DatSupplierVO queryDatSupplierById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(DatSupplierNamespace+"queryDatSupplierById",paraMap);
	}
	public List<DatSupplierVO> queryDatSupplierByPrvID(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(DatSupplierNamespace+"queryDatSupplierByPrvID",paraMap);
	}
	
	/**
	 * 分页查询供应商信息
	 * @param rentcontractId
	 * @author yuefy
	 * @return
	 */
	public Page<List<DatSupplierVO>> queryDatSupplierByPrvID(Map<String,Object> paraMap,
			int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(DatSupplierNamespace+"queryDatSupplierByPrvID",paraMap);
		return PageInterceptor.endPage();  
	};

	@Override
	public DatContractVO queryDatContractById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(DatContractNamespace+"queryDatContractById",paraMap);
	}

	@Override
	public RentContractVO queryRentContractById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RentContractNamespace+"queryRentContractById",paraMap);
	}
	@Override
	public RentContractVO queryAllRentContractById(String rentContractId) {
		return this.getSqlSession().selectOne(RentContractNamespace+"queryAllRentContractById",rentContractId);
	}
	
	@Override
	public int updateCommit(Map<String,Object> map) {
		return this.getSqlSession().update(RentContractNamespace+"commit",map);
	}

	
	@Override
	public List<DatContractVO> checkContractCode(Map<String, Object> map) {
		return this.getSqlSession().selectList(DatContractNamespace+"checkContractCode",map);
	}

	@Override
	public int stopContract(Map<String, Object> map) {
		return this.getSqlSession().update(DatContractNamespace+"stopContract",map);
	}
	
	@Override
	public int deleteContract(Map<String, Object> map) {
		return this.getSqlSession().update(DatContractNamespace+"deleteContract",map);
	}

	@Override
	public int deleteRentContract(Map<String, Object> parmMap) {
		return this.getSqlSession().update(RentContractNamespace+"deleteRentContract",parmMap);
	}

	@Override
	public RentContractVO queryRentContractByBillAccountId(String billAccountId) {
		return this.getSqlSession().selectOne(RentContractNamespace+"queryRentContractByBillAccountId",billAccountId);
	}

	@Override
	public List<String> queryRentContractEndDate(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(RentContractNamespace+"getPaymentEnddate",paraMap);
	}
	@Override
	public RentContractVO queryContractByContractId(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		return this.getSqlSession().selectOne(RentContractNamespace+"queryContractByContractId",paraMap);
	}

	@Override
	public RentContractVO getPaymentPeriodDate(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RentContractNamespace+"getPaymentPerioddate",paraMap);
	}
	@Override
	public RentContractVO queryContractAndSupplier(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RentContractNamespace+"queryContractAndSupplier",paraMap);
	}
	@Override
	public RentContractVO queryContAndSupByBillId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RentContractNamespace+"queryContAndSupByBillId",paraMap);
	}

	@Override
	public List<Map<String, String>> selectContractNumByCondition(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(RentContractNamespace+"selectContractNumByCondition",paraMap);
	}
}