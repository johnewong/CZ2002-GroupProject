/**
Login page for user login
Contains 1. start/end period comparison  2.username, password verification  3. determine the login user to access which page by his role type

 @author Huang XiaoYan
 @version 1.0
 @since Nov-2020
 */


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

        int count=0;
        boolean isValid = false;
        while (!isValid && count<3) {
            count++;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Username: ");
            String userName = scanner.next();
            System.out.println("Password: ");
            String password = scanner.next();

            ArrayList<User> users = new UserDAO().getAllValid();
            String encryptedPassword = DataUtil.encryptPassword(password);

            for (User user : users) {
                if (user.userName.equals(userName) && user.password.equals(encryptedPassword)) {
                    isValid = true;
                    if (user.role == RoleType.Student.toInt()) {

                        //get Current Date
                        Date now = new Date();

                        //verify the access period
                        if (now.compareTo(user.periodStartTime) == 1 && now.compareTo(user.periodEndTime) == -1) {
                            new StudentPage(user).showPage();
                        } else if (now.compareTo(user.periodStartTime) == -1 && now.compareTo(user.periodEndTime) == -1) {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String strPeriod = dateFormat.format(user.periodStartTime);
                            String endPeriod = dateFormat.format(user.periodEndTime);
                            System.out.println("You have no access to the page now");
                            System.out.println("You may access the page from " + strPeriod + " " + "to " + endPeriod);
                        } else {
                            System.out.println("Your access period is end. pLease be on time next times");
                        }
                    } else
                        new AdminPage(user).showPage();
                }
            }

            if (!isValid){
                if(count<3)
                    System.out.println(String.format("Invalid username or password. Please try again. (%d/3)", count));
                else
                    System.out.println(String.format("Invalid username or password. You've entered the wrong password too many times. (%d/3)", count));
            }


        }
    }
}
