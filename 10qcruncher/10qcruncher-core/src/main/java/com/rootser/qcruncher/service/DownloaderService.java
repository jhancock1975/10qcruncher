package com.rootser.qcruncher.service;

import java.util.List;

import com.rootser.qcruncher.common.AppMsg;

public interface DownloaderService {
	public List<AppMsg<String>> downloadUrls(List<AppMsg<String>> urlStrs, String downloadsDir);
}
