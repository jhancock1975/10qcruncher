package com.rootser.qcruncher.common;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AppMsgCatcher {
	Logger logger = LoggerFactory.getLogger(AppMsgCatcher.class);
	@AfterReturning(pointcut = "execution (* com.rootser.qcruncher.common.AppMsg.addMsg(..))",
			returning = "result")
	public void catchMsg(JoinPoint joinPoint, Object result){
		List<String> msgList = (List<String>) result;
		for (String s: msgList){
			logger.debug("in aspect j " + s);
		}
	}
}
