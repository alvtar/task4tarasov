package console.command;

import java.util.List;
import org.apache.log4j.Logger;
import console.menu.*;
import domain.Publication;
import domain.Subscription;
import domain.User;
import exception.PersistentException;
import service.PublicationService;
import service.ServiceLocator;
import service.SubscriptionService;
import service.UserService;


public class AdminCommands extends Command {

    public static final AdminCommands INSTANCE = new AdminCommands();
    private static Logger logger = Logger.getLogger(AdminCommands.class);

    public void run() throws PersistentException {
        MenuGenerator adminMenu = new AdminMenu();
        String temp = "";

        switch (adminMenu.getAnswer()) {
        case "1": { // Find publication by ISSN (index)
            PublicationService service = ServiceLocator.getService(PublicationService.class);

            do {
                temp = new GetIssnMenu().getAnswer();
            } while (!temp.matches("^\\d{3,8}$"));

            Publication publication = service.findByIssn(Integer.parseInt(temp));
            if (publication != null) {
                System.out.println(publication.toString());
            } else { 
                System.out.println("Издание не найдено!");
                logger.info("Publication with ISSN '" + temp + "' not found!");
            }
            break;
        }

        case "2": { // Find publications by title
            PublicationService service = ServiceLocator.getService(PublicationService.class);
            List<Publication> publications = service.findByTitleLike("%" + new GetTitleMenu().getAnswer() + "%");
            if (publications != null) {
                System.out.println(publications.toString());
                
            } else {
                System.out.println("Издания не найдены!");
                logger.info("Publications not found!");
            }
            break;
        }

        case "3": { // List of all publications
            PublicationService service = ServiceLocator.getService(PublicationService.class);
            List<Publication> publications = service.findAll();
            System.out.println(publications.toString());
            break;
        }

        case "4": { // List of all subscriptions
            SubscriptionService service = ServiceLocator.getService(SubscriptionService.class);
            List<Subscription> subscriptions = service.findAll();
            if (subscriptions != null) {
                System.out.println(subscriptions.toString());
            } else {
                System.out.println("Подписки не найдены!");
                logger.info("Subscriptions not found!");
            }
            break;
        }

        case "5": { // List of all users
            UserService service = ServiceLocator.getService(UserService.class);
            List<User> users = service.findAll();
            System.out.println(users.toString());
            break;
        }
        
        case "6": { // New publication
            PublicationService publicationService = ServiceLocator.getService(PublicationService.class);
            Publication publication = new Publication();

            do {
                temp = new GetIssnMenu().getAnswer();
            } while (!temp.matches("^\\d{3,8}$"));

            if (publicationService.findByIssn((Integer.parseInt(temp))) != null) {
                System.out.println("Издание c таким индексом уже существует!");
                logger.info("Publication with ISSN '" + temp + "' already exist!");
                System.out.println(publicationService.findByIssn((Integer.parseInt(temp))));
                break;
            }
            publication.setIssn(Integer.parseInt(temp));
            publication.setTitle(new GetTitleMenu().getAnswer());

            do {
                temp = new GetMonthCostMenu().getAnswer();
            } while (!temp.matches("[0-9]?[0-9]?[0-9]\\.[0-9]?[0-9]"));

            publication.setMonthCost(Float.parseFloat(temp));
            publication.setActive(true); /// TODO --- active

            publicationService.save(publication);
            System.out.println("Издание сохранено!");
            logger.info("Publication with ISSN '" + publication.getIssn() + "' saved!");
            System.out.println(publicationService.findById(publication.getId()));
            break;
        }

        case "7": { // Exit
            setCurrentRole("");
            logger.info("Exit from ADMINISTRATOR MENU to MAIN MENU!");
            return;
        }
        }
    }
}
