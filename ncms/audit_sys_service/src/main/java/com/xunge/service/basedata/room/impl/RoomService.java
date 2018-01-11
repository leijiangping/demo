package com.xunge.service.basedata.room.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBaseresourceVOExample.Criteria;
import com.xunge.model.basedata.vo.RoomQueryVO;
import com.xunge.service.basedata.room.IRoomService;

@Service
public class RoomService implements IRoomService{

	@Resource
	private DatBaseresourceVOMapper mapper;
	
	@Override
	public Page<DatBaseresourceVO> getAll(RoomQueryVO vo) {
		PageInterceptor.startPage(vo.getCur_page_num(), vo.getPage_count());
		Page page = PageInterceptor.endPage();
		DatBaseresourceVOExample example = new DatBaseresourceVOExample();
		Criteria criteria1 = example.createCriteria();
		Criteria criteria2 = example.createCriteria();
		criteria1.andBaseresourceTypeEqualTo(0);
		criteria2.andBaseresourceTypeEqualTo(0);
		// 权限过滤
		if (!StringUtils.isEmpty(vo.getLoginUser().getPrv_id())) {
			criteria1.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
			criteria2.andPrvIdEqualTo(vo.getLoginUser().getPrv_id());
		}
		if (!vo.getLoginUser().getPreg_ids().isEmpty()) {
			criteria1.andPregIdIn(vo.getLoginUser().getPreg_ids());
			criteria2.andPregIdIn(vo.getLoginUser().getPreg_ids());
		}
		if (!vo.getLoginUser().getReg_ids().isEmpty()) {
			criteria1.andRegIdIn(vo.getLoginUser().getReg_ids());
			criteria2.andRegIdIn(vo.getLoginUser().getReg_ids());
		}
		//TODO 查询条件
		if(!StringUtils.isEmpty(vo.getCity())){
			criteria1.andPregIdEqualTo(vo.getCity());
			criteria2.andPregIdEqualTo(vo.getCity());
		}
		if(!StringUtils.isEmpty(vo.getRegion())){ 
			criteria1.andRegIdEqualTo(vo.getRegion());
			criteria2.andRegIdEqualTo(vo.getRegion());
		}
		if(!StringUtils.isEmpty(vo.getRoomReg())){
			criteria1.andBaseresourceCodeLike(PromptMessageComm.PERCENT_SIGN+vo.getRoomReg()+PromptMessageComm.PERCENT_SIGN);
			criteria2.andBaseresourceNameLike(PromptMessageComm.PERCENT_SIGN+vo.getRoomReg()+PromptMessageComm.PERCENT_SIGN);
		}
		if(!StringUtils.isEmpty(vo.getDataFrom())){
			criteria1.andDataFromEqualTo(vo.getDataFrom());
			criteria2.andDataFromEqualTo(vo.getDataFrom());
		}
		// 页面查询条件补充
		if(!StringUtils.isEmpty(vo.getAuditStatus())){
			criteria1.andAuditingStateEqualTo(Integer.parseInt(vo.getAuditStatus()));
			criteria2.andAuditingStateEqualTo(Integer.parseInt(vo.getAuditStatus()));
		}
		
		if(!StringUtils.isEmpty(vo.getProperty())){
			criteria1.andRoomOwnerEqualTo(Integer.parseInt(vo.getProperty()));
			criteria2.andRoomOwnerEqualTo(Integer.parseInt(vo.getProperty()));
		}
		if(vo.getStatus() != null){
			criteria1.andBaseresourceStateEqualTo(Integer.parseInt(vo.getStatus()));
			criteria2.andBaseresourceStateEqualTo(Integer.parseInt(vo.getStatus()));
		}
		example.or(criteria2);
		page.setResult(mapper.selectByExample(example));
		return page;
	}

	@Override
	public DatBaseresourceVO get(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int insert(DatBaseresourceVO record) {
		return mapper.insert(record);
	}

	@Override
	public int update(DatBaseresourceVO record) {
//		DatBaseresourceVO vo = get(record.getBaseresourceId());
//		BeanUtils.copyProperties(record, vo);
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public boolean updateBatch(List<DatBaseresourceVO> list) {
		return mapper.batchUpdate(list);
	}

	@Override
	public int delete(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatch(List<DatBaseresourceVO> list) {
		for(DatBaseresourceVO vo : list){
			mapper.deleteByPrimaryKey(vo.getBaseresourceId());
		}
		return Integer.parseInt(RESULT.SUCCESS_1);
	}
}
