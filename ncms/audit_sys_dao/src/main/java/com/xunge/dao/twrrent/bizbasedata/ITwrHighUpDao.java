package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 挂高范围
 */
public interface ITwrHighUpDao {
	/**
	 * 查询所有挂高集合
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<HighUpVO>> queryAllHighUpVO(int pageSize,int pageNumber);
	/**
	 * 根据风压Id删除挂高对象
	 * @param ids
	 * @return
	 */
	public String deleteHighUpById(List<String> ids); 
	/**
	 * 修改所选挂高对象
	 * @param highUpVo
	 * @return
	 */
	public String updateHighUpById(HighUpVO highUpVo); 
	/**
	 * 新增挂高对象
	 * @param highUpVo
	 * @return
	 */
	public String insertHighUpById(HighUpVO highUpVo); 
	/**
	 * 启用或停用挂高状态
	 * @param ids
	 * @param highUpState
	 * @return
	 */
	public String startOrStopHighUpById(List<String> ids,String highUpState);
	/**
	 * 根据状态和名称查询符合条件的挂高集合
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<HighUpVO>> queryHighUpVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
