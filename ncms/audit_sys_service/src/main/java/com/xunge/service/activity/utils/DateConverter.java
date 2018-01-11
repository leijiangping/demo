/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xunge.service.activity.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;

/**
 * 日期转换类
 * @author zhujj
 * @version 2013-11-03
 */
public class DateConverter {

	private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);

	private static final String DATETIME_PATTERN = PromptMessageComm.DATE_TYPE_24H;

	private static final String DATETIME_PATTERN_NO_SECOND = PromptMessageComm.FORMAT_yyyyMMddHHmm;

	private static final String DATE_PATTERN = SelfelecComm.FORMAT_yyyyMMdd;

	private static final String MONTH_PATTERN = PromptMessageComm.FORMAT_yyyyMM;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object convert(Class type, Object value) {
		Object result = null;
		if (type == Date.class) {
			try {
				result = doConvertToDate(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (type == String.class) {
			result = doConvertToString(value);
		}
		return result;
	}

	/**
	 * Convert String to Date
	 *
	 * @param value
	 * @return
	 * @throws ParseException 
	 */
	private Date doConvertToDate(Object value) throws ParseException {
		Date result = null;

		if (value instanceof String) {
			result = DateUtils.parseDate((String) value, new String[] { DATE_PATTERN, DATETIME_PATTERN,
					DATETIME_PATTERN_NO_SECOND, MONTH_PATTERN });

			// all patterns failed, try a milliseconds constructor
			if (result == null && StringUtils.isNotEmpty((String) value)) {

				try {
					result = new Date(new Long((String) value).longValue());
				} catch (Exception e) {
					logger.error(PromptMessageComm.CONVERT_MILLISECONDS_DATE_FAILS);
					e.printStackTrace();
				}

			}

		} else if (value instanceof Object[]) {
			// let's try to convert the first element only
			Object[] array = (Object[]) value;

			if (array.length >= 1) {
				value = array[0];
				result = doConvertToDate(value);
			}

		} else if (Date.class.isAssignableFrom(value.getClass())) {
			result = (Date) value;
		}
		return result;
	}

	/**
	 * Convert Date to String
	 *
	 * @param value
	 * @return
	 */
	private String doConvertToString(Object value) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_PATTERN);
		String result = null;
		if (value instanceof Date) {
			result = simpleDateFormat.format(value);
		}
		return result;
	}

}