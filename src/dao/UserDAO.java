package dao;

import model.User;
import utility.DataUtility;
import java.util.ArrayList;


public class UserDAO {

    //1 create get update delete
    //2 get all data...

    public User getUser(int id){
        String dataString = DataUtility.loadData("user.txt");
        User user = new User();
        user.displayName = "";
        user.password = "";


        return user;
    }

    public ArrayList<User> getAllUsers(){
        String dataString = DataUtility.loadData("user.txt");



        ArrayList<User> userList = new ArrayList<User>();
        userList.add(new User());

        return userList;
    }

    public void updateName(int userId, String name){

    }
}
