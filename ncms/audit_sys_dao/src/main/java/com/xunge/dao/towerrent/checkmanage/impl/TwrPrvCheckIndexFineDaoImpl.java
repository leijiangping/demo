package com.xunge.dao.towerrent.checkmanage.impl;

import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.checkmanage.ITwrPrvCheckIndexFineDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.checkmanage.TwrPrvCheckIndexFineVO;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;

/**
 * 
 * @author jcy
 * @date 2017年7月19日
 * @description 考核指标扣罚Dao
 */
public class TwrPrvCheckIndexFineDaoImpl extends AbstractBaseDao implements ITwrPrvCheckIndexFineDao{

	final String Namespace = "com.xunge.dao.TowerPrvCheckIndexFineVOMapper.";
	
	@Override
	public Page<List<TwrPrvCheckIndexFineVO>> queryrAllPrvCheckIndexFineVO(Map<String,Object> paramMap, int pageSize, int pageNum) {
		PageInterceptor.startPage(pageNum, pageSize);
		this.getSqlSession().selectList(Namespace+"queryAllPrvCheck",paramMap);
		return PageInterceptor.endPage();	
	}

	@Override
	public TwrPrvCheckIndexFineVO queryCheckIndexFineVOById(String checkId) {
		return this.getSqlSession().selectOne(Namespace+"queryPrvCheckIndexFineById",checkId);
	}

	@Override
	public String deleteTwrById(List<String> prvCheckIdList) {
		int result = this.getSqlSession().delete(Namespace+"deletePrvCheck",prvCheckIdList);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public String updateTwrById(TwrPrvCheckIndexFineVO twoProductVO) {
		int result = this.getSqlSession().update(Namespace+"updatePrvCheck",twoProductVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public String insertTwrById(TwrPrvCheckIndexFineVO checkIndexVO) {
		int result = this.getSqlSession().insert(Namespace+"addPrvCheck",checkIndexVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public String checkTwrById(List<String> prvCheckIdList) {
		int result = this.getSqlSession().update(Namespace+"updatePrvCheckState",prvCheckIdList);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public List<TwrPrvCheckIndexFineVO> queryExportList(Map<String,Object> prvCheckIdList) {
		return this.getSqlSession().selectList(Namespace+"queryAllPrvCheck",prvCheckIdList);
	}

	@Override
	public Page<TwrPrvCheckIndexFineVO> queryTwrRentInformationCheck(
			Map<String, Object> paramMap, int pageNum, int pageSize) {
		PageInterceptor.startPage(pageNum, pageSize);
		this.getSqlSession().selectList(Namespace+"queryPrvCheckInformation",paramMap);
		return PageInterceptor.endPage();	
	}

	@Override
	public List<Map<String, Object>> queryTwrPrvCheckIndexFineMapList(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(Namespace + "queryPrvCheckIndexFineMapListByCondition", params);
	}

	@Override
	public int insertBatchTwrRegPunishVO(List<TwrPrvCheckIndexFineVO> record) {
		return this.getSqlSession().insert(Namespace+"insertBatchTwrPrvPunishVO", record);
	}

	@Override
	public List<TwrPrvCheckIndexFineVO> selectTwrRegPunishList(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryAllPrvCheck",map);
	}
}
