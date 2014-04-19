package org.sipc.se.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Plugin {
	
	public String getUrl() ;
	
	public boolean onEnable() ;
	
	public void getResponse(HttpServletRequest request , HttpServletResponse response) ;
	
}
