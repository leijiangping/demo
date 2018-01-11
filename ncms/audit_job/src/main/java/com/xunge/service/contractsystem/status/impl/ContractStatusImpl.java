package com.xunge.service.contractsystem.status.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.DatContractVOMapper;
import com.xunge.model.basedata.DatContractVO;
import com.xunge.model.contractsystem.ContractParm;
import com.xunge.service.contractsystem.status.IContractStatus;
import com.xunge.util.DateConverter;

@Service
public class ContractStatusImpl implements IContractStatus{

	@Resource
	private DatContractVOMapper datContractVOMapper;
	
	@Override
	public boolean editContractStatus(ContractParm contractParm) {
		
		boolean result=false;
		String elsys_contract_code=contractParm.getElsys_contract_code();
		int status= contractParm.getStatus();
		String change_date=contractParm.getChange_date();
		if(StringUtils.isNoneBlank(elsys_contract_code)){
			DatContractVO datContractVO=new DatContractVO();
			datContractVO.setContractId(elsys_contract_code);
			datContractVO.setContractState(dealStatus(status));
			if(StringUtils.isNoneBlank(change_date)){
				datContractVO.setContractChangedate(DateConverter.converteToDate(change_date));
			}
			result=datContractVOMapper.updateStatus(datContractVO);
		}
		return result;
	}

	private int dealStatus(int status){
		
		int result=0;
		if(-1==status){
			result=-1;
		}
		else if(1==status){
			result=0;
		}
		return result;
	}
}
