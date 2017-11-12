package com.gognamunish.referencedata.messaging;

import java.util.Objects;

import com.gognamunish.referencedata.process.InstrumentProcessor;

public class SourceInstrumentListener implements InstrumentListener {

	private InstrumentProcessor processor;

	public SourceInstrumentListener(InstrumentProcessor processor) {
		this.processor = processor;
	}

	public void onMessage(Message message) {

		Objects.requireNonNull(message, "Incoming message is null");
		Objects.requireNonNull(message.getSource(), "Incoming message's source is null");
		Objects.requireNonNull(message.getInstrument(), "Incoming message's instrument is null");

		processor.process(message);

	}

}
