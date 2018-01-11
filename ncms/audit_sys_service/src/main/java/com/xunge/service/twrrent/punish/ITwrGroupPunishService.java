package com.xunge.service.twrrent.punish;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;


/**
 * 集团既定考核指标扣罚
 * @author changwq
 *
 */
public interface ITwrGroupPunishService {
	public Page<List<TwrGroupPunishVO>> queryGroupPunishVO(
			Map<String,Object> paraMap,int pageNumber,int pageSize);
	/**
	 * 删除集团既定扣罚信息
	 * @param twrGroupPunishId
	 * @return
	 */
	public String deleteGroupPunish(String twrGroupPunishId);
	/**
	 * 集团既定扣罚信息导入
	 * @param file
	 * @param suffix
	 * @param request
	 * @param paraMap
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> importGroupPunish(MultipartFile file,String suffix,
			HttpServletRequest request, Map<String, Object> paraMap) throws Exception;
	/**
	 * 集团扣罚信息全查（不分页）
	 * @param paraMap
	 * @return
	 */
	public List<TwrGroupPunishVO> queryGroupPunish(Map<String,Object> paraMap);
	/**
	 * 根据省份id查询扣罚信息
	 * @param pregId
	 * @return
	 */
	public List<TwrGroupPunishVO> queryGroupPunishByPregId(String pregId,String punishYearMonth,String regId);
	/**
	 * 根据扣罚信息id添加罚金
	 * @param twrGroupPunishId
	 * @param punishamount
	 * @return
	 */
	public String updatePunishAmountById(String twrGroupPunishId,BigDecimal punishamount,String userId,String twrGroupRegPunishId);
	/**
	 * 计算罚金增加到各站点 并计算罚金总额
	 * @return
	 * @throws ParseException 
	 */
	public String calSumPunishAmount(String pregId,String punishYearMonth,String userId) throws ParseException;
}