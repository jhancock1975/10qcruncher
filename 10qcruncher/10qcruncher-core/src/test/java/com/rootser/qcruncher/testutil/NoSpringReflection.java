package com.rootser.qcruncher.testutil;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoSpringReflection {
	private static Logger logger = LoggerFactory.getLogger(NoSpringReflection.class);
	
	public static Object getPrivateMember(Object o, String memberName){
		Field f;
		try {
		
			f = o.getClass().getDeclaredField(memberName);
			f.setAccessible(true);
			return f.get(o);
			
		} catch (NoSuchFieldException e) {
			logger.debug(e.getMessage());
		} catch (SecurityException e) {
			logger.debug(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.debug(e.getMessage());
		}
		return null;
	}
	public static void setPrivateMember(Object o, String memberName, Object value){
		Field f;
		try {
			f = o.getClass().getDeclaredField(memberName);
			f.setAccessible(true);
			f.set(o, value);
		} catch (NoSuchFieldException e) {
			logger.debug(e.getMessage());
		} catch (SecurityException e) {
			logger.debug(e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.debug(e.getMessage());
		}
	}

}
