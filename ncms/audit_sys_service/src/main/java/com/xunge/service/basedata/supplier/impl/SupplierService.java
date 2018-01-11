package com.xunge.service.basedata.supplier.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.TaskComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.rent.contract.SupplierStateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.enums.ResourceEnum.DataFromEnum;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.basedata.DatSupplierVOMapper;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.basedata.DatSupplierVOExample;
import com.xunge.model.basedata.DatSupplierVOExample.Criteria;
import com.xunge.model.basedata.vo.SupplierQueryVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.basedata.supplier.ISupplierService;
import com.xunge.service.basedata.util.ExcelExportHelper;
import com.xunge.service.basedata.util.ExcelImportHelper;

@Service
public class SupplierService implements ISupplierService {

	@Resource
	private DatSupplierVOMapper mapper;

	@Override
	public PageInfo<DatSupplierVO> getAll(SupplierQueryVO vo) {
		PageHelper.startPage(vo.getCur_page_num(), vo.getPage_count());
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if(vo.getDataFrom() != null){
			paramMap.put("dataFrom", DataFromComm.STATE_0);
			paramMap.put("dataFrom1", DataFromComm.STATE_1);
		}
		String pregId = vo.getCity();
		String regId = vo.getRegion();
		String prvId = vo.getLoginUser().getPrv_id();
		List<String> regLst = vo.getLoginUser().getReg_ids();
		List<String> pregLst = vo.getLoginUser().getPreg_ids();
		paramMap.put("regId", regId);
		paramMap.put("pregId", pregId);
		paramMap.put("prvId", prvId);
		paramMap.put("regIds", regLst);
		paramMap.put("pregIds", pregLst);
		paramMap.put("supplierState", StateComm.STATE_0);
		paramMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		if(vo.getSupplierReg() != null){
			paramMap.put("supplierReg", vo.getSupplierReg().trim());
		}
		List<DatSupplierVO> list = mapper.querySupplierVO(paramMap);
		PageInfo<DatSupplierVO> page = new PageInfo<DatSupplierVO>(list);
		return page;
	}
	
	@Override
	public List<DatSupplierVO> getSupplierAll(SupplierQueryVO vo) {
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if(vo.getDataFrom() != null){
			paramMap.put("dataFrom", DataFromComm.STATE_0);
			paramMap.put("dataFrom1", DataFromComm.STATE_1);
		}
		String pregId = vo.getCity();
		String regId = vo.getRegion();
		String prvId = vo.getLoginUser().getPrv_id();
		List<String> regLst = vo.getLoginUser().getReg_ids();
		List<String> pregLst = vo.getLoginUser().getPreg_ids();
		paramMap.put("regId", regId);
		paramMap.put("pregId", pregId);
		paramMap.put("prvId", prvId);
		paramMap.put("regIds", regLst);
		paramMap.put("pregIds", pregLst);
		paramMap.put("supplierState", StateComm.STATE_0);
		paramMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		if(vo.getSupplierReg() != null){
			paramMap.put("supplierReg", vo.getSupplierReg().trim());
		}
		List<DatSupplierVO> list = mapper.querySupplierVO(paramMap);
		return list;
	}

	@Override
	public DatSupplierVO get(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(DatSupplierVO record) {
		return mapper.insert(record);
	}

	@Override
	public int update(DatSupplierVO record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int delete(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatch(List<DatSupplierVO> list) {
		for (DatSupplierVO vo : list) {
			mapper.deleteByPrimaryKey(vo.getSupplierId());
		}
		return 1;
	}

	public String export(SupplierQueryVO vo, String path,HttpServletRequest request, HttpServletResponse response) {
		String fileName = PromptMessageComm.SUPPLIER_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		Object[] sheetObj = ExcelExportHelper.createSupplierExcelSheet(PromptMessageComm.SUPPLIER_MSG, vo.getQueryType());
		if (sheetObj != null && sheetObj.length == 2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		if (wb != null && sheet != null) {
			List<DatSupplierVO> datas = getSupplierAll(vo);
			ExcelExportHelper.setSupplierExcelProperty(sheet, datas, vo.getQueryType());
//			vo.setCur_page_num(1);
//			vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
//			PageInfo<DatSupplierVO> firstPage = getAll(vo);
//			int pages = firstPage.getPages();
//			for (int i = 0; i < pages; i++) {
//				if (0 == i) {
//					ExcelExportHelper.setSupplierExcelProperty(sheet, firstPage.getList(), i, vo.getQueryType());
//				} else {
//					vo.setCur_page_num(i++);
//					vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
//					PageInfo<DatSupplierVO> page = getAll(vo);
//					ExcelExportHelper.setSupplierExcelProperty(sheet, page.getList(), i, vo.getQueryType());
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
	public Object[] importData(String prvId, MultipartFile file,List<SysRegionVO> listreg) {
		Object[] result=new Object[2];
		try{
			String originFileName = file.getOriginalFilename();
			InputStream in = file.getInputStream();
			Workbook workbook = ExcelImportHelper.getWorkbook(in, originFileName);
			List<Integer> errRowList = importListFromExcel(prvId, workbook, 0,listreg);
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
	
	private List<Integer> importListFromExcel(String prvId, Workbook workbook, int sheetNum,List<SysRegionVO> listreg) {
		Sheet sheet = workbook.getSheetAt(sheetNum);
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Map<Integer, DatSupplierVO> map = new HashMap<Integer, DatSupplierVO>();
		List<Integer> errRowList = new ArrayList<Integer>();
		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();
		for (int rowIx = minRowIx + 1; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			DatSupplierVO vo = buildExeclData(row, evaluator);
			vo.setSupplierId(PromptMessageComm.SUPPLIER_ID+SysUUID.generator());
			vo.setPrvId(prvId);
			vo.setSupplierState(SupplierStateComm.STATE_0);
			for (com.xunge.model.system.region.SysRegionVO sysRegionVO : listreg) {
				boolean b1=false;
				boolean b2=false;
				boolean b3=false;
				if(sysRegionVO.getPrvId()!=null&&sysRegionVO.getPrvId().equals(vo.getPrvId())){
					vo.setPrvSname(sysRegionVO.getPrvName());
					b1=true;
				}
				if(sysRegionVO.getPregName()!=null&&sysRegionVO.getPregName().equals(vo.getPregName())){
					vo.setPregId(sysRegionVO.getPregId());
					b2=true;
				}
				if(sysRegionVO.getRegId()!=null&&sysRegionVO.getRegName().equals(vo.getRegName())){
					vo.setRegId(sysRegionVO.getRegId());
					b3=true;
				}
				if(b1&&b2&&b3){
					break;
				}
			}
			map.put(rowIx, vo);
		}
		errRowList = excelImportToDB(map);
		return errRowList;
	}
	

	private DatSupplierVO buildExeclData(Row row, FormulaEvaluator evaluator) {
		DatSupplierVO vo = new DatSupplierVO();
		for(int i=0;i<8;i++){
			String value = ExcelImportHelper.getCellValue(evaluator, row.getCell(new Integer(i)));
			if(StringUtils.isNoneBlank(value)){
				switch (i) {
				case 0:
					vo.setSupplierCode(value);
					break;
				case 1:
					vo.setSupplierName(value);
					break;
				case 2:
					vo.setPregName(value);
					break;
				case 3:
					vo.setRegName(value);
					break;
				case 4:
					vo.setIsDownshare(PromptMessageComm.YES.equals(value) ? 1 : 0);
					break;
				case 5:
					vo.setSupplierSite(value);
					break;
				case 6:
					vo.setSupplierContact(value);
					break;
				case 7:
					vo.setSupplierTelephone(value);
					break;
				}
			}
		}
		return vo;
	}

	private List<Integer> excelImportToDB(Map<Integer, DatSupplierVO> map) {
		List<Integer> errorRow = new ArrayList<Integer>();
		for (Integer rowIndex : map.keySet()) {
			try {
				DatSupplierVO vo = map.get(rowIndex);
				DatSupplierVOExample example = new DatSupplierVOExample();
				Criteria criteria = example.createCriteria();
				criteria.andSupplierCodeEqualTo(vo.getSupplierCode());
				criteria.andSupplierNameEqualTo(vo.getSupplierName());
				criteria.andSupplierSiteEqualTo(vo.getSupplierSite());
				criteria.andIsDownshareEqualTo(vo.getIsDownshare());
				List<DatSupplierVO> oldVOs = mapper.selectByExample(example);
				if (oldVOs != null && oldVOs.size() > 0) {
					vo.setSupplierId(oldVOs.get(0).getSupplierId());
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

	@Override
	public List<DatSupplierVO> getSupplierList(String prvId) {
		DatSupplierVOExample example = new DatSupplierVOExample();
		if(StringUtils.isNoneBlank(prvId)){
			DatSupplierVOExample.Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(prvId);
		}
		return mapper.selectByExample(example);
	}
	@Override
	public List<DatSupplierVO> getSupplierList(String prvId,List<String> list) {
		DatSupplierVOExample example = new DatSupplierVOExample();
		DatSupplierVOExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNoneBlank(prvId)){
			criteria.andPrvIdEqualTo(prvId);
		}
		if(list!=null&&list.size()>0){
			criteria.andSupplierIdIn(list);
		}else if(list!=null&&list.size()==0){
			return Lists.newArrayList();
		}
		return mapper.selectByExample(example);
	}
	
	@Override
	public List<DatSupplierVO> querySupplierVOByCode(Map<String, Object> map) {
		List<DatSupplierVO> list = mapper.querySupplierVO(map);
		return list;
	}
}
