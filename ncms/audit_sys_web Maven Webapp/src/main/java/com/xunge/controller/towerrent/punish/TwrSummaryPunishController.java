package com.xunge.controller.towerrent.punish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.service.twrrent.punish.ITwrSummaryPunishService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
@RequestMapping(value = "/asserts/tpl/towerrent/assessment")
public class TwrSummaryPunishController  extends BaseException{
	
	@Autowired
	private ITwrSummaryPunishService twrSummaryPunishService;
	
	/**
	 * 扣罚汇总信息全查
	 * @param regId
	 * @param punishYearMonth
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/querySummaryPunish")
	public @ResponseBody
	FeedBackObject querySummaryPunish(@ModelAttribute("user") UserLoginInfo loginInfo,
			String regId,String punishYearMonth,
			int pageNumber,int pageSize){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		String prvId = loginInfo.getPrv_id();
		fdback.Obj = twrSummaryPunishService.querySumPunish(
				regId, punishYearMonth, pageNumber, pageSize,prvId);
		return fdback;
	}
	
}
