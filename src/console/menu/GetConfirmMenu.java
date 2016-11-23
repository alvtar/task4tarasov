package console.menu;

import java.util.ArrayList;

public class GetConfirmMenu extends MenuGeneratorImpl {

    @Override
    public String getAnswer() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("МЕНЮ ПОДТВЕРЖДЕНИЯ ПОДПИСКИ");
        lst.add("Введите букву 'д' или 'y' для подтверждения, ");
        lst.add("любой другой символ для отказа от подписки: ");
        lst.add("> ");
        return generate(lst);
    }
}
