/**
 A parent class of AdminPage, LoginPage, StudentPage
 Contains all the shared methods and properties of those pages

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */

package page;

import dao.ClassDAO;
import model.Class;
import model.Session;
import service.ClassSM;
import service.CourseSM;
import utility.ClassType;
import utility.CourseType;
import utility.SchoolName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Page {
    protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    protected Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    protected abstract void showPage();


    /**
     * Shared method to print the course information of a course list
     *
     * @param courses
     */
    protected void printCourseList(ArrayList<CourseSM> courses) {
        //print course list
        System.out.printf("%s %15s %30s %15s %15s %n","Course Code","Course Name", "AU", "Course Type", "School");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (CourseSM course : courses) {
            System.out.printf("%-15s %-30s %10s %15s %15s %n",course.courseCode, course.courseName, course.au,
                    CourseType.getValue(course.courseType), SchoolName.getValue(course.school));
        }
        System.out.print('\n');
    }

    /**
     * Shared method to print the session information of a session list
     *
     * @param sessions
     */
    protected void printSessionList(ArrayList<Session> sessions) {
        for (Session session : sessions) {
            System.out.println(String.format("sessionId:%d  day:%d  time:%s  venue:%s  classType:%s"
                    , session.sessionId, session.day, session.time, session.venue, ClassType.getValue(session.classType)));
        }

    }

    /**
     * Shared method to print the class information of a class list
     *
     * @param classes The class list want to display
     * @param registeredClass The registered class
     */
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

    /**
     * Shared method to let user select a course from a course list
     *
     * @param courses
     */
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

    /**
     * Shared method to let user check the vacancy of a class
     *
     */
    protected void checkVacancy() {
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
