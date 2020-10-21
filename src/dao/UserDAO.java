package dao;

import model.ClassUser;
import model.User;
import utility.DataUtil;

import java.util.ArrayList;

public class UserDAO implements IDAO<User> {
    private ArrayList<User> _allUsers;
    public UserDAO() {
        this._allUsers = getAllUsers();
    }

    public User getUser(int id) {
        String dataString = DataUtil.loadFile("dataFiles/users.txt");
        User users = new User();
        users.displayName = "";
        users.password = "";

        return users;
    }



    public ArrayList<User> getAllUsers() {
        String dataString = DataUtil.loadFile("dataFiles/users.txt");
        String[] rows = dataString.split(";");
        ArrayList<User> userList = new ArrayList<User>();

        for (int i = 1; i < rows.length; i++) {
            User user = new User();
            DataUtil.setObject(user, rows[0], rows[i]);
            userList.add(user);
        }
        return userList;

    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public ArrayList<User> getAllValid() {
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public void add(User item) {

    }

    public void update(User user) {
        User updateUser = _allUsers.get(user.userId);
        updateUser.password = user.password;


        DataUtil.writeFile(_allUsers, "dataFiles/users.txt");
    }

    @Override
    public void delete(int id) {

    }

    public User updateName(int userId, String name) {
        String dataString = DataUtil.loadFile("dataFiles/users.txt");
        String[] rows = dataString.split(";");
        User users
                = new User();
        DataUtil.setObject(users, rows[0], rows[1]);
        return users;
    }
}
