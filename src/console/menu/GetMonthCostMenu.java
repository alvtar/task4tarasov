package console.menu;

import java.util.ArrayList;

public class GetMonthCostMenu  extends MenuGeneratorImpl {
    
    @Override
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ ВВОДА СТОИМОСТИ ПОДПИСКИ ДЛЯ ИЗДАНИЯ");
        lst.add("Введите стоимость подписки на издание за месяц:");
        lst.add("> ");
        
        return generate(lst);
    }
}