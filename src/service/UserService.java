package service;

import dao.ClassUserDAO;
import dao.UserDAO;
import entity.ClassUser;
import entity.User;

import java.util.ArrayList;

public class UserService {

    public User saveUser(User user){
        UserDAO userDAO = new UserDAO();
        userDAO.add(user);
        return user;
    }

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

    public boolean validateUserName(String userName){
        UserDAO userDAO = new UserDAO();
        ArrayList<User> allUser = userDAO.getAllValid();

        for(User user : allUser){
            if(user.userName.equals(userName)){
                System.out.println("Username is already existed ");
                return false;
            }
        }

        return true;
    }

    public boolean validateMatricNumber(String matricNumber){
        UserDAO userDAO = new UserDAO();
        ArrayList<User> allUser = userDAO.getAllValid();

        for(User user : allUser){
            if(user.matricNumber.equals(matricNumber)){
                System.out.println("Matric number is already existed ");
                return false;
            }
        }

        return true;
    }
}
