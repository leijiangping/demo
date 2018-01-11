package com.xunge.util.branch;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.DatBasesiteVOMapper;
import com.xunge.dao.basedata.DatBasestationVOMapper;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.DatBasestationVO;

@Service
@SuppressWarnings({ "unchecked" })
public class ResDBBranchFunc {
	public List<?> list;

	@Resource
	private DatBasestationVOMapper datBasestationVOMapper;

	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;

	@Resource
	private DatBasesiteVOMapper datBasesiteVOMapper;

	public void invoke(Method method, List<?> list) throws Exception {
		this.setList(list);
		method.invoke(this);
	}

	public boolean datBasesiteBatchInsert() {
		if (this.getList().size() <= 0)
			return true;
		return datBasesiteVOMapper.batchInsert((List<DatBasesiteVO>) this
				.getList());
	}

	public boolean datBaseresourceBatchInsert() {
		if (this.getList().size() <= 0)
			return true;
		return datBaseresourceVOMapper
				.batchInsert((List<DatBaseresourceVO>) this.getList());
	}

	public boolean datBasestationBatchInsert() {
		if (this.getList().size() <= 0)
			return true;
		return datBasestationVOMapper.batchInsert((List<DatBasestationVO>) this
				.getList());
	}

	public boolean datBasesiteBatchUpdate() {
		if (this.getList().size() <= 0)
			return true;
		return datBasesiteVOMapper.batchUpdate((List<DatBasesiteVO>) this
				.getList());
	}

	public boolean datBaseresourceBatchUpdate() {
		if (this.getList().size() <= 0)
			return true;
		return datBaseresourceVOMapper
				.batchUpdate((List<DatBaseresourceVO>) this.getList());
	}

	public boolean datBasestationBatchUpdate() {
		if (this.getList().size() <= 0)
			return true;
		return datBasestationVOMapper.batchUpdate((List<DatBasestationVO>) this
				.getList());
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
