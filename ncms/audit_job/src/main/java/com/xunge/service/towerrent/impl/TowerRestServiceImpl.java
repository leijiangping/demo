package com.xunge.service.towerrent.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.xunge.comm.BatchControlComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.tower.CommonData;
import com.xunge.comm.tower.ValidataUtil;
import com.xunge.dao.towerrent.TowerBillbalanceMapper;
import com.xunge.dao.towerrent.TowerLinkMapper;
import com.xunge.dao.towerrent.TowerRentInformationTowerMapper;
import com.xunge.dao.towerrent.TowerRentinformationBizchangeMapper;
import com.xunge.dao.towerrent.TowerStopServerMapper;
import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.towerrent.TowerBillbalanceVO;
import com.xunge.model.towerrent.TowerConfirmBillbalanceVO;
import com.xunge.model.towerrent.TowerRentInformationBean;
import com.xunge.model.towerrent.TowerRentInformationTowerVO;
import com.xunge.model.towerrent.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.TowerStopServerVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.job.ITaskHistoryInfoService;
import com.xunge.service.job.util.irontower.IronTowerFileType;
import com.xunge.service.towerrent.ITowerRestService;
import com.xunge.service.towerrent.excelhandler.TowerBillBalanceHandler;
import com.xunge.service.towerrent.excelhandler.TowerConfirmBillBalanceHandler;
import com.xunge.service.towerrent.excelhandler.TowerRentBizChangeHandler;
import com.xunge.service.towerrent.excelhandler.TowerRentInfoHandler;
import com.xunge.service.towerrent.excelhandler.TowerRentStopServerHandler;
import com.xunge.util.ActUtils;
import com.xunge.util.CollectionUtil;
import com.xunge.util.DateUtil;

@Service
public class TowerRestServiceImpl implements ITowerRestService {

	private static final Logger LOGGER = Logger.getLogger(TowerRestServiceImpl.class);
	
	@Resource
	TowerBillbalanceMapper billbalanceMapper;
	
	@Resource
	TowerRentinformationBizchangeMapper bizchangeMapper;
	
	@Resource
	TowerRentInformationTowerMapper informationTowerMapper;
	
	@Resource
	TowerStopServerMapper stopServerMapper;
	
	@Resource
	TowerLinkMapper towerLinkMapper;
	
	@Resource
	private ITaskHistoryInfoService taskHistoryInfoService;
	
	@Resource 
	IActTaskService actTaskService;
	
	@Override
	public void processData(TaskInfoVO taskInfo,File file,String fileType,List<SysRegionVO> listreg, Map<Integer, Object> dataMap,Map<Integer, String> dicNameMap,Map<Integer, String> dicValueMap, String userName){
		if (IronTowerFileType.PLQZ.toString().equalsIgnoreCase(fileType)) { // 批量起租单
			ImportParams iparams = new ImportParams();
			TowerRentInfoHandler tbh = new TowerRentInfoHandler(listreg);// 自定义数据处理
			tbh.setNeedHandlerFields(new String[] { "区县" });// 需要处理数据的列名
			iparams.setDataHanlder(tbh);
			// 获取excel数据
			List<TowerRentInformationTowerVO> list = ExcelImportUtil.importExcel(file, TowerRentInformationTowerVO.class, iparams);
			if (list.isEmpty()) {
				String historyId = file.getName().substring(0 ,file.getName().indexOf(".")) + "_"+ DateUtil.format(new Date(), "yyyyMMddHHmmss");
				taskHistoryInfoService.recordLog(taskInfo, file.getName()+":文件无内容!", historyId);
			}
			insertInformationTower(taskInfo, list, dataMap, dicNameMap, dicValueMap, userName);
		} else if (IronTowerFileType.YWBG.toString().equalsIgnoreCase(fileType)){// 业务变更
			ImportParams iparams = new ImportParams();
			iparams.setTitleRows(1);
			TowerRentBizChangeHandler tbh = new TowerRentBizChangeHandler(listreg);//自定义数据处理
			tbh.setNeedHandlerFields(new String[]{"区县"});//需要处理数据的列名
			iparams.setDataHanlder(tbh);
			List<TowerRentinformationBizchangeVO> list = ExcelImportUtil.importExcel(file, TowerRentinformationBizchangeVO.class, iparams);
			if (list.isEmpty()) {
				String historyId = file.getName().substring(0 ,file.getName().indexOf(".")) + "_"+ DateUtil.format(new Date(), "yyyyMMddHHmmss");
				taskHistoryInfoService.recordLog(taskInfo, file.getName()+":文件无内容!", historyId);
			}
			insertBizchange(taskInfo, list, dataMap, dicValueMap, userName);
		} else if (IronTowerFileType.FWZZ.toString().equalsIgnoreCase(fileType)){// 服务终止
			ImportParams iparams = new ImportParams();
			iparams.setHeadRows(2);
			TowerRentStopServerHandler tbh = new TowerRentStopServerHandler(listreg);//自定义数据处理
			tbh.setNeedHandlerFields(new String[]{"区县"});//需要处理数据的列名
			iparams.setDataHanlder(tbh);
			List<TowerStopServerVO> list = ExcelImportUtil.importExcel(file, TowerStopServerVO.class, iparams);
			if (list.isEmpty() && null != taskInfo) {
				String historyId = file.getName().substring(0 ,file.getName().indexOf(".")) + "_"+ DateUtil.format(new Date(), "yyyyMMddHHmmss");
				taskHistoryInfoService.recordLog(taskInfo, file.getName()+":文件无内容!", historyId);
			}
			insertStopServer(taskInfo, list, dataMap, userName);
		} else if (IronTowerFileType.DZQR.toString().equalsIgnoreCase(fileType)){// 对账确认单
			// 暂时不做
		} else if (IronTowerFileType.JSXD.toString().equalsIgnoreCase(fileType)){ // 结算详单
			ImportParams iparams = new ImportParams();
			iparams.setLastOfInvalidRow(1);//跳过最后一行
			TowerBillBalanceHandler tbh = new TowerBillBalanceHandler(listreg);//自定义数据处理
			tbh.setNeedHandlerFields(new String[]{"运营商区县"});//需要处理数据的列名
			iparams.setDataHanlder(tbh);
			List<TowerBillbalanceVO> list = ExcelImportUtil.importExcel(file, TowerBillbalanceVO.class, iparams);
			if (list.isEmpty()) {
				String historyId = file.getName().substring(0 ,file.getName().indexOf(".")) + "_"+ DateUtil.format(new Date(), "yyyyMMddHHmmss");
				taskHistoryInfoService.recordLog(taskInfo, file.getName()+":文件无内容!", historyId);
			}
			insertBillbalance(taskInfo, list, dataMap, dicNameMap);
		} else if(IronTowerFileType.QRFY.toString().equalsIgnoreCase(fileType)){ // 确认费用清单
			ImportParams iparams = new ImportParams();
			iparams.setTitleRows(1);
			iparams.setLastOfInvalidRow(1);//跳过最后一行
			TowerConfirmBillBalanceHandler tbh = new TowerConfirmBillBalanceHandler(listreg);//自定义数据处理
			tbh.setNeedHandlerFields(new String[]{"区县"});//需要处理数据的列名
			iparams.setDataHanlder(tbh);
			List<TowerConfirmBillbalanceVO> list = ExcelImportUtil.importExcel(file, TowerConfirmBillbalanceVO.class, iparams);
			if (list.isEmpty()) {
				String historyId = file.getName().substring(0 ,file.getName().indexOf(".")) + "_"+ DateUtil.format(new Date(), "yyyyMMddHHmmss");
				taskHistoryInfoService.recordLog(taskInfo, file.getName()+":文件无内容!", historyId);
			}
			insertConfirmBillbalance(taskInfo, list, dataMap,dicNameMap);
		} else if (IronTowerFileType.DFQD.toString().equalsIgnoreCase(fileType)){ // 电费清单
			// 暂时不做
		}
	}
	
	/**
	 * 结算详单 twr_towerbillbalance
	 * @param list 解析的数据
	 * @param dataMap 已存在的数据
	 * @param dicNameMap 数据字典    key:(枚举值标题code+#+枚举值描述) 哈希码  value:枚举值数值
	 * @return
	 */
	public boolean insertBillbalance(TaskInfoVO taskInfo, List<TowerBillbalanceVO> list, Map<Integer, Object> dataMap, Map<Integer, String> dicNameMap) {
		List<TowerBillbalanceVO> insertList = new ArrayList<TowerBillbalanceVO>();
		List<TowerBillbalanceVO> updateList = new ArrayList<TowerBillbalanceVO>();
		List<TowerBillbalanceVO> errorList = new ArrayList<TowerBillbalanceVO>();
		List<String> errorInfo = new ArrayList<String>();
		
		for (TowerBillbalanceVO vo : list) {
			String validateResult = ValidataUtil.validateTowerBalance(vo);
			if (StringUtils.isBlank(validateResult)) {
				vo.setDataType(CommonData.TOWER_BILL);// 铁塔账单
				
				// 业务属性
				vo.setServiceAttribute(dicNameMap.get((CommonData.SERVICE_ATTRIBUTE + "#" + vo.getServiceAttribute()).hashCode()));
				// 产品类型
				vo.setProductTypeId(dicNameMap.get((CommonData.TOWER_PRODUCTS + "#" + vo.getProductTypeId()).hashCode()));
				// 机房产品
				vo.setRoomTypeId(dicNameMap.get((CommonData.COMPUTER_PRODUCTS + "#" + vo.getRoomTypeId()).hashCode()));
				// 油机发电模式
				vo.setOilGenerateElectricMethodId(dicNameMap.get((CommonData.OIL_MACHINE_MODE + "#" + vo.getOilGenerateElectricMethodId()).hashCode()));
				// 订单属性
				vo.setOrderProp(dicNameMap.get((CommonData.ORDER_ATTRIBUTE + "#" + vo.getOrderProp()).hashCode()));
				// 产权属性
				vo.setRightProp(dicNameMap.get((CommonData.PROPERTY_RIGHTS + "#" + vo.getRightProp()).hashCode()));
				// 原产权方 
				vo.setOriRight(dicNameMap.get((CommonData.CARRIER_CATEGORY + "#" + vo.getOriRight()).hashCode()));
				// 确认状态
				int statekey = (CommonData.CONFIRM_STATE + "#" + vo.getConfirmStateStr()).hashCode();
				vo.setConfirmState(dicNameMap.get(statekey) != null ? Integer.parseInt(dicNameMap.get(statekey)) : null);
				
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				if (dataMap.containsKey(key)) {
					vo.setUpdateTime(new Date());
					updateList.add(vo);
				} else {
					insertList.add(vo);
				}
			} else {
				errorList.add(vo);
				errorInfo.add(validateResult);
			}
		}
		LOGGER.info("错误信息:\n"+CollectionUtil.join(errorInfo, "\n"));
		String message = "总共导入" + list.size() + "条数据;新增数据" + insertList.size() 
				+ "条;修改数据:" + updateList.size() + "条;错误数据:" + errorList.size() + "条";
		LOGGER.info(message);
		String historyId = IronTowerFileType.JSXD + "_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");
		taskHistoryInfoService.recordLog(taskInfo, message, historyId);
		
		boolean flag = false;
		if (!insertList.isEmpty()) {
			for(int i=0, length = insertList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i+BatchControlComm.iBatchCount> insertList.size())?insertList.size():i+BatchControlComm.iBatchCount;
				List<TowerBillbalanceVO> tmp = insertList.subList(i, j);
				flag &= billbalanceMapper.batchInsert(tmp) > 0;
			}
		}
		if (!updateList.isEmpty()) {
			for(int i=0, length = updateList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i + BatchControlComm.iBatchCount > updateList.size()) ? updateList.size() : i + BatchControlComm.iBatchCount;
				List<TowerBillbalanceVO> tmp = updateList.subList(i, j);
				flag &= billbalanceMapper.batchUpdate(tmp) > 0;
			}
		}
		LOGGER.info("新增租赁费账单数据成功："+insertList.size() + "条；更新租赁费账单数据成功："+updateList.size()+"条.");
		return flag;
	}

	/**
	 * 业务变更 twr_rentinformation_bizchange
	 * @param list 解析的数据
	 * @param dataMap 已存在的数据
	 * @param dicValueMap 数据字典 key:(枚举值标题code+#+枚举值数值) 哈希码   value:枚举值描述
	 * @return
	 */
	public boolean insertBizchange(TaskInfoVO taskInfo, List<TowerRentinformationBizchangeVO> list, Map<Integer, Object> dataMap, Map<Integer, String> dicValueMap, String userName) {
		List<TowerRentinformationBizchangeVO> insertList = new ArrayList<TowerRentinformationBizchangeVO>();
		List<TowerRentinformationBizchangeVO> updateList = new ArrayList<TowerRentinformationBizchangeVO>();
		List<TowerRentinformationBizchangeVO> errorList = new ArrayList<TowerRentinformationBizchangeVO>();
		List<String> errorInfo = new ArrayList<String>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		for (TowerRentinformationBizchangeVO vo : list) {
			//业务确认单号
			paraMap.put("businessConfirmNumber", vo.getBusinessConfirmNumber());
			//铁塔站址编码
			paraMap.put("towerStationCode", vo.getTowerStationCode());
			//生效日期
			paraMap.put("activeDate", vo.getChangeActiveDate());
			//移动侧是否有此条信息
			TowerRentInformationBean bean = informationTowerMapper.queryMobileTowerRentInformation(paraMap);
			
			String validateResult = ValidataUtil.validateTowerBizChange(vo, dicValueMap);
		
			if (null == bean) {
				validateResult = vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode() + "移动侧不存在该数据或者"+vo.getChangeActiveDate()+"不在服务期内";
			}
			if (StringUtils.isBlank(validateResult)) {
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode() + "#" + vo.getChangeItem()).hashCode();
				if (dataMap.containsKey(key)) {
					updateList.add(vo);
				} else {
					vo.setAuditState(StateComm.STATE_9);
					insertList.add(vo);
				}
			} else {
				errorList.add(vo);
				errorInfo.add(validateResult);
			}
		}
		LOGGER.info("错误信息:\n"+CollectionUtil.join(errorInfo, "\n"));
		String message = "总共导入" + list.size() + "条数据;新增数据" + insertList.size() 
				+ "条;修改数据:" + updateList.size() + "条;错误数据:" + errorList.size() + "条";
		LOGGER.info(message);
		String historyId = IronTowerFileType.YWBG + "_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");
		taskHistoryInfoService.recordLog(taskInfo, message, historyId);
		
		boolean flag = false;
		if (!insertList.isEmpty()) {
			for(int i=0, length = insertList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i+BatchControlComm.iBatchCount> insertList.size())?insertList.size():i+BatchControlComm.iBatchCount;
				List<TowerRentinformationBizchangeVO> tmp = insertList.subList(i, j);
				flag &= bizchangeMapper.batchInsert(tmp) > 0;
			}
			for(int i = 0 ; i < insertList.size() ; i ++){
				actTaskService.startProcess(ActUtils.PD_BIZCHANGE_INFO[0], ActUtils.PD_BIZCHANGE_INFO[1], insertList.get(i).getTwrRentinformationBizchangeId(),ActUtils.PD_BIZCHANGE_INFO[2],null,userName);
			}
		}
		if (!updateList.isEmpty()) {
			for(int i=0, length = updateList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i + BatchControlComm.iBatchCount > updateList.size()) ? updateList.size() : i + BatchControlComm.iBatchCount;
				List<TowerRentinformationBizchangeVO> tmp = updateList.subList(i, j);
				flag &= bizchangeMapper.batchUpdate(tmp) > 0;
			}
		}
		LOGGER.info("新增起租业务变更确认数据成功："+insertList.size() + "条；更新起租业务变更确认数据成功："+updateList.size()+"条.");
		return flag;
	}

	/**
	 * 起租表数据 twr_rentinformationtower
	 * @param list 解析的数据
	 * @param queryParam 已存在的数据
	 * @param dicNameMap 数据字典    key:(枚举值标题code+#+枚举值描述) 哈希码  value:枚举值数值
	 * @param dicValueMap 数据字典 key:(枚举值标题code+#+枚举值数值) 哈希码   value:枚举值描述
	 * @return
	 */
	public boolean insertInformationTower(TaskInfoVO taskInfo, List<TowerRentInformationTowerVO> list, Map<Integer, Object> dataMap, Map<Integer, String> dicNameMap, Map<Integer, String> dicValueMap, String userName) {
		List<String> allTowerLink = towerLinkMapper.queryAllTowerLink();
		List<TowerRentInformationTowerVO> insertList = new ArrayList<TowerRentInformationTowerVO>();
		List<TowerRentInformationTowerVO> updateList = new ArrayList<TowerRentInformationTowerVO>();
		List<TowerRentInformationTowerVO> errorList = new ArrayList<TowerRentInformationTowerVO>();
		List<String> errorInfo = new ArrayList<String>();

		for (TowerRentInformationTowerVO vo : list) {
			String validateResult = ValidataUtil.validateTowerRentinfo(vo, dicValueMap, allTowerLink);
			if (StringUtils.isBlank(validateResult)) {
				
				// 共享信息
				vo.setResourcesTypeId(dicNameMap.get((CommonData.SHARE_INFO + "#" + vo.getResourcesTypeId()).hashCode()));
				// 铁塔产品
				vo.setProductTypeId(dicNameMap.get((CommonData.TOWER_PRODUCTS + "#" + vo.getProductTypeId()).hashCode()));
				// 机房产品
				vo.setRoomTypeId(dicNameMap.get((CommonData.COMPUTER_PRODUCTS + "#" + vo.getRoomTypeId()).hashCode()));
				// 油机发电服务费模式
				vo.setOilGenerateElectricMethodId(dicNameMap.get((CommonData.OIL_GENERATOR + "#" + vo.getOilGenerateElectricMethodId()).hashCode()));
				// 维护等级
				vo.setMaintenanceLevelId(dicNameMap.get((CommonData.MAINTENANCE_ECHELON + "#" + vo.getMaintenanceLevelId()).hashCode()));
				// 电力保障服务费模式 
				vo.setElectricProtectionMethodId(dicNameMap.get((CommonData.POWER_PROTECTE_MODE + "#" + vo.getElectricProtectionMethodId()).hashCode()));
				// 场地费模式
				vo.setRoomFeeMethod(dicNameMap.get((CommonData.SITE_FEE_MODEL + "#" + vo.getRoomFeeMethod()).hashCode()));
				//电力引入费模式
				vo.setElecImportFeeMethod(dicNameMap.get((CommonData.POWER_INTRODUCE_FEED + "#" + vo.getElecImportFeeMethod()).hashCode()));
				
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				if (dataMap.containsKey(key)) {
					vo.setUpdateTime(new Date());
					updateList.add(vo);
				} else {
					vo.setAuditState(StateComm.STATE_9);
					insertList.add(vo);
				}
			} else {
				errorList.add(vo);
				errorInfo.add(validateResult);
			}
		}
		LOGGER.info("错误信息:\n"+CollectionUtil.join(errorInfo, "\n"));
		String message = "总共导入" + list.size() + "条数据;新增数据" + insertList.size() 
				+ "条;修改数据:" + updateList.size() + "条;错误数据:" + errorList.size() + "条";
		LOGGER.info(message);
		String historyId = IronTowerFileType.PLQZ + "_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");
		taskHistoryInfoService.recordLog(taskInfo, message, historyId);
		
		
		boolean flag = false;
		if (!insertList.isEmpty()) {
			for(int i=0, length = insertList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i+BatchControlComm.iBatchCount> insertList.size())?insertList.size():i+BatchControlComm.iBatchCount;
				List<TowerRentInformationTowerVO> tmp = insertList.subList(i, j);
				flag &= informationTowerMapper.batchInsert(tmp) > 0;
			}
			for(int i = 0 ; i < insertList.size() ; i ++){
				actTaskService.startProcess(ActUtils.PD_RESOURCE_INFO[0], ActUtils.PD_RESOURCE_INFO[1], insertList.get(i).getRentinformationtowerId(), ActUtils.PD_RESOURCE_INFO[2],null,userName);
			}
		}
		if (!updateList.isEmpty()) {
			for(int i=0, length = updateList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i + BatchControlComm.iBatchCount > updateList.size()) ? updateList.size() : i + BatchControlComm.iBatchCount;
				List<TowerRentInformationTowerVO> tmp = updateList.subList(i, j);
				flag &= informationTowerMapper.batchUpdate(tmp) > 0;
			}
		}
		LOGGER.info("新增铁塔起租数据成功："+insertList.size() + "条；更新铁塔起租数据成功："+updateList.size()+"条.");
		return flag;
	}

	/**
	 * 服务终止 twr_rentinformationtower_stopserver
	 * @param list 解析的数据
	 * @param dataMap 已存在的数据
	 * @return
	 */
	public boolean insertStopServer(TaskInfoVO taskInfo, List<TowerStopServerVO> list, Map<Integer, Object> dataMap, String userName) {
		List<TowerStopServerVO> insertList = new ArrayList<TowerStopServerVO>();
		List<TowerStopServerVO> updateList = new ArrayList<TowerStopServerVO>();
		List<TowerStopServerVO> errorList = new ArrayList<TowerStopServerVO>();
		List<String> errorInfo = new ArrayList<String>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		for (TowerStopServerVO vo : list) {
			//业务确认单号
			paraMap.put("businessConfirmNumber", vo.getBusinessConfirmNumber());
			//铁塔站址编码
			paraMap.put("towerStationCode", vo.getTowerStationCode());
			//生效日期
			paraMap.put("activeDate", vo.getStopUseDate());
			TowerRentInformationBean bean = informationTowerMapper.queryMobileTowerRentInformation(paraMap);
			
			String validateResult = ValidataUtil.validateTowerStopServer(vo);
			
			if (null == bean) {
				validateResult = vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode() + "移动侧不存在该数据或者"+vo.getStopUseDate()+"不在服务期内";
			}
			if (StringUtils.isBlank(validateResult)) {
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				if (dataMap.containsKey(key)) {
					updateList.add(vo);
				} else {
					vo.setAuditState(StateComm.STATE_9);
					insertList.add(vo);
				}
			} else {
				errorList.add(vo);
				errorInfo.add(validateResult);
			}
		}
		LOGGER.info("错误信息:\n"+CollectionUtil.join(errorInfo, "\n"));
		String message = "总共导入" + list.size() + "条数据;新增数据" + insertList.size() 
				+ "条;修改数据:" + updateList.size() + "条;错误数据:" + errorList.size() + "条";
		LOGGER.info(message);
		String historyId = IronTowerFileType.FWZZ + "_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");
		taskHistoryInfoService.recordLog(taskInfo, message, historyId);
		
		
		boolean flag = false;
		if (!insertList.isEmpty()) {
			for(int i=0, length = insertList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i+BatchControlComm.iBatchCount> insertList.size())?insertList.size():i+BatchControlComm.iBatchCount;
				List<TowerStopServerVO> tmp = insertList.subList(i, j);
				flag &= stopServerMapper.batchInsert(tmp) > 0;
			}
			
			for(int i = 0 ; i < insertList.size() ; i ++){
				actTaskService.startProcess(ActUtils.PD_STOPSERVER_INFO[0], ActUtils.PD_STOPSERVER_INFO[1], insertList.get(i).getStopServerId(),ActUtils.PD_STOPSERVER_INFO[2], null ,userName);
			}
		}
		if (!updateList.isEmpty()) {
			for(int i=0, length = updateList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i + BatchControlComm.iBatchCount > updateList.size()) ? updateList.size() : i + BatchControlComm.iBatchCount;
				List<TowerStopServerVO> tmp = updateList.subList(i, j);
				flag &= stopServerMapper.batchUpdate(tmp) > 0;
			}
		}
		LOGGER.info("新增铁塔终止服务数据成功："+insertList.size() + "条；更新铁塔终止服务数据成功："+updateList.size()+"条.");
		return flag;
	}
	
	/**
	 * 确认费用清单 twr_towerbillbalance
	 * @param list 解析的数据
	 * @param dataMap 已存在的数据
	 * @param dicNameMap 数据字典    key:(枚举值标题code+#+枚举值描述) 哈希码  value:枚举值数值
	 * @return
	 */
	public boolean insertConfirmBillbalance(TaskInfoVO taskInfo, List<TowerConfirmBillbalanceVO> list, Map<Integer, Object> dataMap,Map<Integer, String> dicNameMap) {
		List<TowerConfirmBillbalanceVO> insertList = new ArrayList<TowerConfirmBillbalanceVO>();
		List<TowerConfirmBillbalanceVO> updateList = new ArrayList<TowerConfirmBillbalanceVO>();
		List<TowerConfirmBillbalanceVO> errorList = new ArrayList<TowerConfirmBillbalanceVO>();
		List<String> errorInfo = new ArrayList<String>();
		
		for (TowerConfirmBillbalanceVO vo : list) {
			String validateResult = ValidataUtil.validateTowerConfirmBalance(vo);
			if (StringUtils.isBlank(validateResult)) {
				vo.setDataType(CommonData.TOWER_BILL);// 铁塔账单
				
				// 确认状态
				int statekey = (CommonData.CONFIRM_STATE + "#" + vo.getConfirmStateStr()).hashCode();
				vo.setConfirmState(Integer.parseInt(dicNameMap.get(statekey)));
				// 产品大类
				int typekey = (CommonData.PRODUCT_BIG_TYPE + "#" + vo.getProductBigType()).hashCode();
				vo.setProductBigType(dicNameMap.get(typekey));
				
				int key = (vo.getBusinessConfirmNumber() + "#" + vo.getTowerStationCode()).hashCode();
				if (dataMap.containsKey(key)) {
					vo.setUpdateTime(new Date());
					updateList.add(vo);
				} else {
					insertList.add(vo);
				}
			} else {
				errorList.add(vo);
				errorInfo.add(validateResult);
			}
		}
		LOGGER.info("错误信息:\n"+CollectionUtil.join(errorInfo, "\n"));
		String message = "总共导入" + list.size() + "条数据;新增数据" + insertList.size() 
				+ "条;修改数据:" + updateList.size() + "条;错误数据:" + errorList.size() + "条";
		LOGGER.info(message);
		String historyId = IronTowerFileType.QRFY + "_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");
		taskHistoryInfoService.recordLog(taskInfo, message, historyId);
		
		boolean flag = false;
		if (!insertList.isEmpty()) {
			for(int i=0, length = insertList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i+BatchControlComm.iBatchCount> insertList.size())?insertList.size():i+BatchControlComm.iBatchCount;
				List<TowerConfirmBillbalanceVO> tmp = insertList.subList(i, j);
				flag &= billbalanceMapper.batchInsert(tmp) > 0;
			}
		}
		if (!updateList.isEmpty()) {
			for(int i=0, length = updateList.size(); i<length; i+=BatchControlComm.iBatchCount){
				int j=(i + BatchControlComm.iBatchCount > updateList.size()) ? updateList.size() : i + BatchControlComm.iBatchCount;
				List<TowerConfirmBillbalanceVO> tmp = updateList.subList(i, j);
				flag &= billbalanceMapper.batchUpdate(tmp) > 0;
			}
		}
		LOGGER.info("新增租赁费账单数据成功："+insertList.size() + "条；更新租赁费账单数据成功："+updateList.size()+"条.");
		return flag;
	}
}
