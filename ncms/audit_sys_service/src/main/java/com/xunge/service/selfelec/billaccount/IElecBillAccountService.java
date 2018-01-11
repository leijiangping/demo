package com.xunge.service.selfelec.billaccount; 
/** 
* @author yangju E-mail: 
* @version 创建时间：2017年6月28日 下午2:49:14 
* 类说明 
*/

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.selfelec.EleBaseresourceelectricmeter;
import com.xunge.model.selfelec.EleBillaccount;
import com.xunge.model.selfelec.EleBillaccountbaseresource;
import com.xunge.model.selfelec.EleContractbillaccount;
import com.xunge.model.selfelec.VDatBaseresource;
import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBaseresourceelectricmeter;
import com.xunge.model.selfelec.VEleBillaccount;
import com.xunge.model.selfelec.VEleBillaccountbaseresource;
import com.xunge.model.selfelec.VEleBillaccountcontract;
import com.xunge.model.selfelec.VEleContract;

public interface IElecBillAccountService {
	/**
	 * 根据条件查询报账点分页列表
	 * @param userLoginInfo
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillaccount> queryReimburseInfo(UserLoginInfo userLoginInfo ,VEleBillaccount param,int pageNumber,int pageSize);
	/**
	 * 报账点审核页面查询
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author xup
	 */
	public PageInfo<EleBillaccount> queryEleBillaccount(Map<String,Object> map,int pageNumber,int pageSize);
	
	/**
	 * 根据查询条件 获取 所有数据
	 * @author  yangju
	 * @param param
	 * @return
	 */
	public List<VEleBillaccount> queryReimburseInfoForExport(UserLoginInfo userLoginInfo ,VEleBillaccount param);
	
	/**
	 * 新增 、修改报账点信息
	 * @param account
	 * @return
	 */
	public int saveOrUpdateEleAccount(EleBillaccount account,UserLoginInfo loginUser);
	
	/**
	 * 更细审核状态
	 * @param ids
	 * @param userId
	 * @param state
	 * @return
	 */
	public int reviewEleAccount(List<VEleBillaccount> ids , String userId , Integer state);
	
	/**
	 * 修改流程状态
	 * @param billaccountId
	 * @param state
	 * @return
	 */
	public int updateActivityCommit(List<Map<String,Object>> ids,UserLoginInfo user);
	
	/**
	 * 审核流程中的数据并进行修改
	 * @param ids
	 * @param user
	 * @return
	 * @author xup
	 */
	public int updateAuditCompelet(List<Map<String,Object>> ids,UserLoginInfo user);
	
	
	/**
	 * 根据视图选中的id 删除报账点
	 * @param ids
	 * @return
	 */
	public int deleteEleAccount(List<VEleBillaccount> ids);
	
	/**
	 * 保存、更新合同关联
	 * @param account
	 * @return
	 */
	public int saveOrUpdateRelationContract(EleContractbillaccount contract);
	
//	/**
//	 * 取消合同关联
//	 * @param contract
//	 * @return
//	 */
//	public int cancelRelationContract(EleContractbillaccount contract);
	
	/**
	 * 保存、更新资源关联
	 * @param account
	 * @return
	 */
	public int saveOrUpdateRelationResource(EleBillaccountbaseresource resource);
	
	/**
	 * 保存、更新电表关联
	 * @param account
	 * @return
	 */
	public int saveOrUpdateElectricmeterContract(EleBaseresourceelectricmeter electricmeter);
	
	/**
	 * 查询未关联电费合同信息
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleContract> queryUnrelationContractInfo(VEleBillaccountcontract param,int pageNumber,int pageSize);
	
	/**
	 * 查询已关联合同信息
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillaccountcontract> queryRelationedContractInfo(VEleBillaccountcontract param,int pageNumber,int pageSize);
	
	/**
	 * 查询未关联电费机房资源信息
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VDatBaseresource> queryUnrelationResourceInfo(VEleBillaccountbaseresource param,int pageNumber,int pageSize);
	
	/**
	 * 查询  资源 电表 树列表
	 * @param resource
	 * @return
	 */
	public List<Map<String , Object>> queryResourceTree(VEleBillaccountbaseresource resource);
	
	/**
	 * 查询  资源 电表 树列表
	 * @param resource
	 * @return
	 */
	public PageInfo<Map<String, Object>> queryResourceMeterTree(VEleBillaccountbaseresource param, int pageNumber,
			int pageSize);
	
	/**
	 * 查询已关联电费机房资源信息
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillaccountbaseresource> queryRelationedResourceInfo(VEleBillaccountbaseresource param,int pageNumber,int pageSize);
	
	/**
	 * 查询未关联电表信息
	 * @param resource
	 * @return
	 */
	public PageInfo<VDatElectricmeter> queryUnrelationElectricmeterInfo(VEleBillaccountbaseresource resource,int pageNumber,int pageSize);
	
	/**
	 * 查询已关联电表信息
	 * @param resource
	 * @return
	 */
	public List<VEleBaseresourceelectricmeter> queryRelationedElectricmeterInfo(VEleBillaccountbaseresource resource);
	
	/**
	 * 根据报账点查询 资源关系图
	 * @author  yangju
	 * @param billaccountId
	 * @return
	 */
	public List<Map<String , Object>> selectResourceRelations(String billaccountId);
	
	/**
	 * @description 根据code查询报账点
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	public List<VEleBillaccount> checkBillaccountCode(Map<String,Object> map);
	/**
	 * 根据报账点id查询报账点信息
	 * @param map
	 * @return
	 * @author xup
	 */
	public List<EleBillaccount> queryBillaccountById(Map<String,Object> map);
	
	/**
	 * 批量添加
	 * @author wangz
	 * @param file
	 * @param suffix
	 * @param request
	 * @return
	 */
	public Map<String,Object> insertExcelData(MultipartFile file,String suffix,HttpServletRequest request)  throws Exception;
	
	/**
	 * 获取最大编码code值
	 * @author wangz
	 * @param loginUser
	 * @return
	 */
	public String queryMaxCode(UserLoginInfo loginUser,String seqName,String seqCode);
	 /**
     * 根据报账点id查询合同状态
     * @param billaccountId
     * @return
     */
	public  List<VEleBillaccountcontract> queryStateByBillaccountId(String billaccountId);
	 /**
     * 查询报账点总数
     * @author SongJL
     * @return
     */
	public Map<String, Object> queryBillaccountNum(Map<String, Object> map);
	
	 /**
     * 根据报账点id更新审核状态
     * @param map
     * 
     */
	public void updateById(Map<String, Object> map);
	/**
	 * 查询历史标杆值（补算）
	 * @param billaccount
	 * @param regId
	 * @return
	 * @author xup
	 */
	public List<DatBaseresourceVO> queryBenchmark(String billaccount,String regId);
}
 