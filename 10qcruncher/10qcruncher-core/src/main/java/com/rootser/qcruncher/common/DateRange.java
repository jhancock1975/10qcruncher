package com.rootser.qcruncher.common;

import java.util.Date;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DateRange {
	
	public DateRange(Date startDate, Date endDate){
		this.dates = new ImmutablePair<Date, Date>(startDate, endDate);
	}
	
	private Pair<Date, Date> dates;
	
	public Date getStartDate(){
		return this.dates.getLeft();
	}
	
	public Date getEndDate(){
		return this.dates.getRight();
	}
}
