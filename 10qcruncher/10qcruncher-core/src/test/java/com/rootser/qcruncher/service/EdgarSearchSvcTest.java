package com.rootser.qcruncher.service;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.DateRange;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class EdgarSearchSvcTest {
	
	@Autowired
	private EdgarSearchSvc searchSvc;

	@Test
	public void testGet10QForDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, 2);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		
		DateRange range = new DateRange(cal.getTime(), cal.getTime());
		
		List<String> urlList = searchSvc.get10QForDateRange(new AppMsg<DateRange>(range)).getResult();
		
		assertTrue("EdgarSearchSvc.get10QForDate returned an empty list for " + cal.toString(), CollectionUtils.isNotEmpty(urlList));
	}

}
