package com.xunge.controller.selfrent.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.service.selfrent.contract.ISelfRentService;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.region.ISysRegionService;
/**
 * 租费公共调用相关Controller
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class RentCommonController extends BaseException{

	@Autowired
	private ISysRegionService sysRegionService;

	@Autowired
	private ISelfRentService selfRentService;
	
	@Autowired
	private IDictionaryService dictionaryService;
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/rent/common/getAddressRent", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressRent(@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userId = loginInfo.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
	/**
	 * 根据省份id查询该省份下的供应商
	 */
	@RequestMapping(value = "/rent/common/queryDatSupplierByPrvID", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryDatSupplierByPrvID(@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("prvId", loginInfo.getPrv_id());
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = selfRentService.queryDatSupplierByPrvID(paraMap);
		fdback.success = RESULT.SUCCESS_1;
		return fdback;
	}
	/**
	 * 数据字典查询
	 * @param dictgroup_code
	 * @return
	 */
	@RequestMapping(value ="/rent/common/queryDictionaryByCode",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryDictionaryByCode(
			@ModelAttribute("user") UserLoginInfo loginInfo, String dictgroup_code){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = dictionaryService.queryDictionaryByCodeRedis(loginInfo.getPrv_id(), dictgroup_code);
		return feedbk;
	}
	
	
}
