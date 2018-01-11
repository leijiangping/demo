package com.xunge.controller.towerrent.settlement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.twrrent.settlement.ITowerMobileBillCheckService;

/**
 * 
 * 塔维结算账单确认和账单对账controller
 *
 */
@RequestMapping("/asserts/tpl/towerrent/settlement")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class TowerMobileBillCheckController extends BaseException{
	@Autowired
	ITowerMobileBillCheckService towerMobileBillCheckService;
	
	/**
	 * 账单对账结果查询
	 * @param loginUser
	 * @param regId
	 * @param accountMonth
	 * @param businessConfirmNumber
	 * @param towerStationCodeOrName
	 * @param checkResult
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * 2017年7月10日 lpw
	 */
	@RequestMapping(value = "/queryTowerAndMobileFee", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerAndMobileFee(@ModelAttribute("user") UserLoginInfo loginInfo,String regId,String pregId,String accountPeroid,
			String businessConfirmNumber,String towerStationCodeOrName,String resCompare,int pageNumber, int pageSize){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback =new FeedBackObject();
		//封装参数
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("accountPeroid", accountPeroid);
		paraMap.put("dataTypeTower", StateComm.STATE_str1);
		paraMap.put("dataTypeMobile", SelfelecComm.NUMBER_STR_2);
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("businessConfirmNumber", businessConfirmNumber);
		paraMap.put("towerStationCodeOrName", towerStationCodeOrName);
		paraMap.put("resCompare", resCompare);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj=towerMobileBillCheckService.queryTowerAndMobileFee(paraMap,pageNumber,pageSize);
		return fdback;
	}
	/**
	 * 账单确认结果查询
	 * @param session
	 * @param regId
	 * @param pregId
	 * @param accountPeroid
	 * @param businessConfirmNumber
	 * @param towerStationCodeOrName
	 * @param resCompare
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryTowerAndMobileConfirmBalance", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerAndMobileConfirmBalance(@ModelAttribute("user") UserLoginInfo loginInfo,String regId,String pregId,String accountPeroid,
			String businessConfirmNumber,String towerStationCodeOrName,String resCompare,int pageNumber, int pageSize){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback =new FeedBackObject();
		//封装参数
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("accountPeroid", accountPeroid);
		paraMap.put("dataTypeTower", StateComm.STATE_str1);
		paraMap.put("dataTypeMobile", SelfelecComm.NUMBER_STR_2);
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("businessConfirmNumber", businessConfirmNumber);
		paraMap.put("towerStationCodeOrName", towerStationCodeOrName);
		paraMap.put("resCompare", resCompare);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj=towerMobileBillCheckService.queryTowerAndMobileConfirmBalance(paraMap,pageNumber,pageSize);
		return fdback;
	}
	/**
	 * 进行账单修改
	 * @param billConfirms
	 * @return
	 */
	@RequestMapping(value = "/updateTowerMobileBillState", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateTowerMobileBillState(@RequestBody List<TowerBillbalanceVO> billConfirmList){
		FeedBackObject fdback =new FeedBackObject();
	/*	List<TowerBillbalanceVO> billConfirmList = new ArrayList<>();
		billConfirmList.add(billConfirms);*/
		fdback.Obj=towerMobileBillCheckService.updateTowerMobileBillState(billConfirmList);
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg = PromptMessageComm.OPERATION_SUSSESS;
		return fdback;
	}
	
	/**
	 * 进行账单确认
	 * @param billConfirms
	 * @return
	 */
	@RequestMapping(value = "/updateTowerMobileBillConfirmState", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateTowerMobileBillConfirmState(@RequestParam Map<String,Object> map){
		FeedBackObject fdback =new FeedBackObject();
		fdback.Obj=towerMobileBillCheckService.updateTowerMobileBillConfirmState(map);
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg = PromptMessageComm.OPERATION_SUSSESS;
		return fdback;
	}
	/**
	 *  取消账单确认
	 * @return
	 */
	@RequestMapping(value = "/updateCancleConfirmState", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateCancleConfirmState(@RequestBody List<String> towerbillbalanceIds){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback =new FeedBackObject();
		paraMap.put("towerbillbalanceIds", towerbillbalanceIds);
		paraMap.put("confirmState", StateComm.STATE_0);
		paraMap.put("afterAdjustmentFee", null);
		fdback.Obj=towerMobileBillCheckService.updateCancleConfirmState(paraMap);
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg = PromptMessageComm.OPERATION_SUSSESS;
		return fdback;
	}
	
	/**
	 * 账单对账导出
	 * @return
	 */
	@RequestMapping(value="/exportBillCheckInfo")
	public void exportBillCheckInfo(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("dataTypeTower", StateComm.STATE_str1);
		paraMap.put("dataTypeMobile", SelfelecComm.NUMBER_STR_2);
		List<TowerAndMobileBillVO> list = towerMobileBillCheckService.queryAllTowerAndMobileFee(paraMap);
		ExportParams params = new ExportParams(PromptMessageComm.BILL_RECONCILIATION, PromptMessageComm.BILL_RECONCILIATION, ExcelType.HSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerAndMobileBillVO.class, list);
        FileUtils.downFile(workBook, PromptMessageComm.BILL_RECONCILIATION+DateDisposeComm.SUFF_XLS, request, response);
        feedbackObj.success = RESULT.SUCCESS_1;
        feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
	/**
	 * 账单确认导出
	 * @return
	 */
	@RequestMapping(value="/exportBillConfirmInfo")
	public void exportBillConfirmInfo(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("dataTypeTower", StateComm.STATE_str1);
		paraMap.put("dataTypeMobile", SelfelecComm.NUMBER_STR_2);
		List<TowerAndMobileBillConfirmVO> list = towerMobileBillCheckService.queryTowerAndMobileConfirmBill(paraMap);
		ExportParams params = new ExportParams(PromptMessageComm.BILL_CONFIRM, PromptMessageComm.BILL_CONFIRM, ExcelType.HSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerAndMobileBillConfirmVO.class, list);
		FileUtils.downFile(workBook, PromptMessageComm.TW_BILL_CONFIRM+DateDisposeComm.SUFF_XLS, request, response);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
	
	/**
	 * 查询用户信息
	 * @param session
	 * @param paraMap
	 */
	public void  queryUserInfo(@ModelAttribute("user") UserLoginInfo loginInfo,Map<String, Object> paraMap){
		//查询参数
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("state", StateComm.STATE_0);
	}
	
	/**
	 * 根据账单id集合查询对应的账单
	 * @author xup
	 * @param towerbillbalanceIds
	 * @return
	 */
	@RequestMapping(value="/queryTowerBillbalanceByIds", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTowerBillbalanceByIds(@RequestParam Map<String,Object> map ){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj=towerMobileBillCheckService.queryTowerBillbalanceByIds(map);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	
}
