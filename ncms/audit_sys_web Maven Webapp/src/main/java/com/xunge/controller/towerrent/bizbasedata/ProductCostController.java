package com.xunge.controller.towerrent.bizbasedata;

import java.util.ArrayList;
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
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.model.towerrent.bizbasedata.ProductCostVO;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrProductCostService;
/**
 * 
 * @author jiacy
 * @date 2017年6月5日
 * @description 建造成本库控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@Controller
public class ProductCostController extends BaseException{
	
	@Autowired
	private ITwrProductCostService proCostService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所有建造成本集合
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryAllProCost", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllProCost(int pageSize,
			int pageNumber) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = proCostService.queryAllProCostVO(pageSize, pageNumber);
		return fdback;
	}

	/**
	 * 批量删除建造成本对象
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteProCost", method = RequestMethod.POST)
	public @ResponseBody  FeedBackObject deleteProCostById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proCostService.deleteProCostById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 更新建造成本对象
	 * @author jiacy
	 * @param proCostVO
	 * @return
	 */
	@RequestMapping(value = "/updateProCost", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateProCostById(ProductCostVO proCostVO) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proCostService.updateProCostById(proCostVO);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 插入建造成本对象
	 * @author jiacy
	 * @param proCostVO
	 * @return
	 */
	@RequestMapping(value = "/insertProCost", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertProCostById(ProductCostVO proCostVO) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success =proCostService.insertProCostById(proCostVO);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
		
	}

	/**
	 * 开启或停用建造成本状态
	 * @author jiacy
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startOrStopProCost", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject startOrStopProCostById(@RequestBody List<String> ids, HttpServletRequest request) {
		String proCostState = request.getParameter("productcostState");
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = proCostService.startOrStopProCostById(ids, proCostState);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 按条件查询建造成本对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryProCostByCondition", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryProCostByStateAndName(HttpServletRequest request,int pageSize,int pageNumber) {
			String productcostState = request.getParameter("productcostState");
			String roomtypeId = request.getParameter("roomtypeId");
			String supttypeId = request.getParameter("supttypeId");
			String producttypeId = request.getParameter("producttypeId");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("productcostState", productcostState);
			paramMap.put("roomtypeId", roomtypeId);
			paramMap.put("supttypeId", supttypeId);
			paramMap.put("producttypeId", producttypeId);
			FeedBackObject backObj = new FeedBackObject();
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = proCostService.queryProCostVOByStateAndName(paramMap, pageSize, pageNumber);
			return backObj;
	}
	
	/**
	 * 查询公共字典中所有机房类型
	 * @author jiacy
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryRoomList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryRoomList() {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.queryRoomList();
		return backObj;
	}
	
	/**
	 * 查询公共字典中所有配套类型
	 * @author jiacy
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySuptList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject querySuptList() {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.querySupptList();
		return backObj;
	}
	
	
	/**
	 * 查询铁塔列表
	 * @author jiacy
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTwrTypeList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryProCostList() {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.queryTwrList();
		return backObj;
	}
	
	/**
	 * 查询三级铁塔列表
	 * @author jiacy
	 * @return
	 */
	@RequestMapping(value = "/queryTwrVoList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject querytwrTypeVO() {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.querytwrTypeVO();
		return backObj;
	}

	/**
	 * 查询风压集合
	 * @author jiacy
	 * @return
	 */
	@RequestMapping(value = "/querywinPressList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryWindPress() {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.queryWindPress();
		return backObj;
	}

	
	/**
	 * 查询挂高集合
	 * @author jiacy
	 * @return
	 */
	@RequestMapping(value = "/queryHighUpList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryHighUpVO() {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.queryHighUpVO();
		return backObj;
	}
	
	/**
	 * 查询机房和配套类型列表
	 * @author jiacy
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryRoomAndSuptType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryRoomAndSuptType(HttpServletRequest request) {
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//ROOM_TYPE  SUPT_TYPE
		String commType = request.getParameter("commType");
		paramMap.put("commType", commType);
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	proCostService.queryRoomAndSuptType(paramMap);
		return backObj;
	}
	
	/**
	 * 查询机房和配套类型列表
	 * @author jiacy
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAllObj", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllObj() {
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("commType", "ROOM_TYPE");
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =	"";
		List<ProductCostVO> twrList = proCostService.querytwrTypeVO();
		List<CommTypeVO> roomList = proCostService.queryRoomAndSuptType(paramMap);
		paramMap.clear();
		paramMap.put("commType", "SUPT_TYPE");
		List<CommTypeVO> suptList = proCostService.queryRoomAndSuptType(paramMap);
		List<WindPressureVO> winPressList = proCostService.queryWindPress();
		List<HighUpVO> highUpList = proCostService.queryHighUpVO();
		List<Object> allIno = new ArrayList<Object>();
		allIno.add(twrList);
		allIno.add(roomList);
		allIno.add(suptList);
		allIno.add(winPressList);
		allIno.add(highUpList);
		backObj.Obj = allIno;
		return backObj;
	}
}
