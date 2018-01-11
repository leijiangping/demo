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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.OperateUtil;
import com.xunge.core.exception.BaseException;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.bizbasedata.TwrProductTypeVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrProductTypeService;

/**
 * 
 * @author jiacy
 * @date 2017年6月5日
 * @description 铁塔类型库控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@Controller
public class ProductTypeController extends BaseException{

	@Autowired
	private ITwrProductTypeService twrProductTypeService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所选铁塔菜单的下级菜单集合，例如选择铁塔根目录该接口会返回根目录的下级目录集合
	 * @author jiacy
	 * @param request
	 * @param menuId
	 * @param session
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryTwrProductTypeVO", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTwrProductTypeVO(HttpServletRequest request,
			@RequestParam("menuId") String menuId,int pageSize,int pageNumber) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		paraMap.put("menuId", menuId);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = twrProductTypeService.queryTwrProductTypeVO(paraMap,pageSize,pageNumber);
		return fdback;
	}

	/**
	 * 返回所有铁塔菜单集合
	 * @author jiacy
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAllTwrProductTypeVO", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTwrProductTypeVO() {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = twrProductTypeService.queryAllTwrProductTypeVO();
		return fdback;
	}

	/**
	 * 铁塔类别修改接口(改进点可以将参数邦定方式改为Model绑定)
	 * @author jiacy
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateTwr", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateTwrById(TwrProductTypeVO twoProductVO) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrProductTypeService.updateTwrById(twoProductVO);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	};

	/**
	 * 新增铁塔种类接口
	 * @author jiacy
	 * @param twoProductVO
	 * @return
	 */
	@RequestMapping(value = "/insertTwr", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject insertTwrById(TwrProductTypeVO twoProductVO) {
		String producttypeId = SysUUID.generator();
		twoProductVO.setProducttypeId(producttypeId);
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrProductTypeService.insertTwrById(twoProductVO);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	};

	/**
	 * 删除铁塔种类接口
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteTwr", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject deleteTwrTypeById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrProductTypeService.deleteTwrById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	};
	
	/**
	 * 启用或停用铁塔接口
	 * @author jiacy
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stopOrStartTwr", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject stopOrStartTwrTypeById(@RequestBody List<String> ids, HttpServletRequest request) {
		String twrState = request.getParameter("twrState");
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = twrProductTypeService.updateStartOrStopTwrTypeById(ids, twrState);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	};
	
    /**
     * 按照条件对铁塔进行查询
     * @author jiacy
     * @param session
     * @param request
     * @param pageSize
     * @param pageNumber
     * @return
     */
	@RequestMapping(value = "/queryProductVOByCondition", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTwrVOByStateAndName(HttpServletRequest request,String productState,String productName,int pageSize,int pageNumber) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("productState", productState);
		paramMap.put("productName", productName);
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj = twrProductTypeService.queryTwrProductTypeInfoByCondition(paramMap, pageSize, pageNumber);
		return backObj;
	};
	
	/**验证塔名称是否重复
	 * @author zhaosf
	 * @param twoProductVO
	 * @return
	 */
	@RequestMapping(value = "/chekTwrName", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject chekTwrName(String producttypeName,int producttypeState) {
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("producttypeName", producttypeName);
		paraMap.put("producttypeState", producttypeState);
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj = twrProductTypeService.queryAllTwrProductTypeInfo(paraMap);
		return backObj;
	}
}
