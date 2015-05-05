package com.rootser.qcruncher.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.rootser.qcruncher.common.AppMsg;

public interface DocRetrievalSvc {
	
	public AppMsg<String> getTxtDoc(AppMsg<String> appMsg);
	
	public List<AppMsg<String>> getTxtDocs(List<AppMsg<String>> appMsgs);
	
	public AppMsg<Document> getHtmlDoc(AppMsg<String> appMsg);
	
	public List<AppMsg<Document>> getHtmlDocs(List<AppMsg<String>> appMsgs);
}
