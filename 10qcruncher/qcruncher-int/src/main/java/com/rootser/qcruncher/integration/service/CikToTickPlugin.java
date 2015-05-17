package com.rootser.qcruncher.integration.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;
import com.rootser.qcruncher.plugin.Plugin;
import com.rootser.qcruncher.service.DocRetrievalSvc;

public class CikToTickPlugin implements Plugin<String, String> {
	
	Logger logger = LoggerFactory.getLogger(CikToTickPlugin.class);
	
	private String getEdgarCikSearchUrl(String cik) {
		StringBuilder result = new StringBuilder("http://edgar.sec.gov/cgi-bin/browse-edgar?CIK=");
		result.append(cik);
		result.append("&Find=Search&owner=exclude&action=getcompany");
		return result.toString();
	}

	private String getYahoLookupUrl(String companyName){
		StringBuilder result = new StringBuilder("http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=");
		result.append(companyName);
		result.append("&callback=YAHOO.Finance.SymbolSuggest.ssCallback");
		return result.toString();
	}
	@Autowired
	private DocRetrievalSvc docSvc;
	@Override
	public AppMsg<String> process(AppMsg<String> cikAppMsg) {
		String cik = cikAppMsg.getResult();
		AppMsg<String> tickerMsg = new AppMsg<String>();
		String url = getEdgarCikSearchUrl(cik);
		try {
			
			Document doc = Jsoup.connect(url).get();
			Elements companyNameSpans = doc.select("span.companyName");
			String companyName = companyNameSpans.first().text();
			String tickerLookupUrl = getYahoLookupUrl(companyName);
			AppMsg<String> lookupResult = docSvc.getTxtDoc(new AppMsg<String>(tickerLookupUrl));
			tickerMsg.copyMsgErrThrows(lookupResult);
			if (! tickerMsg.hasErrors()){
				Gson gson = new Gson();
				@SuppressWarnings("unchecked")
				Map<String, Object> map = gson.fromJson(lookupResult.getResult(), Map.class);
				String ticker = (String) map.get("symbol");
				tickerMsg.setResult(ticker);
			}
			
			
			
		} catch (MalformedURLException e) {
			CommonCatchLogic.commonCatchLogic(logger, tickerMsg, e, 
					"when attempting to find ticker for cik " + cik + 
					" 10-q cruncher is not able to use " + url +  " as a URL." );
		} catch (IOException e) {
			CommonCatchLogic.commonCatchLogic(logger, tickerMsg, e, 
					"when attempting to find ticker for cik " + cik + 
					" 10-q cruncher caught an i/o exception when using " 
							+ url +  " as a URL." );
		}
		return tickerMsg;
	}
}
