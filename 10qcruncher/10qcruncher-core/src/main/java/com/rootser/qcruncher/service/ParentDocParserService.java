package com.rootser.qcruncher.service;

import java.util.List;

import com.rootser.qcruncher.common.AppMsg;

/**
 * The links in the EDGAR RSS feeds
 * are links to what I call, "parent," documents
 * that in turn have links to the actual 10-Q, and
 * additional documents.  These are in HTML format.
 * In addition, there is a link to the full text.
 * 
 * @author john
 *
 */
public interface ParentDocParserService {
	public List<AppMsg<String>> get10QHtmUrl(List<AppMsg<String>> parentDocList, String downloadDirName);
	public AppMsg<String> get10QHtmUrl(AppMsg<String> parentDoc, String downloadDirName);
	public List<AppMsg<String>> get10QTxtUrl(List<AppMsg<String>> parentDocList, String downloadDirName);
	public AppMsg<String> get10QTxtUrl(AppMsg<String> parentDoc, String downloadDirName);
}
