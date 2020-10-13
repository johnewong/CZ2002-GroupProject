import dao.ClassUserDAO;
import dao.UserDAO;
import model.ClassUser;
import model.User;
import service.StudentService;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<ClassUser> classUsers = new ClassUserDAO().getAllClassUsers();
        ClassUser classUser = new ClassUserDAO().getClassUsers(classUsers,2,3);

        System.out.println(String.format("classUserId: %d\n", classUser.classUserId));
        System.out.println(String.format("classId: %d\n", classUser.classId));
        System.out.println(String.format("userId: %d\n", classUser.userId));
        System.out.println(String.format("isDeleted: %b\n", classUser.isDeleted));
        System.out.println(String.format("status: %d\n", classUser.status));
    }
}
