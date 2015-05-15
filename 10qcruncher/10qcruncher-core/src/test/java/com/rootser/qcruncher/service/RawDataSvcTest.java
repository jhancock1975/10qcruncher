package com.rootser.qcruncher.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
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

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.RawData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")

public class RawDataSvcTest {
	
	Logger logger = LoggerFactory.getLogger(RawDataSvcTest.class);
	
	@Autowired 
	private RawDataSvc rawDataSvc;
	
	private RawData result; 
	
	@Before
	public  void initResult(){
		if (result == null){
			result = rawDataSvc.getRawData();
		}
	}
	
	@Autowired
	private Environment env;
	
	@Test
	public void testGetRawData() {
		
		boolean passCond = CollectionUtils.isNotEmpty(result.getHtmlData()) &&
				CollectionUtils.isNotEmpty(result.getTxtData());
		assertTrue("rawDataSvc returned an empty text or html list", passCond);
	}
	
	@Test public void testSelector(){
		
		List<AppMsg<Document>> docs = result.getHtmlData();
		Iterator<AppMsg<Document>> i = docs.iterator();
		int j = 1;
		AppMsg<Document> doc = null;
		while (i.hasNext() && (doc == null || doc.hasErrors())){
			doc = i.next();
			if ( doc.hasErrors()){
				logger.debug("found doc with errors");
			} 
			j++;
		}
		if (j == docs.size()){
			logger.debug("no docs without errors in result");
			fail("no docs without errors in result");
		} else {
			Element cashAndCasEquiv = doc.getResult().select(":contains(Cash and cash equivalents)").first();
			logger.debug(cashAndCasEquiv.toString());
			assertTrue(true);
		}
	}

}
