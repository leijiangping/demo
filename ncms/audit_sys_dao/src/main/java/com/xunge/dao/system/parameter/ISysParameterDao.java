package com.xunge.dao.system.parameter;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.system.parameter.SysParameterVO;

public interface ISysParameterDao {
	/**
	 * 修改系统参数
	 * @param sysParameterVO
	 * @return
	 * @author changwq
	 */
	public int updateParameter(SysParameterVO sysParameterVO);
	/**
	 * 系统参数模糊查询
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public Page<List<SysParameterVO>> queryParameter(Map<String,Object> paraMap,int pageNumber,int pageSize);
	/**
	 * 根据系统参数id查询一个参数详情
	 * @param paraId
	 * @return
	 * @author changwq
	 */
	public SysParameterVO getParameter(String paraId);
	/**
	 * 启用系统参数
	 * @param paraId
	 * @return
	 * @author changwq
	 */
	public int openParameter(String paraId);
	/**
	 * 停用系统参数
	 * @param paraId
	 * @return
	 * @author changwq
	 */
	public int stopParameter(String paraId);
	
	/**
	 * 根据省份prvId paraCode 查询
	 * @param paraMap
	 * @return
	 */
	public SysParameterVO queryParameter(Map<String,Object> paraMap);
}
