package com.xunge.controller.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.service.basedata.supplier.ISupplierService;
import com.xunge.service.tool.IRegionService;

@Controller
@RequestMapping("/asserts/tpl")
public class BaseController extends BaseException {

	@Resource
	private IRegionService regionService;
	
	@Resource
	private ISupplierService supplierService;

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
	/**
	 * 获取区县列表
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value="/getCurrUser")
	public @ResponseBody FeedBackObject getCurrUser(HttpServletRequest request){	
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = loginUser;
		return feedbackObj;
	}
	
	@RequestMapping(value="/suppliers")
	public @ResponseBody FeedBackObject getSuppliers(@RequestBody List<String> list,HttpServletRequest request){
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prvId = loginInfo.getPrv_id();
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = supplierService.getSupplierList(prvId,list);
		return feedbackObj;
	}
	
	/**
	 * 轮询请求进度条数据
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/queryProcessInfo", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject queryProcessInfo(String isStart,String suffix,HttpServletRequest request){
    	FeedBackObject feedbackObj = new FeedBackObject();
		try {
			JSONObject obj=new JSONObject();
			if(request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS_MSG+suffix)!=null&&request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS+suffix)!=null){
				//新的进度条
				if(isStart.equals(PromptMessageComm.RESULT_TRUE)&&request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS+suffix).toString().equals(PromptMessageComm.STR_100)){
					request.getSession().removeAttribute(PromptMessageComm.IMPORT_PROCESS+suffix);
					request.getSession().removeAttribute(PromptMessageComm.IMPORT_PROCESS_MSG+suffix);

					obj.put(PromptMessageComm.IMPORT_PROCESS_MSG,PromptMessageComm.IS_LOADING);
					obj.put(PromptMessageComm.IMPORT_PROCESS, PromptMessageComm.STR_5);
				}else{
					obj.put(PromptMessageComm.IMPORT_PROCESS_MSG, request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS_MSG+suffix).toString());
					obj.put(PromptMessageComm.IMPORT_PROCESS, request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS+suffix).toString());
				}
				//如果进度完成，则清除Session
				if(request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS_MSG+suffix)!=null&&request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS+suffix)!=null
						&&request.getSession().getAttribute(PromptMessageComm.IMPORT_PROCESS+suffix).toString().equals(PromptMessageComm.STR_100)){
					request.getSession().removeAttribute(PromptMessageComm.IMPORT_PROCESS+suffix);
					request.getSession().removeAttribute(PromptMessageComm.IMPORT_PROCESS_MSG+suffix);
				}
			}
			
			feedbackObj.Obj=obj;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.ASK_PROGRESS_SUCCESS;
		}catch (Exception e) {
			// TODO Auto-generated catch block
//			throw new BusinessException("处理导入进度失败，请重试...");
			e.printStackTrace();
		}
		return feedbackObj;
	}
}
