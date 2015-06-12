package com.rootser.qcruncher.service;

import java.util.List;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.DateRange;

public interface EdgarSearchSvc {
	
	public AppMsg<List<String>> get10QForDateRange(AppMsg<DateRange> dateRange);
}
