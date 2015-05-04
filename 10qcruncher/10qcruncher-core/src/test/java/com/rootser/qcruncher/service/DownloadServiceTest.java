package com.rootser.qcruncher.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rootser.qcruncher.common.AppMsg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })

public class DownloadServiceTest {
	@Autowired
	private FeedParserService feedSvc;
	@Autowired
	private DownloaderService downloadSvc;
	@Test
	public void testDownloadUrls() {
		AppMsg<String> appMsgUrl = new AppMsg<String>();
		appMsgUrl.setResult("http://www.sec.gov/Archives/edgar/data/831641/000110465915033269/0001104659-15-033269-index.htm");
		List<AppMsg<String>> appMsgUrls = new ArrayList<AppMsg<String>>();
		appMsgUrls.add(appMsgUrl);
		appMsgUrls = downloadSvc.downloadUrls(appMsgUrls, "/tmp");
		appMsgUrl = appMsgUrls.get(0);
		assertTrue(! appMsgUrl.hasMessages() && ! appMsgUrl.hasThrowables());
	}

}
