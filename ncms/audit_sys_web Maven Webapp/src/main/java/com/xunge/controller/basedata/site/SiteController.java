package com.xunge.controller.basedata.site;

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
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.vo.SiteQueryVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.basedata.site.ISiteService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.tool.IRegionService;

/**
 * 站点管理
 * 
 * Title: SiteController
 * @author Rob
 */
@RestController
@RequestMapping("/asserts/tpl/basedata/site")
public class SiteController extends BaseException {

	@Resource
	private ISiteService siteService;
	@Resource
	private IRegionService regionService;
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private ISysRoleService iSysRoleService;
	@Autowired 
	private ISysRegionService sysRegionService;
	
	/**
	 * 站点列表查询
	 * @return
	 */
	@RequestMapping(value="/list")
	public FeedBackObject getList(@Validated SiteQueryVO vo,HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
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
		act.setProcDefKey(ActUtils.PD_SITE_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_SITE_AUDIT[1]);
		act.setAssignee(loginUser.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		map.put("ids", idsList);
		
		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		vo.setLoginUser(loginInfo);
		vo.setBasesiteState(StateComm.STATE__1);
		feedbackObj.Obj = siteService.getAll(vo, map);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	/**
	 * 站点信息审核页面信息查询
	 * @param vo
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/querySiteInfo")
	public FeedBackObject querySiteInfo(HttpSession session, String basesiteCodeOrName,String basesiteBelong, String basesiteState, String city,String pregId,
			String regId, int pageNumber, int pageSize) {
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_SITE_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_SITE_AUDIT[1]);
		act.setAssignee(loginUser.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		Page<?> page = new Page<>(pageNumber,pageSize);
		feedbackObj.Obj = page;
		if (idsList.size() > 0) {
			map.put("idsList", idsList);
			map.put("basesiteCodeOrName", basesiteCodeOrName);
			map.put("basesiteBelong", basesiteBelong);
			map.put("pregId", pregId);
			map.put("regId", regId);
			map.put("basesiteState", basesiteState);
			map.put("regIds", loginUser.getReg_ids());
			feedbackObj.Obj = siteService.querySiteInfo(map, pageNumber, pageSize);
			feedbackObj.success = RESULT.SUCCESS_1;
		}
		return feedbackObj;
	}
	
	/**
	 * 站点信息查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/one")
	public FeedBackObject getOne(@Validated String id){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = siteService.get(id);
		return feedBackObject;
	}
	
	/**
	 * @description 站点 及 资源点 信息查询
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value="/queryOne", method=RequestMethod.GET)
	public FeedBackObject queryOne(@Validated String id){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = siteService.queryOne(id);
		return feedBackObject;
	}
	
	/**
	 * 新增站点信息
	 * @param record
	 * @return
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public FeedBackObject insert(HttpServletRequest request){
		DatBasesiteVO record = null;
		String param = request.getParameter("param");
		if(StringUtils.isNoneBlank(param)){
			record = JSON.parseObject(param,DatBasesiteVO.class);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		record.setBasesiteId(PromptMessageComm.BASE_SITE+System.currentTimeMillis());
		record.setAuditingState(AuditStateEnum.PREAUDIT);
		record.setDataFrom(DataFromComm.STATE_0);
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		record.setPrvId(prv_id);
		int result = siteService.insert(record);
		feedBackObject.Obj = result;
		if(result == Integer.parseInt(RESULT.SUCCESS_1)){
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_SUCCESS;
		}else{
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_FAILED;
		}
		return feedBackObject;
	}
	/**
	 * 修改站点信息
	 * @param record
	 * @return
	 */
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public @ResponseBody FeedBackObject update(HttpServletRequest request){
		DatBasesiteVO record = null;
		String param = request.getParameter("param");
		if(StringUtils.isNoneBlank(param)){
			record = JSON.parseObject(param,DatBasesiteVO.class);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		record.setAuditingState(AuditStateEnum.PREAUDIT);
		int result = siteService.update(record);
		feedBackObject.Obj = result;
		if(result == Integer.parseInt(RESULT.SUCCESS_1)){
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_SUCCESS;
		}else{
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_FAILED;
		}
		return feedBackObject;
	}
	/**
	 * 删除站点信息
	 * @param list
	 * @return
	 */
	@RequestMapping(value="/remove")
	public FeedBackObject delete(@RequestBody List<DatBasesiteVO> list){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = siteService.deleteBatch(list);
		return feedBackObject;
	}
	/**
	 * 提交审核
	 * 支持批量操作
	 * @param list
	 * @return
	 */
	@RequestMapping(value="/submitAudit", method=RequestMethod.POST)
	public @ResponseBody FeedBackObject submitAudit(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj = siteService.submitAudit(ids, loginUser);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg=PromptMessageComm.SUBMITTED_SUCCESS;
		return feedbackObj;
	}
	/**
	 * 审核
	 * 支持批量操作
	 * @param list
	 * @return
	 */
	@RequestMapping(value="/audit", method=RequestMethod.POST)
	public FeedBackObject audit(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = siteService.audit(ids, loginUser);
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg =ActivityStateComm.AUDIT_NORMAL;
		return feedbk;
	}
	/**
	 * 导出数据
	 * @param list
	 * @return
	 */
	@RequestMapping(value="/export", method=RequestMethod.GET)
	public FeedBackObject export(@Validated SiteQueryVO vo, HttpServletRequest request, HttpServletResponse response){
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
		String fileName = siteService.export(vo, path,request,response);
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
	 * 站点信息审核页面信息导出
	 * @param vo
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/exportAudit", method=RequestMethod.GET)
	public FeedBackObject exportAudit(HttpSession session, HttpServletRequest request,HttpServletResponse response, String basesiteCodeOrName,String basesiteBelong, String basesiteState, String city,String pregId,
			String regId, int pageNumber, int pageSize,Integer queryType) {
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_SITE_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_SITE_AUDIT[1]);
		act.setAssignee(loginUser.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		Page<?> page = new Page<>(pageNumber,pageSize);
		feedbackObj.Obj = page;
		String fileName = "";
		if (idsList.size() > 0) {
			map.put("idsList", idsList);
			map.put("basesiteCodeOrName", basesiteCodeOrName);
			map.put("basesiteBelong", basesiteBelong);
			map.put("pregId", pregId);
			map.put("regId", regId);
			map.put("basesiteState", basesiteState);
			map.put("regIds", loginUser.getReg_ids());
			map.put("queryType", queryType);
			map.put("auditingState", ActivityStateComm.STATE_AUDIT);

			String requestUrl = request.getRequestURL().toString();
			String url = requestUrl.substring(0, requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
			String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF;
			fileName = siteService.exportAudit(map,path, pageNumber, pageSize,request,response);

			feedbackObj.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT+ fileName;
			feedbackObj.success = RESULT.SUCCESS_1;
		}else{
			map.put("op",StateComm.STATE_1);
			map.put("queryType", queryType);
			String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF;
			fileName = siteService.exportAudit(map,path, pageNumber, pageSize,request,response);
		}
		return feedbackObj;
	}
	/**
	 * 导入信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody BaseRet importData(HttpSession session,HttpServletRequest request,Exception ex) {
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
				Map<String,Object> map=Maps.newHashMap();
			    if(userObj!=null){
			    	UserLoginInfo userLoginInfo =(UserLoginInfo)userObj;
			    	prvId=userLoginInfo.getPrv_id();
			    }
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
					Object[] result = siteService.importData(prvId, file,listreg);
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
	 * @description 判断站点编码唯一性
	 * @author yuefy
	 * @date 创建时间：2017年8月18日
	 */
	@RequestMapping(value="/checkBasesiteCode")
	public FeedBackObject checkBasesiteCode(String basesiteCode,HttpServletRequest request){
		FeedBackObject feedbk = new FeedBackObject();
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("basesiteCode", basesiteCode);
		map.put("basesiteState", StateComm.STATE_str__1);
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		
		feedbk.Obj = siteService.checkeSitCode(map);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
}