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
public class HtmlToArffSvcTest {
	
	Logger logger = LoggerFactory.getLogger(HtmlToArffSvcTest.class);

	@Autowired
	private HtmlToArffSvc htmlToArffSvc;

	@Autowired
	private DocRetrievalSvc docSvc;
	
	@Autowired
	private Environment env;

	@Test
	public void testHtmlsToArffs() {
		logger.debug("not implemented");
		assertTrue(true);
	}

	@Test
	public void testHtmlToArff() {

		String niceFormat10Q = env.getProperty("doc.retrieval.3m.10.q.url");
		String spectrum10Q = env.getProperty("doc.retrieval.spectrum.10.q.url");
		String pg10Q = env.getProperty("doc.retrieval.pg.10.q.url");
		
		List<String> urlList = Lists.newArrayList(niceFormat10Q, spectrum10Q, pg10Q);

		AppMsg<String> urlMsg = new AppMsg<String>();

		boolean passCond = false;
		
		for (String urlStr: urlList){

			urlMsg.setResult(urlStr);
			
			AppMsg<Document> tenQReport = docSvc.getHtmlDoc(urlMsg);
			
			try {

			 passCond = ! tenQReport.hasErrors() &&
					tenQReport.getResult().title() != null;

			htmlToArffSvc.htmlToArff(tenQReport);
			
			} catch (SelectorParseException e){
				fail("failed on selector string " + e.getMessage());
			}
			logger.debug("--------------------");
		}
		assertTrue(passCond);

	}

}
