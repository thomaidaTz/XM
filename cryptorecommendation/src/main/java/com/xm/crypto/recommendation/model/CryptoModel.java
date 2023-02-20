package com.xm.crypto.recommendation.model;

import java.util.Date;
import java.lang.StringBuffer;

import org.json.JSONObject;

import java.math.BigDecimal;

public class CryptoModel {
	
	private Date timestamp;
	private String symbol;
	private BigDecimal price;
	
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		StringBuffer toReturn = new StringBuffer("{\n");
		toReturn.append("\"timeStamp\": " + JSONObject.quote(this.getTimestamp().toString()) + ",\n");
		toReturn.append("\"symbol\": " + JSONObject.quote(this.getSymbol()) + ",\n");
		toReturn.append("\"price\": " + JSONObject.quote(this.getPrice().toString()) + "\n");
		toReturn.append("}");
		return toReturn.toString();
	}

}
