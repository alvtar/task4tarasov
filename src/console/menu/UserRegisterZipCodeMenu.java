package console.menu;

import java.util.ArrayList;

public class UserRegisterZipCodeMenu  extends MenuGeneratorImpl {
    
    @Override
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ РЕГИСТРАЦИИ НОВОГО ПОЛЬЗОВАТЕЛЯ");
        lst.add("Введите 6 цифр почтового индекса:");
        lst.add("> ");
        
        return generate(lst);
    }
}
