package dao.mysql;

import java.sql.Connection;
import dao.pool.ConnectionPool;
import exception.PersistentException;

abstract public class BaseDaoImpl {
    protected Connection connection; // Connection for all dao

    public BaseDaoImpl() {
        Connection conn = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
        } catch (PersistentException e) {
        }
        setConnection(conn);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
