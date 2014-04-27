package org.sipc.se.dao;

import java.sql.ResultSet;


public interface OperateDAO {
	
	public ResultSet doQuery(String sql) throws Exception ;
	
	public int doUpdate(String sql) throws Exception ;
	
}
