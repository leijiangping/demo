package com.xunge.service.basedata.resource;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.vo.ResourceQueryVO;
import com.xunge.model.system.region.SysRegionVO;

public interface IDatResourceService {
	
	PageInfo<DatBaseresourceVO> getAll(ResourceQueryVO vo, Map<String,Object> map);
	
	/**
	 * 资源点审核页面查询
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author xup
	 */
	public PageInfo<DatBaseresourceVO> queryDatBaseresource(Map<String,Object> map,int pageNumber,int pageSize);
	
	
	DatBaseresourceVO get(String id);
	
	DatBaseresourceVO queryDetails(String id);
	
	int insert(DatBaseresourceVO record);
		
	int update(DatBaseresourceVO record);
		
	boolean updateBatch(List<DatBaseresourceVO> list);
	
	boolean updateBatchAudit(List<DatBaseresourceVO> list);
		
	int delete(String id);
	
	int deleteBatch(List<DatBaseresourceVO> list);
	
	int submitAudit(List<Map<String,Object>> list,UserLoginInfo loginUser);
	
	int audit(List<Map<String, Object>> list, UserLoginInfo loginInfo);

	public String exportAudit(Map<String, Object> map,String path,HttpServletRequest request,HttpServletResponse response);
	String export(ResourceQueryVO vo, String path,HttpServletRequest request,HttpServletResponse response);
	
	Object[] importData(String prvId, MultipartFile file,List<SysRegionVO> listreg,int baseresourceType);

	/**
	 * @description 判断资源代码唯一性
	 * @author yuefy
	 * @date 创建时间：2017年8月25日
	 */
	public List<DatBaseresourceVO> checkBaseresourceCode(Map<String, Object> map);
    /**
     * 全省资源数
     * @param map
     * @return
     */
    public List<Map<String, String>> queryAllResourceNumByPrvid(Map<String,Object> map);
    /**
     * 未关联机房资源数、机房资源总数
     * @param map
     * @return
     */
    public Map<String, Object> queryNoLinkResource(Map<String,Object> map);
}
