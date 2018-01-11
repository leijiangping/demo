package com.xunge.controller.towerrent.settlement;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.exception.ParameterException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.towerrent.rentinformationhistory.ITwrRentInformationHistoryService;
import com.xunge.service.twrrent.settlement.ITowerBillbalanceService;

/**
 * @description 铁塔账单Controller
 * @author zhujj
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "/asserts/tpl/towerrent/settlement")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class TowerBillbalanceController extends BaseException{

	@Autowired
	private ITowerBillbalanceService towerBillbalanceService;
	@Autowired 
	private ISysRegionService sysRegionService;
	@Autowired
	private ITwrRentInformationHistoryService towerRentInfoHistoryService;
	
	@Autowired
	private ILogService log;
	/**
	 * @description 查询铁塔账单分页数据
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryTowerBillbalanceList",method=RequestMethod.POST)
	@ResponseBody
	public  FeedBackObject queryTowerBillbalanceList(@RequestParam Map<String,Object> map,@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.SELECT_INFO_SUCCESS;
		//获取当前用户所属地区
		//获取当前用户所属地区,权限控制
		map.put("regIds",loginInfo.getReg_ids());
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		feedbackObj.Obj =towerBillbalanceService.queryPageTowerBillbalance(map);
		return feedbackObj;
	}

	/**
	 * @description 导入铁塔租赁账单
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/importFile", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject importFile(@ModelAttribute("user") UserLoginInfo loginInfo,MultipartFile file,String suffix,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		try {
			Map<String,Object> returnMap=towerBillbalanceService.insertExcelData(file,suffix,request);
		
			feedbackObj.Obj=returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_TOWERRENT_BILLS_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");

			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		
		return feedbackObj;
	}
	
    /**
	 * @description 导入更新铁塔账单
	 * @param file
	 * @return
	 */
    @RequestMapping(value = "/importUpdateFile", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject importUpdateFile(@ModelAttribute("user") UserLoginInfo loginInfo,MultipartFile file,String suffix,HttpServletRequest request,HttpServletResponse response){
    	if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject feedbackObj = new FeedBackObject();
		try {

			Map<String,Object> returnMap=towerBillbalanceService.updateExcelData(file,suffix,request);
		
			feedbackObj.Obj=returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =returnMap.get("msg").toString();
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
			throw new BusinessException(PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE);
		}
		return feedbackObj;
	}
	/**
	 * @description 导出铁塔账单数据
	 */
	@RequestMapping(value="/export")
	public void export(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		towerBillbalanceService.export(map, request, response);
	}
	
	/**
	 * @description 查询移动账单分页数据
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryMobileBillbalanceList",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryMobileBillbalanceList(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		map.put("dataType", SelfelecComm.NUMBER_2);
		//获取当前用户所属地区
		//获取当前用户所属地区,权限控制
		map.put("alias", PromptMessageComm.ALIAS_NAME_REG);
		map.put("regIds",loginInfo.getReg_ids());
		map.put("userId", loginInfo.getUser_id());
		
		feedbackObj.Obj = towerBillbalanceService.queryPageMobileBillbalance(map);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.SELECT_INFO_SUCCESS;
		return feedbackObj;
	}
	
	/**
	 * @description 查询移动起租信息拆分表数据
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/queryRentHistroyList",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryRentHistroyList(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		//获取当前用户所属地区
		//获取当前用户所属地区,权限控制
		map.put("alias", PromptMessageComm.ALIAS_NAME_REG);
		map.put("regIds",loginInfo.getReg_ids());
		
		map.put("dataType",SelfelecComm.NUMBER_2 );
		if (map.containsKey("accountPeroid")) {
			String accountPeroid = map.get("accountPeroid").toString();
			Date perDate = DateUtil.parse(accountPeroid, SelfelecComm.FORMAT);
			map.put("monthstartday", accountPeroid + SelfelecComm.NUMBER_STR);
			map.put("monthendday", accountPeroid + DateUtil.gettotalDayOfMonth(perDate));
		}
		feedbackObj.Obj = towerRentInfoHistoryService.queryPageRentInfoHistory(map);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.SELECT_INFO_SUCCESS;
		return feedbackObj;
	}
	
	/**
	 * @description 导出移动账单数据
	 * @author wangz
	 * @param Map
	 */
	@RequestMapping(value="/exportMobilePayMent")
	public void exportMobilePaymentDatil(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;

		towerBillbalanceService.exportMobile(map, request, response);
	}
	
	/**
	 * @description 生成账单
	 * @param Map
	 * @author wangz
	 * @return
	 */
	@RequestMapping(value="/generateBills",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject generateBills(@RequestParam Map<String, Object> map,@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		
		//获取当前用户所属地区,权限控制
		map.put("alias", PromptMessageComm.ALIAS_NAME_REG);
		map.put("regIds",loginInfo.getReg_ids());
		
		generate(map);
		
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.GENERATED_BILLS_SUCCESS;
		return feedBackObject;
	}
	
	/**
	 * @description 重新生成账单
	 * @param Map
	 * @author wangz
	 * @return
	 */
	@RequestMapping(value="/reGenerateBills")
	public @ResponseBody FeedBackObject reGenerateBills(@RequestParam Map<String,Object> map,@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		//获取当前用户所属地区,权限控制
		map.put("alias", PromptMessageComm.ALIAS_NAME_REG);
		map.put("regIds",loginInfo.getReg_ids());
		generate(map);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg =PromptMessageComm.AGAIN_GENERATED_BILLS_SUCCESS;
		return feedBackObject;
	}
	
	/**
	 * 根据传入的参数生成账单
	 * @author wangz
	 * @param map
	 */
	public void generate(Map<String, Object> map){
		if (map.containsKey("accountPeroid")) {
			String accountPeroid = map.get("accountPeroid").toString();
			Date perDate = DateUtil.parse(accountPeroid,SelfelecComm.FORMAT );
			map.put("monthstartday", accountPeroid + SelfelecComm.NUMBER_STR);
			map.put("monthendday", accountPeroid + DateUtil.gettotalDayOfMonth(perDate));
		}
		// 查出的数据多样性
		List<TowerRentInformationHistoryVO> list = towerRentInfoHistoryService.queryTowerRentInfoHistory(map);
		Map<Integer,TowerBillbalanceVO> resultMap = new HashMap<Integer,TowerBillbalanceVO>();
		for (TowerRentInformationHistoryVO bean : list) {
			resultMap = processData(bean.getStartDate(), bean.getEndDate(), bean, resultMap);
		}
		
		// 查询移动账单业务确认单号，铁塔编码，账单日期数据封装map进行判断
		Map<Integer, TowerBillbalanceVO> tbvMap = new HashMap<Integer, TowerBillbalanceVO>();
		List<TowerBillbalanceVO> tbvs = towerBillbalanceService.queryMobileBill(map);
		for (TowerBillbalanceVO tbv : tbvs) {
			int code = (tbv.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL + tbv.getTowerStationCode() + PromptMessageComm.JING_SYMBOL
					+ tbv.getAccountPeroid()).hashCode();
			tbvMap.put(code, tbv);
		}
		
		List<TowerBillbalanceVO> insertlistVo = new ArrayList<TowerBillbalanceVO>();
		List<TowerBillbalanceVO> updatelistVo = new ArrayList<TowerBillbalanceVO>();
		
		for (Map.Entry<Integer, TowerBillbalanceVO> entry : resultMap.entrySet()) {
			if(tbvMap.containsKey(entry.getKey())) {
				updatelistVo.add(entry.getValue());
			} else {
				insertlistVo.add(entry.getValue());
			}
		}
		
		if (!insertlistVo.isEmpty()) {
			towerBillbalanceService.insertBatchMobileBill(insertlistVo);
			log.info(SysLogComm.LOG_Operate, PromptMessageComm.INSERT_INFO + insertlistVo.size() +PromptMessageComm.DATAS);
		} else {
			towerBillbalanceService.updateBatchMobileBill(updatelistVo);
			log.info(SysLogComm.LOG_Operate, PromptMessageComm.UPDATE_INFO + updatelistVo.size() +PromptMessageComm.DATAS);
		}
	}
	
	/**
	 * 根据拆分表中某条数据生成改时间段内的账单数据
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param historyVO 拆分表封装bean
	 * @param map 保存账单的map
	 * @return
	 */
	public static Map<Integer, TowerBillbalanceVO> processData(Date startDate,Date endDate,TowerRentInformationHistoryVO historyVO,Map<Integer, TowerBillbalanceVO> map){
		SimpleDateFormat sdf = new SimpleDateFormat(SelfelecComm.FORMAT);
		int totalDays = 0;
		Calendar cal = Calendar.getInstance();
		// 开始日期
		cal.setTime(startDate);
		int startmonth = cal.get(Calendar.MONTH) + SelfelecComm.NUMBER_1;
		int start_currentDay = cal.get(Calendar.DAY_OF_MONTH);
		totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 结束日期
		cal.setTime(endDate);
		int endmonth = cal.get(Calendar.MONTH) + SelfelecComm.NUMBER_1;
		int end_currentDay = cal.get(Calendar.DAY_OF_MONTH);
		
		// 一个自然月
		if (startmonth == endmonth && end_currentDay - start_currentDay == totalDays -1) {
			int code = (historyVO.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL + historyVO.getTowerStationCode() +PromptMessageComm.JING_SYMBOL +sdf.format(startDate)).hashCode();
			BigDecimal times = div((end_currentDay - start_currentDay), totalDays);
			TowerBillbalanceVO mbv = getObject(historyVO, times, sdf.format(startDate));
			map.put(code, mbv);
		} else {
			// 月份第一天
			if (1 == start_currentDay && endmonth > startmonth) {
				int code = (historyVO.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL + historyVO.getTowerStationCode() +PromptMessageComm.JING_SYMBOL+sdf.format(startDate)).hashCode();
				TowerBillbalanceVO mbv = getObject(historyVO, new BigDecimal(1),sdf.format(startDate));
				if (map.containsKey(code)) {
					mbv = addBill(mbv,map.get(code));
				}
				map.put(code, mbv);
			} else {
				BigDecimal times = div((totalDays - start_currentDay),totalDays);
				TowerBillbalanceVO mbv = getObject(historyVO, times,sdf.format(startDate));
				int code = (historyVO.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL + historyVO.getTowerStationCode() +PromptMessageComm.JING_SYMBOL+sdf.format(startDate)).hashCode();
				if (map.containsKey(code)) {
					mbv = addBill(mbv,map.get(code));
				}
				map.put(code, mbv);
			}
			
			for (int i = startmonth + 1; i < endmonth; i++) {
				String accountPeroid = cal.get(Calendar.YEAR) + (i < 10 ? StateComm.STATE_str0 + i : i + PromptMessageComm.KONG_SYMBOL);
				TowerBillbalanceVO mbv = getObject(historyVO, new BigDecimal(1),accountPeroid);
				int code = (historyVO.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL + historyVO.getTowerStationCode() +PromptMessageComm.JING_SYMBOL +accountPeroid).hashCode();
				if (map.containsKey(code)) {
					mbv = addBill(mbv,map.get(code));
				}
				map.put(code, mbv);
			}
			
			totalDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			// 月份最后一天
			if (totalDays == end_currentDay && endmonth > startmonth) {
				TowerBillbalanceVO mbv = getObject(historyVO, new BigDecimal(1),sdf.format(endDate));
				int code = (historyVO.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL  + historyVO.getTowerStationCode() +PromptMessageComm.JING_SYMBOL +sdf.format(endDate)).hashCode();
				if (map.containsKey(code)) {
					mbv = addBill(mbv,map.get(code));
				}
				map.put(code, mbv);
			} else {
				BigDecimal times = div(end_currentDay,totalDays);
				TowerBillbalanceVO mbv = getObject(historyVO, times,sdf.format(endDate));
				int code = (historyVO.getBusinessConfirmNumber() + PromptMessageComm.JING_SYMBOL + historyVO.getTowerStationCode() +PromptMessageComm.JING_SYMBOL +sdf.format(endDate)).hashCode();
				
				if (map.containsKey(code)) {
					mbv = addBill(mbv,map.get(code));
				}
				map.put(code, mbv);
				
			}
		}
		return map;
	}
	
	/**
	 * 生成的账单数据重复，叠加费用
	 * @param mbv 
	 * @param oldMbv
	 * @return
	 */
	public static TowerBillbalanceVO addBill(TowerBillbalanceVO mbv,TowerBillbalanceVO oldMbv){
		// 费用计算
		//油机发电服务费（包干）TowerAndMobileBillVO（出账费用）
		mbv.setUsePowerServiceFeeOut(mbv.getUsePowerServiceFeeOut().add(oldMbv.getUsePowerServiceFeeOut()));
		//超过10%高等级服务站址额外维护服务费（出账费用）
		mbv.setHightLevelFeeOut(mbv.getHightLevelFeeOut().add(oldMbv.getHightLevelFeeOut()));
		//蓄电池额外保障费（出账费用）
		mbv.setBatteryprotectionfeeOut(mbv.getBatteryprotectionfeeOut().add(oldMbv.getBatteryprotectionfeeOut()));
		//期末铁塔共享后基准价格1+2+3（出账费用）
		mbv.setCurrentTowerShareSumPriceOut(mbv.getCurrentTowerShareSumPriceOut().add(oldMbv.getCurrentTowerShareSumPriceOut()));
		//期末机房共享后基准价格1+2+3（出账费用）
		mbv.setCurrentRoomShareSumPriceOut(mbv.getCurrentRoomShareSumPriceOut().add(oldMbv.getCurrentRoomShareSumPriceOut()));
		//配套共享后基准价格1+2+3（出账费用）
		mbv.setCurrentSupportingShareSumPriceOut(mbv.getCurrentSupportingShareSumPriceOut().add(oldMbv.getCurrentSupportingShareSumPriceOut()));
		//bbu安装在铁塔机房费（出账费用）
		mbv.setBbuOnRoomFeeBack(mbv.getBbuOnRoomFeeBack().add(oldMbv.getBbuOnRoomFeeBack()));
		//维护费折扣后金额1+2+3（出账费用）
		mbv.setCurrentManagerFeeShareSumPriceOut(mbv.getCurrentManagerFeeShareSumPriceOut().add(oldMbv.getCurrentManagerFeeShareSumPriceOut()));
		//场地费折扣后金额（出账费用）
		mbv.setCurrentPraceFeeShareSumPriceOut(mbv.getCurrentPraceFeeShareSumPriceOut().add(oldMbv.getCurrentPraceFeeShareSumPriceOut()));
		//电力引入费折扣后金额（出账费用）
		mbv.setCurrentPowerInFeeShareSumPriceOut(mbv.getCurrentPowerInFeeShareSumPriceOut().add(oldMbv.getCurrentPowerInFeeShareSumPriceOut()));
		//WLAN费用（出账费用）
		mbv.setWlanFeeOut(mbv.getWlanFeeOut().add(oldMbv.getWlanFeeOut()));
		//微波费用（出账费用）
		mbv.setMicrowaveFeeOut(mbv.getMicrowaveFeeOut().add(oldMbv.getMicrowaveFeeOut()));
		//其他费用1（出账费用）
		mbv.setOtherFee1Out(mbv.getOtherFee1Out().add(oldMbv.getOtherFee1Out()));
		//产品服务费合计（出账费用）（不含税）
		mbv.setTotalAmountMonthOut(mbv.getTotalAmountMonthOut().add(oldMbv.getTotalAmountMonthOut()));
		//电费（包干）（出账费用）
		mbv.setElecFeeOut(mbv.getElecFeeOut().add(oldMbv.getElecFeeOut()));
		//油机发电服务费（非包干）（出账费用）
		mbv.setUsePowerServiceFeeOut2(mbv.getUsePowerServiceFeeOut2().add(oldMbv.getUsePowerServiceFeeOut2()));
		return mbv;
	}
	
	
	/**
	 * 除法计算返回bigdecimal，默认保存四位小数
	 * @param v1 被除数
	 * @param v2 除数
	 * @return
	 */
	private static BigDecimal div(int v1,int v2){   
		return div(v1,v2,SelfelecComm.NUMBER_4);   
	}  
	
	/**
	 * 除法计算返回bigdecimal，默认保存四位小数
	 * @param v1 被除数
	 * @param v2 除数
	 * @return
	 */
	private static BigDecimal div(BigDecimal v1,int v2){   
		return div(v1, v2, SelfelecComm.NUMBER_4);   
	} 
	
	/**
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 精确位数
	 * @return
	 */
	private static BigDecimal div(BigDecimal v1,int v2,int scale){   
		if(scale<0){   
			throw new ParameterException(PromptMessageComm.PROPORTION_INT_ZERO);   
		}   
		BigDecimal b2 = new BigDecimal(Double.toString(v2));   
		return v1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);   
	} 
	
	/**  
	* 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指  
	* 定精度，以后的数字四舍五入。  
	* @param v1 被除数  
	* @param v2 除数  
	* @param scale 表示表示需要精确到小数点以后几位。  
	* @return 两个参数的商  
	*/  
	private static BigDecimal div(int v1,int v2,int scale){   
		if(scale<0){   
			throw new ParameterException(PromptMessageComm.PROPORTION_INT_ZERO);   
		}   
		BigDecimal b1 = new BigDecimal(Double.toString(v1));   
		BigDecimal b2 = new BigDecimal(Double.toString(v2));   
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);   
	}   
	
	/**
	 * 计算不足一个自然月的费用
	 * @param obj
	 * @param times
	 * @return
	 */
	private static BigDecimal getCalc(Object obj,BigDecimal times){
		BigDecimal number = new BigDecimal(obj.toString());
		return div(number,SelfelecComm.NUMBER_12).multiply(times);
	}
	
	/**
	 * 计算费用，封装账单对象
	 * @author wangz
	 * @param historyVO
	 * @param times 
	 * @param accountPeroid 账期
	 * @return
	 */
	public static TowerBillbalanceVO getObject(TowerRentInformationHistoryVO historyVO,BigDecimal times,String accountPeroid){
		TowerBillbalanceVO tbv = new TowerBillbalanceVO(); 
		// 费用计算 0包干，3按次
		if (SelfelecComm.NUMBER_0.equals(historyVO.getOilGenerateElectricMethodId())) {
			//油机发电服务费（包干）（出账费用）
			tbv.setUsePowerServiceFeeOut(getCalc(historyVO.getOilGeneratorElectricFee(), times));
			tbv.setUsePowerServiceFeeOut2(new BigDecimal(0));
		} else {
			tbv.setUsePowerServiceFeeOut(new BigDecimal(0));
			//油机发电服务费（非包干）（出账费用）
			tbv.setUsePowerServiceFeeOut2(getCalc(historyVO.getOilGeneratorElectricFee(), times));
		}
		//超过10%高等级服务站址额外维护服务费（出账费用）
		tbv.setHightLevelFeeOut(getCalc(historyVO.getHightLevelFee(), times));
		//蓄电池额外保障费（出账费用）
		tbv.setBatteryprotectionfeeOut(getCalc(historyVO.getBatteryProtectionFee(), times));
		//期末铁塔共享后基准价格1+2+3（出账费用）
		tbv.setCurrentTowerShareSumPriceOut(getCalc(historyVO.getTowerPrice(), times));
		//期末机房共享后基准价格1+2+3（出账费用）
		tbv.setCurrentRoomShareSumPriceOut(getCalc(historyVO.getRoomPrice(), times));
		//配套共享后基准价格1+2+3（出账费用）
		tbv.setCurrentSupportingShareSumPriceOut(getCalc(historyVO.getSupportingPrice(), times));
		//bbu安装在铁塔机房费（出账费用）Product1/2/3
		tbv.setBbuOnRoomFeeBack(getCalc(new BigDecimal(historyVO.getProduct1BbuFee()), times));
		//维护费折扣后金额1+2+3（出账费用）
		tbv.setCurrentManagerFeeShareSumPriceOut(getCalc(historyVO.getMaintenanceFeeDis(), times));
		//场地费折扣后金额（出账费用）
		tbv.setCurrentPraceFeeShareSumPriceOut(getCalc(historyVO.getStageFeeDis(), times));
		//电力引入费折扣后金额（出账费用）
		tbv.setCurrentPowerInFeeShareSumPriceOut(getCalc(historyVO.getElectricImportFeeDis(), times));
		//WLAN费用（出账费用）
		tbv.setWlanFeeOut(getCalc(new BigDecimal(historyVO.getWlanServiceFee()), times));
		//微波费用（出账费用）
		tbv.setMicrowaveFeeOut(getCalc(new BigDecimal(historyVO.getMicrowaveServiceFee()), times));
		//其他费用1（出账费用）
		tbv.setOtherFee1Out(getCalc(historyVO.getOtherFee(), times));
		//产品服务费合计（出账费用）（不含税）
		tbv.setTotalAmountMonthOut(getCalc(historyVO.getTotalAmount(), times));
		//电费（包干）（出账费用）
		tbv.setElecFeeOut(getCalc(historyVO.getElectricProtectionFee(), times));
		
		tbv.setAccountPeroid(accountPeroid);
		
		BigDecimal defaultValue = new BigDecimal(SelfelecComm.STATUS_0);
		//设置其他属性
		tbv.setAdjustmentFee(defaultValue);
		tbv.setAdjustmentFeeNote(null);
		tbv.setAdjustmentFeeType(null);
		tbv.setAfterAdjustmentFee(defaultValue);
		
		tbv.setBatteryprotectionfee(defaultValue);
		tbv.setBatteryprotectionfeeDeductible(defaultValue);
		tbv.setBatteryprotectionfeeWaitReturn(defaultValue);
		tbv.setBbuOnRoomFee(defaultValue);
		tbv.setBbuOnRoomFeeDeductible(defaultValue);
		tbv.setBbuOnRoomFeeWaitReturn(defaultValue);
		tbv.setBusinessConfirmNumber(historyVO.getBusinessConfirmNumber());
		tbv.setCheckState(SelfelecComm.NUMBER_0);
		tbv.setConfirmState(SelfelecComm.STATUS_0);
		tbv.setCreateTime(new Date());
		tbv.setCurrentManagerFeeShareSumPrice(defaultValue);
		tbv.setCurrentManagerFeeShareSumPriceDeductible(defaultValue);
		tbv.setCurrentManagerFeeShareSumPriceWaitReturn(defaultValue);
		tbv.setCurrentPowerInFeeShareSumPrice(defaultValue);
		tbv.setCurrentPowerInFeeShareSumPriceDeductible(defaultValue);
		tbv.setCurrentPowerInFeeShareSumPriceWaitDeductible(defaultValue);
		tbv.setCurrentPraceFeeShareSumPrice(defaultValue);
		tbv.setCurrentPraceFeeShareSumPriceDeductible(defaultValue);
		tbv.setCurrentPraceFeeShareSumPriceWaitReturn(defaultValue);
		tbv.setCurrentSupportingShareNum(StateComm.STATE_0);
		tbv.setCurrentSupportingShareSumPrice(defaultValue);
		tbv.setCurrentSupportingShareSumPriceDeductible(defaultValue);
		tbv.setCurrentSupportingShareSumPriceWaitReturn(defaultValue);
		tbv.setCurrentTowerShareNum(StateComm.STATE_0);
		tbv.setCurrentTowerShareSumPrice(defaultValue);
		tbv.setCurrentTowerShareSumPriceDeductible(defaultValue);
		tbv.setCurrentTowerShareSumPriceWaitReturn(defaultValue);
		tbv.setDataType(SelfelecComm.NUMBER_STR_2);
		tbv.setDisputeFee(defaultValue);
		tbv.setElecFee(defaultValue);
		tbv.setElecFeeDeductible(defaultValue);
		tbv.setElecFeeWaitReturn(defaultValue);
		tbv.setHeight1(null);
		tbv.setHeight2(null);
		tbv.setHeight3(null);
		tbv.setIfbbuOnRoom1(SelfelecComm.STATUS_0);
		tbv.setIfbbuOnRoom2(SelfelecComm.STATUS_0);
		tbv.setIfbbuOnRoom3(SelfelecComm.STATUS_0);
		tbv.setIfServicefeeChange(null);
		tbv.setLocaOperator(SelfelecComm.STATUS_0);
		tbv.setManagerFee1(defaultValue);
		tbv.setOilGenerateElectricMethodId(historyVO.getOilGenerateElectricMethodId());
		tbv.setOilGeneratorElectricFee(defaultValue);
		tbv.setOilGeneratorElectricFee2(defaultValue);
		tbv.setOilGeneratorElectricFeeDeductible(defaultValue);
		tbv.setOilGeneratorElectricFeeDeductible2(defaultValue);
		tbv.setOilGeneratorElectricFeeWaitReturn(defaultValue);
		tbv.setOilGeneratorElectricFeeWaitReturn2(defaultValue);
		tbv.setOrderProp(null);
		tbv.setOriRight(null);
		tbv.setOtherDis1(null);
		tbv.setOtherDis2(null);
		tbv.setOtherDis3(null);
		tbv.setOtherFee1(defaultValue);
		tbv.setOtherFee1Deductible(defaultValue);
		tbv.setOtherFee1WaitReturn(defaultValue);
		tbv.setPowerInFee(defaultValue);
		tbv.setPowerInFeeOperatorStartdate1(null);
		tbv.setPowerInFeeOperatorStartdate2(null);
		tbv.setPowerInFeeShareDis1(null);
		tbv.setPowerInFeeShareDis2(null);
		tbv.setPowerInFeeShareNum(SelfelecComm.STATUS_0);
		tbv.setReimbursementState(SelfelecComm.NUMBER_0);
		tbv.setResCompare(null);
		tbv.setResourcesTypeId(historyVO.getResourcesTypeId());
		tbv.setRightProp(null);
		tbv.setRoomBasePrice1(defaultValue);
		tbv.setRoomBasePrice2(defaultValue);
		tbv.setRoomBasePrice3(defaultValue);
		tbv.setRoomOperatorStartdate1(null);
		tbv.setRoomOperatorStartdate2(null);
		tbv.setRoomSupportingShareDis1(null);
		tbv.setRoomSupportingShareDis2(null);
		tbv.setRoomTypeId(historyVO.getRoomTypeId());
		tbv.setServiceAttribute(null);
		tbv.setServicefeeChange(null);
		tbv.setStartDate(null);
		tbv.setSupportingBasePrice1(defaultValue);
		tbv.setSupportingBasePrice2(defaultValue);
		tbv.setSupportingBasePrice3(defaultValue);
		tbv.setSupportingOperatorStartdate1(null);
		tbv.setSupportingOperatorStartdate2(null);
		tbv.setSupportingShareDis1(null);
		tbv.setSupportingShareDis2(null);
		tbv.setTotalActualAmount(defaultValue);
		tbv.setTotalAmountMonth(defaultValue);
		tbv.setTotalAmountMonthDeductible(defaultValue);
		tbv.setTotalAmountMonthWaitReturn(defaultValue);
		tbv.setTowerOperatorStartdate1(null);
		tbv.setTowerOperatorStartdate2(null);
		tbv.setTowerPrice1(historyVO.getComputeTowerPrice1().toString());
		tbv.setTowerPrice2(historyVO.getComputeTowerPrice2().toString());
		tbv.setTowerPrice3(historyVO.getComputeTowerPrice3());
		tbv.setTowerSupportingShareDis1(historyVO.getTowerShareDis().toString());
		tbv.setTowerSupportingShareDis2(historyVO.getTowerShareDis().toString());
		tbv.setUnitProductNumber1(historyVO.getProduct1UnitNum());
		tbv.setUnitProductNumber2(historyVO.getProduct2UnitNum()); 
		tbv.setUnitProductNumber3(historyVO.getProduct3UnitNum());
		tbv.setUpdateTime(new Date());
		tbv.setUpdateUserId(historyVO.getUpdateUserId());
		tbv.setWlanFee(defaultValue);
		tbv.setWlanFeeDeductible(defaultValue);
		tbv.setWlanFeeWaitReturn(defaultValue);
		
		tbv.setTowerPrice1(historyVO.getTowerPrice().toString());
		tbv.setTowerStationCode(historyVO.getTowerStationCode());
		tbv.setTowerStationName(historyVO.getTowerStationName());
		tbv.setTowerStationAreaName(historyVO.getStationRegId());
		tbv.setAgreeBillCode(historyVO.getAgreeBillCode());
		tbv.setAgreeAreaName(historyVO.getDemandRegId());
		tbv.setOperator(historyVO.getOperator());
		tbv.setOperatorRegId(historyVO.getOperatorRegId());
		tbv.setProductTypeId(historyVO.getProductTypeId());
		return tbv;
	}
}