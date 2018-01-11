package com.xunge.service.system.department.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.department.ISysDepartmentDao;
import com.xunge.model.system.department.SysDepartmentVO;
import com.xunge.service.system.department.ISysDepartmentService;
import com.xunge.service.util.CodeGeneratorUtil;

/**
 * @author Geqs
 * @date 2017年5月23日 下午3:54:24
 * @describe
 */
public class SysDepartmentServiceImpl implements ISysDepartmentService {

	private ISysDepartmentDao sysDepartmentDao;


	public ISysDepartmentDao getSysDepartmentDao() {
		return sysDepartmentDao;
	}

	public void setSysDepartmentDao(ISysDepartmentDao sysDepartmentDao) {
		this.sysDepartmentDao = sysDepartmentDao;
	}

	@Override
	public List<Object> queryMenuByConditionsRedis(String prvId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("prvId", prvId);
		paramMap.put("depState", StateComm.STATE_str__1);
		List<SysDepartmentVO> departmentVOs = sysDepartmentDao.queryDepartmentByConditions(paramMap);
		return menuList(departmentVOs);
	}
	
	private List<Object> menuList(List<SysDepartmentVO> departmentVOs){
		 List<Object> list = new ArrayList<Object>();  
	        for (SysDepartmentVO x : departmentVOs) {     
	            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();  
	            mapArr.put("depCode", x.getDepCode());  
                mapArr.put("depName", x.getDepName()); 
                mapArr.put("parentCode", x.getParentCode()); 
                mapArr.put("parentName", x.getParentName()); 
                
                mapArr.put("depOrder", x.getDepOrder()); 
                mapArr.put("depState", x.getDepState());
                mapArr.put("depId", x.getDepId());
                mapArr.put("prvId", x.getPrvId());
                mapArr.put("open", true);
	            list.add(mapArr);  
	        }     
	        return list;  
	}

	@Override
	public String insertDepartNode(HttpServletRequest request) {
		SysDepartmentVO departmentVO = new SysDepartmentVO();
		departmentVO.setDepId(SysUUID.generator());
		departmentVO.setPrvId(request.getParameter("prvId"));
		departmentVO.setDepCode(CodeGeneratorUtil.DeptCodeGet(request.getParameter("pdepId")));
		departmentVO.setDepName(request.getParameter("depName"));
		departmentVO.setPdepId(request.getParameter("pdepId"));
		departmentVO.setDepOrder(Integer.parseInt(request.getParameter("depOrder")));
		departmentVO.setDepState(StateComm.STATE_0);
		//插入一条组织机构信息
		int result = sysDepartmentDao.saveDepartNode(departmentVO);
		return(result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateDepartNode(HttpServletRequest request) {
		SysDepartmentVO departmentVO = new SysDepartmentVO();
		departmentVO.setDepId(request.getParameter("depId"));
		departmentVO.setDepCode(request.getParameter("depCode"));
		departmentVO.setDepName(request.getParameter("depName"));
		departmentVO.setDepOrder(Integer.parseInt(request.getParameter("depOrder")));
		departmentVO.setDepState(StateComm.STATE_0);
		//修改一条组织机构信息
		int result = sysDepartmentDao.modifyDepartNode(departmentVO);
		return(result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String deleteDepart(List<SysDepartmentVO> depIds) { 
		List<String> idLists = new ArrayList<String>();
		for (SysDepartmentVO departmentVO : depIds) {
			idLists.add(departmentVO.getDepId());
		}
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("idLists", idLists);
		map.put("state", StateComm.STATE__1);
		int result = sysDepartmentDao.modifyDepartStateBath(map);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateOpenUse(List<SysDepartmentVO> depItems) {
		List<String> idLists = new ArrayList<String>();
		for (SysDepartmentVO departmentVO : depItems) {
			idLists.add(departmentVO.getDepId());
		}
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("idLists", idLists);
		map.put("state", StateComm.STATE_0);
		int result = sysDepartmentDao.modifyDepartStateBath(map);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateCloseUse(List<SysDepartmentVO> depItems) {
		List<String> idLists = new ArrayList<String>();
		for (SysDepartmentVO departmentVO : depItems) {
			idLists.add(departmentVO.getDepId());
		}
		Map<String, Object> map = new HashMap<String ,Object>();
		map.put("idLists", idLists);
		map.put("state", StateComm.STATE_9);
		int result = sysDepartmentDao.modifyDepartStateBath(map);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public List<Object> queryDepartByConditionsRedis(String funcCode,
			String funcName, String prvId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("funcCode", funcCode);
		paramMap.put("funcName", funcName);
		paramMap.put("prvId", prvId);
		paramMap.put("depState", StateComm.STATE_str__1);
		List<SysDepartmentVO> list=sysDepartmentDao.queryDepartByConditions(paramMap);
		return menuList(list);
	}

	@Override
	public List<SysDepartmentVO> queryAllDepartment(Map<String, Object> paramMap) {
		return sysDepartmentDao.queryAllDepartment(paramMap);
	}

	@Override
	public SysDepartmentVO queryDeptitemByCodeRedis(String deptCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deptCode", deptCode);
		paramMap.put("state", StateComm.STATE_0);
		return sysDepartmentDao.queryDeptitemByCode(paramMap);
	}
}
