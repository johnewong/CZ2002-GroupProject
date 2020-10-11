package dao;

import model.User;
import utility.DataUtility;
import java.util.ArrayList;


public class UserDAO {

    public ArrayList<User> getAllUsers(){
        String dataString = DataUtility.loadData("user.txt");


        return null;
    }
}
