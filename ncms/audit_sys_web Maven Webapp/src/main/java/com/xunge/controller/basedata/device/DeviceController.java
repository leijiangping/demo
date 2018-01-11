package com.xunge.controller.basedata.device;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.enums.ResourceEnum.ResStateEnum;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.vo.DeviceQueryVO;
import com.xunge.service.basedata.device.IDeviceService;

/**
 * 设备管理
 * 
 * Title: DeviceController
 * 
 * @author Rob
 */
@RestController
@RequestMapping("/asserts/tpl/basedata/device")
public class DeviceController extends BaseException {

	@Resource
	private IDeviceService deviceService;

	/**
	 * 设备列表查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public FeedBackObject getList(@Validated DeviceQueryVO vo,
			HttpServletRequest request) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;

		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		vo.setLoginUser(loginInfo);

		feedBackObject.Obj = deviceService.getAll(vo);
		return feedBackObject;
	}

	@RequestMapping(value = "/one", method = RequestMethod.GET)
	public FeedBackObject getOne(@Validated String id) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = deviceService.get(id);
		return feedBackObject;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public FeedBackObject insert(DatBasestationVO record,
			HttpServletRequest request) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		record.setBasestationId(PromptMessageComm.BASE_STATION_ID + System.currentTimeMillis());
		record.setBasestationState(ResStateEnum.ENTERNET);
		record.setDataFrom(DataFromComm.STATE_0);
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if (loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		record.setPrvId(prv_id);
		int result = deviceService.insert(record);
		feedBackObject.Obj = result;
		if (result == Integer.parseInt(RESULT.SUCCESS_1)) {
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_SUCCESS;
		} else {
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_FAILED;
		}
		return feedBackObject;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject update(DatBasestationVO record) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		int result = deviceService.update(record);
		feedBackObject.Obj = result;
		if (result == Integer.parseInt(RESULT.SUCCESS_1)) {
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_SUCCESS;
		} else {
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_FAILED;
		}
		return feedBackObject;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public FeedBackObject delete(@RequestBody List<DatBasestationVO> list) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = deviceService.deleteBatch(list);
		return feedBackObject;
	}

	@RequestMapping(value = "/export")
	public @ResponseBody
	FeedBackObject export(@Validated DeviceQueryVO vo,
			HttpServletRequest request) {
		FeedBackObject feedBackObject = new FeedBackObject();
		String requestUrl = request.getRequestURL().toString();
		String url = requestUrl.substring(0, requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
		String path = request.getSession().getServletContext().getRealPath("/")
				+ File.separator + PromptMessageComm.WAY_OF_WEB_INF;

		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		vo.setLoginUser(loginInfo);

		String fileName = deviceService.export(vo, path);
		if (!fileName.equals("")) {
			feedBackObject.success = RESULT.SUCCESS_1;
			feedBackObject.msg = PromptMessageComm.EXPORT_INFO_SUCCESS;
			feedBackObject.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT + fileName;
		} else {
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = PromptMessageComm.EXPORT_INFO_FAIL;
			feedBackObject.Obj = null;
		}
		return feedBackObject;
	}

	/**
	 * @description 查询设备制造商
	 * @author yuefy
	 * @date 创建时间：2017年9月20日
	 */
	@RequestMapping(value = "/selectDevice")
	public @ResponseBody FeedBackObject selectDevice(String basestationVendor) {
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		Map<String,Object> map = new HashMap<>();
		feedBackObject.Obj = deviceService.selectDevice(map);
		return feedBackObject;
	}
}
