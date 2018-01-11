package com.xunge.service.system.department;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.model.system.department.SysDepartmentVO;

/**
 * @author Administrator
 * @date 2017年5月23日 下午3:53:01
 * @describe
 */
public interface ISysDepartmentService {
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	public SysDepartmentVO queryDeptitemByCodeRedis(String deptCode);
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	public List<Object> queryMenuByConditionsRedis(String prvId);
	
	/**
	 * 保存组织机构结点信息
	 * @param request
	 * @return
	 */
	public String insertDepartNode(HttpServletRequest request);
	/**
	 * 修改组织机构结点信息
	 * @param request
	 * @return
	 */
	public String updateDepartNode(HttpServletRequest request);
	
	/**
	 * 删除组织机构
	 * @param depId
	 * @return
	 */
	public String deleteDepart(List<SysDepartmentVO> depIds);
	/**
	 * 启用组织机构
	 * @param depItems
	 * @return
	 */
	public String updateOpenUse(List<SysDepartmentVO> depItems);
	/**
	 * 停用组织机构
	 * @param depItems
	 * @return
	 */
	public String updateCloseUse(List<SysDepartmentVO> depItems);
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	public List<Object> queryDepartByConditionsRedis(String funcCode, String funcName, String provSelect);
	
	/**
	 * @description 查询部门信息
	 * @author yuefy
	 * @date 创建时间：2017年8月3日
	 */
	List<SysDepartmentVO> queryAllDepartment(Map<String, Object> paramMap);

}
