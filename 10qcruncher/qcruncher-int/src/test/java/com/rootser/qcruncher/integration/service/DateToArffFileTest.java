package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import com.rootser.qcruncher.common.DateRange;
import com.rootser.qcruncher.integration.common.ArffDataSet;
import com.rootser.qcruncher.service.DocRetrievalSvc;
import com.rootser.qcruncher.service.EdgarSearchSvc;
import com.rootser.qcruncher.service.ParentDocParserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration-test-config.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class DateToArffFileTest {

	Logger logger = LoggerFactory.getLogger(DateToArffFileTest.class);

	@Autowired
	private DateToArffFileSvc dateToArffSvc;
	@Test
	public void getOneDay() throws FileNotFoundException{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2012);
		cal.set(Calendar.DAY_OF_MONTH, 16);
		cal.set(Calendar.MONTH, 4);
		
		AppMsg<String> arffMsg = dateToArffSvc.getArffForDay(cal.getTime());
		
		assertTrue(StringUtils.isNotBlank(arffMsg.getResult()));

	}
	
	@Test
	public void getMultiDay() throws FileNotFoundException {
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, 2012);
		startCal.set(Calendar.DAY_OF_MONTH, 16);
		startCal.set(Calendar.MONTH, 4);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 2012);
		endCal.set(Calendar.DAY_OF_MONTH, 18);
		endCal.set(Calendar.MONTH, 4);
		
		AppMsg<String> arffMsg = dateToArffSvc.getArffForDateRange(new DateRange(startCal.getTime(), endCal.getTime()));
		
		assertTrue(StringUtils.isNotBlank(arffMsg.getResult()));
		
		PrintWriter pw = new PrintWriter(new File("/tmp/10q.arff"));
		pw.print(arffMsg.getResult());
		pw.close();
	}

}
