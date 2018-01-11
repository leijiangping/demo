package com.xunge.service.job.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.job.DateConverter;
import com.xunge.core.util.StrUtil;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.job.SysProvinceVO;

/**
 * DatBaseResource工具类
 */
public class DatBaseResourceUtil {
	
	public static void dealRoomColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){ 
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBaseresourceVO.setPrvId(sysProvinceVO.getPrvId());
						datBaseresourceVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "room_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "room_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "room_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "room_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					datBaseresourceVO.setPregId(value);
					break;
				case "county_cid":
					datBaseresourceVO.setRegId(value);
					break;
				case "site_cid":
					datBaseresourceVO.setBasesiteId(value);
					break;
				case "room_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "room_area":
					value = StrUtil.isBlank(value)?"0.00":value;
					datBaseresourceVO.setBaseresourceArea(new BigDecimal(value));
					break;
				case "room_type":
					datBaseresourceVO.setBaseresourceCategory(value);
					break;
				case "status":
					datBaseresourceVO.setBaseresourceState(Integer.parseInt(value));
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBaseresourceVO.setBaseresourceOpendate(d);
					}
					break;
				case "room_owner":
					datBaseresourceVO.setRoomOwner(value);
					break;
				case "room_property":
					datBaseresourceVO.setRoomProperty(Integer.parseInt(value));
					break;
				case "share_attribute":
					datBaseresourceVO.setRoomShare(value);
					break;
				case "longitude":	
					datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					break;
				case "latitude":
					datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					break;
				case "airconditioner_power":
					datBaseresourceVO.setAirconditionerPower(new BigDecimal(value));
					break;
				case "change_type":

					break;
				}
			}
			
		}
	}
	
	public static void dealResourceSpotColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBaseresourceVO.setPrvId(sysProvinceVO.getPrvId());
						datBaseresourceVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "resource_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "resource_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "resource_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "resource_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					datBaseresourceVO.setPregId(value);
					break;
				case "county_cid":
					datBaseresourceVO.setRegId(value);
					break;
				case "site_cid":
					datBaseresourceVO.setBasesiteId(value);
					break;
				case "resource_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "resource_type":
					datBaseresourceVO.setBaseresourceCategory(value);
					break;
				case "status":
					datBaseresourceVO.setBaseresourceState(Integer.parseInt(value));
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBaseresourceVO.setBaseresourceOpendate(d);
					}
					break;
				case "resource_owner":
					datBaseresourceVO.setRoomOwner(value);
					break;
				case "longitude":
					datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					break;
				case "latitude":
					datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	
	public static void dealHotspotColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBaseresourceVO.setPrvId(sysProvinceVO.getPrvId());
						datBaseresourceVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "hotspot_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "hotspot_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "hotspot_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "hotspot_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					datBaseresourceVO.setPregId(value);
					break;
				case "county_cid":
					datBaseresourceVO.setRegId(value);
					break;
				case "status":
					datBaseresourceVO.setBaseresourceState(Integer.parseInt(value));
					break;
				case "hotspot_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "longitude":
					datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					break;
				case "latitude":
					datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	
	public static void dealPositionColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){ 
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBaseresourceVO.setPrvId(sysProvinceVO.getPrvId());
						datBaseresourceVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "position_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "position_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "position_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "position_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					datBaseresourceVO.setPregId(value);
					break;
				case "county_cid":
					datBaseresourceVO.setRegId(value);
					break;
				case "site_cid":
					datBaseresourceVO.setBasesiteId(value);
					break;
				case "position_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "position_type":
					datBaseresourceVO.setBaseresourceCategory(value);
					break;
				case "status":
					datBaseresourceVO.setBaseresourceState(Integer.parseInt(value));
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBaseresourceVO.setBaseresourceOpendate(d);
					}
					break;
				case "position_owner":
					datBaseresourceVO.setRoomOwner(value);
					break;
				case "position_property":
					datBaseresourceVO.setRoomProperty(Integer.parseInt(value));
					break;
				case "share_attribute":
					datBaseresourceVO.setRoomShare(value);
					break;
				case "longitude":
					datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					break;
				case "latitude":
					datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
}