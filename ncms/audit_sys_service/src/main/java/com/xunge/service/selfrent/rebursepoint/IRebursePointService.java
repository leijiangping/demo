package com.xunge.service.selfrent.rebursepoint;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billAccount.RentBillAccountResourceVO;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillAccountContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.model.selfrent.resource.DatBaseResourceVO;
/**
 * 
 * @author lpw
 *
 */
public interface IRebursePointService {
	/**
	 * 查询报账点信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<List<RentBillaccountVO>> queryRembursePointInfo(Map<String, Object> pMap,int pageNumber,int pageSize);
	/**
	 * 查询用户关联区县的合同信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<RentContractVO>> queryContractAgreement(Map<String, Object> pMap, int pageNumber,
			int pageSize);
	/**
	 * 查询与用户关联区县的资源信息
	 * @param regIds
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<DatBaseResourceVO>> queryResourceInfo(Map<String, Object> pMap, int pageNumber,
			int pageSize);
	/**
	 * 插入报账点
	 * @param rentBillaccount
	 * @param rentBillAccountContract
	 * @param rentBillAccountResource
	 */
	public int insertBillAcount(RentBillaccountVO rentBillaccount,
			RentBillAccountContractVO rentBillAccountContract,
			List<RentBillAccountResourceVO> rentResourceList);
	/**
	 * 修改报账点
	 * @param billAccountId
	 * @param billAccountContractId
	 * @param billAccountResourceId
	 */
	public void updateBillAcount(RentBillaccountVO billaccount,
			String rentcontractId, String baseresourceIds);
	/**
	 * 删除报账点
	 * @param billAccountId
	 * @param billAccountContractId
	 * @param billAccountResourceId
	 */
	public int deleteBillAcount(String billAccountId);
			
	/**
	 * 根据用户选择查询房租合同信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public RentContractVO queryContractByContractId(Map<String, Object> paraMap,
			int pageNumber, int pageSize);
	/**
	 * 根据用户选择查询资源信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<DatBaseResourceVO> queryContractByResourceId(Map<String, Object> paraMap);
			
	/**
	 *根据合同id查询所有信息
	 * @param paraMap
	 * @return
	 */
	public RentContractVO queryContractById(Map<String, Object> paraMap);
	/**
	 * 通过报账点ID查询关联的资源信息
	 * @param pMap
	 * @return
	 * 2017年7月10日 lpw
	 */
	public List<DatBaseResourceVO> queryResource(Map<String, Object> pMap);
	/**
	 * 通过报账点id查询报账点信息
	 * @param paraMap
	 * @return
	 * 2017年7月10日 lpw
	 */
	public RentBillaccountVO queryBillAccountById(Map<String, Object> paraMap);
	/**
	 * 报账点提交审批
	 * @param billAccountId
	 * @param sTATE_str9
	 * @return
	 * @author xup
	 */
	public int billAccountSubmitAudit(List<Map<String,Object>> ids,UserLoginInfo user);
	
	/**
	 * 报账点流程审核
	 * @param billAccountId
	 * @param sTATE_str9
	 * @return
	 * @author xup
	 */
	public int billAccountCheckAudit(List<Map<String,Object>> ids,UserLoginInfo user);
	/**
	 * 查询待审批的报账点	
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * 2017年7月10日 lpw
	 */
	public Page<RentBillaccountVO> queryRembursePointVO(Map<String, Object> paraMap,
			int pageNumber, int pageSize);
	/**
	 * 查询供应商和合同信息
	 * @param paraMap
	 * @return
	 */
	public RentContractVO queryContractAndSupplier(Map<String, Object> paraMap);
	/**
	 * 根据报账id查询合同和供应商信息
	 * @param paraMap
	 * @return
	 */
	public RentContractVO queryContAndSupByBillId(Map<String, Object> paraMap);
	/**
	 * 查询合同是否已绑定了报账点
	 * @param rentcontractId
	 */
	public RentBillAccountContractVO queryContractBindBillacc(String rentcontractId);
	/**
	 * 查询资源点是否已绑定报账点
	 * @param resourceIds
	 * @return
	 */
	public List<RentBillAccountResourceVO> queryResourceBindBillacc(Map<String, Object> paraMap);
	/**
	 * 删除报账点绑定的资源点
	 * @param baseresourceId
	 * @return
	 * 2017年7月7日 lpw
	 */
	public int deleteResourcePoint(String baseresourceId);
	/**
	 * 根据报账点ID支付方式
	 * @param billAccountId
	 * @return
	 */
	public RentBillaccountVO queryPaymentMethod(String billAccountId);
	/**
	 * 导出报账点信息
	 * @param paramMap
	 * @param request
	 * @param response
	 * @author xup
	 * @return 
	 */
	public boolean exportRembursePointInfo(Map<String, Object> paramMap,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 导入报账点信息
	 * @param file
	 * @param suffix
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> insertExcelData(MultipartFile file,String suffix, HttpServletRequest request) throws Exception;
	
}
