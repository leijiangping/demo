package com.xunge.dao.basedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.DatSupplierVO;

public interface DatSupplierVOMapper {
    int countByExample(DatSupplierVOExample example);

    int deleteByExample(DatSupplierVOExample example);

    int deleteByPrimaryKey(String supplierId);

    int insert(DatSupplierVO record);

    int insertSelective(DatSupplierVO record);

    List<DatSupplierVO> selectByExample(DatSupplierVOExample example);

    DatSupplierVO selectByPrimaryKey(String supplierId);
    
    DatSupplierVO selectByParam(DatSupplierVO datSupplierVO);
    
    List<String> selectByIdlist(List<String> ids);

    int updateByExampleSelective(@Param("record") DatSupplierVO record, @Param("example") DatSupplierVOExample example);

    int updateByExample(@Param("record") DatSupplierVO record, @Param("example") DatSupplierVOExample example);

    int updateByPrimaryKeySelective(DatSupplierVO record);

    int updateByPrimaryKey(DatSupplierVO record);

    boolean batchInsert(List<DatSupplierVO> record);

    boolean batchUpdate(List<DatSupplierVO> record);
    
    List<DatSupplierVO> querySupplierVO(Map<String,Object> paramMap);
    
    List<Map<String, String>> selectRedisComStringByPrvId(String prv_id);
}