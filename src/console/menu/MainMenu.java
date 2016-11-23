package console.menu;

import java.util.ArrayList;

public class MainMenu extends MenuGeneratorImpl {

    public String getAnswer() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("ГЛАВНОЕ МЕНЮ");
        lst.add("Сделайте свой выбор:");
        lst.add("1 - Вход в систему;");
        lst.add("2 - Регистрация пользователя;");
        lst.add("3 - Вывод полного списка изданий;");
        lst.add("4 - Выход.");
        lst.add("> ");
        return generate(lst);
    }
}
