package com.rootser.qcruncher.service;

import java.net.MalformedURLException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rootser.qcruncher.common.AppMsg;

//these two annotations for loading spring context in
//unit tests:
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })

//these two annotations for using properties files
@Configuration
@PropertySource("classpath:service-tests.properties")

public class FeedParserServiceTest extends TestCase {
	
	@Autowired
	private FeedParserService feedSvc;
	
	@Autowired
	private Environment env;
	
	@Test
	public void testGetNew10QUrls() {
		List<AppMsg<String>> urls = feedSvc.getNew10QUrls(env.getProperty("10.q.feed.url"));
		boolean passCond = urls.size() != 0 &&
				! urls.get(0).hasMessages() &&
				! urls.get(0).hasThrowables();
		assertTrue(passCond);
	}
	
	@Test
	/**
	 * if this test fails
	 * it means something might be
	 * wrong with error handling
	 */
	public void testRubbishFeedUrl(){
		List<AppMsg<String>> urls = feedSvc.getNew10QUrls("rubbish url");
		AppMsg<String>  appMsg = urls.get(0);
		
		boolean passCond = appMsg.hasMessages() &&
					appMsg.hasThrowables();
					
	}
}
