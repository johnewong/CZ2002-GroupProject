package dao;

import model.User;
import utility.DataUtil;
import java.util.ArrayList;


public class UserDAO {

    //1 create get update delete
    //2 get all data...

    public User getUser(int id){
        String dataString = DataUtil.loadFile(filePath:"dataFiles/users.txt");
        User users = new User();
        users.displayName = "";
        users.password = "";

        return users;
    }

    public ArrayList<User> getAllUsers(){
        String dataString = DataUtil.loadFile("dataFiles/users.txt");
        String[] rows = dataString.split( ";");
        ArrayList<User> userList = new ArrayList<User>();
        
        for (int i=1;i<rows.length;i++) {
        	User user = new User();
        	DataUtil.setObject(user, rows[0], rows[i]);
        	userList.add(user);  	
        }
        return userList;

    }

    public void updateName(int userId, String name){
    	String dataString = DataUtil.loadile("dataFiles/users.txt");
    	String[] rows = dataString.split(";");
    	User users = new User();
    	DataUtil.setObject(users, rows[0], rows[1]);
    	return users;
    }
}
