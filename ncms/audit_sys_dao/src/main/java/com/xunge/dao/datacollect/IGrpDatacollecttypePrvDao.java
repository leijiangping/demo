package com.xunge.dao.datacollect;

import java.util.List;
import java.util.Map;

import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;

public interface IGrpDatacollecttypePrvDao {
	/**
	 * 根据集团收集id删除省级上报文件类型信息
	 * @param datacollectId
	 * @return
	 */
	public int deleteTypePrvById(String datacollectId);
	/**
	 * 新增省级上报文件类型数据
	 * @param grpDatacollecttypePrvVO
	 * @return
	 */
	public int insertSelective(GrpDatacollecttypePrvVO grpDatacollecttypePrvVO);
	/**
	 * 根据省份id查询省级上报信息
	 * @param paraMap
	 * @return
	 */
	public List<GrpDatacollecttypePrvVO> queryEveryPrvMsg(Map<String,Object> paraMap);
	/**
	 * 根据省级上报信息id查询省级上报信息
	 * @param paraMap
	 * @return
	 */
	public List<GrpDatacollecttypePrvVO> queryEveryPrvMsgByPk(Map<String,Object> paraMap);
	/**
	 * 修改省级上报数据类型
	 * @param grpDatacollecttypePrvVO
	 * @return
	 */
	public int updatePrvType(GrpDatacollecttypePrvVO grpDatacollecttypePrvVO);
	/**
	 * 省级上报文件保存
	 * @param paraMap
	 * @return
	 */
	public int updatePrvFiles(Map<String,Object> paraMap);
	/**
	 * 修改时删除数据类型
	 * @param paraMap
	 * @return
	 */
	public int deleteTypePrvByTypeId(Map<String,Object> paraMap);
	/**
	 * 根据升级上报id查询上报文件name
	 * @param paraMap
	 * @return
	 */
	public List<GrpDatacollecttypePrvVO> queryPrvUploadNameById(Map<String,Object> paraMap);
	/**
	 * 根据省份上报编码查询该省上报文件路径
	 * @param map
	 * @return
	 */
	public List<String> queryPrvFilePath(Map<String,Object> map);
}
