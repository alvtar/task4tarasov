package console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.Logger;
import exception.PersistentException;
import console.menu.*;
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
        int userId=0;
        String role="";
        
        init();
        
       // try {

            MenuGenerator menu =new MainMenu();
            
            while (true) {
                
                switch (role) {
                case "": {            // Not logged yet

                    switch (menu.getAnswer()) {
                    case "1": { // Login
                        UserService service = ServiceLocator.getService(UserService.class); 
                    
                        String login=new EnterLoginMenu().getAnswer();
                        String password=new EnterPasswordMenu().getAnswer();
                    
                        User user=service.findByLoginAndPassword(login,password);
                    
                        if (user!=null) {
                            System.out.println(user.getFullName());
                            userId=user.getId();
                            //role=user.getRole().getName();
                            role=user.getRole().name();
                            System.out.println(role);
                        } else {
                            System.out.println("Неверное имя пользователя или пароль!");
                            logger.error("Incorrect login or password!");
                        }
                        break;
                    } 
                    case "2": { // User registration
                        UserService service = ServiceLocator.getService(UserService.class); 
                        
                        User user=new User();
                        
                        user.setLogin(new UserRegisterLoginMenu().getAnswer());
                        user.setPassword(new UserRegisterPasswordMenu().getAnswer());
                        user.setRole(Role.getById(1)); // New User Role="READER";
                        user.setFullName(new UserRegisterFullNameMenu().getAnswer());
                        
                        String temp;
                        do {
                            temp=new UserRegisterZipCodeMenu().getAnswer();  
                        }   
                        while (!temp.matches("^\\d{6}$"));
                        user.setZipCode(Integer.parseInt((temp)));

                        temp=new UserRegisterAddressMenu().getAnswer();
                        System.out.println(temp);  
                        
                        user.setAddress(temp);
                        service.save(user);
                        userId=user.getId(); //// +++
                        role="SUBSCRIBER";
                        
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
                        System.out.println("Всего доброго!");
                        System.exit(0);
                        return;
                    }
                
                    }
                    
                break;
                }
                
                case "SUBSCRIBER": {
                    //System.out.println("ВОШЕЛ ПОЛЬЗОВАТЕЛЬ!");
                    MenuGenerator userMenu =new UserMenu();
                    
                    switch (userMenu.getAnswer()) {
                    
                    case "1": { // Find publication by index
                        PublicationService service = ServiceLocator.getService(PublicationService.class);
                        
                        String temp;
                        do {
                            temp=new UserMenuGetIndex().getAnswer();  
                        }   
                        while (!temp.matches("^\\d{3,5}$"));
                        
                        Publication publication = service.findByIssn(Integer.parseInt(temp));
                        if (publication!=null) System.out.println(publication.toString());
                        else System.out.println("/n Издание не найдено!");
                    
                        break;
                    }
                    
                    case "2": { // Find publications by title
                        PublicationService service = ServiceLocator.getService(PublicationService.class);
                        
                        List<Publication> publications = service.findByTitleLike("%"+new UserMenuGetTitle().getAnswer()+"%");
                        if (publications!=null) System.out.println(publications.toString());
                        else System.out.println("/n Издания не найдены!");
                    
                        break;
                    }
                    
                    case "3": { // List of publications
                        PublicationService service = ServiceLocator.getService(PublicationService.class);
                        List<Publication> publications = service.findAll();
                        System.out.println(publications.toString());
                    
                        break;
                    }
                    
                    case "4": { // List of subscriptions
                        SubscriptionService service = ServiceLocator.getService(SubscriptionService.class);
                        List<Subscription> subscriptions = service.findByUserId(userId);
                        
                        if (subscriptions!=null) System.out.println(subscriptions.toString());
                        else System.out.println("/n Подписки не найдены!");
                        break;
                    }
                    
                    case "5": { // Subscribe
                        PublicationService service = ServiceLocator.getService(PublicationService.class);
                        
                        Subscription subscription=new Subscription();
                        
                        String temp;
                        do {
                            temp=new UserMenuGetIndex().getAnswer();  
                        }   
                        while (!temp.matches("^\\d{3,5}$"));
                        
                        Publication publication = service.findByIssn(Integer.parseInt(temp));
                        if (publication!=null) System.out.println(publication.toString());
                        else {
                            System.out.println("/n Издание не найдено!");
                            break;
                        }
                        
                        subscription.setUserId(userId);   
                        subscription.setPublicationId(publication.getId());
                        
                        subscription.setSubsYear(new UserRegisterPasswordMenu().getAnswer());
                        subscription.setRole(Role.getById(1)); // New User Role="READER";
                        subscription.setFullName(new UserRegisterFullNameMenu().getAnswer());
                        
                        service.save(user);
                        
                        
                        String temp;
                        do {
                            temp=new UserMenuGetIndex().getAnswer();  
                        }   
                        while (!temp.matches("^\\d{3,5}$"));
                        
                        Publication publication = service.save(publication);
                        if (publication!=null) System.out.println(publication.toString());
                        else System.out.println("/n Издание не найдено!");
                    
                        break;
                    }
                    
                    case "6": { // Exit
                        role="";
                        break;
                    }
                    
                    
                    }
                    
                break;
                } 
                
                case "ADMINISTRATOR": {
                    System.out.println("ВОШЕЛ АДМИНИСТРАТОР!");
                    
                    role="";
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