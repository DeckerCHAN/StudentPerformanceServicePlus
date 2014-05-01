package org.sipc.se.dao.proxy;

import java.sql.ResultSet;

import org.sipc.se.dao.OperateDAO;
import org.sipc.se.dao.impl.OperateDAOImpl;
import org.sipc.se.dbc.DataBaseConnection;


public class OperateDAOProxy implements OperateDAO {

	private DataBaseConnection conn ;
	private OperateDAO operate ;
	
	public OperateDAOProxy(){
		this.conn = new DataBaseConnection() ;
		this.operate = new OperateDAOImpl(this.conn.getConnection()) ;
	}
	
	public OperateDAOProxy(String url, String user,String password){
		this.conn = new DataBaseConnection(url , user , password) ;
		this.operate = new OperateDAOImpl(this.conn.getConnection()) ;
	}
	
	@Override
	public ResultSet doQuery(String sql) {
		
		try {
			return this.operate.doQuery(sql) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	@Override
	public int doUpdate(String sql) {
		
		try {
			return this.operate.doUpdate(sql) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0 ;
		}
	}
	
	public void closeDBConnection(){
		 conn.closeConnection() ;
	}
}
