package console;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import exception.PersistentException;
import console.menu.*;
import domain.*;
import service.*;

public class Runner {
    
    static Connection conn=null;
    static Statement st = null;
    static PreparedStatement prSt = null;
    static ResultSet res = null;
    
    
    public static void init() throws PersistentException, SQLException  {

        new AppInit().init();
        new ServiceRegistratorImpl();
        
        /*
        ConnectionPool pool=new ConnectionPool();
        pool.init();   
        ServiceLocator locator = new ServiceLocator();
    
        UserService user=new UserServiceImpl();
        locator.registerService(UserService.class,user);
        
        PublicationService publication=new PublicationServiceImpl();
        locator.registerService(PublicationService.class, publication);
        
        SubscriptionService subscription=new SubscriptionServiceImpl();
        locator.registerService(SubscriptionService.class, subscription);
        
        ServiceLocator.setLocator(locator);*/
        
    }
    
    
    
    
    public static void main(String[] args) throws PersistentException, SQLException {
        int role;
        
        init();
        
        try {
   /*             // 0. Read the properties
                Properties prop = new Properties();
                prop.load(new FileInputStream("db.properties"));
                
                String db_user = prop.getProperty("user");
                String db_pw = prop.getProperty("password");
                String db_url = prop.getProperty("url");

                System.out.println("Connecting to database...");
                System.out.println("Database URL: " + db_url);
                System.out.println("User: " + db_user);

                // 1. Get a connection to database
                conn = DriverManager.getConnection(db_url,prop);
                //conn = DriverManager.getConnection(db_url, db_user, db_password);

                
    */
              
            
            //User user=new UserServiceImpl().findById(1);

            //UserService service = ServiceLocator.getService(UserService.class);
            //List<User> users = service.findAll();
            
            MenuGenerator menu =new MainMenu();
            
            while (true) {

                switch (menu.getAnswer()) {
                case "1": {
                   // System.out.println("Clients="+ new CountSumClients().countSum(tarList.getTariffs()));
                    UserService service = ServiceLocator.getService(UserService.class);
                    //MenuGenerator = new 
                    
           //??????????  login=0       
                    String login=new UserLoginMenu().getAnswer();
                    String password=new UserPasswordMenu().getAnswer();

                    
                    User user=service.findByLoginAndPassword(login,password);
   
                    System.out.println(user.getFullName());
                    
                    break;
                }
                case "2": {
                    //output.showList(new SortByFee().getSorted(tarList.getTariffs()));
                    break;
                }

                case "3": {

               ///     ServiceLocator.getService(UserService.class).findAll();
                    PublicationService service = ServiceLocator.getService(PublicationService.class);
                    List<Publication> publications = service.findAll();
                    System.out.println(publications.toString());
      
                    break;
                }
                
                case "4": return;
                }
            
            
            
            
            //System.out.println(user);
            
            
            //user.setId(null);
            //user.setLogin("TEST");
            
            
            
            //new UserServiceImpl().save(user);
            
            //System.out.println(users);
                
  /*              // 2. Statement
                st = conn.createStatement();

                // 3. Execute SQL query
                res = st.executeQuery("select `login`, `password`, `role` from users");

                // 5. Display the result set
                System.out.println("the statement. Output the list of all users:");
                display(res);

                // 2-1. Prepare statement
                prSt = conn.prepareStatement("select `login`, `password`, `role` from users where role = ? ");

                // 3-1. Set the parameters
                prSt.setInt(1, 0);

                // 4-1. Execute SQL query
                res = prSt.executeQuery();

                // 5-1. Display the result set
                System.out.println("the prepared statement:  role = 0 (admin).\nList of users with the role = 'admin':");
                display(res);

                //
                // Reuse the prepared statement: role = 1 (customer)
                //

                // 3-2. Set the parameters
                prSt.setInt(1, 1);

                // 4-2. Execute SQL query
                res = prSt.executeQuery();

                // 5-2. Display the result set
                System.out.println("\nReuse the prepared statement:  role = 1 (customer).\nList of users with the role = 'customer':");
                display(res);
*/
        }} catch (Exception exc) {
                exc.printStackTrace();
        } finally {
                if (res != null) {
                        res.close();
                }

                if (st != null) {
                        st.close();
                }

                if (prSt != null) {
                        prSt.close();
                }

                if (conn != null) {
                        conn.close();
                }
        }
}

private static void display(ResultSet myRs) throws SQLException {
        System.out.println("----------------------------");
        System.out.println("login\t password\trole");
        System.out.println("----------------------------");

        while (myRs.next()) {
                String login = myRs.getString("login");
                String password = myRs.getString("password");
                Integer role = myRs.getInt("role");
                System.out.printf("%s \t %s \t\t %d\n", login, password, role);
        }

        System.out.println("----------------------------");
    }
}