package org.sipc.se.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sipc.se.bean.PluginTree;
import org.sipc.se.dao.factory.OperateFactory;
import org.sipc.se.dbc.DataBaseConnection;
import org.sipc.se.plugin.PluginImpl;
import org.sipc.se.util.JarFileContent;
import org.sipc.se.util.StaticValue;
import org.sipc.se.util.TreeCollections;


/** 
 * Servlet implementation class LoginServlet
 */
public class ManagerCenterServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; 
	private Map<String,PluginImpl> pluginList = null ;
	static Logger log = LogManager.getLogger() ;
	
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
			this.getServer();
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
		
		//Set Into Log
		//log.info(request.getRemoteAddr()+">>"+request.getPathInfo()) ;
	    	log.info(String.format("[REQUEST] %s:%d>>%s",request.getRemoteAddr(),request.getRemotePort(),request.getPathInfo()));
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
		
		//Sort allPlugin list

		boolean isERROR = TreeCollections.initTree(pluginList) ;
		
		log.info("Plugin加载情况： " + isERROR) ;
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
		
		//First Page
		if(path.equals("/") || path.equals("/*")){
			response.getWriter().print("First Page!!") ;
		}else{
			
			//Router
			PluginImpl plugin = PluginTree.getInstance().getPluginByPath(path) ;
			if(plugin != null){
				plugin.getResponse(request, response) ;
			}else{
				response.sendError(404) ;
			}
		}
	}
	
	public void checkDBInfo(ServletConfig config) throws IOException{
		
		DataBaseConnection db = new DataBaseConnection(config.getServletContext().getRealPath("/")) ;
		if(!db.checkDataBaseDriver()){
			log.info("请查看数据库驱动程序！") ;
		}else
		{
			if(!db.checkDataBaseConnection()){
				log.info("数据库未能连接，查看配置文件是否正确!") ;
			}else{
				log.info("数据库连接成功!") ;
			}
		}
		db.closeConnection() ;
	}
	
	public void getServer(){
		OperateFactory factory = OperateFactory.getInstance() ;
		factory.initMap(pluginList) ;
	}
}
