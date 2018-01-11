package com.xunge.dao;

import java.util.List;
import java.util.Map;

import com.xunge.model.SysAutoMenuVO;
import com.xunge.model.SysMenuVO;
import com.xunge.model.system.mannage.MenuTreeNodeVO;

public interface SysMenuDao {
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	List<MenuTreeNodeVO> queryMenuByConditions(Map<String, Object> paramMap);
	/**
	 * 根据code查询单条菜单
	 * @param paramMap
	 * @return
	 */
	MenuTreeNodeVO queryMenuitemByCode(Map<String, Object> paramMap);
	/**
	 * 根据paramMap获取同级菜单
	 * @param paramMap
	 * @return
	 */
	List<SysAutoMenuVO> queryMenuIndexByRole(Map<String, Object> paramMap);
	/**
	 * 根据paramMap获取同级菜单-用于自动生成左侧菜单
	 * @param paramMap
	 * @return
	 */
	List<SysAutoMenuVO> querySecondMenuIndexByRole(Map<String, Object> paramMap);
	/**
	 * 根据paramMap获取同级菜单结点 
	 * @param paramMap
	 * @return
	 */
	List<MenuTreeNodeVO> querySystemMenuTree(Map<String, Object> paramMap);
	/**
	 * 批量逻辑删除菜单项
	 * @param paramMap
	 * @return
	 */
	public int deleteMenuBatch(Map<String, Object> paramMap);
	/**
	 * 批量修改菜单项状态
	 * @param paramMap
	 * @return
	 */
	int updateMenuStateBatch(Map<String, Object> paramMap);
	/**
	 * 保存菜单结点信息
	 * @param menuId
	 * @return
	 */
	public int insertMenuNode(SysMenuVO menuItem);
	/**
	 * 修改菜单结点信息
	 * @param menuId
	 * @return
	 */
	public int updateMenuNode(SysMenuVO menuItem);
	/**
	 * 修改菜单结点信息
	 * @param menuId
	 * @return
	 */
	public int queryUrlAuthor(Map<String, Object> paramMap);
}
