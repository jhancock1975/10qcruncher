package com.rootser.qcruncher.plugin;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlDownloadPlugin implements DownloadPlugin<Document>{

	public Document download(String urlStr) throws MalformedURLException, IOException {
		return Jsoup.connect(urlStr).get();
	}
	

}
