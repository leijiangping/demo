package com.xunge.controller.selfelec.elecaudit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfelec.vo.ModelParamSetVO;
import com.xunge.service.selfelec.elecaudit.IModelParamSetService;
import com.xunge.service.system.log.ILogService;
/**
 * 标杆参数设置
 */
@RequestMapping("/asserts/tpl/selfelec/overproof")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class ModelParamSetController extends BaseException{
	@Autowired
	private ILogService log;
	
	@Autowired
	IModelParamSetService modelParamSetService;
	
	@RequestMapping(value = "/queryAllModel", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryAll(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> paramMap) {
		if(loginUser == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		paramMap.put("prvId", loginUser.getPrv_id());
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = modelParamSetService.queryAllModel(paramMap);
		fdback.success = RESULT.SUCCESS_1;
		return fdback;
	}
	
	@RequestMapping(value = "/insertModel",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insert(@RequestParam Map<String,Object> paramMap){
		FeedBackObject fdback = new FeedBackObject();
		String newRegId = paramMap.get("regId").toString();
		String newMonthNo = paramMap.get("monthNo").toString();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("prvId", "");
		param.put("monthNo", "");
		param.put("regId", "");
		List<ModelParamSetVO> allModelSet = modelParamSetService.queryAllNoPage(param);
		for(ModelParamSetVO model : allModelSet){
			if (model.getRegId().equalsIgnoreCase(newRegId) && newMonthNo.equalsIgnoreCase(model.getMonthNo()+"")){
				fdback.success = RESULT.SUCCESS_1;
				fdback.msg = PromptMessageComm.ADD_FAILED_INFO;
				return fdback;
			}
		}
		fdback.Obj = modelParamSetService.insertModel(paramMap);
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg = PromptMessageComm.ADD_SUCCESS;
		return fdback;
	}
	
	@RequestMapping(value = "/updateModel", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject updateMode(@RequestParam Map<String,Object> paramMap) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = modelParamSetService.updateModelParam(paramMap);
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg = PromptMessageComm.MODIFYED_SUCCESS;
		return fdback;
	}
	
	@RequestMapping(value = "/exportModel")
	public @ResponseBody FeedBackObject export(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		if(loginUser == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		//获取当前用户所属地区,权限控制
		map.put("state",StateComm.STATE_0+"");
		map.put("userId",loginUser.getUser_id());
		map.put("prvId", loginUser.getPrv_id());
		modelParamSetService.exportExcelData(map, request, response);
		feedbackObj.msg=PromptMessageComm.EXPORT_INFO_SUCCESS;
		return feedbackObj;
	}
}
