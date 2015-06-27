package com.rootser.qcruncher.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;
import com.rootser.qcruncher.common.DateRange;
import com.rootser.qcruncher.plugin.Plugin;

@Component
@Qualifier("EdgarSearchPlugin")
public class EdgarSearchPlugin implements Plugin<DateRange, List<String>> {



	Logger logger = LoggerFactory.getLogger(EdgarSearchPlugin.class);

	@Value("${edgar.search.url.prefix}")
	private String edgarSearchPrefix;

	@Value("${sec.url.prefix}")
	private String secUrlPrefix;

	@Value("${search.url.end}")
	private String searchUrlEnd;

	public static final long MILIS_IN_DAY = 1000 * 60 * 60 * 24;

	private StringBuilder getDateRangeString(Date startDate, Date endDate, StringBuilder result) throws UnsupportedEncodingException{

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

		Date curDate = startDate;
		while (curDate.compareTo(endDate) < 0){
			result.append("filing-date");
			result.append(URLEncoder.encode("=" + dateFormatter.format(curDate), "UTF-8"));
			result.append("+OR+");
			curDate.setTime(curDate.getTime() + MILIS_IN_DAY);
		}

		//strip off last "+OR+"
		result.setLength(result.length() - 4);

		return result;
	}

	private String getSearchUrl(Date startDate, Date endDate) throws UnsupportedEncodingException{

		StringBuilder result = new StringBuilder(edgarSearchPrefix);

		result = getDateRangeString(startDate, endDate, result);

		result.append("+AND+type");
		result.append(URLEncoder.encode("=", "UTF-8"));
		result.append("10-q");

		Calendar cal = Calendar.getInstance();

		result.append("&first=");
		cal.setTime(startDate);
		result.append(cal.get(Calendar.YEAR));

		result.append("&last=");
		cal.setTime(endDate);
		result.append(cal.get(Calendar.YEAR));

		return result.toString();

	}


	public AppMsg<List<String>> process(AppMsg<DateRange> dateMsg) {

		Date startDate = dateMsg.getResult().getStartDate();

		Date endDate = dateMsg.getResult().getEndDate();

		AppMsg<List<String>> urlListMsg = new AppMsg<List<String>>();

		String searchUrl = null;

		try{
			searchUrl = getSearchUrl(startDate, endDate);

			Document searchResult = Jsoup.connect(searchUrl).get();

			List<String> urlList = getTenQUrlsOnPage(searchUrl, searchResult);
			
			List<String> linkedResults = getLinkedResults( searchUrl, searchResult);
			
			if (CollectionUtils.isNotEmpty(linkedResults)){
				urlList.addAll(linkedResults);
			}

			urlListMsg.setResult(urlList);

		} catch(UnsupportedEncodingException e){
			CommonCatchLogic.commonCatchLogic(logger, urlListMsg, e, 
					"10q cruncher tried to encode a url with an unsupported url encoding scheme");
		} catch (IOException e) {
			CommonCatchLogic.commonCatchLogic(logger, urlListMsg, e, 
					"10q cruncher was unable to download search results for search URL " + searchUrl);
		}
		return urlListMsg;
	}

	private List<String> getTenQUrlsOnPage(String searchUrl, Document searchResult) throws IOException{

		Elements tenQUrls = searchResult.select("a[href^=/Archives/edgar/data/]:contains([html])");

		List<String> urlList = new ArrayList<String>();

		urlList.addAll(getUrlStrings(tenQUrls));

		return urlList;

	}

	private List<String> getUrlStrings(Elements elements){
		List<String> result = new ArrayList<String>();

		for (Element element: elements){
			String url = element.attr("href");
			if (!StringUtils.isBlank(url) && ! url.equals("null")){
				result.add(secUrlPrefix + element.attr("href"));
			}
		}

		return result;
	}

	private List<String> getLinkedResults(String searchUrl, Document searchResult) throws IOException {

		String relativeSearchUrl = searchUrl.substring(secUrlPrefix.length()+1, searchUrl.length());

		relativeSearchUrl = relativeSearchUrl.substring(0, relativeSearchUrl.indexOf(searchUrlEnd));

		List<String> tenQPages = new ArrayList<String>();

		Elements additionalSearchUrls = searchResult.select("a[href^="+relativeSearchUrl);
		if (additionalSearchUrls.size() > 0){
			//remove the first match that is
			//a link to an RSS feed of the first
			//search result page
			additionalSearchUrls.remove(0);
		}
		if (additionalSearchUrls.size() > 0){
			List<String>  linkedResultPages = getUrlStrings(additionalSearchUrls);


			for (String linkedPage: linkedResultPages){

				Document linkedPageDoc = Jsoup.connect(linkedPage).get();
				tenQPages.addAll(getTenQUrlsOnPage(linkedPage, linkedPageDoc));

			}
		}
		return tenQPages;
	}

}
