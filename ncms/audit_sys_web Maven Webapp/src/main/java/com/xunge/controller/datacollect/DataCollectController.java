package com.xunge.controller.datacollect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.GrpComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.FeedBackObject;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DownZipUtil;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.core.util.UploadUtils;
import com.xunge.model.datacollect.GrpDatacollectHistoryVO;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;
import com.xunge.model.datacollect.GrpDatacollectVO;
import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;
import com.xunge.model.datacollect.GrpDatacollecttypeVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.ISysProvinceService;
import com.xunge.service.datacollect.IGrpDatacollectHistoryService;
import com.xunge.service.datacollect.IGrpDatacollectPrvService;
import com.xunge.service.datacollect.IGrpDatacollectService;
import com.xunge.service.datacollect.IGrpDatacollecttypePrvService;
import com.xunge.service.datacollect.IGrpDatacollecttypeService;
import com.xunge.service.sysSmsSendHistroy.ISysSmssendHistroyService;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.system.sms.ISmsSendTools;
import com.xunge.service.system.user.ISysUserService;

/**
 * 集团收集表controller
 * 
 * @author changwq
 * 
 */
@Controller
@SessionAttributes(value = { "user" }, types = { UserLoginInfo.class })
@RequestMapping("/asserts/tpl/countanalyse/groupdata")
public class DataCollectController extends BaseException {

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISmsSendTools smsSendTools;

	@Autowired
	private ISysProvinceService sysProvinceService;

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private IGrpDatacollectService grpDatacollectService;

	@Autowired
	private IGrpDatacollecttypeService grpDatacollecttypeService;

	@Autowired
	private IGrpDatacollectPrvService grpDatacollectPrvService;

	@Autowired
	private IGrpDatacollecttypePrvService grpDatacollecttypePrvService;

	@Autowired
	private ISysSmssendHistroyService sysSmssendHistroyService;
	
	@Autowired
	private IGrpDatacollectHistoryService grpDatacollectHistoryService;

	@Autowired
	private ILogService log;
	

	/**
	 * 集团收集表草稿页面条件查询
	 * 
	 * @param datacollectTitle
	 * @param datacollectState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryWiteDoGrpDataCollectVO")
	public @ResponseBody
	FeedBackObject queryWiteDoGrpDataCollectVO(String datacollectTitle,
			String order,String startDatacollectDeadline,String endDatacollectDeadline,
			int pageNumber, int pageSize)
			throws ParseException {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!StrUtil.isBlank(endDatacollectDeadline)) {
			startDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!StrUtil.isBlank(endDatacollectDeadline)) {
			endDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		List<Integer> datacollectState = new ArrayList<Integer>();
		datacollectState.add(GrpComm.SEND_0);
		paraMap.put("datacollectState",datacollectState);
		paraMap.put("datacollectTitle", datacollectTitle);
		paraMap.put("startDatacollectDeadline", startDatacollectDeadline);
		paraMap.put("endDatacollectDeadline", endDatacollectDeadline);
		paraMap.put("order",order);
		feedbk.Obj = grpDatacollectService.queryGrpDataCollectVO(paraMap,
				pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 集团收集表派发完成页面条件查询
	 * 
	 * @param datacollectTitle
	 * @param datacollectState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryDoGrpDataCollectVO")
	public @ResponseBody
	FeedBackObject queryDoGrpDataCollectVO(String datacollectTitle,String datacollectState,
			String order,String startDatacollectDeadline,String endDatacollectDeadline,
			String startDatacollectDate,String endDatacollectDate,
			int pageNumber, int pageSize)
			throws ParseException {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!StrUtil.isBlank(startDatacollectDeadline)) {
			startDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!StrUtil.isBlank(endDatacollectDeadline)) {
			endDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		if (!StrUtil.isBlank(startDatacollectDate)) {
			startDatacollectDate += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!StrUtil.isBlank(endDatacollectDate)) {
			endDatacollectDate += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		List<Integer> state = new ArrayList<Integer>();
		if(!StrUtil.isBlank(datacollectState)){
			state.add(Integer.parseInt(datacollectState));
		}else{
			state.add(GrpComm.SEND_1);
			state.add(GrpComm.SEND_11);
		}
		paraMap.put("datacollectState",state);
		paraMap.put("datacollectTitle", datacollectTitle);
		paraMap.put("startDatacollectDeadline", startDatacollectDeadline);
		paraMap.put("endDatacollectDeadline", endDatacollectDeadline);
		paraMap.put("startDatacollectDate", startDatacollectDate);
		paraMap.put("endDatacollectDate", endDatacollectDate);
		paraMap.put("order",order);
		feedbk.Obj = grpDatacollectService.queryGrpDataCollectVO(paraMap,
				pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 集团收集表领导页面条件查询
	 * 
	 * @param datacollectTitle
	 * @param datacollectState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryWatchGrpDataCollectVO")
	public @ResponseBody
	FeedBackObject queryWatchGrpDataCollectVO(String datacollectTitle,String datacollectState,
			String order,String startDatacollectDeadline,String endDatacollectDeadline,
			int pageNumber, int pageSize)
			throws ParseException {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!startDatacollectDeadline.isEmpty()) {
			startDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!endDatacollectDeadline.isEmpty()) {
			endDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		List<Integer> state = new ArrayList<Integer>();
		if(!StrUtil.isBlank(datacollectState)){
			state.add(Integer.parseInt(datacollectState));
		}else{
			state.add(GrpComm.SEND_1);
			state.add(GrpComm.SEND_11);
		}
		paraMap.put("datacollectState",state);
		paraMap.put("datacollectTitle", datacollectTitle);
		paraMap.put("startDatacollectDeadline", startDatacollectDeadline);
		paraMap.put("endDatacollectDeadline", endDatacollectDeadline);
		paraMap.put("order",order);
		feedbk.Obj = grpDatacollectService.queryGrpDataCollectVO(paraMap,
				pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 根据集团收集表id删除数据
	 * 
	 * @param loginUser
	 * @param datacollectId
	 * @return
	 */
	@RequestMapping(value = "/deleteByPrimaryKey")
	public @ResponseBody
	FeedBackObject deleteByPrimaryKey(@ModelAttribute("user") UserLoginInfo loginUser,HttpServletRequest request,
			String datacollectId) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String[] ids = datacollectId.split(PromptMessageComm.COMMA_SYMBOL);
		FeedBackObject feedbk = new FeedBackObject();
		try {
			for (int i = 0; i < ids.length; i++) {
				grpDatacollectService.deleteByPrimaryKey(ids[i]);
			}
		} catch (Exception e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.OPERATION_DELETE_FAILED+ PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
			return feedbk;
		}
		feedbk.success = RESULT.SUCCESS_1;
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.OPERATION_DELETE_SUCCESS + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}
		return feedbk;
	}

	/**
	 * 新增集团收集表数据，并同步省级
	 * 
	 * @param loginUser
	 * @param datacollectTitle
	 * @param datacollectDeadline
	 * @param datacollectNote
	 * @param prvIds
	 * @param typeIds
	 * @return
	 */
	@RequestMapping(value = "/insertSelective")
	public @ResponseBody
	FeedBackObject insertSelective(
			@ModelAttribute("user") UserLoginInfo loginUser,
			String datacollectTitle, String datacollectDeadline,
			String datacollectNote, String prvId, String id,
			String datacollectCopy) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String[] type = id.split(PromptMessageComm.COMMA_SYMBOL);
		List<String> typeIds = new ArrayList<String>();
		for (int i = 0; i < type.length; i++) {
			typeIds.add(type[i]);
		}
		String[] prv = prvId.split(PromptMessageComm.COMMA_SYMBOL);
		List<String> prvIds = new ArrayList<String>();
		for (int i = 0; i < prv.length; i++) {
			prvIds.add(prv[i]);
		}
		FeedBackObject feedbk = new FeedBackObject();
		// 处理数据
		GrpDatacollectVO grpDatacollectVO = new GrpDatacollectVO();// 封装集团收集表信息
		grpDatacollectVO.setDatacollectId(SysUUID.generator());// 创建id
		grpDatacollectVO.setDatacollectState(GrpComm.SEND_0);// 设置状态为未派发
		grpDatacollectVO.setDatacollectDeadline(datacollectDeadline);// 设置处理时限
		grpDatacollectVO.setDatacollectTitle(datacollectTitle);// 设置标题
		grpDatacollectVO.setDatacollectNote(datacollectNote);// 设置正文
		grpDatacollectVO.setDatacollectCopy(datacollectCopy);// 添加抄送人
		feedbk.success = grpDatacollectService.insertSelective(
				grpDatacollectVO, prvIds, typeIds);// 新增集团收集表数据
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.OPERATION_INSERT_SUCCESS + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			feedbk.Obj = grpDatacollectVO.getDatacollectId();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		} else {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.OPERATION_INSERT_FAILED + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}
		return feedbk;
	}

	/**
	 * 修改集团收集信息
	 * 
	 * @param loginUser
	 * @param datacollectId
	 * @param datacollectTitle
	 * @param datacollectDeadline
	 * @param datacollectNote
	 * @param prvIds
	 * @param typeIds
	 * @return
	 */
	@RequestMapping(value = "/updateByPrimaryKeySelective")
	public @ResponseBody
	FeedBackObject updateByPrimaryKeySelective(
			@ModelAttribute("user") UserLoginInfo loginUser,
			String datacollectId, String datacollectTitle,
			String datacollectDeadline, String datacollectNote, String prvId,
			String id, String datacollectCopy) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String[] type = id.split(PromptMessageComm.COMMA_SYMBOL);
		List<String> typeIds = new ArrayList<String>();
		for (int i = 0; i < type.length; i++) {
			typeIds.add(type[i]);
		}
		String[] prv = prvId.split(PromptMessageComm.COMMA_SYMBOL);
		List<String> prvIds = new ArrayList<String>();
		for (int i = 0; i < prv.length; i++) {
			prvIds.add(prv[i]);
		}
		FeedBackObject feedbk = new FeedBackObject();
		// 处理数据
		GrpDatacollectVO grpDatacollectVO = new GrpDatacollectVO();// 封装集团收集表信息
		grpDatacollectVO.setDatacollectId(datacollectId);// 创建id
		grpDatacollectVO.setDatacollectState(GrpComm.SEND_0);// 设置状态为未派发
		grpDatacollectVO.setDatacollectDeadline(datacollectDeadline);// 设置处理时限
		grpDatacollectVO.setDatacollectTitle(datacollectTitle);// 设置标题
		grpDatacollectVO.setDatacollectNote(datacollectNote);// 设置正文
		grpDatacollectVO.setDatacollectCopy(datacollectCopy);// 添加抄送人
		feedbk.success = grpDatacollectService.updateByPrimaryKeySelective(
				grpDatacollectVO, prvIds, typeIds);
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg =PromptMessageComm.OPERATION_UPDATE_SUCCESS + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		} else {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.OPERATION_UPDATE_FAILED + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}
		return feedbk;
	}

	/**
	 * 查询数据类型
	 * @param dictgroup_code
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryCollectType", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryCollectType(
			@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		String dictgroup_code = PromptMessageComm.DATACOLLECTTEMP_TYPE;
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = dictionaryService.queryDictionaryByCodeRedis(
				loginInfo.getPrv_id(), dictgroup_code);
		return feedbk;
	}

	/**
	 * 集团收集表派发
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/updateStateById", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateStateById(
			@ModelAttribute("user") UserLoginInfo loginInfo,
			String datacollectId) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//Map<String, Object> param = new HashMap<String, Object>();
		// 派发完成短信提醒
		/*
		 * param.put("mobiles","187****1939"); param.put("name","讯格测试人员-常文强");
		 * param.put("title","《测试集团下发数据》");
		 * smsSendTools.JTDataCollectSmsSend(param);
		 */
		String[] ids = datacollectId.split(PromptMessageComm.COMMA_SYMBOL);
		List<String> datacollectIds = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			datacollectIds.add(ids[i]);
		}
		String jtLookMenuId = PromptMessageComm.JT_LOOKMENU_CODE;
		String prvMenuId = PromptMessageComm.PRV_MENU_CODE;
		String prvLookMenuId = PromptMessageComm.PRV_LOOKMENU_CODE;
		// 根据菜单id查询角色对象集合
		List<SystemRoleVO> jtList = sysRoleService
				.queryRoleByMenuId(jtLookMenuId,null);// 集团领导角色
		List<SystemRoleVO> prvList = sysRoleService
				.queryRoleByMenuId(prvMenuId,null);// 省级上报操作人员角色
		List<SystemRoleVO> prvLeaderList = sysRoleService
				.queryRoleByMenuId(prvLookMenuId,null);// 省级上报领导角色
		FeedBackObject feedbk = new FeedBackObject();
		PropertiesLoader prop = new PropertiesLoader(
				"\\properties\\sysConfig.properties");
		feedbk.success = RESULT.SUCCESS_1;
		try {
			for (int i = 0; i < datacollectIds.size(); i++) {
				GrpDatacollectVO grpDatacollectVO = grpDatacollectService
						.queryTitleById(datacollectIds.get(i));
				// 修改集团收集表状态
				grpDatacollectService.updateStateById(loginInfo.getUser_name(),
						datacollectIds.get(i),loginInfo.getUser_id(),grpDatacollectVO.getDatacollectTitle());
			}
		} catch (Exception e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.USER_SENDMSG_TO_PRV_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
			return feedbk;
		}
		for (int i = 0; i < datacollectIds.size(); i++) {
			// 获取集团收集标题
			GrpDatacollectVO grpDatacollectVO = grpDatacollectService
					.queryTitleById(datacollectIds.get(i));
			String title1 = grpDatacollectVO.getDatacollectTitle()
					.replaceAll(" ", "");
			String title = null;
			try {
				title = URLEncoder.encode(title1, PromptMessageComm.UTF_8);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 获取抄送的集团用户
			String copyUsers = grpDatacollectService
					.queryCopyUserById(datacollectIds.get(i));
			if (!StrUtil.isBlank(copyUsers)) {
				String[] userIds = copyUsers.split(PromptMessageComm.COMMA_SYMBOL);
				try {
					for (int a = 0; a < userIds.length; a++) {
						SysUserVO user = sysUserService
								.queryUserByUserId(userIds[a]);
						// 派发完成短信提醒
						// 抄送领导的模板id
						String tempid = prop
								.getProperty("jt_datacollect_send_leaderandcopy_tempid");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("tempid", tempid);
						map.put("mobiles", user.getUserPhone());
						map.put("title", title);
						map.put("bussTableId", datacollectIds.get(i));
						map.put("roleId", jtList.get(0).getRoleId());
						map.put("userId", userIds[a]);
						map.put("prvId", null);
						map.put("user", loginInfo);
						sysSmssendHistroyService.grpSendMsg(map);
					}
				} catch (Exception e) {
					feedbk.success = RESULT.SUCCESS_1;
					feedbk.msg = PromptMessageComm.USER_SENDMSG_TO_PRV_SUCCESS_BUT_SMS_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
					// 添加系统日志
					log.err(SysLogComm.LOG_Error, feedbk.msg);
					return feedbk;
				}
			}
			// 下发的省份id集合
			List<String> prvIdList = grpDatacollectPrvService
					.queryPrvIdByCollId(datacollectIds.get(i));
			// 根据省份id查询可以接受到短信的用户集合
			for (int j = 0; j < prvIdList.size(); j++) {
				String prvId = prvIdList.get(j);
				// 寻找有省份上报编辑页面权限的角色用户
				for (int a = 0; a < prvList.size(); a++) {
					if (prvId.equals(prvList.get(a).getPrvId())) {
						// 查询所有拥有省级上报编辑页面权限的用户
						List<SysUserVO> userList = sysUserService.queryUserByRole(prvList.get(a).getRoleId());
						try {
							for (int b = 0; b < userList.size(); b++) {//***
								// 派发完成短信提醒
								String tempid = prop
										.getProperty("jt_datacollect_send_provincebuz_tempid");
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("tempid", tempid);
								map.put("mobiles", userList.get(b)
										.getUserPhone());
								map.put("title", title);
								map.put("bussTableId", datacollectIds.get(i));
								map.put("roleId", prvList.get(a).getRoleId());
								map.put("userId", userList.get(b).getUserId());
								map.put("prvId", prvId);
								map.put("user", loginInfo);
								sysSmssendHistroyService.grpSendMsg(map);
							}
						} catch (Exception e) {
							feedbk.success = RESULT.SUCCESS_1;
							feedbk.msg = PromptMessageComm.USER_SENDMSG_TO_PRV_SUCCESS_BUT_PRVSMS_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
							// 添加系统日志
							log.err(SysLogComm.LOG_Error, feedbk.msg);
							return feedbk;
						}
					}
				}
				// 寻找有省份上报查询页面权限的角色用户
				for (int a = 0; a < prvLeaderList.size(); a++) {
					if (prvId.equals(prvLeaderList.get(a).getPrvId())) {
						// 查询所有拥有省级领导权限的用户
						List<SysUserVO> userList = sysUserService
								.queryUserByRole(prvLeaderList.get(a)
										.getRoleId());
						try {
							for (int b = 0; b < userList.size(); b++) {
								// 派发完成短信提醒
								String tempid = prop
										.getProperty("jt_datacollect_send_provinceleader_tempid");
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("tempid", tempid);
								map.put("mobiles", userList.get(b)
										.getUserPhone());
								map.put("title", title);
								map.put("bussTableId", datacollectIds.get(i));
								map.put("roleId", prvLeaderList.get(a)
										.getRoleId());
								map.put("userId", userList.get(b).getUserId());
								map.put("prvId", prvId);
								map.put("user", loginInfo);
								sysSmssendHistroyService.grpSendMsg(map);
							}
						} catch (Exception e) {
							feedbk.success = RESULT.SUCCESS_1;
							feedbk.msg = PromptMessageComm.USER_SENDMSG_TO_PRV_SUCCESS_BUT_PRVBUSSMS_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
							// 添加系统日志
							log.err(SysLogComm.LOG_Error, feedbk.msg);
							return feedbk;
						}
					}
				}
			}
		}
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.USER_SENDMSG_TO_PRV_SUCCESS + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}
		return feedbk;
	}
	/**
	 * 集团完结工单-并对应修改各省公司状态为已完结
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/updateStateToFinish", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateStateToFinish(String datacollectId,@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = grpDatacollectService.updateDatacollectToFinish(datacollectId);
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			try {
				feedbk.success = grpDatacollectPrvService.updatePrvStateToFinish(datacollectId,loginInfo.getUser_id());
			} catch (ParseException e) {
				feedbk.success = RESULT.SUCCESS_1;
				feedbk.msg = PromptMessageComm.USER_FINISH_ORDER_SUCCESS_BUT_FINISHPRV_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
				// 添加系统日志
				log.err(SysLogComm.LOG_Error, feedbk.msg);
				return feedbk;
			}
		}
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.USER_FINISH_ORDER_SUCCESS + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		} else {
			feedbk.msg = PromptMessageComm.USER_FINISH_ORDER_FAILED + PromptMessageComm.SYMBOL1+ loginInfo.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}
		return feedbk;
	}
	/**
	 * 查询可以抄送的用户信息
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryCopyUser", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryCopyUser() {
		FeedBackObject feedbk = new FeedBackObject();
		// 可以看到下面菜单的用户需要抄送
		String jtLookMenuId = PromptMessageComm.JT_LOOKMENU_CODE;
		List<SysUserVO> users = new ArrayList<SysUserVO>();
		// 根据菜单id查询角色对象集合
		List<SystemRoleVO> jtList = sysRoleService
				.queryRoleByMenuId(jtLookMenuId,null);// 集团领导角色
		for (int i = 0; i < jtList.size(); i++) {
			List<SysUserVO> userList = sysUserService.queryUserByRole(jtList
					.get(i).getRoleId());
			for (SysUserVO u : userList) {
				users.add(u);
			}
		}
		feedbk.Obj = users;
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}

	/**
	 * 根据集团收集编码查询短信下发状态(分页)
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryHistroyByCollId", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryHistroyByCollId(String grpDatacollectId,
			int pageNumber, int pageSize) {
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = sysSmssendHistroyService.queryHistroyByCollId(
				grpDatacollectId, pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}

	/**
	 * 集团驳回省份上报信息
	 * 
	 * @return
	 * @author changwq
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/updateStateReject", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateStateReject(
			@ModelAttribute("user") UserLoginInfo loginInfo,
			String datacollectGroupOpinion, String datacollectId, String prvId){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		// 获取集团收集标题
		GrpDatacollectVO grpDatacollectVO = grpDatacollectService
				.queryTitleById(datacollectId);
		String title1 = grpDatacollectVO.getDatacollectTitle().replaceAll(" ",
				"");
		String title = null;
		try {
			title = URLEncoder.encode(title1, PromptMessageComm.UTF_8);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		PropertiesLoader prop = new PropertiesLoader(
				"\\properties\\sysConfig.properties");
		// 页面id
		String prvMenuId = PromptMessageComm.PRV_MENU_CODE;
		String prvLookMenuId = PromptMessageComm.PRV_LOOKMENU_CODE;
		// 根据菜单id查询角色对象集合
		List<SystemRoleVO> prvList = sysRoleService
				.queryRoleByMenuId(prvMenuId,null);// 省级上报操作人员角色
		List<SystemRoleVO> prvLeaderList = sysRoleService
				.queryRoleByMenuId(prvLookMenuId,null);// 省级上报领导角色
		// 驳回上报
		try {
			feedbk.success = grpDatacollectPrvService.updateStateReject(
					datacollectGroupOpinion, datacollectId, prvId,loginInfo.getUser_id());
		} catch (ParseException e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.JT_REJECT_ORDER_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
			return feedbk;
		}
		try {
			for (int a = 0; a < prvLeaderList.size(); a++) {
				if (prvId.equals(prvLeaderList.get(a).getPrvId())) {
					// 查询所有拥有省级领导权限的用户
					List<SysUserVO> userList = sysUserService
							.queryUserByRole(prvLeaderList.get(a).getRoleId());
					for (int b = 0; b < userList.size(); b++) {
						// 驳回完成短信提醒
						String tempid = prop
								.getProperty("jt_datacollect_reject_provinceleader_tempid");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("tempid", tempid);
						map.put("mobiles", userList.get(b).getUserPhone());
						map.put("title", title);
						map.put("bussTableId", datacollectId);
						map.put("roleId", prvLeaderList.get(a).getRoleId());
						map.put("userId", userList.get(b).getUserId());
						map.put("prvId", prvId);
						map.put("user", loginInfo);
						sysSmssendHistroyService.grpSendMsg(map);
					}
				}
			}
		} catch (Exception e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.JT_REJECT_ORDER_SUCCESS_BUT_SMS_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
			return feedbk;
		}
		try {
			//发送该工单对应业务员
			GrpDatacollectPrvVO g = grpDatacollectPrvService
					.queryPrvSelfPathAndName(datacollectId, prvId);
			String datacollectPrvId = g.getDatacollectPrvId();
			String userId = grpDatacollectPrvService
					.queryUserIdByPrvId(datacollectPrvId);
			SysUserVO user = sysUserService.queryUserInfoRedis(userId);
			// 派发完成短信提醒
			String tempid = prop
					.getProperty("jt_datacollect_reject_provincebuz_tempid");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tempid", tempid);
			map.put("mobiles", user.getUserPhone());
			map.put("title", title);
			map.put("bussTableId", datacollectId);
			map.put("userId", userId);
			map.put("prvId", prvId);
			map.put("user", loginInfo);
			sysSmssendHistroyService.grpSendMsg(map);
		} catch (Exception e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.JT_REJECT_ORDER_SUCCESS_BUT_PRVBUSSMS_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
			return feedbk;
		}
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.JT_REJECT_ORDER_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}
		return feedbk;
	}

	/**
	 * 查询所有省份名称以及id
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryAllProvince", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryAllProvince() {
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = sysProvinceService.queryAllSimpleProvince();
		return feedbk;
	}

	/**
	 * 根据集团收集id查询详情
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryGrpDataCollectById")
	public @ResponseBody
	FeedBackObject queryGrpDataCollectById(String datacollectId) {
		FeedBackObject feedbk = new FeedBackObject();
		// 添加抄送人姓名和id
		feedbk.Obj = grpDatacollectService
				.queryGrpDataCollectById(datacollectId);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}

	/**
	 * 根据集团收集id查询省级上报情况
	 * 
	 * @return
	 * @author changwq 
	 */
	@RequestMapping(value = "/queryGrpPrvCollectById")
	public @ResponseBody
	FeedBackObject queryGrpPrvCollectById(String datacollectId) {
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = grpDatacollectService
				.queryGrpPrvCollectById(datacollectId);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}

	/**
	 * 省级上报操作页面条件查询
	 * 
	 * @param datacollectTitle
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryDoDataCollectPrvVO")
	public @ResponseBody
	FeedBackObject queryDoDataCollectPrvVO(
			@ModelAttribute("user") UserLoginInfo loginInfo,String order,
			String datacollectTitle, String datacollectPrvState,
			String startDatacollectDeadline, String endDatacollectDeadline,
			int pageNumber, int pageSize) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!StrUtil.isBlank(startDatacollectDeadline)) {
			startDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!StrUtil.isBlank(endDatacollectDeadline)) {
			endDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		List<Integer> state = new ArrayList<Integer>();
		if(!StrUtil.isBlank(datacollectPrvState)){
			Integer prvState = Integer.parseInt(datacollectPrvState);
			state.add(prvState);
		}else{
			state.add(GrpComm.COMMIT_0);
			state.add(GrpComm.COMMIT_9);
			state.add(GrpComm.COMMIT__1);
		}
		paraMap.put("userId",loginInfo.getUser_id());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("datacollectTitle", datacollectTitle);
		paraMap.put("state",state);
		paraMap.put("startDatacollectDeadline", startDatacollectDeadline);
		paraMap.put("endDatacollectDeadline", endDatacollectDeadline);
		paraMap.put("order",order);
		feedbk.Obj = grpDatacollectPrvService.queryDataCollectPrvVO(paraMap,
				pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 省级上报结果页面条件查询
	 * 
	 * @param datacollectTitle
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryWiteDataCollectPrvVO")
	public @ResponseBody
	FeedBackObject queryWiteDataCollectPrvVO(
			@ModelAttribute("user") UserLoginInfo loginInfo,String order,
			String datacollectTitle, String datacollectPrvState,
			String startDatacollectDeadline, String endDatacollectDeadline,
			int pageNumber, int pageSize) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!StrUtil.isBlank(startDatacollectDeadline)) {
			startDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!StrUtil.isBlank(endDatacollectDeadline)) {
			endDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		List<Integer> state = new ArrayList<Integer>();
		if(!StrUtil.isBlank(datacollectPrvState)){
			Integer prvState = Integer.parseInt(datacollectPrvState);
			state.add(prvState);
		}else{
			state.add(GrpComm.COMMIT_1);
			state.add(GrpComm.COMMIT_11);
		}
		paraMap.put("userId",loginInfo.getUser_id());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("datacollectTitle", datacollectTitle);
		paraMap.put("state",state);
		paraMap.put("startDatacollectDeadline", startDatacollectDeadline);
		paraMap.put("endDatacollectDeadline", endDatacollectDeadline);
		paraMap.put("order",order);
		feedbk.Obj = grpDatacollectPrvService.queryDataCollectPrvVO(paraMap,
				pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 省级上报领导页面条件查询
	 * 
	 * @param datacollectTitle
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryWatchDataCollectPrvVO")
	public @ResponseBody
	FeedBackObject queryWatchDataCollectPrvVO(
			@ModelAttribute("user") UserLoginInfo loginInfo,String order,
			String datacollectTitle, String datacollectPrvState,
			String startDatacollectPrvDate, String endDatacollectPrvDate,
			String startDatacollectDeadline, String endDatacollectDeadline,
			int pageNumber, int pageSize) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (!StrUtil.isBlank(startDatacollectDeadline)) {
			startDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_BEFORE;
		}
		if (!StrUtil.isBlank(endDatacollectDeadline)) {
			endDatacollectDeadline += PromptMessageComm.PARAMETER_TIME_AFTER;
		}
		List<Integer> state = new ArrayList<Integer>();
		if(!StrUtil.isBlank(datacollectPrvState)){
			Integer prvState = Integer.parseInt(datacollectPrvState);
			state.add(prvState);
		}else{
			state.add(GrpComm.COMMIT_9);
			state.add(GrpComm.COMMIT__1);
			state.add(GrpComm.COMMIT_1);
			state.add(GrpComm.COMMIT_11);
		}
		paraMap.put("userId",loginInfo.getUser_id());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("datacollectTitle", datacollectTitle);
		paraMap.put("state",state);
		paraMap.put("startDatacollectDeadline", startDatacollectDeadline);
		paraMap.put("endDatacollectDeadline", endDatacollectDeadline);
		paraMap.put("order",order);
		feedbk.Obj = grpDatacollectPrvService.queryDataCollectPrvVO(paraMap,
				pageNumber, pageSize);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 根据省份prvid和集团收集编码查询上报数据
	 * 
	 * @return
	 * @author changwq 
	 */
	@RequestMapping(value = "/queryEveryPrvMsg")
	public @ResponseBody
	FeedBackObject queryEveryPrvMsg(String datacollectId, String prvId) {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> resMap = new HashMap<String, Object>();
		GrpDatacollectPrvVO grpDatacollectPrvVO = grpDatacollectPrvService
				.queryPrvSelfPathAndName(datacollectId, prvId);
		List<GrpDatacollecttypePrvVO> list = grpDatacollectService
				.queryEveryPrvMsg(datacollectId, prvId);
		List<GrpDatacollectHistoryVO> hisList = grpDatacollectHistoryService.queryHistoryByPrvId(grpDatacollectPrvVO.getDatacollectPrvId());
		resMap.put("grpDatacollectPrvVO", grpDatacollectPrvVO);
		resMap.put("list", list);
		resMap.put("hisList",hisList);
		feedbk.Obj = resMap;
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	/**
	 * 设置工单负责人为登录用户
	 * 
	 * @return
	 * @author changwq
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/updateGrpToUserSelf")
	public @ResponseBody
	FeedBackObject updateGrpToUserSelf(@ModelAttribute("user") UserLoginInfo loginInfo,
			String datacollectPrvId){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		PropertiesLoader prop = new PropertiesLoader(
				"\\properties\\sysConfig.properties");
		FeedBackObject feedbk = new FeedBackObject();
		try {
			feedbk.success = grpDatacollectPrvService.updateGrpToUserSelf(
					GrpComm.COMMIT_9, loginInfo.getUser_name(),
					loginInfo.getUser_id(), datacollectPrvId);
		} catch (Exception e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.USER_SIGN_ORDER_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
			return feedbk;
		}
		String tempid = prop.getProperty("jt_datacollect_reject_provincebuz_other_tempid");
		//集团派发时间
		String date2 = grpDatacollectService.querySendDateByPrvId(datacollectPrvId);
		String title1 = date2.replaceAll(" ", "-");
		String date = null;
		try {
			date = URLEncoder.encode(title1, PromptMessageComm.UTF_8);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 页面id
		String prvMenuId = PromptMessageComm.PRV_MENU_CODE;
		String prvId = loginInfo.getPrv_id();
		// 根据菜单id查询角色对象集合
		List<SystemRoleVO> prvList = sysRoleService
				.queryRoleByMenuId(prvMenuId,prvId);// 省级上报操作人员角色
		for(int a=0;a<prvList.size();a++){
			List<SysUserVO> userList = sysUserService.queryUserByRole(prvList.get(a).getRoleId());
			try {
				for (int b = 0; b < userList.size(); b++) {
					if (!(loginInfo.getUser_id().equals(userList.get(b)
							.getUserId()))) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("tempid", tempid);
						map.put("mobiles", userList.get(b).getUserPhone());
						map.put("time", date);
						map.put("bussTableId", datacollectPrvId);
						map.put("userId", userList.get(b).getUserId());
						map.put("prvId", loginInfo.getPrv_id());
						map.put("user", loginInfo);
						sysSmssendHistroyService.grpSendMsg(map);
					}
				}
			} catch (Exception e) {
				feedbk.success = RESULT.FAIL_0;
				feedbk.msg = PromptMessageComm.USER_SIGN_ORDER_SUCCESS_BUT_SMS_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
				// 添加系统日志
				log.err(SysLogComm.LOG_Error, feedbk.msg);
				return feedbk;
			}
		}
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.USER_SIGN_ORDER_SUCCESS + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}
		return feedbk;
	}
	/**
	 * 上报
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/updateDataPrvById")
	public @ResponseBody
	FeedBackObject updateDataPrvById(
			@ModelAttribute("user") UserLoginInfo loginInfo,
			String datacollectPrvId) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String[] ids = datacollectPrvId.split(PromptMessageComm.COMMA_SYMBOL);
		List<String> datacollectPrvIds = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			datacollectPrvIds.add(ids[i]);
		}
		FeedBackObject feedbk = new FeedBackObject();
		try {
			for (int i = 0; i < datacollectPrvIds.size(); i++) {
				// 判断集团下发文件是否上传完毕
				List<GrpDatacollecttypePrvVO> grpDatacollecttypePrvList = grpDatacollecttypePrvService
						.queryPrvUploadNameById(datacollectPrvIds.get(i));
				for (int j = 0; j < grpDatacollecttypePrvList.size(); j++) {
					//获取省份上报信息的上传文件路径和模板id
					GrpDatacollecttypePrvVO grpDatacollecttypePrvVO = grpDatacollecttypePrvList.get(j);
					String typeId = grpDatacollecttypePrvVO.getDatacollecttypeId();
					String fileName = grpDatacollecttypePrvVO.getDatacollecttypePrvFilename();
					String tempType = grpDatacollecttypeService.queryTempTypeByTypeId(typeId);
					if(!(PromptMessageComm.OTHER.equals(tempType)) && StrUtil.isBlank(fileName)){
						feedbk.success = RESULT.FAIL_0;
						feedbk.msg = PromptMessageComm.MODEL_HASNOT_UPLOAD_FINISH + loginInfo.getUser_loginname();
						// 添加系统日志
						log.err(SysLogComm.LOG_Operate, feedbk.msg);
						return feedbk;
					}
				}
				grpDatacollectPrvService.updateDataPrvById(
						datacollectPrvIds.get(i),loginInfo.getUser_id());
			}
		} catch (Exception e) {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.USER_UPLOAD_MSG_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
			return feedbk;
		}
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = PromptMessageComm.USER_UPLOAD_MSG_SUCCESS + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
		// 添加系统日志
		log.info(SysLogComm.LOG_Operate, feedbk.msg);
		return feedbk;
	}

	/**
	 * 根据省份上报信息id查询上报数据
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryCollectPrvById")
	public @ResponseBody
	FeedBackObject queryCollectPrvById(String datacollectPrvId) {
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = grpDatacollectPrvService
				.queryCollectPrvById(datacollectPrvId);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}

	/**
	 * 集团收集-新增页面-数据类型新增按钮
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/insertDataType")
	public @ResponseBody
	FeedBackObject insertDataType(String datacollectId) {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> typeList = new ArrayList<String>();
		List<String[]> a = GrpComm.DATATYPE;
		for (int i = 0; i < a.size(); i++) {
			String type = a.get(i)[0];
			typeList.add(type);
		}
		// 尚未被关联的
		List<String> str = grpDatacollecttypeService.queryTypeBeUsed();
		for (int i = 0; i < str.size(); i++) {
			typeList = getTypeList(typeList, str.get(i));
		}
		if (datacollectId != null) {
			// 已经被关联的
			List<String> strr = grpDatacollecttypeService
					.queryTypeBeUsedById(datacollectId);
			for (int i = 0; i < strr.size(); i++) {
				typeList = getTypeList(typeList, strr.get(i));
			}
		}
		map.put("typeList", typeList);
		map.put("id", SysUUID.generator());
		GrpDatacollecttypeVO grpDatacollecttypeVO = new GrpDatacollecttypeVO();
		grpDatacollecttypeVO.setDatacollecttypeId(map.get("id") + "");
		// 新增集团收集数据类型
		feedbk.success = grpDatacollectService
				.insertSelective(grpDatacollecttypeVO);
		feedbk.Obj = map;
		return feedbk;
	}

	private List<String> getTypeList(List<String> list, String str) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(str) && !list.get(i).equals(PromptMessageComm.DATA_TYPE_OTHER)) {
				list.remove(i);
				break;
			}
		}
		return list;
	}

	/**
	 * 集团收集-新增页面-根据数据类型查询路径，名称
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/queryPathName")
	public @ResponseBody
	FeedBackObject queryPathName(String id, String type) {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String[]> a = GrpComm.DATATYPE;
		for (int i = 0; i < a.size(); i++) {
			if (type.equals(a.get(i)[0])) {
				map.put("path", a.get(i)[1]);
				map.put("name", a.get(i)[2]);
				break;
			}
		}
		GrpDatacollecttypeVO grpDatacollecttypeVO = new GrpDatacollecttypeVO();
		grpDatacollecttypeVO.setDatacollecttypeId(id);
		grpDatacollecttypeVO.setDatacollecttempType(type);
		grpDatacollecttypeVO.setDatacollecttempFilepath(map.get("path") + "");
		grpDatacollecttypeVO.setDatacollecttempFilename(map.get("name") + "");
		// 修改集团收集数据类型
		feedbk.success = grpDatacollectService
				.updateByPrimaryKeySelective(grpDatacollecttypeVO);
		feedbk.Obj = map;
		return feedbk;
	}

	/**
	 * 集团收集-新增页面-根据数据类型查询路径，名称
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/updateFileName")
	public @ResponseBody
	FeedBackObject updateFileName(String id, String dataMoudle) {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> map = new HashMap<String, Object>();
		GrpDatacollecttypeVO grpDatacollecttypeVO = new GrpDatacollecttypeVO();
		grpDatacollecttypeVO.setDatacollecttypeId(id);
		grpDatacollecttypeVO.setDatacollecttempFilename(dataMoudle);
		// 修改集团收集数据类型
		feedbk.success = grpDatacollectService
				.updateByPrimaryKeySelective(grpDatacollecttypeVO);
		feedbk.Obj = map;
		return feedbk;
	}

	/**
	 * 上传表格文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadPrvFile")
	public @ResponseBody
	FeedBackObject uploader(HttpServletRequest request) {
		FeedBackObject feedbk = new FeedBackObject();
		UploadUtils upload = new UploadUtils();
		upload.setDirName(PromptMessageComm.UPLOAD_FILES);

		UserLoginInfo userLoginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if (userLoginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prvCode = userLoginInfo.getPrv_code();
		if (StrUtil.isBlank(prvCode)) {
			prvCode = PromptMessageComm.PRV_CODE_JT;
		}
		/*
		 * if(StrUtil.isNotBlank(paraId)){s prvCode=paraId+"/"+prvCode; }
		 */
		Map<String, Object> resultMap = upload.uploadFile(request, null,
				prvCode);
		if (resultMap != null && resultMap.get(PromptMessageComm.UPLOAD_ERR) != null
				&& !resultMap.get(PromptMessageComm.UPLOAD_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件验证失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = resultMap.get(PromptMessageComm.UPLOAD_ERR).toString();
		}
		// 取业务主键
		Map<String, Object> map = upload.getFields();
		String paraId = null;
		if (map != null && map.get("datacollecttypePrvId") != null) {
			paraId = (String) map.get("datacollecttypePrvId");
		}
		if (map != null && map.get("datacollecttypeId") != null) {
			paraId = (String) map.get("datacollecttypeId");
		}
		if (map != null && map.get(PromptMessageComm.UPLOAD_SAVE_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件上传保存失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString();
		}
		String datacollecttypePrvId = paraId;
		// 处理数据
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("datacollecttypePrvId", datacollecttypePrvId);
		paraMap.put("datacollecttypePrvFilename", resultMap.get("name"));
		paraMap.put("datacollecttypePrvFilepath", resultMap.get("path"));
		// 修改集团收集数据类型
		if(!(RESULT.FAIL_0.equals(feedbk.success))){
			feedbk.success = grpDatacollecttypePrvService.updatePrvFiles(paraMap);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("datacollecttypePrvId", datacollecttypePrvId);
		resMap.put("name", resultMap.get("name"));
		feedbk.Obj = resMap;
		return feedbk;
	}

	/**
	 * 上传省级其他文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadOtherPrvFile")
	public @ResponseBody
	FeedBackObject uploadPrvFile(HttpServletRequest request) {
		FeedBackObject feedbk = new FeedBackObject();
		UploadUtils upload = new UploadUtils();
		upload.setDirName(PromptMessageComm.UPLOAD_FILES);
		UserLoginInfo userLoginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if(userLoginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prvCode = userLoginInfo.getPrv_code();
		if (StrUtil.isBlank(prvCode)) {
			prvCode = PromptMessageComm.PRV_CODE_JT;
		}
		Map<String, Object> map = upload.uploadFile(request, null, prvCode);
		if (map != null && map.get(PromptMessageComm.UPLOAD_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件验证失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_ERR).toString();
		}
		if (map != null && map.get(PromptMessageComm.UPLOAD_SAVE_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件上传保存失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString();
		}
		String datacollectPrvId = request.getParameter("datacollectPrvId");
		// 修改集团收集数据类型
		if(!(RESULT.FAIL_0.equals(feedbk.success))){
			feedbk.success = grpDatacollectPrvService.updatePrvOtherFile(
					map.get("path").toString(), map.get("name").toString(),
					datacollectPrvId);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("datacollectPrvId", datacollectPrvId);
		resMap.put("name", map.get("name"));
		feedbk.Obj = resMap;
		return feedbk;
	}

	/**
	 * 省级上报信息保存（上报备注，其他上报文件路径，名字）
	 * 
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/saveCollectPrv")
	public @ResponseBody
	FeedBackObject saveCollectPrv(
			@ModelAttribute("user") UserLoginInfo loginInfo,
			String datacollectPrvNote, String datacollectPrvOtherfilepath,
			String datacollectPrvOtherfilename, String datacollectPrvId) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = grpDatacollectPrvService
				.updatePrvFileAndNote(datacollectPrvNote,
						datacollectPrvOtherfilepath,
						datacollectPrvOtherfilename, datacollectPrvId,
						GrpComm.COMMIT_9);
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = PromptMessageComm.USER_SEND_PRVMSG_SUCCESS + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		} else {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = PromptMessageComm.USER_SEND_PRVMSG_FAILED + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}
		return feedbk;
	}

	/**
	 * 集团其他文件上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadFile")
	public @ResponseBody
	FeedBackObject uploadFile(HttpServletRequest request) {
		FeedBackObject feedbk = new FeedBackObject();
		UserLoginInfo userLoginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if(userLoginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prvCode = userLoginInfo.getPrv_code();
		if (StrUtil.isBlank(prvCode)) {
			prvCode = PromptMessageComm.PRV_CODE_JT;
		}

		UploadUtils upload = new UploadUtils();
		upload.setDirName(PromptMessageComm.UPLOAD_FILES);

		Map<String, Object> map = upload.uploadFile(request, null, prvCode);
		if (map != null && map.get(PromptMessageComm.UPLOAD_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件验证失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_ERR).toString();
		}
		if (map != null && map.get(PromptMessageComm.UPLOAD_SAVE_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件上传保存失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString();
		}

		Map<String, Object> mapFields = upload.getFields();
		String paraId = null;
		if (mapFields != null && mapFields.get("datacollecttypePrvId") != null) {
			paraId = (String) mapFields.get("datacollecttypePrvId");
		}
		if (mapFields != null && mapFields.get("datacollecttypeId") != null) {
			paraId = (String) mapFields.get("datacollecttypeId");
		}
		// 处理数据
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("datacollecttypeId", paraId);
		paraMap.put("datacollecttempFilepath", map.get("path").toString());
		paraMap.put("datacollecttempFilename", map.get("name").toString());
		// 修改集团收集数据类型
		if(!(RESULT.FAIL_0.equals(feedbk.success))){
			feedbk.success = grpDatacollecttypeService.updateFiles(paraMap);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("datacollecttypeId", paraId);
		resMap.put("name", map.get("name").toString());
		feedbk.Obj = resMap;
		return feedbk;
	}

	/**
	 * 验证标题是否重复
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySameThing")
	public @ResponseBody
	FeedBackObject querySameThing(String datacollectTitle) {
		FeedBackObject feedbk = new FeedBackObject();
		String datacollectId = grpDatacollectService
				.querySameThing(datacollectTitle);
		if (datacollectId != null) {
			feedbk.success = RESULT.FAIL_0;
		} else {
			feedbk.success = RESULT.SUCCESS_1;
		}
		return feedbk;
	}

	/**
	 * 删除集团数据类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteTypeBytypeId")
	public @ResponseBody
	FeedBackObject deleteTypeBytypeId(String datacollecId,
			String datacollectypeId) {
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		try {
			if (datacollecId != null) {
				paraMap.put("datacollecttypeId", datacollectypeId);
				paraMap.put("datacollectId", datacollecId);
				grpDatacollecttypePrvService.deleteTypePrvByTypeId(paraMap);
			}
			feedbk.success = grpDatacollecttypeService
					.deleteByPrimaryKey(datacollectypeId);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return feedbk;
	}

	/**
	 * 下载模板
	 * @return
	 * @author changwq
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/downLoad")
	public @ResponseBody void downLoad(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String path = request.getParameter("path");
		String name = request.getParameter("name");
		FileUtils.download(path, name, request, response);
	}
	
	/**
	 * 清空集团数据类型表中脏数据
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value = "/deleteUserLessMsg")
	public void deleteUserLessMsg(){
		grpDatacollecttypeService.deleteUserLessMsg();
	}
	/**
	 * 各省上报文件打包下载
	 */
	@RequestMapping(value = "/uploadZip")
	public @ResponseBody void uploadZip(String datacollectId,String distributePrvIds,
			HttpServletRequest request,HttpServletResponse response){
		String[] prvid = null;
		if(!StrUtil.isBlank(distributePrvIds)){
			prvid = distributePrvIds.split(PromptMessageComm.COMMA_SYMBOL);
		}
		//创建下载路径集合
		List<String> downPath = new ArrayList<String>();
		if(prvid != null){
			for(int i=0;i<prvid.length;i++){
				List<String> paths = grpDatacollecttypePrvService.uploadZip(datacollectId, prvid[i]);
 				for(int j=0;j<paths.size();j++){
					if(!StrUtil.isBlank(paths.get(j))){
						downPath.add(paths.get(j));
					}
				}
			}
		}
		String zipName = datacollectId + PromptMessageComm.SUFFIX_ZIP;
		String zipPath = PromptMessageComm.ZIP_PATH;
		DownZipUtil.downloadZip(zipPath, zipName, downPath, request, response);
	}
	/**
	 * 保存并上报
	 */
	@RequestMapping(value = "/insertAndSubmit")
	public @ResponseBody FeedBackObject insertAndSubmit(@ModelAttribute("user") UserLoginInfo loginInfo,
			String datacollectPrvNote, String datacollectPrvOtherfilepath,
			String datacollectPrvOtherfilename, String datacollectPrvId){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		//保存
		fdback = saveCollectPrv(loginInfo, datacollectPrvNote, datacollectPrvOtherfilepath,
				datacollectPrvOtherfilename, datacollectPrvId);
		if(fdback.success.equals(RESULT.SUCCESS_1)){
			//上报
			fdback = updateDataPrvById(loginInfo, datacollectPrvId);
		}
		if (fdback.success.equals(RESULT.SUCCESS_1)) {
			fdback.msg = PromptMessageComm.USER_SAVE_AND_SEND_PRVORDER_SUCCESS + PromptMessageComm.SYMBOL1 + loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, fdback.msg);
		} else {
			fdback.success = RESULT.FAIL_0;
	 		// 添加系统日志
			log.err(SysLogComm.LOG_Error, fdback.msg);
		}
		return fdback;
	}
	/**
	 * 保存并派发
	 */
	@RequestMapping(value = "/insertAndSend")
	public @ResponseBody FeedBackObject insertAndSend(@ModelAttribute("user") UserLoginInfo loginUser,
			String datacollectId, String datacollectTitle,
			String datacollectDeadline, String datacollectNote, String prvId,
			String id, String datacollectCopy){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		if(StrUtil.isNotBlank(datacollectId)){
			//修改工单信息
			fdback = updateByPrimaryKeySelective(loginUser, datacollectId, datacollectTitle,
					datacollectDeadline, datacollectNote, prvId, id, datacollectCopy);
			if(fdback.success.equals(RESULT.SUCCESS_1)){
				//派发
				fdback = updateStateById(loginUser, datacollectId);
				if (fdback.success.equals(RESULT.FAIL_0)) {
					// 添加系统日志
					log.info(SysLogComm.LOG_Operate, fdback.msg);
					return fdback;
				}
			}
		}else{
			//新增工单
			fdback = insertSelective(loginUser, datacollectTitle, datacollectDeadline,
					datacollectNote, prvId, id, datacollectCopy);
			if(fdback.success.equals(RESULT.SUCCESS_1)){
				String dataCollectId = (String) fdback.Obj;
				//派发
				fdback = updateStateById(loginUser, dataCollectId);
				if (fdback.success.equals(RESULT.FAIL_0)) {
					// 添加系统日志
					log.info(SysLogComm.LOG_Operate, fdback.msg);
					return fdback;
				}
			}
		}
		if (fdback.success.equals(RESULT.SUCCESS_1)) {
			fdback.msg = PromptMessageComm.USER_SAVE_AND_SEND_ORDER+datacollectTitle+PromptMessageComm.USER_SAVE_AND_SEND_ORDER_SUCCESS + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, fdback.msg);
		} else {
			fdback.success = RESULT.FAIL_0;
			fdback.msg = PromptMessageComm.USER_SAVE_AND_SEND_ORDER+datacollectTitle+PromptMessageComm.USER_SAVE_AND_SEND_ORDER_FAILED + PromptMessageComm.SYMBOL1 + loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, fdback.msg);
		}
		return fdback;
	}
}
