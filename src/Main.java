import dao.ClassUserDAO;
import dao.UserDAO;
import model.ClassUser;
import model.User;
import service.StudentService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ClassUserDAO classUserDAO = new ClassUserDAO();
        ArrayList<ClassUser> allClassUsers = classUserDAO.getAllClassUsers();
        ClassUser classUser = classUserDAO.getClassUser(allClassUsers, 2, 3);

        ClassUser newClassUser = new ClassUser(2,6,0);
        classUserDAO.addClassUser(allClassUsers, newClassUser);


//        System.out.println(String.format("classUserId: %d\n", classUser.classUserId));
//        System.out.println(String.format("classId: %d\n", classUser.classId));
//        System.out.println(String.format("userId: %d\n", classUser.userId));
//        System.out.println(String.format("isDeleted: %b\n", classUser.isDeleted));
//        System.out.println(String.format("status: %d\n", classUser.status));
    }
}
