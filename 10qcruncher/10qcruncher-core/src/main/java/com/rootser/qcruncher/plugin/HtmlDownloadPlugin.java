package com.rootser.qcruncher.plugin;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;

public class HtmlDownloadPlugin implements Plugin<String, Document>{
	
	Logger logger =  LoggerFactory.getLogger(HtmlDownloadPlugin.class);

	@SuppressWarnings("unchecked")
	public AppMsg<Document> process(AppMsg<String> urlStr) {
		try {
			logger.debug("attempting download of " + urlStr.getResult());
			return new AppMsg<Document>(Jsoup.connect(urlStr.getResult()).get());
		} catch (IOException e) {
			return (AppMsg<Document>) CommonCatchLogic.commonCatchLogic(logger, new AppMsg<Document>(), e, 
					"10Q cruncher encountered an input/output exception when trying to download an HTML document.");
		}
	}
	

}
