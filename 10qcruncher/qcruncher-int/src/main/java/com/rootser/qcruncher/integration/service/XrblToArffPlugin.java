package com.rootser.qcruncher.integration.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;
import com.rootser.qcruncher.integration.common.ArffData;
import com.rootser.qcruncher.plugin.Plugin;

public class XrblToArffPlugin implements Plugin<String, ArffData> {

	private static Logger logger = LoggerFactory.getLogger(XrblToArffPlugin.class);

	@SuppressWarnings("unchecked")
	public AppMsg<ArffData> process(AppMsg<String> xrblUrl)  {

		DocumentBuilder dBuilder;
		AppMsg<ArffData> result = new AppMsg<ArffData>(new ArffData());

		try {
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(xrblUrl.getResult());
			visitRecursively(doc, result);


		} catch (ParserConfigurationException e) {
			return  (AppMsg<ArffData>) CommonCatchLogic.commonCatchLogic(logger, result, e, 
					"10qcruncher encountered a parser configuration error when processing " + xrblUrl);

		} catch (SAXException e) {
			return  (AppMsg<ArffData>) CommonCatchLogic.commonCatchLogic(logger, result, e, 
					"10qcruncher encountered a SAX exception when processing " + xrblUrl);
		} catch (IOException e) {
			return  (AppMsg<ArffData>) CommonCatchLogic.commonCatchLogic(logger, result, e, 
					"10qcruncher encountered an input/output exception when processing " + xrblUrl);
		}
		return result;

	}
	
	private Date parseDate(String str){
		
		Matcher dateMatcher;
		Pattern datePattern = Pattern.compile("\\d+_\\d+_\\d+");
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yy");
		
		dateMatcher = datePattern.matcher(str);
		if (dateMatcher.find()){
			try {
				result = sdf.parse(dateMatcher.group());
			} catch(ParseException e){
				logger.debug("caught parse exception when attempting to parse date");
			}
		}
		return result;
	}
	public void visitRecursively(Node node, AppMsg<ArffData> result) {

		// get all child nodes
		NodeList list = node.getChildNodes();

		for (int i=0; i<list.getLength(); i++) {

			// get child node
			Node childNode = list.item(i);

			String nodeName = childNode.getNodeName();

			if (nodeName != null){

				if (nodeName.contains("us-gaap") && childNode.getChildNodes() != null){
					NodeList childNodeList = childNode.getChildNodes();
					for (int j=0; j < childNodeList.getLength(); j++){
						Node curNode = childNodeList.item(j);
						if (curNode != null && curNode.getNodeName() != null && curNode.getNodeName().equals("#text") &&
								curNode.getNodeValue() != null){
							String nodeValStr = curNode.getNodeValue().replace("$", "").replace(",", "").trim();
							Date date = null;
							try {
								double nodeVal = Double.parseDouble(nodeValStr);
								
								String contextRef = "";
								
								if (childNode.getAttributes() != null && childNode.getAttributes().getNamedItem("contextRef") != null){
									contextRef = childNode.getAttributes().getNamedItem("contextRef").getNodeValue();
									date = parseDate(contextRef);
								}
								
								result.getResult().put(new ImmutablePair<String,Date>(nodeName, date), nodeVal);
							} catch(NumberFormatException e){
								logger.debug("found non-numeric gaap node " + nodeName);
							}
						}
					}
				}

				// visit child node
				visitRecursively(childNode, result);

			}
		}

	}
}