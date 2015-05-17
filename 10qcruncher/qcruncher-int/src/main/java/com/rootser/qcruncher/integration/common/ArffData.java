package com.rootser.qcruncher.integration.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class ArffData {

	public ArffData(){
		numericData = new HashMap<String, ArrayList<Pair<String, Double>>>();
	}

	private static final long serialVersionUID = 1L;

	private  String cik;

	private Date startDate;
	
	private Date endDate;

	private HashMap<String, ArrayList<Pair<String, Double>>> numericData;



	public String getCik() {
		return cik;
	}

	public void setCik(String cik) {
		this.cik = cik;
	}
	

	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append("cik = " + this.cik + "\n");

		sb.append("start date: " + this.startDate + "\n");
		sb.append("end date: " + this.endDate + "\n");

		for ( String attrName: numericData.keySet()){
			List<Pair<String, Double>> curList = numericData.get(attrName);
			sb.append(attrName + " -> ");
			for (Pair<String, Double > dataPoint: curList){
				sb.append("("+ dataPoint.getLeft() + "," + dataPoint.getRight() + ")\n");
			}
		}

		return sb.toString();
	}

	public boolean containsNumericDataFor(String s){
		return numericData.containsKey(s);
	}

	public ArrayList<Pair<String, Double>> getListFor(String s){
		return numericData.get(s);
	}

	public String getNumericDataArffString(String s){

		StringBuilder result = new StringBuilder();
		ArrayList<Pair<String, Double>> list = numericData.get(s);

		if (list != null){
			for(Pair<String, Double> curPair : list){
				if (curPair != null) {
					result.append(curPair.getRight()+",");
				}
			}
		}
		result.setLength(result.length() - 1);
		result.append("\n");

		return result.toString();
	}

	public Map<String, Integer> getAttributeCounts(){

		Map<String, Integer> attributes = new HashMap<String, Integer>();


		for(String attribute: numericData.keySet()){
			if (numericData.get(attribute) != null){
				attributes.put(attribute, numericData.get(attribute).size());
			}
		}
		return attributes;
	}
	
	private ArrayList<Pair<String, Double>> getAddAttribute(String attributeName){
		ArrayList<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>();
		if (! numericData.containsKey(attributeName)){
			numericData.put(attributeName, result );
		} else {
			result = numericData.get(attributeName);
		}
		return result;
	}
	
	public void addData(String attributeName, Pair<String, Double> pair){
		List<Pair<String, Double>> list = getAddAttribute(attributeName);
		list.add(pair);
	}
}
