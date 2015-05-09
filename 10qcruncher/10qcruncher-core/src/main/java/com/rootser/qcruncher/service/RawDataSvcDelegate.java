package com.rootser.qcruncher.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.rootser.qcruncher.common.AppMsg;
import com.rootser.qcruncher.common.RawData;

@Component
public class RawDataSvcDelegate<T> {
	Logger logger = LoggerFactory.getLogger(RawDataSvcDelegate.class);
	@SuppressWarnings("unchecked")
	public List<AppMsg<RawData>> processList(List<AppMsg<T>> inputList){
		if (inputList == null){
			logger.debug("received null list");
			AppMsg<RawData> appMsg = new AppMsg<RawData>();
			appMsg.addMsg("10Q cruncher received an empty list of 10Q data when attempting to get raw data from Edgar.");
			return Lists.newArrayList(appMsg);
		} else {
			List<AppMsg<RawData>> result = new ArrayList<AppMsg<RawData>>();
			for (AppMsg<T> appMsg: inputList){
				AppMsg<RawData> cur = new AppMsg<RawData>();
			}
		}
		return null;
	}

}
