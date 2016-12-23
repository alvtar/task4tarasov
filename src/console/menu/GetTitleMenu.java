package console.menu;

import java.util.ArrayList;

public class GetTitleMenu  extends MenuGeneratorImpl {
    
    @Override
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>(); 
        lst.add("");
        lst.add("МЕНЮ ПОИСКА ИЛИ ВВОДА ТИПА И НАЗВАНИЯ ИЗДАНИЯ");
        lst.add("Введите тип издания, название издания или его часть:");
        lst.add("> "); 
        return generate(lst);
    }
}