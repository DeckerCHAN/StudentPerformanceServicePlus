package org.sipc.se.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sipc.se.plugin.PluginImpl;

public class JarFileContent { 
	
	static Logger log = LogManager.getLogger(JarFileContent.class.getName()) ;
	
	public static File findJarFile(String filePath) throws IOException{
		File fileList = new File(filePath) ;
		if(fileList.exists())
		{ 
			return fileList ;
		}
		
		return null ;
		
	}
	
	public static String[] getFileList(String filePath) throws IOException{
		
		return findJarFile(filePath).list() ;
		
	}	
	
	public static String[] getYMLConfig(String fileName) throws IOException{
		
		String[] str = new String[2] ;
		
		JarFile  jarFile = new JarFile (fileName) ;
		JarEntry jarEntry = jarFile.getJarEntry( StaticValue.JAR_YMLFILE ) ;
		
		//Read .yml File
		InputStream input = jarFile.getInputStream(jarEntry) ;
		InputStreamReader inputReader = new InputStreamReader(input) ;
		BufferedReader reader = new BufferedReader(inputReader) ;
		
		//Read
		String line = null ;
		while((line = reader.readLine() ) != null){
			if(line.contains("name")){
				str[0] = line.split(":")[1].trim() ;
			}
			if(line.contains("package")){
				str[1] = line.split(":")[1].trim() ;
			}
		}
		
		//Close Readstream
		reader.close() ;
		jarFile.close() ;
		
		return str ;
		
	}
	
	public static Map<String,PluginImpl> getPluginList(String filePath) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		File fileList = findJarFile(filePath) ;
		Map<String,PluginImpl> pluginList = new HashMap<String,PluginImpl>() ;
		
		for(String fileName : fileList.list() ){
			
			
			String[] ymlContent = getYMLConfig(filePath + "/" + fileName ) ;
			log.info("Plugin Name : " + ymlContent[0] ) ;
			//Load Plugin Instance
			
			PluginImpl plugin = JarFileLoad.jarFileLoad( filePath, fileName , ymlContent[1]) ;
			plugin.setMap(pluginList) ;
			
			//Add All Plugin Into List
			pluginList.put(ymlContent[0] , plugin) ;
		}
		return pluginList ;
	}
	
}
