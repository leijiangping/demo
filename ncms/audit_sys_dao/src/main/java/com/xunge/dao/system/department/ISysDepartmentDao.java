package com.xunge.dao.system.department;

import java.util.List;
import java.util.Map;

import com.xunge.model.system.department.SysDepartmentVO;
/**
 * 
 * @author	Geqs
 *
 */
public interface ISysDepartmentDao {
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	public SysDepartmentVO queryDeptitemByCode(Map<String, Object> paramMap);
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	List<SysDepartmentVO> queryDepartmentByConditions(Map<String, String> paramMap);
	/**
	 * 保存组织机构结点信息
	 * @param menuId
	 * @return
	 */
	public int saveDepartNode(SysDepartmentVO departmentVO);
	/**
	 * 修改组织机构结点信息
	 * @param menuId
	 * @return
	 */
	public int modifyDepartNode(SysDepartmentVO departmentVO);
	
	/**
	 * 批量删除菜单项状态
	 * @param paramMap
	 * @return
	 */
	int modifyDepartStateBath(Map<String, Object> paramMap);
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	List<SysDepartmentVO> queryDepartByConditions(Map<String, String> paramMap);
	
	/**
	 * 查询所有部门信息
	 * @return
	 */
	List<SysDepartmentVO> queryAllDepartment(Map<String, Object> paramMap);
	/**
	 * 新增部门信息
	 * @param dept
	 * @return
	 */
	int addDepartment(SysDepartmentVO dept);
}