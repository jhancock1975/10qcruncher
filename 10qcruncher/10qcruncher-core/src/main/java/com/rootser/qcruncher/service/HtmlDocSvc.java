package com.rootser.qcruncher.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.rootser.qcruncher.common.AppMsg;

public interface HtmlDocSvc {
	public AppMsg<Document> getDoc(AppMsg<String> url);
	public List<AppMsg<Document>> getDocs(List<AppMsg<String>> urls);
	
}
