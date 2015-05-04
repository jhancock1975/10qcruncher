package com.rootser.qcruncher.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rootser.qcruncher.common.AppMsg;
@Service
public class DownloaderServiceImpl implements DownloaderService {
	
	private static Logger logger = LoggerFactory.getLogger(DownloaderServiceImpl.class);
	
	/**
	 * common case for this method is that EDGAR
	 * urls will refer to files where the file
	 * name is the text after the last '/' in
	 * the url
	 * 
	 * if we don't get a string with '/' in it
	 * is probably a malformed URL. So we just
	 * return the input unchanged.
	 * 
	 * @param urlStr
	 * @return
	 */
	private String getFileNameFromUrl(String urlStr){
		if (urlStr.contains("/")){
			return urlStr.substring(urlStr.lastIndexOf('/'), urlStr.length()); 
		} else {
			return urlStr;
		}
	}

	public List<AppMsg<String>> downloadUrls(List<AppMsg<String>> urlAppMsgs, String downloadsDir) {
		File downloadDir = new File(downloadsDir);
		if (CollectionUtils.isEmpty(urlAppMsgs)){
			logger.debug("Received empty list of AppMsg for downloads; nothing to do.");
		} else {
			for (AppMsg<String> msg: urlAppMsgs){
				if (msg == null){
					logger.debug("null app message; nothing to do");
				} else {

					try {
						logger.debug("Downloading to: " + downloadDir.getAbsolutePath());

						String urlStr = msg.getResult();
						
						if (urlStr == null){	
							logger.debug("attempt to use null as URL");
						
						} else {
							
							String latestSecFileName = downloadDir +
									getFileNameFromUrl(urlStr); 
							FileUtils.copyURLToFile(new URL(urlStr),new File(latestSecFileName));
						}

					} catch (MalformedURLException e) {
						logger.debug(e.getMessage());
						msg.addThrowables(e);
						msg.addMsg("The URL " + msg.getResult() + " is not a valid Internet address." +
								"Unable to download a file associated with it.");
						
					} catch (IOException e) {
						logger.debug(e.getMessage());
						msg.addThrowables(e);
						msg.addMsg("Input or output not possible when attempting to download a file at URL "+
								msg.getResult());
					}
				}
			}

			logger.debug("Download complete.");
		}
		return urlAppMsgs;
	}

}
