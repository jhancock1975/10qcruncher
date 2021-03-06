package com.rootser.qcruncher.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:XmlReaderConfig.xml" })
@Configuration
@PropertySource("classpath:service-tests.properties")
public class DocRetrievalSvcTest {

	private static Logger logger = LoggerFactory.getLogger(DocRetrievalSvcTest.class);

	@Autowired
	private DocRetrievalSvc docSvc;

	@Value("${doc.retrieval.test.url}")
	private String testUrlStr;

	@Value("${doc.retrieval.test.txt.url}")
	private String testTxtUrlStr;

	@Autowired
	private Environment env;

	private AppMsg<String> getUrlMsg(){
		AppMsg<String> urlMsg = new AppMsg<String>();
		urlMsg.setResult(testUrlStr);
		return urlMsg;
	}

	@Test
	public void testGetHtmlDoc() {
		AppMsg<String> urlMsg = getUrlMsg();

		AppMsg<Document> docMsg = docSvc.getHtmlDoc(urlMsg);

		boolean passCond = ! docMsg.hasErrors() &&
				docMsg.getResult().title() != null;

		assertTrue(passCond);
	}


	@Test
	public void testSelector() {
		String niceFormat10Q = env.getProperty("doc.retrieval.nice.regular.10.q.url");
		String nasty10Q = env.getProperty("doc.retrieval.nasty.unformatted.10.q.url");
		
		List<String> urlList = Lists.newArrayList(niceFormat10Q, nasty10Q);
		
		AppMsg<String> urlMsg = new AppMsg<String>();
		
		for (String urlStr: urlList){
			
			urlMsg.setResult(urlStr);

			AppMsg<Document> docMsg = docSvc.getHtmlDoc(urlMsg);

			boolean passCond = ! docMsg.hasErrors() &&
					docMsg.getResult().title() != null;

			Element arNetLabel = docMsg.getResult().select("td:contains(Accounts receivable, net)").first();
			Elements arNetSibs = arNetLabel.siblingElements();
			for (Element sib: arNetSibs){
				logger.debug("'" + sib.text().trim() + "'");
			}
			assertTrue(passCond);
		}
		
	}

	@Test
	public void testGetHtmlDocs() {
		@SuppressWarnings("unchecked")
		List<AppMsg<String>> urlMsgs = Lists.newArrayList(getUrlMsg(), getUrlMsg(), getUrlMsg());

		List<AppMsg<Document>> docMsgs = docSvc.getHtmlDocs(urlMsgs);

		for (int i = 0; i < docMsgs.size(); i++){
			boolean passCond = ! docMsgs.get(i).hasErrors() &&
					docMsgs.get(i).getResult().title() != null;
			assertTrue("failed to download document #" + i, passCond);
		}
	}

	@Test
	public void testGetTxtDoc() {
		AppMsg<String> urlMsg = getUrlMsg();

		AppMsg<String> docMsg = docSvc.getTxtDoc(urlMsg);

		boolean passCond = ! docMsg.hasErrors() &&
				docMsg.getResult().contains("10qcruncher is software");

		assertTrue(passCond);
	}

	@Test
	public void testGetTxtDocs() {
		@SuppressWarnings("unchecked")
		List<AppMsg<String>> urlMsgs = Lists.newArrayList(getUrlMsg(), getUrlMsg(), getUrlMsg());

		List<AppMsg<String>> docMsgs = docSvc.getTxtDocs(urlMsgs);

		for (int i = 0; i < docMsgs.size(); i++){
			boolean passCond =  ! docMsgs.get(i).hasErrors() &&
					docMsgs.get(i).getResult().contains("10qcruncher is software");
			assertTrue("failed to download document #" + i, passCond);
		}
	}

	private AppMsg<String> getRubbishUrlMsg(){
		AppMsg<String> urlMsg = new AppMsg<String>();
		urlMsg.setResult("rubbish");
		return urlMsg;
	}
	@Test
	public void testBadTxtURl(){
		AppMsg<String> urlMsg = getRubbishUrlMsg();

		AppMsg<String> docMsg = docSvc.getTxtDoc(urlMsg);

		assertTrue(docMsg.hasErrors());
	}

	@Test
	public void testBadHtmlURl(){
		AppMsg<String> urlMsg = getRubbishUrlMsg();

		AppMsg<Document> docMsg = docSvc.getHtmlDoc(urlMsg);

		assertTrue(docMsg.hasErrors());
	}

	@Test
	public void testBadHtmlUrls(){
		@SuppressWarnings("unchecked")
		List<AppMsg<String>> urlMsgs = Lists.newArrayList(getRubbishUrlMsg(), getRubbishUrlMsg(), getRubbishUrlMsg());

		List<AppMsg<Document>> docMsgs = docSvc.getHtmlDocs(urlMsgs);

		for (int i = 0; i < docMsgs.size(); i++){
			boolean passCond =  docMsgs.get(i).hasErrors();
			assertTrue("Document #" + i + " should have errors.", passCond);
		}
	}
	@Test
	public void testBadTxtUrls(){
		@SuppressWarnings("unchecked")
		List<AppMsg<String>> urlMsgs = Lists.newArrayList(getRubbishUrlMsg(), getRubbishUrlMsg(), getRubbishUrlMsg());

		List<AppMsg<String>> docMsgs = docSvc.getTxtDocs(urlMsgs);

		for (int i = 0; i < docMsgs.size(); i++){
			boolean passCond =  docMsgs.get(i).hasErrors();
			assertTrue("Document #" + i + " should have errors.", passCond);
		}
	}
}
