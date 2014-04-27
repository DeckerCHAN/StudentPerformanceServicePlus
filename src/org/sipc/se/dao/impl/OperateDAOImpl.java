package org.sipc.se.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.sipc.se.dao.OperateDAO;


public class OperateDAOImpl implements OperateDAO {

	private Connection conn ;
	public OperateDAOImpl(Connection conn){
		this.conn = conn ;
	}
	
	@Override
	public ResultSet doQuery(String sql) throws Exception {
		
		PreparedStatement pre = this.conn.prepareStatement(sql) ;
		ResultSet set = pre.executeQuery() ;
		
		return set;
	}
	
	@Override
	public int doUpdate(String sql) throws Exception {
		
		PreparedStatement pre = this.conn.prepareStatement(sql) ;
		int count = pre.executeUpdate() ;
		
		return count;
	}
}
