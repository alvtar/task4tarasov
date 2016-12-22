package console.command;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import console.menu.*;
import domain.Publication;
import domain.Subscription;
import exception.PersistentException;
import service.PublicationService;
import service.ServiceLocator;
import service.SubscriptionService;


public class SubscriberCommands extends Command {

    public static final SubscriberCommands INSTANCE = new SubscriberCommands();

    public void run() throws PersistentException {
        MenuGenerator userMenu = new SubscriberMenu();
        Calendar calendar = new GregorianCalendar();
        String temp = "";
        int tempYear = 0;
        int realYear = 0;
        int realMonth = 0;
        int month1 = 0;
        int month2 = 0;
        boolean isDatesCorrect = false;

        switch (userMenu.getAnswer()) {
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

        case "4": { // List of subscriber subscriptions
            SubscriptionService service = ServiceLocator.getService(SubscriptionService.class);
            List<Subscription> subscriptions = service.findByUserId(getCurrentUserId());
            if (subscriptions != null)
                System.out.println(subscriptions.toString());
            else
                System.out.println("Подписки не найдены!");
            break;
        }

        case "5": { // Subscribe
            SubscriptionService subscriptionService = ServiceLocator.getService(SubscriptionService.class);
            PublicationService publicationService = ServiceLocator.getService(PublicationService.class);
            Subscription subscription = new Subscription();

            do {
                temp = new GetIssnMenu().getAnswer();
            } while (!temp.matches("^\\d{3,5}$"));

            Publication publication = publicationService.findByIssn((Integer.parseInt(temp)));
            if (publication != null)
                System.out.println(publication.toString());
            else {
                System.out.println("/n Издание не найдено!");
                break;
            }
            subscription.setUserId(getCurrentUserId());
            subscription.setPublicationId(publication.getId());
            
            do {
                do {
                    temp = new GetSubsYearMenu().getAnswer();
                } while (!temp.matches("^\\d{4}$"));

                tempYear = Integer.parseInt(temp);
                realYear = calendar.get(Calendar.YEAR);
                realMonth = calendar.get(Calendar.MONTH);

                isDatesCorrect = ((tempYear == realYear & realMonth < 12) | tempYear == ++realYear);

                if (!isDatesCorrect)
                    System.out.println("/n Введен некорректный год подписки!");
            } while (!isDatesCorrect);
            subscription.setSubsYear(tempYear);

            do {
                do {
                    temp = new GetSubsMonthsMenu().getAnswer();
                } while (!temp.matches("[0-9]?[0-9]\\s[0-9]?[0-9]"));

                String[] split = temp.split("\\s");
                month1 = Integer.parseInt(split[0]);
                month2 = Integer.parseInt(split[1]);

                realYear = calendar.get(Calendar.YEAR);
                realMonth = calendar.get(Calendar.MONTH);

                isDatesCorrect = ((month1 >= 1 & month2 <= 12 & month2 >= month1)
                        & ((month1 > realMonth & tempYear == realYear) | (tempYear == ++realYear)));

                if (!isDatesCorrect)
                    System.out.println("/n Введены некорректные месяцы подписки на " + tempYear + " год!");
            } while (!isDatesCorrect);

            int tempMonths = 0;
            for (int m = month1; m <= month2; m++) {
                tempMonths = (int) (tempMonths + Math.pow(2, m));
            }
            subscription.setSubsMonths(tempMonths);

            float tempCost = (month2 - month1 + 1) * publication.getMonthCost();

            System.out.println("СТОИМОСТЬ ПОДПИСКИ СОСТАВЛЯЕТ: " + tempCost + " рублей.");
            subscription.setPaymentSum(tempCost);

            String confirm = new GetConfirmMenu().getAnswer();

            if (confirm.contains("y") | confirm.contains("Y") | confirm.contains("д") | confirm.contains("Д")) {
                subscriptionService.save(subscription);
                System.out.println("Подписка сохранена!");
                System.out.println(subscriptionService.findById(subscription.getId()));
            } else {
                System.out.println("Подписка отменена!");
            }
            break;
        }

        case "6": { // Exit
            setCurrentRole("");
            return;
        }
        }
    }
}
