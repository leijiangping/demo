package com.xunge.service.basedata.ring.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunge.comm.StateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.FileUtils;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.ring.MeterPerfVOMapper;
import com.xunge.dao.basedata.ring.PowerPerfVOMapper;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.DatBaseresourceVOExample.Criteria;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.basedata.ring.IRingService;
import com.xunge.service.basedata.util.ExcelExportHelper;

@Service
public class RingServiceImpl implements IRingService{

	@Resource
	private MeterPerfVOMapper meterPerfVOMapper;
	
	@Resource
	private PowerPerfVOMapper powerPerfVOMapper;
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	private int pageSize=5000;
	
	
	@Override
	public PageInfo<MeterPerfVO> findAllMeterPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,Integer pageNumber, Integer pageSize) {
		//PageInterceptor.startPage(pageNumber, pageSize);
		MeterPerfVO meterPerfVO=new MeterPerfVO();
		
		if(StringUtils.isNoneBlank(prvid)){
			meterPerfVO.setPrvId(prvid);
		}
		if(StringUtils.isNoneBlank(pregid)){
			meterPerfVO.setPregId(pregid);
		}
		if(StringUtils.isNoneBlank(regid)){
			meterPerfVO.setRegId(regid);
		}
		if(PromptMessageComm.RESOURCE_TYPE_10006.equalsIgnoreCase(resourcetype)||PromptMessageComm.RESOURCE_TYPE_10005.equalsIgnoreCase(resourcetype)){
			meterPerfVO.setResourceType(Integer.parseInt(resourcetype));
		}
		if(StringUtils.isNoneBlank(resourcename)){
			meterPerfVO.setResourceName(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
			//meterPerfVO.setResourceCode(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
		}
		if(StringUtils.isNoneBlank(date)){
			meterPerfVO.setQueryDate(date+PromptMessageComm.PERCENT_SIGN);
		}
		if(StringUtils.isNoneBlank(userId)){
			meterPerfVO.setUserId(userId);
		}
		if(pageNumber != null && pageSize != null ){
			PageHelper.startPage(pageNumber, pageSize);
		}
		List<MeterPerfVO> datas=meterPerfVOMapper.selectByCondition(meterPerfVO);
		PageInfo<MeterPerfVO> pageinfo = new PageInfo<MeterPerfVO>(datas);
		return pageinfo;
	}

	@Override
	public PageInfo<PowerPerfVO> findAllPowerPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,Integer pageNumber, Integer pageSize) {
		//PageInterceptor.startPage(pageNumber, pageSize);
		PowerPerfVO powerPerfVO=new PowerPerfVO();
		
		if(StringUtils.isNoneBlank(prvid)){
			powerPerfVO.setPrvId(prvid);
		}
		if(StringUtils.isNoneBlank(pregid)){
			powerPerfVO.setPregId(pregid);
		}
		if(StringUtils.isNoneBlank(regid)){
			powerPerfVO.setRegId(regid);
		}
		if(PromptMessageComm.RESOURCE_TYPE_10006.equalsIgnoreCase(resourcetype)||PromptMessageComm.RESOURCE_TYPE_10005.equalsIgnoreCase(resourcetype)){
			powerPerfVO.setResourceType(Integer.parseInt(resourcetype));
		}
		if(StringUtils.isNoneBlank(resourcename)){
			powerPerfVO.setResourceName(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
			//powerPerfVO.setResourceCode(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
		}
		if(StringUtils.isNoneBlank(date)){
			powerPerfVO.setQueryDate(date+PromptMessageComm.PERCENT_SIGN);
		}
		
		if(StringUtils.isNoneBlank(userId)){
			powerPerfVO.setUserId(userId);
		}
		if(pageNumber != null && pageSize != null ){
			PageHelper.startPage(pageNumber, pageSize);
		}
		List<PowerPerfVO> datas=powerPerfVOMapper.selectByCondition(powerPerfVO);
		PageInfo<PowerPerfVO> pageinfo = new PageInfo<PowerPerfVO>(datas);
		return pageinfo;
	}
	
	private void dealMeterRegInfo(List<MeterPerfVO> cuidDatas,List<MeterPerfVO> codeDatas, List<String> cuids, List<String> codes,SysProvinceVO sysProvinceVO) {
		
		if (cuids != null && cuids.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCuidIn(cuids);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(MeterPerfVO meterPerfVO:cuidDatas){
				String resourceCode=meterPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCuid().equalsIgnoreCase(resourceCode)){
						meterPerfVO.setPregId(datBaseresourceVO.getPregId());
						meterPerfVO.setPregName(datBaseresourceVO.getPregName());
						meterPerfVO.setRegId(datBaseresourceVO.getRegId());
						meterPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
		if (codes != null && codes.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCodeIn(codes);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(MeterPerfVO meterPerfVO:codeDatas){
				String resourceCode=meterPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCode().equalsIgnoreCase(resourceCode)){
						meterPerfVO.setPregId(datBaseresourceVO.getPregId());
						meterPerfVO.setPregName(datBaseresourceVO.getPregName());
						meterPerfVO.setRegId(datBaseresourceVO.getRegId());
						meterPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
	}
	
	private void dealPowerRegInfo(List<PowerPerfVO> cuidDatas,List<PowerPerfVO> codeDatas,List<String>cuids,List<String>codes,SysProvinceVO sysProvinceVO){
		
		if (cuids != null && cuids.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCuidIn(cuids);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(PowerPerfVO powerPerfVO:cuidDatas){
				String resourceCode=powerPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCuid().equalsIgnoreCase(resourceCode)){
						powerPerfVO.setPregId(datBaseresourceVO.getPregId());
						powerPerfVO.setPregName(datBaseresourceVO.getPregName());
						powerPerfVO.setRegId(datBaseresourceVO.getRegId());
						powerPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
		if (codes != null && codes.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCodeIn(codes);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(PowerPerfVO powerPerfVO:codeDatas){
				String resourceCode=powerPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCode().equalsIgnoreCase(resourceCode)){
						powerPerfVO.setPregId(datBaseresourceVO.getPregId());
						powerPerfVO.setPregName(datBaseresourceVO.getPregName());
						powerPerfVO.setRegId(datBaseresourceVO.getRegId());
						powerPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
	}

	@Override
	public String exportAllMeterPerf(String userId,String prvid,String path,String pregid, 
			String regid, String resourcename, String resourcetype,String date,HttpServletRequest request, HttpServletResponse response) {
		
		String result=StateComm.STATE_str1;
		//String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"meter";
		long currenttime = System.currentTimeMillis();
		String fileName = currenttime + DateDisposeComm.SUFF_XLS;
		
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		Object[] sheetObj=ExcelExportHelper.createMeterExcelSheet();
		if(sheetObj!=null&&sheetObj.length==2){
			wb=(HSSFWorkbook)sheetObj[0];
			sheet=(HSSFSheet)sheetObj[1];
		}
		if(wb!=null&&sheet!=null){
			MeterPerfVO meterPerfVO=new MeterPerfVO();
			
			if(StringUtils.isNoneBlank(prvid)){
				meterPerfVO.setPrvId(prvid);
			}
			if(StringUtils.isNoneBlank(pregid)){
				meterPerfVO.setPregId(pregid);
			}
			if(StringUtils.isNoneBlank(regid)){
				meterPerfVO.setRegId(regid);
			}
			if(PromptMessageComm.RESOURCE_TYPE_10006.equalsIgnoreCase(resourcetype)||PromptMessageComm.RESOURCE_TYPE_10005.equalsIgnoreCase(resourcetype)){
				meterPerfVO.setResourceType(Integer.parseInt(resourcetype));
			}
			if(StringUtils.isNoneBlank(resourcename)){
				meterPerfVO.setResourceName(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
				//meterPerfVO.setResourceCode(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
			}
			if(StringUtils.isNoneBlank(date)){
				meterPerfVO.setQueryDate(date+PromptMessageComm.PERCENT_SIGN);
			}
			if(StringUtils.isNoneBlank(userId)){
				meterPerfVO.setUserId(userId);
			}
			List<MeterPerfVO> datas=meterPerfVOMapper.selectByCondition(meterPerfVO);
			ExcelExportHelper.setMeterExcelProperty(sheet, datas);
//			int pages=firstPage.getPages();
//			//int firstPageSize=firstPage.getPageSize();
//			for(int i=0;i<pages;i++){
//				if(0==i){
//					ExcelExportHelper.setMeterExcelProperty(sheet, firstPage.getList(), i);
//				}
//				else{
//					PageInfo<MeterPerfVO> page=findAllMeterPerf(userId,prvid,pregid,regid,resourcename,resourcetype,date,++i,pageSize);
//					List<MeterPerfVO> meterList=page.getList();
//					ExcelExportHelper.setMeterExcelProperty(sheet, meterList, i*pageSize);
//				}
//			}
//			boolean exportResult=ExcelExportHelper.exportFile(wb, path, fileName);
//			if(exportResult){
//				result=fileName;
//			}
		}
		FileUtils.downFile(wb, fileName, request, response);
		return result;
	}

	@Override
	public String exportAllPowerPerf(String userId,String prvid,String path,String pregid,
			String regid, String resourcename, String resourcetype,String date,HttpServletRequest request, HttpServletResponse response) {
		
		String result=StateComm.STATE_str1;
		//String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"meter";
		long currenttime = System.currentTimeMillis();
		String fileName = currenttime + DateDisposeComm.SUFF_XLS;
		
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		Object[] sheetObj=ExcelExportHelper.createPowerExcelSheet();
		if(sheetObj!=null&&sheetObj.length==2){
			wb=(HSSFWorkbook)sheetObj[0];
			sheet=(HSSFSheet)sheetObj[1];
		}
		if(wb!=null&&sheet!=null){
			PowerPerfVO powerPerfVO=new PowerPerfVO();
			
			if(StringUtils.isNoneBlank(prvid)){
				powerPerfVO.setPrvId(prvid);
			}
			if(StringUtils.isNoneBlank(pregid)){
				powerPerfVO.setPregId(pregid);
			}
			if(StringUtils.isNoneBlank(regid)){
				powerPerfVO.setRegId(regid);
			}
			if(PromptMessageComm.RESOURCE_TYPE_10006.equalsIgnoreCase(resourcetype)||PromptMessageComm.RESOURCE_TYPE_10005.equalsIgnoreCase(resourcetype)){
				powerPerfVO.setResourceType(Integer.parseInt(resourcetype));
			}
			if(StringUtils.isNoneBlank(resourcename)){
				powerPerfVO.setResourceName(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
				//powerPerfVO.setResourceCode(PromptMessageComm.PERCENT_SIGN+resourcename+PromptMessageComm.PERCENT_SIGN);
			}
			if(StringUtils.isNoneBlank(date)){
				powerPerfVO.setQueryDate(date+PromptMessageComm.PERCENT_SIGN);
			}
			
			if(StringUtils.isNoneBlank(userId)){
				powerPerfVO.setUserId(userId);
			}
			List<PowerPerfVO> datas=powerPerfVOMapper.selectByCondition(powerPerfVO);
			
			
			ExcelExportHelper.setPowerExcelProperty(sheet, datas);
			
//			int pages=firstPage.getPages();
//			//int firstPageSize=firstPage.getPageSize();
//			for(int i=0;i<pages;i++){
//				if(0==i){
//					ExcelExportHelper.setPowerExcelProperty(sheet, firstPage.getList(), i);
//				}
//				else{
//					PageInfo<PowerPerfVO> page=findAllPowerPerf(userId,prvid,pregid,regid,resourcename,resourcetype,date,++i,pageSize);
//					List<PowerPerfVO> meterList=page.getList();
//					ExcelExportHelper.setPowerExcelProperty(sheet, meterList, i*pageSize);
//				}
//			}
//			boolean exportResult=ExcelExportHelper.exportFile(wb, path, fileName);
//			if(exportResult){
//				result=fileName;
//			}
		}
		FileUtils.downFile(wb, fileName, request, response);
		return result;
	}
}
