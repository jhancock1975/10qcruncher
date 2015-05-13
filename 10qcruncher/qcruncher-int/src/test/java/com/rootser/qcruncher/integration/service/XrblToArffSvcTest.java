package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.*;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Selector.SelectorParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.service.DocRetrievalSvc;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration-test-config.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class XrblToArffSvcTest {
	
	Logger logger = LoggerFactory.getLogger(XrblToArffSvcTest.class);

	@Autowired
	private XrblToArffSvc xrblToArffSvc;
	
	@Autowired
	private Environment env;

	@Test
	public void testXrblsToArffs() {
		logger.debug("not implemented");
		assertTrue(true);
	}

	@Test
	public void testXrblToArff() {

		String xrblUrl = env.getProperty("xrbl.url");
		
		List<String> urlList = Lists.newArrayList(xrblUrl);

		AppMsg<String> urlMsg = new AppMsg<String>();

		boolean passCond = true;
		
		for (String urlStr: urlList){

			urlMsg.setResult(urlStr);
		
			
			try {

			

			xrblToArffSvc.convertXrbl(urlMsg);
			
			} catch (SelectorParseException e){
				fail("failed on selector string " + e.getMessage());
			}
			logger.debug("--------------------");
		}
		assertTrue(passCond);

	}

}
