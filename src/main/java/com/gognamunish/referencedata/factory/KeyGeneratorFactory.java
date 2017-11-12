package com.gognamunish.referencedata.factory;

import java.util.Objects;

import com.gognamunish.referencedata.model.Instrument;
import com.gognamunish.referencedata.util.AppHelper;

public class KeyGeneratorFactory implements AppHelper {

	@FunctionalInterface
	public interface KeyGenerator {
		String getKey(Instrument instrument);
	}

	public static KeyGenerator generateForExchange(String source) {

		Objects.requireNonNull(source, "Source is required for Key generation");

		switch (source) {
		case EXCHANGE_LME:
			return (Instrument i) -> i.getAlternateId(LME_CODE);
		case EXCHANGE_PRIME:
			return (Instrument i) -> i.getAlternateId(EXCHANGE_CODE);
		default:
			return (Instrument i) -> i.getCode();
		}

	}

}
