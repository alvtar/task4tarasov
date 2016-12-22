package console;

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
    

    public static void init() throws PersistentException, SQLException {
        new AppInit().init();
        new ServiceRegistratorImpl();
    }
    
    
    public static void main(String[] args) throws PersistentException, SQLException {
        init();
        MenuGenerator menu = new MainMenu();

        while (true) {
            switch (AppState.getCurrentRole()) {
            case "": { // Not logged yet

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
            case "SUBSCRIBER": { // Subscriber logged in
                SubscriberCommands.INSTANCE.run();
                break;
            }
            case "ADMINISTRATOR": { // Administrator logged in
                AdminCommands.INSTANCE.run();
                break;
            }
            }
        }
    }
}

 /*   private static void display(ResultSet myRs) throws SQLException {
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
}*/