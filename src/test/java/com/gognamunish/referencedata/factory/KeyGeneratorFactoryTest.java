package com.gognamunish.referencedata.factory;

import static com.gognamunish.referencedata.util.InstrumentHelper.EMPTY_STRING;
import static com.gognamunish.referencedata.util.InstrumentHelper.EXCHANGE_CODE_VALUE;
import static com.gognamunish.referencedata.util.InstrumentHelper.LME_CODE_VALUE;
import static com.gognamunish.referencedata.util.InstrumentHelper.LME_INSTRUMENT_WITHOUT_LME_CODE;
import static com.gognamunish.referencedata.util.InstrumentHelper.LME_INSTRUMENT_WITH_LME_CODE;
import static com.gognamunish.referencedata.util.InstrumentHelper.PRIME_INSTRUMENT_WITHOUT_EXCHANGE_CODE;
import static com.gognamunish.referencedata.util.InstrumentHelper.PRIME_INSTRUMENT_WITH_EXCHANGE_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;

import com.gognamunish.referencedata.factory.KeyGeneratorFactory.KeyGenerator;
import com.gognamunish.referencedata.util.AppHelper;

public class KeyGeneratorFactoryTest {

	@Test
	public void when_source_is_empty_or_null() {
		assertThat(catchThrowable(() -> KeyGeneratorFactory.generateForExchange(null)))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Source is required for Key generation");
	}

	@Test
	public void when_exchange_is_lme_with_lme_code_provided() {
		KeyGenerator keyGenerator = KeyGeneratorFactory.generateForExchange(AppHelper.EXCHANGE_LME);
		String key = keyGenerator.getKey(LME_INSTRUMENT_WITH_LME_CODE);

		assertThat(key).isEqualTo(LME_CODE_VALUE);
	}

	@Test
	public void when_exchange_is_lme_and_no_lme_code_provided() {
		KeyGenerator keyGenerator = KeyGeneratorFactory.generateForExchange(AppHelper.EXCHANGE_LME);
		String key = keyGenerator.getKey(LME_INSTRUMENT_WITHOUT_LME_CODE);

		assertThat(key).isEqualTo(EMPTY_STRING);
	}

	@Test
	public void when_exchange_is_prime_with_exchange_code_provided() {
		KeyGenerator keyGenerator = KeyGeneratorFactory.generateForExchange(AppHelper.EXCHANGE_PRIME);
		String key = keyGenerator.getKey(PRIME_INSTRUMENT_WITH_EXCHANGE_CODE);

		assertThat(key).isEqualTo(EXCHANGE_CODE_VALUE);
	}

	@Test
	public void when_exchange_is_prime_and_no_prime_code_provided() {
		KeyGenerator keyGenerator = KeyGeneratorFactory.generateForExchange(AppHelper.EXCHANGE_PRIME);
		String key = keyGenerator.getKey(PRIME_INSTRUMENT_WITHOUT_EXCHANGE_CODE);

		assertThat(key).isEqualTo(EMPTY_STRING);
	}

}
