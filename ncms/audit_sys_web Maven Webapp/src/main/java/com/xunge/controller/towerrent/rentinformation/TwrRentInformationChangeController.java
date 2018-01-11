 package com.xunge.controller.towerrent.rentinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.service.towerrent.rentinformation.ITwrRentInformationChangeService;

@Controller
@RequestMapping("/twrRentInformationChange")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class TwrRentInformationChangeController extends BaseException{

	@Autowired
	private ITwrRentInformationChangeService twrRentInformationChangeService;
	
	@RequestMapping(value = "/queryTwrRentInformationChangePage", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryTwrRentInformationChangePage(@RequestParam Map<String, Object> params,@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		String pageNumberStr = (String) params.get("pageNumber");
		pageNumberStr = pageNumberStr != null ? pageNumberStr : StateComm.PAGE_NUMBER_STR;
		
		String pageSizeStr = (String) params.get("pageSize");
		pageSizeStr = pageSizeStr != null ? pageSizeStr : StateComm.PAGE_SIZE_STR;
		
		Integer pageNumber = Integer.parseInt(pageNumberStr);
		Integer pageSize = Integer.parseInt(pageSizeStr);
		
		//获取当前用户所属地区
		params.put("state",StateComm.STATE_0);
		params.put("userId",loginInfo.getUser_id());
		
		feedbackObj.Obj = twrRentInformationChangeService.queryTwrRentInformationChangePageByCondtion(params, pageNumber, pageSize);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	
	/**
	 * 导出铁塔账单数据
	 */
	@RequestMapping(value="/exportTwrRentInformationChange", method = RequestMethod.GET)
	public void exportTwrRentInformationChange(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> paramsMap,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		String pageNumberStr = (String) paramsMap.get("pageNumber");
		pageNumberStr = pageNumberStr != null ? pageNumberStr : StateComm.PAGE_NUMBER_STR;
		
		String pageSizeStr = (String) paramsMap.get("pageSize");
		pageSizeStr = pageSizeStr != null ? pageSizeStr : StateComm.PAGE_SIZE_STR;
		
		Integer pageNumber = Integer.parseInt(pageNumberStr);
		Integer pageSize = Integer.parseInt(pageSizeStr);
		
		String checkedStr = (String) paramsMap.get("checkedStr");
		List<Long> idsList = null;
		if(checkedStr != null && !"".equals(checkedStr)){
			idsList = new ArrayList<Long>();
			String[] values = checkedStr.split(",");
			Long[] longStr = null;
			if(values != null){
				longStr = (Long[])ConvertUtils.convert(values, Long.class);
			}
			idsList = Arrays.asList(longStr);
			if(idsList.size() > 0){
				paramsMap.put("idsList", idsList);
			}
		}
		
		//获取当前用户所属地区
		paramsMap.put("state",StateComm.STATE_0);
		paramsMap.put("userId",loginInfo.getUser_id());
		
		String selectStyle = (String) paramsMap.get("selectStyle");
		ExportParams params = new ExportParams(PromptMessageComm.MOBILE_RESOURCE_CHANGE_LOG, PromptMessageComm.MOBILE_RESOURCE_CHANGE_LOG, ExcelType.XSSF);
		Workbook workBook = null;
		if(selectStyle != null && SelfelecComm.NUMBER_0.equals(selectStyle)){
			//导出所有数据
			List<TwrRentInformationChangeVO> twrRentInformationChangeVoList = twrRentInformationChangeService.queryTwrRentInformationChangeVoListByCondtion(paramsMap);
			workBook = ExcelExportUtil.exportExcel(params, TwrRentInformationChangeVO.class, twrRentInformationChangeVoList);
		}else{
			//导出当前页所选数据
			Page<List<TwrRentInformationChangeVO>> page = twrRentInformationChangeService.queryTwrRentInformationChangeVoPageByCondtion(paramsMap, pageNumber, pageSize);
			workBook = ExcelExportUtil.exportExcel(params, TwrRentInformationChangeVO.class, page.getResult());
		}
		
		
        FileUtils.downFile(workBook, PromptMessageComm.MOBILE_RESOURCE_CHANGE_LOG_XLS, request, response);
        
        feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
}
