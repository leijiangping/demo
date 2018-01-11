package com.xunge.controller.basedata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.xunge.comm.system.PromptMessageComm;

/**
 * 
 */
public class DateConverter implements Converter {
	@Override
	public boolean canConvert(Class arg0) {

		return Date.class == arg0;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1, MarshallingContext arg2) {
		SimpleDateFormat dateFm = new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		arg1.setValue(dateFm.format(arg0));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext arg1) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat dateFm = new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		try {
			calendar.setTime(dateFm.parse(reader.getValue()));
		} catch (ParseException e) {
			throw new ConversionException(e.getMessage(), e);
		}
		return calendar.getTime();

	}
}
