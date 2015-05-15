package com.rootser.qcruncher.plugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;

public class TextUrlDownloadPlugin implements Plugin<String, String>{
	
	Logger logger = LoggerFactory.getLogger(TextUrlDownloadPlugin.class);

	@SuppressWarnings("unchecked")
	public AppMsg<String> process(AppMsg<String> urlStr){
		AppMsg<String> result = new AppMsg<String>();
		try {
			String urlTxt = IOUtils.toString(new URL(urlStr.getResult()));
			result.setResult(urlTxt);
			
		} catch (MalformedURLException e) {
			result =  (AppMsg<String>) CommonCatchLogic.commonCatchLogic(logger, 
					result, e, "10qcruncher sees " + urlStr + " as an incorrect URL");
		} catch (IOException e) {
			result =  (AppMsg<String>) CommonCatchLogic.commonCatchLogic(logger,
					result, e, "10qcruncher encounrtered an input/output exception when downloading text from " + urlStr);
		}
		return result;
	}
}
