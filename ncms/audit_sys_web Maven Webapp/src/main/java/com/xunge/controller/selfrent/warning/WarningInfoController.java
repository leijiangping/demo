package com.xunge.controller.selfrent.warning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.xunge.comm.StateComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.rent.warning.WarningComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.selfrent.contract.ISelfRentService;
import com.xunge.service.selfrent.contract.exceldatahandler.ContractInfoHandler;
import com.xunge.service.system.region.ISysRegionService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/selfrent/warning_m")
@Controller
/**
 * @description 预警信息controller
 * @author yuefy
 * @date 创建时间：2017年8月1日
 */
public class WarningInfoController extends BaseException{

	@Autowired
	private ISelfRentService selfRentService;

	@Autowired
	private ISysRegionService sysRegionService;
	
	/**
	 * @description 查询所有信息并显示
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value = "/queryRentContractVOWarning", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryRentContractVOWarning(@ModelAttribute("user") UserLoginInfo loginInfo, int pageNumber, int pageSize,String contractName,
			String regId, String pregId,String contractStartdate, String contractEnddate,
			String managerDepartment,String warningDate,HttpSession session) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		paraMap.put("contractName", contractName);
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("contractStartdate", contractStartdate);
		paraMap.put("contractEnddate", contractEnddate);
		paraMap.put("managerDepartment", managerDepartment);
		paraMap.put("warningDate", warningDate);
		
		String paraCode = WarningComm.CONT_ALERT;
		paraMap.put("paraCode", paraCode);
		paraMap.put("contractState", StateComm.STATE_0);
		Page<RentContractVO> rentContractPageVOs = null;
		rentContractPageVOs = selfRentService.queryRentContractVOWarning(paraMap, pageNumber, pageSize);
		List<String> deptList = getManagerDepartmentlst(rentContractPageVOs.getResult());

		FeedBackObject fdback = new FeedBackObject();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pageVO", rentContractPageVOs);
		resultMap.put("deptVO", deptList);
		fdback.Obj = resultMap;
		
		fdback.success = RESULT.SUCCESS_1;
		
		return fdback;
	}
	
	/**
	 * @description 获取用户区域信息
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value = "/getAddressRent", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressRent(@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userId = loginInfo.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
	
	/**
	 * @description 查询租费预警信息
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value = "/queryRentWarning", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryRentWarning(@ModelAttribute("user") UserLoginInfo loginInfo, boolean bIsRent, int pageNumber, int pageSize,String contractName,
			String regId, String pregId,String contractStartdate, String contractEnddate,
			String managerDepartment,String warningDate,HttpSession session){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		paraMap.put("contractName", contractName);
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("contractStartdate", contractStartdate);
		paraMap.put("contractEnddate", contractEnddate);
		paraMap.put("managerDepartment", managerDepartment);
		paraMap.put("warningDate", warningDate);
		
		String paraCode = WarningComm.RENT_ALERT;
		paraMap.put("paraCode", paraCode);
		paraMap.put("contractState", StateComm.STATE_0);
		Page<RentContractVO> rentContractPageVOs = null;
		rentContractPageVOs = selfRentService.queryRentContractVOWarning(paraMap, pageNumber, pageSize);
		List<String> deptList = getManagerDepartmentlst(rentContractPageVOs.getResult());

		FeedBackObject fdback = new FeedBackObject();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pageVO", rentContractPageVOs);
		resultMap.put("deptVO", deptList);
		fdback.Obj = resultMap;
		
		fdback.success = RESULT.SUCCESS_1;
		
		return fdback;
	}

	@SuppressWarnings("unchecked")
	private List<String> getManagerDepartmentlst(List<?> lstvo1) {
		//所属部门信息集合
		List<String> managerDepartmentlst = new ArrayList<String>();
		List<RentContractVO> lstvo2 = (List<RentContractVO>)lstvo1;
		for(RentContractVO vo : lstvo2){
			String managerDepartment = vo.getDatContractVO().getManagerDepartment();
			 if(managerDepartment != null && managerDepartment != ""){
				 if(!(managerDepartmentlst.contains(managerDepartment))){
					 managerDepartmentlst.add(managerDepartment);
				 }
			 }
		}
		return managerDepartmentlst;
	}
	
	/**
	 * @description 合同预警信息导出数据
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value="/exportContractWarning")
	public void exportContractWarning(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginInfo.getPrv_id());
		
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		String paraCode = WarningComm.CONT_ALERT;
		paraMap.put("paraCode", paraCode);
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		
		List<RentContractVO> list = selfRentService.queryRentContractVO(paraMap);
		ContractInfoHandler tbh=new ContractInfoHandler(listreg);//塔维变更信息自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		
		ExportParams params = new ExportParams();
		String[]  outData = {DateDisposeComm.PAYMENT_ENDDATE,DateDisposeComm.PLAY_DATE,DateDisposeComm.MANAGER_DEPARTMENT};
		params.setExclusions(outData);
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, RentContractVO.class,list);
       
		FileUtils.downFile(workBook, DateDisposeComm.CONTRACT_WARNING_XLS, request, response);
	}
	
	/**
	 * @description 租费预警信息导出数据
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value="/exportRentWarning")
	public void exportRentWarning(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginInfo.getPrv_id());
		
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		
		String paraCode = WarningComm.RENT_ALERT;
		paraMap.put("paraCode", paraCode);
		paraMap.put("contractName", request.getParameter("contractName"));
		paraMap.put("regId", request.getParameter("regId"));
		paraMap.put("pregId", request.getParameter("pregId"));
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("contractStartdate", request.getParameter("contractStartdate"));
		paraMap.put("contractEnddate", request.getParameter("contractEnddate"));
		paraMap.put("managerDepartment", request.getParameter("managerDepartment"));
		paraMap.put("warningDate", request.getParameter("warningDate"));
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		List<RentContractVO> list = selfRentService.queryRentContractVO(paraMap);
		ContractInfoHandler tbh=new ContractInfoHandler(listreg);//塔维变更信息自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		
		ExportParams params = new ExportParams();
		String[]  outData = {DateDisposeComm.CONTRACT_ALL_MOUNTH,DateDisposeComm.RESIDUE_DAYS,DateDisposeComm.CONTRACT_CHECKNAME_1,DateDisposeComm.CONTRACT_CHECKNAME_2};
		params.setExclusions(outData);
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, RentContractVO.class,list);
       
		FileUtils.downFile(workBook, DateDisposeComm.RENT_WARNING_XLS, request, response);
	}
}
