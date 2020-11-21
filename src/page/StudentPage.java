package page;

import dao.ClassDAO;
import dao.CourseDAO;
import dao.IDAO;
import model.Class;
import model.Course;
import model.User;
import service.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
                    addCourse();
                    break;
                case 2:
                    //Todo
                    break;
                case 3:
                    //Todo 123123
                    break;
                case 4:
                    checkVancancy();
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

    private void addCourse() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);

        printCourseList(courses);
        System.out.println("Enter the course code: ");

        String courseCode = scanner.next();
        ArrayList<ClassSM> selectedClasses = new ArrayList<>();
        for (CourseSM course : courses) {
            if (courseCode == course.courseCode) {
                selectedClasses = course.classes;
            }

        }
        if (selectedClasses.size() == 0) {
            printClassList(selectedClasses);
        } else {
            System.out.println("selectedClasses Not Found");
        }

        System.out.println("Enter the course section ID: ");
        //String Id = in.readLine();

        try {
            String filename = "user.txt";
            FileWriter fw = new FileWriter(filename, true);
            fw.write(("add a line"));
            fw.close();
        } catch (IOException e) {
            System.err.println("Selected Coures:" + e.getMessage());
        }


    }

    private void changeCourseIndex() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        printCourseList(courses);
    }

    private void printCourseList(ArrayList<CourseSM> courses) {
        //print course list
        for (CourseSM course : courses) {
            System.out.println("Course list: ");
            System.out.println(String.format("Name: %s Code: %s", course.courseName, course.courseCode));
        }
    }

    private void printClassList(ArrayList<ClassSM> classes) {
        //print course list
        for (ClassSM classSM : classes) {
            System.out.println("Course list: ");
            System.out.println(String.format("Name: %d Code: {1} Index:{2} Vacancy:{3} TotalVacancy{4} WaitList{5}"
                    , classSM.classId, classSM.courseId, classSM.indexNumber, classSM.vacancyTaken, classSM.totalVacancy, classSM.numberInWaitlist));
        }
    }

    private void checkVancancy() {
        // get data
        ClassDAO classDAO = new ClassDAO();
        ArrayList<Class> classes = classDAO.getAllValid();

        // display and user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter index number: ");
        String indexNumber = scanner.next();

        for (Class cls : classes) {
            if (cls.indexNumber.equals(indexNumber)) {
                // print class info
                System.out.println(String.format("Current available vacancy: %d", cls.totalVacancy - cls.vacancyTaken));
                return;
            }
        }

        System.out.println("Index number not found");
    }
}

