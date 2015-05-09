/**
 * This class encapsulates data that serves as input to
 * translator services.
 * 
 * Translator services convert data as input for
 * machine learning frameworks.
 */
package com.rootser.qcruncher.common;

import org.jsoup.nodes.Document;

public class RawData {
	String txtData;
	Document htmlData;
	public String getTxtData() {
		return txtData;
	}
	public void setTxtData(String txtData) {
		this.txtData = txtData;
	}
	public Document getHtmlData() {
		return htmlData;
	}
	public void setHtmlData(Document htmlData) {
		this.htmlData = htmlData;
	}
	
}
