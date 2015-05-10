package com.rootser.qcruncher.integration.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
@Service
public class HtmlToArffSvcImpl implements HtmlToArffSvc {
	
	private static Logger logger = LoggerFactory.getLogger(HtmlToArffSvcImpl.class);

	public List<AppMsg<String>> htmlsToArffs(List<AppMsg<Document>> tenQReports) {
		if (CollectionUtils.isEmpty(tenQReports)){
			logger.debug("empty list of 10 q reports, no conversion to arff possible");
		} else {
			for (AppMsg<Document> tenQ: tenQReports){
				
			}
		}
	}

	public AppMsg<String> htmlToArff(AppMsg<Document> tenQReport) {
		// TODO Auto-generated method stub
		return null;
	}

}
