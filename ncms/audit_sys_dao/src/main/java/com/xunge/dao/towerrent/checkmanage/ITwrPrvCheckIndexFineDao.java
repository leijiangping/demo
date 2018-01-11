package com.xunge.dao.towerrent.checkmanage;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.checkmanage.TwrPrvCheckIndexFineVO;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;
/**
 * 
 * @author jcy
 * @date 2017年7月19日
 * @description 考核指标扣罚Dao
 */
public interface ITwrPrvCheckIndexFineDao {
	/**
	 * 查询所有考核指标扣罚对象
	 * @param paraMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrPrvCheckIndexFineVO>> queryrAllPrvCheckIndexFineVO(Map<String,Object> paramMap, int pageSize, int pageNum);
	/**
	 * 查询所有考核指标扣罚
	 * @return
	 */
	public TwrPrvCheckIndexFineVO queryCheckIndexFineVOById(String id);
	/**
	 * 删除考核指标扣罚
	 * @param ids
	 * @return
	 */
	public String deleteTwrById(List<String> ids); 
	/**
	 * 修改考核指标扣罚
	 * @param paramMap
	 * @return
	 */
	public String updateTwrById(TwrPrvCheckIndexFineVO twoProductVO); 
	/**
	 * 新增考核指标扣罚
	 * @param twoProductVO
	 * @return
	 */
	public String insertTwrById(TwrPrvCheckIndexFineVO twoProductVO); 
	
	/**
	 * 考核指标扣罚提交审核
	 * @param twoProductVO
	 * @return
	 */
	public String checkTwrById(List<String> ids); 
	
	/**
	 * 考核指标扣罚导出
	 * @param twoProductVO
	 * @return
	 */
	public List<TwrPrvCheckIndexFineVO> queryExportList(Map<String,Object> prvCheckIdList); 
	
	/**
	 * 扣罚审核查询
	 * @param paramMap
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<TwrPrvCheckIndexFineVO> queryTwrRentInformationCheck(Map<String, Object> paramMap, int pageNum, int pageSize);
	
	/**
	 * 根据参数查询省级指标扣罚Map集合
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryTwrPrvCheckIndexFineMapList(Map<String, Object> params);
	
	/**
	 * 批量保存省内扣罚数据
	 * @param record
	 * @return
	 */
	public int insertBatchTwrRegPunishVO(List<TwrPrvCheckIndexFineVO> record);
	 /**
     * 
     * @param map
     * @return
     */
	public List<TwrPrvCheckIndexFineVO> selectTwrRegPunishList(Map<String, Object> map);
}
