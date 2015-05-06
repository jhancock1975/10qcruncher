package com.rootser.qcruncher.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * this class defines an object that
 * services should use for returning results
 * @author john
 *
 */
@Component
public class AppMsg<T> {
	private T result;
	private List<Throwable> throwables;
	private List<String> messages;
	private boolean hasErrors;

	public AppMsg(){
		throwables = new ArrayList<Throwable>();
		messages = new ArrayList<String>();
		hasErrors = false;
	}

	public AppMsg(T result) {
		this();
		this.result = result; 
	}

	public T getResult(){
		return result;
	}
	
	public void setResult(T result){
		this.result = result;
	}
	
	private boolean has(Collection<?> coll){
		return ! CollectionUtils.isEmpty(coll);
	}
	public boolean hasThrowables(){
		return has(throwables);
	}
	
	public boolean hasMessages(){
		return has(messages);
	}
	
	public List<String> addMsg(String msg){
		messages.add(msg);
		return messages;
	}
	
	public List<String>	addMsg(List<String> msgs){
		messages.addAll(msgs);
		return messages;
	}
	
	public List<String> getMsgs(){
		return messages;
	}
	
	public List<Throwable> addThrowables(Throwable t){
		throwables.add(t);
		return throwables;
	}
	
	public List<Throwable> addThrowables(List<Throwable> t){
		throwables.addAll(t);
		return throwables;
	}
	
	public List<Throwable> getThrowables(){
		return throwables;
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	
}
