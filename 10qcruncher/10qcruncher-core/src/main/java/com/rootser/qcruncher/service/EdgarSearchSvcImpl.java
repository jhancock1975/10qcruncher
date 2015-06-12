package com.rootser.qcruncher.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.DateRange;
import com.rootser.qcruncher.plugin.Plugin;

@Service
public class EdgarSearchSvcImpl implements EdgarSearchSvc {
	
	@Autowired 
	@Qualifier("EdgarSearchPlugin")
	private Plugin<DateRange, List<String>> edgarSearchPlugin;
	
	
	public AppMsg<List<String>> get10QForDateRange(AppMsg<DateRange> dateRangeMsg) {
	
		return new ProcessDelegate<List<String>, DateRange>().applyPluginProcess(dateRangeMsg, edgarSearchPlugin);
	}

}
