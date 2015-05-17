package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CikToTickerSvcTest {
	@Autowired
	private CikToTickerSvc cikSvc;
	@Test
	public void testCikToTick() {
		AppMsg<String> tickerMsg  = cikSvc.cikToTick(new AppMsg<String>("0000812152"));
		assertTrue("CikToTickerSvc returned wrong ticker returned for Coca Cola", tickerMsg.getResult().contains("KO"));
	}

}
