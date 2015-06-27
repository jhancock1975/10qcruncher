package com.rootser.qcruncher.integration.service;

import java.util.Date;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.DateRange;

public interface DateToArffFileSvc {
	public AppMsg<String> getArffForDay(Date date);
	
	public AppMsg<String> getArffForDateRange(DateRange range);
}
