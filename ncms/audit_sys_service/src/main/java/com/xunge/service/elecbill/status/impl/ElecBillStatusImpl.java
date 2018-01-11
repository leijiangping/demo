package com.xunge.service.elecbill.status.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xunge.dao.elecbill.EleBillamountVOMapper;
import com.xunge.dao.elecbill.RentBillamountVOMapper;
import com.xunge.model.elecbill.EleBillamountVO;
import com.xunge.model.elecbill.ElecBillParm;
import com.xunge.model.elecbill.RentBillamountVO;
import com.xunge.service.elecbill.status.IElecBillStatus;

@Service
public class ElecBillStatusImpl implements IElecBillStatus{

	@Resource
	private EleBillamountVOMapper eleBillamountVOMapper;
	
	@Resource
	private RentBillamountVOMapper rentBillamountVOMapper;
	
	@Override
	public boolean editBillStatus(ElecBillParm elecBillParm) {
		
		boolean result=false;
		String boeNum=elecBillParm.getBoe_num();
		String collectNum=elecBillParm.getCollect_num();
		int status=elecBillParm.getStatus();
		String paymentDate=elecBillParm.getPayment_date();
		String checkDate=elecBillParm.getCheck_date();
		
		if(StringUtils.isNoneBlank(collectNum)){
			
			if(collectNum.startsWith("ZD-")||collectNum.startsWith("TD-")){
				EleBillamountVO eleBillamountVO=new EleBillamountVO();
				eleBillamountVO.setBillamountId(collectNum);
				eleBillamountVO.setBillamountState(dealStatus(status));
				result=eleBillamountVOMapper.updateStatus(eleBillamountVO);
			}
			else if(collectNum.startsWith("ZZ-")||collectNum.startsWith("TZ-")){
				RentBillamountVO rentBillamountVO=new RentBillamountVO();
				rentBillamountVO.setBillamountId(collectNum);
				rentBillamountVO.setBillamountState(dealStatus(status));
				result=rentBillamountVOMapper.updateStatus(rentBillamountVO);
			}
		}	
		return result;
	}

	private int dealStatus(int status){
		
		int result=0;
		if(-1==status){
			result=8;
		}
		else if(1==status){
			result=1;
		}
		else if(2==status){
			result=2;
		}
		return result;
	}
}
