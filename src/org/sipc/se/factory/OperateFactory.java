package org.sipc.se.dao.factory;

import java.util.Map;

import org.sipc.se.bean.PluginTree;
import org.sipc.se.dao.OperateDAO;
import org.sipc.se.dao.proxy.OperateDAOProxy;

import org.sipc.se.plugin.PluginImpl;

public class OperateFactory {
	
	private static OperateFactory factory = new OperateFactory() ;
	private Map<String , PluginImpl> map ;
	
	private OperateFactory(){}
	
	public static OperateFactory getInstance(){
		return factory ;
	}
	
	public void initMap(Map<String , PluginImpl> map){
		this.map = map ;
	}
	
	public PluginTree getPluginTree(){
		return PluginTree.getInstance() ;
	}
	
	public PluginImpl getPluginByName(String name){
		return this.map.get(name) ;
	}
	
	public OperateDAO getDAOInstance(){
		return new OperateDAOProxy() ; 
	}
}
