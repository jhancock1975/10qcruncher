package com.rootser.qcruncher.plugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class TextUrlDownloadPlugin implements DownloadPlugin<String>{

	public String download(String urlStr) throws MalformedURLException,
			IOException {
		return IOUtils.toString(new URL(urlStr));
	}
	
}
