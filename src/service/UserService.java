/**
 User service provide complex data manipulation methods.

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */

package service;

import dao.ClassUserDAO;
import dao.UserDAO;
import model.ClassUser;
import model.User;

import java.util.ArrayList;

public class UserService {

    /**
     * Method to add  a class
     *
     * @param user
     * @return return user
     */
    public User saveUser(User user){
        UserDAO userDAO = new UserDAO();
        userDAO.add(user);
        return user;
    }

    /**
     * Method to get all student in the class
     *
     * @param classId
     * @return return list of users
     */
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

    /**
     * Method to validate user name duplcation
     *
     * @param userName
     * @return isValid
     */
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

    /**
     * Method to validate matric number duplcation
     *
     * @param matricNumber
     * @return isValid
     */
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
