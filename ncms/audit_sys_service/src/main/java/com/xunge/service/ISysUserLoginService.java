package com.xunge.service;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.system.user.SysUserVO;

/**
 * 用户登录 Service 接口
 * @author yuefy
 *
 */
public interface ISysUserLoginService {
	/**
	 * 登录方法
	 */
	public UserLoginInfo getUserLoginInfoRedis(SysUserVO user);

}
