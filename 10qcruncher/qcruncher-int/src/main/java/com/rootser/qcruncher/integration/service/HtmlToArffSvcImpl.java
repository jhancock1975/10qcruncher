package com.rootser.qcruncher.integration.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.service.ProcessDelegate;
@Service
public class HtmlToArffSvcImpl implements HtmlToArffSvc {

	private static Logger logger = LoggerFactory.getLogger(HtmlToArffSvcImpl.class);

	public List<AppMsg<ArffData>> htmlsToArffs(List<AppMsg<Document>> tenQReports) {
		return new ProcessDelegate< ArffData, Document>().applyPluginProcessList(tenQReports, new HtmlToArffSvcPlugin());
	}
	
	public AppMsg<ArffData> htmlToArff(AppMsg<Document> tenQReport) {
		return new ProcessDelegate<ArffData, Document>().applyPluginProcess(tenQReport, new HtmlToArffSvcPlugin());
	}


	
	
	@SuppressWarnings("unused")
	/**
	 * This method is not used
	 * but we want to leave it
	 * as inspiration to find
	 * a general method for
	 * identifying tables of
	 * financial data in 10-q
	 * reports.
	 * 
	 * @param tenQReport
	 * @return
	 */
	private AppMsg<String> parseReportFuzzy(AppMsg<Document> tenQReport) {

		Pattern literalDollarSign = Pattern.compile("\\$");
		Matcher dollarMatcher;

		Pattern dataPointLabel = Pattern.compile("[\\w\\s]+");
		Matcher labelMatcher;

		Pattern numberPattern = Pattern.compile("[\\d,]+");
		Matcher numberMatcher;

		Elements tables = tenQReport.getResult().select("table");
		for (Element table: tables){
			dollarMatcher = literalDollarSign.matcher(table.text());
			if (dollarMatcher.find()){
				Elements tdElements = table.select("td");
				boolean lookingForNumbers = false;
				int numberFoundCount = 0;
				for (Element tdElement: tdElements){
					String tdText = tdElement.text();
					tdText = tdText.replaceAll("&nbsp;", " ");
					labelMatcher = dataPointLabel.matcher(tdText);
					if (labelMatcher.find() && ! lookingForNumbers){
						lookingForNumbers = true;
						numberFoundCount = 0;
						logger.debug("found label: " + tdText);
					}
					if (lookingForNumbers && numberFoundCount < 2){
						numberMatcher = numberPattern.matcher(tdElement.text());
						if (numberMatcher.find()){
							logger.debug("found number: " + tdElement.text());
							numberFoundCount++;
						}
					}
				}
			}

		}
		return null;
	}
}