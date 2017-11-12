package com.gognamunish.referencedata.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface AppHelper {

	String EXCHANGE_LME = "LME";
	String EXCHANGE_PRIME = "PRIME";

	String LME_CODE = "LME_CODE";
	String INSTRUMENT_CODE = "INSTRUMENT_CODE";
	String EXCHANGE_CODE = "EXCHANGE_CODE";

	String LABEL = "LABEL";
	String TRADABLE = "TRADABLE";
	String MARKET = "MARKET";
	String DELIVERY_DATE = "DELIVERY_DATE";
	String LAST_TRADING_DATE = "LAST_TRADING_DATE";

	public static Date fromString(String date) {
		try {
			return new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
		}

		return null;
	}

	public static String toString(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy").format(date);
	}

}
