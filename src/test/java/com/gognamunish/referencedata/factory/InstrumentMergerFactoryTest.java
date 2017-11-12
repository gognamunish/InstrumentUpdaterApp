package com.gognamunish.referencedata.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;

import com.gognamunish.referencedata.factory.InstrumentMergerFactory.InstrumentMerger;
import com.gognamunish.referencedata.model.Instrument;
import com.gognamunish.referencedata.util.AppHelper;
import com.gognamunish.referencedata.util.InstrumentHelper;

public class InstrumentMergerFactoryTest {

	@Test
	public void when_source_is_empty_or_null() {
		assertThat(catchThrowable(() -> InstrumentMergerFactory.generateForExchange(null)))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Source is required for Key generation");
	}

	@Test
	public void when_arriving_instrument_is_lme_and_existing_instrument_is_null() {
		Instrument existing = null;
		Instrument arriving = InstrumentHelper.LME_INSTRUMENT_WITH_LME_CODE;

		InstrumentMerger instrumentMerger = InstrumentMergerFactory.generateForExchange(AppHelper.EXCHANGE_LME);
		Instrument mergedInstrument = instrumentMerger.merge(existing, arriving);

		assertThat(mergedInstrument.isTradable()).isEqualTo(arriving.isTradable());
		assertThat(mergedInstrument.getTradeDate()).isEqualTo(arriving.getTradeDate());
		assertThat(mergedInstrument.getDeliveryDate()).isEqualTo(arriving.getDeliveryDate());
	}

	@Test
	public void when_arriving_instrument_is_prime_and_existing_instrument_is_lme() {
		Instrument existing = InstrumentHelper.LME_INSTRUMENT_WITH_LME_CODE;
		Instrument arriving = InstrumentHelper.PRIME_INSTRUMENT_WITH_EXCHANGE_CODE;

		InstrumentMerger instrumentMerger = InstrumentMergerFactory.generateForExchange(AppHelper.EXCHANGE_PRIME);
		Instrument mergedInstrument = instrumentMerger.merge(existing, arriving);

		assertThat(mergedInstrument.isTradable()).isEqualTo(arriving.isTradable());
		assertThat(mergedInstrument.getTradeDate()).isEqualTo(arriving.getTradeDate());
		assertThat(mergedInstrument.getDeliveryDate()).isEqualTo(existing.getDeliveryDate());
	}

	@Test
	public void when_arriving_instrument_is_lme_and_existing_instrument_is_prime() {
		Instrument existing = InstrumentHelper.PRIME_INSTRUMENT_WITH_EXCHANGE_CODE;
		Instrument arriving = InstrumentHelper.LME_INSTRUMENT_WITH_LME_CODE;

		InstrumentMerger instrumentMerger = InstrumentMergerFactory.generateForExchange(AppHelper.EXCHANGE_LME);
		Instrument mergedInstrument = instrumentMerger.merge(existing, arriving);

		assertThat(mergedInstrument.isTradable()).isEqualTo(existing.isTradable());
		assertThat(mergedInstrument.getTradeDate()).isEqualTo(arriving.getTradeDate());
		assertThat(mergedInstrument.getDeliveryDate()).isEqualTo(arriving.getDeliveryDate());
	}

}
