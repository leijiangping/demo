package com.xunge.core.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 权限控制
 * @author zhujj
 * @date 2017年8月4日 下午4:41:54 
 * @version 1.0.0 
 */
public class UserAuthor {
	/**
	 * 获取用户区县权限过滤
	 * @param alias
	 * @param list
	 * @return
	 */
	public String getUserAuthor(String alias,List<String> list){
		String sql="and ${alias}.reg_id in("+StringUtils.join(list.toArray(),",")+")";
		return sql;
	}
}
