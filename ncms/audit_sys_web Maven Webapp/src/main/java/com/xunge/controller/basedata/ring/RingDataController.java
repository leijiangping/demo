package com.xunge.controller.basedata.ring;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.xunge.comm.StateComm;
import com.xunge.comm.TaskComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.BaseRet;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.service.basedata.ring.IRingService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.tool.IRegionService;

@Controller
@RequestMapping("asserts/tpl/basedata/ring")
public class RingDataController extends BaseException {

	@Autowired
	private IRingService ringService;
	
	@Resource
	private IRegionService regionService;
	
	@Resource
	private ISysRegionService sysRegionService;
	
	/**
	 * 查询所有电表数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryMeterPerf", method = RequestMethod.POST)
	public @ResponseBody BaseRet queryMeterPerf(HttpSession session,String city,String region,String resourcename,String resourcetype,String year,String month,String day,int cur_page_num, int page_count) {
		BaseRet baseRet = new BaseRet();
		try{
			String prvid = "";
			String regid="";
			String userId="";
			Object userObj = session.getAttribute("user");
			if(userObj == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			if (userObj != null) {
				UserLoginInfo userLoginInfo = (UserLoginInfo) userObj;
				prvid = userLoginInfo.getPrv_id();
				regid = userLoginInfo.getReg_id();
				userId=userLoginInfo.getUser_id();
			}
			String date ="";
			if(StringUtils.isNoneBlank(year)){
				date=date+year;
				if(StringUtils.isNoneBlank(month)){
					if(month.length()==1){
						month=StateComm.STATE_str0+month;
					}
					date=date+"-"+month;
					if(StringUtils.isNoneBlank(day)){
						if(date.length()==1){
							date=StateComm.STATE_str0+date;
						}
						date=date+"-"+day;
					}
				}
			}
			/*if(StringUtils.isNoneBlank(regid)){
				region=regid;
			}*/
			PageInfo<MeterPerfVO> page = ringService.findAllMeterPerf(userId,prvid,city,region,resourcename,resourcetype,date,cur_page_num, page_count);
			baseRet.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
			baseRet.setMessage(PromptMessageComm.SELECT_INFO_SUCCESS);
			baseRet.setData(page);
		}catch(Exception e){
			baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			baseRet.setMessage(PromptMessageComm.SELECT_FAIL);
		}
	    return baseRet;
	}
	
	/**
	 * 查询所有开关电源数据
	 * 
	 * @return
	 */

	@RequestMapping(value = "/queryPowerPerf", method = RequestMethod.POST)
	public @ResponseBody BaseRet queryPowerPerf(HttpSession session,String city,String region,String resourcename,String resourcetype,String year,String month,String day,int cur_page_num, int page_count) {
		BaseRet baseRet = new BaseRet();
		try{
			String prvid = "";
			String regid="";
			String userId="";
			Object userObj = session.getAttribute("user");
			if(userObj == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			if (userObj != null) {
				UserLoginInfo userLoginInfo = (UserLoginInfo) userObj;
				prvid = userLoginInfo.getPrv_id();
				regid = userLoginInfo.getReg_id();
				userId=userLoginInfo.getUser_id();
			}
			String date ="";
			if(StringUtils.isNoneBlank(year)){
				date=date+year;
				if(StringUtils.isNoneBlank(month)){
					if(month.length()==1){
						month=StateComm.STATE_str0+month;
					}
					date=date+"-"+month;
					if(StringUtils.isNoneBlank(day)){
						if(date.length()==1){
							date=StateComm.STATE_str0+date;
						}
						date=date+"-"+day;
					}
				}
			}
			/*if(StringUtils.isNoneBlank(regid)){
				region=regid;
			}*/
			PageInfo<PowerPerfVO> page = ringService.findAllPowerPerf(userId,prvid,city,region,resourcename,resourcetype,date,cur_page_num, page_count);
			baseRet.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
			baseRet.setMessage(PromptMessageComm.SELECT_INFO_SUCCESS);
			baseRet.setData(page);
		}catch(Exception e){
			baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			baseRet.setMessage(PromptMessageComm.SELECT_FAIL);
		}
	    return baseRet;
	}
	
	/**
	 * 导出所有电表数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exportMeterPerf")
	public @ResponseBody BaseRet exportMeterPerf(HttpSession session,HttpServletRequest request,HttpServletResponse response,String city,String region,String resourcename,String resourcetype,String year,String month,String day) {
		BaseRet baseRet = new BaseRet();
		try{
			String prvid = "";
			String regid="";
			String userId="";
			Object userObj = session.getAttribute("user");
			if(userObj == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			if (userObj != null) {
				UserLoginInfo userLoginInfo = (UserLoginInfo) userObj;
				prvid = userLoginInfo.getPrv_id();
				regid = userLoginInfo.getReg_id();
				userId=userLoginInfo.getUser_id();
			}
			String date ="";
			if(StringUtils.isNoneBlank(year)){
				date=date+year;
				if(StringUtils.isNoneBlank(month)){
					if(month.length()==1){
						month=StateComm.STATE_str0+month;
					}
					date=date+"-"+month;
					if(StringUtils.isNoneBlank(day)){
						if(date.length()==1){
							date=StateComm.STATE_str0+date;
						}
						date=date+"-"+day;
					}
				}
			}
			/*if(StringUtils.isNoneBlank(regid)){
				region=regid;
			}*/
			String requestUrl=request.getRequestURL().toString();
			String url=requestUrl.substring(0,requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
			String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_METER;
			
			String fileName = ringService.exportAllMeterPerf(userId,prvid,path,city,region,resourcename,resourcetype,date,request,response);
			if(StateComm.STATE_str1.equals(fileName)){
				baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
				baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
			}
			else{
				baseRet.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
				baseRet.setMessage(PromptMessageComm.OPERATION_SUSSESS);
				baseRet.setData(url+PromptMessageComm.WAY_OF_METER+fileName);
			}
			
		}catch(Exception e){
			baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	}
	
	
	/**
	 * 导出所有开关电源数据
	 * 
	 * @return
	 */

	@RequestMapping(value = "/exportPowerPerf")
	public @ResponseBody BaseRet exportPowerPerf(HttpSession session,HttpServletRequest request,HttpServletResponse response,String city,String region,String resourcename,String resourcetype,String year,String month,String day) {
		BaseRet baseRet = new BaseRet();
		try{
			String prvid = "";
			String regid="";
			String userId="";
			Object userObj = session.getAttribute("user");
			if(userObj == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			if (userObj != null) {
				UserLoginInfo userLoginInfo = (UserLoginInfo) userObj;
				prvid = userLoginInfo.getPrv_id();
				regid = userLoginInfo.getReg_id();
				userId=userLoginInfo.getUser_id();
			}
			String date ="";
			if(StringUtils.isNoneBlank(year)){
				date=date+year;
				if(StringUtils.isNoneBlank(month)){
					if(month.length()==1){
						month=StateComm.STATE_str0+month;
					}
					date=date+"-"+month;
					if(StringUtils.isNoneBlank(day)){
						if(date.length()==1){
							date=StateComm.STATE_str0+date;
						}
						date=date+"-"+day;
					}
				}
			}
			/*if(StringUtils.isNoneBlank(regid)){
				region=regid;
			}*/
			String requestUrl=request.getRequestURL().toString();
			String url=requestUrl.substring(0,requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
			String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_POWER;
			String fileName = ringService.exportAllPowerPerf(userId,prvid,path,city,region,resourcename,resourcetype,date,request,response);
			if(StateComm.STATE_str1.equals(fileName)){
				baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
				baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
			}
			else{
				baseRet.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
				baseRet.setMessage(PromptMessageComm.OPERATION_SUSSESS);
				baseRet.setData(url+PromptMessageComm.WAY_OF_POWER+fileName);
			}
			
		}catch(Exception e){
			baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	} 
	
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressRent", method = RequestMethod.POST)
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
	
	/**
	 * 获取地市列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/citys", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject getCitys(HttpServletRequest request){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
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
}
