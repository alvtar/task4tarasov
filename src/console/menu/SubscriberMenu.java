package console.menu;

import java.util.ArrayList;

public class SubscriberMenu extends MenuGeneratorImpl {
    public String getAnswer() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("МЕНЮ ПОДПИСЧИКА");
        lst.add("Сделайте свой выбор:");
        lst.add("1 - Поиск издания по индексу;");
        lst.add("2 - Поиск издания по наименованию;");
        lst.add("3 - Вывод полного списка изданий;");
        lst.add("4 - Вывод истории подписок;");
        lst.add("5 - Новая подписка;");
        lst.add("6 - Выход.");
        lst.add("> ");
        return generate(lst);
    }
}
