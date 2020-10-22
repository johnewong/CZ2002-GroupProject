package service;

import dao.ClassUserDAO;
import dao.UserDAO;
import model.ClassUser;
import model.User;
import utility.StatusEnum;

import java.util.ArrayList;

public class ClassService {

    public ArrayList<User> getClassMates(int classId) {
        ClassUserDAO classUserDAO = new ClassUserDAO();
        UserDAO userDAO = new UserDAO();

        ArrayList<ClassUser> classUsers = classUserDAO.getClassMates(classId);
        ArrayList<User> users = new ArrayList<>();
        for (ClassUser cu : classUsers){
            users.add(userDAO.get(cu.userId));
        }

        return users;
    }

}
