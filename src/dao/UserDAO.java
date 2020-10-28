package dao;

import model.User;
import utility.DataUtil;
import java.util.ArrayList;


public class UserDAO implements IDAO<User> {
    private ArrayList<User> allUsers;
    private ArrayList<User> allValidUsers;
    
    //constructor
    public UserDAO() {
        this.allUsers = getAll();
        this.allValidUsers = getAllValid();
    }

    @Override
    public ArrayList<User> getAll() {
        String dataString = DataUtil.loadFile("dataFiles/user.txt");
        String[] rows = dataString.split(";");
        ArrayList<User> userList = new ArrayList<>();

        for (int i = 1; i < rows.length; i++) {
            User user = new User();
            DataUtil.setObject(user, rows[0], rows[i]);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public ArrayList<User> getAllValid() {
    	ArrayList<User> userList=new ArrayList<>();
    			for (User user : allUsers) {
    				if (!user.isDeleted)
    					userList.add(user);
    			}
        return userList;
    }
    public ArrayList<User> getByUserId (int userId){
    	ArrayList<User> userList = new ArrayList<>();
    	for (User user : allValidUsers) {
    		if(!user.isDeleted)
    			userList.add(user);
    	}
    return userList;
    }

    @Override
    public User get(int userId) {
    	for (User user : this.allValidUsers)
    	{
    		if (user.userId == userId) {
    			return user;
    		}		
    	}
        return null;
    }
    
    @Override
    public void add(User newUser) {
    	try {
    		ArrayList<User> users = this.allUsers;
    		//validation
    		for (User everyone: users) {
    			if (everyone.userId == newUser.userId && everyone.userId == newUser.userId & !everyone.isDeleted) {
    				throw new Exception ("This user is already existed. ");
    			}
    		}
    		users.sort((a,b)-> a.userId - b.userId);
    		newUser.userId = users.get(users.size() - 1).userId +1;
    		users.add(newUser);
    		DataUtil.writeFile(users, "dataFile/user.txt");
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
 }

    public void update(User item) {
        User existUser = this.get(item.userId);
        if (existUser != null) {
        	existUser.userName = item.userName;
        	existUser.displayName = item.displayName;
        	existUser.password = item.password;
        	existUser.matricNumber = item.matricNumber;
        	existUser.nationality = item.nationality;
        	existUser.school = item.school;
        }
        else {
        	System.out.println("User ID not found.");
        }
    }

    @Override
    public void delete(int userId) {
    	User existUser = this.get(userId);
    	if (existUser != null) {
    		existUser.isDeleted = true;
    	}
    	else {
    		System.out.println("User ID not found.");
    	}

    }

}