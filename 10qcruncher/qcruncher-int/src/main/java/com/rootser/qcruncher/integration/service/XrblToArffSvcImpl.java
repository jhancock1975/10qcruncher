package com.rootser.qcruncher.integration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.plugin.Plugin;
import com.rootser.qcruncher.service.ProcessDelegate;
@Service
public class XrblToArffSvcImpl implements XrblToArffSvc {

	public List<AppMsg<ArffData>> convertXrbls(List<AppMsg<String>> xmlDocUrls) {
		Plugin<String, ArffData>  xrblPlugin = new XrblToArffPlugin();
		ProcessDelegate<ArffData, String> delegate = new ProcessDelegate<ArffData, String>();
		return delegate.applyPluginProcessList(xmlDocUrls, xrblPlugin);
	}

	public AppMsg<ArffData> convertXrbl(AppMsg<String> xmlDocUrl) {
		Plugin<String, ArffData>  xrblPlugin = new XrblToArffPlugin();
		ProcessDelegate<ArffData, String> delegate = new ProcessDelegate<ArffData, String>();
		return delegate.applyPluginProcess(xmlDocUrl, xrblPlugin);
	}

}
