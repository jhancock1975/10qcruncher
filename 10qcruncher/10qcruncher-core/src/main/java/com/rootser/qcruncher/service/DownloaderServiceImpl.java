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
@Service
public class DownloaderServiceImpl implements DownloaderService {
	private static Logger logger = LoggerFactory.getLogger(DownloaderServiceImpl.class);
	public void downloadUrls(List<String> urlStrs, String downloadsDir) {
		File downloadDir = new File(downloadsDir);
		for (String urlStr: urlStrs){

			try {
				logger.debug("Downloading to: " + downloadDir.getAbsolutePath());
				String latestSecFileName = downloadDir +
						urlStr.substring(
								urlStr.lastIndexOf('/'), urlStr.length()); 

				FileUtils.copyURLToFile(new URL(urlStr),
						new File(latestSecFileName));

			} catch (MalformedURLException e) {
				logger.debug(e.getMessage());
			} catch (IOException e) {
				logger.debug(e.getMessage());
			}

			logger.debug("Download complete.");
		}
	}

}
