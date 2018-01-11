package com.xunge.controller.towerelec.payment;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.IncludeAndLossComm;
import com.xunge.comm.elec.MeterTypeComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.common.BaseController;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.selfelec.EleBillaccount;
import com.xunge.model.selfelec.EleBillaccountPaymentdetail;
import com.xunge.model.selfelec.ElePaymentdetail;
import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBillaccountInfo;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfo;
import com.xunge.model.selfelec.VEleBillaccountcontract;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfelec.billaccount.IElecBillAccountService;
import com.xunge.service.selfelec.payment.IElecPaymentService;
import com.xunge.service.system.role.ISysRoleService;

@Controller
@RequestMapping("/asserts/tpl/towerelec/payment")
public class TowerPaymentController extends BaseController {

	@Autowired
	private IElecPaymentService elecPaymentService;
	@Autowired
	private ISysRoleService iSysRoleService;

	@Autowired
	private IElecBillAccountService elecReimburseInfoService;
	
	@Autowired
	private IActTaskService actTaskService;

	@RequestMapping(value="/export")
	public void export(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("regIds",loginInfo.getReg_ids());
		PageInfo<VEleBillaccountPaymentInfo> pageinfo = elecPaymentService.queryEleBillaccountPayment(map, 0, 10);
//		PageInfo<VEleBillaccountPaymentInfo> queryVEleBillaccountPaymentInfo(UserLoginInfo loginInfo , VEleBillaccountPaymentInfo param,0, 10);
		
		ExportParams params = new ExportParams(DateDisposeComm.PAY_DETAIL, DateDisposeComm.PAY_DETAIL, ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VEleBillaccountPaymentInfo.class, pageinfo.getList());
        FileUtils.downFile(workBook, DateDisposeComm.PAY_DETAIL_XLS, request, response);
	}
	/**
	 * 判断合同是否审核通过
	 * @param billaccountId
	 * @return
	 */
	@RequestMapping(value="/queryStateByBillaccountId", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryStateByBillaccountId(String billaccountId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
//		VEleBillaccountcontract contract = elecPaymentService.getVeleBillaccountContract(billaccountId);
		feedbackObj.Obj =elecPaymentService.getVeleBillaccountContract(billaccountId);
		return feedbackObj;
	}
	
	/**
	 * 查询标杆信息
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getBenchmark", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject getBenchmark(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		// "billaccountId":billaccountId ,
		// "billamountStartdate":billamountStartdate,"billamountEnddate":billamountEnddate,"totalDegreeActual":totalDegreeActual

		/**
		 * <table id="resourcetreetable" class=
		 * "table table-hover table-striped" >
		 * <thead>
		 * <tr>
		 * <th></th>
		 * <th>资源类型</th>
		 * <th>资源编码</th>
		 * <th>资源名称</th>
		 * <th>资源状态</th>
		 * <th></th>
		 * </tr>
		 * </thead> <tbody>
		 * 
		 * </tbody>
		 * </table>
		 */

		FeedBackObject fdback = new FeedBackObject();

		String totalDegreeActualStr = request.getParameter("totalDegreeActual");
		String billamountStartdateStr = request.getParameter("billamountStartdate");
		String billamountEnddateStr = request.getParameter("billamountEnddate");
		SimpleDateFormat sdf = new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd);

		StringBuffer htmlSb = new StringBuffer();
		htmlSb.append("<table id=\"benchmarktb\" class=\"table table-hover table-striped\">");
		htmlSb.append(
				"<thead><tr><th><font color='red'>标杆类型</font></th><th><font color='red'>标杆值</font></th><th><font color='red'>是否超标</font></th><th><font color='red'>超标率</font></th></tr></thead>");
		htmlSb.append("<tbody>");

		try {

			Double totalDegreeActual = Double.parseDouble(totalDegreeActualStr);

			String billaccountId = request.getParameter("billaccountId");

			// billaccountId = "77fb7ecf759f4d32aa3b303722fec748";

			Map<String, Object> param = new HashMap<>();
			param.put("billaccountId", billaccountId);
			param.put("startdate", sdf.parse(billamountStartdateStr));
			param.put("enddate", sdf.parse(billamountEnddateStr));

			elecPaymentService.getBenchmark(param);

			Map<String, Object> resultMap = param;

			Object errCodeObj = resultMap.get("errCode");
			if (errCodeObj != null && (int) errCodeObj == StateComm.STATE_0) {
				NumberFormat nFormat = NumberFormat.getNumberInstance();
				nFormat.setMaximumFractionDigits(2);// 设置小数点后面位数为

				// 智能电表标杆
				BigDecimal electricmeterBenchmark = (BigDecimal) resultMap.get("electricmeterBenchmark");
				// 动环负载标杆
				BigDecimal powerloadBenchmark = (BigDecimal) resultMap.get("powerloadBenchmark");
				// 额定功率标杆
				BigDecimal powerratingBenchmark = (BigDecimal) resultMap.get("powerratingBenchmark");
				// 环比历史电量标杆
				BigDecimal hisNowBenchmark = (BigDecimal) resultMap.get("hisNowBenchmark");
				// 同比历史电量标杆
				BigDecimal hisLastBenchmark = (BigDecimal) resultMap.get("hisLastBenchmark");

				// 同比历史电量标杆
				if (hisLastBenchmark == null) {
					htmlSb.append(
							"<tr><td><font color='red'>历史标杆-同比</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td></tr>");
				} else {
					if (totalDegreeActual > hisLastBenchmark.doubleValue()) {
						htmlSb.append("<tr><td><font color='red'>历史标杆-同比</font></td><td><font color='red'>"
								+ hisLastBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>是</font></td><td><font color='red'>"
								+ nFormat.format((totalDegreeActual - hisLastBenchmark.doubleValue()) * 100
										/ hisLastBenchmark.doubleValue())
								+ "%</font></td></tr>");
					} else {
						htmlSb.append("<tr><td><font color='red'>历史标杆-同比</font></td><td><font color='red'>"
								+ hisLastBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>否</font></td><td><font color='red'>0.00%</font></td></tr>");
					}
				}

				// 环比历史电量标杆
				if (hisNowBenchmark == null) {
					htmlSb.append(
							"<tr><td><font color='red'>历史标杆-环比</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td></tr>");
				} else {
					if (totalDegreeActual > hisNowBenchmark.doubleValue()) {
						htmlSb.append("<tr><td><font color='red'>历史标杆-环比</font></td><td><font color='red'>"
								+ hisNowBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>是</font></td><td><font color='red'>"
								+ nFormat.format((totalDegreeActual - hisNowBenchmark.doubleValue()) * 100
										/ hisNowBenchmark.doubleValue())
								+ "%</font></td></tr>");
					} else {
						htmlSb.append("<tr><td><font color='red'>历史标杆-环比</font></td><td><font color='red'>"
								+ hisNowBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>否</font></td><td><font color='red'>0.00%</font></td></tr>");
					}
				}

				// 额定功率标杆
				if (powerratingBenchmark == null) {
					htmlSb.append(
							"<tr><td><font color='red'>额定功率标杆</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td></tr>");
				} else {
					if (totalDegreeActual > powerratingBenchmark.doubleValue()) {
						htmlSb.append("<tr><td><font color='red'>额定功率标杆</font></td><td><font color='red'>"
								+ powerratingBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>是</font></td><td><font color='red'>"
								+ nFormat.format((totalDegreeActual - powerratingBenchmark.doubleValue()) * 100
										/ powerratingBenchmark.doubleValue())
								+ "%</font></td></tr>");
					} else {
						htmlSb.append("<tr><td><font color='red'>额定功率标杆</font></td><td><font color='red'>"
								+ powerratingBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>否</font></td><td><font color='red'>0.00%</font></td></tr>");
					}
				}

				// 智能电表标杆
				if (electricmeterBenchmark == null) {
					htmlSb.append(
							"<tr><td><font color='red'>智能电表标杆</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td></tr>");
				} else {
					if (totalDegreeActual > electricmeterBenchmark.doubleValue()) {
						htmlSb.append("<tr><td><font color='red'>智能电表标杆</font></td><td><font color='red'>"
								+ electricmeterBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>是</font></td><td><font color='red'>"
								+ nFormat.format((totalDegreeActual - electricmeterBenchmark.doubleValue()) * 100
										/ electricmeterBenchmark.doubleValue())
								+ "%</font></td></tr>");
					} else {
						htmlSb.append("<tr><td><font color='red'>智能电表标杆</font></td><td><font color='red'>"
								+ electricmeterBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>否</font></td><td><font color='red'>0.00%</font></td></tr>");
					}
				}

				// 动环负载标杆
				if (powerloadBenchmark == null) {
					htmlSb.append(
							"<tr><td><font color='red'>动环负载标杆</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td><td><font color='red'>-</font></td></tr>");
				} else {
					if (totalDegreeActual > powerloadBenchmark.doubleValue()) {
						htmlSb.append("<tr><td><font color='red'>动环负载标杆</font></td><td><font color='red'>"
								+ powerloadBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>是</font></td><td><font color='red'>"
								+ nFormat.format((totalDegreeActual - powerloadBenchmark.doubleValue()) * 100
										/ powerloadBenchmark.doubleValue())
								+ "%</font></td></tr>");
					} else {
						htmlSb.append("<tr><td><font color='red'>动环负载标杆</font></td><td><font color='red'>"
								+ powerloadBenchmark.doubleValue()
								+ "</font></td><td><font color='red'>否</font></td><td><font color='red'>0.00%</font></td></tr>");
					}
				}

				fdback.success = RESULT.SUCCESS_1;
			} else {
				fdback.msg = PromptMessageComm.NO_RESULT;
				fdback.success = RESULT.FAIL_0;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		htmlSb.append("</tbody>");
		htmlSb.append("</table>");
		fdback.Obj = htmlSb.toString();

		return fdback;
	}

	/**
	 * 审批提交流程
	 * 
	 * @param loginUser
	 * @param ids
	 * @return
	 * @author xup
	 */
	@RequestMapping(value = "/commitProcess", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject commitProcess(HttpSession session, @RequestBody List<Map<String, Object>> ids) {
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		feedbackObj.Obj = elecPaymentService.updateActivityCommit(ids, loginUser);
		feedbackObj.msg = PromptMessageComm.SUBMITTED_SUCCESS;
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}

	/**
	 * 查询报账点缴费信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryVEleBillaccountPaymentInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryVEleBillaccountPaymentInfo(HttpSession session,
			VEleBillaccountPaymentInfo param, int pageNumber, int pageSize) {
		UserLoginInfo loginInfo = (UserLoginInfo) session.getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecPaymentService.queryVEleBillaccountPaymentInfo(loginInfo, param, pageNumber, pageSize);

		return fdback;
	}

	/**
	 * 查询报账点缴费信息审核页面
	 * 
	 * @param session
	 * @param auditingState
	 * @param submitState
	 * @param taskDefKey
	 * @param billaccountCode
	 * @param billaccountName
	 * @param pregId
	 * @param regId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author xup
	 */
	@RequestMapping(value = "/queryEleBillaccountPaymentInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryEleBillaccountPayment(HttpSession session, String auditingState,
			String submitState, String taskDefKey, String billaccountCode, String billaccountName, String pregId,
			String regId, int pageNumber, int pageSize) {
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String assignee = loginUser.getUser_loginname();
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup = iSysRoleService.queryRoleNameById(list);
		Act act = new Act();
		act.setProcDefKey(ActUtils.EleBillaccountPaymentdetail[0]);
		act.setBusinessTable(ActUtils.EleBillaccountPaymentdetail[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if (taskDefKey != null) {
			act.setTaskDefKey(taskDefKey);// 设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());// 区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject feedbackObj = new FeedBackObject();
		Page<?> page = new Page<>(pageNumber, pageSize);
		feedbackObj.Obj = page;
		if (idsList.size() > 0) {
			/*
			 * String userId = loginUser.getUser_id(); List<String> regIds =
			 * loginUser.getReg_ids(); map.put("regIds", regIds);
			 * map.put("alias", "v_sys_region"); map.put("state",
			 * StateComm.STATE_0); map.put("userId", userId);
			 */
			map.put("idsList", idsList);
			map.put("billaccountName", billaccountName);
			map.put("auditingState", auditingState);
			map.put("pregId", pregId);
			map.put("regId", regId);
			map.put("billaccountCode", billaccountCode);
			map.put("submitState", submitState);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.Obj = elecPaymentService.queryEleBillaccountPayment(map, pageNumber, pageSize);
		}
		return feedbackObj;
	}

	/**
	 * 保存流程审核
	 * 
	 * @param ids
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value = "/reviewBillAccountAudit", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCheckInfo(@RequestBody List<Map<String, Object>> ids,
			HttpServletRequest request) {
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = elecPaymentService.updateAuditCompelet(ids, loginUser);
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = ActivityStateComm.AUDIT_NORMAL;
		return feedbk;
	}

	/**
	 * 查询当前审核人
	 * 
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value = "/queryCurUser", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryCurUser(HttpServletRequest request) {
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String user_loginname = loginInfo.getUser_loginname();
		List<String> list = new ArrayList<String>();
		list.add(user_loginname);
		feedbk.Obj = list;
		return feedbk;
	}

	@RequestMapping(value = "/submitBillaccountDetail", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject submitBillaccountDetail(EleBillaccountPaymentdetail detail,
			HttpServletRequest request, HttpServletResponse response) {

		FeedBackObject fdback = new FeedBackObject();

		if (detail != null) {

			fdback.success = RESULT.SUCCESS_1;
			elecPaymentService.saveOrUpdateEleBillaccountPaymentdetail(detail);
			fdback.Obj = detail;
		} else {
			fdback.success = RESULT.FAIL_0;
		}

		return fdback;
	}

	/**
	 * 保存更新 报账点缴费详情
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateBillaccountDetail", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject saveOrUpdateBillaccountDetail(EleBillaccountPaymentdetail detail,
			HttpServletRequest request, HttpServletResponse response) {

		FeedBackObject fdback = new FeedBackObject();

		if (detail != null) {
			detail.setAuditingState(ActivityStateComm.STATE_UNCOMMITTED);
			fdback.success = RESULT.SUCCESS_1;
			int saveOrUpdateEleBillaccountPaymentdetail = elecPaymentService.saveOrUpdateEleBillaccountPaymentdetail(detail);
			if(saveOrUpdateEleBillaccountPaymentdetail!=1){
				fdback.msg=PromptMessageComm.DO_NOT_REPEAT_PAYMENTS;
				fdback.success = RESULT.FAIL_0;
			}
			fdback.Obj = detail;
		} else {
			fdback.success = RESULT.FAIL_0;
		}

		return fdback;
	}

	/**
	 * 保存更新 电表缴费详情
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateDetail", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject saveOrUpdateDetail(HttpServletRequest request, HttpServletResponse response) {

		String param = request.getParameter("param");
		FeedBackObject fdback = new FeedBackObject();

		if (StringUtils.isNoneBlank(param)) {

			List<ElePaymentdetail> details = JSON.parseArray(param, ElePaymentdetail.class);

			for (ElePaymentdetail detail : details) {
				elecPaymentService.saveOrUpdateElePaymentdetail(detail);
			}

			fdback.success = RESULT.SUCCESS_1;
		} else {
			fdback.success = RESULT.FAIL_0;
		}

		return fdback;
	}

	// 审核
	@RequestMapping(value = "/batchreview", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject batchreview(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String jsonStr = request.getParameter("params");
		List<EleBillaccountPaymentdetail> ids = JSON.parseArray(jsonStr, EleBillaccountPaymentdetail.class);
		String userId = request.getParameter("userId");
		String stateStr = request.getParameter("state");
		Integer state = null;
		if (stateStr != null) {
			state = Integer.parseInt(stateStr);
		}

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecPaymentService.batchreviewEleBillaccountPaymentdetail(ids, userId, state);

		return fdback;
	}

	/**
	 * 查询 （修改）电表信息 并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryUpdateElectricmeterInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryUpdateElectricmeterInfo(EleBillaccountPaymentdetail detail) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;

		VEleBillaccountcontract contract = elecPaymentService.getVeleBillaccountContract(detail.getBillaccountId());
		JSONObject jso = new JSONObject();

		// 拼接 li 列表
		StringBuffer liSb = new StringBuffer();

		// 拼接 tab content
		StringBuffer tabSb = new StringBuffer();

		/**
		 * 根据是否是包干合同 判断是否需要动态获取电表信息
		 * 
		 */
		if (contract != null && contract.getIsIncludeAll() != null && contract.getIsIncludeAll() != IncludeAndLossComm.YES) {
			List<VEleBillaccountInfo> meterlist = elecPaymentService
					.getVEleBillaccountInfoByBPaymentdetailId(detail.getBillaccountpaymentdetailId());
			if (meterlist == null || meterlist.size() == 0) {

				fdback.success = RESULT.FAIL_0;
				if (null != contract.getContractId() && !StrUtil.isEmpty(contract.getContractId())){
					jso.put("contract", contract);
					fdback.Obj = jso;
				}
				return fdback;
			}

			// 第一个li
			boolean firstflag = true;
			String liactive = null;
			String tabactive = null;

			String elecontractPrices;
			String[] elecontractPrice;
			for (VEleBillaccountInfo meter : meterlist) {
				if (firstflag) {
					liactive = "class=\"active\"";
					tabactive = " active";

					firstflag = false;
				} else {
					liactive = "";
					tabactive = "";
				}

				liSb.append("<li " + liactive + "><a href=\"#").append(meter.getMeterId())
						.append("_info\" data-toggle=\"tab\">").append(meter.getMeterCode()).append("</a></li>");

				if (meter.getMeterType() == MeterTypeComm.Meter_type1) {
					// 普通表
					tabSb.append("<div class=\"tab-pane fade in " + tabactive + "\" id=\"" + meter.getMeterId()
							+ "_info\">" + "   	<form id=\"" + meter.getMeterId() + "_infoForm\" action=\"\">"
							+ "<input type=\"hidden\" name=\"paymentdetailId\" id=\"paymentdetailId\" value=\""
							+ meter.getPaymentdetailId() + "\" />"
							+ "<input type=\"hidden\" name=\"billaccountpaymentdetailId\" id=\"billaccountpaymentdetailId\" value=\""
							+ meter.getBillaccountpaymentdetailId() + "\" />"
							+ "<input type=\"hidden\" name=\"meterId\" value=\"" + meter.getMeterId() + "\" />"
							+ "       <ul class=\"list-group\">                                                  "
							+ "        	<li class=\"list-group-item row\">                                      "
							+ "               <div class=\"col-md-2\">电表编码</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getMeterCode()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表类型</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\"普通表\" id=\"meterType\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表户号</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getAccountNumber()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">上期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"lastReadnum\" id=\"lastReadnum\" value=\""
							+ convertNullValue(meter.getLastReadnum()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">本期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"nowReadnum\" id=\"nowReadnum\"  value=\""
							+ convertNullValue(meter.getNowReadnum()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表倍率</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"meterRate\" id=\"meterRate\"  value=\""
							+ convertNullValue(meter.getMeterRate()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">本期度数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"nowDegree\" id=\"nowDegree\" disabled  value=\""
							+ convertNullValue(meter.getNowDegree()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">移动分摊比例</div>                         "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatio\"  disabled value=\""
							+ subZeroAndDot(convertNullValue(contract.getCmccRatio())) + "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">%</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">分摊后度数</div>                           "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatioAfter\" disabled >                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "                                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  ");

					elecontractPrices = contract.getElecontractPrice();
					if (elecontractPrices != null) {
						elecontractPrice = elecontractPrices.split("\\|");

						String useDegrees = meter.getUseDegrees();
						String[] useDegreeArr = { "", "", "", "" };
						if (StringUtils.isNoneBlank(useDegrees)) {
							useDegreeArr = useDegrees.split("\\|");
						}

						String useDegreeTmp = null;
						for (int i = 0; i < elecontractPrice.length; i++) {
							if (i < useDegreeArr.length) {
								useDegreeTmp = useDegreeArr[i];
							}
							tabSb.append(
									"           <li class=\"list-group-item row\">                                     "
											+ "               <div class=\"col-md-2\">电费单价" + (i + 1)
											+ "</div>                            "
											+ "               <div class=\"col-md-2\">                                           "
											+ "                   <input type=\"text\" class=\"form-control\" name=\"elecPrice\" disabled value=\""
											+ convertNullValue(elecontractPrice[i]) + "\">                   "
											+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\">用电量" + (i + 1)
											+ "</div>                              "
											+ "               <div class=\"col-md-2\">                                           "
											+ "                   <input type=\"text\" class=\"form-control\"  name=\"useDegrees\" value=\""
											+ convertNullValue(useDegreeTmp) + "\">                   "
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\"></div>                                     "
											+ "               <div class=\"col-md-2\">                                           "
											+ "               </div>                                                             "
											+ "           </li>                                                                  ");
						}
					}
					tabSb.append("           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">不含税金额</div>                           "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"payamountNotax\" id=\"payamountNotax\" value=\""
							+ convertNullValue(meter.getPayamountNotax()) + "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">税金</div>                                 "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  name=\"tax\"    id=\"tax\" disabled value=\""
							+ convertNullValue(contract.getContractTax()) + "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "                                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  ");

					if (contract.getIncludeLoss() != null && contract.getIncludeLoss() == IncludeAndLossComm.YES) {
						if (elecontractPrices != null) {
							elecontractPrice = elecontractPrices.split("\\|");
							String lossDegrees = meter.getLossDegrees();
							String[] lossDegreeArr = { "", "", "", "" };
							if (StringUtils.isNoneBlank(lossDegrees)) {
								lossDegreeArr = lossDegrees.split("\\|");
							}

							String lossDegreeTmp = null;
							for (int i = 0; i < elecontractPrice.length; i++) {
								if (i < lossDegreeArr.length) {
									lossDegreeTmp = lossDegreeArr[i];
								}
								tabSb.append(
										"           <li class=\"list-group-item row\">                                     "
												+ "               <div class=\"col-md-2\">电损单价" + (i + 1)
												+ "</div>                            "
												+ "               <div class=\"col-md-2\">                                           "
												+ "                   <input type=\"text\" disabled class=\"form-control\" value=\""
												+ convertNullValue(elecontractPrice[i]) + "\">                   "
												+ "               </div>                                                             "
												+ "               <div class=\"col-md-2\">电损电量" + (i + 1)
												+ "</div>                            "
												+ "               <div class=\"col-md-2\">                                           "
												+ "                   <input type=\"text\" class=\"form-control\"  name=\"lossDegrees\" value=\""
												+ convertNullValue(lossDegreeTmp) + "\">                   "
												+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
												+ "               </div>                                                             "
												+ "               <div class=\"col-md-2\"></div>                                     "
												+ "               <div class=\"col-md-2\">                                           "
												+ "               </div>                                                             "
												+ "           </li>                                                                  ");
							}

							tabSb.append(
									"           <li class=\"list-group-item row\">                                     "
											+ "               <div class=\"col-md-2\">电损金额</div>                             "
											+ "               <div class=\"col-md-2\">                                           "
											+ "                   <input type=\"text\" class=\"form-control\" name=\"meterLoss\" id=\"meterLoss\" value=\""
											+ convertNullValue(meter.getMeterLoss()) + "\">                   "
											+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\"></div>                                     "
											+ "               <div class=\"col-md-2\">                                           "
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\"></div>                                     "
											+ "               <div class=\"col-md-2\">                                           "
											+ "               </div>                                                             "
											+ "           </li>                                                                  "
											+ "       </ul>                                                                      "
											+ "       </form>                                                                    "
											+ "   </div>                                                                         ");
						}
					}
				} else if (meter.getMeterType() == MeterTypeComm.Meter_type2) {
					// 平谷峰表
					tabSb.append("<div class=\"tab-pane fade in " + tabactive + "\" id=\"" + meter.getMeterId()
							+ "_info\">" + "   	<form id=\"" + meter.getMeterId() + "_infoForm\" action=\"\">"
							+ "<input type=\"hidden\" name=\"paymentdetailId\" id=\"paymentdetailId\" value=\""
							+ meter.getPaymentdetailId() + "\" />"
							+ "<input type=\"hidden\" name=\"billaccountpaymentdetailId\" id=\"billaccountpaymentdetailId\" value=\""
							+ meter.getBillaccountpaymentdetailId() + "\" />"
							+ "<input type=\"hidden\" name=\"meterId\" value=\"" + meter.getMeterId() + "\" />"
							+ "       <ul class=\"list-group\">                                                  "
							+ "        	<li class=\"list-group-item row\">                                      "
							+ "               <div class=\"col-md-2\">电表编码</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getMeterCode()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表类型</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\"平峰谷表\" id=\"meterType\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表户号</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getAccountNumber()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">上期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled name=\"lastReadnum\" id=\"lastReadnum\" value=\""
							+ convertNullValue(meter.getLastReadnum()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">本期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" disabled class=\"form-control\" name=\"nowReadnum\" id=\"nowReadnum\"  value=\""
							+ convertNullValue(meter.getNowReadnum()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表倍率</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled name=\"meterRate\" id=\"meterRate\"  value=\""
							+ convertNullValue(meter.getMeterRate()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">本期度数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"nowDegree\" id=\"nowDegree\" disabled  value=\""
							+ convertNullValue(meter.getNowDegree()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">移动分摊比例</div>                         "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatio\"  disabled value=\""
							+ subZeroAndDot(convertNullValue(contract.getCmccRatio())) + "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">%</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">分摊后度数</div>                           "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatioAfter\" disabled >                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "                                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  ");

					tabSb.append("                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">平单价</div>                         "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" id=\"flatPrice\" disabled value=\""
							+ convertNullValue(contract.getFlatPrice()) + "\">             "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">平上期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\"  name=\"lastFlatReadnum\" id=\"lastFlatReadnum\" value=\""
							+ convertNullValue(meter.getLastFlatReadnum()) + "\">             "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">平本期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    	<input type=\"text\" class=\"form-control\" name=\"nowFlatReadnum\" id=\"nowFlatReadnum\" value=\""
							+ convertNullValue(meter.getNowFlatReadnum()) + "\">               "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">峰单价</div>                         "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" id=\"peakPrice\" disabled value=\""
							+ convertNullValue(contract.getPeakPrice()) + "\">             "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">峰上期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\"  name=\"lastPeakReadnum\" id=\"lastPeakReadnum\" value=\""
							+ convertNullValue(meter.getLastPeakReadnum()) + "\">             "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">峰本期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    	<input type=\"text\" class=\"form-control\" name=\"nowPeakReadnum\" id=\"nowPeakReadnum\" value=\""
							+ convertNullValue(meter.getNowPeakReadnum()) + "\">               "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">谷单价</div>                         "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" id=\"valleyPrice\"  value=\""
							+ convertNullValue(contract.getValleyPrice()) + "\">             "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">谷上期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\"  name=\"lastValleyReadnum\" id=\"lastValleyReadnum\" value=\""
							+ convertNullValue(meter.getLastValleyReadnum()) + "\">             "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">谷本期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    	<input type=\"text\" class=\"form-control\" name=\"nowValleyReadnum\" id=\"nowValleyReadnum\" value=\""
							+ convertNullValue(meter.getNowValleyReadnum()) + "\">               "
							+ "                    </div>                                                       "
							+ "                </li>                                                            ");

					if (contract.getTopPrice() != null) {
						tabSb.append("                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">尖单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"topPrice\" disabled value=\""
								+ convertNullValue(contract.getTopPrice()) + "\">             "
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">尖上期读数</div>                     "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\"  name=\"lastTopReadnum\" id=\"lastTopReadnum\" value=\""
								+ convertNullValue(meter.getLastTopReadnum()) + "\">             "
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">尖本期读数</div>                     "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    	<input type=\"text\" class=\"form-control\" name=\"nowTopReadnum\" id=\"nowTopReadnum\" value=\""
								+ convertNullValue(meter.getNowTopReadnum()) + "\">               "
								+ "                    </div>                                                       "
								+ "                </li>");
					}

					tabSb.append("                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">不含税金额</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" name=\"payamountNotax\" id=\"payamountNotax\" value=\""
							+ convertNullValue(meter.getPayamountNotax()) + "\">             "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">税金</div>                           "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\"  name=\"tax\"   id=\"tax\"  value=\""
							+ convertNullValue(contract.getContractTax()) + "\">             "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                                                                                 "
							+ "                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                </li>                                                            ");

					String lossDegrees = meter.getLossDegrees();
					String[] lossDegreeArr = { "", "", "", "" };
					if (StringUtils.isNoneBlank(lossDegrees)) {
						lossDegreeArr = lossDegrees.split("\\|");
					}

					String flatlossDegreeTmp = "", peaklossDegreeTmp = "", valleylossDegreeTmp = "",
							toplossDegreeTmp = "";
					if (lossDegreeArr.length > 0) {
						if (lossDegreeArr.length < 2) {
							flatlossDegreeTmp = lossDegreeArr[0];
						} else if (lossDegreeArr.length < 3) {
							flatlossDegreeTmp = lossDegreeArr[0];
							peaklossDegreeTmp = lossDegreeArr[1];
						} else if (lossDegreeArr.length < 4) {
							flatlossDegreeTmp = lossDegreeArr[0];
							peaklossDegreeTmp = lossDegreeArr[1];
							valleylossDegreeTmp = lossDegreeArr[2];
						} else if (lossDegreeArr.length < 5) {
							flatlossDegreeTmp = lossDegreeArr[0];
							peaklossDegreeTmp = lossDegreeArr[1];
							valleylossDegreeTmp = lossDegreeArr[2];
							toplossDegreeTmp = lossDegreeArr[3];
						}
					}

					if (contract.getIncludeLoss() != null && contract.getIncludeLoss() == IncludeAndLossComm.YES) {
						tabSb.append("                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">平单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"flatPrice\"  value=\""
								+ convertNullValue(contract.getFlatPrice()) + "\">             "
								+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">电损电量1</div>                      "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\" value=\""
								+ convertNullValue(flatlossDegreeTmp) + "\">             "
								+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\"></div>                               "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    </div>                                                       "
								+ "                </li>                                                            "
								+ "                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">峰单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"peakPrice\" disabled value=\""
								+ convertNullValue(contract.getPeakPrice()) + "\">             "
								+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">电损电量2</div>                      "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\"  value=\""
								+ convertNullValue(peaklossDegreeTmp) + "\">             "
								+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\"></div>                               "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    </div>                                                       "
								+ "                </li>                                                            "
								+ "                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">谷单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"valleyPrice\" disabled value=\""
								+ convertNullValue(contract.getValleyPrice()) + "\">             "
								+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">电损电量3</div>                      "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\"  value=\""
								+ convertNullValue(valleylossDegreeTmp) + "\" >             "
								+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\"></div>                               "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    </div>                                                       "
								+ "                </li>                                                            ");

						if (contract.getTopPrice() != null) {
							tabSb.append(
									"                <li class=\"list-group-item row\">                               "
											+ "                    <div class=\"col-md-2\">尖单价</div>                         "
											+ "                    <div class=\"col-md-2\">                                     "
											+ "                        <input type=\"text\" class=\"form-control\" id=\"topPrice\" disabled value=\""
											+ convertNullValue(contract.getTopPrice()) + "\">             "
											+ "                    </div>                                                       "
											+ "                    <div class=\"col-md-2\">电损电量4</div>                      "
											+ "                    <div class=\"col-md-2\">                                     "
											+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\"  value=\""
											+ convertNullValue(toplossDegreeTmp) + "\" >             "
											+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
											+ "                    </div>                                                       "
											+ "                    <div class=\"col-md-2\"></div>                               "
											+ "                    <div class=\"col-md-2\">                                     "
											+ "                    </div>                                                       "
											+ "                </li>                                                            ");
						}

						tabSb.append(
								"           <li class=\"list-group-item row\">                                     "
										+ "               <div class=\"col-md-2\">电损金额</div>                             "
										+ "               <div class=\"col-md-2\">                                           "
										+ "                   <input type=\"text\" class=\"form-control\" name=\"meterLoss\" id=\"meterLoss\" value=\""
										+ convertNullValue(meter.getMeterLoss()) + "\">                   "
										+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
										+ "               </div>                                                             "
										+ "               <div class=\"col-md-2\"></div>                                     "
										+ "               <div class=\"col-md-2\">                                           "
										+ "               </div>                                                             "
										+ "               <div class=\"col-md-2\"></div>                                     "
										+ "               <div class=\"col-md-2\">                                           "
										+ "               </div>                                                             "
										+ "           </li>                                                                  "
										+ "       </ul>                                                                      "
										+ "       </form>                                                                    "
										+ "   </div>");
					}
				}

			}
		}

		jso.put("li", liSb.toString());
		jso.put("tabcontent", tabSb.toString());
		jso.put("contract", contract);

		fdback.Obj = jso;

		return fdback;
	}
	/**
	 * 查询 电表信息 并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryElectricmeterInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryElectricmeterInfo(String billaccountId) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;

		EleBillaccount billaccount = elecPaymentService.getEleBillaccountById(billaccountId);

		VEleBillaccountcontract contract = elecPaymentService.getVeleBillaccountContract(billaccountId);

		JSONObject jso = new JSONObject();

		// 拼接 li 列表
		/**
		 * <li class="active"><a href="#1_info" data-toggle="tab">DB-001</a>
		 * </li>
		 * <li><a href="#2_info" data-toggle="tab">DB-002</a></li>
		 * <li><a href="#3_info" data-toggle="tab">DB-003</a></li>
		 * <li><a href="#6_info" data-toggle="tab">DB-006</a></li>
		 * <li><a href="#7_info" data-toggle="tab">DB-007</a></li>
		 */
		StringBuffer liSb = new StringBuffer();

		// 拼接 tab content
		StringBuffer tabSb = new StringBuffer();

		/**
		 * 根据是否是包干合同 判断是否需要动态获取电表信息
		 * 
		 */
		if (contract != null && contract.getIsIncludeAll() != null && contract.getIsIncludeAll() != IncludeAndLossComm.YES) {

			List<VDatElectricmeter> meterlist = elecPaymentService.getElectricmeterByBillaccountIdShow(billaccountId);

			// 第一个li
			boolean firstflag = true;
			String liactive = null;
			String tabactive = null;

			String elecontractPrices;
			String[] elecontractPrice;
			for (VDatElectricmeter meter : meterlist) {
				if (firstflag) {
					liactive = "class=\"active\"";
					tabactive = " active";

					firstflag = false;
				} else {
					liactive = "";
					tabactive = "";
				}

				liSb.append("<li " + liactive + "><a href=\"#").append(meter.getMeterId())
						.append("_info\" data-toggle=\"tab\">").append(meter.getMeterCode()).append("</a></li>");

				if (meter.getMeterType() == MeterTypeComm.Meter_type1) {
					// 普通表
					String cmccRatio="";
					if(contract.getCmccRatio()!=null){
						cmccRatio=subZeroAndDot(convertNullValue(contract.getCmccRatio()));
					}
					tabSb.append("<div class=\"tab-pane fade in " + tabactive + "\" id=\"" + meter.getMeterId()
							+ "_info\">" + "   	<form id=\"" + meter.getMeterId() + "_infoForm\" action=\"\">"
							+ "<input type=\"hidden\" name=\"billaccountpaymentdetailId\" id=\"billaccountpaymentdetailId\" />"
							+ "<input type=\"hidden\" name=\"meterId\" value=\"" + meter.getMeterId() + "\" />"
							+ "<input type=\"hidden\" name=\"upperValue\" id=\"upperValue\" value=\"" + meter.getUpperValue() + "\" />"
							+ "       <ul class=\"list-group\">                                                  "
							+ "        	<li class=\"list-group-item row\">                                      "
							+ "               <div class=\"col-md-2\">电表编码</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getMeterCode()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表类型</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\"普通表\"  id=\"meterType\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表户号</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getAccountNumber()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">上期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  name=\"lastReadnum\" id=\"lastReadnum\" value=\""
							+ convertNullValue(meter.getLastReadnum()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">本期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"nowReadnum\" id=\"nowReadnum\" >                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表倍率</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  name=\"meterRate\" id=\"meterRate\" value=\""
							+ convertNullValue(meter.getElectricmeterMultiply()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">本期度数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"nowDegree\"  id=\"nowDegree\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">移动分摊比例</div>                         "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatio\"  value=\""
							+ cmccRatio+ "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">%</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">分摊后度数</div>                           "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatioAfter\" >                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "                                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  ");

					elecontractPrices = contract.getElecontractPrice();
					if (elecontractPrices != null) {
						elecontractPrice = elecontractPrices.split("\\|");
						for (int i = 0; i < elecontractPrice.length; i++) {
							tabSb.append(
									"           <li class=\"list-group-item row\">                                     "
											+ "               <div class=\"col-md-2\">电费单价" + (i + 1)
											+ "</div>                            "
											+ "               <div class=\"col-md-2\">                                           "
											+ "                   <input type=\"text\" class=\"form-control\" name=\"elecPrice\"  value=\""
											+ convertNullValue(elecontractPrice[i]) + "\">                   "
											+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\">用电量" + (i + 1)
											+ "</div>                              "
											+ "               <div class=\"col-md-2\">                                           "
											+ "                   <input type=\"text\" class=\"form-control\"  name=\"useDegrees\">                   "
											+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\"></div>                                     "
											+ "               <div class=\"col-md-2\">                                           "
											+ "               </div>                                                             "
											+ "           </li>                                                                  ");
						}
					}
					tabSb.append("           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">不含税金额</div>                           "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"payamountNotax\" id=\"payamountNotax\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">税金</div>                                 "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"tax\"   id=\"tax\" value=\""
							+ convertNullValue(contract.getContractTax()) + "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "                                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  ");

					if (contract.getIncludeLoss() != null && contract.getIncludeLoss() == IncludeAndLossComm.YES) {
						if (elecontractPrices != null) {
							elecontractPrice = elecontractPrices.split("\\|");
							for (int i = 0; i < elecontractPrice.length; i++) {
								tabSb.append(
										"           <li class=\"list-group-item row\">                                     "
												+ "               <div class=\"col-md-2\">电损单价" + (i + 1)
												+ "</div>                            "
												+ "               <div class=\"col-md-2\">                                           "
												+ "                   <input type=\"text\"  class=\"form-control\" value=\""
												+ convertNullValue(elecontractPrice[i]) + "\">                   "
												+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
												+ "               </div>                                                             "
												+ "               <div class=\"col-md-2\">电损电量" + (i + 1)
												+ "</div>                            "
												+ "               <div class=\"col-md-2\">                                           "
												+ "                   <input type=\"text\" class=\"form-control\"  name=\"lossDegrees\">                   "
												+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
												+ "               </div>                                                             "
												+ "               <div class=\"col-md-2\"></div>                                     "
												+ "               <div class=\"col-md-2\">                                           "
												+ "               </div>                                                             "
												+ "           </li>                                                                  ");
							}
							tabSb.append(
									"           <li class=\"list-group-item row\">                                     "
											+ "               <div class=\"col-md-2\">电损金额</div>                             "
											+ "               <div class=\"col-md-2\">                                           "
											+ "                   <input type=\"text\" class=\"form-control\"  name=\"meterLoss\" id=\"meterLoss\" >                   "
											+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\"></div>                                     "
											+ "               <div class=\"col-md-2\">                                           "
											+ "               </div>                                                             "
											+ "               <div class=\"col-md-2\"></div>                                     "
											+ "               <div class=\"col-md-2\">                                           "
											+ "               </div>                                                             "
											+ "           </li>                                                                  "
											+ "       </ul>                                                                      "
											+ "       </form>                                                                    "
											+ "   </div>                                                                         ");
						}
					}

				} else if (meter.getMeterType() == MeterTypeComm.Meter_type2) {
					String cmccRatio="";
					if(contract.getCmccRatio()!=null){
						cmccRatio=subZeroAndDot(convertNullValue(contract.getCmccRatio()));
					}
				/*	if(contract.getValleyPrice()==null){
						$("#chose_Btn").attr("disabled",true);
					}*/
					// 平谷峰表
					tabSb.append("<div class=\"tab-pane fade in " + tabactive + "\" id=\"" + meter.getMeterId()
							+ "_info\">" + "   	<form id=\"" + meter.getMeterId() + "_infoForm\" action=\"\">"
							+ "<input type=\"hidden\" name=\"billaccountpaymentdetailId\"  id=\"billaccountpaymentdetailId\" />"
							+ "<input type=\"hidden\" name=\"meterId\" value=\"" + meter.getMeterId() + "\" />"
							+ "<input type=\"hidden\" name=\"upperValue\" id=\"upperValue\" value=\"" + meter.getUpperValue() + "\" />"
							+ "<input type=\"hidden\" name=\"flatUpperValue\" id=\"flatUpperValue\" value=\"" + meter.getFlatUpperValue() + "\" />"
							+ "<input type=\"hidden\" name=\"peakUpperValue\" id=\"peakUpperValue\" value=\"" + meter.getPeakUpperValue() + "\" />"
							+ "<input type=\"hidden\" name=\"valleyUpperValue\" id=\"valleyUpperValue\" value=\"" + meter.getValleyUpperValue() + "\" />"
							+ "<input type=\"hidden\" name=\"topUpperValue\" id=\"topUpperValue\" value=\"" + meter.getTopUpperValue() + "\" />"
							+ "       <ul class=\"list-group\">                                                  "
							+ "        	<li class=\"list-group-item row\">                                      "
							+ "               <div class=\"col-md-2\">电表编码</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getMeterCode()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表类型</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\"平峰谷表\" id=\"meterType\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表户号</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" disabled value=\""
							+ convertNullValue(meter.getAccountNumber()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">上期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"lastReadnum\" id=\"lastReadnum\" value=\""
							+ convertNullValue(meter.getLastReadnum()) + "\">                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">本期读数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\"  class=\"form-control\" name=\"nowReadnum\" id=\"nowReadnum\" >                   "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">电表倍率</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\"  class=\"form-control\" name=\"meterRate\" id=\"meterRate\" value=\""
							+ convertNullValue(meter.getElectricmeterMultiply()) + "\">                   "
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\">本期度数</div>                             "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\" name=\"nowDegree\"  id=\"nowDegree\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">移动分摊比例</div>                         "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"  id=\"cmccRatio\"   value=\""
							+ cmccRatio + "\">                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">%</span>"
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\">分摊后度数</div>                           "
							+ "               <div class=\"col-md-2\">                                           "
							+ "                   <input type=\"text\" class=\"form-control\"   id=\"cmccRatioAfter\" >                   "
							+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
							+ "               </div>                                                             "
							+ "           </li>                                                                  "
							+ "                                                                                  "
							+ "           <li class=\"list-group-item row\">                                     "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "               <div class=\"col-md-2\"></div>                                     "
							+ "               <div class=\"col-md-2\">                                           "
							+ "               </div>                                                             "
							+ "           </li>                                                                  ");

					tabSb.append("                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">平单价</div>                         "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" id=\"flatPrice\" "+(contract.getFlatPrice()==null?"disabled=disabled":"")+" value=\""
							+ convertNullValue(contract.getFlatPrice()) + "\">             "
							+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">平上期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" "+(contract.getFlatPrice()==null?"disabled=disabled":"")+" name=\"lastFlatReadnum\" id=\"lastFlatReadnum\" value=\""
							+ convertNullValue(meter.getFlatLastreadnum()) + "\">             "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">平本期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    	<input type=\"text\" class=\"form-control\" "+(contract.getFlatPrice()==null?"disabled=disabled":"")+" name=\"nowFlatReadnum\" id=\"nowFlatReadnum\">               "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">峰单价</div>                         "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" id=\"peakPrice\" "+(contract.getPeakPrice()==null?"disabled=disabled":"")+" value=\""
							+ convertNullValue(contract.getPeakPrice()) + "\">             "
							+ "					  	   <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">峰上期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" "+(contract.getPeakPrice()==null?"disabled=disabled":"")+" name=\"lastPeakReadnum\" id=\"lastPeakReadnum\" value=\""
							+ convertNullValue(meter.getPeakLastreadnum()) + "\">             "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">峰本期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    	<input type=\"text\" class=\"form-control\" "+(contract.getPeakPrice()==null?"disabled=disabled":"")+" name=\"nowPeakReadnum\" id=\"nowPeakReadnum\">               "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">谷单价</div>                         "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" id=\"valleyPrice\" "+(contract.getValleyPrice()==null?"disabled=disabled":"")+" value=\""
							+ convertNullValue(contract.getValleyPrice()) + "\">             "
							+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">谷上期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" "+(contract.getValleyPrice()==null?"disabled=disabled":"")+" name=\"lastValleyReadnum\" id=\"lastValleyReadnum\" value=\""
							+ convertNullValue(meter.getValleyLastreadnum()) + "\">             "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">谷本期读数</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    	<input type=\"text\" class=\"form-control\" name=\"nowValleyReadnum\" "+(contract.getValleyPrice()==null?"disabled=disabled":"")+"id=\"nowValleyReadnum\">               "
							+ "                    </div>                                                       "
							+ "                </li>                                                            ");

					if (contract.getTopPrice() != null) {
						tabSb.append("                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">尖单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"topPrice\"  value=\""
								+ convertNullValue(contract.getTopPrice()) + "\">             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">尖上期读数</div>                     "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\"  name=\"lastTopReadnum\" id=\"lastTopReadnum\" value=\""
								+ convertNullValue(meter.getTopLastreadnum()) + "\">             "
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">尖本期读数</div>                     "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    	<input type=\"text\" class=\"form-control\" name=\"nowTopReadnum\" id=\"nowTopReadnum\">               "
								+ "                    </div>                                                       "
								+ "                </li>                                                            ");
					}

					tabSb.append("                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\">不含税金额</div>                     "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\" name=\"payamountNotax\" id=\"payamountNotax\">             "
							+ "					  	   <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\">税金</div>                           "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                        <input type=\"text\" class=\"form-control\"  name=\"tax\"   id=\"tax\"   value=\""
							+ convertNullValue(contract.getContractTax()) + "\">             "
							+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                                                                                 "
							+ "                <li class=\"list-group-item row\">                               "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                    <div class=\"col-md-2\"></div>                               "
							+ "                    <div class=\"col-md-2\">                                     "
							+ "                    </div>                                                       "
							+ "                </li>                                                            "
							+ "                <li class=\"list-group-item row\">                               ");

					if (contract.getIncludeLoss() != null && contract.getIncludeLoss() == IncludeAndLossComm.YES) {
						tabSb.append("                    <div class=\"col-md-2\">平单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"flatPrice\"   value=\""
								+ convertNullValue(contract.getFlatPrice()) + "\">             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">电损电量1</div>                      "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\" >             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\"></div>                               "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    </div>                                                       "
								+ "                </li>                                                            "
								+ "                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">峰单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"peakPrice\"  value=\""
								+ convertNullValue(contract.getPeakPrice()) + "\">             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">电损电量2</div>                      "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\" >             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\"></div>                               "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    </div>                                                       "
								+ "                </li>                                                            "
								+ "                <li class=\"list-group-item row\">                               "
								+ "                    <div class=\"col-md-2\">谷单价</div>                         "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" id=\"valleyPrice\"  value=\""
								+ convertNullValue(contract.getValleyPrice()) + "\">             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\">电损电量3</div>                      "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\" >             "
								+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
								+ "                    </div>                                                       "
								+ "                    <div class=\"col-md-2\"></div>                               "
								+ "                    <div class=\"col-md-2\">                                     "
								+ "                    </div>                                                       "
								+ "                </li>                                                            ");

						if (contract.getTopPrice() != null) {
							tabSb.append(
									"                <li class=\"list-group-item row\">                               "
											+ "                    <div class=\"col-md-2\">尖单价</div>                         "
											+ "                    <div class=\"col-md-2\">                                     "
											+ "                        <input type=\"text\" class=\"form-control\" id=\"topPrice\"  value=\""
											+ convertNullValue(contract.getTopPrice()) + "\">             "
											+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
											+ "                    </div>                                                       "
											+ "                    <div class=\"col-md-2\">电损电量4</div>                      "
											+ "                    <div class=\"col-md-2\">                                     "
											+ "                        <input type=\"text\" class=\"form-control\" name=\"lossDegrees\"   >             "
											+ "					       <span style=\"position: absolute;top: 0px;right: 30%;\">度</span>"
											+ "                    </div>                                                       "
											+ "                    <div class=\"col-md-2\"></div>                               "
											+ "                    <div class=\"col-md-2\">                                     "
											+ "                    </div>                                                       "
											+ "                </li>                                                            ");
						}

						tabSb.append(
								"           <li class=\"list-group-item row\">                                     "
										+ "               <div class=\"col-md-2\">电损金额</div>                             "
										+ "               <div class=\"col-md-2\">                                           "
										+ "                   <input type=\"text\" class=\"form-control\"  name=\"meterLoss\" id=\"meterLoss\" >                   "
										+ "					  <span style=\"position: absolute;top: 0px;right: 30%;\">元</span>"
										+ "               </div>                                                             "
										+ "               <div class=\"col-md-2\"></div>                                     "
										+ "               <div class=\"col-md-2\">                                           "
										+ "               </div>                                                             "
										+ "               <div class=\"col-md-2\"></div>                                     "
										+ "               <div class=\"col-md-2\">                                           "
										+ "               </div>                                                             "
										+ "           </li>                                                                  "
										+ "       </ul>                                                                      "
										+ "       </form>                                                                    "
										+ "   </div>                                                                         ");
					}

				}

			}
		}

		jso.put("li", liSb.toString());
		jso.put("tabcontent", tabSb.toString());
		jso.put("contract", contract);
		jso.put("billaccount", billaccount);

		fdback.Obj = jso;

		return fdback;
	}
	/** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }  
	private String convertNullValue(Object obj){
		return obj == null ? "" : obj.toString();
	}
	
	@RequestMapping(value="/queryDetail", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject getEleBillaccountDetailById(@RequestParam("eleBillaccountDetailById") String eleBillaccountDetailById){
		FeedBackObject fb = new FeedBackObject();
		fb.Obj =  elecPaymentService.getEleBillaccountDetailById(eleBillaccountDetailById);
		return fb;
	}
	
	
	@RequestMapping(value = "/deletePaymentDetail", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject deletePaymentDetail(@RequestBody Map<String,Object> map) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecPaymentService.deletePaymentDetail(map);
		return fdback;
	}
}
