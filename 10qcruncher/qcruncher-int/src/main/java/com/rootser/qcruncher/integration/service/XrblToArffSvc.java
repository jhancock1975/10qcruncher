package com.rootser.qcruncher.integration.service;

import java.util.List;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.integration.common.ArffDataSet;

public interface XrblToArffSvc {
	
	public AppMsg<ArffDataSet> convertXrbls(List<AppMsg<String>> xmlDocs);
	
	public AppMsg<ArffData> convertXrbl(AppMsg<String> xmlDocUrl);
	
	public AppMsg<String> toArffFormat(AppMsg<ArffDataSet> arffDataList);
	
}
