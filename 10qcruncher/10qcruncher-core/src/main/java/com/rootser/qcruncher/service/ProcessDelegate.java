/**
 * a common pattern in this code
 * is to process an AppMsg or a list of AppMsg
 * This class does the common work of checking
 * if there is actually anything to process.
 * 
 * If there is then it will call the process method
 * of the plugin.  The process method does any logic
 * specific to the class of T where AppMsg takes
 * T as a type parameter.
 */
package com.rootser.qcruncher.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.CommonCatchLogic;
import com.rootser.qcruncher.plugin.Plugin;

public class ProcessDelegate<T, S> {
	Logger logger = LoggerFactory.getLogger(ProcessDelegate.class);


	public  AppMsg<T> applyPluginProcess(AppMsg<S> appMsg, Plugin<S, T> plugin){
		AppMsg<T> processedResult = new AppMsg<T>();
		if (appMsg == null){
			logger.debug("AppMsg for getDoc is null.");
			processedResult.addMsg("10qcruncher is trying to process a null application message.");
			processedResult.setHasErrors(true);
		} else {
			if (appMsg.getResult() == null){
				logger.debug("AppMsg.getResult is null.");
				processedResult.addMsg("10qcruncher is trying to process an application message with a null content.");
				processedResult.setHasErrors(true);
			} else {
				try {

					processedResult = plugin.process(appMsg);


				} catch(IllegalArgumentException e){

					CommonCatchLogic.commonCatchLogic(logger, processedResult, e);
					processedResult.addMsg("10qcruncher thinks that " + appMsg.getResult() +
							" is not a valid object for processing.");
				}
			}
		}


		if (processedResult == null){
			logger.debug("plugin for process delegate of type " + this.getClass()+ "plugin.process returned null  ");
			processedResult = new AppMsg<T>();
			processedResult.addMsg("A plugin returned a null app message.");
			processedResult.setHasErrors(true);
		}
		if (processedResult.getResult() == null){
			processedResult.addMsg("10qcruncher plugin " + plugin.getClass() + " returned null when processing " + appMsg.toString());
			processedResult.setHasErrors(true);
		}

		return processedResult;
	}
	@SuppressWarnings("unchecked")
	public List<AppMsg<T>> applyPluginProcessList(List<AppMsg<S>> appMsgs, Plugin<S, T> plugin){

		AppMsg<T> errMsg = new AppMsg<T>();

		if (appMsgs == null){

			logger.debug("AppMsg for applyPluginProcessList (list method) is null");
			errMsg.addMsg("10qcruncher is trying to process a null list of application messages.");
			errMsg.setHasErrors(true);
			return (List<AppMsg<T>>) Lists.newArrayList(errMsg);

		} else {

			List<AppMsg<T>> resultList = new ArrayList<AppMsg<T>>();
			for (AppMsg<S> url : appMsgs){
				resultList.add(applyPluginProcess(url, plugin));
			}

			return(resultList);
		}
	}
}
