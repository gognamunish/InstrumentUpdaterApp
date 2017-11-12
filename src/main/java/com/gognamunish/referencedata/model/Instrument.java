package com.gognamunish.referencedata.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gognamunish.referencedata.factory.KeyGeneratorFactory;

public class Instrument {

	private String code;
	private String key;
	private String label;
	private String source;
	private String market;
	private boolean tradable;
	private Date tradeDate;
	private Date deliveryDate;
	private Map<String, String> alternateIds = new HashMap<String, String>();
	private Map<String, String> optionalFields = new HashMap<String, String>();

	public Instrument(Builder builder) {
		this.code = builder.code;
		this.label = builder.label;
		this.source = builder.source;
		this.market = builder.market;
		this.tradable = builder.tradable;
		this.tradeDate = builder.tradeDate;
		this.deliveryDate = builder.deliveryDate;
		this.alternateIds = builder.alternateIds;
		this.optionalFields = builder.optionalFields;
		this.key = KeyGeneratorFactory.generateForExchange(source).getKey(this);
	}

	public String getAlternateId(String key) {
		return alternateIds.getOrDefault(key, "");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public boolean isTradable() {
		return tradable;
	}

	public void setTradable(boolean tradable) {
		this.tradable = tradable;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Map<String, String> getAlternateIds() {
		return alternateIds;
	}

	public void setAlternateIds(Map<String, String> alternateIds) {
		this.alternateIds = alternateIds;
	}

	public Map<String, String> getOptionalFields() {
		return optionalFields;
	}

	public void setOptionalFields(Map<String, String> optionalFields) {
		this.optionalFields = optionalFields;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String code;
		private String label;
		private String source;
		private String market;
		private Date tradeDate;
		private Date deliveryDate;
		private boolean tradable = true;
		private Map<String, String> alternateIds = new HashMap<>();
		private Map<String, String> optionalFields = new HashMap<>();

		public Builder withCode(String code) {
			this.code = code;
			return this;
		}

		public Builder tradable(boolean tradable) {
			this.tradable = tradable;
			return this;
		}

		public Builder withLabel(String label) {
			this.label = label;
			return this;
		}

		public Builder withSource(String exchange) {
			this.source = exchange;
			return this;
		}

		public Builder withMarket(String market) {
			this.market = market;
			return this;
		}

		public Builder withTradeDate(Date tradeDate) {
			this.tradeDate = tradeDate;
			return this;
		}

		public Builder withDeliveryDate(Date deliveryDate) {
			this.deliveryDate = deliveryDate;
			return this;
		}

		public Builder withAlternateIds(Map<String, String> alternateIds) {
			this.alternateIds = alternateIds;
			return this;
		}

		public Builder withOptionalFields(Map<String, String> optionalFields) {
			this.optionalFields = optionalFields;
			return this;
		}

		public Instrument build() {
			return new Instrument(this);
		}

		public Builder withAlternateId(String id, String value) {
			alternateIds.put(id, value);
			return this;
		}

	}

	@Override
	public String toString() {
		return "Instrument [code=" + code + ", key=" + key + ", label=" + label + ", source=" + source + ", market="
				+ market + ", tradable=" + tradable + ", tradeDate=" + tradeDate + ", deliveryDate=" + deliveryDate
				+ ", alternateIds=" + alternateIds + ", optionalFields=" + optionalFields + "]";
	}

}
