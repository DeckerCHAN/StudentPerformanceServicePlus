package org.sipc.se.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.sipc.se.bean.PluginTree;
import org.sipc.se.plugin.PluginImpl;

public class TreeCollections {
	
	public static boolean initTree(Map<String , PluginImpl> plugins){
		
		List<PluginImpl> list = new ArrayList<PluginImpl>() ;
		for(PluginImpl plugin : plugins.values()) {
			list.add(plugin) ;
		}
		
		Collections.sort(list) ;
		
		PluginTree tree = PluginTree.getInstance() ;
		
		try{
			for(PluginImpl plugin : list){
				tree.insertNodeIntoTree(plugin) ;
			}
			return true ;
		}catch(Exception e){
			e.printStackTrace() ;
			return false ;
		}
	}
}
