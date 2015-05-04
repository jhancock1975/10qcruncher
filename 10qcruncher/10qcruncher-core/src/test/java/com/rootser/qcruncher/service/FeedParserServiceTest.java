package com.rootser.qcruncher.service;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rootser.qcruncher.common.AppMsg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })
public class FeedParserServiceTest extends TestCase {
	
	@Autowired
	FeedParserService feedSvc;
	@Test
	public void testGetNew10QUrls() {
		List<AppMsg<String>> urls = feedSvc.getNew10QUrls("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=10-q&company=&dateb=&owner=include&start=0&count=40&output=atom");
		boolean passCond = urls.size() != 0 &&
				! urls.get(0).hasMessages() &&
				! urls.get(0).hasThrowables();
		assertTrue(passCond);
	}

}
