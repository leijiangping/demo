package com.xunge.service.selfelec.electricmeter.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.xunge.comm.StateComm;
import com.xunge.comm.elec.MeterTypeComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.job.DateConverter;
import com.xunge.comm.job.IDUtils;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.selfelec.electricmeter.DatElectricMeterVOMapper;
import com.xunge.model.selfelec.electricmeter.DatElectricMeterVO;
import com.xunge.service.basedata.util.ExcelExportHelper;
import com.xunge.service.basedata.util.ExcelImportHelper;
import com.xunge.service.selfelec.electricmeter.IElecticMeterService;

@Service
public class ElecticMeterServiceImpl implements IElecticMeterService{

	
	@Resource
	private DatElectricMeterVOMapper datElectricMeterVOMapper;
	
	@Override
	public PageInfo<DatElectricMeterVO> findElectricMeter(List<String> regIds,String userId,String prvid,String pregid,String regid, String meterCode, String meterState,
			String meterType, String relateResState, int pageNumber, int pageSize) {
		//PageInterceptor.startPage(pageNumber, pageSize);
		DatElectricMeterVO datElectricMeterVO=new DatElectricMeterVO();
		
		if(StringUtils.isNoneBlank(prvid)){
			datElectricMeterVO.setPrvId(prvid);
		}
		if(StringUtils.isNoneBlank(pregid)){
			datElectricMeterVO.setPregId(pregid);
		}
		if(StringUtils.isNoneBlank(regid)){
			datElectricMeterVO.setRegId(regid);
		}
		if(StringUtils.isNoneBlank(meterCode)){
			datElectricMeterVO.setMeterCode("%"+meterCode+"%");
		}
		if(StateComm.STATE_str0.equalsIgnoreCase(meterState)||StateComm.STATE_str9.equalsIgnoreCase(meterState)){
			datElectricMeterVO.setMeterState(Integer.parseInt(meterState));
		}
		if(MeterTypeComm.Meter_type_1.equalsIgnoreCase(meterType)||MeterTypeComm.Meter_type_2.equalsIgnoreCase(meterType)){
			datElectricMeterVO.setMeterType(Integer.parseInt(meterType));
		}
		if(StateComm.STATE_str0.equalsIgnoreCase(relateResState)||StateComm.STATE_str1.equalsIgnoreCase(relateResState)){
			datElectricMeterVO.setRelateResState(Integer.parseInt(relateResState));
		}
		if(StringUtils.isNoneBlank(userId)){
			datElectricMeterVO.setUserId(userId);
		}
		PageHelper.startPage(pageNumber, pageSize);
		datElectricMeterVO.setRegIds(regIds);
		List<DatElectricMeterVO> datas=datElectricMeterVOMapper.selectByCondition(datElectricMeterVO);
		PageInfo<DatElectricMeterVO> pageinfo = new PageInfo<DatElectricMeterVO>(datas);
		return pageinfo;
	}

	@Override
	public String exportElectricMeter(List<String> regIds,String userId,String prvid,String path,String pregid,String regid, String meterCode, String meterState,
			String meterType, String relateResState) {
		
		String result=StateComm.STATE_str1;
		//String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"meter";
		long currenttime = System.currentTimeMillis();
		String fileName = currenttime + DateDisposeComm.SUFF_XLS;
		
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		Object[] sheetObj=ExcelExportHelper.createElectricMeterExcelSheet();
		if(sheetObj!=null&&sheetObj.length==SelfelecComm.NUMBER_2){
			wb=(HSSFWorkbook)sheetObj[0];
			sheet=(HSSFSheet)sheetObj[1];
		}
		if(wb!=null&&sheet!=null){
			List<DatElectricMeterVO> datas = findEexportData(regIds, userId, prvid, pregid, regid, meterCode, meterState, meterType, relateResState);
			ExcelExportHelper.setElectricMeterExcelProperty(sheet, datas);
			
//			PageInfo<> firstPage=findElectricMeter(regIds,userId,prvid,pregid,regid,meterCode, meterState,meterType,relateResState,1,pageSize);
//			int pages=firstPage.getPages();
//			//int firstPageSize=firstPage.getPageSize();
//			for(int i=0;i<pages;i++){
//				if(0==i){
//					ExcelExportHelper.setElectricMeterExcelProperty(sheet, firstPage.getList(), i);
//				}
//				else{
//					PageInfo<DatElectricMeterVO> page=findElectricMeter(regIds,userId,prvid,pregid,regid,meterCode, meterState,meterType,relateResState,++i,pageSize);
//					List<DatElectricMeterVO> meterList=page.getList();
//					ExcelExportHelper.setElectricMeterExcelProperty(sheet, meterList, i*pageSize);
//				}
//			}
			boolean exportResult=ExcelExportHelper.exportFile(wb, path, fileName);
			if(exportResult){
				result=fileName;
			}
		}
		
		return result;
	}
	
	@Override
	public Object[] importElectricMeter(String prvId,MultipartFile file) {
		
		Object[] result=new Object[SelfelecComm.NUMBER_2];
		try{
			String originFileName = file.getOriginalFilename();
			InputStream in = file.getInputStream();
			Workbook workbook = ExcelImportHelper.getWorkbook(in, originFileName);
			List<Integer> errRowList = importListFromExcel(prvId,workbook, Integer.parseInt(SelfelecComm.NUMBER_0));
			String errorMsg = ExcelImportHelper.buildMsg(errRowList);
			in.close();
			result[0]=Integer.parseInt(SelfelecComm.NUMBER_0);
			result[1]=errorMsg;
		}catch(Exception e){
			result[0]=SelfelecComm.NUMBER_1;
			result[1]=PromptMessageComm.OPERATION_FAILED;
		}
		return result;
	}
	
	private List<Integer> importListFromExcel(String prvId,Workbook workbook, int sheetNum) {
		Sheet sheet = workbook.getSheetAt(sheetNum);

		// 解析公式结果  
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Map<Integer, DatElectricMeterVO> map = new HashMap<Integer, DatElectricMeterVO>();
		List<Integer> errRowList = new ArrayList<Integer>();
		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();
		for (int rowIx = minRowIx + SelfelecComm.NUMBER_1; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			DatElectricMeterVO datElectricMeterVO = buildDatElectricMeter(row, evaluator);
			if(StringUtils.isNoneBlank(prvId)){
				if(StringUtils.isNoneBlank(datElectricMeterVO.getPrvId())){
					if(prvId.equalsIgnoreCase(datElectricMeterVO.getPrvId())){
						map.put(rowIx, datElectricMeterVO);
					}
				}
				else{
					datElectricMeterVO.setPrvId(prvId);
					map.put(rowIx, datElectricMeterVO);
				}
			}
		}
		errRowList = excelImportToDB(map);
		return errRowList;
	}

	private List<Integer> excelImportToDB(Map<Integer, DatElectricMeterVO> map) {
		List<Integer> errorRow = new ArrayList<Integer>();
		for (Integer rowIndex : map.keySet()) {
			try {
				DatElectricMeterVO datElectricMeterVO = map.get(rowIndex);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("meterCode", datElectricMeterVO.getMeterCode());
				paramMap.put("prvId", datElectricMeterVO.getPrvId());
				List<DatElectricMeterVO> oldDatElectricMeterVO=datElectricMeterVOMapper.selectByMeterCodeMap(paramMap);
				
				if (oldDatElectricMeterVO.size() <= Integer.parseInt(SelfelecComm.NUMBER_0)) {
					datElectricMeterVO.setDataFrom(SelfelecComm.NUMBER_1);
					datElectricMeterVOMapper.insertSelective(datElectricMeterVO);
				} 
			} catch (Exception e) {
				errorRow.add(rowIndex);
			}
		}
		return errorRow;
	}

	private DatElectricMeterVO buildDatElectricMeter(Row row, FormulaEvaluator evaluator) {
		
		DatElectricMeterVO datElectricMeterVO = new DatElectricMeterVO();
		datElectricMeterVO.setMeterId(SysUUID.generator());
		for(int i=Integer.parseInt(SelfelecComm.NUMBER_0);i<SelfelecComm.NUMBER_24;i++){
			String value=ExcelImportHelper.getCellValue(evaluator, row.getCell(new Integer(i)));
			if(StringUtils.isNoneBlank(value)){
				switch (i) {
				case 0:
					datElectricMeterVO.setMeterCode(value);
					break;
				case 1:
					datElectricMeterVO.setMeterType(dealMeterType(value));
					break;
				case 2:
					datElectricMeterVO.setPrvId(value);
					break;
				case 3:
					datElectricMeterVO.setPrvSname(value);
					break;
				case 4:
					datElectricMeterVO.setPregId(value);
					break;
				case 5:
					datElectricMeterVO.setPregName(value);
					break;
				case 6:
					datElectricMeterVO.setRegId(value);
					break;
				case 7:
					datElectricMeterVO.setRegName(value);
					break;
				case 8:
					datElectricMeterVO.setMeterState(dealMeterState(value));
					break;
				case 9:
					datElectricMeterVO.setAccountNumber(value);
					break;
				case 10:
					datElectricMeterVO.setElectricmeterMultiply(filterValue(value));
					break;
				case 11:
					datElectricMeterVO.setIsShare(dealIsShare(value));
					break;
				case 12:
					datElectricMeterVO.setInstallDate(DateConverter.converteToDate(value));
					break;
				case 13:
					datElectricMeterVO.setInitialValue(filterValue(value));
					break;
				case 14:
					datElectricMeterVO.setFlatValue(filterValue(value));
					break;
				case 15:
					datElectricMeterVO.setTopValue(filterValue(value));
					break;
				case 16:
					datElectricMeterVO.setPeakValue(filterValue(value));
					break;
				case 17:
					datElectricMeterVO.setValleyValue(filterValue(value));
					break;
				case 18:
					datElectricMeterVO.setUpperValue(filterValue(value));
					break;
				case 19:
					datElectricMeterVO.setFlatUpperValue(filterValue(value));
					break;
				case 20:
					datElectricMeterVO.setTopUpperValue(filterValue(value));
					break;
				case 21:
					datElectricMeterVO.setPeakUpperValue(filterValue(value));
					break;
				case 22:
					datElectricMeterVO.setValleyUpperValue(filterValue(value));
					break;
				case 23:
					datElectricMeterVO.setMeterNote(value);
					break;
				}
			}
		}
		return datElectricMeterVO;
	}
	
	public BigDecimal filterValue(String value){
		return value.equals(PromptMessageComm.NOTHING_NULL) ? null : new BigDecimal(value);
	}
	
	
    private static int dealMeterType(String meterType){
		
		int result=SelfelecComm.NUMBER_1;
		if(MeterTypeComm.METER_TYPE1_ZH.equalsIgnoreCase(meterType)){
			result=SelfelecComm.NUMBER_1;
		}
		else if(MeterTypeComm.METER_TYPE2_ZH.equalsIgnoreCase(meterType)){
			result=SelfelecComm.NUMBER_2;
		}
		return result;
	}
	
	private static int dealMeterState(String meterState){
		
		int result=StateComm.STATE_0;
		if(StateComm.NORMAL_STR.equalsIgnoreCase(meterState)){
			result=StateComm.STATE_0;
		}
		else if(StateComm.STOP_STR.equalsIgnoreCase(meterState)){
			result=StateComm.STATE_9;
		}
		return result;
	}
	
	private static int dealIsShare(String isShare){
		
		int result=Integer.parseInt(SelfelecComm.NUMBER_0);
		if(PromptMessageComm.NO.equalsIgnoreCase(isShare)){
			result=Integer.parseInt(SelfelecComm.NUMBER_0);
		}
		else if(PromptMessageComm.YES.equalsIgnoreCase(isShare)){
			result=SelfelecComm.NUMBER_1;
		}
		return result;
	}

	@Override
	public boolean addDatElectricMeter(DatElectricMeterVO datElectricMeterVO) {
		
		datElectricMeterVO.setDataFrom(DataFromComm.STATE_0);
		datElectricMeterVO.setMeterId(IDUtils.getID());
		return datElectricMeterVOMapper.insertSelective(datElectricMeterVO);
	}

	@Override
	public boolean editDatElectricMeter(DatElectricMeterVO datElectricMeterVO) {
		 return datElectricMeterVOMapper.updateByPrimaryKey(datElectricMeterVO);
	}

	@Override
	public boolean delDatElectricMeter(String[] ids) {
		return datElectricMeterVOMapper.delDatElectricMeter(ids);
	}

	@Override
	public boolean isExistMeterCode(String meterCode) {
		
		boolean result=false;
		List<DatElectricMeterVO> list=datElectricMeterVOMapper.selectByMeterCode(meterCode);
		if(list!=null&&list.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
			result=true;
		}
		return result;
	}

	public DatElectricMeterVO queryElecMeterId(String elecMeterId){
		return datElectricMeterVOMapper.queryElecMeterId(elecMeterId);
	}
	
	/**
	 * 根据页面过滤参数查询导出数据
	 * @param regIds
	 * @param userId
	 * @param prvid
	 * @param pregid
	 * @param regid
	 * @param meterCode
	 * @param meterState
	 * @param meterType
	 * @param relateResState
	 * @return
	 */
	private List<DatElectricMeterVO> findEexportData(List<String> regIds,String userId,String prvid,String pregid,String regid, String meterCode, String meterState,
			String meterType, String relateResState) {
		DatElectricMeterVO datElectricMeterVO=new DatElectricMeterVO();
		
		if(StringUtils.isNoneBlank(prvid)){
			datElectricMeterVO.setPrvId(prvid);
		}
		if(StringUtils.isNoneBlank(pregid)){
			datElectricMeterVO.setPregId(pregid);
		}
		if(StringUtils.isNoneBlank(regid)){
			datElectricMeterVO.setRegId(regid);
		}
		if(StringUtils.isNoneBlank(meterCode)){
			datElectricMeterVO.setMeterCode("%"+meterCode+"%");
		}
		if(StateComm.STATE_str0.equalsIgnoreCase(meterState)||StateComm.STATE_str9.equalsIgnoreCase(meterState)){
			datElectricMeterVO.setMeterState(Integer.parseInt(meterState));
		}
		if(MeterTypeComm.Meter_type_1.equalsIgnoreCase(meterType)||MeterTypeComm.Meter_type_2.equalsIgnoreCase(meterType)){
			datElectricMeterVO.setMeterType(Integer.parseInt(meterType));
		}
		if(StateComm.STATE_str0.equalsIgnoreCase(relateResState)||StateComm.STATE_str1.equalsIgnoreCase(relateResState)){
			datElectricMeterVO.setRelateResState(Integer.parseInt(relateResState));
		}
		if(StringUtils.isNoneBlank(userId)){
			datElectricMeterVO.setUserId(userId);
		}
		datElectricMeterVO.setRegIds(regIds);
		List<DatElectricMeterVO> datas=datElectricMeterVOMapper.selectByCondition(datElectricMeterVO);
		return datas;
	}

	@Override
	public DatElectricMeterVO queryMeterByMeterId(Map<String, Object> map) {
		return datElectricMeterVOMapper.queryMeterByMeterId(map);
	}

	@Override
	public List<Map<String, String>> queryAllMeterNumByPrvid(Map<String, Object> map) {
		return datElectricMeterVOMapper.queryMeterNumByPrvid(map);
	}

	@Override
	public Map<String,Object> queryNolinkMeter(Map<String, Object> map) {
		int linkedres = datElectricMeterVOMapper.selectLinkedElecMeter(map);//已关联资源数
		int allres = datElectricMeterVOMapper.selectAllElecMeter(map);//所有资源数
		Map<String, Object> resmap = new HashMap<>();
		resmap.put("nolinkedmeter", allres-linkedres);
		resmap.put("allmeter", allres);
		return resmap;
	}

	@Override
	public boolean updateMeterState(Map<String, Object> map) {
		return datElectricMeterVOMapper.updateMeterState(map);
	}
}
