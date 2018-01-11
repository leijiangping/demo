package com.xunge.dao.basedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;

public interface DatBaseresourceVOMapper {
    int countByExample(DatBaseresourceVOExample example);

    int deleteByExample(DatBaseresourceVOExample example);

    int deleteByPrimaryKey(String baseresourceId);

    int insert(DatBaseresourceVO record);

    int insertSelective(DatBaseresourceVO record);

    List<DatBaseresourceVO> selectByExample(DatBaseresourceVOExample example);
    
    /**
     * @description 根据站点Id  查找 资源点信息 
     * @author yuefy
     * @date 创建时间：2017年8月7日
     */
    List<DatBaseresourceVO> getDatBaseresourceById(String basesiteId);

    DatBaseresourceVO selectByPrimaryKey(String baseresourceId);

    int updateByExampleSelective(@Param("record") DatBaseresourceVO record, @Param("example") DatBaseresourceVOExample example);

    int updateByExample(@Param("record") DatBaseresourceVO record, @Param("example") DatBaseresourceVOExample example);

    int updateByPrimaryKeySelective(DatBaseresourceVO record);

    int updateByPrimaryKey(DatBaseresourceVO record);
    
    boolean batchInsert(List<DatBaseresourceVO> datas);
    
	boolean delByCuidsAndPrvid(Map<String,Object> map);
	
	boolean batchUpdate(List<DatBaseresourceVO> datas);
	
	boolean batchUpdateForAudit(List<DatBaseresourceVO> datas);
	
	 /**
     * 查询资源点信息审核页面
     * @param hashMaps
     * @return
     * @author xup
     */
	public List<DatBaseresourceVO> queryDatBaseresource(Map<String,Object> hashMaps);
}