/**
 StudentPage for student to add/drop course and check/change their registered classes

 @author Weng Yifei, Zhan Yijia, Huang Xiao Yan
 @version 1.0
 @since Nov-2020
 */

package page;

import model.User;
import service.*;
import utility.DataUtil;
import utility.CourseType;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentPage extends Page {
    private User user;
    private Scanner scanner = new Scanner(System.in);


    public StudentPage() {

    }

    /**
     * Constructor to initialize the login user from login page
     *
     * @param user The user object from login page
     */
    public StudentPage(User user) {
        this.user = user;
    }

    public void showPage() {
        int sel = 0;

        do {
            System.out.println(String.format("\nHi %s Welcome to MySTARS", user.displayName));
            System.out.println("---------------------------------------------------");
            System.out.println("|                                                 |");
            System.out.println("|              Course Registration                |");
            System.out.println("|                                                 |");
            System.out.println("---------------------------------------------------");
            System.out.println("                      Menu          ");
            System.out.println("---------------------------------------------------");
            System.out.println("| 1. Add Course                                   |");
            System.out.println("| 2. Drop Course                                  |");
            System.out.println("| 3. Check/Print Courses Registered               |");
            System.out.println("| 4. Check Vacancies Available                    |");
            System.out.println("| 5. Change Index Number of Registered Courses    |");
            System.out.println("| 6. Swap Index Number with Another Student       |");
            System.out.println("| 7. Exit                                         |");
            System.out.println("---------------------------------------------------");
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
                    checkVacancy();
                    break;
                case 5:
                    changeCourseIndex();
                    break;
                case 6:
                    swapIndex();
                    break;
                case 7:
                    System.out.println("Program end....");
                    break;

                default:
                    System.out.println("Opps. Invalid input. Please try again!");

            }


        } while (sel != 7);
    }

    /**
     * Method to register a course
     * Contains  1. Unregistered courses display  2. Select course  3. Register course
     */
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

    /**
     * Method to drop a course
     * Contains  1. Registered courses display  2. Select course  3. drop course
     */
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

    /**
     * Method to print all registered courses
     * Contains  1. Registered courses display  2. In wailtlist courses display
     */
    private void printCoursesRegistered() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> registCourses = service.getRegisteredCourses(this.user);
        ArrayList<CourseSM> waitCourses = service.getWaitlistCourses(this.user);
        printCoursesInfo(registCourses, true);
        printCoursesInfo(waitCourses, false);
    }

    /**
     * Method to let student change the class index of his registered courses
     * Contains  1. Registered courses display  2. Select course  3. Classes of selected course display  4. Change class
     */
    private void changeCourseIndex() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        System.out.println("Registered Courses: ");

        printCourseList(courses);
        CourseSM selectedCourse = selectCourse(courses);

        printClassList(selectedCourse.classes, selectedCourse.registeredClass);
        ClassSM selectedClass = selectClass(selectedCourse, false, false);

        ClassSM returnClass = new ClassService().changeClass(user, selectedCourse.registeredClass, selectedClass, false);
        System.out.println(String.format("You have successfully changed to class %s", returnClass.indexNumber));
    }

    /**
     * Method to let student swap the class with another student
     * Contains  1. Registered courses display  2. Select course  3. Classes of selected course display  4. Select class
     * 5. Students in the class display  6. Key in student martic number  7. Key in password  8. Swap
     */
    private void swapIndex() {
        CourseService service = new CourseService();
        ArrayList<CourseSM> courses = service.getRegisteredCourses(this.user);
        System.out.println("Registered Courses: ");

        printCourseList(courses);
        CourseSM selectedCourse = selectCourse(courses);

        System.out.println("Classes: ");
        printClassList(selectedCourse.classes, selectedCourse.registeredClass);
        System.out.println("Please select the class you want to swap to");
        ClassSM selectedClass = selectClass(selectedCourse, true, false);

        System.out.println("Students in this class: ");
        printClassStudents(selectedClass);
        System.out.println("Please key in the student MatricNumber you want to swap with");
        String matricNumber = scanner.next();

        User selectedStudent = null;
        while (selectedStudent == null) {
            for (User student : selectedClass.users) {
                if (student.matricNumber.equals(matricNumber)) {
                    selectedStudent = student;
                    break;
                }
            }
            if(selectedStudent != null)
                break;
            System.out.println("Student not found. Please key in again");
        }

        System.out.println("Please key in the password");
        int count = 0;
        while (count < 3) {
            String password = scanner.next();
            if (DataUtil.encryptPassword(password).equals(selectedStudent.password)) {
                ClassService classService = new ClassService();
                ClassSM newClass = classService.changeClass(this.user, selectedCourse.registeredClass, selectedClass, true);
                ClassSM oldClass = classService.changeClass(selectedStudent, selectedClass, selectedCourse.registeredClass, true);
                System.out.println(String.format("You have successfully swap from class %s to class %s", oldClass.indexNumber, newClass.indexNumber));
                break;
            }

            count++;
            System.out.println(String.format("Invalid password. Please try again. (%d/3)", count));
        }
    }

    private ClassSM selectClass(CourseSM selectedCourse, boolean isWaitlistAllow, boolean isDrop) {
        ClassSM selectedClass = null;
        while (selectedClass == null) {
            System.out.println("Please key in the index number");
            String inputIndex = scanner.next();
            if (!isDrop && selectedCourse.registeredClass != null && inputIndex.equals(selectedCourse.registeredClass.indexNumber)) {
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

    private void printClassStudents(ClassSM classSM) {
        System.out.println("-----------------------------------");
        System.out.printf("%s %29s %n","Name", "Matric Number");
        System.out.println("-----------------------------------");
        for (User user : classSM.users) {
            //System.out.println(String.format("Name: %s  Matric Number: %s", user.displayName, user.matricNumber));
            System.out.printf("%-20s %s %n",user.displayName, user.matricNumber);
        }
    }

    private void printCoursesInfo(ArrayList<CourseSM> courses, boolean isRegistered) {
        if (isRegistered) {
            System.out.println("Registered Course:");
            System.out.println("---------------------------------------------------------------------------------------------");
        } else {
            System.out.println("In Waitlist Course:");
            System.out.println("---------------------------------------------------------------------------------------------");
        }
        System.out.printf("%s %15s %35s %10s %15s %n","Course Code","Course Name","Index Number", "AU", "Course Type");
        //System.out.println("Course Code" + "  " + "AU" + "  " + "Index Number" + "  " + "Course Type");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (CourseSM course : courses) {
            if(isRegistered)
                System.out.printf("%-15s %-35s %-10s %10s %15s %n",course.courseCode, course.courseName,
                    course.registeredClass.indexNumber,course.au, CourseType.getValue(course.courseType));
            else
                System.out.printf("%-15s %-35s %-10s %10s %15s %n",course.courseCode, course.courseName,
                        "null",course.au, CourseType.getValue(course.courseType));
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }

}

