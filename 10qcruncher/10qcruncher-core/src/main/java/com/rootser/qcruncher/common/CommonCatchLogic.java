package com.rootser.qcruncher.common;

import org.slf4j.Logger;

public class CommonCatchLogic {
	public static void commonCatchLogic(Logger logger, AppMsg<?> appMsg , Throwable e){
		logger.debug(e.getMessage());
		appMsg.addThrowables(e);
		appMsg.setHasErrors(true);
	}
}
