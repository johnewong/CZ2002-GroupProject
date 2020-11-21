package page;

import dao.ClassDAO;
import dao.ClassUserDAO;
import dao.CourseDAO;
import dao.IDAO;
import model.Class;
import model.ClassUser;
import model.Course;
import model.User;
import service.*;
import utility.CourseType;
import utility.StatusEnum;

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
                    dropCourse();
                    break;
                case 3:
                    printCoursesRegistered();
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
        // available courses/  unregistered courses
        ArrayList<CourseSM> unregisteredCourses = service.getRegisteredCourses(this.user);
        Scanner scanner = new Scanner(System.in);

        printCourseList(unregisteredCourses);
        System.out.println("Enter the course code: ");

        String courseCode = scanner.next();
        CourseSM selectedCourse = null;
        ArrayList<ClassSM> selectedClasses = new ArrayList<>();
        for (CourseSM course : unregisteredCourses) {
            if (courseCode == course.courseCode) {
                selectedCourse = course;
                selectedClasses = course.classes;
            }

        }

        // there are available classes to register
        //
        if (selectedClasses.size() > 0) {
            printClassList(selectedClasses);
            Class vancancyTanken = new Class();

            ClassDAO classDAO = new ClassDAO();
            vancancyTanken.vacancyTaken++;
            classDAO.update(vancancyTanken);
            ClassUser classUser = new ClassUser(this.user.userId, vancancyTanken.classId, StatusEnum.REGISTERED.toInt());
            ClassUserDAO classUserDAO = new ClassUserDAO();
            classUserDAO.add(classUser);

        }

        if (selectedClasses.size() == 0) {
            printClassList(selectedClasses);
            //Todo let user input class index number
            System.out.println("Typy in index number");
            String index = scanner.next();
            //ArrayList<ClassSM> indexNumber = new ArrayList<>();
            for (CourseSM course : unregisteredCourses) {
                if (index == course.courseCode) {
                    selectedCourse = course;
                    selectedClasses = course.classes;
                }

            }
            System.out.println("Add to WaitList ");
            Class selectedClass = new Class();

            // +1 in class.numberInWaitlist
            ClassDAO classDAO = new ClassDAO();
            selectedClass.numberInWaitlist++;
            classDAO.update(selectedClass);

            // add classUser status -> inWaitlist
            ClassUser classUser = new ClassUser(this.user.userId, selectedClass.classId, StatusEnum.INWAITLIST.toInt());
            ClassUserDAO classUserDAO = new ClassUserDAO();
            classUserDAO.add(classUser);


        } else {
            System.out.println("selectedClasses Not Found");
        }


        //String Id = in.readLine();

        //try {
        // String filename = "user.txt";
        //FileWriter fw = new FileWriter(filename, true);
        //fw.write(("Enter the course section ID"));
        //fw.close();
        //} catch (IOException e) {
        //  System.err.println("Selected Coures:" + e.getMessage());
        //}
    }

    private void dropCourse() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        if (courses != null) {
            // IDAO dao = new ClassDAO();
            //dao.delete();

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
                System.out.println(cls.totalVacancy - cls.vacancyTaken);
                break;
            }
        }

        System.out.println("Index number not found");
    }

    private void printCoursesRegistered() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> registCourses = service.getRegisteredCourses(this.user);
        ArrayList<CourseSM> waitCourses = service.getWaitlistCourses(this.user);
        printCoursesInfo(registCourses, true);
        printCoursesInfo(waitCourses, false);
    }

    private void printCoursesInfo(ArrayList<CourseSM> courses, boolean isRegistered) {
        if (isRegistered) {
            System.out.println("Register Course:");
            System.out.println("-----------------");
        } else {
            System.out.println("In Waitlist Course:");
            System.out.println("--------------------");
        }
        System.out.println("Course Code" + "  " + "AU" + "  " + "Course Type" + "  " + "Index Number");
        System.out.println("-------------------------------------------");
        for (CourseSM course : courses) {

            System.out.println(course.courseCode + "       " + course.au + "    " + CourseType.getValue(course.courseType) + "         " + course.classes.get(0).indexNumber);
            System.out.println("-------------------------------------------");

//            if (isRegistered)
//                System.out.println("Register");
//            else
//                System.out.println("In Waitlist");

        }
    }

}

