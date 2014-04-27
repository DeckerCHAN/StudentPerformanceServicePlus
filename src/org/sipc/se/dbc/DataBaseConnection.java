package org.sipc.se.dbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.sipc.se.util.PropertiesFileLoad;

public class DataBaseConnection { 
	
	private static String DBDRIVER = null ;
	private static String DBURL = null ;
	private static String DBUSER = null ;
	private static String DBPASS = null ;
	
	private  Connection conn = null ;
	
	public void getDataBaseInfo(String path) throws IOException{
		
		Properties  properties = PropertiesFileLoad.readPropertiesFile(path) ;
		DBDRIVER = properties.getProperty("driver").trim() ;
		DBURL = properties.getProperty("url").trim() ;
		DBUSER = properties.getProperty("user").trim() ;
		DBPASS = properties.getProperty("password").trim() ;
		
	}
	
	public DataBaseConnection(String path) throws IOException{
		getDataBaseInfo(path) ;
	}
	
	public DataBaseConnection(){
		checkDataBaseDriver() ;
		checkDataBaseConnection() ;
	}
	
	public Connection getConnection(){
		return this.conn ;
	}
	
	public boolean checkDataBaseConnection(){
		try{
			this.conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS) ;
		}catch(Exception e){
			return false ;
		}
		return true ;
	}
	
	public boolean checkDataBaseDriver(){
		try{
			Class.forName(DBDRIVER) ;
		}catch(Exception e){
			return false ;
		}
		return true ;
	}
	
	public void closeConnection(){
		
		try{
			if(this.conn != null)
				this.conn.close() ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
}
