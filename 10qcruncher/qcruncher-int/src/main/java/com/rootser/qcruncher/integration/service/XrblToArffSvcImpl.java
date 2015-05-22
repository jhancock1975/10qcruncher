package com.rootser.qcruncher.integration.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.integration.common.ArffDataSet;
import com.rootser.qcruncher.plugin.Plugin;
import com.rootser.qcruncher.service.ProcessDelegate;
@Service
public class XrblToArffSvcImpl implements XrblToArffSvc {


	public AppMsg<ArffDataSet> convertXrbls(List<AppMsg<String>> xmlDocUrls) {
		Plugin<String, ArffData>  xrblPlugin = new XrblToArffPlugin();
		ProcessDelegate<ArffData, String> delegate = new ProcessDelegate<ArffData, String>();
		List<AppMsg<ArffData>> arffDataMsgs = delegate.applyPluginProcessList(xmlDocUrls, xrblPlugin);
		
		AppMsg<ArffDataSet> dataSetMsg = new AppMsg<ArffDataSet>();
		for (AppMsg<ArffData> arffDataMsg: arffDataMsgs){
			dataSetMsg.copyMsgErrThrows(arffDataMsg);
		}
		
		return dataSetMsg;
	}

	public AppMsg<ArffData> convertXrbl(AppMsg<String> xmlDocUrl) {
		Plugin<String, ArffData>  xrblPlugin = new XrblToArffPlugin();
		ProcessDelegate<ArffData, String> delegate = new ProcessDelegate<ArffData, String>();
		return delegate.applyPluginProcess(xmlDocUrl, xrblPlugin);
	}

	

	public String toArffFormat(AppMsg<ArffDataSet> arffDataList) {

		Map<String, Integer> attributeCounts = arffDataList.getResult().getAttributeCounts();

		StringBuilder result = new StringBuilder("%ARFF data generated by 10-q cruncher\n@RELATION 10q\n");

		result.append("@ATTRIBUTE cik string\n");
		result.append("@ATTRIBUTE minDate date\n");
		result.append("@ATTRIBUTE maxDate date\n");

		for (String attrib: attributeCounts.keySet()){
			int max = attributeCounts.get(attrib);
			for (int i = 0; i < max; i++){
				result.append("@ATTRIBUTE " + attrib + i + " numeric\n");
			}
		}

		result.append("\n@DATA\n");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		for (ArffData arffData: arffDataList.getResult().getDataSet()){

			result.append(arffData.getCik()+",");
			result.append(format.format(arffData.getStartDate()) + ",");
			result.append(format.format(arffData.getEndDate()) + ",");

			for(String attrib: attributeCounts.keySet()){

				List<Pair<String, Double>> data = arffData.getListFor(attrib);
				int max = attributeCounts.get(attrib);
				for (int i = 0; i < max; i++){
					if (data != null && i < data.size()){
						result.append(data.get(i).getRight() + ",");
					} else {
						result.append("0,");
					}
				}
			}
			result.setLength(result.length()-1);
			result.append("\n");		
		}

		return result.toString();
	}

}
