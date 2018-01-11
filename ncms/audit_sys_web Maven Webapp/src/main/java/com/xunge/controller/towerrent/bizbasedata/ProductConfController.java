package com.xunge.controller.towerrent.bizbasedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.xunge.model.towerrent.bizbasedata.ProductConfigVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrProductConfService;

/**
 * 
 * @author jiacy
 * @date 2017年6月5日
 * @description 产品配置库控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@Controller
public class ProductConfController extends BaseException{
	
	@Autowired
	private ITwrProductConfService proConfService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所有配置库对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryAllProConf", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllProConfVO(int pageSize,
			int pageNumber) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = proConfService.queryAllObj(pageSize, pageNumber);
		return fdback;
	}

	/**
	 * 批量删除配置库对象
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteProConf", method = RequestMethod.POST)
	public @ResponseBody  FeedBackObject deleteProConfById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proConfService.deleteObjById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 更新配置库
	 * @author jiacy
	 * @param confVo
	 * @return
	 */
	@RequestMapping(value = "/updateProConf", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateProConfById(ProductConfigVO confVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proConfService.updateObjById(confVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 插入配置库对象
	 * @author jiacy
	 * @param confVo
	 * @return
	 */
	@RequestMapping(value = "/insertProConf", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertProConfById(ProductConfigVO confVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proConfService.insertObjById(confVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
		
	}

	/**
	 * 开启或停用配置库状态
	 * @author jiacy
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStopProConf", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject startOrStopProConfById(@RequestBody List<String> ids, HttpServletRequest request) {
		String productconfigState = request.getParameter("productconfigState");
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proConfService.startOrStopObjById(ids, productconfigState);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 按条件查询配置库对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryProConf", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryProConfByStateAndName(HttpServletRequest request,int pageSize,int pageNumber) {
			String highupState = request.getParameter("productconfigState");
			String producttypeId = request.getParameter("producttypeId");
			String supttypeId = request.getParameter("supttypeId");
			String roomtypeId = request.getParameter("roomtypeId");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("roomtypeId", roomtypeId);
			paramMap.put("supttypeId", supttypeId);
			paramMap.put("producttypeId", producttypeId);
			paramMap.put("productconfigState", highupState);
			FeedBackObject backObj = new FeedBackObject();
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = proConfService.queryObjByStateAndName(paramMap, pageSize, pageNumber);
			return backObj;
	}
	
	/**验证产品配置名称是否重复
	 * @param request
	 * @param productconfigName
	 * @return
	 */
	@RequestMapping(value = "/checkProConfName", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject checkProConfName(HttpServletRequest request,String productconfigName){
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("productconfigName", productconfigName);
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj = proConfService.queryObjByCondition(paramMap);
		return backObj;
	}
}
