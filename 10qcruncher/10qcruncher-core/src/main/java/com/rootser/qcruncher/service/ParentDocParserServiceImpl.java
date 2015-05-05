package com.rootser.qcruncher.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
@Service
@Configuration
@PropertySource("classpath:parent-parser.properties")
public class ParentDocParserServiceImpl implements ParentDocParserService {

	Logger logger = LoggerFactory.getLogger(ParentDocParserServiceImpl.class);

	@Value("${10.q.html.selector}")
	private String htmlSelector;
	
	@Value("${10.q.txt.selector}")
	private String txtSelector;
	
	public List<AppMsg<String>> get10QHtmUrl(List<AppMsg<String>> parentDocList) {
		return get10QUrlList(parentDocList, htmlSelector);
	}
	
	public List<AppMsg<String>> get10QTxtUrl(List<AppMsg<String>> parentDocList) {
		return get10QUrlList(parentDocList, txtSelector);
	}
	
	private List<AppMsg<String>> get10QUrlList(List<AppMsg<String>> parentDocList, String selectorString) {
		List<AppMsg<String>> appMsgs = new ArrayList<AppMsg<String>>();
		for(AppMsg<String> parentDoc: parentDocList){
			appMsgs.add(get10QUrl(parentDoc, selectorString));
		}
		return appMsgs;
	}
	
	public AppMsg<String> get10QHtmUrl(AppMsg<String> parentDoc) {

		return get10QUrl(parentDoc, htmlSelector);
	}

	public AppMsg<String> get10QTxtUrl(AppMsg<String> parentDoc) {
		return get10QUrl(parentDoc, txtSelector);
	}

	private AppMsg<String> get10QUrl(AppMsg<String> parentDoc, String selectorString){
		AppMsg<String> appMsg = new AppMsg<String>();

		if (parentDoc == null){
			logger.debug("parentDoc is null.");
			appMsg.addMsg("10qcruncher received a \"null,\" message when " +
					"trying to get the URL for the HTML version of a 10-Q report.");
		} else {

			String parentDocUrlStr = parentDoc.getResult();


			if (parentDocUrlStr == null){

				logger.debug("ParentDoc has null url string.");
				appMsg.addMsg("10qcruncher tried to download a null URL.");

			} else {

				try {

					Document doc = Jsoup.connect(parentDocUrlStr).get();
					Element tenQUrl= doc.select(selectorString).first();
					String tenQUrlStr = tenQUrl.attributes().get("href"); 
					logger.debug(tenQUrlStr);
					appMsg.setResult(tenQUrlStr);

				} catch (IOException e) {

					commonCatchLogic(appMsg, e);
					appMsg.addMsg("10qcruncher is unable to get parent document at " + parentDocUrlStr);

				} catch(IllegalArgumentException e){

					commonCatchLogic(appMsg, e);
					appMsg.addMsg("10qcruncher thinks that " + parentDocUrlStr +
							" is not a valid URL.");
				}
			}
		}
		return appMsg;
	}

	private void commonCatchLogic(AppMsg<String> appMsg , Exception e){
		logger.debug(e.getMessage());
		appMsg.addThrowables(e);
	}
}
