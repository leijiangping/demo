package com.xunge.controller.login;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.siemens.ct.its.smap.common.exception.SmapAuthenticateTypeNotSatisfied;
import com.siemens.ct.its.smap.common.exception.SmapAuthenticateUserBySessionIdException;
import com.siemens.ct.its.smap.sso2.common.model.TicketBall;
import com.siemens.ct.its.smap2.client.XSmap2Client;
import com.siemens.ct.its.smap2.client.XSmap2ClientFactory;
import com.xunge.comm.HomeComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.comm.system.SystemConfComm;
import com.xunge.controller.activity.mapper.JsonMapper;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.VerifyCodeUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.ISysUserLoginService;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.user.ISysUserService;

/**
 * 用户登陆控制层
 *
 */
@Controller
public class LoginControl extends BaseException {

	@Autowired
	private ISysUserLoginService sysUserLogin;

	@Autowired
	private ISysRegionService sysRegionService;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private IDictionaryService dictionaryService;

	@Autowired
	private ILogService log;

	private static PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.URL_SYSCONFIG);

	private static final String localAppId = prop.getProperty("localAppId");

	private static final String authenticateUrls = prop.getProperty("authenticateUrls");

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 用户登陆方法
	 */
	@RequestMapping(value = "/login")
	public @ResponseBody FeedBackObject login(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userLoginName = request.getParameter("userLoginname");
		String userPassword = request.getParameter("userPassword");
		String ververifyCode = request.getParameter("verifyCode");
		String prvFlag = request.getParameter("prvFlag");

		// 获取登录用户
		SysUserVO user = userService.queryByUserLoginNameRedis(userLoginName, userPassword, prvFlag);
		// 版本号
		List<Map<String, Object>> value = dictionaryService.queryDictionaryByCodeRedis(SystemConfComm.PRVID,
				SystemConfComm.SYSTEM_CONF);
		String dictValue = null;
		if (value != null && value.size() > Integer.parseInt(HomeComm.VALUE_str0)) {
			for (Map<String, Object> dictionary : value) {
				if (SystemConfComm.DICT_NAME.equals(dictionary.get("dict_name"))) {
					dictValue = (String) dictionary.get("dict_value");
				}
			}
		}

		UserLoginInfo userLoginInfo = sysUserLogin.getUserLoginInfoRedis(user);

		FeedBackObject feedBackObj = new FeedBackObject();
		if (userLoginInfo != null) {
			feedBackObj.success = RESULT.SUCCESS_1;
			session.setAttribute("user", userLoginInfo);

			// 记住用户名、密码功能
			String rememberFlag = request.getParameter("remFlag");
			// "1"表示用户勾选记住密码
			if (StateComm.CHECKBOX_STATE_1.equals(rememberFlag)) {
				String loginInfo = userLoginName + "," + user.getUserPassword();
				setUserInCookies(response, loginInfo);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getUserId());
			map.put("dictValue", dictValue);
			feedBackObj.Obj = map;
			feedBackObj.msg = PromptMessageComm.LOGIN_SUCCESS + user.getUserName();
			// 校验用户输入的验证码是否正确
			if (!checkCode(session, ververifyCode)) {
				feedBackObj.success = RESULT.FAIL_0;
				feedBackObj.msg = PromptMessageComm.INCORRECT_VERIFICATION_CODE;
			}
			// 添加系统日志
			log.info(SysLogComm.LOG_Login, feedBackObj.msg);
		} else {
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = PromptMessageComm.PULL_USER_INFORMATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Login, feedBackObj.msg);
		}

		return feedBackObj;
	}

	/**
	 * 设置用户信息到本地
	 * 
	 * @param response
	 * @param loginInfo
	 */
	private void setUserInCookies(HttpServletResponse response, String loginInfo) {
		Cookie userCookie = new Cookie("loginInfo", loginInfo);
		userCookie.setMaxAge(PromptMessageComm.COOKIE_MAXAGE); // 存活期为一个月 30*24*60*60
		userCookie.setPath("/");
		response.addCookie(userCookie);
	}

	/**
	 * 登陆成功跳转页面
	 */
	@RequestMapping(value = "/welcome")
	public ModelAndView welcome(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserLoginInfo userLoginInfo = (UserLoginInfo) session.getAttribute("user");
		if(userLoginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		String regId = userLoginInfo.getReg_id();
		String prvName = sysRegionService.getPrvNameById(regId);
		String userName = userLoginInfo.getUser_loginname();
		Date now = new Date();
		String nowDate = DateUtil.formatDate(now) + " " + DateUtil.getWeekOfDate(now);

		ModelAndView mav = new ModelAndView(PromptMessageComm.INDEX);
		mav.getModel().put("prvName", prvName);
		mav.getModel().put("userName", userName);
		mav.getModel().put("nowDate", nowDate);
		return mav;
	}

	/**
	 * 用户退出
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) throws Exception {
		// 清除session，用户退出
		session.invalidate();
		return PromptMessageComm.URL_LOGIN;
	}

	/**
	 * @author jiacy
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/imageGen")
	public void AuthImage(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", PromptMessageComm.NO_CACHE);
		response.setHeader("Cache-Control", PromptMessageComm.NO_CACHE);
		response.setDateHeader("Expires", Integer.parseInt(SelfelecComm.NUMBER_0));
		response.setContentType(PromptMessageComm.CONTENT_TYPE_IMAGE_JPEG);
		// 生成随机字串
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		// 删除以前的
		session.removeAttribute("verifyCode");
		session.setAttribute("verifyCode", verifyCode.toLowerCase());
		// 生成图片
		int w = PromptMessageComm.IMAGE_W, h = PromptMessageComm.IMAGE_H;
		try {
			VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证码验证
	 * 
	 * @param session
	 * @param code
	 */
	private boolean checkCode(HttpSession session, String code) {
		String codeSession = (String) session.getAttribute("verifyCode");
		if (StrUtil.isEmpty(codeSession)) {
			// 验证码拉取失败
			return false;
		}
		if (StrUtil.isEmpty(code)) {
			// 未填写验证码信息
			return false;
		}
		if (codeSession.equalsIgnoreCase(code.toLowerCase())) {
			// 验证码通过
			return true;
		} else {
			// 验证码错误
			return false;
		}
	}

	/**
	 * 用户单点登录
	 */
	@RequestMapping(value = "/sso_login")
	public ModelAndView ssoLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String sessionData = request.getParameter("session_data");
		String uid = userLoginBySessionData(sessionData);
		logger.debug(PromptMessageComm.LOGIN_UID, uid);
		if (uid == null || uid.trim().equals("")) {
			logger.debug(PromptMessageComm.UID_IS_EMPTY);
			throw new BusinessException(PromptMessageComm.UID_IS_EMPTY);
		}
		String[] strs = uid.split("@");
		String userLoginName = strs[0];
		String prvFlag = "@" + strs[1];

		// 获取登录用户
		SysUserVO user = userService.queryByUserLoginNameRedis(userLoginName, prvFlag);
		if(user == null){
			return new ModelAndView(PromptMessageComm.URL_ERR_SSO_ERROR);
		}

		// 版本号
		List<Map<String, Object>> value = dictionaryService.queryDictionaryByCodeRedis(SystemConfComm.PRVID,
				SystemConfComm.SYSTEM_CONF);
		String dictValue = null;
		if (value != null && value.size() > Integer.parseInt(SelfelecComm.NUMBER_0)) {
			for (Map<String, Object> dictionary : value) {
				if (SystemConfComm.DICT_NAME.equals(dictionary.get("dict_name"))) {
					dictValue = (String) dictionary.get("dict_value");
				}
			}
		}

		UserLoginInfo userLoginInfo = sysUserLogin.getUserLoginInfoRedis(user);

		if (userLoginInfo != null) {
			session.setAttribute("user", userLoginInfo);

			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getUserId());
			map.put("dictValue", dictValue);
			// 添加系统日志
			log.info(SysLogComm.LOG_Login, PromptMessageComm.USER_SSO_LOGIN_IS_SUCCESS);
		} else {
			// 添加系统日志
			log.errNoLogin(SysLogComm.LOG_Login, PromptMessageComm.SSO_LOGIN_FAILED_TO_PULL_USER_INFORMATION, null, sessionData);
		}

		return new ModelAndView(PromptMessageComm.URL_REDIRECT_WELCOME);
	}

	/**
	 * SSO 接收方： 认证会话数据，并获取用户uid
	 * 
	 * @param sessionData
	 * @return SSO UID
	 * @throws SmapAuthenticateUserBySessionIdException
	 * @throws SmapAuthenticateTypeNotSatisfied
	 */
	public String userLoginBySessionData(String sessionData) {
		XSmap2Client client = null;
		try {
			client = XSmap2ClientFactory.getInstance(localAppId, authenticateUrls);
		} catch (Exception e1) {
			logger.error(PromptMessageComm.UNABLE_TO_GET_SSO_CLIENT + e1.getMessage(), e1);
			throw new BusinessException(PromptMessageComm.UNABLE_TO_GET_SSO_CLIENT);
		}
		String uid = null;
		if (client != null) {
			TicketBall tb = null;
			try {
				tb = client.accessApplication(sessionData);
			} catch (SmapAuthenticateTypeNotSatisfied | SmapAuthenticateUserBySessionIdException e) {
				logger.error(PromptMessageComm.SSO_CHECK_SESSIONDATA_EXCEPTION + e.getMessage(), e);
				throw new BusinessException(PromptMessageComm.SSO_CHECK_SESSIONDATA_EXCEPTION);
			}
			if (tb != null && tb.getTicket() != null && tb.getTicket().getUid() != null) {
				uid = tb.getTicket().getUid().toLowerCase();
			}
			return uid;
		} else {
			logger.error(PromptMessageComm.SSO_CLIENT_IS_EMPTY);
			throw new BusinessException(PromptMessageComm.SSO_CLIENT_IS_EMPTY);
		}
	}

}