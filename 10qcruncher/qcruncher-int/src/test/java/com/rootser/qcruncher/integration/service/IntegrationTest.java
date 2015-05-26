package com.rootser.qcruncher.integration.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffDataSet;
import com.rootser.qcruncher.service.DocRetrievalSvc;
import com.rootser.qcruncher.service.EdgarSearchSvc;
import com.rootser.qcruncher.service.ParentDocParserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration-test-config.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class IntegrationTest {

	Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

	@Autowired
	private EdgarSearchSvc searchSvc;

	@Autowired 
	private ParentDocParserService parentDocParserSvc;

	@Autowired 
	private DocRetrievalSvc docRetSvc;

	@Autowired 
	private XrblToArffSvc xrblSvc;

	@Test
	public void test() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.DAY_OF_MONTH, 16);
		cal.set(Calendar.MONTH, 4);

		AppMsg<List<String>> parentUrls = searchSvc.get10QForDate(new AppMsg<Date>(cal.getTime()));

		List<AppMsg<String>> parentUrlMsgs = new ArrayList<AppMsg<String>>();
		for (String url: parentUrls.getResult()){
			AppMsg<String> curAppMsg = new AppMsg<String>(url);
			parentUrlMsgs.add(curAppMsg);
		}

		List<AppMsg<String>> xrblUrls = parentDocParserSvc.get10QXrblUrl(parentUrlMsgs);

		AppMsg<ArffDataSet> dataSet = xrblSvc.convertXrbls(xrblUrls);

		AppMsg<String> arff = xrblSvc.toArffFormat(dataSet);

		logger.debug(arff.getResult());
	}

}
