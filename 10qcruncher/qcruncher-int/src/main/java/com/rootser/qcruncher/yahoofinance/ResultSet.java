package com.rootser.qcruncher.yahoofinance;

public class ResultSet {
	private Result[] Result;

	private String Query;

	public Result[] getResult() {
		return Result;
	}

	public void setResult(Result[] Result) {
		this.Result = Result;
	}

	public String getQuery() {
		return Query;
	}

	public void setQuery(String Query) {
		this.Query = Query;
	}

	@Override
	public String toString() {
		return "ClassPojo [Result = " + Result + ", Query = " + Query + "]";
	}
}
