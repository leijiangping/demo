package com.xunge.service.selfrent.billamount.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.selfrent.billaccount.IVPaymentDao;
import com.xunge.dao.selfrent.billamount.RentBillamountDao;
import com.xunge.dao.selfrent.billamount.RentBillamountDetailDao;
import com.xunge.model.selfrent.billAccount.VPaymentVO;
import com.xunge.model.selfrent.billamount.RentBillamountDetailVO;
import com.xunge.model.selfrent.billamount.RentBillamountVO;
import com.xunge.service.selfrent.billamount.IRentBillamountService;
import com.xunge.service.util.CodeGeneratorUtil;

/**
 * 租费报账汇总Service
 * @author zhujj
 * @version 2017-06-26
 */
public class RentBillamountServiceImpl implements IRentBillamountService<RentBillamountVO>  {
	private RentBillamountDao<RentBillamountVO> billAmountDao;
	private RentBillamountDetailDao billAmountDetailDao;
	private IVPaymentDao paymentDao;
	
	
	public IVPaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(IVPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public RentBillamountDao<RentBillamountVO> getBillAmountDao() {
		return billAmountDao;
	}

	public void setBillAmountDao(RentBillamountDao<RentBillamountVO> billAmountDao) {
		this.billAmountDao = billAmountDao;
	}

	public RentBillamountDetailDao getBillAmountDetailDao() {
		return billAmountDetailDao;
	}

	public void setBillAmountDetailDao(RentBillamountDetailDao billAmountDetailDao) {
		this.billAmountDetailDao = billAmountDetailDao;
	}

	public Page<List<RentBillamountVO>> queryRentBillamountPage(Map<String,Object> entity) {
		// TODO Auto-generated method stubfindPage
		return billAmountDao.queryRentBillamountPage(entity);
	}
	public RentBillamountVO queryRentBillamountById(Map<String,Object> map) {
		// TODO Auto-generated method stubfindPage
		return billAmountDao.queryRentBillamountById(map);
	}
	@Override
	public List<RentBillamountVO> findList(RentBillamountVO entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 插入租费报账汇总
	 * @param List<VPaymentVO> 缴费记录
	 * @return
	 */
	public Map<String,Object> insertRentBillamount(List<VPaymentVO> list,Map<String,Object> map){
		RentBillamountVO rentBillamountVO=new RentBillamountVO();
		String billamountId=SysUUID.generator();
		float billamountNotax=0.00f;
		float billamountWithtax=0.00f;
		float billamountTaxamount=0.00f;
		for(int i=0;i<list.size();i++){
			VPaymentVO vp=list.get(i);
			if(i==Integer.parseInt(SelfelecComm.NUMBER_0)){
				UserLoginInfo userInfo=(UserLoginInfo)map.get("userInfo");
				if(userInfo == null){
					throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
				}
				rentBillamountVO.setBillamountId(billamountId);
				rentBillamountVO.setRentcontractId(vp.getRentcontractId());
				rentBillamountVO.setSupplierCode(vp.getSupplierCode());
				rentBillamountVO.setSupplierName(vp.getSupplierName());
				rentBillamountVO.setSupplierAddress(vp.getSupplierAddress());
				rentBillamountVO.setSupplierContact(vp.getSupplierContact());
				rentBillamountVO.setSupplierTelephone(vp.getSupplierTelephone());
				rentBillamountVO.setBankUser(vp.getBankUser());
				rentBillamountVO.setBankAccount(vp.getBankAccount());
				rentBillamountVO.setDepositBank(vp.getDepositBank());
				rentBillamountVO.setBillamountCode(CodeGeneratorUtil.BillAmountCodeGet(userInfo.getPrv_code()));//自动生成code
				rentBillamountVO.setBillamountDate(new Date());

				
				rentBillamountVO.setBillamountState(StateComm.STATE_0);//报账状态0：未报账1：已报账
				rentBillamountVO.setBillamountNote(DateDisposeComm.TEST);//汇总摘要
				
				rentBillamountVO.setUserNumber(userInfo.getUser_code());//报账工号
				rentBillamountVO.setUserName(userInfo.getUser_loginname());//报账用户名
				
				
			}
			
			RentBillamountDetailVO rentBillamountDetailVO = new RentBillamountDetailVO();
			rentBillamountDetailVO.setBillamountdetailId(SysUUID.generator());
			rentBillamountDetailVO.setBillamountId(billamountId);
			rentBillamountDetailVO.setBillamountEnddate(vp.getPaymentEnddate());
			rentBillamountDetailVO.setBillamountStartdate(vp.getPaymentStartdate());
			//相差的天数
			long diffDay=DateUtil.diff(rentBillamountDetailVO.getBillamountStartdate(),rentBillamountDetailVO.getBillamountEnddate(), DateUtil.DAY_MS);
			//进位取整:Math.ceil(3.1)=4 
			rentBillamountDetailVO.setBillamountNumber(Math.ceil(diffDay/30.417));//数量是（缴费期终-缴费期始）/30.417 取整
			rentBillamountDetailVO.setBillamountPrice(vp.getPayActamount());//单价，房租使用合同年租费除以12个月
			rentBillamountDetailVO.setBillamountNotax(vp.getPayCalcamount());
			
			rentBillamountDetailVO.setBillamountTaxamount(vp.getBillamountTaxamount());
			rentBillamountDetailVO.setBillamountTaxratio(vp.getBillamountTaxratio());
			
			//汇总金额

			billamountNotax+=vp.getPayCalcamount();
			billamountWithtax+=vp.getPayActamount();
			billamountTaxamount+=vp.getBillamountTaxratio();
			
			rentBillamountVO.addRentBillamountDetail(rentBillamountDetailVO);
		}

		rentBillamountVO.setBillamountNotax(billamountNotax);// 汇总金额（不含税）
		rentBillamountVO.setBillamountWithtax(billamountWithtax);// 汇总金额（含税）
		rentBillamountVO.setBillamountTaxamount(billamountTaxamount);// 汇总税额
		int count=0;
		count +=billAmountDao.insertRentBillamountVO(rentBillamountVO);
		count +=billAmountDetailDao.insertRentBillamountDetailList(rentBillamountVO.getRentBillamountDetail());
		//更新缴费记录中汇总ID
		map.put("billamountId", billamountId);
		count +=paymentDao.updateBillamountIdByPaymentId(map);
		return map;
	}


}