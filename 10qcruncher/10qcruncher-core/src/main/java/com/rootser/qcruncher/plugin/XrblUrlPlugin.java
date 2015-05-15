package com.rootser.qcruncher.plugin;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.service.DocRetrievalSvc;

@Component
public class XrblUrlPlugin implements Plugin<String, String> {

	@Value("${10.q.xrbl.selector}")
	private String xrblSelector;
	
	@Value("${10.q.xrbl.td.str}")
	private String xrblTdStr;
	
	@Autowired
	private DocRetrievalSvc docSvc;
	
	private AppMsg<String> convertAppMsg(AppMsg<Document> docMsg){
		AppMsg<String> result = new AppMsg<String>();
		result.setHasErrors(docMsg.hasErrors());
		result.addMsg(docMsg.getMsgs());
		result.addThrowables(docMsg.getThrowables());
		return result;
	}
	
	public AppMsg<String> process(AppMsg<String> inputParam) {
		AppMsg<Document> docMsg =  docSvc.getHtmlDoc(inputParam);
		if ( docMsg.hasErrors()){
			return convertAppMsg(docMsg);
		} else {
			Document parentDoc = docMsg.getResult();
			Element xrblTd = parentDoc.select(xrblTdStr).first();
			Element xrblLink = xrblTd.siblingElements().select(xrblSelector).first();
			return new AppMsg<String>(xrblLink.attributes().get("href"));
		}
	}

}
