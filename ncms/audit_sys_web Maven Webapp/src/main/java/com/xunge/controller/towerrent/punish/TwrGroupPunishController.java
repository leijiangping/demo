package com.xunge.controller.towerrent.punish;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.xunge.comm.PunishComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.punish.ITwrGroupPunishService;
import com.xunge.service.twrrent.punish.ITwrGroupRegPunishService;
import com.xunge.service.twrrent.settlement.ITowerBillbalanceService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
@RequestMapping(value = "/asserts/tpl/towerrent/assessment")
public class TwrGroupPunishController  extends BaseException{
	
	@Autowired
	private ITwrGroupPunishService twrGroupPunishService;
	
	@Autowired
	private ILogService log;
	
	@Autowired
	private ITowerBillbalanceService towerBillbalanceService;
	
	@Autowired
	private ITwrGroupRegPunishService twrGroupRegPunishService;
	
	/**
	 * 集团既定考核指标扣罚 页面模糊查询
	 * @param loginInfo
	 * @param pageNumber
	 * @param pageSize
	 * @param pRegId
	 * @param regId
	 * @param punishYearMonth
	 * @param towerstationcode
	 * @param ifduty
	 * @param ifcal
	 * @return
	 */
	@RequestMapping(value ="/queryGroupPunishVO",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryGroupPunishVO(
			@ModelAttribute("user") UserLoginInfo loginInfo,Integer pageNumber,Integer pageSize,
			String pRegId,String punishYearMonth,String towerstationcode,
			Integer ifduty,Integer ifcal){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("relationState",StateComm.STATE_0);
		paraMap.put("state",PunishComm.STATE_0);
		paraMap.put("userId",loginInfo.getUser_id());
		paraMap.put("pRegId",pRegId);
		paraMap.put("punishYearMonth",punishYearMonth);
		paraMap.put("towerStationCode",towerstationcode);
		paraMap.put("ifduty",ifduty);
		paraMap.put("ifcal",ifcal);
		fdback.Obj = twrGroupPunishService.queryGroupPunishVO(paraMap, pageNumber, pageSize);
		return fdback;
	}
	/**
	 * 删除集团既定扣罚信息
	 * @param twrGroupPunishId
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value ="/deleteGroupPunish",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject deleteGroupPunish(String twrGroupPunishId,
			@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = twrGroupPunishService.deleteGroupPunish(twrGroupPunishId);
		if(fdback.success.equals(RESULT.SUCCESS_1)){
			fdback.msg = PromptMessageComm.USER_DELETE_DEDUCTION_INFO_SUCCESS+loginInfo.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, fdback.msg);
		}else{
			fdback.success = RESULT.FAIL_0;
			fdback.msg = PromptMessageComm.USER_DELETE_DEDUCTION_INFO_FAILED+loginInfo.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, fdback.msg);
		}	
		return fdback;
	}
	
	/**
	 * 导入扣罚信息
	 * @return
	 */
	@RequestMapping(value = "/importGroupPunish", method = RequestMethod.POST)
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
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		feedbackObj.success = RESULT.SUCCESS_1;
		try {
			Map<String,Object> returnMap= twrGroupPunishService.importGroupPunish(file,suffix,request,paraMap);
			feedbackObj.Obj = returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.USER_IMPORT_DEDUCTION_INFO_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");
		}catch (Exception e) {
			throw new BusinessException(PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE);
		}
		return feedbackObj;
	}
	
	/**
	 * 计算扣罚金额
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/queryPunishAmount", method = RequestMethod.POST)
	public @ResponseBody 
	FeedBackObject queryPunishAmount(String pregId,String punishYearMonth,
			@ModelAttribute("user") UserLoginInfo loginUser) throws ParseException{
			if(loginUser == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			FeedBackObject fdback = new FeedBackObject();
			String userId = loginUser.getUser_id();//获取当前登陆用户id
			String msg = twrGroupPunishService.calSumPunishAmount(pregId,punishYearMonth,userId);
			fdback.success = RESULT.SUCCESS_1;
			if(fdback.success.equals(RESULT.SUCCESS_1)){
				fdback.msg = msg+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
				// 添加系统日志
				log.info(SysLogComm.LOG_Operate, fdback.msg);
			}else{
				fdback.success = RESULT.FAIL_0;
				fdback.msg = msg+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
				// 添加系统日志
				log.err(SysLogComm.LOG_Error, fdback.msg);
			}	
			return fdback;
	}
	
	
	/**
	 * 导出集团扣罚信息数据
	 * @author changwq
	 */
	@RequestMapping(value="/exportTowerResourceInfo")
	public void exportTowerResourceInfo(
			@ModelAttribute("user") UserLoginInfo loginInfo,Integer pageNumber,Integer pageSize,
			String pRegId,String regId,String punishYearMonth,String towerstationcode,
			Integer ifduty,Integer ifcal,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}

		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_GROUP_ASSESSMENT_DEDUCTION_INFO_SUCCESS;
		
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("relationState",StateComm.STATE_0);
		paraMap.put("state",StateComm.STATE_0);
		paraMap.put("userId",loginInfo.getUser_id());
		paraMap.put("pRegId",pRegId);
		paraMap.put("regId",regId);
		paraMap.put("punishYearMonth",punishYearMonth);
		paraMap.put("towerStationCode",towerstationcode);
		paraMap.put("ifduty",ifduty);
		paraMap.put("ifcal",ifcal);
		List<TwrGroupPunishVO> list = twrGroupPunishService.queryGroupPunish(paraMap);
		ExportParams params = new ExportParams(PromptMessageComm.GROUP_ASSESSMENT_DEDUCTION_INFO, PromptMessageComm.GROUP_ASSESSMENT_DEDUCTION_INFO, ExcelType.HSSF);

		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TwrGroupPunishVO.class,list);
        FileUtils.downFile(workBook, PromptMessageComm.GROUP_ASSESSMENT_DEDUCTION_INFO_XLS, request, response);
	}
}
