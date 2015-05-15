/**
 * This class should be used in conjunction
 * with ProcessDelegate;
 * 
 * 
 */
package com.rootser.qcruncher.plugin;

import com.rootser.qcruncher.common.AppMsg;


/**
 * @author john
 *
 * @param <T>
 */
public interface Plugin<S, T>{
	public AppMsg<T> process(AppMsg<S> inputParam) ;
}
