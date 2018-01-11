package com.xunge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.dao.SysMenuDao;
import com.xunge.model.SysAutoMenuVO;
import com.xunge.model.SysMenuVO;
import com.xunge.model.system.mannage.MenuTreeNodeVO;
import com.xunge.service.IMenuManagementService;

/**
 * 功能菜单维护 Service 实现类
 *
 */
public class MenuManagementServiceImpl implements IMenuManagementService {
	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Override
	public MenuTreeNodeVO queryMenuitemByCodeRedis(String mcode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menu_code", mcode);
		param.put("menu_state", StateComm.STATE_0);
		return sysMenuDao.queryMenuitemByCode(param);
	}

	@Override
	public List<SysAutoMenuVO> queryMenuIndexByRoleRedis(List<String> roleIds,String iUserType) {
		//通过可操作菜单集合查询对应顶级菜单实体集合
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("role_ids", roleIds);
		param.put("menu_id", iUserType);
		param.put("menu_state", StateComm.STATE_0);
		// 查询所有子菜单
		List<SysAutoMenuVO> oriMenuList = sysMenuDao.queryMenuIndexByRole(param);
		// 拼接菜单
		List<SysAutoMenuVO> menuList = mergeMenuList(oriMenuList, iUserType);
		return menuList;
	}

	@Override
	public List<SysAutoMenuVO> querySecondMenuIndexByRole(List<String> roleIds,
			String parentId, String parentCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("role_ids", roleIds);
		param.put("pmenu_code", parentCode);
		param.put("menu_state", StateComm.STATE_0);
		// 查询所有子菜单
		List<SysAutoMenuVO> oriMenuList = sysMenuDao.querySecondMenuIndexByRole(param);
		// 拼接菜单
		List<SysAutoMenuVO> menuList = mergeMenuList(oriMenuList, parentId);
		return menuList;
	}
	
	private List<SysAutoMenuVO> mergeMenuList(List<SysAutoMenuVO> oriMList, String pmenuId){
		List<SysAutoMenuVO> menuList = new ArrayList<SysAutoMenuVO>();
		
		// 重新遍历的菜单list
		List<SysAutoMenuVO> reOriMList = new ArrayList<SysAutoMenuVO>();
		reOriMList.addAll(oriMList);
		
		if(oriMList.size()>0){
			for(SysAutoMenuVO item : oriMList){
				if(pmenuId.equals(item.getParentId())){
					// 去掉本元素
					reOriMList.remove(item);
					// 以自己的code作为父code，重新遍历
					item.setChildMenus(mergeMenuList(reOriMList, item.getId()));
					// 添加节点
					menuList.add(item);
				}
			}
		}
		return menuList;
	}
	
	@Override
	public List<MenuTreeNodeVO> queryFunctionMenuTreeRedis(String menuId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuId", menuId);
		List<MenuTreeNodeVO> menuList = sysMenuDao.querySystemMenuTree(paramMap);
		return menuList;
	}

    /**
     * 获取菜单访问权限
     * @param assertUrl
     * @return
     */
	@Override
    public String queryUrlAuthor(List<String> roleIds, String assertUrl) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleIds", roleIds);
		param.put("url", assertUrl);
    	int result = sysMenuDao.queryUrlAuthor(param);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
    }

	@Override
	public String deleteMenu(List<String> idsList) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE__1);
		int result = sysMenuDao.updateMenuStateBatch(paramMap);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateStateOpenUse(List<String> idsList) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE_0);
		int result = sysMenuDao.updateMenuStateBatch(paramMap);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateStateCloseUse(List<String> idsList) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE_9);
		int result = sysMenuDao.updateMenuStateBatch(paramMap);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public List<MenuTreeNodeVO> queryMenuByConditionsRedis(String menuCode, String menuName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("menuCode", menuCode);
		paramMap.put("menuName", menuName);
		List<MenuTreeNodeVO> list=sysMenuDao.queryMenuByConditions(paramMap);
		return list;
	}

	@Override
	public String insertMenuNode(SysMenuVO item) {
    	// 插入一条菜单信息
		int result = sysMenuDao.insertMenuNode(item);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateMenuNode(SysMenuVO menuItem) {
    	// 修改一条菜单信息
		int result = sysMenuDao.updateMenuNode(menuItem);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

}
