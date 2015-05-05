package com.rootser.qcruncher.service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
@Service
public class ParentDocParserServiceImpl implements ParentDocParserService {
	
	Logger logger = LoggerFactory.getLogger(ParentDocParserServiceImpl.class);
	
	enum DocType{
		html, txt;
	}

	public List<AppMsg<String>> get10QHtmUrl(List<AppMsg<String>> parentDocList) {
		// TODO Auto-generated method stub
		return null;
	}

	public AppMsg<String> get10QHtmUrl(AppMsg<String> parentDoc) {
		if (parentDoc == null){
			logger.debug("parentDoc is null.");
		} else {
			
			String parentDocUrlStr = parentDoc.getResult();
			AppMsg<String> appMsg = new AppMsg<String>();
			
			if (parentDocUrlStr == null){
				
				logger.debug("ParentDoc has null url string.");
				appMsg.addMsg("10qcruncher tried to download a null URL.");
				
			} else {
				
				try {
				
					Document doc = Jsoup.connect(parentDocUrlStr).get();
					Element tenQUrl= doc.select("a[href~=.*10q.*htm]").first();
					logger.debug(tenQUrl.html());
				
				} catch (IOException e) {
					logger.debug(e.getMessage());
					appMsg.addThrowables(e);
					appMsg.addMsg("10qcruncher is unable to get parent document at " + parentDocUrlStr);
				}
			}
		}
		return null;
	}

	public List<AppMsg<String>> get10QTxtUrl(List<AppMsg<String>> parentDocList) {
		// TODO Auto-generated method stub
		return null;
	}

	public AppMsg<String> get10QTxtUrl(AppMsg<String> parentDoc) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
