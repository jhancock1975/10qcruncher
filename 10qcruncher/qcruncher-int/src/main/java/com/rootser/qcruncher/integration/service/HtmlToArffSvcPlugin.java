package com.rootser.qcruncher.integration.service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.plugin.Plugin;

public class HtmlToArffSvcPlugin implements Plugin<Document, ArffData>{
	
	private Logger logger = LoggerFactory.getLogger(HtmlToArffSvcPlugin.class);
	
	public AppMsg<ArffData> process(AppMsg<Document> tenQReport) {

		Pattern numberPattern = Pattern.compile("[\\d,]+");
		Matcher numberMatcher;
		
		AppMsg<ArffData> arffDataMsg = new AppMsg<ArffData>( new ArffData());
		
		
		for (String keyword: TenQKeywords.keywords){
			
			ArffData curArffLine = new ArffData();

			Element dataPointLabel = tenQReport.getResult().select(keyword).first();

			if (dataPointLabel != null){

				Elements arNetSibs = dataPointLabel.siblingElements();

				int numberMatchCount = 0;
				
				Double firstNumber = null;
				Double secondNumber = null;
				
				for (Element sib: arNetSibs){
					numberMatcher = numberPattern.matcher(sib.text());
					
					if (numberMatcher.find()){
						String numberStr="";
						try {
							numberStr = sib.text().trim().replaceAll("\\(", "-").replaceAll(",", "").replaceAll("\\)", "");
							switch(numberMatchCount){
							case 0:
								firstNumber = Double.parseDouble(numberStr);
								numberMatchCount++;
								break;
							case 1:
								secondNumber = Double.parseDouble(numberStr);
								numberMatchCount++;
								break;
							}
							if (numberMatchCount > 1){
								curArffLine.put(new ImmutablePair<String, Date>(dataPointLabel.text(),null), firstNumber - secondNumber);
								break;
							}
						} catch(NumberFormatException e){
							logger.debug("attempt to treat " + numberStr + " as number");
						}
					}
				}
			}
			arffDataMsg.setResult(curArffLine);
		}
		logger.debug(arffDataMsg.getResult().toString());
		return arffDataMsg;
	}

}
