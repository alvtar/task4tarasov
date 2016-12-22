package console.command;

import java.util.List;
import console.menu.*;
import domain.Publication;
import domain.Subscription;
import exception.PersistentException;
import service.PublicationService;
import service.ServiceLocator;
import service.SubscriptionService;


public class AdminCommands extends Command {

    public static final AdminCommands INSTANCE = new AdminCommands();

    
    public void run() throws PersistentException {
        MenuGenerator adminMenu = new AdminMenu();
        String temp = "";

        switch (adminMenu.getAnswer()) {
        case "1": { // Find publication by index
            PublicationService service = ServiceLocator.getService(PublicationService.class);

            do {
                temp = new GetIssnMenu().getAnswer();
            } while (!temp.matches("^\\d{3,5}$"));

            Publication publication = service.findByIssn(Integer.parseInt(temp));
            if (publication != null)
                System.out.println(publication.toString());
            else
                System.out.println("Издание не найдено!");
            break;
        }

        case "2": { // Find publications by title
            PublicationService service = ServiceLocator.getService(PublicationService.class);
            List<Publication> publications = service.findByTitleLike("%" + new GetTitleMenu().getAnswer() + "%");
            if (publications != null)
                System.out.println(publications.toString());
            else
                System.out.println("Издания не найдены!");
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
            if (subscriptions != null)
                System.out.println(subscriptions.toString());
            else
                System.out.println("Подписки не найдены!");
            break;
        }

        case "5": { // New publication
            PublicationService publicationService = ServiceLocator.getService(PublicationService.class);
            Publication publication = new Publication();

            do {
                temp = new GetIssnMenu().getAnswer();
            } while (!temp.matches("^\\d{3,5}$"));

            if (publicationService.findByIssn((Integer.parseInt(temp))) != null) {
                System.out.println("Издание c таким индексом уже существует!");
                System.out.println(publication.toString());
                break;
            }
            publication.setIssn(Integer.parseInt(temp));
            publication.setTitle(new GetTitleMenu().getAnswer());

            do {
                temp = new GetMonthCostMenu().getAnswer();
            } while (!temp.matches("[0-9]?[0-9]\\.[0-9]?[0-9]"));

            publication.setMonthCost(Float.parseFloat(temp));
            publication.setActive(true); /// TODO --- active

            publicationService.save(publication);
            System.out.println("Издание сохранено!");
            System.out.println(publicationService.findById(publication.getId()));
            break;
        }

        case "6": { // Exit
            setCurrentRole("");
            return;
        }

        }
    }
}
