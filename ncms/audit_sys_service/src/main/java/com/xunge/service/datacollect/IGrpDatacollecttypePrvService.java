package com.xunge.service.datacollect;

import java.util.List;
import java.util.Map;

import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;

public interface IGrpDatacollecttypePrvService {
	/**
	 * 修改省级上报数据类型
	 * @param grpDatacollecttypePrvVO
	 * @return
	 */
	public String updatePrvType(GrpDatacollecttypePrvVO grpDatacollecttypePrvVO);
	/**
	 * 省级上报文件保存
	 * @param paraMap
	 * @return
	 */
	public String updatePrvFiles(Map<String,Object> paraMap);
	/**
	 * 修改时删除数据类型
	 * @param paraMap
	 * @return
	 */
	public String deleteTypePrvByTypeId(Map<String,Object> paraMap);
	/**
	 * 根据省份上报id查询上报文件name
	 * @param datacollectPrvId
	 * @return
	 */
	public List<GrpDatacollecttypePrvVO> queryPrvUploadNameById(String datacollectPrvId);
	/**
	 * 下载省级上报文件并打包下载
	 */
	public List<String> uploadZip(String datacollectId,String prvId);
}
