package com.xunge.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.core.exception.BaseException;
import com.xunge.model.SysDataAuthMenuTreeVO;
import com.xunge.service.ISysDataAuthMenuTreeService;

@Controller
@RequestMapping("/asserts/tpl/system")
public class DataAuthMenuTree extends BaseException {
	
	@Autowired
    private ISysDataAuthMenuTreeService sysDataAuthMenuTreeService;
    /**
     * 获取菜单树
     * @return
     */
	@RequestMapping(value="/role/queryAllMenuTree", method = RequestMethod.POST)
	public @ResponseBody List<SysDataAuthMenuTreeVO> queryAllMenuTree(){
		List<SysDataAuthMenuTreeVO> lsmt = sysDataAuthMenuTreeService.queryAllMenuTreeRedis();
		return lsmt;
	}
}
