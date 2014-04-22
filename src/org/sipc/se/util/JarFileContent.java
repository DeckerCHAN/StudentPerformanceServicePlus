package org.sipc.se.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sipc.se.plugin.Plugin;

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
	
	public static String getYMLConfig(String fileName) throws IOException{
		String className = null ;
		JarFile  jarFile = new JarFile (fileName) ;
		JarEntry jarEntry = jarFile.getJarEntry( StaticValue.JAR_YMLFILE ) ;
		
		//Read .yml File
		InputStream input = jarFile.getInputStream(jarEntry) ;
		InputStreamReader inputReader = new InputStreamReader(input) ;
		BufferedReader reader = new BufferedReader(inputReader) ;
		
		//Read
		String line = null ;
		while((line = reader.readLine() ) != null){
			if(line.contains("package")){
				className = line.split(":")[1].trim() ;
			}
		}
		
		//Close Readstream
		reader.close() ;
		jarFile.close() ;
		
		return className ;
		
	}
	
	public static List<Plugin> getPluginList(String filePath) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		File fileList = findJarFile(filePath) ;
		List<Plugin> pluginList = new ArrayList<Plugin>() ;
		
		for(String fileName : fileList.list() ){
			
			log.info("Plugin Name : " + fileName) ;
			String className = getYMLConfig(filePath + "/" + fileName ) ;
			
			//Write log file
			log.info("Package.ClassName : " + className ) ;
			
			//Load Plugin Instance
			Plugin plugin = JarFileLoad.jarFileLoad( filePath, fileName , className) ;
			
			//Add All Plugin Into List
			if( plugin.onEnable() ) {
				
				pluginList.add(plugin) ;
			}
		}
		return pluginList ;
	}
	
}
