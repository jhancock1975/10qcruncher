package com.rootser.qcruncher.service;

import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.plugin.HtmlDownloadPlugin;
import com.rootser.qcruncher.plugin.TextUrlDownloadPlugin;

@Service
public class DocRetrievalSvcImpl implements DocRetrievalSvc {

	public AppMsg<String> getTxtDoc(AppMsg<String> urlMsg) {
		return new DownloadDelegate<String>().getDoc(urlMsg, new TextUrlDownloadPlugin());
	}

	public List<AppMsg<String>> getTxtDocs(List<AppMsg<String>> urlMsgs) {
		return new DownloadDelegate<String>().getDocs(urlMsgs, new TextUrlDownloadPlugin());
	}

	public AppMsg<Document> getHtmlDoc(AppMsg<String> urlMsg) {
		return new DownloadDelegate<Document>().getDoc(urlMsg, new HtmlDownloadPlugin());
	}

	public List<AppMsg<Document>> getHtmlDocs(List<AppMsg<String>> urlMsgs) {
		return new DownloadDelegate<Document>().getDocs(urlMsgs, new HtmlDownloadPlugin());
	}

	 

}
