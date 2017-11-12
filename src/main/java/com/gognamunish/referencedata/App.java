package com.gognamunish.referencedata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gognamunish.referencedata.messaging.SourceInstrumentListener;
import com.gognamunish.referencedata.messaging.ThreadSafePublisher;
import com.gognamunish.referencedata.model.Instrument;
import com.gognamunish.referencedata.process.InstrumentProcessor;
import com.gognamunish.referencedata.util.AppHelper;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		scenario1();
		scenario2();
		scenario3();

	}

	private static void scenario3() {
		InstrumentProcessor processor = new InstrumentProcessor();

		ThreadSafePublisher exchangeLME = new ThreadSafePublisher(AppHelper.EXCHANGE_LME);
		ThreadSafePublisher exchangePRIME = new ThreadSafePublisher(AppHelper.EXCHANGE_PRIME);

		SourceInstrumentListener listener = new SourceInstrumentListener(processor);
		exchangeLME.registerListener(listener);
		exchangePRIME.registerListener(listener);

		Instrument lmeinstrument = new Instrument.Builder().withDeliveryDate(AppHelper.fromString("17-03-2018"))
				.withSource(AppHelper.EXCHANGE_LME).withTradeDate(AppHelper.fromString("15-03-2018"))
				.withMarket("LME_PB").withLabel("Lead 13 March 2018").withAlternateId(AppHelper.LME_CODE, "PB_03_2018")
				.build();

		Instrument primeInstrument = new Instrument.Builder().withDeliveryDate(AppHelper.fromString("18-03-2018"))
				.withTradeDate(AppHelper.fromString("14-03-2018")).withMarket("LME_PB").withLabel("Lead 13 March 2018")
				.withAlternateId(AppHelper.EXCHANGE_CODE, "PB_03_2018")
				.withAlternateId(AppHelper.INSTRUMENT_CODE, "PRIME_PB_03_2018").tradable(false)
				.withSource(AppHelper.EXCHANGE_PRIME).build();

		exchangePRIME.publish(primeInstrument);
		logger.info("{}", processor.getLastInternallyPublishedInstrument());
		exchangeLME.publish(lmeinstrument);
		logger.info("{}", processor.getLastInternallyPublishedInstrument());
	}

	private static void scenario2() {
		InstrumentProcessor processor = new InstrumentProcessor();

		ThreadSafePublisher exchangeLME = new ThreadSafePublisher(AppHelper.EXCHANGE_LME);
		ThreadSafePublisher exchangePRIME = new ThreadSafePublisher(AppHelper.EXCHANGE_PRIME);

		SourceInstrumentListener listener = new SourceInstrumentListener(processor);
		exchangeLME.registerListener(listener);
		exchangePRIME.registerListener(listener);

		Instrument lmeinstrument = new Instrument.Builder().withDeliveryDate(AppHelper.fromString("17-03-2018"))
				.withTradeDate(AppHelper.fromString("15-03-2018")).withMarket("PB").withLabel("Lead 13 March 2018")
				.withSource(AppHelper.EXCHANGE_LME).withAlternateId(AppHelper.LME_CODE, "PB_03_2018")
				.withAlternateId(AppHelper.EXCHANGE_LME, "PB_03_2018").build();

		Instrument primeInstrument = new Instrument.Builder().withDeliveryDate(AppHelper.fromString("17-03-2018"))
				.withTradeDate(AppHelper.fromString("15-03-2018")).withMarket("LME_PB").withLabel("Lead 13 March 2018")
				.withSource(AppHelper.EXCHANGE_PRIME).withAlternateId(AppHelper.EXCHANGE_CODE, "PRIME_PB_03_2018")
				.tradable(false).build();

		exchangeLME.publish(lmeinstrument);
		logger.info("{}", processor.getLastInternallyPublishedInstrument());
		exchangePRIME.publish(primeInstrument);
		logger.info("{}", processor.getLastInternallyPublishedInstrument());

	}

	private static void scenario1() {
		InstrumentProcessor processor = new InstrumentProcessor();

		ThreadSafePublisher publisher = new ThreadSafePublisher(AppHelper.LME_CODE);
		SourceInstrumentListener listener = new SourceInstrumentListener(processor);
		publisher.registerListener(listener);

		Instrument.Builder builder = new Instrument.Builder();
		Instrument instrument = builder.withDeliveryDate(AppHelper.fromString("17-03-2018"))
				.withTradeDate(AppHelper.fromString("15-03-2018")).withMarket("PB").withLabel("Lead 13 March 2018")
				.withSource(AppHelper.EXCHANGE_LME).withAlternateId(AppHelper.INSTRUMENT_CODE, "PB_03_2018").build();

		publisher.publish(instrument);

		logger.info("{}", processor.getLastInternallyPublishedInstrument());
	}

}
