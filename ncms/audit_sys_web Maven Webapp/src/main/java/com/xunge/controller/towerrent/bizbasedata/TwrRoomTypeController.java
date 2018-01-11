package com.xunge.controller.towerrent.bizbasedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.OperateUtil;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrRoomTypeService;

/**
 * @description 机房类型的控制器
 * @author zhaosf
 * @date 日期：2017年9月19日 时间：下午1:57:01
 */

@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@Controller
public class TwrRoomTypeController {

	@Autowired
	ITwrRoomTypeService twrRoomTypeService;
	@Autowired
	ILogService log;
	
	
	/**
	 * 新增机房类型
	 * @author zhaosf
	 * @param commTypeVO
	 * @return
	 */
	@RequestMapping(value = "/insertRoomType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertRoomType(HttpServletRequest request){		
		CommTypeVO commTypeVO = new CommTypeVO();
		commTypeVO.setCommtype_id(SysUUID.generator());
		commTypeVO.setCommtype_name(request.getParameter("commTypeName"));
		commTypeVO.setCommtype_group(request.getParameter("commTypeGroup"));
		commTypeVO.setCommtype_state(Integer.parseInt(request.getParameter("commtypeState")));
		commTypeVO.setCommtype_note(request.getParameter("commTypeNote"));		
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrRoomTypeService.insertRoomType(commTypeVO);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}
	
	/** 编辑机房类型信息
	 * @author zhaosf
	 * @param commTypeVO
	 * @return
	 */
	@RequestMapping(value = "/updateRoomType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateRoomTypeByCommTypeId(HttpServletRequest request){
		CommTypeVO commTypeVO = new CommTypeVO();
		commTypeVO.setCommtype_id(request.getParameter("commTypeId"));
		commTypeVO.setCommtype_name(request.getParameter("commTypeName"));
		commTypeVO.setCommtype_group(request.getParameter("commTypeGroup"));
		commTypeVO.setCommtype_state(Integer.parseInt(request.getParameter("commtypeState")));
		commTypeVO.setCommtype_note(request.getParameter("commTypeNote"));
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrRoomTypeService.updateRoomTypeByCommTypeId(commTypeVO);
		return backObj;
	}
	
	/** 批量删除机房类型信息
	 * @author zhaosf
	 * @param commypeIds
	 * @return
	 */
	@RequestMapping(value = "/deleteRoomType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject deleteRoomTypeByCommTypeId(@RequestBody List<String> list){
		FeedBackObject backObj = new FeedBackObject();
		if (list.size() == 0) {
			backObj.msg = PromptMessageComm.SELECT_OPERATION_DATAS;
			return backObj;
		}
		twrRoomTypeService.deleteRoomTypeByCommTypeId(list);
		backObj.success = RESULT.SUCCESS_1;
		backObj.msg = PromptMessageComm.OPERATION_SUSSESS;
		return backObj;
	}
	
	/**根据条件查询机房类型信息
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/queryAllRoomTypeByCondition", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllRoomTypeByCondition(
			HttpServletRequest request,Integer pageSize,Integer pageNum){
		String str = request.getParameter("commTypeState");
		Map<String, Object> paraMap  = new HashMap<String, Object>();
		if(StrUtil.isNotEmpty(str)){
			paraMap.put("commtype_state", str);
		}
		paraMap.put("commtype_name", request.getParameter("commTypeName"));
		paraMap.put("commtype_group", request.getParameter("commTypeGroup"));
		
		FeedBackObject backObj = new FeedBackObject();
		backObj.Obj = twrRoomTypeService.queryAllRoomTypeByCondition(paraMap,pageSize,pageNum);
		backObj.success = RESULT.SUCCESS_1;
		return backObj;
	}
	
	/**
	 * 查询所有机房类型信息
	 * @param commTypeGroup
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/queryAllRoomType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllRoomType(HttpServletRequest request,Integer pageSize,Integer pageNum){
		FeedBackObject backObj = new FeedBackObject();
		backObj.Obj = twrRoomTypeService.queryAllRoomType(request.getParameter("commTypeGroup"), pageSize, pageNum);
		backObj.success = RESULT.SUCCESS_1;
		return backObj;
	}
	
	/**启动机房
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/startRoomType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject startRoomType(@RequestBody List<String> list){
		FeedBackObject backObj = new FeedBackObject();
		if (list.size() == 0) {
			backObj.msg = PromptMessageComm.SELECT_OPERATION_DATAS;
			return backObj;
		}
		twrRoomTypeService.startOrStopRoomType(list,StateComm.STATE_0);
		backObj.success = RESULT.SUCCESS_1;
		backObj.msg = PromptMessageComm.OPERATION_SUSSESS;
		return backObj;
	}
	
	/**停用机房
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/stopRoomType", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject stopRoomType(@RequestBody List<String> list){
		FeedBackObject backObj = new FeedBackObject();
		if (list.size() == 0) {
			backObj.msg = PromptMessageComm.SELECT_OPERATION_DATAS;
			return backObj;
		}
		twrRoomTypeService.startOrStopRoomType(list,StateComm.STATE_9);
		backObj.success = RESULT.SUCCESS_1;
		backObj.msg = PromptMessageComm.OPERATION_SUSSESS;
		return backObj;
	}
	
	/**
	 * 检查机房名称是否重复
	 * @param request
	 * @param commtypeName
	 * @param commtypeGroup
	 * @param commtypeState
	 * @return
	 */
	@RequestMapping(value = "/checkTwrName", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject checkTwrName(HttpServletRequest request,String commtypeName,String commtypeGroup,int commtypeState){
 		FeedBackObject backObj = new FeedBackObject();
		Map<String, Object> paraMap  = new HashMap<String, Object>();
		paraMap.put("commtype_name", commtypeName);
		paraMap.put("commtype_group", commtypeGroup);
		paraMap.put("commtype_state", commtypeState);
		backObj.Obj = twrRoomTypeService.queryTwrCommType(paraMap);
		backObj.success = RESULT.SUCCESS_1;
		return backObj;
	}
	
	
}
