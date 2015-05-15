package com.rootser.qcruncher.common;

import org.slf4j.Logger;

public class CommonCatchLogic {
	public static AppMsg<?> commonCatchLogic(Logger logger, AppMsg<?> appMsg , Throwable e){
		logger.debug(e.getMessage());
		appMsg.addThrowables(e);
		appMsg.setHasErrors(true);
		return appMsg;
	}
	
	 public static AppMsg<?> commonCatchLogic(Logger logger, AppMsg<?> appMsg , Throwable e, String msg){
			logger.debug(e.getMessage());
			appMsg.addThrowables(e);
			appMsg.setHasErrors(true);
			appMsg.addMsg(msg);
			return appMsg;
		}
}
