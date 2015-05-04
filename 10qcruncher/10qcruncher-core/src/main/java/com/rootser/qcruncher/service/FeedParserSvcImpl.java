package com.rootser.qcruncher.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rootser.qcruncher.common.AppMsg;

@Service
public class FeedParserSvcImpl implements FeedParserService {

	private static Logger logger = LoggerFactory.getLogger(FeedParserSvcImpl.class);

	public List<AppMsg<String>> getNew10QUrls(String urlStr) {
		List<AppMsg<String>> urls = getFeed(urlStr);
		return urls;
	}

	private AppMsg<String> addMsgAndExc(Throwable t, String msg, AppMsg<String> appMsg){
		appMsg.addMsg(msg);
		appMsg.addThrowables(t);
		return appMsg;
	}
	private List<AppMsg<String>> getFeed(String urlStr){

		MalformedURLException malUrl;
		String malUrlMsg;
		URL feedUrl = null;
		AppMsg<String> appMsg = new AppMsg<String>();

		try{
			feedUrl = new URL(urlStr);
		} catch(MalformedURLException e){
			malUrlMsg = "Found improper Internet address " + urlStr;
			appMsg = addMsgAndExc(e, malUrlMsg, appMsg);
			logger.debug(e.getMessage());
		}


		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = null;;

		try {
			feed = input.build(new XmlReader(feedUrl));
		} catch (IllegalArgumentException e) {

			String feedIllArgMsg = "Unable to build XML reader possibly "+
					"because of a problem with URL " + feedUrl;
			appMsg = addMsgAndExc(e, feedIllArgMsg, appMsg);
			logger.debug(e.getMessage());

		} catch (FeedException e) {

			String feedExMsg = "Problem with feed when attempting " +
					"to build XML reader for URL" + feedUrl;
			appMsg = addMsgAndExc(e, feedExMsg, appMsg);
			logger.debug(e.getMessage());

		} catch (IOException e) {

			String ioExMsg = "Input or output error " +
					" when attempting " +
					"to build XML reader for URL" + feedUrl;
			appMsg = addMsgAndExc(e, ioExMsg, appMsg);
			logger.debug(e.getMessage());
		}

		List<AppMsg<String>> appMsgs = new ArrayList<AppMsg<String>>();

		if (appMsg.hasMessages() || appMsg.hasThrowables()){
			appMsgs.add(appMsg);
			return appMsgs;
		} else {

			List<SyndEntry> entries = feed.getEntries();

			for(SyndEntry entry: entries){
				logger.debug("Today's 10-q reports:");
				logger.debug(entry.getTitle());
				logger.debug(entry.getLink());
				for (SyndLink link: entry.getLinks()){
					AppMsg<String> msg = new AppMsg<String>();
					msg.setResult(link.getHref());
					appMsgs.add(msg);
				}
			}
			return appMsgs;
		}
	}
}