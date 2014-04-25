package org.sipc.se.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileLoad { 
	
	public static Properties readPropertiesFile(String path) throws IOException{
		Properties properties = new Properties() ;
		
		InputStream input = new FileInputStream(path + "/jdbc.properties") ;
		properties.load(input) ;
		
		input.close() ; 
		return properties ; 
	}
}
