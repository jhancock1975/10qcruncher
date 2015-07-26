package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.DateRange;
import com.rootser.qcruncher.integration.common.ArffDataSet;
import com.rootser.qcruncher.service.DocRetrievalSvc;
import com.rootser.qcruncher.service.EdgarSearchSvc;
import com.rootser.qcruncher.service.ParentDocParserService;
@Service
public class DateToArffFileSvcImpl implements DateToArffFileSvc {

	Logger logger = LoggerFactory.getLogger(DateToArffFileTest.class);

	@Autowired
	private EdgarSearchSvc searchSvc;

	@Autowired 
	private ParentDocParserService parentDocParserSvc;

	@Autowired 
	private DocRetrievalSvc docRetSvc;

	@Autowired 
	private XbrlToArffSvc xbrlSvc;


	private AppMsg<String> getArffs(Date startDate, Date endDate){

		AppMsg<List<String>> parentUrls = searchSvc.get10QForDateRange(new AppMsg<DateRange>(
				new DateRange(startDate, endDate)));

		List<String> urls = new ArrayList<String>();

		for (String curMsg: parentUrls.getResult()){
			if (! StringUtils.isBlank(curMsg)){
				urls.add(curMsg);
			} else {
				logger.debug("found blank url");
			}
		}


		List<AppMsg<String>> parentUrlMsgs = new ArrayList<AppMsg<String>>();
		for (String url: parentUrls.getResult()){
			AppMsg<String> curAppMsg = new AppMsg<String>(url);
			parentUrlMsgs.add(curAppMsg);

		}

		List<AppMsg<String>> xrblUrls = parentDocParserSvc.get10QXrblUrl(parentUrlMsgs);
		urls = new ArrayList<String>();
		for (AppMsg<String> curMsg: xrblUrls){
			if ( ! StringUtils.isBlank(curMsg.getResult()) && ! curMsg.hasErrors()){
				urls.add(curMsg.getResult());
			} else if (! curMsg.hasErrors()){
				fail("found blank url without an error to explain why");
			}
		}

		AppMsg<ArffDataSet> dataSet = xbrlSvc.convertXrbls(xrblUrls);

		return xbrlSvc.toArffFormat(dataSet);
	}

	public AppMsg<String> getArffForDay(Date date) {
		return getArffs(date, date);

	}

	public AppMsg<String> getArffForDateRange(DateRange range) {
		return getArffs(range.getStartDate(), range.getEndDate());

	}

}
