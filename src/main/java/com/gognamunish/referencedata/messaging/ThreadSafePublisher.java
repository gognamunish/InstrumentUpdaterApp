package com.gognamunish.referencedata.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.gognamunish.referencedata.model.Instrument;

public class ThreadSafePublisher {

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	protected final Lock readLock = readWriteLock.readLock();
	protected final Lock writeLock = readWriteLock.writeLock();

	private List<SourceInstrumentListener> listeners = new ArrayList<>();
	private String source;

	public ThreadSafePublisher(String source) {
		this.source = source;
	}

	public void publish(Instrument instrument) {
		notifySourceInstrumentListeners(instrument);
	}

	public SourceInstrumentListener registerListener(SourceInstrumentListener listener) {
		this.writeLock.lock();
		try {
			this.listeners.add(listener);
		} finally {
			this.writeLock.unlock();
		}
		return listener;
	}

	private void notifySourceInstrumentListeners(Instrument instrument) {
		this.readLock.lock();
		try {
			this.listeners.forEach(listener -> listener.onMessage(new Message(source, instrument)));
		} finally {
			this.readLock.unlock();
		}
	}

}
