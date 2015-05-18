package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration-test-config.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class AvgSp500Test {
	
	Logger logger = LoggerFactory.getLogger(AvgSp500Test.class);
	
	@Autowired
	AvgSp500Svc spSvc;

	@Test
	public void testGetAvgSp500() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2001);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		Date startDate = cal.getTime();
		
		cal.set(Calendar.YEAR, 2001);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		Date endDate = cal.getTime();
		
		Double avg = spSvc.getAvgSp500(
				new AppMsg<Pair<Date, Date>>(
						new ImmutablePair<Date, Date>(startDate, endDate))
				).getResult();
		
		logger.debug(avg.toString());
		
		assertTrue(avg != null);
		
	}

}
