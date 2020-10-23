package page;

import dao.IDAO;
import model.User;
import service.CourseSM;
import service.UserService;
import service.CourseService;
import service.ClassUserService;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentPage extends Page {
    private User user;

    public StudentPage() {

    }

    public StudentPage(User user) {
        this.user = user;
    }

    public void showPage() {
        Scanner scanner = new Scanner(System.in);
        int sel = 0;

        do {
            System.out.println(String.format("\nHi %s Welcome to Student Page!", user.displayName));

            System.out.println("1. Add Course");
            System.out.println("2. Drop Course ");
            System.out.println("3. Check/Print Courses Registered");
            System.out.println("4. Check Vacancies Available ");
            System.out.println("5. Change Index Number of Course");
            System.out.println("6. Swap Index Number with Another Student");
            System.out.println("7. Exit");
            System.out.println("Please choose an option:  ");

            sel = scanner.nextInt();
            switch (sel) {
                case 1:
                    //Todo
                    break;
                case 2:
                    //Todo
                    break;
                case 3:
                    //Todo
                    break;
                case 4:
                    //Todo
                    break;
                //Change Index Number of Course
                case 5:
                    changeCourseIndex();
                    break;
                case 6:
                    //Todo
                    break;
                case 7:
                    System.out.println("Program end....");
                    break;

                default:
                    System.out.println("Opps. Invalid input. Please try again!");

            }


        } while (sel != 7);
    }


    private void changeCourseIndex() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);

        //print course list
        for (CourseSM course : courses) {
            System.out.println("Course list: ");
            System.out.println(String.format("Name: {0} Code: {1}", course.courseName, course.courseCode));
        }

    }
}