import dao.UserDAO;
import model.User;
import service.StudentService;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World! test");

        System.out.println("Hello World test 123!");


        ArrayList<User> allUsers = new UserDAO().getAllUsers();
        User user = new User();
        user = new UserDAO().getUser(123);


        StudentService svc = new StudentService();
        svc.AddCourse();
    }
}
