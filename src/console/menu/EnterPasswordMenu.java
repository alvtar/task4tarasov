package console.menu;

import java.util.ArrayList;

public class EnterPasswordMenu extends MenuGeneratorImpl {

    public String getAnswer() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("МЕНЮ ВХОДА В СИСТЕМУ");
        lst.add("Введите пароль:");
        lst.add("> ");
        return generate(lst);
    }
}
