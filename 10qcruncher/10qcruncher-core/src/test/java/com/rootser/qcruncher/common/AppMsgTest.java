package com.rootser.qcruncher.common;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.rootser.qcruncher.testutil.NoSpringReflection;

public class AppMsgTest {
	private static Logger logger = LoggerFactory.getLogger(AppMsgTest.class);
	private AppMsg<Boolean> msg;
	
	@Before
	public void setUp() throws Exception {
		msg = new AppMsg<Boolean>(); 
	}

	
	private boolean isEmptyButNotNull(Collection<?> coll){
		return CollectionUtils.isEmpty(coll) && (coll != null);
	}
	
	@Test
	public void testAppMsg() {
		
		List<Throwable> throwables = (List<Throwable>) NoSpringReflection.getPrivateMember(msg, "throwables");
		List<String> messages = (List<String>) NoSpringReflection.getPrivateMember(msg, "messages");
		Boolean result = (Boolean) NoSpringReflection.getPrivateMember(msg, "result");
		
		boolean passCond = isEmptyButNotNull(throwables) &&
					isEmptyButNotNull(messages) &&
					(result == null);
		
		assertTrue(passCond);
	}

	@Test
	public void testGetResult() {
		NoSpringReflection.setPrivateMember(msg, "result", Boolean.FALSE);
		assertTrue(msg.getResult().equals(Boolean.FALSE));
	}

	@Test
	public void testSetResult() {
		msg.setResult(Boolean.TRUE);
		Boolean val = (Boolean) NoSpringReflection.getPrivateMember(msg, "result");
		assertTrue(val != null && val.equals(Boolean.TRUE));
	}

	@Test
	public void testHasThrowables() {
		Exception e = new Exception("test");
		NoSpringReflection.setPrivateMember(msg, "throwables", Arrays.asList(new Throwable[] {e}));
		assertTrue(msg.hasThrowables());
	}

	@Test
	public void testHasMessages() {
		String s = "test";
		NoSpringReflection.setPrivateMember(msg, "messages", Arrays.asList(new String[] {s}));
		assertTrue(msg.hasMessages());
	}

	@Test
	public void testAddMsg() {
		String s = "test";
		msg.addMsg(s);
		List<String> val = (List<String>) NoSpringReflection.getPrivateMember(msg, "messages");
		assertTrue(val.get(0) == s);
	}

	@Test
	public void testGetMsgs() {
		List<String> testMsgs = Arrays.asList(new String[] {"msg1", "msg2"});
		NoSpringReflection.setPrivateMember(msg, "messages", testMsgs);
		List<String> actMsgs = msg.getMsgs();
		assertTrue(actMsgs == testMsgs);
	}

	@Test
	public void testAddThrowables() {
		Exception e = new Exception("test");
		msg.addThrowables(e);
		List<Throwable> val = (List<Throwable>) NoSpringReflection.getPrivateMember(msg, "throwables");
		assertTrue(val.get(0) == e);
	}

	@Test
	public void testGetThrowables() {
		List<Throwable> testThrowJull = Arrays.asList(
				new Throwable[] {new Exception("aqualung"), new RuntimeException("my friend")});
		NoSpringReflection.setPrivateMember(msg, "throwables", testThrowJull);
		List<Throwable> actThrowable = msg.getThrowables();
		assertTrue(testThrowJull == actThrowable);
	}

}
