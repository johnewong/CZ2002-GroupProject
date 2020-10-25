package page;

import dao.UserDAO;
import model.User;
import utility.DataUtil;
import utility.RoleType;

import java.util.ArrayList;

public class LoginPage extends Page {
    @Override
    public void showPage() {
        // user input username password
        String username="";
        String password="";

        ArrayList<User> users = new UserDAO().getAllValid();
        String encryptedPassword= DataUtil.encryptPassword(password);

        for (User user : users){
            if(user.password.equals(encryptedPassword)){
                if(user.role == RoleType.Student.toInt()){
                    new StudentPage(user).showPage();
                }
                else
                    new AdminPage(user).showPage();
            }

        }

    }
}
