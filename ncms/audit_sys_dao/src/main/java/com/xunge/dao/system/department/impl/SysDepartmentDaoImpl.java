package com.xunge.dao.system.department.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.department.ISysDepartmentDao;
import com.xunge.model.system.department.SysDepartmentVO;

/**
 * @author Geqs
 * @date 2017年5月23日 下午3:25:06
 * @describe
 */
public class SysDepartmentDaoImpl extends AbstractBaseDao implements ISysDepartmentDao {
		
	final String Namespace = "com.xunge.dao.SysDepartmentMapper.";

	@Override
	public SysDepartmentVO queryDeptitemByCode(Map<String, Object> paramMap) {
		return this.getSqlSession().selectOne(Namespace+"queryDeptitemByCode", paramMap);
	}

	@Override
	public List<SysDepartmentVO> queryDepartmentByConditions(
			Map<String, String> paramMap) {
		return this.getSqlSession().selectList(Namespace+"queryDepartmentByConditions", paramMap);
	}

	@Override
	public int saveDepartNode(SysDepartmentVO departmentVO) {
		return this.getSqlSession().update(Namespace+"saveDepartNode", departmentVO);
	}

	@Override
	public int modifyDepartNode(SysDepartmentVO departmentVO) {
		return this.getSqlSession().update(Namespace+"modiyDepartNode", departmentVO);
	}

	@Override
	public int modifyDepartStateBath(Map<String, Object> paramMap) {
		return this.getSqlSession().update(Namespace+"modifyDepartStateBath", paramMap);
	}

	@Override
	public List<SysDepartmentVO> queryDepartByConditions(
			Map<String, String> paramMap) {
		return this.getSqlSession().selectList(Namespace+"queryDepartByConditions", paramMap);
	}

	@Override
	public List<SysDepartmentVO> queryAllDepartment(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(Namespace+"queryDepartment",paramMap);
	}

	@Override
	public int addDepartment(SysDepartmentVO dept) {
		return this.getSqlSession().insert(Namespace+"addDepartment",dept);
	}
	
}
