package com.xunge.dao.basedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.DatContractVO;
import com.xunge.model.basedata.DatContractVOExample;

public interface DatContractVOMapper {
    int countByExample(DatContractVOExample example);

    int deleteByExample(DatContractVOExample example);

    int deleteByPrimaryKey(String contractId);

    int delByContractsysId(Map<String, Object> map);

    int insert(DatContractVO record);

    int insertSelective(DatContractVO record);

    List<DatContractVO> selectByExample(DatContractVOExample example);

    DatContractVO selectByPrimaryKey(String contractId);

    int updateByExampleSelective(@Param("record") DatContractVO record, @Param("example") DatContractVOExample example);

    int updateByExample(@Param("record") DatContractVO record, @Param("example") DatContractVOExample example);

    int updateByPrimaryKeySelective(DatContractVO record);

    int updateByPrimaryKey(DatContractVO record);
    
    boolean batchInsert(List<DatContractVO> record);
    
    boolean batchUpdate(List<DatContractVO> record);
    
    boolean updateStatus(DatContractVO record);
}