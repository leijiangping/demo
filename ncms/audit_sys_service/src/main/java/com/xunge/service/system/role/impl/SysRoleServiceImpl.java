package com.xunge.service.system.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.PrvComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.CollectionUtil;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.system.role.ISysRoleDao;
import com.xunge.dao.system.user.ISysUserDao;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.rolemenu.RoleMenuVO;
import com.xunge.model.system.roleuser.RoleUserVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.util.CodeGeneratorUtil;
/**
 * 角色信息维护service实现类
 *
 */
public class SysRoleServiceImpl implements ISysRoleService {
	
	public ISysRoleDao sysRoleDao;
	
	private ISysUserDao sysUserDao;
	
	private ISysRegionDao sysRegionDao;
	
	public ISysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(ISysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}

	@Override
	public String updateRole(SystemRoleVO sysRoleVO, String roleId) {
		int result = sysRoleDao.updateRole(sysRoleVO, roleId);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}


	@Override
	public Page<List<SystemRoleVO>> queryAllRole(int roleState,int cur_page_num,int page_count) {
		roleState=StateComm.STATE__1;
		return this.sysRoleDao.queryAllRole(roleState,cur_page_num, page_count);
	}
	

	@Override
	public Page<List<SystemRoleVO>> queryAllSysRole(int pageNumber,int pageSize) {
		int state = StateComm.STATE_0;
		return this.sysRoleDao.queryAllSysRole(state,pageNumber,pageSize);
	}
	
	@Override
	public Page<List<SystemRoleVO>> querySysRoleByName(HttpServletRequest request,String roleCode,String RoleName,int cur_page_num,int page_count,String prvId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
	
		SystemRoleVO role=new SystemRoleVO();
		role.setPrvId(prvId);
		role.setRoleName(RoleName);
		role.setRoleCode(roleCode);
		role.setRoleState(StateComm.STATE_str__1);
		paramMap.put("role", role);
		return this.sysRoleDao.querySysRoleByName(paramMap,cur_page_num,page_count);
	}

	
	@Override
	public Page<List<SysUserVO>> queryAllUser(String prvId,int cur_page_num,int page_count) {
		 
		return this.getSysUserDao().querySysUser(prvId,cur_page_num,page_count);
	}
	
	@Override
	public String insertRole(HttpServletRequest request) {
		
		String roleName = request.getParameter("roleName");
		String roleNote=request.getParameter("roleNote");
		String prvId=request.getParameter("prvId");
		SystemRoleVO sysRoleVO=new SystemRoleVO();
		sysRoleVO.setRoleId(SysUUID.generator());
		sysRoleVO.setRoleName(roleName);
		sysRoleVO.setRoleCode(CodeGeneratorUtil.RoleCodeGet());
		sysRoleVO.setRoleState(StateComm.STATE_str0);
		sysRoleVO.setRoleNote(roleNote);
		sysRoleVO.setPrvId(prvId);
		int result = sysRoleDao.insertRole(sysRoleVO);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}
	
	@Override
	public String deleteRole(List<String> list) {
		List<String> idsList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			idsList.add(list.get(i));
		}
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE__1);
		int result = sysRoleDao.deleteRole(paramMap);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String openUse(List<String> list) {
		List<String> idsList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			idsList.add(list.get(i));
		}
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE_0);
		int result = sysRoleDao.updateState(paramMap);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String closeUse(List<String> list) {
		List<String> idsList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			idsList.add(list.get(i));
		}
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE_9);
		int result = sysRoleDao.updateState(paramMap);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String dispatchRoleToUser(HttpServletRequest request) {
		String roleId=request.getParameter("roleId");
		String userId=request.getParameter("userId");
		String[] strings =null;
		if(userId!="" && userId!=null){
			List<String>  listNew=new ArrayList<>();
			strings = userId.split(",");
			for(int i=0;i<strings.length;i++){
				listNew.add(strings[i]);
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId",listNew );
			paramMap.put("roleId",roleId );
			paramMap.put("relationState", StateComm.STATE_0);
			List<RoleUserVO> list = sysUserDao.queryUserRoleVOByUserId(paramMap);
			if(list.size()>0){
				List<String>  listOld=new ArrayList<>();
				for(int j=0;j<list.size();j++){
					String userid=list.get(j).getUserId();
					listOld.add(userid);
				}
				//找出需要删除的idsLists
				List<String> idsLists =new ArrayList<String>();
				for (int i = 0; i < listOld.size(); i++) {	
					if (listNew.contains(listOld.get(i))){
						idsLists.add(listOld.get(i));
					}
				}
				if(idsLists.size()>0){
					Map<String, Object> paramMaps = new HashMap<String, Object>();
					paramMaps.put("idsLists", idsLists);
					paramMaps.put("roleId", roleId);
					paramMaps.put("state", StateComm.STATE__1);
					sysRoleDao.deletePatchRoleUser(paramMaps);
				}
				
				List<String> insert =CollectionUtil.getDifferentList(listNew,listOld);
				if(insert.size()>0){
					List<RoleUserVO> idsList = new ArrayList<RoleUserVO>();
					RoleUserVO su=null;
					for(int i=0;i<insert.size();i++){
						su=new RoleUserVO();
						su.setRoleId(roleId);
						su.setUserId(insert.get(i));
						su.setRelationState(StateComm.STATE_0);
						su.setRoleUserId(SysUUID.generator());
						idsList.add(su);
						System.out.println(su.toString());
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("idsList", idsList);
					sysRoleDao.insertPatchRoleUser(map);
				}
				
			}else{
				List<RoleUserVO> idsList = new ArrayList<RoleUserVO>();
				RoleUserVO su=null;
				for(int i=0;i<listNew.size();i++){
					su=new RoleUserVO();
					su.setRoleId(roleId);
					su.setUserId(listNew.get(i));
					su.setRelationState(StateComm.STATE_0);
					su.setRoleUserId(SysUUID.generator());
					idsList.add(su);
					System.out.println(su.toString());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idsList", idsList);
				sysRoleDao.insertPatchRoleUser(map);
			}
			
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public List<SystemRoleVO> queryAllRoleById(String roleId) {
		List<SystemRoleVO> list = sysRoleDao.queryAllRoleById(roleId);
		return list;
	}

	

	public ISysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}
	@Override
	public String insertRoleMenu(String roleId, List<String> list) {
		try {
			
			sysRoleDao.delRoleMenu(roleId);
			List<RoleMenuVO> lstRoleMenuVO = new ArrayList<RoleMenuVO>();
			
			for (int i = 0; i < list.size(); i++) {
				String roleMenuId = SysUUID.generator();
				RoleMenuVO roleMenuVO = new RoleMenuVO(roleMenuId,roleId,list.get(i));
				lstRoleMenuVO.add(roleMenuVO);
			}
			Map<String, Object> rolemenuMap = new HashMap<String, Object>();
			rolemenuMap.put("lstRoleMenuVO", lstRoleMenuVO);
			sysRoleDao.insertRoleMenu(rolemenuMap);
			
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			e.printStackTrace();
			return RESULT.FAIL_0;
		}
	}

	@Override
	public List<String> queryMenuId(String roleId) {
		List<String> lsst = sysRoleDao.queryMenuId(roleId);
		return lsst;
	}
	
	@Override
	public List<String> queryMenuId(Map map) {
		List<String> lsst = sysRoleDao.queryMenuId(map);
		return lsst;
	}

	@Override
	public List<SystemRoleVO> queryAllRoleName(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return sysRoleDao.queryAllRoleName(paramMap);
	}

	@Override
	public List<SysProvinceVO> queryAllProvinceRedis(String prv_id) {
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("prvId", prv_id);
		paramMap.put("prvState",PrvComm.UPLINE_9);
		List<SysProvinceVO> list=sysRoleDao.queryAllProvince(paramMap);
		return list;
	}

	@Override
	public List<SysUserVO> queryUserByRoleName(List<String> roleIdLists,UserLoginInfo user) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("roleLists", roleIdLists);
		List<SystemRoleVO> roleIdList = sysRoleDao.queryRoleByRoleName(paramMaps);
		List<String> roleIds = new ArrayList<>();
		for (int i = 0; i < roleIdList.size(); i++) {
			roleIds.add(roleIdList.get(i).getRoleId());
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("roleIdLists", roleIds);
		paramMap.put("regId", user.getReg_id());
		List<SysUserVO> queryUserByRoleId = sysRoleDao.queryUserByRoleName(paramMap);
		return queryUserByRoleId;
	}
	@Override
	public List<SysUserVO> queryUserByRoleName(List<String> roleIdLists,UserLoginInfo user,String regId) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("roleLists", roleIdLists);
		List<SystemRoleVO> roleIdList = sysRoleDao.queryRoleByRoleName(paramMaps);
		List<String> roleIds = new ArrayList<>();
		for (int i = 0; i < roleIdList.size(); i++) {
			roleIds.add(roleIdList.get(i).getRoleId());
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("roleIdLists", roleIds);
		paramMap.put("regId", user.getReg_id());
		paramMap.put("prvId", user.getPrv_id());
		paramMap.put("dataRegId", regId);//如果数据的dataRegId为null，则为向下共享
		
		List<SysUserVO> queryUserByRoleId = sysRoleDao.queryUserByRoleName(paramMap);
		return queryUserByRoleId;
	}
	
	//  不加省份限制条件
	@Override
	public List<SysUserVO> queryUserByRoleName(List<String> roleIdLists) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("roleLists", roleIdLists);
		List<SystemRoleVO> roleIdList = sysRoleDao.queryRoleByRoleName(paramMaps);
		List<String> roleIds = new ArrayList<>();
		for (int i = 0; i < roleIdList.size(); i++) {
			roleIds.add(roleIdList.get(i).getRoleId());
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("roleIdLists", roleIds);
		List<SysUserVO> queryUserByRoleId = sysRoleDao.queryUserByRoleName(paramMap);
		return queryUserByRoleId;
	}
	
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	@Override
	public List<String> queryRoleNameById(List<String> list) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("roleLists", list);
		List<String> list2 = sysRoleDao.queryRoleNameById(paramMap);
		return list2;
	}

	@Override
	public List<SystemRoleVO> queryRoleByMenuId(String menuId,String prvId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("menuId", menuId);
		paraMap.put("roleState", StateComm.STATE_0);
		if(!StrUtil.isBlank(prvId)){
			paraMap.put("prvId",prvId);
		}
		List<SystemRoleVO> list = sysRoleDao.queryRoleByMenuId(paraMap);
		return list;
	}


}