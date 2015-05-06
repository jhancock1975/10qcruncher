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

public class ParentDocParserServiceTest {

	@Autowired
	private Environment env;

	@Autowired
	private ParentDocParserService parseSvc;

	private AppMsg<String> getUrlMsg(String url){
		AppMsg<String> appMsgUrl = new AppMsg<String>();
		appMsgUrl.setResult(url);
		return appMsgUrl;
	}

	@Value("${10.q.parent.url}")
	String tenQParentUrl;

	@Test
	public void testGet10QHtmUrlList() {
		
		AppMsg<String> appMsg = getUrlMsg(tenQParentUrl);

		@SuppressWarnings("unchecked")
		List<AppMsg<String>> parentUrls = Lists.newArrayList(appMsg, appMsg, appMsg);

		List<AppMsg<String>> appMsgs = parseSvc.get10QHtmUrl(parentUrls);

		for(int i = 0; i < appMsgs.size(); i++){
			AppMsg<String> result = appMsgs.get(i);
			boolean passCond = ! (result.hasMessages() && result.hasThrowables()) && 
					result.getResult().endsWith("10q.htm");
			assertTrue("unable to parse 10q html url from good parent doc url on " 
					+ i + "th iteration", passCond );
		}
	}

	@Test
	public void testGet10QHtmUrl() {
		AppMsg<String> appMsgUrl = getUrlMsg(tenQParentUrl);

		AppMsg<String> result = parseSvc.get10QHtmUrl(appMsgUrl);

		boolean passCond = !(result.hasMessages() && result.hasThrowables())
				&& result.getResult().endsWith("10q.htm");

		assertTrue(passCond);
	}

	@Test
	public void testGet10Q_Bad_HtmUrl() {
		
		AppMsg<String> appMsg = getUrlMsg("rubbish");

		AppMsg<String> result = parseSvc.get10QHtmUrl(appMsg);

		assertTrue(result.hasMessages() && result.hasThrowables());
	}

	@Test
	public void testGet10QTxtUrlList() {
		AppMsg<String> appMsg = getUrlMsg(tenQParentUrl);

		@SuppressWarnings("unchecked")
		List<AppMsg<String>> parentUrls = Lists.newArrayList(appMsg, appMsg, appMsg);

		List<AppMsg<String>> appMsgs = parseSvc.get10QTxtUrl(parentUrls);

		for(int i = 0; i < appMsgs.size(); i++){
			AppMsg<String> result = appMsgs.get(i);
			boolean passCond = ! (result.hasMessages() && result.hasThrowables()) && 
					result.getResult().endsWith(".txt");
			assertTrue("unable to parse 10q txt url from good parent doc url on " 
					+ i + "th iteration", passCond );
		}
	}

	@Test
	public void testGet10QTxtUrl() {
		
		AppMsg<String> appMsgUrl = getUrlMsg(tenQParentUrl);

		AppMsg<String> result = parseSvc.get10QTxtUrl(appMsgUrl);

		boolean passCond = !(result.hasMessages() && result.hasThrowables())
				&& result.getResult().endsWith(".txt");

		assertTrue(passCond);
	}

}
