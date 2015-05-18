package com.rootser.qcruncher.service;

import java.util.Date;
import java.util.List;

import com.rootser.qcruncher.common.AppMsg;

public interface EdgarSearchSvc {
	public AppMsg<List<String>> get10QForDate(AppMsg<Date> date);
}
