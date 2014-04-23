package org.sipc.se.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class PluginImpl implements Plugin , Comparable<PluginImpl> {

	public abstract String getUrl() ; 
	
	public abstract boolean onEnable() ; 

	public abstract void getResponse(HttpServletRequest request,
			HttpServletResponse response) ;

	@Override
	public int compareTo(PluginImpl o) {
		
		return this.getUrl().compareTo(o.getUrl());
		
	}
	
}
