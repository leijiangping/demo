package com.xunge.service.basedata.device.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunge.comm.StateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.dao.basedata.DatBasestationVOMapper;
import com.xunge.model.basedata.DatBasestationVOExample.Criteria;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatBasestationVOExample;
import com.xunge.model.basedata.DatDevicefactoryVO;
import com.xunge.model.basedata.vo.DeviceQueryVO;
import com.xunge.service.basedata.device.IDeviceService;
import com.xunge.service.basedata.util.ExcelExportHelper;

@Service
public class DeviceService implements IDeviceService {

	@Resource
	private DatBasestationVOMapper mapper;

	@Override
	public PageInfo<DatBasestationVO> getAll(DeviceQueryVO vo) {
		DatBasestationVOExample example = new DatBasestationVOExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		Criteria criteria3 = example.createCriteria();
		// TODO 查询条件
		if (!StringUtils.isEmpty(vo.getDevType())) {
			criteria1.andBasestationCategoryEqualTo(vo.getDevType());
			criteria2.andBasestationCategoryEqualTo(vo.getDevType());
			criteria3.andBasestationCategoryEqualTo(vo.getDevType());
		}
		if (!StringUtils.isEmpty(vo.getStatus())) {
			criteria1.andBasestationStateEqualTo(vo.getStatus());
			criteria2.andBasestationStateEqualTo(vo.getStatus());
			criteria3.andBasestationStateEqualTo(vo.getStatus());
		}
		if (!StringUtils.isEmpty(vo.getReg())) {
			criteria1.andBaseresourceCuidLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria2.andBasestationCodeLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
			criteria3.andBasestationNameLike(PromptMessageComm.PERCENT_SIGN + vo.getReg() + PromptMessageComm.PERCENT_SIGN);
		}
		// 权限过滤
		if (!StringUtils.isEmpty(vo.getLoginUser().getPrv_id())) {
			criteria1.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria2.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria3.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
		}
		
		example.or(criteria2);
		example.or(criteria3);
		PageHelper.startPage(vo.getCur_page_num(), vo.getPage_count());
		PageInfo<DatBasestationVO> page = new PageInfo<DatBasestationVO>(mapper.selectByExample(example));
		return page;
	}

	@Override
	public DatBasestationVO get(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(DatBasestationVO record) {
		return mapper.insert(record);
	}

	@Override
	public int update(DatBasestationVO record) {
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int delete(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatch(List<DatBasestationVO> list) {
		for (DatBasestationVO vo : list) {
			mapper.deleteByPrimaryKey(vo.getBasestationId());
		}
		return 1;
	}

	public String export(DeviceQueryVO vo, String path) {
		String fileName = PromptMessageComm.DEVICE_INFO_EXPORT + System.currentTimeMillis() + DateDisposeComm.SUFF_XLS;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		Object[] sheetObj = ExcelExportHelper.createDeviceExcelSheet(PromptMessageComm.DEVICE_INFO);
		if (sheetObj != null && sheetObj.length == 2) {
			wb = (HSSFWorkbook) sheetObj[0];
			sheet = (HSSFSheet) sheetObj[1];
		}
		if (wb != null && sheet != null) {
			vo.setCur_page_num(1);
			vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
			PageInfo<DatBasestationVO> firstPage = getAll(vo);
			int pages = firstPage.getPages();
			for (int i = 0; i < pages; i++) {
				if (StateComm.STATE_0 == i) {
					ExcelExportHelper.setDeviceExcelProperty(sheet, firstPage.getList(), i);
				} else {
					vo.setCur_page_num(i++);
					vo.setPage_count(ExcelExportHelper.EXPORT_PAGESIZE);
					PageInfo<DatBasestationVO> page = getAll(vo);
					ExcelExportHelper.setDeviceExcelProperty(sheet, page.getList(), i);
				}
			}
		}
		boolean exportResult = ExcelExportHelper.exportFile(wb, path, fileName);
		if (!exportResult) {
			fileName = "";
		}
		return fileName;
	}
	
	/**
	 * @description 查询设备制造商 
	 * @author yuefy
	 * @date 创建时间：2017年9月20日
	 */
	public List<DatDevicefactoryVO> selectDevice(Map<String,Object> map){
		return mapper.selectDivice(map);
	};
}
