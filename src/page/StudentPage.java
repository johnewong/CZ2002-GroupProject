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
import utility.StatusEnum;
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
    private Scanner scanner = new Scanner(System.in);


    public StudentPage() {

    }

    public StudentPage(User user) {
        this.user = user;
    }

    public void showPage() {
        int sel = 0;

        do {
            System.out.println(String.format("\nHi %s Welcome to Student Page!", user.displayName));

            System.out.println("1. Add Course");
            System.out.println("2. Drop Course ");
            System.out.println("3. Check/Print Courses Registered");
            System.out.println("4. Check Vacancies Available ");
            System.out.println("5. Change Index Number of Registered Courses");
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
        ArrayList<CourseSM> unregisteredCourses = service.getUnregisteredCourses(this.user);
        System.out.println("Unregistered Courses: ");

        printCourseList(unregisteredCourses);
        CourseSM selectedCourse = selectCourse(unregisteredCourses);

        printClassList(selectedCourse.classes, selectedCourse.registeredClass);
        ClassSM selectedClass = selectClass(selectedCourse, true, false);


        ClassSM returnClass = new ClassService().registerClass(user, selectedClass);
        System.out.println(String.format("You have successfully change a class %s", returnClass.indexNumber));
    }

    private void dropCourse() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        System.out.println("Registered Courses: ");

        printCourseList(courses);
        CourseSM selectedCourse = selectCourse(courses);

        printClassList(selectedCourse.classes, selectedCourse.registeredClass);
        ClassSM selectedClass = selectClass(selectedCourse, false, true);

        ClassSM returnClass = new ClassService().dropClass(user, selectedClass);
        System.out.println(String.format("You have successfully dropped to class %s", returnClass.indexNumber));
    }


    private void changeCourseIndex() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        System.out.println("Registered Courses: ");

        printCourseList(courses);
        CourseSM selectedCourse = selectCourse(courses);

        printClassList(selectedCourse.classes, selectedCourse.registeredClass);
        ClassSM selectedClass = selectClass(selectedCourse, false, false);

        ClassSM returnClass = new ClassService().changeClass(user, selectedCourse.registeredClass, selectedClass);
        System.out.println(String.format("You have successfully changed to class %s", returnClass.indexNumber));
    }

    private ClassSM selectClass(CourseSM selectedCourse, boolean isWaitlistAllow, boolean isDelete) {
        ClassSM selectedClass = null;
        while (selectedClass == null) {
            System.out.println("Please key in the index number");
            String inputIndex = scanner.next();
            if (!isDelete && selectedCourse.registeredClass != null && inputIndex.equals(selectedCourse.registeredClass.indexNumber)) {
                System.out.println("You cannot register an already registered class");
                continue;
            }

            for (ClassSM classSM : selectedCourse.classes) {
                if (classSM.indexNumber.equals(inputIndex)) {
                    if (classSM.totalVacancy == classSM.vacancyTaken && !isWaitlistAllow) {
                        System.out.println("This class is full");
                        break;
                    }
                    selectedClass = classSM;
                }
            }
        }

        return selectedClass;
    }

    private CourseSM selectCourse(ArrayList<CourseSM> courses) {
        CourseSM selectedCourse = null;
        while (selectedCourse == null) {
            System.out.println("Please key in the course code");
            String inputCourseCode = scanner.next();

            for (CourseSM course : courses) {
                if (course.courseCode.equals(inputCourseCode)) {
                    selectedCourse = course;
                }
            }
            if (selectedCourse != null)
                break;

            System.out.println("Course not found. Please key in again");
        }

        return selectedCourse;
    }

    private void printCourseList(ArrayList<CourseSM> courses) {
        //print course list
        for (CourseSM course : courses) {
            System.out.println(String.format("Code: %s  Name: %s  AU: %d  Type: %s", course.courseCode, course.courseName, course.au, CourseType.getValue(course.courseType)));
        }
    }

    private void printClassList(ArrayList<ClassSM> classes, ClassSM registeredClass) {
        for (ClassSM classSM : classes) {
            String output = "Index Number:%s  Available Vacancy:%d";
            if (classSM.totalVacancy == classSM.vacancyTaken) {
                output += " [FULL]";
            }

            if (registeredClass != null && registeredClass.classId == classSM.classId) {
                output += " [REGISTERED]";
            }

            System.out.println(String.format(output
                    , classSM.indexNumber, classSM.totalVacancy - classSM.vacancyTaken));
        }
    }

    private void checkVancancy() {
        // get data
        ClassDAO classDAO = new ClassDAO();
        ArrayList<Class> classes = classDAO.getAllValid();

        // display and user input
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

    private void printCoursesRegistered() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> registCourses = service.getRegisteredCourses(this.user);
        ArrayList<CourseSM> waitCourses = service.getWaitlistCourses(this.user);
        printCoursesInfo(registCourses, true);
        printCoursesInfo(waitCourses, false);
    }

    private void printCoursesInfo(ArrayList<CourseSM> courses, boolean isRegistered) {
        if (isRegistered) {
            System.out.println("-----------------");
            System.out.println("Registered Course:");
            System.out.println("-----------------");
        } else {
            System.out.println("--------------------");
            System.out.println("In Waitlist Course:");
            System.out.println("--------------------");
        }
        System.out.println("Course Code" + "  " + "AU" + "  " + "Index Number" + "  " + "Course Type");
        System.out.println("-------------------------------------------");
        for (CourseSM course : courses) {

            System.out.println(course.courseCode + "       " + course.au + "      " + course.classes.get(0).indexNumber + "        " + CourseType.getValue(course.courseType));
            System.out.println("-------------------------------------------");


        }
    }

}

