package console.menu;

import java.util.ArrayList;

public class GetSubsMonthsMenu  extends MenuGeneratorImpl {
    
    @Override
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ ВВОДА МЕСЯЦЕВ ДЛЯ ПОДПИСКИ");
        lst.add("Введите через пробел начальный и конечный месяцы подписки):");
        lst.add("(Начиная со следующего за текущим месяца):");
        lst.add("> ");
        
        return generate(lst);
    }
}