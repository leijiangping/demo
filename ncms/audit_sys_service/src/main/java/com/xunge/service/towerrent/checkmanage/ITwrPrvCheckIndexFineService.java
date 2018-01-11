package com.xunge.service.towerrent.checkmanage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.checkmanage.TwrPrvCheckIndexFineVO;
/**
 * 
 * @author jiacy
 * @date 2017年7月19日
 * @description 考核指标扣罚Service
 */
public interface ITwrPrvCheckIndexFineService {
	/**
	 * 查询所有考核指标扣罚对象
	 * @author jiacy
	 * @param paraMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrPrvCheckIndexFineVO>> queryrAllPrvCheckIndexFineVO(Map<String,Object> paramMap, int pageSize, int pageNum);
	/**
	 * 查询所有考核指标扣罚
	 * @author jiacy
	 * @return
	 */
	public TwrPrvCheckIndexFineVO queryCheckIndexFineVOById(String id);
	/**
	 * 删除考核指标扣罚
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	public String deleteTwrById(List<String> ids); 
	/**
	 * 修改考核指标扣罚
	 * @author jiacy
	 * @param twrPrvCheckIndexFine
	 * @return
	 */
	public String updateTwrById(TwrPrvCheckIndexFineVO twrPrvCheckIndexFine); 
	/**
	 * 新增考核指标扣罚
	 * @author jiacy
	 * @param twoProductVO
	 * @return
	 */
	public String insertTwrById(TwrPrvCheckIndexFineVO twoProductVO); 
	
	/**
	 * 考核指标扣罚提交审核
	 * @author jiacy
	 * @param twoProductVO
	 * @return
	 */
	public String checkTwrById(List<String> ids); 
	
	/**
	 * 考核指标扣罚导出
	 * @author jiacy
	 * @param twoProductVO
	 * @return
	 */
	public List<TwrPrvCheckIndexFineVO> queryExportList(Map<String,Object> prvCheckIdList); 
	/**
	 * 考核指标审核页面查询
	 * @author jiacy
	 * @param twoProductVO
	 * @return
	 */
	public Page<TwrPrvCheckIndexFineVO> queryTwrRentInformationCheck(Map<String,Object> paramMap,int pageNum,int pageSize);
	
	/**
	 * 根据参数查询省级指标扣罚Map集合
	 * @author jiacy
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryTwrPrvCheckIndexFineMapList(Map<String, Object> params);
    /**
	 * 导入数据
	 * @author jiacy
	 * @param file 文件流
	 * @param suffix 标识,为了防止重复
	 * @param response
	 * @throws Exception
	 * @return int 导入数据总量
	 */
	public Map<String, Object> insertExcelData(MultipartFile file,String suffix,HttpServletRequest request) throws Exception;
}
