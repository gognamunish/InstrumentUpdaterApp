package com.gognamunish.referencedata.process;

import java.util.HashMap;
import java.util.Map;

import com.gognamunish.referencedata.factory.InstrumentMergerFactory;
import com.gognamunish.referencedata.factory.InstrumentMergerFactory.InstrumentMerger;
import com.gognamunish.referencedata.messaging.Message;
import com.gognamunish.referencedata.model.Instrument;

public class InstrumentProcessor {

	private Instrument lastInternallyPublishedInstrument;
	private Map<String, Instrument> messages = new HashMap<>();

	public void process(Message message) {
		Instrument arrivingInstrument = message.getInstrument();

		Instrument existingInstrument = messages.get(arrivingInstrument.getKey());

		InstrumentMerger instrumentMerger = InstrumentMergerFactory.generateForExchange(message.getSource());
		Instrument updatedInstrument = instrumentMerger.merge(existingInstrument, arrivingInstrument);

		publishInternally(updatedInstrument);

	}

	private void publishInternally(Instrument updatedInstrument) {

		// we can again publish this message to some listener queue, not doing
		// so for sake of no benefit for this test

		messages.put(updatedInstrument.getKey(), updatedInstrument);
		lastInternallyPublishedInstrument = updatedInstrument;
	}

	// for testing only
	public Instrument getLastInternallyPublishedInstrument() {
		return lastInternallyPublishedInstrument;
	}

}
