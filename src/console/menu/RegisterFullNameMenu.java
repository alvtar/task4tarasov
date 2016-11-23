package console.menu;

import java.util.ArrayList;

public class RegisterFullNameMenu extends MenuGeneratorImpl {

    @Override
    public String getAnswer() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("МЕНЮ РЕГИСТРАЦИИ НОВОГО ПОЛЬЗОВАТЕЛЯ");
        lst.add("Введите Ф.И.О. пользователя:");
        lst.add("> ");
        return generate(lst);
    }
}
