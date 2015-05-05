package com.rootser.qcruncher.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;
import com.rootser.qcruncher.plugin.DownloadPlugin;

public class DownloadDelegate<T> {
	Logger logger = LoggerFactory.getLogger(DownloadDelegate.class);
	
	
	public  AppMsg<T> getDoc(AppMsg<String> urlMsg, DownloadPlugin<T> plugin){
		AppMsg<T> doc = new AppMsg<T>();
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
					doc.setResult(plugin.download(url));
				} catch (IOException e) {

					CommonCatchLogic.commonCatchLogic(logger, doc, e);
					doc.addMsg("10qcruncher is unable to get parent document at " + url);

				} catch(IllegalArgumentException e){

					CommonCatchLogic.commonCatchLogic(logger, doc, e);
					doc.addMsg("10qcruncher thinks that " + url +
							" is not a valid URL.");
				}
			}
		}
		return doc;
	}
	@SuppressWarnings("unchecked")
	public List<AppMsg<T>> getDocs(List<AppMsg<String>> urls, DownloadPlugin<T> plugin){
		
		AppMsg<T> doc = new AppMsg<T>();
		
		if (urls == null){
			
			logger.debug("AppMsg for getTxtDocs (list method) is null");
			doc.addMsg("10qcruncher is trying to download a list of documents with a non-existant list.");
			doc.setHasErrors(true);
			return (List<AppMsg<T>>) Lists.newArrayList(doc);
			
		} else {
			
			List<AppMsg<T>> docs = new ArrayList<AppMsg<T>>();
			for (AppMsg<String> url : urls){
				docs.add(getDoc(url, plugin));
			}
			
			return(docs);
		}
	}
}
