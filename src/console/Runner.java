package console;

/** Task 4, variant 12
 * @author Tarasov Alexandr 
 * 
 * Class Runner with main() method
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
                    } else {
                        System.out.println("Неверное имя пользователя или пароль!");
                        logger.error("Incorrect login or password!");
                    }
                    break;
                }
                case "2": { // User registration
                    UserService service = ServiceLocator.getService(UserService.class);
                    User user = new User();
                    user.setLogin(new RegisterLoginMenu().getAnswer());
                    user.setPassword(new RegisterPasswordMenu().getAnswer());
                    user.setRole(Role.getById(1)); // New User Role="READER";
                    user.setFullName(new RegisterFullNameMenu().getAnswer());

                    String temp;
                    do {
                        temp = new RegisterZipCodeMenu().getAnswer();
                    } while (!temp.matches("^\\d{6}$"));

                    user.setZipCode(Integer.parseInt((temp)));
                    temp = new RegisterAddressMenu().getAnswer();
                    user.setAddress(temp);
                    service.save(user);

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
                    System.exit(0);
                    //return;
                }
                }
                break;
            }
            case "SUBSCRIBER": { // Subscriber logged in - go to SUBSCRIBER MENU
                SubscriberCommands.INSTANCE.run();
                break;
            }
            case "ADMINISTRATOR": { // Administrator logged in - go to ADMINISTRATOR MENU
                AdminCommands.INSTANCE.run();
                break;
            }
            }
        }
    }
}

/*
 * private static void display(ResultSet myRs) throws SQLException {
 * System.out.println("----------------------------");
 * System.out.println("login\t password\trole");
 * System.out.println("----------------------------");
 * 
 * while (myRs.next()) { String login = myRs.getString("login"); String password
 * = myRs.getString("password"); Integer role = myRs.getInt("role");
 * System.out.printf("%s \t %s \t\t %d\n", login, password, role); }
 * 
 * System.out.println("----------------------------"); } }
 */