package console;

/** Task 4, variant 12
 * @author Tarasov Alexandr 
 * 
 * Class Runner with main() method
 * 
 * Settings of logging level and pool parameters are in the console.AppInit class
 * 
 * ADMIN login,pass=admin,admin
 * USER  login,pass=user1,111
 * 
 * */


import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import exception.PersistentException;
import console.command.SubscriberCommands;
import console.menu.*;
import console.command.*;
import dao.pool.ConnectionPool;
import domain.*;
import service.*;

public class Runner {

    private static Logger logger = Logger.getLogger(Runner.class);

    // Application initialization
    public static void init() throws PersistentException, SQLException {
        new AppInit().init();         // Connection pool initialization
        new ServiceRegistratorImpl(); // Service registration
    }
    
    // Main method to start console application
    public static void main(String[] args) throws PersistentException, SQLException {
        init();
        MenuGenerator menu = new MainMenu();
        String temp="";

        // Endless cycle for console menu
        while (true) {
            // Switching depending on the role of logged user
            switch (AppState.getCurrentRole()) {
            case "": { // User or admin not logged yet - MAIN MENU
                
                // MAIN MENU selection
                switch (menu.getAnswer()) {
                case "1": { // Login
                    UserService service = ServiceLocator.getService(UserService.class);
                    String login = new EnterLoginMenu().getAnswer();
                    String password = new EnterPasswordMenu().getAnswer();
                    User user = service.findByLoginAndPassword(login, password);

                    if (user != null) {
                        System.out.println("ПОЛЬЗОВАТЕЛЬ: " + user.getFullName());                      
                        AppState.setCurrentUserId(user.getId());
                        AppState.setCurrentRole(user.getRole().name());
                        logger.info("User with login '"+user.getFullName()+"' entered!");
                    } else {
                        System.out.println("Неверное имя пользователя или пароль!");
                        logger.info("Incorrect login or password!");
                    }
                    break;
                }
                case "2": { // User registration
                    UserService service = ServiceLocator.getService(UserService.class);
                    User user = new User();
                    
                    temp=new RegisterLoginMenu().getAnswer();
                    if (!service.isUniqueLogin(temp)) {
                        System.out.println("Пользователь с таким логином уже существует!");
                        logger.info("User with login '"+temp+"' already exist!");
                        break;
                    }
                    user.setLogin(temp);
                    
                    user.setPassword(new RegisterPasswordMenu().getAnswer());
                    user.setRole(Role.getById(1)); // New User Role="SUBSCRIBER";
                    user.setFullName(new RegisterFullNameMenu().getAnswer());

                    do {
                        temp = new RegisterZipCodeMenu().getAnswer();
                    } while (!temp.matches("^\\d{6}$"));

                    user.setZipCode(Integer.parseInt((temp)));
                    temp = new RegisterAddressMenu().getAnswer();
                    user.setAddress(temp);
                    service.save(user);
                    
                    System.out.println("Пользователь зарегистрирован!");
                    logger.info("User with login '"+user.getLogin()+"' registered!");
                    System.out.println(user);
                    
                    AppState.setCurrentUserId(user.getId());
                    AppState.setCurrentRole("SUBSCRIBER");
                    break;
                }
                case "3": { // List of all publications
                    PublicationService service = ServiceLocator.getService(PublicationService.class);
                    List<Publication> publications = service.findAll();
                    System.out.println(publications.toString());
                    break;
                }
                case "4": { // Exit from application
                    ConnectionPool.getInstance().destroy();
                    System.out.println("Приложение завершило работу!");
                    logger.info("Exit from application!");
                    System.exit(0);
                    //return;
                }
                }
                break;
            }
            case "SUBSCRIBER": { // Subscriber logged in - go to SUBSCRIBER MENU
                logger.info("Enter to SUBSCRIBER Menu!");
                SubscriberCommands.INSTANCE.run();
                break;
            }
            case "ADMINISTRATOR": { // Administrator logged in - go to ADMINISTRATOR MENU
                logger.info("Enter to ADMINISTRATOR Menu!");
                AdminCommands.INSTANCE.run();
                break;
            }
            }
        }
    }
}
