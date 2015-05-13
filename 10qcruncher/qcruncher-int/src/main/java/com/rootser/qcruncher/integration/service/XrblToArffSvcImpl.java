package com.rootser.qcruncher.integration.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.plugin.Plugin;
import com.rootser.qcruncher.service.ProcessDelegate;
@Service
public class XrblToArffSvcImpl implements XrblToArffSvc {

	public List<AppMsg<ArffData>> convertXrbls(List<AppMsg<String>> xmlDocUrls) {
		Plugin<AppMsg<ArffData>, String>  xrblPlugin = new XrblToArffPlugin();
		ProcessDelegate<AppMsg<ArffData>, String> delegate = new ProcessDelegate<AppMsg<ArffData>, String>();
		List<AppMsg<AppMsg<ArffData>>> result = delegate.applyPluginProcessList(xmlDocUrls, xrblPlugin);
		List<AppMsg<ArffData>> unpackedResult = new ArrayList<AppMsg<ArffData>>();
		for (AppMsg<AppMsg<ArffData>> packedAppMsg: result){
			AppMsg<ArffData> curMsg = new AppMsg<ArffData>();
			curMsg.addMsg(packedAppMsg.getMsgs());
			curMsg.addMsg(packedAppMsg.getResult().getMsgs());
			curMsg.addThrowables(packedAppMsg.getThrowables());
			curMsg.addThrowables(packedAppMsg.getResult().getThrowables());
			curMsg.setResult(packedAppMsg.getResult().getResult());
			unpackedResult.add(curMsg);
		}
		return unpackedResult;
	}

	public AppMsg<ArffData> convertXrbl(AppMsg<String> xmlDocUrl) {
		Plugin<AppMsg<ArffData>, String>  xrblPlugin = new XrblToArffPlugin();
		ProcessDelegate<AppMsg<ArffData>, String> delegate = new ProcessDelegate<AppMsg<ArffData>, String>();
		AppMsg<AppMsg<ArffData>> packedAppMsg = delegate.applyPluginProcess(xmlDocUrl, xrblPlugin);
		AppMsg<ArffData> curMsg = new AppMsg<ArffData>();
		curMsg.addMsg(packedAppMsg.getMsgs());
		curMsg.addMsg(packedAppMsg.getResult().getMsgs());
		curMsg.addThrowables(packedAppMsg.getThrowables());
		curMsg.addThrowables(packedAppMsg.getResult().getThrowables());
		curMsg.setResult(packedAppMsg.getResult().getResult());
		return curMsg;
	}

}
