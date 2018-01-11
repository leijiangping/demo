package com.xunge.controller.selfelec.elecaudit;

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
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfelec.vo.HistoryPaymentModelVO;
import com.xunge.service.selfelec.elecaudit.IHistoryPaymentModelService;
import com.xunge.service.system.log.ILogService;
/**
 * 
 * @author jiacy
 * @date 2017年8月21日
 * @description 超标稽核管理--历史缴费标杆控制器
 */
@RequestMapping("/asserts/tpl/selfelec/overproof")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class HistoryPaymentModelController extends BaseException{
	@Autowired
	private ILogService log;
	
	@Autowired
	IHistoryPaymentModelService historyPaymentModelService;
	
	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryAll(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> paramMap) {
		if(loginUser == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		paramMap.put("prvId", loginUser.getPrv_id());
		FeedBackObject fdback = new FeedBackObject();
		Page<HistoryPaymentModelVO> result = historyPaymentModelService.queryAll(paramMap);
		int resultSize = result.getResult().size();
		result.setTotal(resultSize);
		int pageNumber = Integer.parseInt(paramMap.get("pageNumber").toString());
		int pageSize = Integer.parseInt(paramMap.get("pageSize").toString());
		result.setStartRow((pageNumber - SelfelecComm.NUMBER_1) * pageSize + SelfelecComm.NUMBER_1);
		if (resultSize >= pageNumber * pageSize){
			result.setEndRow(pageNumber * pageSize);
		}else{
			result.setEndRow(resultSize);
		}
		fdback.Obj = result;
		fdback.success = RESULT.SUCCESS_1;
		return fdback;
	}
	
	@RequestMapping(value = "/export")
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
		historyPaymentModelService.exportExcelData(map, request, response);
		feedbackObj.msg=PromptMessageComm.EXPORT_INFO_SUCCESS;
		return feedbackObj;
	}
}
