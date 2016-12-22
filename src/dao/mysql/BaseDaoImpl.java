package dao.mysql;

import java.sql.Connection;

import dao.pool.ConnectionPool;
import exception.PersistentException;

abstract public class BaseDaoImpl {
	protected Connection connection;

	
	public BaseDaoImpl () {
	        Connection conn=null;
	        try {
	            conn = ConnectionPool.getInstance().getConnection();
	        } catch (PersistentException e) {
	        }
	        setConnection(conn);
	        ///System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  "+conn.toString());
	    }
	
	
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	
}
