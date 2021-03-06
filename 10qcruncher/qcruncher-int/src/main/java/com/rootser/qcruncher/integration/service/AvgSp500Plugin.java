package com.rootser.qcruncher.integration.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;
import com.rootser.qcruncher.plugin.Plugin;

public class AvgSp500Plugin implements Plugin<Pair<Date, Date>, Double> {

	public AvgSp500Plugin(){
		spyCache = new HashMap<AppMsg<Pair<Date, Date>>, Double>();
	}

	Logger logger = LoggerFactory.getLogger(AvgSp500Plugin.class);

	private Calendar getCalendar(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private Map<AppMsg<Pair<Date, Date>>, Double> spyCache;

	public AppMsg<Double> process(AppMsg<Pair<Date, Date>> inputParam) {

		AppMsg<Double> avgMsg = new AppMsg<Double>();

		Double cachedVal = spyCache.get(inputParam);
		if (cachedVal != null){
			avgMsg.setResult(cachedVal);
			avgMsg.addMsg("used cached value for average SPY value for date range" +
					inputParam.getResult().getLeft() + " through "  + inputParam.getResult().getRight());
		} else {

			Calendar startCal = getCalendar(inputParam.getResult().getLeft());
			Calendar endCal = getCalendar(inputParam.getResult().getRight());
			try {
				Stock spy = YahooFinance.get("spy", startCal, endCal, Interval.DAILY);
				Double sum = 0D;
				for (HistoricalQuote quote: spy.getHistory()){
					sum += Double.parseDouble(quote.getAdjClose().toString());
				}
				avgMsg.setResult(sum / spy.getHistory().size());
			} catch(NumberFormatException e){
				CommonCatchLogic.commonCatchLogic(logger, avgMsg, e, "unable to comupte average value of S&P 500 for period");
			}
		}
		return avgMsg;
	}
}
