package com.rootser.qcruncher.service;

import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Service
public class FeedParserSvcImpl implements FeedParserService {

	private static Logger logger = LoggerFactory.getLogger(FeedParserSvcImpl.class);
	public List<String> getNew10QUrls(String urlStr) {
		getFeed(urlStr);
		return null;
	}
	private void getFeed(String urlStr){
		boolean ok = false;
		try {
			URL feedUrl = new URL(urlStr);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));

			List<SyndEntry> entries = feed.getEntries();
			for(SyndEntry entry: entries){
				logger.debug(entry.getTitle());
			}
			

			ok = true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("ERROR: "+ex.getMessage());
		}


		if (!ok) {

			logger.debug("FeedReader reads and prints any RSS/Atom feed type.");
			logger.debug("The first parameter must be the URL of the feed to read.");

		}
	}

}
