package com.xunge.controller.towerrent.bizbasedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.OperateUtil;
import com.xunge.core.exception.BaseException;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrWindPressureService;

/**
 * 
 * @author jiacy
 * @date 2017年7月6日
 * @description 风压控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@Controller
public class WindPressureController extends BaseException{
	
	@Autowired
	private ITwrWindPressureService twrWindPressureService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所有风压对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryAllWinPress", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllWinPressVO(int pageSize,
			int pageNumber) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = twrWindPressureService.queryAllWinPressVO(pageSize, pageNumber);
		return fdback;
	}

	/**
	 * 删除风压对象
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteWinPress", method = RequestMethod.POST)
	public @ResponseBody  FeedBackObject deleteWinPressById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrWindPressureService.deleteWinPressById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 修改风压对象
	 * @author jiacy
	 * @param pressureVo
	 * @return
	 */
	@RequestMapping(value = "/updateWinPress", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateWinPressById(WindPressureVO pressureVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrWindPressureService.updateWinPressById(pressureVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 新增风压对象
	 * @author jiacy
	 * @param pressureVo
	 * @return
	 */
	@RequestMapping(value = "/insertWinPress", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertWinPressById(WindPressureVO pressureVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrWindPressureService.insertWinPressById(pressureVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
		
	}

	/**
	 * 启用停用风压对象
	 * @author jiacy
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStopWinPress", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject startOrStopWinPressById(@RequestBody List<String> ids, HttpServletRequest request) {
		String twrState = request.getParameter("windpressureState");
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrWindPressureService.updateStartOrStopWinPressById(ids, twrState);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 按条件过滤风压对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryWinPressByCondition", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryWinPressVOByStateAndName(HttpServletRequest request,int pageSize,int pageNumber) {
			String winPressState = request.getParameter("windpressureState");
			String winPressName = request.getParameter("windpressureName");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("windpressureState", winPressState);
			paramMap.put("windpressureName", winPressName);
			FeedBackObject backObj = new FeedBackObject();
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = twrWindPressureService.queryWinPressVOByStateAndName(paramMap,pageSize,pageNumber);
			return backObj;
	}
}
