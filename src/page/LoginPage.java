package page;

import dao.UserDAO;
import model.User;
import utility.DataUtil;
import utility.RoleType;
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

        ArrayList<User> users = new UserDAO().getAllValid();
        //String encryptedPassword= DataUtil.encryptPassword(password);

        for (User user : users){
            //user.password.equals(encryptedPassword)
            if(user.userName.equals(userName) && user.password.equals(password)){
                if(user.role == RoleType.Student.toInt()){
                    //get today's time
                    Date now = new Date(System.currentTimeMillis());

                    if(now.after(user.periodStartTime) && now.before(user.periodEndTime))
                    {
                        new StudentPage(user).showPage();
                    }
                    else
                        System.out.println("Your have no access to the page");

                }
                else
                    new AdminPage(user).showPage();
            }

        }

    }







}
