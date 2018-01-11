package com.xunge.controller.selfrent.billaccount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfrent.billAccount.CoverAllVO;
import com.xunge.model.selfrent.billAccount.DatBaseresourceVO;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.selfrent.billaccount.IBillAccountService;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/selfrent/billaccount_m")
@Controller
public class BillAccountController extends BaseException{

	@Autowired
	private IBillAccountService billAccountService;
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private IDictionaryService dictionaryService;
	
	@Autowired
	private ILogService log;
	
	@Autowired
	private IActTaskService actTaskService;
	/**
	 * 新增缴费记录
	 * @param ca
	 * @param request
	 * @param loginInfo
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value ="/insertAllForm",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertAllForm(CoverAllVO ca,HttpServletRequest request,
			@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObj = new FeedBackObject();
		Map<String,Object> map = new HashMap<String,Object>();
		RentContractVO rc = ca.getRentContractVO();
		//报账点id
		String baid = ca.getBillAccountVO().getBillAccountId();
		//合同租费信息
		RentPaymentVO rpm =	ca.getRentPayMentVO();
		rpm.setPaymentId(SysUUID.generator());
		rpm.setBillaccountId(baid);
		rpm.setIncludeTax(rc.getIncludeTax());
		rpm.setBillamountTaxratio(rc.getBillamountTaxratio());
		rpm.setPaymentState(StateComm.STATE_0);
		rpm.setPaymentFlowstate(ActivityStateComm.STATE_UNCOMMITTED);
		//机房租费信息
		List<DatBaseresourceVO> dbslist = ca.getLstDatbase();
		if(dbslist != null){
			for(int i = Integer.parseInt(SelfelecComm.NUMBER_0);i < dbslist.size();i++){
				dbslist.get(i).getRentPaymentdetailVO().setPaymentdetailId(SysUUID.generator());
				dbslist.get(i).getRentPaymentdetailVO().setPaymentId(rpm.getPaymentId());
			}
			map.put("dbslist",dbslist);
		}
		//封装数据
		map.put("billaccountId",baid);
		map.put("rentPaymentVO",rpm);
		feedBackObj.success = billAccountService.insertAllForm(map);
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = loginInfo.getUser_loginname()+PromptMessageComm.BILLACCOUNT_RENTINFO_SUCCESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
			feedBackObj.success = RESULT.SUCCESS_1;
		}else{
			feedBackObj.msg = loginInfo.getUser_loginname()+"："+feedBackObj.success;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
			feedBackObj.success = RESULT.FAIL_0;
		}	
		return feedBackObj;
	}
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressBill", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddress(@ModelAttribute("user") UserLoginInfo loginInfo) {
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
	 * 通过数据code查询数据字典
	 * @param dictgroup_code
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value ="/queryDictionaryByCode",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryDictionaryByCode(
			@ModelAttribute("user") UserLoginInfo loginInfo, String dictgroup_code){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = dictionaryService.queryDictionaryByCodeRedis(loginInfo.getPrv_id(), dictgroup_code);
		return feedbk;
	}
	/**
	 * 查询报账点信息
	 * @param pageNumber
	 * @param pageSize
	 * @param billaccountName
	 * @param pRegId
	 * @param regId
	 * @param billaccountState
	 * @return
	 * @author changwq
	 */
	@RequestMapping(value="/queryBillAccountVO",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillAccountVO(
			@ModelAttribute("user") UserLoginInfo loginInfo,int pageNumber,int pageSize,
            String billaccountName,String pRegId,String regId,String billaccountState){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("billaccountName",billaccountName);
		map.put("pRegId",pRegId);
		map.put("regId",regId);
		map.put("billaccountState",billaccountState);
		map.put("state",StateComm.STATE_0);
		map.put("userId",loginInfo.getUser_id());
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		try {
			feedbackObj.Obj =billAccountService.queryBillAccountVO(map, pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedbackObj;
	}
	//查询所有报账点相关信息
		@RequestMapping(value="/queryAll",method=RequestMethod.POST)
		public @ResponseBody FeedBackObject queryAll(String billAccountId){
			FeedBackObject feedbackObj = new FeedBackObject();
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.Obj =billAccountService.queryAll(billAccountId);
			// 启动流程
			//actTaskService.startProcess(ActUtils.PD_SELFRENT_AUDIT[0], ActUtils.PD_SELFRENT_AUDIT[1], id,"租费合同待审核");
			return feedbackObj;
		}
}
