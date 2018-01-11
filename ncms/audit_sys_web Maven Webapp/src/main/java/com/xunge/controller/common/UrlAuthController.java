package com.xunge.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.service.IMenuManagementService;

@RestController
@RequestMapping("/asserts/tpl/common/region")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class UrlAuthController {

    @Autowired
    private IMenuManagementService menuManService;

	/**
	 * 获取地市列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/authUrl", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject authUrl(@ModelAttribute("user") UserLoginInfo loginInfo, String assertUrl) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		List<String> roleIds = loginInfo.getRole_ids();
		feedbackObj.success = menuManService.queryUrlAuthor(roleIds, assertUrl);
		return feedbackObj;
	}

}
