package com.xunge.service.selfrent.contract.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.dao.selfrent.contract.IDatPaymentPeriodDao;
import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;
import com.xunge.service.selfrent.contract.IDatPaymentPeriodService;

/**
 * 缴费周期接口实现类
 * @author changwq
 *
 */
public class DatPaymentPeriodServiceImpl implements IDatPaymentPeriodService {

	private IDatPaymentPeriodDao datPaymentPeriodDao;

	public IDatPaymentPeriodDao getDatPaymentPeriodDao() {
		return datPaymentPeriodDao;
	}

	public void setDatPaymentPeriodDao(IDatPaymentPeriodDao datPaymentPeriodDao) {
		this.datPaymentPeriodDao = datPaymentPeriodDao;
	}

	@Override
	public List<DatPaymentPeriodVO> queryAllDatPaymentPeriod() {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("state",StateComm.STATE_0);
		return datPaymentPeriodDao.queryAllDatPaymentPeriod(paraMap);
	}

	@Override
	public DatPaymentPeriodVO queryDatPaymentPeriodById(String paymentperiodId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("paymentperiodId",paymentperiodId);
		return datPaymentPeriodDao.queryDatPaymentPeriodById(paraMap);
	}
	
	
	
}
