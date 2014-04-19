package org.sipc.se.plugin;

import javax.servlet.http.HttpServletResponse;

public interface Plugin {
	
	public String getURL() ;
	
	public boolean onEnable() ;
	
	public HttpServletResponse getResponse() ;
	
}
