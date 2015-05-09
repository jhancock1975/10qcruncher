package com.rootser.qcruncher.integration.service;

import java.util.List;

import org.jsoup.nodes.Document;

import com.rootser.qcruncher.common.AppMsg;

public interface HtmlToArffSvc {
	List<AppMsg<String>> htmlToArff(List<AppMsg<Document>> tenQReports);
}
