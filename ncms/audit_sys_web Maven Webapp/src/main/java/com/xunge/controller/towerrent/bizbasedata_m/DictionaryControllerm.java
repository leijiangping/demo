package com.xunge.controller.towerrent.bizbasedata_m;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.DictionaryComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.dictionary.DictionaryGroupVO;
import com.xunge.model.system.dictionary.DictionaryVO;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.log.ILogService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller  
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
public class DictionaryControllerm extends BaseException {

	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private ILogService log;
	/**
	 * 通过登陆用户查询所有数据字典
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value = "/queryAllDictionaryGroup", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryAllDictionaryGroup(@ModelAttribute("user") UserLoginInfo loginInfo,HttpServletRequest request){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//从当前登录者的Session中获取当前登录者所属省份
		String prv_id = loginInfo.getPrv_id();
		String dictgroup_code = request.getParameter("dictgroup_code");
		FeedBackObject feedBackObj = new FeedBackObject();
		List<DictionaryGroupVO> dglist = dictionaryService.queryAllDictionaryGroupRedis(prv_id);
		//根据页面code值 获取对应的groupid
		for(DictionaryGroupVO dg : dglist){
			if(dg.getDictgroup_code().equals(dictgroup_code)){
				feedBackObj.Obj = dg;
				break;
			}
		}
		feedBackObj.success = RESULT.SUCCESS_1;
		return feedBackObj;
	}
	/**
	 * 通过id查询数据字典
	 * @param ID
	 * @return
	 */
	@RequestMapping(value = "/queryDictionaryByID", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryDictionaryByID(String ID){
		FeedBackObject feedBackObj = new FeedBackObject();
		feedBackObj.success = RESULT.SUCCESS_1;
		feedBackObj.Obj = dictionaryService.queryDictionaryByIDRedis(ID);
		return feedBackObj;
	}
	/**
	 * 查询所有数据字典
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDictionary", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryDictionary(
			@ModelAttribute("user") UserLoginInfo loginInfo, HttpServletRequest request){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String dictgroup_id = request.getParameter("dictgroup_id"); 
		String dict_name = request.getParameter("dict_name"); 
		String dict_value = request.getParameter("dict_value"); 
		String dict_state = request.getParameter("dict_state"); 
		int page_number = Integer.parseInt(request.getParameter("pageNumber")); 
		int page_size = Integer.parseInt(request.getParameter("pageSize"));
		
		Map<String, Object> param = new HashMap<>();
		param.put(DictionaryComm.DICT_GROUP_ID, dictgroup_id);
		param.put(DictionaryComm.DICT_NAME, dict_name);
		param.put(DictionaryComm.DICT_VALUE, dict_value);
		param.put(DictionaryComm.DICT_STATE, dict_state);
		param.put("prv_id", loginInfo.getPrv_id());
		
		FeedBackObject feedBackObj = new FeedBackObject();
		Page<List<DictionaryVO>> pageInfo = dictionaryService.queryDictionaryRedis(param, page_number, page_size);
		feedBackObj.Obj = pageInfo;
		// 判断处理是否成功
		if(pageInfo != null){
			feedBackObj.success = RESULT.SUCCESS_1;
			feedBackObj.msg=PromptMessageComm.SEARCH_SUCCESS;
    	}
    	else{
			feedBackObj.success = RESULT.FAIL_0;
    		feedBackObj.msg=PromptMessageComm.SELECT_FAIL;
    	}
		return feedBackObj;
	}
	/**
	 * 新增数据字典
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/insertDictionary")
	public @ResponseBody FeedBackObject insertDictionary(HttpServletRequest request
			,HttpSession session) {
		FeedBackObject feedBackObj = new FeedBackObject();
		// 批量更新
		feedBackObj.success = dictionaryService.insertDictionary(request);
		// 判断处理是否成功
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
	}
	/**
	 * 修改数据字典
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateDictionary")
	public @ResponseBody FeedBackObject updateDictionary(HttpServletRequest request
			,HttpSession session) {
		FeedBackObject feedBackObj = new FeedBackObject();
		// 批量更新
		feedBackObj.success = dictionaryService.updateDictionary(request);
		// 判断处理是否成功
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
	}
	/**
	 * 删除数据字典
	 * @param itemsId
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteDictionaryBranch")
	public @ResponseBody FeedBackObject deleteDictionaryBranch(@RequestBody List<String> itemsId
			,HttpSession session) {
		FeedBackObject feedBackObj = new FeedBackObject();
		// 批量更新
		feedBackObj.success = dictionaryService.updateDictionaryStateDelete(itemsId);
		// 判断处理是否成功
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
	}
	/**
	 * 启用数据字典
	 * @param itemsId
	 * @param session
	 * @return
	 */
	@RequestMapping("/openUseDictionaryBranch")
	public @ResponseBody FeedBackObject openUseDictionaryBranch(@RequestBody List<String> itemsId
			,HttpSession session) {
		FeedBackObject feedBackObj = new FeedBackObject();
		// 批量更新
		feedBackObj.success = dictionaryService.updateDictionaryStateOpen(itemsId);
		// 判断处理是否成功
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
	}
	/**
	 * 停用数据字典
	 * @param itemsId
	 * @param session
	 * @return
	 */
	@RequestMapping("/stopUseDictionaryBranch")
	public @ResponseBody FeedBackObject stopUseDictionaryBranch(@RequestBody List<String> itemsId
			,HttpSession session) {
		FeedBackObject feedBackObj = new FeedBackObject();
		// 批量更新
		feedBackObj.success = dictionaryService.updateDictionaryStateStop(itemsId);
		// 判断处理是否成功
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
	}
}
