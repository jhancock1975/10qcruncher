package com.rootser.qcruncher.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class DownloadServiceTest {
	
	
	
	@Autowired
	private DownloaderService downloadSvc;
	
	@Autowired
	private Environment env;
	@Test
	public void testDownloadUrls() {
		
		AppMsg<String> appMsgUrl = new AppMsg<String>();
		appMsgUrl.setResult(env.getProperty("10.q.parent.url"));
		
		@SuppressWarnings("unchecked")
		List<AppMsg<String>> appMsgUrls = Lists.newArrayList(appMsgUrl);
		
		appMsgUrls = downloadSvc.downloadUrls(appMsgUrls, "/tmp");
		
		appMsgUrl = appMsgUrls.get(0);
		assertTrue(! appMsgUrl.hasMessages() && ! appMsgUrl.hasThrowables());
	}
	
	@Test
	/**
	 * Failure of this test may indicate a problem
	 * with exception handling.
	 */
	public void testMalformedUrl(){
		AppMsg<String> appMsgUrl = new AppMsg<String>();
		appMsgUrl.setResult("rubbish url");
		
		@SuppressWarnings("unchecked")
		List<AppMsg<String>> appMsgUrls = Lists.newArrayList(appMsgUrl);
		
		appMsgUrls = downloadSvc.downloadUrls(appMsgUrls, "/tmp");
		
		appMsgUrl = appMsgUrls.get(0);
		assertTrue( appMsgUrl.hasMessages() &&  appMsgUrl.hasThrowables());
		
	}

}
