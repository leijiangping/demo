package com.xunge.service.selfrent.billaccount;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billAccount.BillAccountVO;
import com.xunge.model.selfrent.billAccount.CoverAllVO;
/**
 * 报账点service 接口
 *
 */
public interface IBillAccountService {
	/**
	 * 查询报账点信息
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws ParseException 
	 */
	public Page<BillAccountVO> queryBillAccountVO(Map<String,Object> map,int pageNumber,int pageSize) throws ParseException;
	/**
	 * 查询所有报账点相关信息
	 * */
	public CoverAllVO queryAll(String billAccountId);
	/**
	 * 新增付款信息
	 * */
	public String insertAllForm(Map<String,Object> map);
	/**
	 * 修改付款信息
	 * */
	public String updateAllForm(Map<String,Object> map);
	/**
	 * 查询报账点关系结构图
	 * @param billaccountId
	 * @return
	 */
	public List<Map<String,Object>> queryBillaccountRelations(String billaccountId);
}
