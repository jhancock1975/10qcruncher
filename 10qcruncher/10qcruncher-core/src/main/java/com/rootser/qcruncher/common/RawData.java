/**
 * This class encapsulates data that serves as input to
 * translator services.
 * 
 * Translator services convert data as input for
 * machine learning frameworks.
 */
package com.rootser.qcruncher.common;

import java.util.List;

import org.jsoup.nodes.Document;

public class RawData {
	List<AppMsg<String>> txtData;
	List<AppMsg<Document>> htmlData;
	
	public RawData(List<AppMsg<String>> txtData, List<AppMsg<Document>> htmlData) {
		super();
		this.txtData = txtData;
		this.htmlData = htmlData;
	}
	public List<AppMsg<String>> getTxtData() {
		return txtData;
	}
	public void setTxtData(List<AppMsg<String>> txtData) {
		this.txtData = txtData;
	}
	public List<AppMsg<Document>> getHtmlData() {
		return htmlData;
	}
	public void setHtmlData(List<AppMsg<Document>> htmlData) {
		this.htmlData = htmlData;
	}
	
	
}
