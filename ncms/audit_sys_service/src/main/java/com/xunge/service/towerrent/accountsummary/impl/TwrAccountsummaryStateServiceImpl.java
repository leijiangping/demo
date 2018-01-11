package com.xunge.service.towerrent.accountsummary.impl;

import com.xunge.dao.towerrent.accountsummary.ITwrAccountsummaryStateVODao;
import com.xunge.service.towerrent.accountsummary.ITwrAccountsummaryStateService;


public class TwrAccountsummaryStateServiceImpl implements ITwrAccountsummaryStateService{

	private ITwrAccountsummaryStateVODao twrAccountsummaryStateVODao;

	public ITwrAccountsummaryStateVODao getTwrAccountsummaryStateVODao() {
		return twrAccountsummaryStateVODao;
	}

	public void setTwrAccountsummaryStateVODao(
			ITwrAccountsummaryStateVODao twrAccountsummaryStateVODao) {
		this.twrAccountsummaryStateVODao = twrAccountsummaryStateVODao;
	}
}
