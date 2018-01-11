package com.xunge.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.core.exception.BaseException;
import com.xunge.model.FeedBackObject;
import com.xunge.model.SysDataAuthMenuTreeVO;
import com.xunge.service.ISysProvinceService;

@Controller
@RequestMapping("/asserts/tpl/system/user")
public class RegionManageController extends BaseException {
	
	@Autowired
    private ISysProvinceService sysProvinceService;
    /**
     * 根据省份id查询区县信息树
     * @param prvId
     * @return
     */
	@RequestMapping(value="/queryOnePro", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryOnePro(String prvId){
		FeedBackObject feedBackObject = new FeedBackObject();
		List<SysDataAuthMenuTreeVO> lsmt = sysProvinceService.queryOneProRedis(prvId);
		feedBackObject.Obj = lsmt;
		return feedBackObject;
	}
}
