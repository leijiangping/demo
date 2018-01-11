package com.xunge.controller.towerrent.bizbasedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.OperateUtil;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.bizbasedata.CostDifferVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.twrrent.bizbasedata.ITwrCostDiffService;

/**
 * 
 * @author jiacy
 * @date 2017年6月5日
 * @description 费用差异范围控制器
 */
@RequestMapping("/asserts/tpl/towerrent/bizbasedata_m")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class ChargeDiffController extends BaseException{
	
	@Autowired
	private ITwrCostDiffService costDiffService;
	@Autowired
	private ILogService log;
	
	/**
	 * 查询所有对象
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryAllChargeDiff", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllObj(int pageSize,
			int pageNumber,@ModelAttribute("user")UserLoginInfo loginUser) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("prvId", loginUser.getPrv_id());
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = costDiffService.queryAllObj(pageSize, pageNumber,paramMap);
		return fdback;
	}

	/**
	 * 批量删除对象
	 * @author jiacy
	 * @param ids
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteChargeDiff", method = RequestMethod.POST)
	public @ResponseBody  FeedBackObject deleteObjById(@RequestBody List<String> ids) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = costDiffService.deleteObjById(ids);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 更新费用差异范围对象
	 * @author jiacy
	 * @param costDiffVo
	 * @return
	 */
	@RequestMapping(value = "/updateChargeDiff", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateObjById(CostDifferVO costDiffVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = costDiffService.updateObjById(costDiffVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
	}

	/**
	 * 插入费用差异范围对象
	 * @author jiacy
	 * @param costDiffVo
	 * @return
	 */
	@RequestMapping(value = "/insertChargeDiff", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertObjId(CostDifferVO costDiffVo) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = costDiffService.insertObjById(costDiffVo);
		OperateUtil.echoBeahivor(backObj, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObj;
		
	}

	/**
	 * 按条件查询挂高
	 * @author jiacy
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/queryChargeDiffByCondition", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryObjByCondition(HttpServletRequest request,int pageSize,int pageNumber) {
			String prvId = request.getParameter("prvId");
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("prvId", prvId);
			FeedBackObject backObj = new FeedBackObject();
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = costDiffService.queryObjByCondition(paramMap, pageSize, pageNumber);
			return backObj;
	}
}
