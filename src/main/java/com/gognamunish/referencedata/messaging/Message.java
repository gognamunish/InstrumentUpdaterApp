package com.gognamunish.referencedata.messaging;

import com.gognamunish.referencedata.model.Instrument;

public class Message {

	private final String source;
	private final Instrument instrument;

	public Message(String source, Instrument instrument) {
		super();
		this.source = source;
		this.instrument = instrument;
	}

	public String getSource() {
		return source;
	}

	public Instrument getInstrument() {
		return instrument;
	}

}
