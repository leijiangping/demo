package com.xunge.controller.selfelec.billaccount;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.FeedBackObject;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.service.selfelec.billaccount.IEleBenchmarkBillaccountService;

/**
 * @descript 报账点标杆查询
 * @author 王昭
 * @date 2017-08-19 11:31:00
 */

@Controller
@RequestMapping("/asserts/tpl/selfelec/overproof")
public class BenchmarkBillaccountController extends BaseException {
	
	@Autowired
	private IEleBenchmarkBillaccountService eleBenchmarkBillaccountService;
	
	/**
	 * 额定功率
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryPowerRate", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryPowerRate(@RequestParam Map<String,Object> map,HttpServletRequest request){
		FeedBackObject feedBackObject = new FeedBackObject();
		
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		// 拼参数
		String strDate = map.get("year").toString()+map.get("month").toString();
		Date perDate = DateUtil.parse(strDate, SelfelecComm.FORMAT);
		map.put("monthstartday", strDate + SelfelecComm.NUMBER_STR);
		map.put("monthendday", strDate + DateUtil.gettotalDayOfMonth(perDate));
			
		feedBackObject.Obj = eleBenchmarkBillaccountService.queryPagePowerRate(map);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.SEARCH_SUCCESS;
		return feedBackObject; 
	}
	
	/**
	 * 额定功率数据导出
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportPowerRate")
	public void exportPowerRate(@RequestParam Map<String,Object> map, HttpServletRequest request,HttpServletResponse response){
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		// 拼参数
		String strDate = map.get("year").toString()+map.get("month").toString();
		Date perDate = DateUtil.parse(strDate, SelfelecComm.FORMAT);
		map.put("monthstartday", strDate + SelfelecComm.NUMBER_STR);
		map.put("monthendday", strDate + DateUtil.gettotalDayOfMonth(perDate));
			
		eleBenchmarkBillaccountService.exportPowerRate(map,request, response);
	}
	
	/**
	 * 开关电源负载
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryPowerload", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryPowerload(@RequestParam Map<String,Object> map,HttpServletRequest request){
		FeedBackObject feedBackObject = new FeedBackObject();
		
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		// 拼参数
		String strDate = map.get("year").toString()+map.get("month").toString();
		Date perDate = DateUtil.parse(strDate, SelfelecComm.FORMAT);
		map.put("monthstartday", strDate + SelfelecComm.NUMBER_STR);
		map.put("monthendday", strDate + DateUtil.gettotalDayOfMonth(perDate));
			
		feedBackObject.Obj = eleBenchmarkBillaccountService.queryPagePowerload(map);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.SEARCH_SUCCESS;
		return feedBackObject;
	}
	
	/**
	 * 开关电源负载数据导出
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportPowerload")
	@ResponseBody
	public FeedBackObject exportPowerload(@RequestParam Map<String,Object> map, HttpServletRequest request,HttpServletResponse response){
		FeedBackObject feedBackObject = new FeedBackObject();
		
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		// 拼参数
		String strDate = map.get("year").toString()+map.get("month").toString();
		Date perDate = DateUtil.parse(strDate, SelfelecComm.FORMAT);
		map.put("monthstartday", strDate + SelfelecComm.NUMBER_STR);
		map.put("monthendday", strDate + DateUtil.gettotalDayOfMonth(perDate));
			
		eleBenchmarkBillaccountService.exportPowerload(map, request, response);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.SEARCH_SUCCESS;
		return feedBackObject;
	}
	
	/**
	 * 智能电表
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryElectricmeter", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryElectricmeter(@RequestParam Map<String,Object> map,HttpServletRequest request){
		FeedBackObject feedBackObject = new FeedBackObject();
		
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		// 拼参数
		String strDate = map.get("year").toString()+map.get("month").toString();
		Date perDate = DateUtil.parse(strDate, SelfelecComm.FORMAT);
		map.put("monthstartday", strDate + SelfelecComm.NUMBER_STR);
		map.put("monthendday", strDate + DateUtil.gettotalDayOfMonth(perDate));
			
		feedBackObject.Obj = eleBenchmarkBillaccountService.queryPageElectricmeter(map);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.SEARCH_SUCCESS;
		return feedBackObject;
	}
	
	/**
	 * 智能电表数据导出
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportElectricmeter")
	@ResponseBody
	public FeedBackObject exportElectricmeter(@RequestParam Map<String,Object> map, HttpServletRequest request,HttpServletResponse response){
		FeedBackObject feedBackObject = new FeedBackObject();
		
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("pregIds", loginInfo.getPreg_ids());
		map.put("regIds", loginInfo.getReg_ids());
		// 拼参数
		String strDate = map.get("year").toString()+map.get("month").toString();
		Date perDate = DateUtil.parse(strDate, SelfelecComm.FORMAT);
		map.put("monthstartday", strDate + SelfelecComm.NUMBER_STR);
		map.put("monthendday", strDate + DateUtil.gettotalDayOfMonth(perDate));
			
		eleBenchmarkBillaccountService.exportElectricmeter(map, request, response);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.SEARCH_SUCCESS;
		return feedBackObject;
	}
}
