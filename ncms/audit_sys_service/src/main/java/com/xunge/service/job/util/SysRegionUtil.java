package com.xunge.service.job.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.job.SysProvinceVO;

/**
 * SysRegion工具类
 */
public class SysRegionUtil {
	
	public static void dealCityColumns(SysRegionVO sysRegionVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						sysRegionVO.setPrvId(sysProvinceVO.getPrvId());
					}
					break;
				case "city_cid":
					sysRegionVO.setRegId(value);
					break;
				case "city_name":
					sysRegionVO.setRegName(value);
					break;
				case "change_type":

					break;
				}
			}

		}
	}
	
	public static void dealRegionColumns(SysRegionVO sysRegionVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0; i < columns.size(); i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						sysRegionVO.setPrvId(sysProvinceVO.getPrvId());
					}
					break;
				case "county_cid":
					sysRegionVO.setRegId(value);
					break;
				case "county_name":
					sysRegionVO.setRegName(value);
					break;
				case "city_cid":
					sysRegionVO.setPregId(value);
					break;
				case "change_type":

					break;
				}
			}
		}
	}
}