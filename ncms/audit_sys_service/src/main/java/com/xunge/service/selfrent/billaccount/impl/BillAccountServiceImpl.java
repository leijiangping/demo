package com.xunge.service.selfrent.billaccount.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.DateUtils;
import com.xunge.dao.selfrent.billaccount.IBillAccountDao;
import com.xunge.dao.selfrent.billaccount.IDatBaseresourceDao;
import com.xunge.dao.selfrent.billaccount.IRentPaymentDao;
import com.xunge.dao.selfrent.billaccount.IRentPaymentdetailDao;
import com.xunge.dao.selfrent.contract.IDatPaymentPeriodDao;
import com.xunge.dao.selfrent.contract.ISelfRentDao;
import com.xunge.model.selfrent.billAccount.BillAccountVO;
import com.xunge.model.selfrent.billAccount.CoverAllVO;
import com.xunge.model.selfrent.billAccount.DatBaseresourceVO;
import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;
import com.xunge.model.selfrent.billAccount.RentPaymentdetailVO;
import com.xunge.model.selfrent.contract.DatContractVO;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.service.selfrent.billaccount.IBillAccountService;
/**
 * 报账点service实现类
 *
 */
public class BillAccountServiceImpl implements IBillAccountService {

	private IBillAccountDao billAccountDao;
	
	private IRentPaymentDao rentPayMentDao;
	
	private IDatBaseresourceDao datBaseResourceDao;
	
	private ISelfRentDao selfRentDao;
	
	private IRentPaymentdetailDao rentPaymentdetailDao;
	
	private IDatPaymentPeriodDao datPaymentPeriodDao;
	
	/**
	 * 查询报账点信息
	 * @throws ParseException 
	 */
	@Override
	public Page<BillAccountVO> queryBillAccountVO(
			Map<String, Object> map, int pageNumber, int pageSize) throws ParseException {
		map.put("auditState",ActivityStateComm.STATE_NORMAL);
		Page<BillAccountVO> pageLists = billAccountDao.queryBillAccountVO(map, pageNumber, pageSize);
		Page<BillAccountVO> pageList = new Page<>(pageNumber, pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd); 
		List<BillAccountVO> list = new ArrayList<BillAccountVO>();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		list = pageLists.getResult();
		if(list.size() > 0 && list != null){
			for(int i=0;i<list.size();i++){
				String billAccountId = list.get(i).getBillAccountId();
				List<RentPaymentVO> paymentList = rentPayMentDao.queryRentPaymentByBillAccountId(billAccountId);
				//判断缴费区间是否连续
				if(isGoon(paymentList)){
					//连续
					//获取合同对象
					RentContractVO rentContractVO = selfRentDao.queryRentContractByBillAccountId(billAccountId);
					//获取缴费周期id
					if(rentContractVO != null){
						String paymentperiodId = rentContractVO.getPaymentperiodId();
						paraMap.put("paymentperiodId",paymentperiodId);
						if(paymentperiodId != null){
							//查询缴费周期对象
							DatPaymentPeriodVO datPaymentPeriod = datPaymentPeriodDao.queryDatPaymentPeriodById(paraMap);
							if(datPaymentPeriod != null){
								//缴费周期数值（天）
								Integer value = datPaymentPeriod.getPaymentperiodValue();
								if(paymentList.size()>0){
									String endDate = paymentList.get(paymentList.size()-1).getPaymentEnddate();
									Date enddate = sdf.parse(endDate);
									//计算当前时间减去最后一次缴费期终时间加缴费周期时间
									long msg = new Date().getTime()-(enddate.getTime()+value);
									if(msg < 0){
										//在缴费周期内
										list.get(i).setIsPay(SelfelecComm.IS_PAY_YES);
									}else{
										//已经超出缴费周期
										list.get(i).setIsPay(SelfelecComm.IS_PAY_NO);
									}
								}else{
									//没有缴费记录
									list.get(i).setIsPay(SelfelecComm.IS_PAY_NO);
								}
							}else{
								//没有缴费周期对象
								list.get(i).setIsPay(SelfelecComm.IS_PAY_NO);
							}
						}else{
							//未关联缴费周期
							list.get(i).setIsPay(SelfelecComm.IS_PAY_NO);
						}
					}else{
						//未关联合同
						list.get(i).setIsPay(SelfelecComm.IS_PAY_NO);
					}
				}else{
					//不连续
					list.get(i).setIsPay(SelfelecComm.IS_PAY_NO);
				}
			}
		}
		pageList.setResult(list);
		return pageList;
	}
	/**
	 * 判断缴费周期是否连续
	 * @param list
	 * @return
	 * @throws ParseException
	 */
	private boolean isGoon(List<RentPaymentVO> list) throws ParseException{
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd); 
		Calendar calendar = new GregorianCalendar();
		if(list.size()>0){
			for(int i=0;i<list.size()-1;i++){
				//获取下一次缴费期始
				String startDate = list.get(i+1).getPaymentStartdate();
				//获取本次缴费期终
				String endDate = list.get(i).getPaymentEnddate();
				//字符串转DATE
				Date enddate = DateUtils.parseDate(endDate); 
				//本次期终加一天
				calendar.setTime(enddate); 
				calendar.add(calendar.DATE,1);
				enddate = calendar.getTime();
				Date startdate = DateUtils.parseDate(startDate); 
				//比较本次期终加一天和下一次期始日期是否相同
				if(startdate.getTime() == enddate.getTime()){
					continue;
				}else{
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 根据报账点id查询所有有关信息
	 */
	@Override
	public CoverAllVO queryAll(String billaccountId) {
		//创建整合对象
		CoverAllVO ca = new CoverAllVO();
		Map<String, Object> paraMap = new HashMap<String,Object>();
		paraMap.put("billaccountId",billaccountId);
		DatContractVO datContractVO = null;
		DatSupplierVO datSupplierVO = null;
		//获取报账点对象
		BillAccountVO billAccountVO = billAccountDao.queryBillAccountById(billaccountId);
		//获取资源点集合
		List<DatBaseresourceVO> lstDatbase = datBaseResourceDao.queryDatBaseresourceByBillAccountId(billaccountId);
		Date finallyTime = null;
		//获取缴费信息
		RentPaymentVO rentPayMentVO = null;
		List<RentPaymentVO> rentPayMentVOlst = rentPayMentDao.queryRentPaymentByBillAccountId(billaccountId);
		if(rentPayMentVOlst != null && rentPayMentVOlst.size()>0){
			//初始化
			finallyTime = DateUtil.parse(rentPayMentVOlst.get(0).getPaymentEnddate());
			rentPayMentVO = rentPayMentVOlst.get(0);
			if(rentPayMentVOlst.size()>1){
				for(int i = 1;i < rentPayMentVOlst.size();i++){
					Date secondTime = DateUtil.parse(rentPayMentVOlst.get(i).getPaymentEnddate());
					if(finallyTime.compareTo(secondTime) == StateComm.STATE__1){
						finallyTime = secondTime;
						rentPayMentVO = rentPayMentVOlst.get(i);
					}
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(finallyTime);
			cal.add(Calendar.DATE,StateComm.STATE_1);
			finallyTime =cal.getTime();
			String EndDate = (new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd)).format(finallyTime);//加一天后结果
			rentPayMentVO.setPaymentEnddate(EndDate);
			ca.setRentPayMentVO(rentPayMentVO);
		}
		
		//获取合同对象
		RentContractVO rentContractVO = selfRentDao.queryRentContractByBillAccountId(billaccountId);
		//传如参数
		if(rentContractVO != null){
			paraMap.put("supplierId",rentContractVO.getSupplierId());
			paraMap.put("datContractId",rentContractVO.getContractId());
			//获取主合同对象
			datContractVO = selfRentDao.queryDatContractById(paraMap);
			//获取供应商对象
			datSupplierVO = selfRentDao.queryDatSupplierById(paraMap);
		}
		
		//设置返回值
		ca.setBillAccountVO(billAccountVO);
		ca.setLstDatbase(lstDatbase);
		ca.setRentContractVO(rentContractVO);
		ca.setDatContractVO(datContractVO);
		ca.setDatSupplierVO(datSupplierVO);
		return ca;
	}
	
	/**
	 * 报账点租费录入
	 * */
	@Override
	public String insertAllForm(Map<String, Object> map) {
		String msg = "";
		//controller传入参数
		RentPaymentVO newRentPaymentVO = (RentPaymentVO) map.get("rentPaymentVO");
		Date newStartTime = DateUtil.parse(newRentPaymentVO.getPaymentStartdate());
		Date newEndTime = DateUtil.parse(newRentPaymentVO.getPaymentEnddate());
		List<RentPaymentVO> rentPayMentVOlst = rentPayMentDao.queryRentPaymentByBillAccountId((String)map.get("billaccountId"));
		if(rentPayMentVOlst != null){
			for(int i = 0;i < rentPayMentVOlst.size();i++){
				Date oldStartTime = DateUtil.parse(rentPayMentVOlst.get(i).getPaymentStartdate());
				Date oldEndTime = DateUtil.parse(rentPayMentVOlst.get(i).getPaymentEnddate());
				if(newStartTime.getTime() > oldEndTime.getTime() || newEndTime.getTime() < oldStartTime.getTime()){
					continue;
				}else{
					//throw new BusinessException("不可重复缴费！");
					msg = PromptMessageComm.DO_NOT_REPEAT_PAYMENTS;
					return msg;
				}
			}
		}
		map.put("state",StateComm.STATE_0);
		int pop = rentPayMentDao.insertRentPayment(newRentPaymentVO);
		if(pop != StateComm.STATE_1){
			msg = PromptMessageComm.BILLACCOUNT_PAYMENT_ENTERING_FAILED;
			return msg;
		}
		List<DatBaseresourceVO> dbslst = (List<DatBaseresourceVO>) map.get("dbslist");
		if(dbslst != null){
			for(int i = 0;i<dbslst.size();i++){
				map.put("baseresourceId",dbslst.get(i).getBaseresourceId());
				map.put("rentPaymentdetailVO",dbslst.get(i).getRentPaymentdetailVO());
				int dop = rentPaymentdetailDao.insertRentPaymentdetail(map);
				if(dop != 1){
					msg = PromptMessageComm.BILLACCOUNT_LINKROOM_PAYMENT_ENTERING_FAILED;
					return msg;
				}
			}
		}
		msg = RESULT.SUCCESS_1;
		return msg;
		//return "报账点缴费信息录入失败！";
	}
	
	@Override
	public String updateAllForm(Map<String, Object> map) {
		try {
			RentPaymentVO rentpaymentVO = (RentPaymentVO) map
					.get("rentpaymentVO");
			rentPayMentDao.updateRentPayment(rentpaymentVO);
			List<DatBaseresourceVO> dbsList = (List<DatBaseresourceVO>) map.get("dbsList");
			if(dbsList != null){
				for (int i = 0; i < dbsList.size(); i++) {
					RentPaymentdetailVO rentpaymentdetailVO = dbsList.get(i)
							.getRentPaymentdetailVO();
					rentPaymentdetailDao
							.updateRentPaymentdetail(rentpaymentdetailVO);
				}
			}
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
	}
	
	public IBillAccountDao getBillAccountDao() {
		return billAccountDao;
	}

	public void setBillAccountDao(IBillAccountDao billAccountDao) {
		this.billAccountDao = billAccountDao;
	}
	public IRentPaymentDao getRentPayMentDao() {
		return rentPayMentDao;
	}
	public void setRentPayMentDao(IRentPaymentDao rentPayMentDao) {
		this.rentPayMentDao = rentPayMentDao;
	}
	public IDatBaseresourceDao getDatBaseResourceDao() {
		return datBaseResourceDao;
	}
	public void setDatBaseResourceDao(IDatBaseresourceDao datBaseResourceDao) {
		this.datBaseResourceDao = datBaseResourceDao;
	}
	public ISelfRentDao getSelfRentDao() {
		return selfRentDao;
	}
	public void setSelfRentDao(ISelfRentDao selfRentDao) {
		this.selfRentDao = selfRentDao;
	}
	public IRentPaymentdetailDao getRentPaymentdetailDao() {
		return rentPaymentdetailDao;
	}
	public void setRentPaymentdetailDao(IRentPaymentdetailDao rentPaymentdetailDao) {
		this.rentPaymentdetailDao = rentPaymentdetailDao;
	}
	public IDatPaymentPeriodDao getDatPaymentPeriodDao() {
		return datPaymentPeriodDao;
	}
	public void setDatPaymentPeriodDao(IDatPaymentPeriodDao datPaymentPeriodDao) {
		this.datPaymentPeriodDao = datPaymentPeriodDao;
	}
	@Override
	public List<Map<String, Object>> queryBillaccountRelations(
			String billaccountId) {
		return billAccountDao.queryBillaccountRelations(billaccountId);
	}
}
