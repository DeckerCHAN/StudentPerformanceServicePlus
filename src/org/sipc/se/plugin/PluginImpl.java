package org.sipc.se.plugin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PluginImpl implements Plugin , Comparable<PluginImpl> {
	
	public Logger log = LogManager.getLogger() ;
	
	public abstract String getUrl() ; 
	
	public abstract boolean onEnable() ; 

	public abstract void getResponse(HttpServletRequest request,
			HttpServletResponse response) ;
	
	public String toString(){
		
		return getUrl() ;
	}
	
	@Override
	public int compareTo(PluginImpl o) {
		
		return this.getUrl().compareTo(o.getUrl());
		
	}
	
}
