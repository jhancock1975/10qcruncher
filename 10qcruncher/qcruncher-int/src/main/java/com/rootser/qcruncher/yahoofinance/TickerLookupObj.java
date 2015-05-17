package com.rootser.qcruncher.yahoofinance;

public class TickerLookupObj {
	private ResultSet ResultSet;

	public ResultSet getResultSet() {
		return ResultSet;
	}

	public void setResultSet(ResultSet ResultSet) {
		this.ResultSet = ResultSet;
	}

	@Override
	public String toString() {
		return "ClassPojo [ResultSet = " + ResultSet + "]";
	}
}
