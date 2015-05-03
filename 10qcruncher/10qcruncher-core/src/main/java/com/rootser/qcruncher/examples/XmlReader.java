package com.rootser.qcruncher.examples;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
@Configuration
@ComponentScan(basePackages = { "com.rootser.qcruncher.*" })
@PropertySource("classpath:XmlReader.properties")
@Component
public class XmlReader {

	private static Logger logger = LoggerFactory.getLogger(XmlReader.class);
	@Value("${edgar.url}")
	private String edgarUrl;
		
	public String getEdgarUrl(){
		
		logger.info(this.edgarUrl);
		return this.edgarUrl;
	}

	

	private Resource[] getResourceArr(String resourceSearchStr) throws IOException {
		ResourcePatternResolver resolver = 
				new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
		try {
			return resolver.getResources(resourceSearchStr);
		} catch (IOException e) {
			doGenericExceptionAct(e);
			throw e;
		}

	}
	public void analyze10qs(String fileName) {

		try {
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			visitRecursively(doc);

		} catch (IOException e) {
			doGenericExceptionAct(e);
		} catch (ParserConfigurationException e) {
			doGenericExceptionAct(e);
		} catch (SAXException e) {
			doGenericExceptionAct(e);
		}
	}

	public void visitRecursively(Node node) {
		// get all child nodes
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			// get child node
			Node childNode = list.item(i);
			logger.debug("Found Node: " + childNode.getNodeName()
					+ " - with value: " + childNode.getNodeValue());
			// visit child node
			visitRecursively(childNode);
		}
	}

	private String noResourceDirErrMsg(){
		StringBuilder sb = new StringBuilder("Unable to find downloads directory on classpath.\n");
		sb.append("Please create a directory named \"downloads\",\n");
		sb.append("and add it to the classpath.\n");
		return sb.toString();
	}

	private String getLatestSecFilingXml(String downloadsDir) throws IOException{

		File downloadDir = new File(downloadsDir);
		logger.debug("Downloading to: " + downloadDir.getAbsolutePath());
		String latestSecFileName = downloadDir+"/xbrlrss.all.xml"; 

		FileUtils.copyURLToFile(new URL(edgarUrl), 
				new File(latestSecFileName));

		logger.debug("Download complete.");
		return latestSecFileName;
	}



	private boolean hasValidDownloadDir(Resource[] resources) {
		try {
			if (resources.length > 0){
				File rsrcFile = resources[0].getFile();
				return rsrcFile.isDirectory();
			} else {
				return false;
			}
		}catch(IOException e){
			doGenericExceptionAct(e);
			return false;
		}
	}
	private void doGenericExceptionAct(Exception e) {
		logger.debug(e.getMessage());
	}

	public static void main(String[] args) throws IOException {

		XmlReader reader = new XmlReader();
		logger.debug(reader.edgarUrl);
		/*Resource[] resources = reader.getResourceArr("classpath*:/downloads");
		if (reader.hasValidDownloadDir(resources)){
			String latestSecFileName = reader.getLatestSecFilingXml(resources[0].getFilename());
			reader.analyze10qs(latestSecFileName);
		}*/
	}
}
