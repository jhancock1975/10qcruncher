package com.rootser.qcruncher.service;

import java.util.List;

import com.rootser.qcruncher.common.AppMsg;

public interface FeedParserService {
	public List<AppMsg<String>> getNew10QUrls(String urlStr);
}
