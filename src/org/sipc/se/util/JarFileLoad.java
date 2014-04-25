package org.sipc.se.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.sipc.se.plugin.PluginImpl;

 
class JarLoader extends URLClassLoader {
	
	private static JarLoader loader = null;
	
	private JarLoader(){
		super(new URL[0], JarLoader.class.getClassLoader());
	}
	public static JarLoader getInstance(){
		if(loader==null)
			loader = new JarLoader();
		return loader;
	}
	public void addURL(String url) throws MalformedURLException {

		this.addURL(new URL(url));
	}

}

public class JarFileLoad{
	
	public static PluginImpl jarFileLoad(String pluginPath , String plginName , String classFullName) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		JarLoader loader = JarLoader.getInstance();
		
		//Load yourProject/Plugin/*.jar
		loader.addURL("jar:file:///" + pluginPath + "/" + plginName + "!/");
		//New Instance
		
		try{
			PluginImpl plugin = (PluginImpl)Class.forName( classFullName , true, loader).newInstance();
			return plugin ;
		}catch(Exception e){
			return null ;
		}
		
	}
}
