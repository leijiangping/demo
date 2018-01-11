package com.xunge.service.basedata.DatPaymentPeriod.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.DatPaymentperiodMapper;
import com.xunge.service.basedata.DatPaymentPeriod.IDatPaymentperiodService;

@Service
public class DatPaymentperiodServiceImpl implements IDatPaymentperiodService {
	
	@Resource
    private DatPaymentperiodMapper datPaymentperiodMapper;

	@Override
	public String selectIdByValue(String value) {
		return datPaymentperiodMapper.selectIdByValue(value);
	}

}
