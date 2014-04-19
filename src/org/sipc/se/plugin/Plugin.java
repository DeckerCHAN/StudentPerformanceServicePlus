package org.sipc.se.plugin;

import javax.servlet.http.HttpServletResponse;

public interface Plugin {
	
	public String getUrl() ;
	
	public boolean onEnable() ;
	
	public HttpServletResponse getResponse(HttpServletRequest request) ;
	
}
