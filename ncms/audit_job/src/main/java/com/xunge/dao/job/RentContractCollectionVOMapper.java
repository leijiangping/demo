package com.xunge.dao.job;

import java.util.List;

import com.xunge.model.job.RentContractCollectionVO;
import com.xunge.model.selfrent.RentContractVO;

public interface RentContractCollectionVOMapper {
    int deleteByPrimaryKey(String rentcontractId);

    int insert(RentContractCollectionVO record);

    int insertSelective(RentContractCollectionVO record);

    RentContractCollectionVO selectByPrimaryKey(String rentcontractId);

    int updateByPrimaryKeySelective(RentContractCollectionVO record);

    int updateByPrimaryKey(RentContractCollectionVO record);
    
    boolean batchInsertColl(List<RentContractCollectionVO> rentContractList);
	/**
	 * 批量新增房租合同
	 * @author SongJL
	 * @param paraMap
	 * @return
	 */
	public boolean batchInsert(List<RentContractVO> record);
	/**
	 * 批量更新房租合同
	 * @author SongJL
	 * @param paraMap
	 * @return
	 */
	public boolean batchUpdate(List<RentContractVO> record);
}