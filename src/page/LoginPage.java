package page;

import dao.UserDAO;
import model.User;
import utility.DataUtil;
import utility.RoleType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LoginPage extends Page {
    @Override
    public void showPage() {

        System.out.println("-------------------------------------------");
        System.out.println("|                                          |");
        System.out.println("|           Welcome To MySTARS             |");
        System.out.println("|                                          |");
        System.out.println("-------------------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Username: ");
        String userName = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();

        //Date now = new Date();
        //System.out.println(now);

        ArrayList<User> users = new UserDAO().getAllValid();
        String encryptedPassword= DataUtil.encryptPassword(password);

        for (User user : users){
            //user.password.equals(encryptedPassword)

            if(user.userName.equals(userName) && user.password.equals(encryptedPassword)){


                if(user.role == RoleType.Student.toInt()){

                    //get Current Date
                    Date now = new Date();

                    //verify the access period
                    if(now.compareTo(user.periodStartTime)==1 && now.compareTo(user.periodEndTime) == -1){
                        new StudentPage(user).showPage();
                    }

                    else{
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String strPeriod = dateFormat.format(user.periodStartTime);
                        String endPeriod = dateFormat.format(user.periodEndTime);
                        System.out.println("You have no access to the page now");
                        System.out.println("You may access the page from " +  strPeriod +  " " + "to " + endPeriod);
                    }



                }

                else
                    new AdminPage(user).showPage();
            }

        }

//            if(count >0){
//                count+=1;
//                System.out.println("Invalid Username or Password. Please Enter Again");
//            }


    }


}
