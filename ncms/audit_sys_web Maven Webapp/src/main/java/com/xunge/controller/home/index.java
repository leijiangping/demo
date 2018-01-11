package com.xunge.controller.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.HomeComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.TypeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtils;
import com.xunge.core.util.MD5Util;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.basedata.resource.IDatResourceService;
import com.xunge.service.datacollect.IGrpDatacollectPrvService;
import com.xunge.service.selfelec.billaccount.IElecBillAccountService;
import com.xunge.service.selfelec.billamount.IElecBillamountService;
import com.xunge.service.selfelec.eleccontract.IEleccontractService;
import com.xunge.service.selfelec.electricmeter.IElecticMeterService;
import com.xunge.service.selfelec.payment.IElecPaymentService;
import com.xunge.service.selfrent.contract.ISelfRentService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.system.user.ISysUserService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class index extends BaseException{

	@Autowired
	private ISysUserService userService;
	@Autowired
	private ISysRegionService sysRegionService;

	@Autowired
	private IEleccontractService eleccontractService;
	@Autowired
	private ISelfRentService rentcontractService;
	@Autowired
	private IElecticMeterService electicMeterService;
	@Autowired
	private IDatResourceService datResourceService;
	@Autowired
	private IElecBillamountService elecBillamountService;
	@Autowired
	private IElecBillAccountService elecBillAccountService;
	@Autowired
	private IElecPaymentService elecPaymentService;

	@Autowired
	private IGrpDatacollectPrvService grpDatacollectPrvService;

	@Autowired
	private ISysRoleService iSysRoleService;
	@Autowired
	private IActTaskService actTaskService;
	
    @RequestMapping("/")
    public String pageIndex(HttpSession session) {
    	//改为跳转到登陆页面的时候加载
//		List<SysProvinceGroupVO> prvGroups=sysRegionService.selectByPrvGroup();
//    	session.setAttribute("prvGroup", prvGroups);
    	return PromptMessageComm.URL_LOGIN;
    }
    
    /**
     * 首页信息
     * @author SongJL
     * @return
     */
    @RequestMapping("/asserts/tpl/homeIndex")
    public @ResponseBody FeedBackObject homeIndex(@ModelAttribute("user") UserLoginInfo loginInfo,String regName,String prvName) {

		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}

		// 获取查询区域id
    	Map<String, Object> paramMap = new HashMap<>();
    	paramMap.put("prv_id", loginInfo.getPrv_id());
		List<String> reg_ids = sysRegionService.queryRegionIdsByPrvId(paramMap);//需要展示的区县id
		paramMap.put("reg_ids", reg_ids);
		
		// 全省合同数据
		List<Map<String, String>> contract_num = new ArrayList<>();
		if((reg_ids!=null && reg_ids.size() > Integer.parseInt(HomeComm.VALUE_str0)) || paramMap.get("prv_ids")!=null){
			contract_num = eleccontractService.queryAllRegionContractNum(paramMap);
		}
		else{
			Map<String, String> nullmap = new HashMap<>();
			nullmap.put("name", PromptMessageComm.NO_NAME);
			nullmap.put("value", HomeComm.VALUE_str0);
			contract_num.add(nullmap);
		}

		// 机房资源点
		List<Map<String, String>> resource_num = new ArrayList<>();
		if((reg_ids!=null && reg_ids.size() > Integer.parseInt(HomeComm.VALUE_str0)) || paramMap.get("prv_ids")!=null){
			resource_num = datResourceService.queryAllResourceNumByPrvid(paramMap);
		}
		else{
			Map<String, String> nullmap = new HashMap<>();
			nullmap.put("name", PromptMessageComm.NO_NAME);
			nullmap.put("value", HomeComm.VALUE_str0);
			resource_num.add(nullmap);
		}
		
		// 全省电表数
		List<Map<String, String>> elemeter_num = new ArrayList<>();
		if((reg_ids!=null && reg_ids.size() > Integer.parseInt(HomeComm.VALUE_str0)) || paramMap.get("prv_ids")!=null){
			elemeter_num = electicMeterService.queryAllMeterNumByPrvid(paramMap);
		}
		else{
			Map<String, String> nullmap = new HashMap<>();
			nullmap.put("name", PromptMessageComm.NO_NAME);
			nullmap.put("value", HomeComm.VALUE_str0);
			elemeter_num.add(nullmap);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("prv_id", loginInfo.getPrv_id());
		param.put("nowDate", DateUtils.getYear());
		param.put("regName", regName);
		param.put("prvName", prvName);
		// 全省自维电费缴费数量
		List<Map<String, String>> selfElecPaymentDetail_num = new ArrayList<>();
		param.put("billaccountType",TypeComm.SELFELEC_BILLACCOUNTTYPE);
		selfElecPaymentDetail_num = elecPaymentService.queryPaymentDetailByCondition(param);

		// 全省塔维电费缴费数量
		List<Map<String, String>> selfTowerPaymentDetail_num = new ArrayList<>();
		param.put("billaccountType",TypeComm.TOWERELEC_BILLACCOUNTTYPE);
		selfTowerPaymentDetail_num = elecPaymentService.queryPaymentDetailByCondition(param);
		
		
		
		//未关联机房资源数、机房资源总数
		Map<String,Object> resmap = datResourceService.queryNoLinkResource(paramMap);
		//未关联机电表数、电表总数
		Map<String,Object> metermap = electicMeterService.queryNolinkMeter(paramMap);
		//报账点总数
		Map<String,Object> billmap = elecBillAccountService.queryBillaccountNum(paramMap);
		//已推送报账单数、已报账单数
		Map<String,Object> pushedbillmap = elecBillamountService.queryPushedBillLv(paramMap);
		
    	Map<String, Object> resMap = new HashMap<>();
    	resMap.put("contract_num", contract_num);//全省合同数
    	resMap.put("resource_num", resource_num);//全省资源数
    	resMap.put("elemeter_num", elemeter_num);//全省电表数
    	resMap.put("selfElecPaymentDetail_num", selfElecPaymentDetail_num);//全省自维电费缴费数量
    	resMap.put("selfTowerPaymentDetail_num", selfTowerPaymentDetail_num);//全省塔维电费缴费数量
    	
    	resMap.putAll(resmap);//未关联机房资源数、机房资源总数
    	resMap.putAll(metermap);//未关联机电表数、电表总数
    	resMap.putAll(billmap);//报账点总数
    	resMap.putAll(pushedbillmap);//已推送报账单数、已报账单数
    	
    	//获取待办任务数量-------开始-------
    	Act act = new Act();
		String assignee = loginInfo.getUser_loginname();
		List<String> roleIds = loginInfo.getRole_ids();
		List<String> assigneeNameGroup = iSysRoleService.queryRoleNameById(roleIds);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		
		act.setRegCode(loginInfo.getPrv_code());// 区分省份

		Page<Act> page=new Page<Act>(Integer.parseInt(StateComm.PAGE_NUMBER_STR),Integer.parseInt(StateComm.PAGE_SIZE_STR));
    	Page<Act> list=actTaskService.todoListPage(page,act);
    	List<Act> listData=grpDatacollectPrvService.queryWiteToDoReject(loginInfo,"0",null,null);
    	
    	resMap.put("todonums", list.getTotal()+listData.size());//待办事项
    	//获取待办任务数量-------结束-------
    	
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj = resMap;
    	return backObj;
    }

	/**
	 * 查询首页显示待办数量
	 * @author SongJL
	 * @return
	 */
	@RequestMapping(value = "/asserts/tpl/queryTodoList", method = RequestMethod.GET)
    public @ResponseBody FeedBackObject queryTodoList(@ModelAttribute("user") UserLoginInfo loginInfo) {

		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	Map<String, Object> resMap = new HashMap<>();
		//获取待办任务数量-------开始-------
    	Act act = new Act();
		String assignee = loginInfo.getUser_loginname();
		List<String> roleIds = loginInfo.getRole_ids();
		List<String> assigneeNameGroup = iSysRoleService.queryRoleNameById(roleIds);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		
		act.setRegCode(loginInfo.getPrv_code());// 区分省份

		Page<Act> page=new Page<Act>(1,10);
    	Page<Act> list=actTaskService.todoListPage(page,act);
    	List<Act> listData=grpDatacollectPrvService.queryWiteToDoReject(loginInfo,"0",null,null);
    	
    	resMap.put("todonums", list.getTotal()+listData.size());//待办事项
    	//获取待办任务数量-------结束-------
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj =resMap;
		return backObj;
    }
 
	/**
	 * 查询个人信息
	 * @author SongJL
	 * @return
	 */
	@RequestMapping(value = "/queryUserInfo", method = RequestMethod.GET)
    public @ResponseBody FeedBackObject queryUserInfo(@ModelAttribute("user") UserLoginInfo loginInfo) {

		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj = userService.queryUserInfoRedis(loginInfo.getUser_id());
		return backObj;
    }
 
	/**
	 * 修改个人密码
	 * @author SongJL
	 * @return
	 */
	@RequestMapping(value = "/updateUserPswd", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject updateUserPswd(@RequestParam Map<String,Object> map, 
    		@ModelAttribute("user") UserLoginInfo loginInfo) {

		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<>();
		//用户原来密码
		String userPassword = (String)map.get("userPassword");
		//用户输入原来密码
		String  inputOldPsw = "";
		//用户输入新密码
		String userNewPassword ="";
		try {
			inputOldPsw = MD5Util.encode((String)map.get("user_pswd"));
			userNewPassword = MD5Util.encode((String)map.get("new_pswd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!userPassword.equals(inputOldPsw)){
			throw new BusinessException(PromptMessageComm.PASSWORD_NO_MATCHING);
		}
		paraMap.put("new_pswd", userNewPassword);
		paraMap.put("newUserpswd", map.get("new_pswd"));
		paraMap.put("user", loginInfo);
		paraMap.put("user_id", loginInfo.getUser_id());
		int ires = userService.updateUserPswd(paraMap);
		if(ires == HomeComm.STATE_1){
			backObj.success = RESULT.SUCCESS_1;
			backObj.msg=PromptMessageComm.PASSWORD_MODIF_SUCCESS;
    	}
    	else{
    		backObj.success = RESULT.FAIL_0;
    		backObj.msg=PromptMessageComm.PASSWORD_MODIF_FAILED;
    	}
		return backObj;
    }
	 
	/**
	 * 修改个人信息
	 * @author SongJL
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject updateUserInfo(@RequestParam Map<String,Object> map, 
    		@ModelAttribute("user") UserLoginInfo loginInfo) {

		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		FeedBackObject backObj = new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<>();
		paraMap.put("login_name", map.get("login_name"));
		paraMap.put("user_email", map.get("user_email"));
		paraMap.put("user_phone", map.get("user_phone"));
		paraMap.put("user_id", loginInfo.getUser_id());
		int ires = userService.updateUserInfo(paraMap);
		if(ires == HomeComm.STATE_1){
			backObj.success = RESULT.SUCCESS_1;
			backObj.msg = PromptMessageComm.PERSONAL_INFORMATION_CHANGES_SUCCESS;
    	}
    	else{
    		backObj.success = RESULT.FAIL_0;
    		backObj.msg = PromptMessageComm.PERSONAL_INFORMATION_CHANGES_FAILED;
    	}
		return backObj;
    }
}