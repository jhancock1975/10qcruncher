package com.rootser.qcruncher.integration.service;

import java.util.List;

import org.w3c.dom.Document;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;

public interface XrblToArffSvc {
	
	public List<AppMsg<ArffData>> convertXrbls(List<AppMsg<String>> xmlDocs);
	
	public AppMsg<ArffData> convertXrbl(AppMsg<String> xmlDocUrl);
	
}
