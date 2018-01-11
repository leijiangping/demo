package com.xunge.service.job.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.job.DateConverter;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.job.SysProvinceVO;

/**
 * DatBaseSite工具类
 */
public class DatBaseSiteUtil {
	
	public static void dealSiteColumns(DatBasesiteVO datBasesiteVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {
		int columnSize=columns.size();
		for (int i = 0; i < columnSize; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasesiteVO.setPrvId(sysProvinceVO.getPrvId());
						datBasesiteVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "site_cid":
					datBasesiteVO.setBasesiteCuid(value);
					break;
				case "site_code":
					datBasesiteVO.setBasesiteCode(value);
					break;
				case "site_maintenance":
					datBasesiteVO.setBasesiteMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBasesiteVO.setTowerSiteCode(value);
					break;
				case "site_name":
					datBasesiteVO.setBasesiteName(value);
					break;
				case "city_cid":
					datBasesiteVO.setPregId(value);
					break;
				case "county_cid":
					datBasesiteVO.setRegId(value);
					break;
				case "site_address":
					datBasesiteVO.setBasesiteAddress(value);
					break;
				case "site_type":
					datBasesiteVO.setBasesiteType(Integer.parseInt(value));
					break;
				case "status":
					datBasesiteVO.setBasesiteState(Integer.parseInt(value));
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasesiteVO.setBasesiteOpendate(d);
					}
					break;
				case "site_owner":
					datBasesiteVO.setBasesiteBelong(value);
					break;
				case "site_property":
					datBasesiteVO.setBasesiteProperty(Integer.parseInt(value));
					break;
				case "share_attribute":
					datBasesiteVO.setBasesiteShare(value);
					break;
				case "longitude":
					datBasesiteVO.setBasesiteLongitude(new BigDecimal(value));
					break;
				case "latitude":
					datBasesiteVO.setBasesiteLatitude(new BigDecimal(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
}