package org.sipc.se.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sipc.se.dbc.DataBaseConnection;
import org.sipc.se.plugin.Plugin;
import org.sipc.se.util.JarFileContent;
import org.sipc.se.util.StaticValue;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class ManagerCenterServlet extends HttpServlet {
	
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
			this.checkDBInfo(config) ;
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
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
		//SetEncode
		request.setCharacterEncoding("utf-8") ;
		response.setCharacterEncoding("utf-8") ;

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
	protected void loadAllPlugin(ServletConfig config) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		pluginList = JarFileContent.getPluginList(config.getServletContext().getRealPath(StaticValue.CENTER_FILEPATH_VALUE)) ;
			
	}
	
	/**
	 * The function to route URL . Need requestPath , httpRequest , httpRespnose 
	 * It can turn into each section which the url are needed. 
	 * @param path
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	
	protected void routerURL(String path , HttpServletRequest request , HttpServletResponse response) throws IOException{
		
		boolean isFindURL = false ;
		//Print Request Path On Console
		System.out.println(path) ;
		
		//First Page
		if(path.equals("/") || path.equals("/*")){
			response.getWriter().print("First Page!!") ;
			isFindURL = true ;
		}else{
			
			//Router
			for(Plugin plugin : pluginList){
				if(path.split("/")[1].equals(plugin.getUrl())){
					//Get The Plgin Object 
					plugin.getResponse(request, response) ;
					isFindURL = true ;
				}
			}
		}
		if(!isFindURL){
			response.getWriter().print("<h2>Error page !</h2><h1>Please Check URL!</h1>") ;
		}
	}
	
	public void checkDBInfo(ServletConfig config) throws IOException{
		
		DataBaseConnection db = new DataBaseConnection(config.getServletContext().getRealPath("/")) ;
		if(!db.checkDataBaseDriver()){
			System.out.println("请查看数据库驱动程序!") ;
		}else
		{
			if(!db.checkDataBaseConnection()){
				System.out.println("数据库未能连接，查看配置文件是否正确!") ;
			}else{
				System.out.println("数据库连接成功") ;
			}
		}
	}
	
	public void getServer(){
		
	}
}
