package com.xunge.dao.twrrent.punish;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;

/**
 * @description 地市扣罚表
 * @author zhujj
 * @version 2017-07-20
 */
public interface ITwrRegPunishDao {
	/**
	 * @description 根据ID编码物理删除
	 * @param twrRegPunishId
	 * @return
	 * @author zhujj
	 */
    public int deleteByPrimaryKey(List<String> list);
    /**
     * @description 保存地市扣罚表数据
     * @param record
     * @return
	 * @author zhujj
     */
    public int insertTwrRegPunishVO(TwrRegPunishVO record);
    
	/**
	 * @description 批量保存城市扣罚数据
	 * @param record
	 * @return
	 * @author zhujj
	 */
	public int insertBatchTwrRegPunishVO(List<TwrRegPunishVO> record);
    /**
     * @description 保存地市扣罚表不为空的数据
     * @param record
     * @return
	 * @author zhujj
     */
    public int insertSelective(TwrRegPunishVO record);
    /**
     * @description 根据ID编码查询地方扣罚信息
     * @param twrRegPunishId
     * @return
	 * @author zhujj
     */
    public TwrRegPunishVO selectByPrimaryKey(String twrRegPunishId);
    /**
     * @description 查询数据
     * @param twrRegPunish
     * @return
	 * @author zhujj
     */
    public List<TwrRegPunishVO> selectByTwrRegPunish(TwrRegPunishVO twrRegPunish);
    
    /**
     * @description 修改地市扣罚表不为空的数据
     * @param record
     * @return
	 * @author zhujj
     */
    public int updateByPrimaryKeySelective(TwrRegPunishVO record);
    /**
     * @description 修改地市扣罚表
     * @param record
     * @return
	 * @author zhujj
     */
    public int updateByPrimaryKey(TwrRegPunishVO record);
    /**
     * @description 根据编码修改地市扣罚表状态
     * @param record
     * @return
	 * @author zhujj
     */
    public int updateStateByPrimaryKey(TwrRegPunishVO record);
    /**
     * @description 根据编码修改地市扣罚表审核状态
     * @param record
     * @return
	 * @author zhujj
     */
    public int updateAuditStateByPrimaryKey(TwrRegPunishVO record);

    /**
     * @description 根据编码修改汇总ID
     * @param record
     * @return
	 * @author zhujj
     */
    public int updateAccountsummaryIDByPrimaryKey(TwrRegPunishVO record);

    /**
     * @description 根据编码集合批量修改
     * @param record
     * @return
	 * @author zhujj
     */
    public int updateAccountsummaryIDByBatchID(List<TwrRegPunishVO> record);

    /**
     * @description 分页查询地方扣罚表
     * @param twrRegPunishId
     * @return
	 * @author zhujj
     */
    public Page<TwrRegPunishVO> selectTwrRegPunishPage(Map<String,Object> map);
    /**
     * @description 查询地方扣罚集合
     * @param map
     * @return
	 * @author zhujj
     */
	public List<TwrRegPunishVO> selectTwrRegPunishList(Map<String, Object> map);
	
	/**
	 * @description 根据参数查询地市指标扣罚
	 * @param params
	 * @return
	 * @author zhujj
	 */
	List<Map<String, Object>> queryTwrRegPunishMapListByCondition(Map<String, Object> params);
    
}