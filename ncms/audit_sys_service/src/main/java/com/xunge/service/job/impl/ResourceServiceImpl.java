package com.xunge.service.job.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.comm.StateComm;
import com.xunge.comm.job.IDUtils;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.DatBasesiteVOMapper;
import com.xunge.dao.basedata.DatBasestationVOMapper;
import com.xunge.dao.basedata.SysRegionVOMapper;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.DatBasesiteVOExample;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.job.IResourceService;
import com.xunge.service.job.util.DatBaseResourceUtil;
import com.xunge.service.job.util.DatBaseSiteUtil;
import com.xunge.service.job.util.DatBaseStationUtil;
import com.xunge.service.job.util.DataType;
import com.xunge.service.job.util.SysRegionUtil;
import com.xunge.service.job.util.TableType;
import com.xunge.service.job.util.ValueUtil;

@Service
public class ResourceServiceImpl implements IResourceService {

	@Resource
	private DatBasestationVOMapper datBasestationVOMapper;
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	@Resource
	private DatBasesiteVOMapper datBasesiteVOMapper;
	
	@Resource
	private SysRegionVOMapper sysRegionVOMapper;

	private int iBatchCount = 500;
	

	@Override
	public boolean InsertBaseStation(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<DatBasestationVO> datas = new ArrayList<DatBasestationVO>();
		List<String> resourceCuIds=new ArrayList<String>(); 
		for (String key : map.keySet()) {
			DatBasestationVO datBasestationVO = dealDatBasestationVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datBasestationVO.setBasestationId(IDUtils.getID());
			datas.add(datBasestationVO);
			resourceCuIds.add(datBasestationVO.getBaseresourceId());
		}
		dealDatBasestationResourceId(datas,resourceCuIds,sysProvinceVO);
		
		boolean bBatchInsert = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBasestationVO> tmp = datas.subList(i, j);
			bBatchInsert = datBasestationVOMapper.batchInsert(tmp);
		}
		return bBatchInsert;
	}

	@Override
	public boolean updateBaseStation(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {

		List<DatBasestationVO> datas = new ArrayList<DatBasestationVO>();
		List<String> resourceCuIds=new ArrayList<String>(); 
		for (String key : map.keySet()) {
			DatBasestationVO datBasestationVO = dealDatBasestationVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(datBasestationVO);
			resourceCuIds.add(datBasestationVO.getBaseresourceId());
		}
		dealDatBasestationResourceId(datas,resourceCuIds,sysProvinceVO);
		
		boolean bBatchUpdate = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBasestationVO> tmp = datas.subList(i, j);
			bBatchUpdate = datBasestationVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delBaseStation(String pkName,String typeName,List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		return datBasestationVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public boolean InsertBaseresource(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		
		List<DatBaseresourceVO> datas = new ArrayList<DatBaseresourceVO>();
		List<String> siteCuIds=new ArrayList<String>(); 
		for (String key : map.keySet()) {
			DatBaseresourceVO datBaseresourceVO = dealDatBaseresourceVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datBaseresourceVO.setBaseresourceId(IDUtils.getID());
			datas.add(datBaseresourceVO);
			siteCuIds.add(datBaseresourceVO.getBasesiteId());
		}
		dealDatBaseresourceSiteId(datas,siteCuIds,sysProvinceVO);
		dealBaseresourceRegion(datas,sysProvinceVO);
		
		boolean bUpdateDatas = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBaseresourceVO> tmp = datas.subList(i, j);
			bUpdateDatas = datBaseresourceVOMapper.batchInsert(tmp);
		}
		return bUpdateDatas;
	}

	@Override
	public boolean updateBaseresource(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		
		List<DatBaseresourceVO> datas = new ArrayList<DatBaseresourceVO>();
		List<String> siteCuIds=new ArrayList<String>(); 
		for (String key : map.keySet()) {
			DatBaseresourceVO datBaseresourceVO = dealDatBaseresourceVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(datBaseresourceVO);
			siteCuIds.add(datBaseresourceVO.getBasesiteId());
		}
		dealDatBaseresourceSiteId(datas,siteCuIds,sysProvinceVO);
		dealBaseresourceRegion(datas,sysProvinceVO);

		boolean bUpdateDatas = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBaseresourceVO> tmp = datas.subList(i, j);
			bUpdateDatas = datBaseresourceVOMapper.batchUpdate(tmp);
		}
		return bUpdateDatas;
	}

	@Override
	public boolean delBaseresource(String pkName,String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		
		return datBaseresourceVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public boolean InsertBasesite(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<DatBasesiteVO> datas = new ArrayList<DatBasesiteVO>();
		for (String key : map.keySet()) {
			DatBasesiteVO datBasesiteVO = this.dealDatBasesiteVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datBasesiteVO.setBasesiteId(IDUtils.getID());
			datas.add(datBasesiteVO);
		}
		dealBasesiteRegion(datas,sysProvinceVO);

		boolean bBatchInsert = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBasesiteVO> tmp = datas.subList(i, j);
			bBatchInsert = datBasesiteVOMapper.batchInsert(tmp);
		}
		return bBatchInsert;
	}

	@Override
	public boolean updateBasesite(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<DatBasesiteVO> datas = new ArrayList<DatBasesiteVO>();
		for (String key : map.keySet()) {
			DatBasesiteVO datBasesiteVO = this.dealDatBasesiteVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(datBasesiteVO);
		}
		dealBasesiteRegion(datas,sysProvinceVO);

		boolean bBatchUpdate = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBasesiteVO> tmp = datas.subList(i, j);
			bBatchUpdate = datBasesiteVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delBasesite(String pkName,String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		return datBasesiteVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public boolean InsertSysRegion(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<SysRegionVO> datas = new ArrayList<SysRegionVO>();
		for (String key : map.keySet()) {
			SysRegionVO sysRegionVO = this.dealSysRegionVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(sysRegionVO);
		}
		boolean bBatchInsert = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<SysRegionVO> tmp = datas.subList(i, j);
			bBatchInsert = sysRegionVOMapper.batchUpdate(tmp);
		}
		return bBatchInsert;
	}

	@Override
	public boolean updateSysRegion(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<SysRegionVO> datas = new ArrayList<SysRegionVO>();
		for (String key : map.keySet()) {
			SysRegionVO sysRegionVO = this.dealSysRegionVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(sysRegionVO);
		}
		boolean bBatchUpdate = false;
		for(int i=0; i<datas.size(); i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<SysRegionVO> tmp = datas.subList(i, j);
			bBatchUpdate = sysRegionVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delSysRegion(String pkName,String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();
		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		String[] a=null;
		return sysRegionVOMapper.deByCuids(list.toArray(a));
	}

	public String[] getDBparms(String perfix){

		String[] result=new String[3];
		String pkName="";
		String tableName="";
		if(perfix.equalsIgnoreCase(DataType.RESOURCE_CITY)){
			pkName="city_cid";
			tableName=TableType.RESOURCE_SYS_REGION;
		}
		else if(perfix.equalsIgnoreCase(DataType.RESOURCE_REGION)){
			pkName="county_cid";
			tableName=TableType.RESOURCE_SYS_REGION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_SITE)){
        	pkName="site_cid";
        	tableName=TableType.RESOURCE_DAT_BASESITE;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_ROOM)){
        	pkName="room_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_RESOURCESPOT)){
        	pkName="resource_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_HOTSPOT)){
        	pkName="hotspot_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_POSITION)){
        	pkName="position_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_WIRELESS2G)){
        	pkName="bts_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_WIRELESS3G)){
        	pkName="nodeb_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_WIRELESS4G)){
        	pkName="enodeb_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_AP)){
        	pkName="ap_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_HOTSPOTSWITCH)){
        	pkName="hotspotswitch_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_RRU)){
        	pkName="rru_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_REPEATER)){
        	pkName="repeater_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_DISTRIBUTION)){
        	pkName="distribution_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_ONU)){
        	pkName="onu_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_OLT)){
        	pkName="olt_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_PTN)){
        	pkName="ptn_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_OTN)){
        	pkName="otn_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
		result[0]=pkName;
		result[1]=perfix;
		result[2]=tableName;
		return result;
	}
	
	
	private SysRegionVO dealSysRegionVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		SysRegionVO sysRegionVO = new SysRegionVO();
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		switch (typeName) {

		case DataType.RESOURCE_CITY:
			sysRegionVO.setLevel(1);
			SysRegionUtil.dealCityColumns(sysRegionVO, columns, valueArray,sysProvinceVO);
			break;
		case DataType.RESOURCE_REGION:
			sysRegionVO.setLevel(2);
			SysRegionUtil.dealRegionColumns(sysRegionVO, columns, valueArray,sysProvinceVO);
			break;
		}
		//sysRegionVO.setRegId(IDUtils.getID());
		sysRegionVO.setIsValid(0);
		sysRegionVO.setRegState(0);
		return sysRegionVO;
	}
	 

	private DatBasesiteVO dealDatBasesiteVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		DatBasesiteVO datBasesiteVO = new DatBasesiteVO();
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		DatBaseSiteUtil.dealSiteColumns(datBasesiteVO, columns, valueArray,sysProvinceVO);
		datBasesiteVO.setDataFrom(2);
		datBasesiteVO.setAuditingState(0);
		datBasesiteVO.setBasesiteState(dealStatus(datBasesiteVO.getBasesiteState()));
		return datBasesiteVO;
	}

	private DatBaseresourceVO dealDatBaseresourceVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		DatBaseresourceVO datBaseresourceVO = new DatBaseresourceVO();
		int baseresource_type = 0;
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		
		if(DataType.RESOURCE_ROOM.equalsIgnoreCase(typeName)){
			baseresource_type = 0;
			DatBaseResourceUtil.dealRoomColumns(datBaseresourceVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_RESOURCESPOT.equalsIgnoreCase(typeName)){
			baseresource_type = 1;
			DatBaseResourceUtil.dealResourceSpotColumns(datBaseresourceVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_HOTSPOT.equalsIgnoreCase(typeName)){
			baseresource_type = 2;
			DatBaseResourceUtil.dealHotspotColumns(datBaseresourceVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_POSITION.equalsIgnoreCase(typeName)){
			baseresource_type = 3;
			DatBaseResourceUtil.dealPositionColumns(datBaseresourceVO, columns, valueArray,sysProvinceVO);
		}
		datBaseresourceVO.setDataFrom(2);
		datBaseresourceVO.setAuditingState(0);
		datBaseresourceVO.setBaseresourceState(dealStatus(datBaseresourceVO.getBaseresourceState()));
		datBaseresourceVO.setBaseresourceType(baseresource_type);
		return datBaseresourceVO;
	}

	private DatBasestationVO dealDatBasestationVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		DatBasestationVO datBasestationVO = new DatBasestationVO();
		int basestation_category = 1;
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);

		if(DataType.RESOURCE_AP.equalsIgnoreCase(typeName)){
			basestation_category = 1;
			DatBaseStationUtil.dealApColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_WIRELESS2G.equalsIgnoreCase(typeName)){
			basestation_category = 2;
			DatBaseStationUtil.dealWireless2GColumns(datBasestationVO,columns,valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_WIRELESS3G.equalsIgnoreCase(typeName)){
			basestation_category = 3;
			DatBaseStationUtil.dealWireless3GColumns(datBasestationVO,columns,valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_WIRELESS4G.equalsIgnoreCase(typeName)){
			basestation_category = 4;
			DatBaseStationUtil.dealWireless4GColumns(datBasestationVO,columns,valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_HOTSPOTSWITCH.equalsIgnoreCase(typeName)){
			basestation_category = 5;
			DatBaseStationUtil.dealHotspotSwitchColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_RRU.equalsIgnoreCase(typeName)){
			basestation_category = 6;
			DatBaseStationUtil.dealRruColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_REPEATER.equalsIgnoreCase(typeName)){
			basestation_category = 7;
			DatBaseStationUtil.dealRepeaterColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_DISTRIBUTION.equalsIgnoreCase(typeName)){
			basestation_category = 8;
			DatBaseStationUtil.dealDistributionColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_ONU.equalsIgnoreCase(typeName)){
			basestation_category = 9;
			DatBaseStationUtil.dealOnuColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_OLT.equalsIgnoreCase(typeName)){
			basestation_category = 10;
			DatBaseStationUtil.dealOLTColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_PTN.equalsIgnoreCase(typeName)){
			basestation_category = 11;
			DatBaseStationUtil.dealPTNColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_OTN.equalsIgnoreCase(typeName)){
			basestation_category = 12;
			DatBaseStationUtil.dealOTNColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		datBasestationVO.setDataFrom(2);
		datBasestationVO.setBasestationState(dealStatus(datBasestationVO.getBasestationState()));
		datBasestationVO.setBasestationCategory(basestation_category);
		return datBasestationVO;
	}

	
	private void dealDatBasestationResourceId(List<DatBasestationVO> datas,List<String> resourceCuIds,SysProvinceVO sysProvinceVO) {
		
		DatBaseresourceVOExample example=new DatBaseresourceVOExample();
	    com.xunge.model.basedata.DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		criteria.andBaseresourceCuidIn(resourceCuIds);
		List<DatBaseresourceVO> resourceList= datBaseresourceVOMapper.selectByExample(example);
		for(DatBasestationVO station:datas){
			for(DatBaseresourceVO source:resourceList){
				if(station.getBaseresourceId().equalsIgnoreCase(source.getBaseresourceCuid())){
					station.setBaseresourceId(source.getBaseresourceId());
					break;
				}
			}
		}
	}
	
	
	private void dealDatBaseresourceSiteId(List<DatBaseresourceVO> datas,List<String> siteCuIds,SysProvinceVO sysProvinceVO) {
		
		DatBasesiteVOExample example=new DatBasesiteVOExample();
		com.xunge.model.basedata.DatBasesiteVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		criteria.andBasesiteCuidIn(siteCuIds);
		List<DatBasesiteVO> siteList= datBasesiteVOMapper.selectByExample(example);
		for(DatBaseresourceVO resource:datas){
			for(DatBasesiteVO site:siteList){
				if(resource.getBasesiteId().equalsIgnoreCase(site.getBasesiteCuid())){
					resource.setBasesiteId(site.getBasesiteId());
					break;
				}
			}
		}
	}
	
	private int dealStatus(int status){
		
		int result=status;
		switch(status){
		case 1:
			result=4;
		    break;
		case 2:
			result=6;
			break;
		case 3:
			result=2;
			break;
		case 4:
			result=1;
			break;
		case 5:
			result=5;
			break;
		case 6:
			result=3;
			break;
		case 7:
			result=7;
			break;
		case 8:
			result=8;
			break;
		}
		return result;
	}
	
	private List<SysRegionVO> getSysRegion(SysProvinceVO sysProvinceVO){
		
		List<SysRegionVO> listreg=new ArrayList<SysRegionVO>();
		SysRegionVO sysRegionVO=new SysRegionVO();
		sysRegionVO.setPrvId(sysProvinceVO.getPrvId());
		sysRegionVO.setRegState(StateComm.STATE_0);
		listreg=sysRegionVOMapper.getAddress(sysRegionVO);
		return listreg;
	}
	
	private void dealBaseresourceRegion(List<DatBaseresourceVO> datas,SysProvinceVO sysProvinceVO){
		List<SysRegionVO> listreg=getSysRegion(sysProvinceVO);
		for(DatBaseresourceVO datBaseresourceVO:datas){
			String pregId=datBaseresourceVO.getPregId();
			String regId=datBaseresourceVO.getRegId();
			for(SysRegionVO region:listreg){
				if(region.getRegCode().equalsIgnoreCase(regId)){
					datBaseresourceVO.setRegId(region.getRegId());
					datBaseresourceVO.setRegName(region.getRegName());
				}
				if(region.getRegCode().equalsIgnoreCase(pregId)){
					datBaseresourceVO.setPregId(region.getRegId());
					datBaseresourceVO.setPregName(region.getRegName());
				}
			}
		}
	}
	
	private void dealBasesiteRegion(List<DatBasesiteVO> datas,SysProvinceVO sysProvinceVO){
		List<SysRegionVO> listreg=getSysRegion(sysProvinceVO);
		for(DatBasesiteVO datBasesiteVO:datas){
			String pregId=datBasesiteVO.getPregId();
			String regId=datBasesiteVO.getRegId();
			for(SysRegionVO region:listreg){
				if(region.getRegCode().equalsIgnoreCase(regId)){
					datBasesiteVO.setRegId(region.getRegId());
					datBasesiteVO.setRegName(region.getRegName());
				}
				if(region.getRegCode().equalsIgnoreCase(pregId)){
					datBasesiteVO.setPregId(region.getRegId());
					datBasesiteVO.setPregName(region.getRegName());
				}
			}
		}
	}
}
