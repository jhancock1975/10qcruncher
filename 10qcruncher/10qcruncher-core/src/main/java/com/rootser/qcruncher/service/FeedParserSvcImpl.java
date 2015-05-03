package com.rootser.qcruncher.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class FeedParserSvcImpl implements FeedParserService {

	private static Logger logger = LoggerFactory.getLogger(FeedParserSvcImpl.class);

	public List<String> getNew10QUrls(String urlStr) {
		List<String> urls = getFeed(urlStr);
		return urls;
	}

	private List<String> getFeed(String urlStr){
		List<String> result = new ArrayList<String>();
		try{
			URL feedUrl = new URL(urlStr);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			List<SyndEntry> entries = feed.getEntries();

			for(SyndEntry entry: entries){
				logger.debug("Today's 10-q reports:");
				logger.debug(entry.getTitle());
				logger.debug(entry.getLink());
				result.add(entry.getLink());
			}
		} catch(MalformedURLException e){
			logger.debug(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		} catch (FeedException e) {
			logger.debug(e.getMessage());
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return result;
	}
}
