package com.rootser.qcruncher.yahoofinance;

public class Result {
	private String typeDisp;

	private String symbol;

	private String name;

	private String type;

	private String exchDisp;

	private String exch;

	public String getTypeDisp() {
		return typeDisp;
	}

	public void setTypeDisp(String typeDisp) {
		this.typeDisp = typeDisp;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExchDisp() {
		return exchDisp;
	}

	public void setExchDisp(String exchDisp) {
		this.exchDisp = exchDisp;
	}

	public String getExch() {
		return exch;
	}

	public void setExch(String exch) {
		this.exch = exch;
	}

	@Override
	public String toString() {
		return "ClassPojo [typeDisp = " + typeDisp + ", symbol = " + symbol
				+ ", name = " + name + ", type = " + type + ", exchDisp = "
				+ exchDisp + ", exch = " + exch + "]";
	}
}
