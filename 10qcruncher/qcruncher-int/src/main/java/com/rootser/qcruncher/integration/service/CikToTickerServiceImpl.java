package com.rootser.qcruncher.integration.service;

import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.service.ProcessDelegate;
@Service
public class CikToTickerServiceImpl implements CikToTickerSvc {

	
	
	public AppMsg<String> cikToTick(AppMsg<String> cik) {
		return new ProcessDelegate<String, String>().applyPluginProcess(cik, new CikToTickPlugin());
	}

}
