package com.rootser.qcruncher.integration.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;

public interface HtmlToArffSvc {
	List<AppMsg<ArffData>> htmlsToArffs(List<AppMsg<Document>> tenQReports);
	
	AppMsg<ArffData> htmlToArff(AppMsg<Document> tenQReport);
}
