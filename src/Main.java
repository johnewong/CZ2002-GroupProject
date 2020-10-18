import dao.ClassUserDAO;
import dao.UserDAO;
import model.ClassUser;
import model.User;
import service.StudentService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ClassUserDAO classUserDAO = new ClassUserDAO();
        ArrayList<ClassUser> allClassUsers = classUserDAO.getAll();
        ClassUser classUser = classUserDAO.get(2, 1);

        ClassUser newClassUser = new ClassUser(2, 7, 0);
        classUserDAO.add(newClassUser);





    }
}
