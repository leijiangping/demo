package com.xunge.service.basedata.supplier;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.basedata.vo.SupplierQueryVO;
import com.xunge.model.system.region.SysRegionVO;

public interface ISupplierService {

	/**
	 * 根据权限获取供应商列表
	 * @param prvId
	 * @return
	 */
	List<DatSupplierVO> getSupplierList(String prvId);
	List<DatSupplierVO> getSupplierList(String prvId,List<String> list);
	
	PageInfo<DatSupplierVO> getAll(SupplierQueryVO vo);
	/**
	 * @description 查询供应商列表 
	 * @author yuefy
	 * @date 创建时间：2017年9月8日
	 */
	public List<DatSupplierVO> getSupplierAll(SupplierQueryVO vo);
	
	DatSupplierVO get(String id);
	
	int insert(DatSupplierVO record);
		
	int update(DatSupplierVO record);
		
	int delete(String id);
	
	int deleteBatch(List<DatSupplierVO> list);
	
	String export(SupplierQueryVO vo, String path,HttpServletRequest request, HttpServletResponse response);
	
	Object[] importData(String prvId, MultipartFile file,List<SysRegionVO> listreg);
	
	/**
	 * @description 通过code查询供应商
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	List<DatSupplierVO> querySupplierVOByCode(Map<String,Object> map);
}
