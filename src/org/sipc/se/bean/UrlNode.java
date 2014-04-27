package org.sipc.se.bean;

import java.util.ArrayList;
import java.util.List;

import org.sipc.se.plugin.PluginImpl;

public class UrlNode { 
	
	public String selfUrl = "~" ;
	public String parentUrl ;
	public PluginImpl pluginNode ;
	public List<UrlNode> childNodes = new ArrayList<UrlNode>() ;;
	
	public UrlNode(){}
	
	public UrlNode( PluginImpl pluginNode ){
		this.pluginNode = pluginNode ;
		getSelfUrl(pluginNode) ;
	}
	
	public void getSelfUrl(PluginImpl pluginNode){
		String str[] = pluginNode.getUrl().split("/") ;
		
		this.selfUrl = str[ str.length-1 ] ;
		this.parentUrl = str[str.length -2] ;
	}
	
	public boolean addNode(UrlNode node){
		
		if (node.parentUrl.equals(this.selfUrl)){
			childNodes.add(node) ;
			return true ;
		}else{
			for(UrlNode urlNode : childNodes){
				if( urlNode.addNode(node) )
					return true ;
			}
		}
		return false ;
	}
	
	public boolean hasNode(String[] url , int n){
		
		if(url.length - 1 == n){
			for(UrlNode childNode : childNodes){
				if(childNode.selfUrl.equals(url[n])){
					return true ;
				}
			}
			return false ;
		}else{
			for(UrlNode node : childNodes){
				if( node.hasNode(url, n+1)) 
					return true ;
			}
		}
		return false ;

	}
   
	public PluginImpl getByPath(String[] url , int n){
		if(url.length -1 == n){
			for(UrlNode childNode: this.childNodes){
				if(childNode.selfUrl.equals(url[n])){
					return childNode.pluginNode ;
				}
			}
			return null ;
		}else{
			for(UrlNode node : childNodes){
				if(node.selfUrl.equals(url[n])){
					PluginImpl pluginImpl = node.getByPath(url, n+1) ;
					if( pluginImpl != null) 
						return pluginImpl ;
					else 
						return node.pluginNode ;
				}
			}
		}
		return null ;
	}
	
}
 