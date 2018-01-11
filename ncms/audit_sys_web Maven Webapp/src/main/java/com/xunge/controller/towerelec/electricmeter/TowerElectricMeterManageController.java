package com.xunge.controller.towerelec.electricmeter;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.github.pagehelper.PageInfo;
import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.BaseRet;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.selfelec.electricmeter.DatElectricMeterVO;
import com.xunge.service.ISysProvinceService;
import com.xunge.service.selfelec.electricmeter.IElecticMeterService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.tool.IRegionService;
/**
 * @description 塔维电费 电表管理 Controller
 * @author yuefy
 * @date 创建时间：2017年9月18日
 */
@Controller      
@RequestMapping("asserts/tpl/towerelec/electricmeter")
public class TowerElectricMeterManageController extends BaseException {

	@Autowired
	private IElecticMeterService electicMeterService;
	
	@Resource
	private IRegionService regionService;
	
	@Resource
	private ISysProvinceService sysProvinceService;
	
	@Resource
	private ISysRegionService sysRegionService;
	
	/**
	 * 查询电表信息列表
	 * @return
	 */
	@RequestMapping(value = "/queryElectricMeter", method = RequestMethod.POST)
	public @ResponseBody BaseRet queryElectricMeter(HttpSession session,String city,String region,String meterCode,String meterState,String meterType,String relateResState,int cur_page_num, int page_count) {
		BaseRet baseRet = new BaseRet();
		try{
			meterCode = meterCode.trim();
			String prvId = "";
			String userId="";
			List<String> regIds = null;
			Object userObj = session.getAttribute("user");
			if(userObj == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			if (userObj != null) {
				UserLoginInfo userLoginInfo = (UserLoginInfo) userObj;
				prvId = userLoginInfo.getPrv_id();
				userId=userLoginInfo.getUser_id();
				regIds=userLoginInfo.getReg_ids();
			}
			PageInfo<DatElectricMeterVO> page = electicMeterService.findElectricMeter(regIds,userId,prvId,city,region,meterCode,meterState,meterType,relateResState,cur_page_num,page_count);
			baseRet.setStatus(SelfelecComm.STATUS_0);
			baseRet.setMessage(PromptMessageComm.OPERATION_SUSSESS);
			baseRet.setData(page);
		}catch(Exception e){
			baseRet.setStatus(SelfelecComm.STATUS_1);
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	}
	
	
	/**
	 * 导出电表信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exportElectricMeter", method = RequestMethod.POST)
	public @ResponseBody BaseRet exportElectricMeter(HttpSession session,HttpServletRequest request,String city,String region,String meterCode,String meterState,String meterType,String relateResState) {
		BaseRet baseRet = new BaseRet();
		try{
			String prvid = "";
			String userId="";
			List<String> regIds = null;
			Object userObj = session.getAttribute("user");
			if(userObj == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			if (userObj != null) {
				UserLoginInfo userLoginInfo = (UserLoginInfo) userObj;
				prvid = userLoginInfo.getPrv_id();
				userId=userLoginInfo.getUser_id();
				regIds = userLoginInfo.getReg_ids();
			}
			String requestUrl=request.getRequestURL().toString();
			String url=requestUrl.substring(0,requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + PromptMessageComm.WAY_OF_ELECTRICMETER;
			String fileName = electicMeterService.exportElectricMeter(regIds,userId,prvid,path,city,region,meterCode,meterState,meterType,relateResState);
			if(StateComm.STATE_str1.equals(fileName)){
				baseRet.setStatus(SelfelecComm.STATUS_1);
				baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
			}
			else{
				baseRet.setStatus(SelfelecComm.STATUS_0);
				baseRet.setMessage(PromptMessageComm.OPERATION_SUSSESS);
				baseRet.setData(url+PromptMessageComm.WAY_OF_EXPORT_ELECTRICMETER+fileName);
			}
			
		}catch(Exception e){
			baseRet.setStatus(SelfelecComm.STATUS_1);
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	}
	
	/**
	 * 导入电表信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/importElectricMeter", method = RequestMethod.POST)
	public @ResponseBody BaseRet importElectricMeter(HttpSession session,HttpServletRequest request,Exception ex) {
		BaseRet baseRet = new BaseRet();
		
		if(ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){  
	         String num =getFileMB(((org.springframework.web.multipart.MaxUploadSizeExceededException)ex).getMaxUploadSize());
	         baseRet.setStatus(SelfelecComm.STATUS_1);
	     	 baseRet.setMessage(PromptMessageComm.UPLOAD_FILE_OVER+num+PromptMessageComm.UPLOAD_FILE_BIG_END);
	         return baseRet;
	    } 
		try{CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

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
		    String errorMsg=PromptMessageComm.OPERATION_SUSSESS;
			if(iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multipartRequest.getFile(iter.next().toString());
				Object[] result=electicMeterService.importElectricMeter(prvId,file);
				int status=Integer.parseInt(result[0].toString());
				errorMsg=result[1].toString();
				baseRet.setStatus(status);
				baseRet.setMessage(errorMsg);
			}
		}
		}catch(Exception e){
			baseRet.setStatus(SelfelecComm.STATUS_1);
			baseRet.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	    return baseRet;
	}
	
    private String getFileMB(long byteFile){  
        if(byteFile== Integer.parseInt(SelfelecComm.NUMBER_0))  
           return PromptMessageComm.SIZE_0MB;  
        long mb=1024*1024;  
        return ""+byteFile/mb+PromptMessageComm.SIZE_MB;  
    }  
	
    /**
     * 添加
     */
    @RequestMapping(value = "/addElectricMeter", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet addElectricMeter(HttpSession session,DatElectricMeterVO datElectricMeterVO){
    	
    	BaseRet ret = new BaseRet();
    	if(!isExistElectricMeter(datElectricMeterVO)){
	    	UserLoginInfo userLoginInfo =(UserLoginInfo)session.getAttribute("user");
	    	if(userLoginInfo == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
	    	String prvid = userLoginInfo.getPrv_id();
	    	datElectricMeterVO.setPrvId(prvid);
		    String dateStr=datElectricMeterVO.getDateStr();
		    if(StringUtils.isNoneBlank(dateStr)){
		    	datElectricMeterVO.setInstallDate(DateUtil.parse(dateStr));
		    }
    		boolean result= electicMeterService.addDatElectricMeter(datElectricMeterVO);
        	if(result){
        		ret.setStatus(SelfelecComm.STATUS_0);
        		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
        	}
        	else{
        		ret.setStatus(SelfelecComm.STATUS_1);
    			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
        	}
    	}
    	else{
    		ret.setStatus(SelfelecComm.STATUS_1);
			ret.setMessage(PromptMessageComm.METER_EXISTS);
    	}
    	return ret;
    }
    
    
    /**
     * 修改
     */
    @RequestMapping(value = "/editElectricMeter", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet editElectricMeter(DatElectricMeterVO datElectricMeterVO){
    	
		BaseRet ret = new BaseRet();
		String dateStr=datElectricMeterVO.getDateStr();
	    if(StringUtils.isNoneBlank(dateStr)){
	    	datElectricMeterVO.setInstallDate(DateUtil.parse(dateStr));
	    }
		boolean result = electicMeterService.editDatElectricMeter(datElectricMeterVO);
		if (result) {
			ret.setStatus(SelfelecComm.STATUS_0);
			ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
		} else {
			ret.setStatus(SelfelecComm.STATUS_1);
			ret.setMessage(PromptMessageComm.OPERATION_FAILED);
		}
	
    	return ret;
    }
    
    
    /**
     * 删除
     */
    @RequestMapping(value = "/delElectricMeter", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet delElectricMeter(String ids){
    	
    	BaseRet ret = new BaseRet();
    	String[] idArray = ids.split(",");
    	boolean result= electicMeterService.delDatElectricMeter(idArray);
    	if(result){
    		ret.setStatus(SelfelecComm.STATUS_0);
    		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else{
    		ret.setStatus(SelfelecComm.STATUS_1);
			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
    	}
    	return ret;
    }
    
	
    private boolean isExistElectricMeter(DatElectricMeterVO datElectricMeterVO){
    	
    	boolean isExist=false;
    	String meterCode=datElectricMeterVO.getMeterCode();
    	isExist=electicMeterService.isExistMeterCode(meterCode);
		return isExist;
    }
    
    
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressRent", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressRent(@ModelAttribute("user") UserLoginInfo loginUser) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userId = loginUser.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
    
	/**
	 * 获取地市列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/citys", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject getCitys(HttpServletRequest request){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		feedbackObj.Obj = regionService.getCitys(prv_id);
		return feedbackObj;
	}
	
	/**
	 * 获取区县列表
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value="/regions", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject getRegions(@Validated String cityId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = regionService.getRegion(cityId);
		return feedbackObj;
	}
	
	@RequestMapping(value="/queryOneMeter", method=RequestMethod.GET)
	public @ResponseBody FeedBackObject queryElecMeterId(String elecMeterId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = electicMeterService.queryElecMeterId(elecMeterId);
		return feedbackObj;
	}
	
	@RequestMapping(value="/queryMeterByMeterId", method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryMeterByMeterId(@RequestBody List<String> items){
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String, Object>  elecMeterId=new HashMap<>();
		elecMeterId.put("meterId", items);
		feedbackObj.Obj = electicMeterService.queryMeterByMeterId(elecMeterId);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}	
}
