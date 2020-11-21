package service;

import dao.ClassUserDAO;
import dao.UserDAO;
import model.ClassUser;
import model.User;

import java.util.ArrayList;

public class UserService {

    public ArrayList<User> getClassMatesById(int classId) {
        ClassUserDAO classUserDAO = new ClassUserDAO();
        UserDAO userDAO = new UserDAO();

        ArrayList<ClassUser> classUsers = classUserDAO.getByClassId(classId);
        ArrayList<User> users = new ArrayList<>();
        for (ClassUser cu : classUsers){
            users.add(userDAO.get(cu.userId));
        }

        return users;
    }

}
