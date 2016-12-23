package console.menu;

import java.util.ArrayList;

public class AdminMenu  extends MenuGeneratorImpl {
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ АДМИНИСТРАТОРА");
        lst.add("Сделайте свой выбор:");
        lst.add("1 - Поиск издания по индексу;");
        lst.add("2 - Поиск издания по наименованию;");
        lst.add("3 - Вывод полного списка изданий;");
        lst.add("4 - Вывод полного списка подписок;");
        lst.add("5 - Вывод полного списка пользователей;");
        lst.add("6 - Новое издание;");
        lst.add("7 - Выход.");
        lst.add("> ");
        
        return generate(lst);
    }
}