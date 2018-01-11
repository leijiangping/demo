package com.xunge.dao.job;

import java.util.List;
import java.util.Map;

import com.xunge.model.job.DatContractCollectionVO;

public interface DatContractCollectionVOMapper {
    int deleteByPrimaryKey(String contractId);

    int insert(DatContractCollectionVO record);

    int insertSelective(DatContractCollectionVO record);

    DatContractCollectionVO selectByPrimaryKey(String contractId);

    int updateByPrimaryKeySelective(DatContractCollectionVO record);

    int updateByPrimaryKey(DatContractCollectionVO record);
    
    boolean batchInsert(List<DatContractCollectionVO> mainContractList);
    
    Map<String,Integer> syncContractData(Map<String, Object> param);
}