package com.rootser.qcruncher.integration.service;

import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import com.rootser.qcruncher.common.AppMsg;

public interface AvgSp500Svc {

	/**
	 * calculates average value of Standard and Poor's 500
	 * index between startDate and endDate using daily 
	 * closing price of the echange traded fund with ticker
	 * symbol SPY.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public AppMsg<Double> getAvgSp500(AppMsg<Pair<Date, Date>> startEndDates);

}
