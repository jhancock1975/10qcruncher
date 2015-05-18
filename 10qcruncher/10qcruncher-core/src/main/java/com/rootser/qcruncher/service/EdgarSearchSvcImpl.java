package com.rootser.qcruncher.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.plugin.Plugin;

@Service
public class EdgarSearchSvcImpl implements EdgarSearchSvc {
	
	@Autowired 
	@Qualifier("EdgarSearchPlugin")
	private Plugin<Date, List<String>> edgarSearchPlugin;
	public AppMsg<List<String>> get10QForDate(AppMsg<Date> dateMsg) {
		return new ProcessDelegate<List<String>, Date>( ).applyPluginProcess(dateMsg, edgarSearchPlugin);
	}

}
