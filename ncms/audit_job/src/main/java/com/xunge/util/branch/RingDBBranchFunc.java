package com.xunge.util.branch;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.ring.MeterPerfVOMapper;
import com.xunge.dao.basedata.ring.PowerPerfVOMapper;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;

@Service
@SuppressWarnings({ "unchecked" })
public class RingDBBranchFunc {
	
	
	@Resource
	private MeterPerfVOMapper meterPerfVOMapper;
	
	@Resource
	private PowerPerfVOMapper powerPerfVOMapper;
	
	public List<?> list;

	public void invoke(Method method, List<?> list) throws Exception {
		this.setList(list);
		method.invoke(this);
	}
	
	public boolean meterPerfBatchInsert(){
		if(this.getList().size()<=0)return true;
		return meterPerfVOMapper.batchInsert((List<MeterPerfVO>) this.getList());
	}
	
	public boolean meterPerfBatchUpdate(){
		if(this.getList().size()<=0)return true;
		return meterPerfVOMapper.batchUpdate((List<MeterPerfVO>) this.getList());
	}
	
	public boolean powerPerfBatchInsert(){
		if(this.getList().size()<=0)return true;
		return powerPerfVOMapper.batchInsert((List<PowerPerfVO>) this.getList());
	}
	
	public boolean powerPerfBatchUpdate(){
		if(this.getList().size()<=0)return true;
		return powerPerfVOMapper.batchUpdate((List<PowerPerfVO>) this.getList());
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
