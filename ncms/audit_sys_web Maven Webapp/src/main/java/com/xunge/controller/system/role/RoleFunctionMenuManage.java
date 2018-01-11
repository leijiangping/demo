package com.xunge.controller.system.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.core.exception.BaseException;
import com.xunge.model.system.mannage.MenuTreeNodeVO;
import com.xunge.service.IMenuManagementService;


@Controller
@RequestMapping("/asserts/tpl/systemManage/role")
public class RoleFunctionMenuManage extends BaseException {

    @Autowired
    private IMenuManagementService menuManService;
    /**
     * 获取菜单
     * @param menuId
     * @return
     */
    @RequestMapping(value="/getFunctionMenuManage", method = RequestMethod.POST)
    public @ResponseBody List<MenuTreeNodeVO> getFunctionMenuManage(String menuId) {
    	List<MenuTreeNodeVO> MenuTreeNodeList = menuManService.queryFunctionMenuTreeRedis(menuId);
    	return MenuTreeNodeList;
    }
}
