package com.xunge.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

import com.xunge.model.SysAutoMenuVO;
import com.xunge.model.SysMenuVO;
import com.xunge.model.system.mannage.MenuTreeNodeVO;

/**
 * 功能菜单维护 Service 接口
 *
 */
public interface IMenuManagementService {
	
	/**
	 * 根据code单条查询
	 * @param paramMap
	 * @return
	 */
	public MenuTreeNodeVO queryMenuitemByCodeRedis(String mcode);
	
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	public List<MenuTreeNodeVO> queryMenuByConditionsRedis(String menuCode, String menuName);
	/**
	 * 查询顶级菜单
	 * @param menuId
	 * @return
	 */
	public List<SysAutoMenuVO> queryMenuIndexByRoleRedis(List<String> roleIds,String iUserType);
	
	/**
	 * 通过pmenuId查询同级菜单
	 * @param menuId
	 * @return
	 */
	public List<SysAutoMenuVO> querySecondMenuIndexByRole(List<String> roleIds,String pmenuCode, String menuParentCode);
	/**
	 * 获取整个菜单树
	 * @param 
	 * @return
	 */
	public List<MenuTreeNodeVO> queryFunctionMenuTreeRedis(String pmenuId);
	/**
	 * 保存菜单结点信息
	 * @param menuId
	 * @return
	 */
	public String insertMenuNode(SysMenuVO item);
	/**
	 * 修改菜单结点信息
	 * @param menuId
	 * @return
	 */
	public String updateMenuNode(SysMenuVO item);
	/**
	 * 启用菜单
	 * @param menuId
	 * @return
	 */
	public String updateStateOpenUse(List<String> idsList);
	/**
	 * 停用菜单
	 * @param menuId
	 * @return
	 */
	public String updateStateCloseUse(List<String> idsList);
	/**
	 * 删除菜单
	 * @param menuId
	 * @return
	 */
	public String deleteMenu(List<String> idsList);
	

    /**
     * 获取菜单访问权限
     * @param assertUrl
     * @return
     */
    public String queryUrlAuthor(List<String> roleIds, String assertUrl);
}
