package com.rootser.qcruncher.plugin;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author john
 *
 * @param <T>
 */
public interface DownloadPlugin<T> {
	public T download(String urlStr) throws MalformedURLException, IOException;
}
