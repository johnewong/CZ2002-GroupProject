package dao;

import model.User;
import utility.DataUtil;
import java.util.ArrayList;


public class UserDAO {

    //1 create get update delete
    //2 get all data...

    public User getUser(int id){
        String dataString = DataUtil.loadFile("user.txt");
        User user = new User();
        user.displayName = "";
        user.password = "";


        return user;
    }

    public ArrayList<User> getAllUsers(){
        String dataString = DataUtil.loadFile("user.txt");



        ArrayList<User> userList = new ArrayList<User>();
        userList.add(new User());

        return userList;
    }

    public void updateName(int userId, String name){

    }
}
