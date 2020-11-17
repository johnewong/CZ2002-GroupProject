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


        ArrayList<User> users = new UserDAO().getAllValid();
        String encryptedPassword= DataUtil.encryptPassword(password);

        for (User user : users){
            //user.password.equals(encryptedPassword)
            if(user.userName.equals(userName) && user.password.equals(encryptedPassword)){
                if(user.role == RoleType.Student.toInt()){
                    //get today's time
                    //Date now = new Date(System.currentTimeMillis());

                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //System.out.println(formatter.format(now));
                    //String currentDate = formatter.format(now);
                    Date date=new Date();
                    try {
						date = sdf.parse(user.periodStartTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                    Date date2=new Date();
                    try {
						date2 = sdf.parse(user.periodEndTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                    if(now.after(date) && now.before(date2))
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
