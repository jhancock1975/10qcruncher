package com.rootser.qcruncher.integration.common;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

public class ArffData extends HashMap<Pair<String,Date>, Double>{
	
	private static final long serialVersionUID = 1L;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
			for (Pair<String,Date> s: keySet()){
				sb.append(s + " = " + get(s)+"\n");
			}
		return sb.toString();
	}
	
	
}
