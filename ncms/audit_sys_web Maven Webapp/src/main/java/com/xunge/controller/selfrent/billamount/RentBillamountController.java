package com.xunge.controller.selfrent.billamount;


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

import com.google.common.collect.Maps;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfrent.billAccount.VPaymentVO;
import com.xunge.model.selfrent.billamount.RentBillamountVO;
import com.xunge.service.selfrent.billaccount.IPaymentService;
import com.xunge.service.selfrent.billamount.IRentBillamountService;
import com.xunge.service.system.log.ILogService;


/**
 * @description 租费报账汇总Controller
 * @author zhujj
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "/asserts/tpl/selfrent/rentbillamount")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class RentBillamountController extends BaseException{

	@Autowired
	private IRentBillamountService<RentBillamountVO> rentBillamountService;
	@Autowired
	private IPaymentService paymentService;
	@Autowired
	private ILogService log;
	/**
	 * @description 报账汇总分页数据查询
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryRentamount",method=RequestMethod.POST)
	@ResponseBody
	public  FeedBackObject queryRentamount(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		//获取当前用户所属地区,权限控制
		map.put("regIds",loginInfo.getReg_ids());
		feedbackObj.Obj =rentBillamountService.queryRentBillamountPage(map);
		return feedbackObj;
	}
	/**
	 * @description 查询待汇总缴费信息
     * @param hashMaps
	 * @param hashMaps:pageNumber（必填）当前页
	 * @param hashMaps:pageSize（必填）每页显示多少条
	 * @param hashMaps:billType 报账类型
	 * @param hashMaps:paymentMethod 支付方式
	 * @param hashMaps:contractCode 合同编码
	 * @param hashMaps:payment_id 缴费信息主键
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/queryPaymentNyNoAmount",method=RequestMethod.POST)
	@ResponseBody
	public  FeedBackObject queryContractPaymentByNoAmount(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		//获取当前用户所属地区,权限控制
		map.put("regIds",loginInfo.getReg_ids());
		feedbackObj.Obj =paymentService.queryContractPaymentByNoAmount(map);
		return feedbackObj;
	}
	/**
	 * @description 生成租费报账汇总
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/createRentBillamount",method=RequestMethod.POST)
	@ResponseBody
	public  FeedBackObject createRentBillamount(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestBody List<String> ids,HttpServletRequest request){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String,Object> map=Maps.newHashMap();
		try{
			map.put("paymentIds", ids);
			//获取当前用户所属地区,权限控制
			map.put("regIds",loginInfo.getReg_ids());
			List<VPaymentVO> list = paymentService.queryContractPaymentByNoAmountList(map);
			map.put("userInfo", loginInfo);
			map =rentBillamountService.insertRentBillamount(list,map);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg=PromptMessageComm.BILL_GENERATATED_SUCCESS;
			feedbackObj.Obj=map;
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch(Exception e){
			e.printStackTrace();
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.BILL_GENERATATED_FAILE;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	/**
	 * @description 根据租费报账汇总Id查询租费报账汇总信息
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/queryRentBillamountById",method=RequestMethod.POST)
	@ResponseBody
	public  FeedBackObject queryRentBillamountById(@RequestParam Map<String,Object> map){

		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.Obj=rentBillamountService.queryRentBillamountById(map);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg=PromptMessageComm.SELECT_BILLAMOUNT_SUCCESS;
		return feedbackObj;
	}
	/**
	 * @description 导出缴费明细
	 * @param Map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/exportPaymentDatil")
	
	public void exportPaymentDatil(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("regIds",loginInfo.getReg_ids());
		List<VPaymentVO> list = paymentService.queryContractPaymentByNoAmountList(map);
		
		ExportParams params = new ExportParams(DateDisposeComm.PAY_DETAIL, DateDisposeComm.PAY_DETAIL, ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VPaymentVO.class, list);
        FileUtils.downFile(workBook, DateDisposeComm.PAY_DETAIL_XLS, request, response);
	}
	/**
	 * @description 导出缴费明细PDF
	 * @param Map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/exportPaymentDatilPDF")
	
	public void exportPaymentDatilPDF(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("regIds",loginInfo.getReg_ids());
		List<VPaymentVO> list = paymentService.queryContractPaymentByNoAmountList(map);
		
		ExportParams params = new ExportParams(DateDisposeComm.PAY_DETAIL, DateDisposeComm.PAY_DETAIL, ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VPaymentVO.class, list);
        FileUtils.downFile(workBook, DateDisposeComm.PAY_DETAIL_XLS, request, response);
	}
}