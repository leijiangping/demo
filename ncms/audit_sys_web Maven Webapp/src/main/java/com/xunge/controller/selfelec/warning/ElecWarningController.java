package com.xunge.controller.selfelec.warning;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.github.pagehelper.PageInfo;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.rent.warning.WarningComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfo;
import com.xunge.model.selfelec.VEleContract;
import com.xunge.model.selfelec.VEleContractCuring;
import com.xunge.service.selfelec.billamount.IElecBillamountService;
import com.xunge.service.selfelec.eleccontract.IEleccontractService;
import com.xunge.service.selfrent.contract.IDatPaymentPeriodService;
import com.xunge.service.system.region.ISysRegionService;

@RestController
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/selfelec/warning")
public class ElecWarningController extends BaseException {

	@Autowired
	private IEleccontractService eleccontractService;
	
	@Autowired
	private IDatPaymentPeriodService datPaymentPeriodService;

	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private IElecBillamountService elecBillamountService;
	
	/**
	 * @description 查询电费合同预警信息列表 
	 * @author yuefy
	 * @date 创建时间：2017年9月7日
	 */
	@RequestMapping(value = "/queryEleccontractWarningList", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryEleccontractWarningList(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> map,Integer dataFrom) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		map.put("prvId", loginUser.getPrv_id());
		map.put("regId",map.get("regId"));
		map.put("pregId",map.get("pregId"));
		map.put("regIds",loginUser.getReg_ids());
		map.put("pregIds",loginUser.getPreg_ids());
		map.put("contractItem",map.get("contractItem"));
		map.put("isDownShare",ContractComm.IS_DOWNSHAR_YES);
		map.put("auditState", ActivityStateComm.STATE_NORMAL);
		map.put("contractState", ContractStateComm.STATE_0);
		map.put("contractStartdate", map.get("contractStartdate"));
		map.put("contractEnddate", map.get("contractEnddate"));
		map.put("managerDepartment", map.get("managerDepartment"));
		
		//系统录入及导入数据
		if(DataFromComm.STATE_0 == dataFrom){
			map.put("dataFrom",dataFrom);
			map.put("dataFrom1",DataFromComm.STATE_1);
			//接口采集数据
		}else if(DataFromComm.STATE_2 == dataFrom){
			map.put("dataFrom",dataFrom);
		}
		String paraCode = WarningComm.CONT_ALERT;
		map.put("paraCode", paraCode);
		FeedBackObject backObject = new FeedBackObject();
		PageInfo<VEleContract> vEleContractList = eleccontractService.queryEleccontractWarningList(map);
		List<String> deptList = getManagerDepartmentlst(vEleContractList.getList());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("vEleContractList", vEleContractList);
		resultMap.put("deptVO", deptList);
		backObject.Obj = resultMap;
		backObject.success = RESULT.SUCCESS_1;
		return backObject;
	}
	
	
	/**
	 * @description 查询电费预警信息列表 
	 * @author yuefy
	 * @date 创建时间：2017年9月7日
	 */
	@RequestMapping(value = "/queryElecWarningList", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryElecWarningList(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> map) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		map.put("prvId", loginUser.getPrv_id());
		map.put("regIds",loginUser.getReg_ids());
		map.put("pregIds",loginUser.getPreg_ids());
		map.put("auditState", ActivityStateComm.STATE_NORMAL);
		map.put("nowDateTime", new Date());
		FeedBackObject backObject = new FeedBackObject();
		PageInfo<VEleBillaccountPaymentInfo> page= new PageInfo<VEleBillaccountPaymentInfo>();
		List<VEleBillaccountPaymentInfo> list = elecBillamountService.selectBillamountPaymentDetails(map);
		if(list.size() > Integer.parseInt(SelfelecComm.NUMBER_0)){
			page= new PageInfo<VEleBillaccountPaymentInfo>(list);
		}
		backObject.success = RESULT.SUCCESS_1;
		backObject.Obj = page;
		return backObject;
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getManagerDepartmentlst(List<?> lstvo1) {
		//所属部门信息集合
		List<String> managerDepartmentlst = new ArrayList<String>();
		List<VEleContract> lstvo2 = (List<VEleContract>)lstvo1;
		for(VEleContract vo : lstvo2){
			String managerDepartment = vo.getManagerDepartment();
			 if(managerDepartment != null && managerDepartment != ""){
				 if(!(managerDepartmentlst.contains(managerDepartment))){
					 managerDepartmentlst.add(managerDepartment);
				 }
			 }
		}
		return managerDepartmentlst;
	}
	
	
	/**
	 * @description 查询缴费周期
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	@RequestMapping(value ="/queryPaymentPeriod")
	public @ResponseBody FeedBackObject queryPaymentPeriod(){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = datPaymentPeriodService.queryAllDatPaymentPeriod();
		return feedbk;
	}
	
	/**
	 * @description 电费预警信息导出数据
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value="/exportEleWarning")
	public void exportEleContractWarning(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		map.put("prvId", loginInfo.getPrv_id());
		map.put("regIds",loginInfo.getReg_ids());
		map.put("pregIds",loginInfo.getPreg_ids());
		map.put("auditState", ActivityStateComm.STATE_NORMAL);
		List<VEleBillaccountPaymentInfo> list = elecBillamountService.selectBillamountPaymentDetails(map);
		ExportParams params = new ExportParams();
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VEleBillaccountPaymentInfo.class,list);
		
		FileUtils.downFile(workBook, DateDisposeComm.ELEC_WARNING_XLS, request, response);
		
	}
	
	
	/**
	 * @description 电费合同预警信息导出数据
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value="/exportEleContractWarning")
	public void exportEleWarning(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg=PromptMessageComm.SEARCH_SUCCESS;//"查询合同信息成功";
		
		map.put("prvId", loginInfo.getPrv_id());
		map.put("regId",map.get("regId"));
		map.put("pregId",map.get("pregId"));
		map.put("regIds",loginInfo.getReg_ids());
		map.put("pregIds",loginInfo.getPreg_ids());
		map.put("contractItem",map.get("contractItem"));
		map.put("isDownShare",ContractComm.IS_DOWNSHAR_YES);
		map.put("auditState", ActivityStateComm.STATE_NORMAL);
		map.put("contractState", ContractStateComm.STATE_0);
		map.put("contractStartdate", map.get("contractStartdate"));
		map.put("contractEnddate", map.get("contractEnddate"));
		map.put("managerDepartment", map.get("managerDepartment"));
		//系统录入及导入数据
		Integer dataFrom = Integer.parseInt((String)map.get("dataFrom"));
		String paraCode = WarningComm.CONT_ALERT;
		map.put("paraCode", paraCode);
		if(DataFromComm.STATE_0 == dataFrom){
			map.put("dataFrom",dataFrom);
			map.put("dataFrom1",DataFromComm.STATE_1);
			map.put(DateDisposeComm.EXPORT_FLAG, DateDisposeComm.EXPORT_FLAG);
			PageInfo<VEleContractCuring> lists = eleccontractService.queryAllEleContractCuring(map);
			List<VEleContractCuring> list = lists.getList();
			ExportParams params = new ExportParams();
			String[]  outData = {DateDisposeComm.PLAY_DATE,DateDisposeComm.MANAGER_DEPARTMENT,DateDisposeComm.PAYMENT_ENDDATE};
			params.setExclusions(outData);
			org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VEleContractCuring.class,list);
	       
			FileUtils.downFile(workBook, DateDisposeComm.ELEC_CACHING_WARNING_XLS, request, response);
			//接口采集数据
		}else if(DataFromComm.STATE_2 == dataFrom){
			map.put("dataFrom",dataFrom);
			map.put(DateDisposeComm.EXPORT_FLAG, DateDisposeComm.EXPORT_FLAG);
			PageInfo<VEleContract> lists = eleccontractService.queryEleccontractWarningList(map);
			List<VEleContract> list = lists.getList();
			ExportParams params = new ExportParams();
			String[]  outData = {DateDisposeComm.PLAY_DATE,DateDisposeComm.MANAGER_DEPARTMENT,DateDisposeComm.PAYMENT_ENDDATE};
			params.setExclusions(outData);
			org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VEleContract.class,list);
	       
			FileUtils.downFile(workBook, DateDisposeComm.ELEC_CONTRACT_WARNING_XLS, request, response);
		}
	}
    
    
}