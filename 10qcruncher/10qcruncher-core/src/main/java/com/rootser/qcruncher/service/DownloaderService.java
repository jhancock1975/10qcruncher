package com.rootser.qcruncher.service;

import java.util.List;

public interface DownloaderService {
	public void downloadUrls(List<String> urlStrs, String downloadsDir);
}
