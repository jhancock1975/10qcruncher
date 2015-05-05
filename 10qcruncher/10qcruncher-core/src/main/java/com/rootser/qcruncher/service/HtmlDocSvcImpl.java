package com.rootser.qcruncher.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;

@Service
public class HtmlDocSvcImpl implements HtmlDocSvc{
	
	Logger logger = LoggerFactory.getLogger(HtmlDocSvcImpl.class);
	
	public AppMsg<Document> getDoc(AppMsg<String> urlMsg) {
		AppMsg<Document> doc = new AppMsg<Document>();
		if (urlMsg == null){
			logger.debug("AppMsg for getDoc is null");
			doc.addMsg("10qcruncher is trying to download a document with no information about any document.");
			doc.setHasErrors(true);
		} else {
			String url = urlMsg.getResult();
			if (url == null){
				logger.debug("Url from AppMsg for getDoc is null.");
				doc.addMsg("10qcruncher is trying to download a null URL.");
				doc.setHasErrors(true);
			} else {
				try {
					 doc.setResult(Jsoup.connect(url).get());
				} catch (IOException e) {

					commonCatchLogic(doc, e);
					doc.addMsg("10qcruncher is unable to get parent document at " + url);

				} catch(IllegalArgumentException e){

					commonCatchLogic(doc, e);
					doc.addMsg("10qcruncher thinks that " + url +
							" is not a valid URL.");
				}
			}
		}
		return doc;
	}
	
	private void commonCatchLogic(AppMsg<?> appMsg , Exception e){
		logger.debug(e.getMessage());
		appMsg.addThrowables(e);
		appMsg.setHasErrors(true);
	}

	@SuppressWarnings("unchecked")
	public List<AppMsg<Document>> getDocs(List<AppMsg<String>> urls) {
		AppMsg<Document> doc = new AppMsg<Document>();
		if (urls == null){
			logger.debug("AppMsg for getDocs (list method) is null");
			doc.addMsg("10qcruncher is trying to download a list of documents with a non-existant list.");
			doc.setHasErrors(true);
			return (List<AppMsg<Document>>) Lists.newArrayList(doc);
		} else {
			List<AppMsg<Document>> docs = new ArrayList<AppMsg<Document>>();
			for (AppMsg<String> url : urls){
				docs.add(getDoc(url));
			}
			return(docs);
		}
	}
	

}
