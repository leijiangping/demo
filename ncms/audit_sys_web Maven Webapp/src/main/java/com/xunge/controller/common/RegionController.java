package com.xunge.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.tool.IRegionService;

/**
 * 区县管理
* 
* Title: RegionController
* @author Rob
 */
@RestController
@RequestMapping("/asserts/tpl/common/region")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class RegionController extends BaseException {

	@Resource
	private IRegionService regionService;
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	/**
	 * 获取地市列表
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value="/citys", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject getCitys(@ModelAttribute("user") UserLoginInfo loginInfo) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		feedbackObj.Obj = regionService.getCitys(prv_id);
		return feedbackObj;
	}
	/**
	 * 获取区县列表
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value="/regions", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject getRegions(@Validated String cityId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = regionService.getRegion(cityId);
		return feedbackObj;
	}
	
	/**
	 * @description 获取区县列表
	 * @author yuefy
	 * @date 创建时间：2017年8月14日
	 */
	@RequestMapping(value = "/getAddressElec", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressRent(@ModelAttribute("user") UserLoginInfo loginUser) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userId = loginUser.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
}
