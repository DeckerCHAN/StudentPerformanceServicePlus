package org.sipc.se.bean; 

import org.sipc.se.plugin.PluginImpl;

public class PluginTree {
	
	private UrlNode rootNode ;
	private static PluginTree treeNode = new PluginTree() ;
	
	private void initTreeNode(){
		rootNode = new UrlNode() ;
	}
	
	//Init RootNode
	private PluginTree(){
		initTreeNode() ;
	}
	
	// Single Instance
	public static PluginTree getInstance(){
		return treeNode ;
	}
	
	public boolean insertNodeIntoTree(PluginImpl plugin){
		return rootNode.addNode(new UrlNode(plugin)) ;
	}
	
	private void showAllNodesOnTree(UrlNode node , int i ){
		
		System.out.println("Level : " + i + " , Path : " + node.selfUrl + " , parentUrl : " + node.parentUrl + " , childNum :" + node.childNodes.size() );
		
		if( node.childNodes == null || node.childNodes.size() == 0)
			return ;
		for(UrlNode nodes : node.childNodes){
			showAllNodesOnTree(nodes , i + 1) ;
		}
	}
	
	public void showAllNodes(){
		
		System.out.println("==========================");
		showAllNodesOnTree(rootNode , 1) ;
		System.out.println("==========================");
		
	}
	
	public PluginImpl getPluginByPath(String fullPath){
		return rootNode.getByPath(fullPath.split("/"), 1) ; 
	}
	
	public boolean hasNode(String fullPath){
		return rootNode.hasNode(fullPath.split("/"), 1) ;
	}

	public UrlNode getRootNode() {
		return rootNode;
	}
	
}

