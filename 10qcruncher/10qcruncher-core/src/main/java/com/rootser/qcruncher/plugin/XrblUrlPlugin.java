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

	@Value("${10.q.xrbl.td.doc.str}")
	private String xrblTdDocStr;

	@Value("${10.q.xrbl.td.file.str}")
	private String xrblTdFileStr;

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

			/*
			 * at some point the SEC started using, "XBRL DOCUMENT,"
			 * instead of, "XBRL FILE."
			 * 
			 */
			Element xrblTd = parentDoc.select(xrblTdDocStr).first();
			if (xrblTd == null) {
				xrblTd = parentDoc.select(xrblTdFileStr).first();
			}
			if (xrblTd != null){
				Element xrblLink = xrblTd.siblingElements().select(xrblSelector).first();
				return new AppMsg<String>(xrblLink.attributes().get("href"));
			} else {
				AppMsg<String> negResult = new AppMsg<String>();
				negResult.setHasErrors(true);
				negResult.setResult("10q parent document does not have links to any XBRL files.");
				return negResult;
			}
		}
	}

}
