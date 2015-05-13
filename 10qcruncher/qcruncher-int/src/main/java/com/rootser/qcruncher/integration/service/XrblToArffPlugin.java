package com.rootser.qcruncher.integration.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

public class XrblToArffPlugin implements Plugin<AppMsg<ArffData>, String> {

	Logger logger = LoggerFactory.getLogger(XrblToArffPlugin.class);

	@SuppressWarnings("unchecked")
	public AppMsg<ArffData> process(String xrblUrl) throws MalformedURLException, IOException {

		DocumentBuilder dBuilder;
		AppMsg<ArffData> result = new AppMsg<ArffData>();

		try {
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(xrblUrl);
			visitRecursively(doc);


		} catch (ParserConfigurationException e) {
			return  (AppMsg<ArffData>) CommonCatchLogic.commonCatchLogic(logger, result, e, 
					"10qcruncher encountered a parser configuration error when processing " + xrblUrl);

		} catch (SAXException e) {
			return  (AppMsg<ArffData>) CommonCatchLogic.commonCatchLogic(logger, result, e, 
					"10qcruncher encountered a SAX exception when processing " + xrblUrl);
		}
		return result;

	}

	public static void visitRecursively(Node node) {

		// get all child nodes

		NodeList list = node.getChildNodes();

		for (int i=0; i<list.getLength(); i++) {

			// get child node

			Node childNode = list.item(i);

			System.out.println("Found Node: " + childNode.getNodeName()

					+ " - with value: " + childNode.getNodeValue());

			// visit child node

			visitRecursively(childNode);

		}

	}

}