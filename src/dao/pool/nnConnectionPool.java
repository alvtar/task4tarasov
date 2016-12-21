package dao.pool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class nnConnectionPool {

    private Logger log = LoggerFactory.getLogger(getClass());

    private int maxPool;

    /** JDBC URL to use for connecting to the database server. */
    private String url;

    /** Username to use for connecting to the database server. */
    private String user;

    /** Password to use for connecting to the database server. */
    private String pass;

    private List<PooledConnection> free, used;

    
    
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static Properties db_properties;
    private static String db_url;;
    private static String db_user;
    private static String db_password;
    
    
    
    
    
    
    public ConnectionPool() throws ClassNotFoundException, FileNotFoundException, IOException {
      //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        
        Class.forName(DRIVER_CLASS);
        
        db_properties = new Properties();
        db_properties.load(new FileInputStream("db.properties"));
        db_user = db_properties.getProperty("user");
        db_password = db_properties.getProperty("password");
        db_url = db_properties.getProperty("url");
        
        
        
        
    }

    
    
    
    
    
    public ConnectionPool(int minPool, int maxPool, String url, String username, String password) throws SQLException {
        this.maxPool = maxPool;
        this.url = url;
        this.user = username;
        this.pass = password;

        free = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));
        used = Collections.synchronizedList(new ArrayList<PooledConnection>(maxPool));

        for (int i = 0; i < minPool; i++) {
            free.add(createConnectionWrapper());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }

    public synchronized void destroy() {
        for (PooledConnection cw : free) {
            try {cw.getRawConnection().close();} catch (Exception e) {log.info("Unable to close connection");}
        }

        for (PooledConnection cw : used) {
            try {cw.getRawConnection().close();} catch (Exception e) {log.info("Unable to close connection");}
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        PooledConnection cw = null;

        if (free.size() > 0) {
            cw = free.remove(free.size() - 1);
        } else if (used.size() < maxPool) {
            cw = createConnectionWrapper();
        } else {
            throw new RuntimeException("Unable to create a connection");
        }

        used.add(cw);

        return cw;
    }

    
    
    protected PooledConnection createConnectionWrapper() throws SQLException {
      Connection connection = null;
      PooledConnection pcon = null;

      try {
          connection = DriverManager.getConnection(url, user, pass);

        // Add caching wrapper to connection.
        pcon = new PooledConnection(this, connection);
        log.info("Created a new connection");

        // Check for warnings.
        SQLWarning warn = connection.getWarnings();

        while (warn != null) {
          log.info("Warning - {}", warn.getMessage());
          warn = warn.getNextWarning();
        }
      } catch (SQLException ex) {
        log.error("Can't create a new connection for {}", url, ex);
        // Clean up open connection.
        try {if (connection != null) connection.close();} catch (SQLException ex2) {log.warn("Unable to close connection", ex2);}
        // Rethrow exception.
        throw ex;
      }

      return pcon;
    }

    protected void freeConnectionWrapper(PooledConnection connection) {
        used.remove(connection);
        free.add(connection);
    }
}
