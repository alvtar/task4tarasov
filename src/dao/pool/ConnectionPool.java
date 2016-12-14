package dao.pool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import exception.PersistentException;


final public class ConnectionPool {
    
    private static Logger logger = Logger.getLogger(ConnectionPool.class);
    
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private Properties db_properties;
    private String db_url;;
    private String db_user;
    private String db_password;
    
    
    //public static final String DB_URL = "jdbc:mysql://localhost:3306/Elective?useUnicode=true&characterEncoding=UTF-8";
    //public static final String DB_USER = "admin";
    //public static final String DB_PASSWORD = "admin";

    private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>();

    private ConnectionPool() {
    }

    synchronized public Connection getConnection() throws PersistentException {
        Connection connection = null;
        while(connection == null) {
            try {
                if(connections.isEmpty()) {
                    connection = DriverManager.getConnection(db_url, db_properties);
                } else {
                    connection = connections.take();
                    if(!connection.isValid(0)) {
                        connection = null;
                    } // Вставить ограничение максимальных подключений
                }
            } catch(InterruptedException e) {
                throw new PersistentException(e);
            } catch(SQLException e) {
                throw new PersistentException(e);
            }
        }
        return connection;
    }

    public void freeConnection(Connection connection) {
        try {
            connections.put(connection);
        } catch(InterruptedException e) {
        }
    }

    public void init() throws PersistentException {
        try {
            Class.forName(DRIVER_CLASS);
            
            db_properties = new Properties();
            db_properties.load(new FileInputStream("db.properties"));
            db_user = db_properties.getProperty("user");
            db_password = db_properties.getProperty("password");
            db_url = db_properties.getProperty("url");
            
            
        } catch(ClassNotFoundException | IOException e) {
            throw new PersistentException(e);
        }
    }

    
    
    private static ConnectionPool instance = new ConnectionPool();
    
    public static ConnectionPool getInstance() {
        return instance;
    }
}