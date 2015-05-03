package com.rootser.qcruncher.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })

public class DownloadServiceTest {
	@Autowired
	private FeedParserService feedSvc;
	@Autowired
	private DownloaderService downloadSvc;
	@Test
	public void testDownloadUrls() {
		List<String> urls = feedSvc.getNew10QUrls("http://www.sec.gov/cgi-bin/browse-edgar?action=getcurrent&type=10-q&company=&dateb=&owner=include&start=0&count=40&output=atom");
		downloadSvc.downloadUrls(urls, "/tmp");
		assertTrue(true);
	}

}
