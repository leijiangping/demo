package com.xunge.service.elecbill.status.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xunge.dao.elecbill.EleBillamountVOMapper;
import com.xunge.dao.elecbill.RentBillamountVOMapper;
import com.xunge.model.elecbill.EleBillamountVO;
import com.xunge.model.elecbill.ElecBillParm;
import com.xunge.model.selfrent.billamount.RentBillamountVO;
import com.xunge.service.elecbill.status.IElecBillStatus;

@Service
public class ElecBillStatusImpl implements IElecBillStatus{

	@Resource
	private EleBillamountVOMapper eleBillamountVOMapper;
	
	@Resource
	private RentBillamountVOMapper rentBillamountVOMapper;

	private static final Logger LOGGER = Logger.getLogger(ElecBillStatusImpl.class);
	@Override
	public boolean editBillStatus(ElecBillParm elecBillParm) {
		
		boolean result=false;
		String collectNum=elecBillParm.getCollect_num();
		int status=elecBillParm.getStatus();
		
		if(StringUtils.isNoneBlank(collectNum)){
			
			/**
			 * 自维电费：ZDHZD
			 * 铁塔电费：TDHZD
			 * 自维租费：ZZHZD
			 * 铁塔租费（铁塔产品服务费）：TZHZD；
			 */
			if(collectNum.startsWith("ZDHZD-")||collectNum.startsWith("TDHZD-")){
				LOGGER.info("自维电费汇总报账状态修改，state:"+dealStatus(status));
				EleBillamountVO eleBillamountVO=new EleBillamountVO();
				eleBillamountVO.setBillamountCode(collectNum);
				eleBillamountVO.setBillamountState(dealStatus(status));
				result=eleBillamountVOMapper.updateStatus(eleBillamountVO);
			}
			else if(collectNum.startsWith("ZZHZD-")){
				LOGGER.info("自维租费汇总报账状态修改，state:"+dealStatus(status));
				RentBillamountVO rentBillamountVO=new RentBillamountVO();
				rentBillamountVO.setBillamountCode(collectNum);
				rentBillamountVO.setBillamountState(dealStatus(status));
				result=rentBillamountVOMapper.updateStatus(rentBillamountVO);
			}else if (collectNum.startsWith("TZHZD-")){
				//待完善， 铁塔租费（铁塔产品服务费）：TZHZD；
				LOGGER.info("铁塔租费汇总报账状态修改，state:"+dealStatus(status));
			}
		}	
		return result;
	}

	/**
	 * 转换报账系统回传状态为我们定义的状态
	 * @param status
	 * @return
	 */
	private int dealStatus(int status){
		
		int result=0;
		//-1删除---8已撤销
		if(-1==status){
			result=8;
		}
		//1已报帐----1已报帐
		else if(1==status){
			result=1;
		}
		//2-已审核----2已审核
		else if(2==status){
			result=2;
		}
		return result;
	}
}
