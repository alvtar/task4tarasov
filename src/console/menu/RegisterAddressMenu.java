package console.menu;

import java.util.ArrayList;

public class RegisterAddressMenu  extends MenuGeneratorImpl {
    
    @Override
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ РЕГИСТРАЦИИ НОВОГО ПОЛЬЗОВАТЕЛЯ");
        lst.add("Введите адрес нового пользователя:");
        lst.add("> ");
        
        return generate(lst);
    }
}
