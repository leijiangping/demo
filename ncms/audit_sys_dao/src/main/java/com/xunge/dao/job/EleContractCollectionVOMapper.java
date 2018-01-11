package com.xunge.dao.job;

import java.util.List;

import com.xunge.model.job.EleContractCollectionVO;
import com.xunge.model.selfelec.EleContract;

public interface EleContractCollectionVOMapper {
    int deleteByPrimaryKey(String elecontractId);

    int insert(EleContractCollectionVO record);

    int insertSelective(EleContractCollectionVO record);

    EleContractCollectionVO selectByPrimaryKey(String elecontractId);

    int updateByPrimaryKeySelective(EleContractCollectionVO record);

    int updateByPrimaryKey(EleContractCollectionVO record);
    
    boolean batchInsertColl(List<EleContractCollectionVO> eleContractList);
    
	boolean batchInsert(List<EleContract> record);
    
    boolean batchUpdate(List<EleContract> record);
}