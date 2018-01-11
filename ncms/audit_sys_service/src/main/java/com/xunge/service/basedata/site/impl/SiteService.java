package com.xunge.service.basedata.site.impl;

import java.io.InputStream;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.TaskComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.enums.ResourceEnum;
import com.xunge.core.enums.ResourceEnum.AuditStateEnum;
import com.xunge.core.enums.ResourceEnum.DataFromEnum;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.DatBasesiteVOMapper;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.DatBasesiteVOExample;
import com.xunge.model.basedata.DatBasesiteVOExample.Criteria;
import com.xunge.model.basedata.vo.SiteQueryVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.basedata.site.ISiteService;
import com.xunge.service.basedata.util.ExcelExportHelper;
import com.xunge.service.basedata.util.ExcelImportHelper;

@Service
public class SiteService implements ISiteService {

	@Resource
	private DatBasesiteVOMapper mapper;

	@Autowired
	private DatBaseresourceVOMapper datBaseresourceMapper;
	@Autowired
	private IActTaskService actTaskService;

	@Override
	public PageInfo<DatBasesiteVO> getAll(SiteQueryVO vo,
			Map<String, Object> map) {
		DatBasesiteVOExample example = new DatBasesiteVOExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		// 权限过滤
		if (!StringUtils.isEmpty(vo.getLoginUser().getPrv_id())) {
			criteria1.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria2.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
		}
		// TODO 查询条件
		if (!StringUtils.isEmpty(vo.getCity())) {
			criteria1.andPregIdEqualTo(vo.getCity());
			criteria2.andPregIdEqualTo(vo.getCity());
		}
		// TODO 查询条件
		if (vo.getBasesiteState() != null) {
			criteria1.andBasesiteStateNoEqualTo(vo.getBasesiteState());
			criteria2.andBasesiteStateNoEqualTo(vo.getBasesiteState());
		}
		List<String> regIds = vo.getLoginUser().getReg_ids();
		// 权限过滤
		if (regIds != null && regIds.size() > 0) {
			criteria1.andRegIdIn(regIds);
			criteria2.andRegIdIn(regIds);
		}

		if (!StringUtils.isEmpty(vo.getRegion())) {
			criteria1.andRegIdEqualTo(vo.getRegion());
			criteria2.andRegIdEqualTo(vo.getRegion());
		}
		if (!StringUtils.isEmpty(vo.getSiteReg())) {
			criteria1.andBasesiteCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getSiteReg() + PromptMessageComm.PERCENT_SIGN);
			criteria2.andBasesiteNameLike(PromptMessageComm.PERCENT_SIGN + vo.getSiteReg() + PromptMessageComm.PERCENT_SIGN);
		}
		if (vo.getAuditStatus() != null) {
			criteria1.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria2.andAuditingStateEqualTo(vo.getAuditStatus());
		}
		if (vo.getQueryType() != null) {
			if (vo.getQueryType() == 0) {
				criteria1.andDataFromEqualTo(DataFromEnum.SYSTEM);
				criteria2.andDataFromEqualTo(DataFromEnum.IMPORT);
			} else if (vo.getQueryType() == 1) {
				criteria1.andDataFromOfNull();
				criteria2.andDataFromOfNull();
			} else {
				criteria1.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria2.andDataFromEqualTo(DataFromEnum.COLLECTION);
			}
		}
		// 页面查询条件补充
		if (!StringUtils.isEmpty(vo.getProperty())) {
			criteria1.andBasesiteBelongEqualTo(Integer.parseInt(vo
					.getProperty()));
			criteria2.andBasesiteBelongEqualTo(Integer.parseInt(vo
					.getProperty()));
		}
		if (vo.getStatus() != null) {
			criteria1.andBasesiteStateEqualTo(vo.getStatus());
			criteria2.andBasesiteStateEqualTo(vo.getStatus());
		}
		example.or(criteria2);
		PageHelper.startPage(vo.getCur_page_num(), vo.getPage_count());
		PageInfo<DatBasesiteVO> page = new PageInfo<DatBasesiteVO>(
				mapper.selectByExample(example));
		if (map != null) {
			if (map.get("ids") != null) {
				List<DatBasesiteVO> list = Lists.newArrayList();
				List<Act> acts = (List<Act>) map.get("ids");
				for (DatBasesiteVO t : page.getList()) {
					for (Act actTemp : acts) {
						if (t.getBasesiteId() != null
								&& t.getBasesiteId().equals(
										actTemp.getBusinessId())) {
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
	 * @description 查询站点列表
	 * @author yuefy
	 * @date 创建时间：2017年9月07日
	 */
	public List<DatBasesiteVO> getBasesiteAll(SiteQueryVO vo) {
		DatBasesiteVOExample example = new DatBasesiteVOExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		// 权限过滤
		if (!StringUtils.isEmpty(vo.getLoginUser().getPrv_id())) {
			criteria1.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria2.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
		}
		// TODO 查询条件
		if (!StringUtils.isEmpty(vo.getCity())) {
			criteria1.andPregIdEqualTo(vo.getCity());
			criteria2.andPregIdEqualTo(vo.getCity());
		}
		// TODO 查询条件
		if (vo.getBasesiteState() != null) {
			criteria1.andBasesiteStateNoEqualTo(vo.getBasesiteState());
			criteria2.andBasesiteStateNoEqualTo(vo.getBasesiteState());
		}
		List<String> regIds = vo.getLoginUser().getReg_ids();
		// 权限过滤
		if (regIds != null && regIds.size() > 0) {
			criteria1.andRegIdIn(regIds);
			criteria2.andRegIdIn(regIds);
		}

		if (!StringUtils.isEmpty(vo.getRegion())) {
			criteria1.andRegIdEqualTo(vo.getRegion());
			criteria2.andRegIdEqualTo(vo.getRegion());
		}
		if (!StringUtils.isEmpty(vo.getSiteReg())) {
			criteria1.andBasesiteCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getSiteReg() + PromptMessageComm.PERCENT_SIGN);
			criteria2.andBasesiteNameLike(PromptMessageComm.PERCENT_SIGN + vo.getSiteReg() + PromptMessageComm.PERCENT_SIGN);
		}
		if (vo.getAuditStatus() != null) {
			criteria1.andAuditingStateEqualTo(vo.getAuditStatus());
			criteria2.andAuditingStateEqualTo(vo.getAuditStatus());
		}
		if (vo.getQueryType() != null) {
			if (vo.getQueryType() == 0) {
				criteria1.andDataFromEqualTo(DataFromEnum.SYSTEM);
				criteria2.andDataFromEqualTo(DataFromEnum.IMPORT);
			} else if (vo.getQueryType() == 1) {
				criteria1.andDataFromOfNull();
				criteria2.andDataFromOfNull();
			} else {
				criteria1.andDataFromEqualTo(DataFromEnum.COLLECTION);
				criteria2.andDataFromEqualTo(DataFromEnum.COLLECTION);
			}
		}
		// 页面查询条件补充
		if (!StringUtils.isEmpty(vo.getProperty())) {
			criteria1.andBasesiteBelongEqualTo(Integer.parseInt(vo
					.getProperty()));
			criteria2.andBasesiteBelongEqualTo(Integer.parseInt(vo
					.getProperty()));
		}
		if (vo.getStatus() != null) {
			criteria1.andBasesiteStateEqualTo(vo.getStatus());
			criteria2.andBasesiteStateEqualTo(vo.getStatus());
		}
		example.or(criteria2);
		List<DatBasesiteVO> page = mapper.selectByExample(example);
		return page;
	}

	@Override
	public DatBasesiteVO get(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public DatBasesiteVO queryOne(String id) {
		List<DatBaseresourceVO> list = datBaseresourceMapper
				.getDatBaseresourceById(id);
		DatBasesiteVO datBasesite = mapper.selectByPrimaryKey(id);
		datBasesite.setDatBaseresourceList(list);
		return datBasesite;
	}

	@Override
	public int insert(DatBasesiteVO record) {
		return mapper.insert(record);
	}

	@Override
	public int update(DatBasesiteVO record) {
		DatBasesiteVO vo = get(record.getBasesiteId());
		BeanUtils.copyProperties(record, vo);
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public boolean updateBatch(List<DatBasesiteVO> list) {
		return mapper.batchUpdate(list);
	}

	@Override
	public int delete(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatch(List<DatBasesiteVO> list) {
		for (DatBasesiteVO vo : list) {
			mapper.deleteByPrimaryKey(vo.getBasesiteId());
		}
		return 1;
	}

	@Override
	public int submitAudit(List<Map<String, Object>> list,
			UserLoginInfo loginUser) {
		for (Map<String, Object> map : list) {
			DatBasesiteVO record = mapper.selectByPrimaryKey(map.get("id")
					.toString());
			if (record != null
					&& record.getAuditingState() == ActivityStateComm.STATE_UNCOMMITTED) {
				actTaskService.startProcess(ActUtils.PD_SITE_AUDIT[0],
						loginUser.getPrv_code(), ActUtils.PD_SITE_AUDIT[1], map
								.get("id").toString(),
						ActUtils.PD_SITE_AUDIT[2], map, loginUser
								.getUser_loginname());
				record.setAuditingState(AuditStateEnum.AUDITING);
				mapper.updateByPrimaryKey(record);
			}
		}
		return list.size();
	}

	@Override
	public int audit(List<Map<String, Object>> list, UserLoginInfo loginInfo) {
		for (Map<String, Object> map : list) {
			String taskid = map.get("taskId").toString();
			Task t = actTaskService.getTask(taskid);
			DatBasesiteVO record = mapper.selectByPrimaryKey(map.get("id")
					.toString());
			// 增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if (t != null && record != null) {
				Map<String, Object> vars = new HashMap<String, Object>();
				vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NAME,
						map.get("auditState").toString());
				if (StrUtil.isNotBlank(map.get("nextAuditUserId").toString())) {
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER, map
							.get("nextAuditUserId").toString());// 指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if (StrUtil.isNotBlank(user_loginname)) {
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_CURRUSER,
							user_loginname);// 指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_SITE_AUDIT[1],
						record.getBasesiteId(),
						map.get("auditNote").toString(),
						ActUtils.PD_SITE_AUDIT[2], vars);
				Task newtask = actTaskService.getTask(
						ActUtils.PD_SITE_AUDIT[1], record.getBasesiteId());
				if (newtask == null) {
					record.setAuditingDate(new Date());
					record.setAuditingUserId(user_loginname);
					if (map.get("auditState") != null
							&& map.get("auditState")
									.toString()
									.equals(ActivityStateComm.STATE_UNAPPROVE
											+ "")) {
						record.setAuditingState(AuditStateEnum.AUDITFAIL);
					} else if (map.get("auditState") != null
							&& map.get("auditState")
									.toString()
									.equals(ActivityStateComm.STATE_NORMAL + "")) {
						record.setAuditingState(AuditStateEnum.AUDITSUCC);
					}
					mapper.updateByPrimaryKey(record);
				}
			}
		}
		return list.size();
	}

	public String exportAudit(Map<String, Object> map, String path,
			int pageNumber, int pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		String fileName = PromptMessageComm.DEVICE_INFO_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		int qt = Integer.parseInt(map.get("") != null ? map.get("").toString()
				: StateComm.STATE_str0);
		Object[] sheetObj = ExcelExportHelper.createResourceExcelSheet(PromptMessageComm.SITE_INFO,
				qt, PromptMessageComm.SITE);
		if (sheetObj != null && sheetObj.length == 2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		if (wb != null && sheet != null) {
			List<DatBasesiteVO> datas = null;
			if(map.get("op") != null){
				datas = null;
			}else{
				datas = querySiteInfo(map);
			}
			ExcelExportHelper.setSiteExcelProperty(sheet, datas, Integer.parseInt(map.get("queryType").toString()));
		}
		FileUtils.downFile(wb, fileName, request, response);
		return fileName;
	}

	public String export(SiteQueryVO vo, String path,
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = PromptMessageComm.DEVICE_INFO_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		Object[] sheetObj = ExcelExportHelper.createResourceExcelSheet(PromptMessageComm.SITE_INFO,
				vo.getQueryType(), PromptMessageComm.SITE);
		if (sheetObj != null && sheetObj.length == 2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		if (wb != null && sheet != null) {
			List<DatBasesiteVO> datas = getBasesiteAll(vo);
			ExcelExportHelper.setSiteExcelProperty(sheet, datas,
					vo.getQueryType());
			// vo.setCur_page_num(1);
			// vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
			// PageInfo<DatBasesiteVO> firstPage = getAll(vo, null);
			// int pages = firstPage.getPages();
			// for(int i=0;i<pages;i++){
			// if(0==i){
			// ExcelExportHelper.setSiteExcelProperty(sheet,
			// firstPage.getList(), i, vo.getQueryType());
			// }else{
			// vo.setCur_page_num(i++);
			// vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
			// PageInfo<DatBasesiteVO> page = getAll(vo, null);
			// ExcelExportHelper.setSiteExcelProperty(sheet, page.getList(), i,
			// vo.getQueryType());
			// }
			// }
		}
		FileUtils.downFile(wb, fileName, request, response);
		// boolean exportResult = ExcelExportHelper.exportFile(wb, path,
		// fileName);
		// if(!exportResult){
		// fileName = "";
		// }
		return fileName;
	}

	@Override
	public Object[] importData(String prvId, MultipartFile file,
			List<SysRegionVO> listReg) {
		Object[] result = new Object[2];
		try {
			String originFileName = file.getOriginalFilename();
			InputStream in = file.getInputStream();
			Workbook workbook = ExcelImportHelper.getWorkbook(in,
					originFileName);
			List<Integer> errRowList = importListFromExcel(prvId, workbook, 0,
					listReg);
			String errorMsg = ExcelImportHelper.buildMsg(errRowList);
			in.close();
			result[0] = Integer.parseInt(TaskComm.SUCCESS_0);
			result[1] = errorMsg;
		} catch (Exception e) {
			result[0] = Integer.parseInt(TaskComm.FAIL_1);
			result[1] = PromptMessageComm.OPERATION_FAILED;
		}
		return result;
	}

	private List<Integer> importListFromExcel(String prvId, Workbook workbook,
			int sheetNum, List<SysRegionVO> listReg) {
		Sheet sheet = workbook.getSheetAt(sheetNum);
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();
		Map<Integer, DatBasesiteVO> map = new HashMap<Integer, DatBasesiteVO>();
		List<Integer> errRowList = new ArrayList<Integer>();
		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();

		for (int rowIx = minRowIx + 1; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			DatBasesiteVO vo = buildExeclData(row, evaluator);
			vo.setBasesiteId(PromptMessageComm.BASE_SITE + System.currentTimeMillis());
			vo.setPrvId(prvId);
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : listReg) {
				int i = 0;
				if (sysRegionVO.getPrvId() != null
						&& sysRegionVO.getPrvId().equals(vo.getPrvId())) {
					vo.setPrvSname(sysRegionVO.getPrvName());
					i++;
				}
				if (sysRegionVO.getPregName() != null
						&& sysRegionVO.getPregName().equals(vo.getPregName())) {
					vo.setPregId(sysRegionVO.getPregId());
					i++;
				}
				if (sysRegionVO.getRegId() != null
						&& sysRegionVO.getRegName().equals(vo.getRegName())) {
					vo.setRegId(sysRegionVO.getRegId());
					i++;
				}
				if (i == 3) {
					break;
				}
			}

			map.put(rowIx, vo);
		}
		errRowList = excelImportToDB(map);
		return errRowList;
	}

	private DatBasesiteVO buildExeclData(Row row, FormulaEvaluator evaluator) {
		DatBasesiteVO vo = new DatBasesiteVO();

		for (int i = 0; i < 7; i++) {
			String value = ExcelImportHelper.getCellValue(evaluator,
					row.getCell(new Integer(i)));
			if (StringUtils.isNoneBlank(value)) {
				switch (i) {
				case 0:
					vo.setAuditingState((Integer)ResourceEnum.auditStateEnum.getValue(value));
					break;
				case 1:
					vo.setBasesiteCode(value);
					break;
				case 2:
					vo.setBasesiteName(value);
					break;
				case 3:
					vo.setPregName(value);
					break;
				case 4:
					vo.setRegName(value);
					break;
				case 5:
					String basesiteBelong = "";
					String[] data1 = value.split(PromptMessageComm.COMMA_SYMBOL);
					for(int j = 0 ; j < data1.length ; j++){
						basesiteBelong += ResourceEnum.resBelongEnum.getValue(data1[j])+PromptMessageComm.COMMA_SYMBOL;
					}
					vo.setBasesiteBelong(basesiteBelong);
					break;
				case 6:
					vo.setBasesiteState((Integer) ResourceEnum.resStateEnum.getValue(value));
					break;
				}
			}
		}
		return vo;
	}

	private List<Integer> excelImportToDB(Map<Integer, DatBasesiteVO> map) {
		List<Integer> errorRow = new ArrayList<Integer>();
		for (Integer rowIndex : map.keySet()) {
			try {
				DatBasesiteVO vo = map.get(rowIndex);
				DatBasesiteVOExample example = new DatBasesiteVOExample();
				Criteria criteria = example.createCriteria();
				criteria.andBasesiteCodeEqualTo(vo.getBasesiteCode());
				criteria.andBasesiteNameEqualTo(vo.getBasesiteName());
				List<DatBasesiteVO> oldVOs = mapper.selectByExample(example);
				if (oldVOs != null && oldVOs.size() > 0) {
					vo.setBasesiteId(oldVOs.get(0).getBasesiteId());
					mapper.updateByPrimaryKeySelective(vo);
				} else {
					vo.setDataFrom(DataFromEnum.IMPORT);
					mapper.insertSelective(vo);
				}
			} catch (Exception e) {
				errorRow.add(rowIndex);
			}
		}
		return errorRow;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<DatBasesiteVO> querySiteInfo(Map<String, Object> map,
			int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<DatBasesiteVO> datas = mapper.querySiteInfo(map);
		PageInfo<DatBasesiteVO> page = new PageInfo<DatBasesiteVO>(datas);
		if (map.get("idsList") != null) {
			List<DatBasesiteVO> list = Lists.newArrayList();
			for (DatBasesiteVO t : page.getList()) {
				for (Act actTemp : (List<Act>) map.get("idsList")) {
					if (t.getBasesiteId() != null
							&& t.getBasesiteId()
									.equals(actTemp.getBusinessId())) {
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
	 * @description 查询站点信息
	 * @author yuefy
	 * @date 创建时间：2017年8月18日
	 */
	@Override
	public List<DatBasesiteVO> querySiteInfo(Map<String, Object> map) {
		List<DatBasesiteVO> datas = mapper.querySiteInfo(map);
		return datas;
	}

	@Override
	public List<DatBasesiteVO> checkeSitCode(Map<String, Object> paramMap) {
		return mapper.checkByCode(paramMap);
	}

}
