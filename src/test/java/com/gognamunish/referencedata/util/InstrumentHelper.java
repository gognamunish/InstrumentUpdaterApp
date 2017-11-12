package com.gognamunish.referencedata.util;

import com.gognamunish.referencedata.model.Instrument;

public class InstrumentHelper {
	
	public static final String EMPTY_STRING = "";
	public static final String EXCHANGE_CODE_VALUE = "ExchangeCode";
	public static final String LME_CODE_VALUE = "LMECode";

	public static final Instrument LME_INSTRUMENT_WITH_LME_CODE = new Instrument.Builder()
			.withDeliveryDate(AppHelper.fromString("14-03-2018"))
			.withSource(AppHelper.EXCHANGE_PRIME)
			.withTradeDate(AppHelper.fromString("15-03-2018"))
			.withMarket("LME_PB").withLabel("Lead 13 March 2018")
			.withAlternateId(AppHelper.LME_CODE, LME_CODE_VALUE)
			.withCode("lme_code")
			.build();
	
	public static final Instrument LME_INSTRUMENT_WITHOUT_LME_CODE = new Instrument.Builder()
			.withDeliveryDate(AppHelper.fromString("16-03-2018"))
			.withSource(AppHelper.EXCHANGE_PRIME)
			.withTradeDate(AppHelper.fromString("17-03-2018"))
			.withMarket("LME_PB").withLabel("Lead 13 March 2018")
			.withCode("lme_code")
			.build();
	
	public static final Instrument PRIME_INSTRUMENT_WITH_EXCHANGE_CODE = new Instrument.Builder()
			.withDeliveryDate(AppHelper.fromString("18-03-2018"))
			.withSource(AppHelper.EXCHANGE_PRIME)
			.withTradeDate(AppHelper.fromString("15-03-2018"))
			.withMarket("LME_PB").withLabel("Lead 13 March 2018")
			.withAlternateId(AppHelper.EXCHANGE_CODE, EXCHANGE_CODE_VALUE)
			.tradable(false)
			.withCode("prime_code")
			.build();
	
	public static final Instrument PRIME_INSTRUMENT_WITHOUT_EXCHANGE_CODE = new Instrument.Builder()
			.withDeliveryDate(AppHelper.fromString("17-03-2018"))
			.withSource(AppHelper.EXCHANGE_PRIME)
			.withTradeDate(AppHelper.fromString("15-03-2018"))
			.withMarket("LME_PB").withLabel("Lead 13 March 2018")
			.withCode("prime_code")
			.build();

}
