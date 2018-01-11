package com.xunge.service.basedata.room;

import java.util.List;

import com.xunge.core.page.Page;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.vo.RoomQueryVO;

public interface IRoomService {
	
	Page<DatBaseresourceVO> getAll(RoomQueryVO vo);
	
	DatBaseresourceVO get(String id);
	
	int insert(DatBaseresourceVO record);
		
	int update(DatBaseresourceVO record);
		
	boolean updateBatch(List<DatBaseresourceVO> list);
		
	int delete(String id);
	
	int deleteBatch(List<DatBaseresourceVO> list);
}
