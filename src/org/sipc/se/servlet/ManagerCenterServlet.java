package org.sipc.se.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sipc.se.plugin.Plugin;
import org.sipc.se.util.StaticValue;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class ManagerCenterServlet extends HttpServlet {
	private static int num = 0;
	private static final long serialVersionUID = 1L;
	private List<Plugin> pluginList = new ArrayList<Plugin>() ;
	
    /**
     * Default constructor. 
     * @throws IOException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
	
    public ManagerCenterServlet() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // TODO Auto-generated constructor stub
    	
    }

	@Override
	public void init(ServletConfig config) { 
		// TODO Auto-generated method stub
		
		try {
			this.loadAllPlugin(config) ;
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
    	this.checkDBInfo() ;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response) ;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8") ;
		response.setCharacterEncoding("utf-8") ;
		System.out.println("TIME : " + (num++) + " , PATH : " + request.getPathInfo() ) ;
		routerURL(request.getPathInfo() , request , response ) ;
	}
	
	/**
	 * loadALLPlugin
	 * @param config
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void loadAllPlugin(ServletConfig config) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		File fileList = new File(config.getServletContext().getRealPath(StaticValue.CENTER_FILEPATH_VALUE)) ;
		
		//print on console
		System.out.println(fileList.getCanonicalPath());
		System.out.println(fileList.exists());
		
		//foreach all jar file
		for(String fileName : fileList.list() ){
			
			//Read yml which is in Jar file bn
			String className = null ;
			JarFile  jarFile = new JarFile ( config.getServletContext().getRealPath(StaticValue.CENTER_FILEPATH_VALUE) + "/" + fileName) ;
			JarEntry jarEntry = jarFile.getJarEntry( StaticValue.JAR_YMLFILE ) ;
			
			// read .yml file
			InputStream input = jarFile.getInputStream(jarEntry) ;
			InputStreamReader inputReader = new InputStreamReader(input) ;
			BufferedReader reader = new BufferedReader(inputReader) ;
			String line = null ;
			while((line = reader.readLine() ) != null){
				if(line.contains("package")){
					className = line.split(":")[1].trim() ;
				}
			}
			//close readstream
			reader.close() ;
			jarFile.close() ;
			
			//load Plugin instance
			System.out.println(className) ;
			Plugin plugin = (Plugin)Class.forName(className).newInstance() ;
			if(plugin.onEnable()){
				pluginList.add(plugin) ;
			}
		}
	}
	
	public void checkDBInfo(){
	
	}
	
	public void getServer(){
		
	}
	
	public void routerURL(String path , HttpServletRequest request , HttpServletResponse response) throws IOException{
		System.out.println(path) ;
		if(path.equals("/") || path.equals("/*")){
			response.getWriter().print("First Page!!") ;
		}else{
			for(Plugin plugin : pluginList){
				if(path.split("/")[1].equals(plugin.getUrl())){
					//get the plgin object 
					plugin.getResponse(request, response) ;
				}
			}
		}
	}
}
