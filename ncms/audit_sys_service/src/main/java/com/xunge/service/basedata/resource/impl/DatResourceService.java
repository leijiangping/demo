package com.xunge.service.basedata.resource.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xunge.comm.TaskComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.enums.ResourceEnum;
import com.xunge.core.enums.ResourceEnum.AuditStateEnum;
import com.xunge.core.enums.ResourceEnum.DataFromEnum;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.DatBasestationVOMapper;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBaseresourceVOExample.Criteria;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.vo.ResourceQueryVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.basedata.resource.IDatResourceService;
import com.xunge.service.basedata.util.ExcelExportHelper;
import com.xunge.service.basedata.util.ExcelImportHelper;

@Service
public class DatResourceService implements IDatResourceService {

	@Resource
	private DatBaseresourceVOMapper mapper;
	
	@Resource
	private DatBasestationVOMapper datBasestationVOMapper;
	@Autowired
	private IActTaskService actTaskService;
	
	@Override
	public PageInfo<DatBaseresourceVO> getAll(ResourceQueryVO vo, Map<String,Object> map) {
		DatBaseresourceVOExample example = new DatBaseresourceVOExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		Criteria criteria3 = example.createCriteria();
		Criteria criteria4 = example.createCriteria();
		// 权限过滤
		if (!StringUtils.isEmpty(vo.getLoginUser().getPrv_id())) {
			criteria1.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria2.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria3.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria4.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
		}
		if (!vo.getLoginUser().getReg_ids().isEmpty()) {
			criteria1.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria2.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria3.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria4.andRegIdIn(vo.getLoginUser().getReg_ids());
		}
		
		if(vo.getResType() != null){			
			criteria1.andBaseresourceTypeEqualTo(vo.getResType());
			criteria2.andBaseresourceTypeEqualTo(vo.getResType());
			criteria3.andBaseresourceTypeEqualTo(vo.getResType());
			criteria4.andBaseresourceTypeEqualTo(vo.getResType());
		}
		if (!StringUtils.isEmpty(vo.getCity())) {
			criteria1.andPregIdEqualTo(vo.getCity());
			criteria2.andPregIdEqualTo(vo.getCity());
			criteria3.andPregIdEqualTo(vo.getCity());
			criteria4.andPregIdEqualTo(vo.getCity());
		}
		if (!StringUtils.isEmpty(vo.getRegion())) {
			criteria1.andRegIdEqualTo(vo.getRegion());
			criteria2.andRegIdEqualTo(vo.getRegion());
			criteria3.andPregIdEqualTo(vo.getRegion());
			criteria4.andPregIdEqualTo(vo.getRegion());
		}
		if (!StringUtils.isEmpty(vo.getReg())) {
			criteria1.andBaseresourceCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria2.andBaseresourceNameLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria3.andBaseresourceNameLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria4.andBaseresourceCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
		}
		if(vo.getProperty() != null){
			criteria1.andRoomOwnerEqualTo(vo.getProperty());
			criteria2.andRoomOwnerEqualTo(vo.getProperty());
			criteria3.andRoomOwnerEqualTo(vo.getProperty());
			criteria4.andRoomOwnerEqualTo(vo.getProperty());
		}
		if (vo.getAuditStatus() != null) {
			criteria1.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria2.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria3.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria4.andAuditingStateEqualTo(vo.getAuditStatus());
		}
		
		if(vo.getStatus() != null){
			criteria1.andBaseresourceStateEqualTo(vo.getStatus());
			criteria2.andBaseresourceStateEqualTo(vo.getStatus());
			criteria3.andBaseresourceStateEqualTo(vo.getStatus());
			criteria4.andBaseresourceStateEqualTo(vo.getStatus());
		}
		
		if (vo.getQueryType() != null) {
			if(vo.getQueryType() == DataFromComm.STATE_0){
				criteria1.andDataFromEqualTo(DataFromEnum.SYSTEM);
				criteria2.andDataFromEqualTo(DataFromEnum.IMPORT);
				criteria3.andDataFromEqualTo(DataFromEnum.SYSTEM);
				criteria4.andDataFromEqualTo(DataFromEnum.IMPORT);
			}else if(vo.getQueryType() == DataFromComm.STATE_2){
				criteria1.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria2.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria3.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria4.andDataFromEqualTo(DataFromEnum.COLLECTION);
			}else{
				criteria1.andDataFromOfNull();
				criteria2.andDataFromOfNull();
			}
		}
		if (vo.getResType() != null){
			criteria1.andBaseresourceTypeEqualTo(vo.getResType());
			criteria2.andBaseresourceTypeEqualTo(vo.getResType());
			criteria3.andBaseresourceTypeEqualTo(vo.getResType());
			criteria4.andBaseresourceTypeEqualTo(vo.getResType());
		}
		example.or(criteria2);
		example.or(criteria3);
		example.or(criteria4);
		PageHelper.startPage(vo.getCur_page_num(), vo.getPage_count());
		PageInfo<DatBaseresourceVO> page = new PageInfo<DatBaseresourceVO>(mapper.selectByExample(example));
		if(map != null){
			if(map.get("ids")!=null){
				List<DatBaseresourceVO> list = Lists.newArrayList();
				List<Act> acts = (List<Act>)map.get("ids");
				for(DatBaseresourceVO t:page.getList()){
					for(Act actTemp : acts){
						if(t.getBaseresourceId()!=null&&t.getBaseresourceId().equals(actTemp.getBusinessId())){
							t.setAct(actTemp);
						}
					}
					list.add(t);
				}
				page.setList(list);
			}
		}
		return page;
	}
	
	/**
	 * @description 查询资源列表
	 * @author yuefy
	 * @date 创建时间：2017年9月07日
	 */
	public List<DatBaseresourceVO> getBaseresourceAll(ResourceQueryVO vo) {
		DatBaseresourceVOExample example = new DatBaseresourceVOExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		Criteria criteria3 = example.createCriteria();
		Criteria criteria4 = example.createCriteria();
		// 权限过滤
		if (!StringUtils.isEmpty(vo.getLoginUser().getPrv_id())) {
			criteria1.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria2.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria3.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria4.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
		}
		if (!vo.getLoginUser().getReg_ids().isEmpty()) {
			criteria1.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria2.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria3.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria4.andRegIdIn(vo.getLoginUser().getReg_ids());
		}
		
		if(vo.getResType() != null){			
			criteria1.andBaseresourceTypeEqualTo(vo.getResType());
			criteria2.andBaseresourceTypeEqualTo(vo.getResType());
			criteria3.andBaseresourceTypeEqualTo(vo.getResType());
			criteria4.andBaseresourceTypeEqualTo(vo.getResType());
		}
		if (!StringUtils.isEmpty(vo.getCity())) {
			criteria1.andPregIdEqualTo(vo.getCity());
			criteria2.andPregIdEqualTo(vo.getCity());
			criteria3.andPregIdEqualTo(vo.getCity());
			criteria4.andPregIdEqualTo(vo.getCity());
		}
		if (!StringUtils.isEmpty(vo.getRegion())) {
			criteria1.andRegIdEqualTo(vo.getRegion());
			criteria2.andRegIdEqualTo(vo.getRegion());
			criteria3.andPregIdEqualTo(vo.getRegion());
			criteria4.andPregIdEqualTo(vo.getRegion());
		}
		if (!StringUtils.isEmpty(vo.getReg())) {
			criteria1.andBaseresourceCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria2.andBaseresourceNameLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria3.andBaseresourceNameLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria4.andBaseresourceCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
		}
		if(vo.getProperty() != null){
			criteria1.andRoomOwnerEqualTo(vo.getProperty());
			criteria2.andRoomOwnerEqualTo(vo.getProperty());
			criteria3.andRoomOwnerEqualTo(vo.getProperty());
			criteria4.andRoomOwnerEqualTo(vo.getProperty());
		}
		if (vo.getAuditStatus() != null) {
			criteria1.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria2.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria3.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria4.andAuditingStateEqualTo(vo.getAuditStatus());
		}
		
		if(vo.getStatus() != null){
			criteria1.andBaseresourceStateEqualTo(vo.getStatus());
			criteria2.andBaseresourceStateEqualTo(vo.getStatus());
			criteria3.andBaseresourceStateEqualTo(vo.getStatus());
			criteria4.andBaseresourceStateEqualTo(vo.getStatus());
		}
		
		if (vo.getQueryType() != null) {
			if(vo.getQueryType() == DataFromComm.STATE_0){
				criteria1.andDataFromEqualTo(DataFromEnum.SYSTEM);
				criteria2.andDataFromEqualTo(DataFromEnum.IMPORT);
				criteria3.andDataFromEqualTo(DataFromEnum.SYSTEM);
				criteria4.andDataFromEqualTo(DataFromEnum.IMPORT);
			}else if(vo.getQueryType() == DataFromComm.STATE_2){
				criteria1.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria2.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria3.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria4.andDataFromEqualTo(DataFromEnum.COLLECTION);
			}else{
				criteria1.andDataFromOfNull();
				criteria2.andDataFromOfNull();
			}
		}
		if (vo.getResType() != null){
			criteria1.andBaseresourceTypeEqualTo(vo.getResType());
			criteria2.andBaseresourceTypeEqualTo(vo.getResType());
			criteria3.andBaseresourceTypeEqualTo(vo.getResType());
			criteria4.andBaseresourceTypeEqualTo(vo.getResType());
		}
		example.or(criteria2);
		example.or(criteria3);
		example.or(criteria4);
		List<DatBaseresourceVO> page = mapper.selectByExample(example);
		return page;
	}
	
	

	@Override
	public DatBaseresourceVO get(String id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	@Override
	public DatBaseresourceVO queryDetails(String id) {
		List<DatBasestationVO> datBasestationVO = datBasestationVOMapper.queryById(id);
		DatBaseresourceVO datBaseresourceVO = mapper.selectByPrimaryKey(id);
		datBaseresourceVO.setDatBasestationVO(datBasestationVO);
		return datBaseresourceVO;
	}
	
	@Override
	public int insert(DatBaseresourceVO record) {
		return mapper.insert(record);
	}

	@Override
	public int update(DatBaseresourceVO record) {
		// DatBaseresourceVO vo = get(record.getBaseresourceId());
		// BeanUtils.copyProperties(record, vo);
		// 审核未通过(8)修改后状态为未提交(-1)
		if (record.getAuditingState() == ActivityStateComm.STATE_UNAPPROVE ) {
			record.setAuditingState(ActivityStateComm.STATE_UNCOMMITTED);
		}
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public boolean updateBatch(List<DatBaseresourceVO> list) {
		return mapper.batchUpdate(list);
	}

	@Override
	public boolean updateBatchAudit(List<DatBaseresourceVO> list) {
		return mapper.batchUpdateForAudit(list);
	}

	@Override
	public int delete(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatch(List<DatBaseresourceVO> list) {
		for (DatBaseresourceVO vo : list) {
			mapper.deleteByPrimaryKey(vo.getBaseresourceId());
		}
		return Integer.parseInt(RESULT.SUCCESS_1);
	}
	
	@Override
	public int submitAudit(List<Map<String,Object>> list,UserLoginInfo loginUser) {
		for(Map<String,Object> map:list){
			DatBaseresourceVO record = mapper.selectByPrimaryKey(map.get("id").toString());
			if(record!=null&&record.getAuditingState()==ActivityStateComm.STATE_UNCOMMITTED){
				actTaskService.startProcess(ActUtils.PD_BASERESOURCE_AUDIT[0],loginUser.getPrv_code(), ActUtils.PD_BASERESOURCE_AUDIT[1], map.get("id").toString(),ActUtils.PD_BASERESOURCE_AUDIT[2],map,loginUser.getUser_loginname());
				record.setAuditingState(AuditStateEnum.AUDITING);
				mapper.updateByPrimaryKey(record);
			}
		}
		return list.size();
	}
	
	@Override
	public int audit(List<Map<String, Object>> list, UserLoginInfo loginInfo) {
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			Task t = actTaskService.getTask(taskid);
			DatBaseresourceVO record = mapper.selectByPrimaryKey(map.get("id").toString());
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null && record != null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NAME, map.get("auditState").toString());
				if(StrUtil.isNotBlank(map.get("nextAuditUserId").toString())){
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER, map.get("nextAuditUserId").toString());//指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_CURRUSER, user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_BASERESOURCE_AUDIT[1],record.getBaseresourceId(),map.get("auditNote").toString(),ActUtils.PD_BASERESOURCE_AUDIT[2],vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_BASERESOURCE_AUDIT[1],record.getBaseresourceId());
				if(newtask==null){
					record.setAuditingDate(new Date());
					record.setAuditingUserId(user_loginname);
					if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
						record.setAuditingState(AuditStateEnum.AUDITFAIL);
					}else if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
						record.setAuditingState(AuditStateEnum.AUDITSUCC);
					}
					mapper.updateByPrimaryKey(record);
				}
			}
		}
		return list.size();
	}
	public String exportAudit(Map<String, Object> map,String path,HttpServletRequest request,HttpServletResponse response) {
		int rq=Integer.parseInt(map.get("queryType").toString());
		String resName = getResName(rq);
		if (resName == "") {
			return "";
		}
		String fileName = resName + PromptMessageComm.DEVICE_INFO_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		Object[] sheetObj = ExcelExportHelper.createResourceExcelSheet(resName + PromptMessageComm.DEVICE_INFO,rq, resName);
		if (sheetObj != null && sheetObj.length == 2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		List<DatBaseresourceVO> datas = null;
		if (wb != null && sheet != null) {
			if(map.get("op") != null){
				datas = null;
			}else{
				datas = mapper.queryDatBaseresource(map);
			}
			ExcelExportHelper.setResourceExcelProperty(sheet, datas,rq, resName);
//			int pages = page.getPages();
//			for (int i = 0; i < pages; i++) {
//				if (0 == i) {
//					ExcelExportHelper.setResourceExcelProperty(sheet, page.getList(), i, rq,
//							resName);
//				} else {
//					ExcelExportHelper.setResourceExcelProperty(sheet, page.getList(), i, rq, resName);
//				}
//			}
		}
		FileUtils.downFile(wb, fileName, request, response);
//		boolean exportResult = ExcelExportHelper.exportFile(wb, path, fileName);
//		if (!exportResult) {
//			fileName = "";
//		}
		return fileName;
	}
	
	
	public String export(ResourceQueryVO vo, String path, HttpServletRequest request,HttpServletResponse response) {
		String resName = getResName(vo.getResType());
		if (resName == "") {
			return "";
		}
		String fileName = resName + PromptMessageComm.DEVICE_INFO_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		Object[] sheetObj = ExcelExportHelper.createResourceExcelSheet(resName + PromptMessageComm.DEVICE_INFO, vo.getQueryType(), resName);
		if (sheetObj != null && sheetObj.length == 2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		if (wb != null && sheet != null) {
			Map<String,Object> map = new HashMap<>();
			List<DatBaseresourceVO> datas = getBaseresourceAll(vo);
			ExcelExportHelper.setResourceExcelProperty(sheet, datas,vo.getQueryType(), resName);
//			vo.setCur_page_num(1);
//			vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
//			PageInfo<DatBaseresourceVO> firstPage = getAll(vo, null);
//			int pages = firstPage.getPages();
//			for (int i = 0; i < pages; i++) {
//				if (0 == i) {
//					ExcelExportHelper.setResourceExcelProperty(sheet, firstPage.getList(), i, vo.getQueryType(),
//							resName);
//				} else {
//					vo.setCur_page_num(i++);
//					vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
//					PageInfo<DatBaseresourceVO> page = getAll(vo, null);
//					ExcelExportHelper.setResourceExcelProperty(sheet, page.getList(), i, vo.getQueryType(), resName);
//				}
//			}
		}
		
		FileUtils.downFile(wb, fileName, request, response);
//		boolean exportResult = ExcelExportHelper.exportFile(wb, path, fileName);
//		if (!exportResult) {
//			fileName = "";
//		}
		return fileName;
	}
	

	@Override
	public Object[] importData(String prvId, MultipartFile file,List<SysRegionVO> listreg,int baseresourceType) {
		Object[] result=new Object[2];
		try{
			String originFileName = file.getOriginalFilename();
			InputStream in = file.getInputStream();
			Workbook workbook = ExcelImportHelper.getWorkbook(in, originFileName);
			List<Integer> errRowList = importListFromExcel(prvId, workbook, 0,listreg,baseresourceType);
			String errorMsg = ExcelImportHelper.buildMsg(errRowList);
			in.close();
			result[0]=Integer.parseInt(TaskComm.SUCCESS_0);
			result[1]=errorMsg;
		}catch(Exception e){
			result[0]=Integer.parseInt(TaskComm.FAIL_1);
			result[1]=PromptMessageComm.OPERATION_FAILED;
		}
		return result;
	}
	
	private List<Integer> importListFromExcel(String prvId, Workbook workbook, int sheetNum,List<SysRegionVO> listreg,int baseresourceType) {
		Sheet sheet = workbook.getSheetAt(sheetNum);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Map<Integer, DatBaseresourceVO> map = new HashMap<Integer, DatBaseresourceVO>();
		List<Integer> errRowList = new ArrayList<Integer>();
		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();
		for (int rowIx = minRowIx + 1; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			DatBaseresourceVO vo = buildExeclData(row, evaluator);
			vo.setBaseresourceId(PromptMessageComm.BASE_RESOURCE_ID+SysUUID.generator());
			vo.setPrvId(prvId);
			vo.setBaseresourceType(baseresourceType);
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : listreg) {
				int i=0;
				if(sysRegionVO.getPrvId()!=null&&sysRegionVO.getPrvId().equals(vo.getPrvId())){
					vo.setPrvSname(sysRegionVO.getPrvName());
					i++;
				}
				if(sysRegionVO.getPregName()!=null&&sysRegionVO.getPregName().equals(vo.getPregName())){
					vo.setPregId(sysRegionVO.getPregId());
					i++;
				}
				if(sysRegionVO.getRegId()!=null&&sysRegionVO.getRegName().equals(vo.getRegName())){
					vo.setRegId(sysRegionVO.getRegId());
					i++;
				}
				if(i==3){
					break;
				}
			}
			map.put(rowIx, vo);
		}
		errRowList = excelImportToDB(map);
		return errRowList;
	}
	

	private DatBaseresourceVO buildExeclData(Row row, FormulaEvaluator evaluator) {
		DatBaseresourceVO vo = new DatBaseresourceVO();
		for(int i=0;i<8;i++){
			String value = ExcelImportHelper.getCellValue(evaluator, row.getCell(new Integer(i)));
			if(StringUtils.isNoneBlank(value)){
				switch (i) {
				case 0:
					vo.setAuditingState((Integer)ResourceEnum.auditStateEnum.getValue(value));
					break;
				case 1:
					vo.setBaseresourceCode(value);
					break;
				case 2:
					vo.setBaseresourceName(value);
					break;
				case 3:
					vo.setPregName(value);
					break;
				case 4:
					vo.setRegName(value);
					break;
				case 5:
					vo.setRoomOwner(ResourceEnum.resBelongEnum.getValue(value)+"");
					break;
				case 6:
					vo.setBaseresourceState((Integer) ResourceEnum.resStateEnum.getValue(value));
					break;
				case 7:
					vo.setBaseresourceType((Integer) ResourceEnum.resourceTypeEnum.getValue(value));
					break;
				}
			}
		}
		return vo;
	}

	private List<Integer> excelImportToDB(Map<Integer, DatBaseresourceVO> map) {
		List<Integer> errorRow = new ArrayList<Integer>();
		for (Integer rowIndex : map.keySet()) {
			try {
				DatBaseresourceVO vo = map.get(rowIndex);
				DatBaseresourceVOExample example = new DatBaseresourceVOExample();
				Criteria criteria = example.createCriteria();
				criteria.andBaseresourceCodeEqualTo(vo.getBaseresourceCode());
				criteria.andBaseresourceNameEqualTo(vo.getBaseresourceName());
				List<DatBaseresourceVO> oldVOs = mapper.selectByExample(example);
				if (oldVOs != null && oldVOs.size() > 0) {
					vo.setBaseresourceId(oldVOs.get(0).getBaseresourceId());
					mapper.updateByPrimaryKeySelective(vo);
				}else {
					vo.setDataFrom(DataFromEnum.IMPORT);
					mapper.insertSelective(vo);
				}
			} catch (Exception e) {
				errorRow.add(rowIndex);
			}
		}
		return errorRow;
	}

	private String getResName(Integer type) {
		String resName = "";
		switch (type) {
		case 0:
			resName = PromptMessageComm.ENGINE_HOME;
			break;
		case 1:
			resName = PromptMessageComm.RESOURCE_POINT;
			break;
		case 2:
			resName = PromptMessageComm.HOT_POINT;
			break;
		default:
			break;
		}
		return resName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DatBaseresourceVO> queryDatBaseresource(
			Map<String, Object> map, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<DatBaseresourceVO> datas = mapper.queryDatBaseresource(map);
		PageInfo<DatBaseresourceVO> page= new PageInfo<DatBaseresourceVO>(datas);
		if(map.get("idsList")!=null){
			List<DatBaseresourceVO> list=Lists.newArrayList();
			for(DatBaseresourceVO t:page.getList()){
				for(Act actTemp:(List<Act>)map.get("idsList")){
					if(t.getBaseresourceId()!=null&&t.getBaseresourceId().equals(actTemp.getBusinessId())){
						t.setAct(actTemp);
						list.add(t);
					}
				}
			}
			page.setList(list);
		}
		return page;
	}
	
	/**
	 * @description 判断资源代码唯一性
	 * @author yuefy
	 * @date 创建时间：2017年8月25日
	 */
	@Override
	public List<DatBaseresourceVO> checkBaseresourceCode(Map<String, Object> map){
		return mapper.checkBaseresourceCode(map);
	};

	@Override
	public List<Map<String, String>> queryAllResourceNumByPrvid(Map<String, Object> map) {
		return mapper.queryAllResourceNumByPrvid(map);
	}

	@Override
	public Map<String, Object> queryNoLinkResource(Map<String, Object> paramMap) {
		//未关联机房资源数
		int nolinkedres = mapper.selectNolinkResource(paramMap);//已关联资源数
		//机房资源总数
		int allres = mapper.selectAllResource(paramMap);//所有资源数
		Map<String, Object> resmap = new HashMap<>();
		resmap.put("nolinkedres", nolinkedres);
		resmap.put("allres", allres);
		return resmap;
	}
}
