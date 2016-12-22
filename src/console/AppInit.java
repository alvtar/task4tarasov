package console;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import dao.pool.ConnectionPool;
import exception.PersistentException;

public class AppInit {
    private static Logger logger = Logger.getLogger(AppInit.class);
    public static final String LOG_FILE_NAME = "log.txt";
    public static final Level LOG_LEVEL = Level.ALL;
    public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";
    
    public static final String DB_PROPERTIES_FILE = "db.properties";
    public static final Properties DB_PROPERTIES = new Properties();
    
    public static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static final int DB_POOL_START_SIZE = 10;
    public static final int DB_POOL_MAX_SIZE = 1000;
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;
    
    
    
    public void init() {
        try {
                Logger root = Logger.getRootLogger();
                Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
                root.addAppender(new FileAppender(layout, LOG_FILE_NAME, true));
                root.addAppender(new ConsoleAppender(layout));
                root.setLevel(LOG_LEVEL);
                
                //db_properties = new Properties();
                DB_PROPERTIES.load(new FileInputStream(DB_PROPERTIES_FILE));
                DB_USER = DB_PROPERTIES.getProperty("user");
                DB_PASSWORD = DB_PROPERTIES.getProperty("password");
                DB_URL = DB_PROPERTIES.getProperty("url");
                
                
                ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL, DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE, DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch(PersistentException | IOException e) {
                logger.fatal("It is impossible to initialize application", e);
               
        }
}
}
