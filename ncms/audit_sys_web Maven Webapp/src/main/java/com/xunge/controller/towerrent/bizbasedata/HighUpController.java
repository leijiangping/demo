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
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrHighUpService;
import com.xunge.service.twrrent.bizbasedata.ITwrProductTypeService;

/**
 * 
 * @author jiacy
 * @date 2017年6月5日
 * @description 挂高控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@Controller
public class HighUpController extends BaseException{
	
	@Autowired
	private ITwrHighUpService highUpService;
	@Autowired
	private ITwrProductTypeService twrProductTypeService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所有挂高集合
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryAllHighUp", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllHighUpVO(int pageSize,
			int pageNumber) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = highUpService.queryAllHighUpVO(pageSize, pageNumber);
		return fdback;
	}

	/**
	 * 批量删除挂高
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteHighUp", method = RequestMethod.POST)
	public @ResponseBody  FeedBackObject deleteHighUpById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = highUpService.deleteHighUpById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 更新挂高对象
	 * @author jiacy
	 * @param highUpVo
	 * @return
	 */
	@RequestMapping(value = "/updateHighUp", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateHighUpById(HighUpVO highUpVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = highUpService.updateHighUpById(highUpVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 插入挂高
	 * @author jiacy
	 * @param highUpVo
	 * @return
	 */
	@RequestMapping(value = "/insertHighUp", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertHighUpById(HighUpVO highUpVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = highUpService.insertHighUpById(highUpVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
		
	}

	/**
	 * 开启或停用挂高对象
	 * @author jiacy
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStopHighUp", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject startOrStopHighUpById(@RequestBody List<String> ids, HttpServletRequest request) {
		String highUpState = request.getParameter("highupState");
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = highUpService.updateStartOrStopHighUpById(ids, highUpState);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 按条件查询挂高集合
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryHighUpByCondition", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryHighUpVOByStateAndName(HttpServletRequest request,int pageSize,int pageNumber) {
			String highupState = request.getParameter("highupState");
			String producttypeId = request.getParameter("producttypeId");
			String highupName = request.getParameter("highupName");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("highupState", highupState);
			paramMap.put("producttypeId", producttypeId);
			paramMap.put("highupName", highupName);
			FeedBackObject backObj = new FeedBackObject();
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = highUpService.queryHighUpVOByStateAndName(paramMap, pageSize, pageNumber);
			return backObj;
	}
	
	/**查询所有铁塔信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTwrProductTypeNameList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrProductTypeNameList(HttpServletRequest request,int producttypeState){ 
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("producttypeState", producttypeState);
		backObj.Obj = twrProductTypeService.queryAllTwrProductTypeInfo(paraMap);
		backObj.success = RESULT.SUCCESS_1;
		return backObj;
	}
}
