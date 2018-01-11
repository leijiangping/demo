package com.xunge.controller.basedata.resource;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.TaskComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.BaseRet;
import com.xunge.controller.basedata.util.FileUtils;
import com.xunge.core.enums.ResourceEnum.AuditStateEnum;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.vo.ResourceQueryVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.basedata.resource.IDatResourceService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.tool.IAuditorService;
import com.xunge.service.tool.IRegionService;

/**
 * 基础资源管理
 * 
 * Title: ResourceController
 * 
 * @author Rob
 */
@RestController
@RequestMapping(value = "/asserts/tpl/basedata/resource")
public class ResourceController extends BaseException {

	@Resource
	private IDatResourceService datResourceService;
	@Resource
	private IRegionService regionService;
	@Resource
	private IAuditorService auditService;
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private ISysRoleService iSysRoleService;

	@Autowired 
	private ISysRegionService sysRegionService;
	/**
	 * 查询基础资源列表
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list")
	public FeedBackObject getList(@Validated ResourceQueryVO vo, HttpServletRequest request) {
		FeedBackObject feedBackObject = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_BASERESOURCE_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_BASERESOURCE_AUDIT[1]);
		act.setAssignee(loginUser.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		map.put("ids", idsList);
		
		// 数据权限过滤(根据登录用户过滤)
		vo.setLoginUser(loginUser);
		
		feedBackObject.Obj = datResourceService.getAll(vo, map);
		feedBackObject.success = RESULT.SUCCESS_1;
		return feedBackObject;
	}
	
	@RequestMapping(value = "/queryDatBaseresource")
	public FeedBackObject queryDatBaseresource(HttpSession session,String roomOwner, String baseresourceCodeOrName, String baseresourceState, String queryType,String pregId,
			String regId, int pageNumber, int pageSize) {
		FeedBackObject feedBackObject = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_BASERESOURCE_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_BASERESOURCE_AUDIT[1]);
		act.setAssignee(loginUser.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		Page<?> page = new Page<>(pageNumber,pageSize);
		feedBackObject.Obj = page;
		if (idsList.size() > 0) {
			map.put("idsList", idsList);
			map.put("baseresourceCodeOrName", baseresourceCodeOrName);
			map.put("baseresourceState", baseresourceState);
			map.put("pregId", pregId);
			map.put("regId", regId);
			map.put("queryType", queryType);
			map.put("roomOwner",roomOwner);
			feedBackObject.Obj = datResourceService.queryDatBaseresource(map, pageNumber, pageSize);
			feedBackObject.success = RESULT.SUCCESS_1;
		}
		return feedBackObject;
	}
	
	/**
	 * 查询单个基础资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/one", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject getOne(String id) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = datResourceService.get(id);
		return feedBackObject;
	}

	/**
	 * 新增基础资源信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject add(HttpServletRequest request) {
		DatBaseresourceVO record = null;
		String param = request.getParameter("param");
		if(StringUtils.isNoneBlank(param)){
			record = JSON.parseObject(param,DatBaseresourceVO.class);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		record.setBaseresourceId(PromptMessageComm.BASE_RESOURCE_ID + System.currentTimeMillis());
		record.setAuditingState(AuditStateEnum.PREAUDIT);
		record.setDataFrom(DataFromComm.STATE_0);
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if (loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		record.setPrvId(prv_id);
		int result = datResourceService.insert(record);
		feedBackObject.Obj = result;
		if (result == Integer.parseInt(RESULT.SUCCESS_1)) {
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_SUCCESS;
		} else {
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_FAILED;
		}
		return feedBackObject;
	}

	/**
	 * 修改基础资源信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject modify(HttpServletRequest request) {
		DatBaseresourceVO record = null;
		String param = request.getParameter("param");
		if(StringUtils.isNoneBlank(param)){
			record = JSON.parseObject(param,DatBaseresourceVO.class);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		int result = datResourceService.update(record);
		feedBackObject.Obj = result;
		if (result == Integer.parseInt(RESULT.SUCCESS_1)) {
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_SUCCESS;
		} else {
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_FAILED;
		}
		return feedBackObject;
	}
	/**
	 * 删除基础资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject remove(@RequestBody List<DatBaseresourceVO> list) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = datResourceService.deleteBatch(list);
		return feedBackObject;
	}
	/**
	 * 提交审核 支持批量操作
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/submitaudit", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject submitAudit(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = datResourceService.submitAudit(ids, loginUser);
		return feedBackObject;
	}

	/**
	 * 审核 支持批量
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public FeedBackObject audit(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.Obj = datResourceService.audit(ids, loginUser);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg =ActivityStateComm.AUDIT_NORMAL;
		return feedBackObject;
	}
	/**
	 * 审核导出数据
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/exportAudit", method = RequestMethod.GET)
	public FeedBackObject exportAudit(@Validated ResourceQueryVO vo,HttpServletRequest request,HttpServletResponse response,Integer roomOwner) {
		FeedBackObject feedBackObject = new FeedBackObject();
		String requestUrl = request.getRequestURL().toString();
		String url = requestUrl.substring(0, requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF;
		
		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginInfo.getReg_ids());
		List<String> list = loginInfo.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_BASERESOURCE_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_BASERESOURCE_AUDIT[1]);
		act.setAssignee(loginInfo.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginInfo.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		if (idsList.size() > 0) {
			map.put("idsList", idsList);
			map.put("baseresourceCodeOrName", vo.getReg());
			map.put("baseresourceState", vo.getStatus());
			map.put("pregId", vo.getCity());
			map.put("regId", vo.getRegion());
			map.put("queryType", vo.getResType());
			map.put("roomOwner",roomOwner);
			map.put("auditingState",ActivityStateComm.STATE_AUDIT);
			String fileName = datResourceService.exportAudit(map, path,request,response);
			
			feedBackObject.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT + fileName;
			feedBackObject.success = RESULT.SUCCESS_1;
		}else{
			map.put("op",StateComm.STATE_1);
			String fileName = datResourceService.exportAudit(map, path,request,response);
			feedBackObject.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT + fileName;
			feedBackObject.success = RESULT.SUCCESS_1;
		}
		return feedBackObject;
	}
	/**
	 * 导出数据
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public FeedBackObject export(@Validated ResourceQueryVO vo, HttpServletRequest request,HttpServletResponse response) {
		FeedBackObject feedBackObject = new FeedBackObject();
		String requestUrl = request.getRequestURL().toString();
		String url = requestUrl.substring(0, requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF;
		
		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		vo.setLoginUser(loginInfo);
		
		String fileName = datResourceService.export(vo, path,request,response);
		if (!fileName.equals("")) {
			feedBackObject.success = RESULT.SUCCESS_1;
			feedBackObject.msg = PromptMessageComm.EXPORT_INFO_SUCCESS;
			feedBackObject.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT + fileName;
		} else {
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = PromptMessageComm.EXPORT_INFO_FAIL;
			feedBackObject.Obj = null;
		}
		return feedBackObject;
	}

	/**
	 * 导入数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody BaseRet importData(int baseresourceType,HttpSession session,HttpServletRequest request,Exception ex) {
		BaseRet baseRet = new BaseRet();
		if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){  
	         String num = FileUtils.getFileMB(((org.springframework.web.multipart.MaxUploadSizeExceededException)ex).getMaxUploadSize());
	         baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
	     	 baseRet.setMessage(PromptMessageComm.UPLOAD_FILE_OVER+num+PromptMessageComm.UPLOAD_FAIL);
	         return baseRet;
	    } 
		try{
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multipartRequest.getFileNames();
				String prvId="";
				Object userObj=session.getAttribute("user");
				if(userObj == null){
					throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
				}
			    if(userObj!=null){
			    	UserLoginInfo userLoginInfo =(UserLoginInfo)userObj;
			    	prvId=userLoginInfo.getPrv_id();
			    }
				Map<String,Object> map=Maps.newHashMap();
			  //设置自定义数据处理
				map.put("state",StateComm.STATE_str0);

				//获取当前用户所属地区
				UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
				if(loginInfo == null){
					throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
				}
				map.put("prvId",loginInfo.getPrv_id());
				
				List<SysRegionVO> listreg=sysRegionService.getAddress(map);//准备需要的数据处理
			    String errorMsg=PromptMessageComm.OPERATION_SUSSESS;
				if(iter.hasNext()) {
					// 一次遍历所有文件
					MultipartFile file = multipartRequest.getFile(iter.next().toString());
					Object[] result = datResourceService.importData(prvId, file,listreg,baseresourceType);
					int status=Integer.parseInt(result[0].toString());
					errorMsg=result[1].toString();
					baseRet.setStatus(status);
					baseRet.setMessage(errorMsg);
				}
			}
		}catch(Exception e){
			baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	}
	
	/**
	 * 获取审核人列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/auditors", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject getRegions(HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if (loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		feedbackObj.Obj = auditService.getAuditor(loginInfo.getUser_id());
		return feedbackObj;
	}
	
	/**
	 * 查询基础资源详情页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryDetails", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryDetails(String id) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = datResourceService.queryDetails(id);
		return feedBackObject;
	}
	
	/**
	 * @description 判断资源代码唯一性
	 * @author yuefy
	 * @date 创建时间：2017年8月25日
	 */
	@RequestMapping(value = "/checkBaseresourceCode")
	public @ResponseBody FeedBackObject checkBaseresourceCode(String baseresourceCode) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("baseresourceCode", baseresourceCode);
		feedBackObject.Obj = datResourceService.checkBaseresourceCode(map);
		return feedBackObject;
	}
	
}
