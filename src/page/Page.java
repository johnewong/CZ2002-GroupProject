package page;

import dao.ClassDAO;
import model.Class;
import model.User;
import service.ClassSM;
import service.CourseSM;
import utility.CourseType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Page {
    protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    protected Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    //this.scanner.useDelimiter("\n");

    protected abstract void showPage();

    protected void printCourseList(ArrayList<CourseSM> courses) {
        //print course list
        for (CourseSM course : courses) {
            System.out.println(String.format("Code: %s  Name: %s  AU: %d  Type: %s", course.courseCode, course.courseName, course.au, CourseType.getValue(course.courseType)));
        }
    }

    protected void printClassList(ArrayList<ClassSM> classes, ClassSM registeredClass) {
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

    public CourseSM selectCourse(ArrayList<CourseSM> courses) {
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

    protected void checkVancancy() {
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
}
