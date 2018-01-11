package com.xunge.service.job.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.job.DateConverter;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.job.SysProvinceVO;

/**
 * DatBasestation工具类
 */
public class DatBaseStationUtil {

	public static void dealWireless2GColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "bts_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "bts_code":
					datBasestationVO.setBasestationCode(value);
					break;
				case "bts_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "admission_date":

					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasestationVO.setBasestationOpendate(d);
					}
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "trx_count":
					datBasestationVO.setBasestationCarrier(Integer.parseInt(value));
					break;
				case "related_room":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "coverage":
					datBasestationVO.setBasestationCovertype(Integer.parseInt(value));
					break;
		        //数据表无该字段
				case "change_type":
					break;
				}
			}
			
		}
	}

	public static void dealWireless3GColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "nodeb_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "nodeb_code":
					datBasestationVO.setBasestationCode(value);
					break;
				case "nodeb_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasestationVO.setBasestationOpendate(d);
					}
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "trx_count":
					datBasestationVO.setBasestationCarrier(Integer.parseInt(value));
					break;
				case "related_room":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "coverage":
					datBasestationVO.setBasestationCovertype(Integer.parseInt(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealWireless4GColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "enodeb_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "enodeb_code":
					datBasestationVO.setBasestationCode(value);
					break;
				case "enodeb_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasestationVO.setBasestationOpendate(d);
					}
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				//数据表无该字段
				case "base_station":

					break;
				case "trx_count":
					datBasestationVO.setBasestationCarrier(Integer.parseInt(value));
					break;
				case "related_room":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "coverage":
					datBasestationVO.setBasestationCovertype(Integer.parseInt(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealApColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "ap_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "ap_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_hotspot":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealHotspotSwitchColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "hotspotswitch_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "hotspotswitch_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_hotspot":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealRruColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "rru_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "rru_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealRepeaterColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "repeater_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "repeater_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_resource":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "rpt_type":
					datBasestationVO.setBasestationType(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}

	public static void dealDistributionColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "distribution_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "distribution_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}

	public static void dealOnuColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "onu_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "onu_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "access_type":
					datBasestationVO.setBasestationType(Integer.parseInt(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealOLTColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "olt_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "olt_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void dealPTNColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "ptn_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "ptn_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}

	public static void dealOTNColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "otn_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "otn_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceId(value);
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}
}