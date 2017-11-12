package com.gognamunish.referencedata.factory;

import java.util.Objects;

import com.gognamunish.referencedata.model.Instrument;
import com.gognamunish.referencedata.util.AppHelper;

public class InstrumentMergerFactory implements AppHelper {

	@FunctionalInterface
	public interface InstrumentMerger {
		Instrument merge(Instrument oldOne, Instrument newOne);
	}

	public static InstrumentMerger generateForExchange(String source) {

		Objects.requireNonNull(source, "Source is required for Key generation");

		switch (source) {
		case EXCHANGE_LME:
			return (Instrument oldOne, Instrument newOne) -> {

				if (oldOne == null) {
					return newOne;
				}

				if (oldOne.getSource().equals(newOne.getSource()) && oldOne.getCode().equals(newOne.getCode())) {
					return newOne;
				}

				return new Instrument.Builder()
						.withDeliveryDate(newOne.getDeliveryDate())
						.withTradeDate(newOne.getTradeDate())
						.withSource(newOne.getSource())
						.withOptionalFields(newOne.getOptionalFields())
						.withAlternateIds(newOne.getAlternateIds())
						.withLabel(oldOne.getLabel())
						.withMarket(oldOne.getMarket())
						.tradable(oldOne.isTradable())
						.build();

			};

		case EXCHANGE_PRIME:
			return (Instrument oldOne, Instrument newOne) -> {

				if (oldOne == null) {
					return newOne;
				}

				if (oldOne.getSource().equals(newOne.getSource()) && oldOne.getCode().equals(newOne.getCode())) {
					return newOne;
				}

				return new Instrument.Builder()
						.withOptionalFields(newOne.getOptionalFields())
						.withAlternateIds(newOne.getAlternateIds())
						.tradable(newOne.isTradable())
						.withSource(newOne.getSource())
						.withTradeDate(oldOne.getTradeDate())
						.withDeliveryDate(oldOne.getDeliveryDate())
						.withLabel(oldOne.getLabel())
						.withMarket(oldOne.getMarket())
						.build();

			};
		default:
			return (Instrument oldOne, Instrument newOne) -> newOne;
		}

	}

}