package com.xunge.controller.basedata.supplier;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.TaskComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.rent.contract.SupplierStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.BaseRet;
import com.xunge.controller.basedata.util.FileUtils;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.basedata.vo.SupplierQueryVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.basedata.supplier.ISupplierService;
import com.xunge.service.system.region.ISysRegionService;

/**
 * 供应商管理
* 
* Title: SupplierController
* @author Rob
 */
@RestController
@RequestMapping("/asserts/tpl/basedata/supplier")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class SupplierController extends BaseException {

	@Resource
	private ISupplierService supplierService;

	@Autowired 
	private ISysRegionService sysRegionService;
	/**
	 * 供应商列表查询
	 * @return
	 */
	@RequestMapping(value="/list")
	public FeedBackObject getList(@Validated SupplierQueryVO vo,HttpServletRequest request,
			String sRegId,String sPregId,Integer dataFrom){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		
		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		vo.setLoginUser(loginInfo);
		if(sRegId != null && sRegId != ""){
			vo.setRegion(sRegId);
		}
		if(sPregId != null && sPregId != ""){
			vo.setCity(sPregId);
		}
		if(dataFrom != null){
			vo.setDataFrom(dataFrom);
		}
		feedBackObject.Obj = supplierService.getAll(vo);
		return feedBackObject;
	}
	
	@RequestMapping(value="/one", method=RequestMethod.GET)
	public FeedBackObject getOne(@Validated String id){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = supplierService.get(id);
		return feedBackObject;
	}
	
	@RequestMapping(value="/checkSupplierCode")
	public FeedBackObject checkSupplierCode(String supplierCode,HttpServletRequest request){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("supplierCode", supplierCode);
		paramMap.put("supplierState",StateComm.STATE_str0);
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		paramMap.put("prvId", loginInfo.getPrv_id());
		paramMap.put("pregIds", loginInfo.getPreg_ids());
		paramMap.put("regIds", loginInfo.getReg_ids());
		feedBackObject.Obj = supplierService.querySupplierVOByCode(paramMap);
		return feedBackObject;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public FeedBackObject insert(@RequestBody DatSupplierVO record, HttpServletRequest request){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		record.setSupplierId(PromptMessageComm.SUPPLIER_ID+System.currentTimeMillis());
		record.setSupplierState(SupplierStateComm.STATE_0);
		record.setDataFrom(DataFromComm.STATE_0);
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		record.setPrvId(prv_id);
		int result = supplierService.insert(record);
		feedBackObject.Obj = result;
		if(result == Integer.parseInt(RESULT.SUCCESS_1)){
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_SUCCESS;
		}else{
			feedBackObject.msg = PromptMessageComm.OPERATION_INSERT_FAILED;
		}
		return feedBackObject;
	}
	
	@RequestMapping(value="/modify")
	public FeedBackObject update(@RequestBody DatSupplierVO record){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		record.setSupplierState(SupplierStateComm.STATE_0);
		int result = supplierService.update(record);
		feedBackObject.Obj = result;
		if(result == Integer.parseInt(RESULT.SUCCESS_1)){
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_SUCCESS;
		}else{
			feedBackObject.msg = PromptMessageComm.OPERATION_UPDATE_FAILED;
		}
		return feedBackObject;
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public FeedBackObject delete(@RequestBody List<DatSupplierVO> list){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = supplierService.deleteBatch(list);
		return feedBackObject;
	}
	/**
	 * 导出数据
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/export", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject export(@Validated SupplierQueryVO vo, 
			HttpServletRequest request,HttpServletResponse response,Integer dataFrom){
		FeedBackObject feedBackObject = new FeedBackObject();
		String requestUrl = request.getRequestURL().toString();
		String url = requestUrl.substring(0, requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF;
		
		// 数据权限过滤(根据登录用户过滤)
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		vo.setLoginUser(loginInfo);
		vo.setDataFrom(dataFrom);
		String fileName = supplierService.export(vo, path,request,response);
		if (!fileName.equals("")) {
			feedBackObject.success = RESULT.SUCCESS_1;
			feedBackObject.msg = PromptMessageComm.MAKE_OUT_FILE_SUCCESS;
			feedBackObject.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT + fileName;
		} else {
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = PromptMessageComm.MAKE_OUT_FILE_FAILED;
			feedBackObject.Obj = null;
		}
		return feedBackObject;
	}
	/**
	 * 导入信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody BaseRet importData(HttpSession session,HttpServletRequest request,Exception ex) {
		BaseRet baseRet = new BaseRet();
		if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){  
	         String num = FileUtils.getFileMB(((org.springframework.web.multipart.MaxUploadSizeExceededException)ex).getMaxUploadSize());
	         baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
	     	 baseRet.setMessage(PromptMessageComm.UPLOAD_FILE_BIG_HEAD+num+PromptMessageComm.UPLOAD_FILE_BIG_END);
	         return baseRet;
	    } 
		try{
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multipartRequest.getFileNames();
				String prvId="";
				Object userObj=session.getAttribute("user");
				if(userObj == null){
					throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
				}
			    if(userObj!=null){
			    	UserLoginInfo userLoginInfo =(UserLoginInfo)userObj;
			    	prvId=userLoginInfo.getPrv_id();
			    }

				Map<String,Object> map=Maps.newHashMap();
			  //设置自定义数据处理
				map.put("state",StateComm.STATE_str0);

				//获取当前用户所属地区
				UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
				if(loginInfo == null){
					throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
				}
				map.put("prvId",loginInfo.getPrv_id());
				
				List<SysRegionVO> listreg=sysRegionService.getAddress(map);//准备需要的数据处理
			    String errorMsg=PromptMessageComm.OPERATION_SUSSESS;
				if(iter.hasNext()) {
					// 一次遍历所有文件
					MultipartFile file = multipartRequest.getFile(iter.next().toString());
					Object[] result = supplierService.importData(prvId, file,listreg);
					int status=Integer.parseInt(result[0].toString());
					errorMsg=result[1].toString();
					baseRet.setStatus(status);
					baseRet.setMessage(errorMsg);
				}
			}
		}catch(Exception e){
			baseRet.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	}
}
