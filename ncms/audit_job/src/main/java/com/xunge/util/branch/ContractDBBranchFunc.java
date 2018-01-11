package com.xunge.util.branch;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.DatContractVOMapper;
import com.xunge.dao.basedata.DatSupplierVOMapper;
import com.xunge.dao.job.EleContractCollectionVOMapper;
import com.xunge.dao.job.RentContractCollectionVOMapper;
import com.xunge.exception.NoteException;
import com.xunge.model.basedata.DatContractVO;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.selfelec.EleContract;
import com.xunge.model.selfrent.RentContractVO;

@Service
@SuppressWarnings({"unchecked"})
public class ContractDBBranchFunc {
	
	public List<?> list;
	
	public void invoke(Method method, List<?> list)
			throws Exception{
		this.setList(list);
		method.invoke(this);
	}

	public boolean insertDataSupplier(){
		if(this.getList().size()<=0)return true;
		return datSupplierVOMapper.batchInsert((List<DatSupplierVO>) this.getList());
	}
	
	public boolean insertIntodatContract(){
		if(this.getList().size()<=0)return true;
		return datContractVOMapper.batchInsert((List<DatContractVO>) this.getList());
	}
	
	public boolean insertIntoElecContract(){
		if(this.getList().size()<=0)return true;
		return elecContractMapper.batchInsert((List<EleContract>)this.getList());
	}
	
	public boolean insertIntoRentContract(){
		if(this.getList().size()<=0)return true;
		return rentContractMapper.batchInsert((List<RentContractVO>)this.getList());
	}

	public boolean updateDataSupplier(){
		if(this.getList().size()<=0)return true;
		datSupplierVOMapper.batchUpdate((List<DatSupplierVO>)this.getList());
		return true;
	}
	
	public boolean updatedatContract(){
		if(this.getList().size()<=0)return true;
		return datContractVOMapper.batchUpdate((List<DatContractVO>)this.getList());
	}
	
	public boolean updateElecContract(){
		if(this.getList().size()<=0)return true;
		return elecContractMapper.batchUpdate((List<EleContract>)this.getList());
	}
	
	public boolean updateRentContract(){
		if(this.getList().size()<=0)return true;
		return rentContractMapper.batchUpdate((List<RentContractVO>)this.getList());
	}


	@Resource
	private DatSupplierVOMapper datSupplierVOMapper;

	@Resource
	private DatContractVOMapper datContractVOMapper;

	@Resource
	private EleContractCollectionVOMapper elecContractMapper;
	
	@Resource
	private RentContractCollectionVOMapper rentContractMapper;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
