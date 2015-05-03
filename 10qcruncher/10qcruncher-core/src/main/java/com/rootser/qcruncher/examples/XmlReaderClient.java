package com.rootser.qcruncher.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
@Component("XmlReaderClient")
public class XmlReaderClient {
	@Autowired
	private  XmlReader xmlReader;
	public void go(){
		xmlReader.getEdgarUrl();
	}
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath:XmlReaderConfig.xml");
		 XmlReaderClient client = (XmlReaderClient) context.getBean("XmlReaderClient");
		 client.go();
		 ((ClassPathXmlApplicationContext) context).close();
	}
}
