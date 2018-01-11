package com.xunge.service.system.parameter;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.system.parameter.SysParameterVO;

/**
 * 系统参数管理service接口
 */
public interface ISysParameterService {
	/**
	 * 修改参数
	 * @param sysParameterVO
	 * @return
	 */
	public String updateParameter(SysParameterVO sysParameterVO);
	
	/**
	 * 查询所有参数
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<SysParameterVO>> queryParameter(Map<String,Object> paraMap,int pageNumber,int pageSize);
	
	/**
	 * 根据参数Id获取一个参数对象
	 * @param paraId
	 * @return
	 */
	public SysParameterVO getParameter(String paraId);
	
	/**
	 * 启用参数
	 * @param paraId
	 * @return
	 */
	public String openParameter(String paraId);
	
	/**
	 * 停用参数
	 * @param paraId
	 * @return
	 */
	public String stopParameter(String paraId);
}
