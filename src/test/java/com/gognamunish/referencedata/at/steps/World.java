package com.gognamunish.referencedata.at.steps;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gognamunish.referencedata.messaging.SourceInstrumentListener;
import com.gognamunish.referencedata.messaging.ThreadSafePublisher;
import com.gognamunish.referencedata.model.Instrument;
import com.gognamunish.referencedata.process.InstrumentProcessor;


public class World {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentUpdaterSteps.class);

	private static World instance = new World();
	private InstrumentProcessor processor = new InstrumentProcessor();
	private SourceInstrumentListener listener = new SourceInstrumentListener(processor);
	private Map<String, ThreadSafePublisher> publishers = new HashMap<>();
	private Map<String, Instrument> instrumentsToPublish = new HashMap<>();

	private World() {
	}

	public static World getInstance() {
		return instance;
	}

	public void addPublisher(String publisherSource) {
		ThreadSafePublisher publisher = new ThreadSafePublisher(publisherSource);
		publisher.registerListener(listener);
		publishers.put(publisherSource, publisher);
		LOGGER.info("Publisher added for Source {}", publisherSource);
	}

	public void publish(String source, String instrumentCode) {
		Instrument instrument = instrumentsToPublish.get(instrumentCode);
		publishers.get(source).publish(instrument);

	}

	public void addInstrument(Instrument instrument) {
		instrumentsToPublish.put(instrument.getCode(), instrument);
		LOGGER.info("Adding Instrument {}", instrument);
	}

	public Instrument getLastInternallyPublishedInstrument() {
		return processor.getLastInternallyPublishedInstrument();
	}

}
