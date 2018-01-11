package com.xunge.controller.towerrent.rentmanager;

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
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.towerrent.rentinformationbizchange.ITwrRentInformationBizChangeService;
import com.xunge.service.twrrent.rentinformationbizchange.exceldatahandler.TowerChangeInfoHandler;

/**
 * 铁塔资源信息变更controller
 * @author yuefy
 * @date 2017.07.18
 *
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/towerrent/rentmanage")
@Controller
public class TowerChangeInfoController extends BaseException {
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private ITwrRentInformationBizChangeService twrRentInformationBizChangeService;
	
	@Autowired
	private ILogService log;
	
	
	/**
	 * 查询所有铁塔资源变更信息并显示
	 * @param pregId
	 * @param regId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryTowerChangeInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerResourceInfo(@ModelAttribute("user") UserLoginInfo loginUser,
			String pregId,String regId, Integer pageNumber, Integer pageSize) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		paraMap.put("regIds", regIds);
		FeedBackObject fdback = new FeedBackObject();
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = twrRentInformationBizChangeService.queryInformationBizChangeCheckInfo(paraMap,pageNumber,pageSize);
		return fdback;
	}
	
	/**
	 * 导入变更信息
	 * @return
	 */
	@RequestMapping(value = "/importTowerChangeInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject importTowerChangeInfo(@ModelAttribute("user") UserLoginInfo loginUser,HttpServletRequest request,String suffix,
			MultipartFile file) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("prvId", loginUser.getPrv_id());
		paraMap.put("userLoginname", loginUser.getUser_loginname());
		paraMap.put("state", StateComm.STATE_0);
		feedbackObj.success = RESULT.SUCCESS_1;
		try {
			Map<String,Object> returnMap= twrRentInformationBizChangeService.importTowerChangeInfo(file,suffix,request,paraMap);
			feedbackObj.Obj = returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_TOWERRENT_CHANGE_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
		}
		return feedbackObj;
	}
	
	
	/**
	 * 导出变更信息数据
	 * @author yuefy
	 */
	@RequestMapping(value="/exportTowerChangeInfo")
	public void exportTowerChangeInfo(@ModelAttribute("user") UserLoginInfo loginUser,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginUser.getPrv_id());
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		paraMap.put("regIds", regIds);
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		paraMap.put("regId", request.getParameter("regId"));
		paraMap.put("pregId", request.getParameter("pregId"));
		List<TowerRentinformationBizchangeVO> list = twrRentInformationBizChangeService.queryTowerRentinformationBizchangeInfo(paraMap);
		TowerChangeInfoHandler tbh=new TowerChangeInfoHandler(listreg);//塔维变更信息自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.COUNTY});//需要处理数据的列名 
		
		ExportParams params = new ExportParams();

		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerRentinformationBizchangeVO.class,list);
       
		FileUtils.downFile(workBook, PromptMessageComm.BUSINESS_CHANGES_NOTICE_INFO_XLS, request, response);
	}
	
}
