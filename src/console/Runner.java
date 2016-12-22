package console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.Logger;
import exception.PersistentException;
import console.command.SubscriberCommands;
import console.menu.*;
import console.command.*;
import dao.mysql.UserDaoImpl;
import dao.pool.ConnectionPool;
import domain.*;
import service.*;


public class Runner {

    private static Logger logger = Logger.getLogger(Runner.class);

    static Connection conn = null;
    static Statement st = null;
    static PreparedStatement prSt = null;
    static ResultSet res = null;

    
    
    public static void init() throws PersistentException, SQLException {
        new AppInit().init();
        new ServiceRegistratorImpl();
    }

    
    
    public static void main(String[] args) throws PersistentException, SQLException {

        
        init();
        
       // try {

            MenuGenerator menu =new MainMenu();
            
            while (true) {
                
                switch (AppState.getCurrentRole()) {
                case "": {            // Not logged yet

                    switch (menu.getAnswer()) {
                    case "1": { // Login
                        UserService service = ServiceLocator.getService(UserService.class); 
                    
                        String login=new EnterLoginMenu().getAnswer();
                        String password=new EnterPasswordMenu().getAnswer();
                    
                        User user=service.findByLoginAndPassword(login,password);
                    
                        if (user!=null) {
                            System.out.println(user.getFullName());
                            AppState.setCurrentUserId(user.getId());
                            //role=user.getRole().getName();
                            AppState.setCurrentRole(user.getRole().name());
                            ///System.out.println(role);
                        } else {
                            System.out.println("Неверное имя пользователя или пароль!");
                            logger.error("Incorrect login or password!");
                        }
                        break;
                    } 
                    case "2": { // User registration
                        UserService service = ServiceLocator.getService(UserService.class); 
                        
                        User user=new User();
                        
                        user.setLogin(new RegisterLoginMenu().getAnswer());
                        user.setPassword(new RegisterPasswordMenu().getAnswer());
                        user.setRole(Role.getById(1)); // New User Role="READER";
                        user.setFullName(new RegisterFullNameMenu().getAnswer());
                        
                        String temp;
                        do {
                            temp=new RegisterZipCodeMenu().getAnswer();  
                        }   
                        while (!temp.matches("^\\d{6}$"));
                        user.setZipCode(Integer.parseInt((temp)));

                        temp=new RegisterAddressMenu().getAnswer();
                        System.out.println(temp);  
                        
                        user.setAddress(temp);
                        service.save(user);
                        AppState.setCurrentUserId(user.getId()); //// +++
                        AppState.setCurrentRole("SUBSCRIBER");
                        
                        //System.out.println(userId);  

           /*             if (user!=null) {
                            System.out.println(user.getFullName());
                            userId=user.getId();
                            role=user.getRole().getName();
                        } else {
                            System.out.println("Неверное имя пользователя или пароль!");
                            logger.error("Incorrect login or password!");
                        }  */
                        break;
                    }
                    case "3": { // List of publications
                        PublicationService service = ServiceLocator.getService(PublicationService.class);
                        List<Publication> publications = service.findAll();
                        System.out.println(publications.toString());
                    
                        break;
                    }
                
                    case "4": { // Exit
                        ConnectionPool.getInstance().destroy();
                        System.out.println("Приложение завершило работу!");
                        System.exit(0);
                        return;
                    }
                
                    }
                    
                break;
                }
                
                case "SUBSCRIBER": {
                    SubscriberCommands.INSTANCE.run();
                    ///AppState.setCurrentRole("");
                    break;
                }
                
                case "ADMINISTRATOR": {
                    //System.out.println("ВОШЕЛ АДМИНИСТРАТОР!");
                    
                    ///AppState.setCurrentRole("");
                break;
                }      
                    
                    
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
   //     }} catch (Exception exc) {
   //             exc.printStackTrace();
  //      } finally {
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
     // /  
            
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