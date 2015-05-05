package com.rootser.qcruncher.service;

import java.util.List;

import com.rootser.qcruncher.common.AppMsg;

public interface TxtDocSvc {
	public AppMsg<String> getTxtDoc(AppMsg<String> url);
	
	public List<AppMsg<String>> getTxtDocs(List<AppMsg<String>> urls);
}
