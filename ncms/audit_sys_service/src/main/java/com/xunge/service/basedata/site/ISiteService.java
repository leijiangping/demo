package com.xunge.service.basedata.site;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.vo.SiteQueryVO;
import com.xunge.model.system.region.SysRegionVO;

public interface ISiteService {

	PageInfo<DatBasesiteVO> getAll(SiteQueryVO vo, Map<String,Object> map);
	
	/**
	 * 报账点审核页面查询 分页
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author xup
	 */
	public PageInfo<DatBasesiteVO> querySiteInfo(Map<String,Object> map,int pageNumber,int pageSize);
	
	/**
	 * @description 查询站点信息
	 * @author yuefy
	 * @date 创建时间：2017年8月18日
	 */
	public List<DatBasesiteVO> querySiteInfo(Map<String,Object> map);
	
	DatBasesiteVO get(String id);
	/**
	 * @description 查询 站点 和资源点信息 
	 * @author yuefy
	 * @date 创建时间：2017年8月7日
	 */
	DatBasesiteVO queryOne(String id);
	
	int insert(DatBasesiteVO record);
		
	int update(DatBasesiteVO record);
	
	boolean updateBatch(List<DatBasesiteVO> list);
		
	int delete(String id);
	
	int deleteBatch(List<DatBasesiteVO> list);
	
	int submitAudit(List<Map<String,Object>> list,UserLoginInfo loginUser);
	
	int audit(List<Map<String, Object>> list, UserLoginInfo loginInfo);
	/**
	 * 审核页面导出
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public String exportAudit(Map<String,Object> map,String filePath,int pageNumber,int pageSize, HttpServletRequest request, HttpServletResponse response);
	String export(SiteQueryVO vo, String path,HttpServletRequest request,HttpServletResponse response);
	
	Object[] importData(String prvId, MultipartFile file,List<SysRegionVO> listReg);
	
	/**
	 * 验证站点编码
	 * @author wangz
	 * @param paramMap
	 * @return
	 */
	List<DatBasesiteVO> checkeSitCode(Map<String,Object> paramMap);
}
