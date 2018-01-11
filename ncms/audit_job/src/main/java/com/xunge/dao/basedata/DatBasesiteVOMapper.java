package com.xunge.dao.basedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.DatBasesiteVO;

public interface DatBasesiteVOMapper {
    int countByExample(DatBasesiteVOExample example);

    int deleteByExample(DatBasesiteVOExample example);

    int deleteByPrimaryKey(String basesiteId);

    int insert(DatBasesiteVO record);

    int insertSelective(DatBasesiteVO record);

    List<DatBasesiteVO> selectByExample(DatBasesiteVOExample example);

    DatBasesiteVO selectByPrimaryKey(String basesiteId);

    int updateByExampleSelective(@Param("record") DatBasesiteVO record, @Param("example") DatBasesiteVOExample example);

    int updateByExample(@Param("record") DatBasesiteVO record, @Param("example") DatBasesiteVOExample example);

    int updateByPrimaryKeySelective(DatBasesiteVO record);

    int updateByPrimaryKey(DatBasesiteVO record);
    
    boolean batchInsert(List<DatBasesiteVO> datas);
    
	boolean delByCuidsAndPrvid(Map<String,Object> map);
	
	boolean batchUpdate(List<DatBasesiteVO> datas);
	
	 /**
     * 查询站点信息审核页面
     * @param hashMaps
     * @return
     * @author xup
     */
	public List<DatBasesiteVO> querySiteInfo(Map<String,Object> hashMaps);
	
	/**
	 * 编码校验
	 * @author wangz
	 * @param map
	 * @return
	 */
	public List<DatBasesiteVO> checkByCode(Map<String,Object> map);
}