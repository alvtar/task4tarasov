package console.menu;


import java.util.ArrayList;
import java.util.Scanner;


public abstract class MenuGeneratorImpl implements MenuGenerator {

    @Override
    public String generate(ArrayList<String> choice) {
        
        for (String ch : choice) {
            System.out.println(ch);
        }

        @SuppressWarnings("resource")
        Scanner in=new Scanner(System.in);
        if (in.hasNext()) { 
            String temp=in.nextLine();
            //System.out.println(temp);
               // return in.next();
            return temp;
        } else return "";
    }
    
    
    @Override
    public abstract String getAnswer(); 
    
}
