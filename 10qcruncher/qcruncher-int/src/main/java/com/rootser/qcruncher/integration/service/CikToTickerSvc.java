package com.rootser.qcruncher.integration.service;

import com.rootser.qcruncher.common.AppMsg;

public interface CikToTickerSvc {
	public AppMsg<String> cikToTick(AppMsg<String> cik);
}
