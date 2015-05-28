package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	
	private void dumpListToFile(List<String> strList, String fileName){
		Collections.sort(strList);
		try {
			PrintWriter pw = new PrintWriter(new File("/tmp/parenturls"));
			for (String s: strList){
				pw.println(s);
			}
			pw.close();
		} catch(IOException e){
			logger.debug("couldn't open tmp file for debugging output");
		}
	}

	@Test
	public void test() throws FileNotFoundException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.DAY_OF_MONTH, 16);
		cal.set(Calendar.MONTH, 4);

		AppMsg<List<String>> parentUrls = searchSvc.get10QForDate(new AppMsg<Date>(cal.getTime()));
		List<String> urls = new ArrayList<String>();
		
		for (String curMsg: parentUrls.getResult()){
			if (! StringUtils.isBlank(curMsg)){
				urls.add(curMsg);
			} else {
				logger.debug("found blank url");
				fail("found blank url from edgar search service");
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

		AppMsg<ArffDataSet> dataSet = xrblSvc.convertXrbls(xrblUrls);

		AppMsg<String> arff = xrblSvc.toArffFormat(dataSet);

		assertTrue(arff.getResult() != null);
		
		PrintWriter pw = new PrintWriter(new File("/tmp/10qdata.arff"));
		pw.println(arff.getResult());
		pw.close();
		
		
	}

}
