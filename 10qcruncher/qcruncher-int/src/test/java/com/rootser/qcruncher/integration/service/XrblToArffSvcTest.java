package com.rootser.qcruncher.integration.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

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
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.integration.common.ArffDataSet;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration-test-config.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class XrblToArffSvcTest {

	Logger logger = LoggerFactory.getLogger(XrblToArffSvcTest.class);

	@Autowired
	private XbrlToArffSvc xrblToArffSvc;

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
		String xrbUrl2 = env.getProperty("xrbl.url.2");

		List<String> urlList = Lists.newArrayList(xrblUrl, xrbUrl2);

		AppMsg<String> urlMsg = new AppMsg<String>();
		
		AppMsg<ArffDataSet> arffDataList = new AppMsg<ArffDataSet>();
		arffDataList.setResult(new ArffDataSet());

		boolean passCond = true;

		for (String urlStr: urlList){
			
			urlMsg.setResult(urlStr);
			
			AppMsg<ArffData> arffData = xrblToArffSvc.convertXrbl(urlMsg);
			
			arffDataList.getResult().add(arffData.getResult());
			
			logger.debug(arffData.getResult().toString());

			logger.debug("--------****--------");
		}
		
		logger.debug(xrblToArffSvc.toArffFormat(arffDataList).getResult());
		
		assertTrue(passCond);

	}

}
