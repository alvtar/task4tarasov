package console.menu;

import java.util.ArrayList;

public class UserRegisterPasswordMenu extends MenuGeneratorImpl{

    @Override
    public String getAnswer() {
        ArrayList<String> lst=new ArrayList<>();
        
        lst.add("");
        lst.add("МЕНЮ РЕГИСТРАЦИИ НОВОГО ПОЛЬЗОВАТЕЛЯ");
        lst.add("Введите пароль нового пользователя");
        lst.add(">: \n");
        
        return generate(lst);
    }

}
