package com.rootser.qcruncher.integration.service;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.service.ProcessDelegate;
@Service
public class AvgSp500SvcImpl implements AvgSp500Svc {

	
	public AppMsg<Double> getAvgSp500(AppMsg<Pair<Date, Date>> startEndDates) {
		return new ProcessDelegate<Double, Pair<Date, Date>>().applyPluginProcess(startEndDates, new AvgSp500Plugin());
	}

}
