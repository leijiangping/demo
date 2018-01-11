package com.xunge.service.twrrent.punish;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;

/**
 * @Description 地市扣罚逻辑处理
 * @author zhujj
 * @version 2017-07-20 
 */
public interface ITwrRegPunishService {
	/**
	 * @description 根据ID编码物理删除
	 * @param twrRegPunishId
	 * @return
	 */
    public int deleteByPrimaryKey(List<String> list);
    /**
     * @description 保存地市扣罚表数据
     * @param record
     * @return
     */
    public int insertTwrRegPunishVO(TwrRegPunishVO record);
    
	/**
	 * @description 批量保存城市扣罚数据
	 * @param record
	 * @return
	 */
	public int insertBatchTwrRegPunishVO(List<TwrRegPunishVO> record);
    /**
     * @description 保存地市扣罚表不为空的数据
     * @param record
     * @return
     */
    public int insertSelective(TwrRegPunishVO record);
    /**
     * @description 根据ID编码查询地方扣罚信息
     * @param twrRegPunishId
     * @return
     */
    public TwrRegPunishVO selectByPrimaryKey(String twrRegPunishId);
    /**
     * @description 获取单条数据
     * @param TwrRegPunishVO
     * @return TwrRegPunishVO
     */
    public List<TwrRegPunishVO> selectByTwrRegPunish(TwrRegPunishVO vo);
    /**
     * @description 修改地市扣罚表不为空的数据
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(TwrRegPunishVO record);
    /**
     * @description 修改地市扣罚表
     * @param record
     * @return
     */
    public int updateByPrimaryKey(TwrRegPunishVO record);
    /**
     * @description 根据编码修改地市扣罚表状态
     * @param record
     * @return
     */
    public int updateStateByPrimaryKey(TwrRegPunishVO record);
    /**
     * @description 根据编码修改地市扣罚表审核状态
     * @param record
     * @return
     */
    public int updateAuditStateByPrimaryKey(TwrRegPunishVO record);

    /**
     * @description 根据编码修改汇总ID
     * @param record
     * @return
     */
    public int updateAccountsummaryIDByPrimaryKey(TwrRegPunishVO record);

    /**
     * @description 根据编码集合批量修改汇总ID
     * @param record
     * @return
     */
    public int updateAccountsummaryIDByBatchID(List<TwrRegPunishVO> record);

    /**
     * @description 提交审核，启动流程并更新审核状态为审核中
     * @param ids 地市扣罚编码集合
     * @return
     */
    public int updateAuditAndStartFlow(List<Map<String,Object>> ids,UserLoginInfo user);

    /**
     * @description 审核并提交
     * @param ids 地市扣罚编码集合
     * @return
     */
    public int updateAuditCompelet(List<Map<String,Object>> ids,UserLoginInfo user);

    /**
     * @description 分页查询地方扣罚表
     * @param twrRegPunishId
     * @author zhujj
     * @return
     */
    public Page<TwrRegPunishVO> selectTwrRegPunishPage(Map<String,Object> map);

    /**
     * @description 查询地方扣罚表集合
     * @param twrRegPunishId
     * @return
     */
    public List<TwrRegPunishVO> selectTwrRegPunishList(Map<String,Object> map);

    /**
     * @description 地方扣罚表集合导出
     * @param twrRegPunishId
     * @return
     */
    public boolean exportExcelData(Map<String,Object> map,HttpServletRequest request,HttpServletResponse response);

    /**
     * @description 查询地方扣罚表集合导出-导入模板
     * @param twrRegPunishId
     * @return
     */
    public boolean exportTemplate(Map<String,Object> map,HttpServletRequest request,HttpServletResponse response);
    /**
	 * @description 导入数据
	 * @param file 文件流
	 * @param suffix 标识,为了防止重复
	 * @param response
	 * @throws Exception
	 * @return int 导入数据总量
	 */
	public Map<String, Object> insertExcelData(MultipartFile file,String suffix,HttpServletRequest request) throws Exception;
	
	/**
	 * @description 根据参数查询地市指标扣罚
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryTwrRegPunishMapListByCondition(Map<String, Object> params);

    
}