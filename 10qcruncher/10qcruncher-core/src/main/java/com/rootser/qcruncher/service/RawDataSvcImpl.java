package com.rootser.qcruncher.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.RawData;
@Configuration
@PropertySource("classpath:feed-parser.properties")
public class RawDataSvcImpl implements RawDataSvc {
	
	@Autowired 
	private FeedParserService parserSvc;
	
	@Autowired
	private ParentDocParserService parentDocSvc;
	
	@Autowired
	private DocRetrievalSvc docSvc;

	@Autowired Environment env;
	public RawData getRawData() {
		List<AppMsg<String>> parentDocUrls = parserSvc.getNew10QUrls(env.getProperty("10.q.feed.url"));
		List<AppMsg<String>> tenQUrls = parentDocSvc.get10QHtmUrl(parentDocUrls);
		List<AppMsg<Document>> tenQHtm = docSvc.getHtmlDocs(tenQUrls);
		List<AppMsg<String>> tenQTxt = docSvc.getTxtDocs(tenQUrls);
		return new RawData(tenQTxt, tenQHtm);
	}

}
