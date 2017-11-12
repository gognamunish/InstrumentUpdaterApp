package com.gognamunish.referencedata.at.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import com.gognamunish.referencedata.model.Instrument;
import com.gognamunish.referencedata.model.Instrument.Builder;
import com.gognamunish.referencedata.util.AppHelper;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InstrumentUpdaterSteps implements AppHelper {

	// ideally something injected by Spring, Guice etc.
	private World world = World.getInstance();

	@Given("^the \"([^\"]*)\" instrument \"([^\"]*)\" with these details:$")
	@And("^a  \"([^\"]*)\" instrument \"([^\"]*)\" with these details:$")
	public void the_instrument_with_these_details(String source, String instrumentCode,
			List<Map<String, String>> instrumentList) throws Throwable {
		world.addPublisher(source);
		Instrument instrument = buildInstrument(source, instrumentCode, instrumentList.get(0));
		world.addInstrument(instrument);
	}

	@When("^\"([^\"]*)\" publishes instrument \"([^\"]*)\"$")
	public void publishes_instrument(String source, String instrumentCode) throws Throwable {
		world.publish(source, instrumentCode);
	}

	@Then("^the application publishes the following instrument internally$")
	public void the_application_publishes_the_following_instrument_internally(List<Map<String, String>> instrumentList)
			throws Throwable {

		Instrument mergedInstrument = world.getLastInternallyPublishedInstrument();

		instrumentList.get(0).entrySet().forEach(entry -> {
			switch (entry.getKey()) {
			case LAST_TRADING_DATE:
				assertThat(AppHelper.toString(mergedInstrument.getTradeDate())).isEqualTo(entry.getValue());
				break;
			case DELIVERY_DATE:
				assertThat(AppHelper.toString(mergedInstrument.getDeliveryDate())).isEqualTo(entry.getValue());
				break;
			case MARKET:
				assertThat(mergedInstrument.getMarket()).isEqualTo(entry.getValue());
				break;
			case TRADABLE:
				assertThat(mergedInstrument.isTradable()).isEqualTo("TRUE".equals(entry.getValue()));
				break;
			case LABEL:
				assertThat(mergedInstrument.getLabel()).isEqualTo(entry.getValue());
				break;
			default:
				// ignore
			}

		});

	}

	private Instrument buildInstrument(String source, String instrumentCode, Map<String, String> map) {
		final Builder builder = new Instrument.Builder();
		builder.withSource(source);
		builder.withCode(instrumentCode);

		if (EXCHANGE_LME.equals(source)) {
			builder.withAlternateId(LME_CODE, instrumentCode);
		}

		map.entrySet().forEach(entry -> {
			switch (entry.getKey()) {
			case LAST_TRADING_DATE:
				builder.withTradeDate(AppHelper.fromString(entry.getValue()));
				break;
			case DELIVERY_DATE:
				builder.withDeliveryDate(AppHelper.fromString(entry.getValue()));
				break;
			case MARKET:
				builder.withMarket(entry.getValue());
				break;
			case TRADABLE:
				builder.tradable("TRUE".equals(entry.getValue()));
				break;
			case LABEL:
				builder.withLabel(entry.getValue());
				break;
			case EXCHANGE_CODE:
				builder.withAlternateId(EXCHANGE_CODE, entry.getValue());
				break;
			case LME_CODE:
				builder.withAlternateId(LME_CODE, entry.getValue());
				break;
			default:

			}

		});

		return builder.build();
	}
}
