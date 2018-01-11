package com.xunge.service.selfrent.contract;

import java.util.List;
import java.util.Map;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.selfrent.contract.DatContractVO;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;
/**
 * @description 房租合同维护service接口
 * @author yuefy
 * @date 创建时间：2017年8月2日
 */
public interface ISelfRentService {
	/**
	 * @description 分页查询所有主合同信息
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public Page<RentContractVO> queryRentContractVO(Map<String,Object> paraMap,
			int pageNumber,int pageSize) ;
	
	/**
	 * @description 查询所有主合同信息
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public List<RentContractVO> queryRentContractVO(Map<String,Object> paraMap) ;
	
	/**
	 * @description 分页查询所有主合同信息  以及合同终期 计划缴费日期进行判断
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public Page<RentContractVO> queryRentContractVORentWarning(Map<String,Object> paraMap,
			int pageNumber,int pageSize) ;
	/**
	 * @description 根据省份查询供应商
	 */
	public List<DatSupplierVO> queryDatSupplierByPrvID(Map<String, Object> paraMap);
	
	/**
	 * @description 分页查询供应商信息
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public Page<List<DatSupplierVO>> queryDatSupplierByPrvID(Map<String,Object> paraMap,
			int pageNumber,int pageSize) ;
	
	/**
	 * @description 根据房租合同id查询房租合同对象、主合同对象、供应商对象
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public RentContractVO queryAllOfContract(String rentcontractId); 
	/**
	 * 
	 * @description 新增、续签合同
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public int insertRentContract(Map<String,Object> map);
	/**
	 * @description 修改、补录合同
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public String updateRentContract(Map<String,Object> map);
	/**
	 * 补录页面提交审批
	 */
	public int updateCommit(List<Map<String,Object>> list, UserLoginInfo user);

	/**
	 * 
	 * @description 判断合同代码唯一性
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public List<DatContractVO> checkContractCode(Map<String, Object> map);

	/**
	 * @description 删除合同
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public String deleteContract(Map<String, Object> map);

	/**
	 * @description 终止合同
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public String stopContract(Map<String, Object> map);
	
	/**
	 * @description 查询合同最后缴费日期
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public List<String> queryRentContractEndDate(Map<String,Object> paraMap);

	/**
	 * @description 合同流程审核
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public int rentContractCheckAudit(List<Map<String, Object>> ids,
			UserLoginInfo loginUser);
	
	/**
	 * 地区下房租合同总数查询
	 * @param ids
	 * @param loginUser
	 * @return
	 */
	public List<Map<String, String>> queryAllRegionRentContractNumRedis(Map<String,Object> paraMap);

	/**
	 * @description 查询租费合同预警信息
	 * @author yuefy
	 * @date 创建时间：2017年9月25日
	 */
	public Page<RentContractVO> queryRentContractVOWarning(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
}
