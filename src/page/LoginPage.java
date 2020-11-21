package page;

import dao.UserDAO;
import model.User;
import utility.DataUtil;
import utility.RoleType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LoginPage extends Page {
    @Override
    public void showPage() {
        // user input username password
        System.out.println("Username: ");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();

        Date now = new Date();
        System.out.println(now);


        ArrayList<User> users = new UserDAO().getAllValid();
        String encryptedPassword= DataUtil.encryptPassword(password);

        for (User user : users){
            //user.password.equals(encryptedPassword)

            //if( user.periodStartTime)

            if(user.userName.equals(userName) && user.password.equals(encryptedPassword)){

                if(user.role == RoleType.Student.toInt()){
                    System.out.println("Compare time:" +now.compareTo(user.periodStartTime));


                    new StudentPage(user).showPage();
//                    }
//                    else
//                        System.out.println("Your have no access to the page");

                }
                else
                    new AdminPage(user).showPage();
            }

        }

    }


}
