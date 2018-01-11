package com.xunge.service.job.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.job.HttpTookit;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.job.DatContractCollectionVOMapper;
import com.xunge.dao.job.EleContractCollectionVOMapper;
import com.xunge.dao.job.RentContractCollectionVOMapper;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.job.DatContractCollectionVO;
import com.xunge.model.job.EleContractCollectionVO;
import com.xunge.model.job.RentContractCollectionVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.job.IContractJobService;
import com.xunge.util.DateConverter;
import com.xunge.util.IDUtils;

@Service
public class ContractJobServiceImpl implements IContractJobService{
 
	private static int page=0; 
	
	private static int pageSize=5000; 
	
	private static final Logger LOGGER = Logger.getLogger(ContractJobServiceImpl.class);
	
	@Resource
	private DatContractCollectionVOMapper datContractCollectionVOMapper;
	
	@Resource
	private EleContractCollectionVOMapper eleContractCollectionVOMapper;
	
	@Resource
	private RentContractCollectionVOMapper rentContractCollectionVOMapper;
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	@Override
	public boolean dealContract(String url, String dateStr,SysProvinceVO sysProvinceVO) {
		
		boolean returnResult=false;
		try{
			String baseUrl=url+"?system_id=CMCC_NCMP&submit_date="+dateStr+"&page_size="+pageSize+"&current_page=";
			Map<String, String> params = new HashMap<>();
			String result = HttpTookit.doGet(baseUrl+page, params);
			JSONObject json=JSONObject.parseObject(result);
			if(json!=null&&"Y".equals(json.get("is_succ"))){
				int totalCount=json.getInteger("total_record");
				int totalPage=json.getInteger("total_page");
				//int pageSize=json.getInteger("page_size");
				//int currentPage=json.getInteger("current_page");

				if(totalCount>0){
					for(int i=0;i<totalPage;i++){
						if(i==0){
							JSONArray jsonArray=json.getJSONArray("contract_list");
							if(jsonArray!=null){
								List<JSONObject> pageList=jsonArray.toJavaList(JSONObject.class);
								dealOnePage(pageList,sysProvinceVO);
							}
						}
						else{
							String pageResult = HttpTookit.doGet(baseUrl+i, params);
							JSONObject pageJson=JSONObject.parseObject(pageResult);
							if(pageJson!=null&&"Y".equals(pageJson.get("is_succ"))){
								JSONArray jsonArray=pageJson.getJSONArray("contract_list");
								if(jsonArray!=null){
									List<JSONObject> pageList=jsonArray.toJavaList(JSONObject.class);
									dealOnePage(pageList,sysProvinceVO);
								}
							}
						}
					}
					callProcedure(sysProvinceVO.getPrvId());
				}
				returnResult=true;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			returnResult=false;
		}
		return returnResult;
	}

	
	private void callProcedure(String prvId){
		  
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("prvId", "");
		/*map.put("addEleCnt", 0);
		map.put("updateEleCnt", 0);
		map.put("addRentCnt", 0);
		map.put("updateRentCnt", 0);
		map.put("errCode", 0);*/
		Map<String,Integer> map=datContractCollectionVOMapper.syncContractData(pMap);
		int errCode=map.get("errCode");
		if(0==errCode){
			LOGGER.info("合同差异比对成功!");
		}
		else if(-1==errCode){
			LOGGER.error("合同差异比对失败!");
		}
	}
	
	private void dealOnePage(List<JSONObject> pageList,SysProvinceVO sysProvinceVO){
		
		List<DatContractCollectionVO> mainContractList=new ArrayList<DatContractCollectionVO>();
		List<EleContractCollectionVO> eleContractList=new ArrayList<EleContractCollectionVO>();
		List<RentContractCollectionVO> rentContractList=new ArrayList<RentContractCollectionVO>();
		List<String> resourceCuIds=new ArrayList<String>();
		for(JSONObject jsonObject:pageList){
			
			int contractType=jsonObject.getInteger("contract_type");
			DatContractCollectionVO datContractCollectionVO=createDatContract(jsonObject,sysProvinceVO);;
			String contractId=IDUtils.getID();
			
			if(datContractCollectionVO!=null){
				
				datContractCollectionVO.setContractId(contractId);
				mainContractList.add(datContractCollectionVO);
				resourceCuIds.add(dealBelongRoom(datContractCollectionVO.getContractSpaceresource()));
				
				switch(contractType){
				case 1:
					EleContractCollectionVO eleContractCollectionVO=createEleContract(jsonObject,sysProvinceVO);
					if(eleContractCollectionVO!=null){
						eleContractCollectionVO.setContractId(contractId);
						eleContractList.add(eleContractCollectionVO);
					}
					break;
				case 2:
					RentContractCollectionVO rentContractCollectionVO=createRentContract(jsonObject,sysProvinceVO);
					if(rentContractCollectionVO!=null){
						rentContractCollectionVO.setContractId(contractId);
						rentContractList.add(rentContractCollectionVO);
					}
					break;
				case 3:
					EleContractCollectionVO eleContractCollectionVO2=createEleContract(jsonObject,sysProvinceVO);
					if(eleContractCollectionVO2!=null){
						eleContractCollectionVO2.setContractId(contractId);
						eleContractList.add(eleContractCollectionVO2);
					}
					RentContractCollectionVO rentContractCollectionVO2=createRentContract(jsonObject,sysProvinceVO);
					if(rentContractCollectionVO2!=null){
						rentContractCollectionVO2.setContractId(contractId);
						rentContractList.add(rentContractCollectionVO2);
					}
					break;
				}
			}
		}
		if(mainContractList.size()>0){
			dealDatContractResourceId(mainContractList,sysProvinceVO,resourceCuIds);
			datContractCollectionVOMapper.batchInsert(mainContractList);
		}
		if(eleContractList.size()>0){
			eleContractCollectionVOMapper.batchInsertColl(eleContractList);
		}
		if(rentContractList.size()>0){
			rentContractCollectionVOMapper.batchInsertColl(rentContractList);
		}
	}
	
	private DatContractCollectionVO createDatContract(JSONObject jsonObject,SysProvinceVO sysProvinceVO){
		
		DatContractCollectionVO mainContract=new DatContractCollectionVO();
		
		mainContract.setContractFlow(jsonObject.getString("contract_num"));
		mainContract.setContractCode(jsonObject.getString("contract_code"));
		
		mainContract.setContractName(jsonObject.getString("contract_name"));
		mainContract.setContractType(jsonObject.getInteger("contract_type"));
		mainContract.setContractStartdate(DateConverter.converteToDate(jsonObject.getString("start_date")));
		mainContract.setContractEnddate(DateConverter.converteToDate(jsonObject.getString("end_date")));
		mainContract.setContractSigndate(DateConverter.converteToDate(jsonObject.getString("signn_date")));
		mainContract.setContractIntroduction(jsonObject.getString("contract_description"));
		mainContract.setContractState(dealStatus(jsonObject.getInteger("contract_status")));
		mainContract.setAuditingState(0);
		mainContract.setContractCheckname2(jsonObject.getString("org_name"));
		//部门id是否一致
		mainContract.setSysDepId(jsonObject.getString("contract_dept"));
		
		String prvId=sysProvinceVO.getPrvId();
		String prvSname=sysProvinceVO.getPrvSname();
		mainContract.setPrvId(prvId);
		mainContract.setPrvSname(prvSname);
		mainContract.setPregId(jsonObject.getString("city"));
		mainContract.setRegId(jsonObject.getString("region"));
		mainContract.setDataFrom(2);
		mainContract.setContractSpaceresource(jsonObject.getString("belong_room"));
		return mainContract;
	}
	
	private EleContractCollectionVO createEleContract(JSONObject jsonObject,SysProvinceVO sysProvinceVO){
		
		EleContractCollectionVO eleContract=new EleContractCollectionVO();
		eleContract.setElecontractId(IDUtils.getID());
		
		eleContract.setSupplierId(jsonObject.getString("vendor_number"));
		eleContract.setSupplyMethod(jsonObject.getInteger("electric_type"));
		int electric_contract_type= jsonObject.getInteger("electric_contract_type");
		eleContract.setIsIncludeAll(electric_contract_type);
		if(1==electric_contract_type){
			eleContract.setContractMoney(new BigDecimal(jsonObject.getString("electric_contract_amount")));
			eleContract.setContractTax(new BigDecimal(jsonObject.getString("electric_contract_tax")));
			eleContract.setContractTotalAmount(new BigDecimal(jsonObject.getString("electric_contract_amount_total")));
			eleContract.setContractYearAmount(new BigDecimal(12*jsonObject.getFloat(("electric_month_money"))));
			
		}
		eleContract.setIndependentMeter(jsonObject.getInteger("if_depend_meter"));
		eleContract.setCmccRatio(new BigDecimal(jsonObject.getString("CMCC_proportion")));
		eleContract.setUnicomRatio(new BigDecimal(jsonObject.getString("CUCC_proportion")));
		eleContract.setTelcomRatio(new BigDecimal(jsonObject.getString("CTCC_proportion")));
		eleContract.setIncludePriceTax(jsonObject.getInteger("electric_if_include_tax"));
		eleContract.setPriceType(jsonObject.getInteger("price_type"));
		eleContract.setElecontractPrice(jsonObject.getString("electric_price"));
		eleContract.setFlatPrice(new BigDecimal(jsonObject.getString("flat_price")));
		eleContract.setPeakPrice(new BigDecimal(jsonObject.getString("peak_price")));
		eleContract.setValleyPrice(new BigDecimal(jsonObject.getString("valley_price")));
		eleContract.setTopPrice(new BigDecimal(jsonObject.getString("ace_price")));
		
		eleContract.setPaymentperiodId(jsonObject.getString("electric_period"));
		eleContract.setTaxRate(new BigDecimal(jsonObject.getString("electric_tax")));
		
		eleContract.setSupplierCode(jsonObject.getString("vendor_number"));
		eleContract.setSupplierName(jsonObject.getString("vendor_name"));
		eleContract.setSupplierSite(jsonObject.getString("vendor_site"));
		return eleContract;
		
	}
	
	private RentContractCollectionVO createRentContract(JSONObject jsonObject,SysProvinceVO sysProvinceVO){
		
		RentContractCollectionVO rentContract=new RentContractCollectionVO();
		rentContract.setRentcontractId(IDUtils.getID());
		
		rentContract.setSupplierId(jsonObject.getString("vendor_number"));
		rentContract.setPaymentperiodId(jsonObject.getString("rent_period"));
		rentContract.setTotalAmountnotax(new BigDecimal(jsonObject.getString("contract_amount")));
		rentContract.setIncludeTax(jsonObject.getInteger("rent_if_include_tax"));
		rentContract.setBillamountTaxratio(new BigDecimal(jsonObject.getString("rent_tax")));
		rentContract.setTaxAmount(new BigDecimal(jsonObject.getString("tax_amount")));
		rentContract.setTotalAmount(new BigDecimal(jsonObject.getString("contract_amount_total")));
		rentContract.setYearAmount(new BigDecimal(jsonObject.getString("rent_year_money")));
		rentContract.setPropertyArea(new BigDecimal(jsonObject.getString("rent_area")));
		rentContract.setPaymentperiodId(jsonObject.getString("rent_period"));
		rentContract.setRentcontractNote(jsonObject.getString("contract_description"));
		rentContract.setRentcontractState("9");
		rentContract.setSupplierCode(jsonObject.getString("vendor_number"));
		rentContract.setSupplierName(jsonObject.getString("vendor_name"));
		rentContract.setSupplierSite(jsonObject.getString("vendor_site"));
		return rentContract;	
	}
	
	
	private String dealBelongRoom(String room){
		
		String result="";
		if(StringUtils.isNoneBlank(room)){
			String[] roomArray=room.split("\\|",-1);
			if(roomArray.length>0){
				result=roomArray[0];
			}
		}
		return result;
		
	}
	
	private int dealStatus(int status){
		
		int result=0;
		switch(status){
		case 1:
			result=1;
			break;
		case 2:
			result=-1;
			break;
		case 3:
			result=3;
			break;
		case 4:
			result=4;
			break;
		case 5:
			result=0;
			break;
		case 6:
			result=0;
			break;
		case 7:
			result=9;
			break;
		case 8:
			result=8;
			break;
		case 9:
			result=2;
			break;
		case 10:
			result=9;
			break;
		}
		return result;
	}
	
	private void dealDatContractResourceId(List<DatContractCollectionVO> datas,SysProvinceVO sysProvinceVO,List<String> resourceCuIds) {
		
		DatBaseresourceVOExample example=new DatBaseresourceVOExample();
	    com.xunge.model.basedata.DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		criteria.andBaseresourceCuidIn(resourceCuIds);
		List<DatBaseresourceVO> resourceList= datBaseresourceVOMapper.selectByExample(example);
		for(DatContractCollectionVO contarct:datas){
			for(DatBaseresourceVO source:resourceList){
				if(dealBelongRoom(contarct.getContractSpaceresource()).equalsIgnoreCase(source.getBaseresourceCuid())){
					contarct.setContractSpaceresource(source.getBaseresourceId());
					break;
				}
			}
		}
	}
}
