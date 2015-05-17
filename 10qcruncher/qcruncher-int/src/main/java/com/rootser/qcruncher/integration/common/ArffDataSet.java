package com.rootser.qcruncher.integration.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArffDataSet {
	
	public ArffDataSet(){
		this.dataSet = new ArrayList<ArffData>();
	}
	List<ArffData> dataSet;
	
	public void setDataSet(List<ArffData> dataSet){
		this.dataSet = dataSet;
	}
	
	public List<ArffData> getDataSet(){
		return dataSet;
	}
	
	public void add(ArffData data){
		this.dataSet.add(data);
	}
	
	public Map<String,Integer> getAttributeCounts(){
		Map<String, Integer> attrCounts = new HashMap<String, Integer>();
		for(ArffData data: dataSet){
			Map<String,Integer> tmpAttrCounts = data.getAttributeCounts();
			for (String key: tmpAttrCounts.keySet()){
				if ( ! attrCounts.containsKey(key)){
					attrCounts.put(key, tmpAttrCounts.get(key));
				} else {
					attrCounts.put(key, Math.max(attrCounts.get(key), tmpAttrCounts.get(key)));
				}
			}
		}
		return attrCounts;
	}
}
