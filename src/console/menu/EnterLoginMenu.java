package console.menu;

import java.util.ArrayList;


public class EnterLoginMenu extends MenuGeneratorImpl{

    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ ВХОДА В СИСТЕМУ");
        lst.add("Введите логин:");
        lst.add("> ");
        
        return generate(lst);
    }
 
}
