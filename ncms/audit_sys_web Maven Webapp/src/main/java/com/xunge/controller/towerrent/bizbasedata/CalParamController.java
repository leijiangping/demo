package com.xunge.controller.towerrent.bizbasedata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.OperateUtil;
import com.xunge.core.exception.BaseException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.StrUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.bizbasedata.CalParameterVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrCalParamService;

/**
 * 
 * @author jiacy
 * @date 2017年7月6日
 * @description 计算参数控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class CalParamController extends BaseException{
	
	@Autowired
	private ITwrCalParamService calParamService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所有计算参数对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryAllCalParam", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllCalVO(int pageSize,
			int pageNumber,@ModelAttribute("user")UserLoginInfo loginUser) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("prvId", loginUser.getPrv_id());
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = calParamService.queryAllCalParameterVO(pageSize, pageNumber,paramMap);
		return fdback;
	}
	
	/**
	 * 删除计算参数对象
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteCalParam", method = RequestMethod.POST)
	public @ResponseBody  FeedBackObject deleteCalVoById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = calParamService.deleteCalParameterById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 更新计算参数对象
	 * @author jiacy
	 * @param calVo
	 * @return
	 */
	@RequestMapping(value = "/updateCalParam", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateCalVoById(CalParameterVO calVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = calParamService.updateCalParameterById(calVo);
		//回显并记录日志
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 新增计算参数对象
	 * @author jiacy
	 * @param calVo
	 * @return
	 */
	@RequestMapping(value = "/insertCalParam", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCalParamVO(@RequestBody List<CalParameterVO> calVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = calParamService.insertCalParameterById(calVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
		
	}

	/**
	 * 启用或者停用
	 * @author jiacy
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStopCalParam", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject startOrStopCalParamById(@RequestBody List<String> ids, HttpServletRequest request) {
		String calParamState = request.getParameter("calcparameterState");
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = calParamService.updateStartOrStopWinPressById(ids, calParamState);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 按条件查询计算参数对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryCalParamByCondition", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryCalParamByStateAndName(HttpServletRequest request,int pageSize,int pageNumber,@ModelAttribute("user")UserLoginInfo loginUser) {
			String highupState = request.getParameter("calcparameterState");
			String producttypeId = request.getParameter("pregId");
			String highupName = request.getParameter("calcparameterName");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("calcparameterState", highupState);
			paramMap.put("pregId", producttypeId);
			paramMap.put("calcparameterName", highupName);
			paramMap.put("prvId", loginUser.getPrv_id());
			FeedBackObject backObj = new FeedBackObject();
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = calParamService.queryCalParameterVOByStateAndName(paramMap, pageSize, pageNumber);
			return backObj;
	}
	
	/**
	 * 查询计算参数表中的结算参数集合
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryParamObj", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryParamObj(String calcparameterName) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StrUtil.isNotBlank(calcparameterName)){
			String []str = calcparameterName.split(",");
			List<String> list = Arrays.asList(str);
			paraMap.put("list", list);			
		}
		fdback.Obj = calParamService.queryParamObj(paraMap);
		return fdback;
	}
	
}
