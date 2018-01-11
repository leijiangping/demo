package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrProductConfDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.ProductConfigVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 产品配置库
 */
public class TwrProductConfDaoImpl extends AbstractBaseDao implements ITwrProductConfDao{

	final String TwrProConfNamespace = "com.xunge.mapping.ProductConfigVOMapper.";
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductConfigVO>> queryAllObj(int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrProConfNamespace+"queryAllObj");
		return PageInterceptor.endPage(); 
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteObjById(List<String> ids) {
		Map<String,List<String>> twrIdMap = new HashMap<String,List<String>>();
		twrIdMap.put("objIdList", ids);
		int result = this.getSqlSession().delete(TwrProConfNamespace+"deleteObjById",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateObjById(ProductConfigVO proConfVo) {
		int result = this.getSqlSession().update(TwrProConfNamespace+"updateObjById",proConfVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertObjById(ProductConfigVO proConfVo) {
		proConfVo.setProductconfigId(SysUUID.generator());
		int result = this.getSqlSession().insert(TwrProConfNamespace+"addObj",proConfVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopObjById(List<String> ids, String highUpState) {
		Map<String,Object> twrIdMap = new HashMap<String,Object>();
		twrIdMap.put("objIdList", ids);
		twrIdMap.put("productconfigState", highUpState);
		int result = this.getSqlSession().update(TwrProConfNamespace+"updateObjState",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductConfigVO>> queryObjByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrProConfNamespace+"queryObjByCondition",paramMap);
		return PageInterceptor.endPage();
	}

	/**
	 * @author zhaosf
	 */
	@Override
	public List<ProductConfigVO> queryObjByCondition(
			Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(TwrProConfNamespace+"queryObjByCondition",paramMap);
	}
	

}
