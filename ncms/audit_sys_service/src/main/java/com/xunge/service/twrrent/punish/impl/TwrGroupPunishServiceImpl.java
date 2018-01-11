package com.xunge.service.twrrent.punish.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.google.common.collect.Maps;
import com.xunge.comm.PunishComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.towerrent.mobile.ITwrRentInformationDao;
import com.xunge.dao.twrrent.punish.ITwrGroupPunishDao;
import com.xunge.dao.twrrent.punish.ITwrGroupRegPunishDao;
import com.xunge.dao.twrrent.settlement.ITowerBillbalanceDao;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;
import com.xunge.model.towerrent.punish.TwrGroupRegPunishVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.twrrent.punish.ITwrGroupPunishService;

/**
 * 集团既定考核指标扣罚
 * @author changwq
 * 
 */
public class TwrGroupPunishServiceImpl implements ITwrGroupPunishService {
	
	private ITwrGroupPunishDao twrGroupPunishDao;
	
	private ISysRegionDao sysRegionDao;
	
	private ITowerBillbalanceDao towerBillbalanceDao;
	
	private ITwrRentInformationDao twrRentInformationDao;
	
	private ITwrGroupRegPunishDao twrGroupRegPunishDao;

	@Override
	public Page<List<TwrGroupPunishVO>> queryGroupPunishVO(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		return twrGroupPunishDao.queryGroupPunishVO(paraMap, pageNumber, pageSize);
	}

	@Override
	public String deleteGroupPunish(String twrGroupPunishId) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("twrGroupPunishId",twrGroupPunishId);
		paraMap.put("state",PunishComm.STATE_1);
		try {
			twrGroupPunishDao.deleteGroupPunish(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	@Override
	public Map<String, Object> importGroupPunish(MultipartFile file,
			String suffix, HttpServletRequest request,
			Map<String, Object> paraMap) throws  Exception {
			//_TowerBillbalance这个是标识Session Name的，每个功能建议唯一
			SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,SelfelecComm.NUMBER_5, request);
			//导入参数
			ImportParams iparams=new ImportParams();
			List<TwrGroupPunishVO> list = new ArrayList<TwrGroupPunishVO>();
			List<TwrGroupPunishVO> listExist = new ArrayList<TwrGroupPunishVO>();
			List<TwrGroupPunishVO> listInsert = new ArrayList<TwrGroupPunishVO>();
			List<TwrGroupPunishVO> listUpdate = new ArrayList<TwrGroupPunishVO>();
			list = ExcelImportUtil.importExcel(file.getInputStream(),TwrGroupPunishVO.class, iparams);
			/**
			 * 查询本年本月是否有这条扣罚信息
			 */
			if(list != null && list.size() > 0){
				for (int index=0;index<list.size();index++) {
					TwrGroupPunishVO twrGroupPunishVO = list.get(index);
					//铁塔站址编码
					paraMap.put("towerStationCode", twrGroupPunishVO.getTowerstationcode());
					//年月
					paraMap.put("accountPeroid",twrGroupPunishVO.getPunishYearMonth());
					//查询铁塔侧账单数据（datetype = 1）
					paraMap.put("dataType",1);
					//根据年月和站址编码查是否有账单数据
					String str = towerBillbalanceDao.queryBalance(paraMap);
					if(str != null && str != ""){
						TwrGroupPunishVO twrGP = twrGroupPunishDao.queryIfGroupPunish(paraMap);
						if(twrGP != null){
							//判断是否已经计算
							if(twrGP.getTwrGroupRegPunishId() != null && twrGP.getTwrGroupRegPunishId() != ""){
								//不保存的数据
								listExist.add(twrGroupPunishVO);
							}else{
								twrGroupPunishVO.setTwrGroupPunishId(twrGP.getTwrGroupPunishId());
								twrGroupPunishVO.setTowerbillbalanceId(str);
								twrGroupPunishVO.setSaveUserId((String)paraMap.get("userId"));
								twrGroupPunishVO.setSaveTime(new Date());
								//保存修改的数据
								listUpdate.add(twrGroupPunishVO);
							}
						}else{
							List<TwrRentInformationVO> twrRentInformationList = twrRentInformationDao.queryMsgByTwrStaCode(paraMap);
							for(int i = 0;i < twrRentInformationList.size();i++){
								//判断维护等级，如果有高维护等级，则就是高维护等级
								if(twrRentInformationList.get(i).getMaintenanceLevelId().equals(PunishComm.STR_LEVEL_1)){
									twrGroupPunishVO.setMaintenancelevelid(PunishComm.LEVEL_1);
									break;
								}else{
									twrGroupPunishVO.setMaintenancelevelid(PunishComm.LEVEL_0);
								}
							}
							//创建扣罚信息id
							twrGroupPunishVO.setTwrGroupPunishId(SysUUID.generator());
							//根据站址编码找到的站点只会有一个regid
							twrGroupPunishVO.setRegId(twrRentInformationList.get(StateComm.STATE_0).getRegId());
							//设置铁塔侧账单id
							twrGroupPunishVO.setTowerbillbalanceId(str);
							//设置创建用户id
							twrGroupPunishVO.setUpdateUserId((String)paraMap.get("userId"));
							//设置创建时间
							twrGroupPunishVO.setUpdateTime(new Date());
							//保存的数据
							listInsert.add(twrGroupPunishVO);
						}
					}else{
						//不保存的数据
						listExist.add(twrGroupPunishVO);
					}
				}
			}
			SessionUtils.setProcessSession(suffix,DateDisposeComm.ANALAYSIS_FILE_DONE,SelfelecComm.NUMBER_10, request);
			//大批量数据分批插入，每次插入500条
			int a = SelfelecComm.NUMBER_500;//每次提交2000条
			int loop = (int) Math.ceil(listInsert.size() / (double) a);//批次
			int count=listInsert.size();
			int percent = (int) Math.ceil(90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
			int sumPercent=SelfelecComm.NUMBER_20;//方便每次计算
			for (int i = 0; i < loop; i++) {
				 int stop = Math.min(a - 1, listInsert.size());//判断结束的序号
				 twrGroupPunishDao.insert(listInsert.subList(0, stop));
				 listInsert.subList(0, stop).clear();//清除已经插入的数据
				 sumPercent+=percent;
				 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
				 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
			}
			
			int b = 500;//每次提交2000条
			int loopb = (int) Math.ceil(listUpdate.size() / (double) b);//批次
			int countb=listUpdate.size();
			int percentb = (int) Math.ceil(90 / (double) loopb);//计算每批占的百分比保存到数据库占80%的进度
			int sumPercentb=SelfelecComm.NUMBER_20;//方便每次计算
			for (int i = 0; i < loop; i++) {
				 int stop = Math.min(b - 1, listUpdate.size());//判断结束的序号
				 twrGroupPunishDao.update(listUpdate.subList(0, stop));
				 listUpdate.subList(StateComm.STATE_0, stop).clear();//清除已经插入的数据
				 sumPercentb+=percentb;
				 sumPercentb=(sumPercentb>100?100:sumPercentb);//如果大于100，按100计算
				 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_UPDATE_DATA,sumPercentb,request);
			}
			
			SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.UPDATE_INFO+countb+PromptMessageComm.DATAS,100,request);
			Map<String,Object> returnMap=Maps.newHashMap();
			returnMap.put("errMsg", listExist.size()>0?PromptMessageComm.NO_BILL_DATAS+listExist.size()+PromptMessageComm.STRIP:"");
			returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.UPDATE_INFO+countb+PromptMessageComm.DATAS);
			returnMap.put("isExist", listExist);
			returnMap.put("successCount", count+countb);
			return returnMap;
	}
	
	@Override
	public List<TwrGroupPunishVO> queryGroupPunishByPregId(String pregId,String punishYearMonth,String regId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("pregId",pregId);
		paraMap.put("state",PunishComm.STATE_0);
		paraMap.put("punishYearMonth",punishYearMonth);
		paraMap.put("regId",regId);
		return twrGroupPunishDao.queryGroupPunishByPregId(paraMap);
	}

	@Override
	public List<TwrGroupPunishVO> queryGroupPunish(Map<String, Object> paraMap) {
		return twrGroupPunishDao.queryGroupPunish(paraMap);
	}

	@Override
	public String updatePunishAmountById(String twrGroupPunishId,
			BigDecimal punishamount,String userId,String twrGroupRegPunishId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("twrGroupRegPunishId", twrGroupRegPunishId);
		paraMap.put("twrGroupPunishId", twrGroupPunishId);
		paraMap.put("punishamount",punishamount);
		paraMap.put("updateUserId",userId);
		paraMap.put("updateTime",new Date());
		try {
			twrGroupPunishDao.updatePunishAmountById(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}
	
	public ITwrGroupPunishDao getTwrGroupPunishDao() {
		return twrGroupPunishDao;
	}

	public void setTwrGroupPunishDao(ITwrGroupPunishDao twrGroupPunishDao) {
		this.twrGroupPunishDao = twrGroupPunishDao;
	}

	public ITowerBillbalanceDao getTowerBillbalanceDao() {
		return towerBillbalanceDao;
	}

	public void setTowerBillbalanceDao(ITowerBillbalanceDao towerBillbalanceDao) {
		this.towerBillbalanceDao = towerBillbalanceDao;
	}

	public ITwrRentInformationDao getTwrRentInformationDao() {
		return twrRentInformationDao;
	}

	public void setTwrRentInformationDao(
			ITwrRentInformationDao twrRentInformationDao) {
		this.twrRentInformationDao = twrRentInformationDao;
	}

	public ITwrGroupRegPunishDao getTwrGroupRegPunishDao() {
		return twrGroupRegPunishDao;
	}

	public void setTwrGroupRegPunishDao(ITwrGroupRegPunishDao twrGroupRegPunishDao) {
		this.twrGroupRegPunishDao = twrGroupRegPunishDao;
	} 

	@Override
	public String calSumPunishAmount(String pregId,String punishYearMonth,String userId) throws ParseException {
		//根据地市id和年月查找集团扣罚汇总信息
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("regId",pregId);
		map.put("state",PunishComm.STATE_0);
		map.put("punishYearMonth",punishYearMonth);
		TwrGroupRegPunishVO twrGroupRegPunishVO = twrGroupRegPunishDao.queryGroupRegPunish(map);
		if(twrGroupRegPunishVO != null){
			if(twrGroupRegPunishVO.getAccountsummaryId() != null && twrGroupRegPunishVO.getAccountsummaryId() != ""){
				return PromptMessageComm.FINE_YES_COUNT;
			}else{
				//计算地市罚金合计
				BigDecimal sumPunishAmount = getSumPunishAmount(pregId,punishYearMonth,userId,twrGroupRegPunishVO.getTwrGroupRegPunishId());
				//设置修改修改用户编码和修改时间
				twrGroupRegPunishVO.setUpdateUserId(userId);
				twrGroupRegPunishVO.setUpdateTime(new Date());
				//重新设置扣罚总金额
				twrGroupRegPunishVO.setPunishamount(sumPunishAmount);
				//修改已有的扣罚汇总信息
				Integer success = twrGroupRegPunishDao.updateByPrimaryKeySelective(twrGroupRegPunishVO);
				return PromptMessageComm.FINE_YES_COUNT_NO_SUMMARY_YES_UPDATE;
			}
		}else{
			//创建一个新的扣罚汇总信息对象
			TwrGroupRegPunishVO twrGroupRegPunish = new TwrGroupRegPunishVO();
			//设置新的编码
			twrGroupRegPunish.setTwrGroupRegPunishId(SysUUID.generator());
			//新增一个扣罚汇总信息
			Integer success = twrGroupRegPunishDao.insertSelective(twrGroupRegPunish);
			//计算地市罚金合计
			BigDecimal sumPunishAmount = getSumPunishAmount(pregId,punishYearMonth,userId,twrGroupRegPunish.getTwrGroupRegPunishId());
			//设置年月
			twrGroupRegPunish.setPunishYearMonth(punishYearMonth);
			//设置地市id
			twrGroupRegPunish.setRegId(pregId);
			//设置扣罚金额和
			twrGroupRegPunish.setPunishamount(sumPunishAmount);
			//设置扣罚汇总信息状态 0 正常 1 删除
			twrGroupRegPunish.setState(PunishComm.STATE_0);
			//设置创建用户编码和时间
			twrGroupRegPunish.setCreateUserId(userId);
			twrGroupRegPunish.setCreateTime(new Date());
			//修改已有的扣罚汇总信息
			success = twrGroupRegPunishDao.updateByPrimaryKeySelective(twrGroupRegPunish);
		}
		return PromptMessageComm.FINE_YES_COUNT;
	}
	private BigDecimal getSumPunishAmount(String pregId,String punishYearMonth,String userId,String twrGroupRegPunishId) throws ParseException{
		//创建普通维护等级的扣罚信息集合
		List<TwrGroupPunishVO> normal = new ArrayList<TwrGroupPunishVO>();
		//创建高维护等级的扣罚信息集合
		List<TwrGroupPunishVO> high = new ArrayList<TwrGroupPunishVO>();
		//根据省份id查询扣罚信息
		List<TwrGroupPunishVO> twrGroupPunishList = queryGroupPunishByPregId(pregId,punishYearMonth,null);
		for(int i=0;i <twrGroupPunishList.size();i++){
			//循环一次，创建一个对象
			TwrGroupPunishVO twrGroupPunish = twrGroupPunishList.get(i);
			//判断维护等级
			if(twrGroupPunish.getMaintenancelevelid() == PunishComm.LEVEL_1){
				high.add(twrGroupPunish);
			}else{
				normal.add(twrGroupPunish);
			}
		}
		//声明该地市在当前年月扣罚金额总和
		BigDecimal sumPunishAmount = new BigDecimal(StateComm.STATE_0);
		//计算普通维护等级平均停电退服时长
			BigDecimal normalTime = new BigDecimal(StateComm.STATE_0);
			for(int i=0;i <normal.size();i++){
				normalTime = normal.get(i).getTakebacktime().add(normalTime);
			}
			if(normal.size() > 0){
				BigDecimal avgNormalTime = normalTime.divide(new BigDecimal(normal.size()));
				//如果平均停电退服时长大于标准参数 
				if(avgNormalTime.compareTo(PunishComm.NORMAL_TIME) == StateComm.STATE_0){
					for(int i=0;i <normal.size();i++){
						BigDecimal punishAmount = calPunishAmount(normal.get(i),PunishComm.NORMAL_TIME,userId,twrGroupRegPunishId);
						sumPunishAmount = sumPunishAmount.add(punishAmount);
					}
				}else{//平均值未超标72h
					BigDecimal time = new BigDecimal(SelfelecComm.NUMBER_72).multiply(new BigDecimal(SelfelecComm.NUMBER_60));
					for(int i=0;i <normal.size();i++){
						BigDecimal punishAmount = calPunishAmount(normal.get(i),time,userId,twrGroupRegPunishId);
						sumPunishAmount = sumPunishAmount.add(punishAmount);
					}
				}
			}
		//计算高维护等级平均停电退服时长
			BigDecimal highTime = new BigDecimal(StateComm.STATE_0);
			for(int i=0;i <high.size();i++){
				highTime = high.get(i).getTakebacktime().add(highTime);
			}
			if(high.size() > 0){
				BigDecimal avgHighTime = highTime.divide(new BigDecimal(high.size()));
				//如果平均停电退服时长大于标准值
				if(avgHighTime.compareTo(PunishComm.HIGH_TIME) == StateComm.STATE_1){
					for(int i=0;i <high.size();i++){
						BigDecimal punishAmount = calPunishAmount(high.get(i),PunishComm.HIGH_TIME,userId,twrGroupRegPunishId);
						sumPunishAmount = sumPunishAmount.add(punishAmount);
					}
				}else{//平均值未超标
					BigDecimal time = new BigDecimal(StateComm.STATE_0);
					for(int i=0;i <high.size();i++){
						//覆盖场景为非高铁24h
						if(high.get(i).getCoversceneid() == PunishComm.COVER_0){
							time = new BigDecimal(SelfelecComm.NUMBER_24).multiply(new BigDecimal(SelfelecComm.NUMBER_60));
						}else//覆盖场景为高铁12h
						if(high.get(i).getCoversceneid() == PunishComm.COVER_1){
							time = new BigDecimal(SelfelecComm.NUMBER_12).multiply(new BigDecimal(SelfelecComm.NUMBER_60));
						}
						BigDecimal punishAmount = calPunishAmount(high.get(i),time,userId,twrGroupRegPunishId);
						sumPunishAmount = sumPunishAmount.add(punishAmount);
					}
				}
			}
		return sumPunishAmount;
	}
	/**
	 * 计算站点罚金
	 * @param twrGroupPunishVO
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	private BigDecimal calPunishAmount(TwrGroupPunishVO twrGroupPunishVO,BigDecimal time,String userId,String twrGroupRegPunishId) throws ParseException{
		BigDecimal punishamount = new BigDecimal(StateComm.STATE_0);
		//找到超标的站点
		if(twrGroupPunishVO.getTakebacktime().compareTo(time) == StateComm.STATE_1){
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("towerbillbalanceId",twrGroupPunishVO.getTowerbillbalanceId());
			List<TowerBillbalanceVO> billList = towerBillbalanceDao.queryParameter(paraMap);
			//期末铁塔共享后基准价格1+2+3
			BigDecimal tower = new BigDecimal(StateComm.STATE_0);
			//期末机房共享后基准价格1+2+3
			BigDecimal room = new BigDecimal(StateComm.STATE_0);
			//配套共享后基准价格1+2+3
			BigDecimal supporting = new BigDecimal(StateComm.STATE_0);
			//场地费折扣后金额
			BigDecimal prace = new BigDecimal(StateComm.STATE_0);
			//电力引入费折扣后金额
			BigDecimal power = new BigDecimal(StateComm.STATE_0);
			for(int j=0;j<billList.size();j++){
				tower = tower.add(billList.get(j).getCurrentTowerShareSumPriceOut());
				room = room.add(billList.get(j).getCurrentRoomShareSumPriceOut());
				supporting = supporting.add(billList.get(j).getCurrentSupportingShareSumPriceOut());
				prace = prace.add(billList.get(j).getCurrentPraceFeeShareSumPriceOut());
				power = power.add(billList.get(j).getCurrentPowerInFeeShareSumPriceOut());
			}
			//计算月基准价格
			//月基准价格=（期末铁塔共享后基准价格1+2+3）+（期末机房共享后基准价格1+2+3）+（配套共享后基准价格1+2+3）+（场地费折扣后金额）+（电力引入费折扣后金额）
			BigDecimal baseAmount = tower.add(room).add(supporting).add(prace).add(power);
			//单站址罚金=（（单站址断电退服月总时长-达标值）/（当月天数×24×60））×月基准价格×2
			SimpleDateFormat sdf =  new  SimpleDateFormat(SelfelecComm.FORMAT_yyyy_MM);  
		    Date date = sdf.parse(twrGroupPunishVO.getPunishYearMonth());
		    int day = getDaysOfMonth(date); 
		    //（单站址断电退服月总时长-达标值）
		    BigDecimal a = twrGroupPunishVO.getTakebacktime().subtract(PunishComm.NORMAL_TIME);
		    //（当月天数×24×60）
		    BigDecimal d = new BigDecimal(day).multiply(new BigDecimal(SelfelecComm.NUMBER_24)).multiply(new BigDecimal(60));
		    //月基准价格×2
		    BigDecimal m = baseAmount.multiply(new BigDecimal(SelfelecComm.NUMBER_2));
		    punishamount = (a.divide(d, SelfelecComm.NUMBER_8, BigDecimal.ROUND_UP)).multiply(m);
		    //计算完毕给数据库该对象添加罚金字段
		    String success = updatePunishAmountById(twrGroupPunishVO.getTwrGroupPunishId(), punishamount,userId,twrGroupRegPunishId);
		}
		//返回当前站点扣罚金额
	    return punishamount;
	}
	/**
	 * 根据date类型获取当月天数
	 * @param date
	 * @return
	 */
	private static int getDaysOfMonth(Date date){    
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        return  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    }

}
