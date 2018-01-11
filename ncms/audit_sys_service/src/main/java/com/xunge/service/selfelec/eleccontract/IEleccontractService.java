package com.xunge.service.selfelec.eleccontract;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.basedata.DatAttachment;
import com.xunge.model.selfelec.VEleContract;
import com.xunge.model.selfelec.VEleContractCuring;
import com.xunge.model.selfelec.vo.ElecContractQueryVO;

public interface IEleccontractService {

	public PageInfo<VEleContract> findAllVEleContract(ElecContractQueryVO vo,Map<String,Object> map);
	
	public int add(VEleContract record,UserLoginInfo loginUser);
	
	public int modify(VEleContract record);
	
	public int remove(VEleContract elecontract);
	
	public String export(ElecContractQueryVO vo, String path, HttpServletRequest request, HttpServletResponse response,UserLoginInfo loginUser,Integer activityFlag);
	/**
	 * 查看合同对应的的业务附件
	 * @param record
	 * @return
	 */
	public PageInfo<DatAttachment> selectByBusiness(DatAttachment record);
	public String addAttach(DatAttachment record);
	
	public void delAttach(String url);
	
	public int submitAudit(List<Map<String,Object>> list,UserLoginInfo loginUser);
	
	public int audit(List<Map<String, Object>> list, UserLoginInfo loginInfo);
	
	public PageInfo<VEleContract> queryAllContract(Map<String,Object> paramMap);
	
	public VEleContract queryByPrimaryKey(String elecContractId);
	
	public int updateContractVO(Map<String,Object> paramMap);
	
	public VEleContract queryOneElecContractById(String elecContractId);

	/**
	 * @description 查询电费合同到期预警
	 * @author yuefy
	 * @date 创建时间：2017年9月7日
	 */
	public PageInfo<VEleContract> queryEleccontractWarningList(Map<String, Object> map);

	/**
	 * @description 查询报账点和合同关联表信息
	 * @author yuefy
	 * @date 创建时间：2017年9月8日
	 */
	public List<String> queryBillaccountContract(Map<String, Object> map);

	/**
	 * @description 查询合同信息
	 * @author yuefy
	 * @date 创建时间：2017年9月8日
	 */
	public List<VEleContract> queryAllEleContract(Map<String, Object> paramMap);
	
	/**
	 * @description 查询固化信息信息
	 * @author yuefy
	 * @date 创建时间：2017年9月8日
	 */
	public PageInfo<VEleContractCuring> queryAllEleContractCuring(Map<String, Object> paramMap);
	
	/**
	 * 根据省份、用户权限汇总各地区合同数量
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> queryAllRegionContractNum(Map<String, Object> paramMap);
	/**
	 * 未关联合同占用率
	 * @param paramMap
	 * @return
	 */
	public Map<String,Object> queryNoLinkContract(Map<String, Object> paramMap);

	/**
	 * @description 批量修改合同状态
	 * @author yuefy
	 * @date 创建时间：2017年10月19日
	 */
	boolean updateContractState(Map<String, Object> paramMap);
}
