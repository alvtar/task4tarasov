package console.menu;

import java.util.ArrayList;

public class GetSubsYearMenu extends MenuGeneratorImpl {

    @Override
    public String getAnswer() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("");
        lst.add("МЕНЮ ВВОДА ГОДА");
        lst.add("Введите 4 цифры года (текущего или следующего):");
        lst.add("> ");
        return generate(lst);
    }
}