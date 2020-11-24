package page;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import dao.ClassDAO;
import dao.CourseDAO;
import dao.IDAO;
import dao.UserDAO;
import model.Class;
import model.Course;
import model.User;
import service.ClassSM;
import service.CourseSM;
import service.CourseService;
import service.UserService;
import utility.CourseType;
import utility.DataUtil;
import utility.RoleType;
import utility.SchoolName;

public class AdminPage extends Page {
    private User user;

    public AdminPage(User user) {
        this.user = user;

    }

    public AdminPage() {

    }

    public void showPage() {
        int sel = 0;
        do {
            System.out.println(String.format("\nHi %s", user.displayName));
            System.out.println("-------------------------------------------");
            System.out.println("|                                          |");
            System.out.println("|           Welcome To Admin Page          |");
            System.out.println("|                                          |");
            System.out.println("-------------------------------------------");
            System.out.println("                    Menu          ");
            System.out.println(" -------------------------------------------");
            System.out.println("| 1. Edit Student Access Period             |");
            System.out.println("| 2. Add Student Information                |");
            System.out.println("| 3. Add Courses                            |");
            System.out.println("| 4. Update Courses                         |");
            System.out.println("| 5. Check Course Availability Slots        |");
            System.out.println("| 6. Print Student List By Index Number     |");
            System.out.println("| 7. Print Student List By Course           |");
            System.out.println("| 8. Exit                                   |");
            System.out.println(" -------------------------------------------");
            System.out.println("Please choose an option:  ");

            sel = scanner.nextInt();
            switch (sel) {
                case 1:
                    changeAccessPeriod();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    addCourse();
                    break;
                case 4:
                    updateCourse();
                    break;
                case 5:
                    checkVancancy();
                    break;
                case 6:
                    printStudentListByIndex();
                    break;
                case 7:
                    printStudentListByCourse();
                    break;
                case 8:
                    exitAdminPage();
                    break;
                default:
                    System.out.println("Invalid input. Please try again!");
            }

        } while (sel != 8);
    }

    private void changeAccessPeriod() {

        // get all student info
        UserDAO userDAO = new UserDAO();
        ArrayList<User> students = userDAO.getAllValidStudents();

        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%s %20s %27s %27s %27s %n","User Name","Student Name","Matric Number", "Start Period", "End Period");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        for (User student : students) {
            System.out.printf("%-17s %-20s %15s %30s %28s %n",student.userName, student.displayName, student.matricNumber,
                    DATE_FORMATTER.format(student.periodStartTime), DATE_FORMATTER.format(student.periodEndTime));
            System.out.print('\n');


        }
        // let admin choose student to edit
        User selectedUser = null;

        while (selectedUser == null) {
            System.out.println("Please input student User Name   (-1 to exit)");
            String username = scanner.next();
            for (User student : students) {
                if (student.userName.equals(username)) {
                    selectedUser = student;
                }
            }

            if (username.equals("-1"))
                new AdminPage(this.user).showPage();
        }

        Date startDate = new Date();
        Date endDate = new Date();
        Boolean isValid = false;
        while (!isValid) {
            System.out.println(String.format("Current user start period: %s.  Please input start period (format: yyyy-MM-dd)"
                    , DATE_FORMATTER.format(selectedUser.periodStartTime)));
            String inputStartPeriod = scanner.next();

            System.out.println(String.format("Current user end period: %s.  Please input end period (format: yyyy-MM-dd)"
                    , DATE_FORMATTER.format(selectedUser.periodEndTime)));
            String inputEndPeriod = scanner.next();

            try {
                startDate = DATE_FORMATTER.parse(inputStartPeriod);
                endDate = DATE_FORMATTER.parse(inputEndPeriod);
                if (startDate.compareTo(endDate) > 0) {
                    throw new Exception("Error: Start date cannot be later than end date");
                }
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error: Invalid date input. Please input again!");
                isValid = false;
            }
        }

        selectedUser.periodStartTime = startDate;
        selectedUser.periodEndTime = endDate;

        // call StudentDAO.Update()
        userDAO.update(selectedUser);
    }

    private void addStudent() {
        UserService userService = new UserService();
        String userName = null;
        Boolean isValid = false;
        while (!isValid) {
            System.out.println("Enter user name ");
            userName = scanner.next();
            isValid = userService.validateUserName(userName);
        }

        System.out.println("Enter display name ");
        String displayName = scanner.next();

        String password = null;
        String confirmPassword = null;
        isValid = false;
        while ((password == null || confirmPassword == null) || !isValid) {
            System.out.println("Enter password ");
            password = scanner.next();

            System.out.println("Confirm your password ");
            confirmPassword = scanner.next();

            if (!password.equals(confirmPassword)) {
                System.out.println("Your confirm password is wrong. Please key in again");
            } else
                isValid = true;
        }
        String encrptedPassword = DataUtil.encryptPassword(password);

        String matricNumber = null;
        isValid = false;
        while (!isValid) {
            System.out.println("Enter matric number ");
            matricNumber = scanner.next();
            isValid = userService.validateMatricNumber(matricNumber);
        }

        System.out.println("Enter nationality ");
        String nationality = scanner.next();

        int school = enterSchool();

        int gender = 0;
        isValid = false;
        while (!isValid) {
            System.out.println("Enter gender (Male:0 Female:1)");
            gender = scanner.nextInt();
            if (gender == 0 || gender == 1)
                isValid = true;
            else
                System.out.println("Invalid input");
        }

//        int role = 0;
//        isValid = false;
//        while (!isValid) {
//            System.out.println("Enter role (Student:0 Admin:1)");
//            role = scanner.nextInt();
//            if (role == 0 || role == 1)
//                isValid = true;
//            else
//                System.out.println("Invalid input");
//        }

        Date startDate = new Date();
        Date endDate = new Date();
        isValid = false;
        while (!isValid) {
            System.out.println("Enter start Period (format: yyyy-MM-dd)");
            String inputStartPeriod = scanner.next();
            System.out.println("Enter end Period (format: yyyy-MM-dd)");
            String inputEndPeriod = scanner.next();

            try {
                startDate = DATE_FORMATTER.parse(inputStartPeriod);
                endDate = DATE_FORMATTER.parse(inputEndPeriod);
                if (startDate.compareTo(endDate) > 0) {
                    throw new Exception("Error: Start date cannot be later than end date");
                }
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error: Invalid date input. Please input again!");
                isValid = false;
            }
        }

        User newUser = new User();
        newUser.userName = userName;
        newUser.displayName = displayName;
        newUser.password = encrptedPassword;
        newUser.matricNumber = matricNumber;
        newUser.role = RoleType.Student.toInt();
        newUser.nationality = nationality;
        newUser.gender = gender;
        newUser.school = school;
        newUser.periodStartTime = startDate;
        newUser.periodEndTime = endDate;

        userService.saveUser(newUser);
        System.out.println(String.format("You have successfully save a new student %s", newUser.userName));
    }

    private void addCourse() {
        CourseService courseService = new CourseService();

        String courseCode = enterCourseCode();
        String courseName = enterCourseName();

        int school = enterSchool();
        int courseType = enterCourseType();

        System.out.println("Enter AU ");
        int au = scanner.nextInt();

        Course newCourse = new Course();
        newCourse.courseCode = courseCode;
        newCourse.courseName = courseName;
        newCourse.school = school;
        newCourse.courseType = courseType;
        newCourse.au = au;

        courseService.saveCourse(newCourse);
        System.out.println(String.format("You have successfully add a new course %s", newCourse.courseCode));
    }

    private void updateCourse() {
        CourseService courseService = new CourseService();
        ArrayList<CourseSM> allCourses = courseService.getAllCourses();
        printCourseList(allCourses);
        CourseSM selectedCourse = selectCourse(allCourses);

        int sel = 0;
        System.out.println("Select the field you want to update.");
        System.out.println("1. CourseCode");
        System.out.println("2. CourseName");
        System.out.println("3. School");
        System.out.println("4. CourseType");
        System.out.println("5. AU");
        System.out.println("6. Save update");
        do {
            System.out.println("Please select: ");
            sel = scanner.nextInt();
            switch (sel) {
                case 1:
                    selectedCourse.courseCode = enterCourseCode();
                    break;
                case 2:
                    selectedCourse.courseName = enterCourseName();
                    break;
                case 3:
                    selectedCourse.school = enterSchool();
                    break;
                case 4:
                    selectedCourse.courseType = enterCourseType();
                    break;
                case 5:
                    selectedCourse.au = enterAU();
                    break;
                case 6:
                    courseService.saveCourse(selectedCourse);
                    System.out.println(String.format("You have successfully add the course %s", selectedCourse.courseCode));
                    break;
                default:
                    System.out.println("Invalid input. Please try again!");

            }
        } while (sel != 6);

    }

    private String enterCourseCode() {
        Boolean isValid = false;
        String courseCode = null;
        while (!isValid) {
            System.out.println("Enter course code ");
            courseCode = scanner.next();
            isValid = new CourseService().validateCourseCode(courseCode);
        }

        return courseCode;
    }

    private String enterCourseName() {
        System.out.println("Enter course name  ");
        return scanner.next();
    }

    private int enterAU() {
        System.out.println("Enter AU ");
        return scanner.nextInt();
    }


    private int enterSchool() {
        boolean isValid = false;
        int school = 0;
        while (!isValid) {
            System.out.println("Enter school number (EEE:0  MAE:1  SCSE:2  MSE:3)");
            school = scanner.nextInt();
            for (SchoolName schoolName : SchoolName.values()) {
                if (schoolName.toInt() == school) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid)
                System.out.println("Invalid school number");
        }
        return school;
    }

    private int enterCourseType() {
        boolean isValid = false;
        int courseTypeCode = 0;
        while (!isValid) {
            //Core(0, "Core"), GERCore(1, "GERCore"), CoreElective(2, "CoreElective"), UE(3, "UE");
            System.out.println("Enter course type (Core:0  GERCore:1  CoreElective:2  UE:3)");
            courseTypeCode = scanner.nextInt();
            for (CourseType courseType : CourseType.values()) {
                if (courseType.toInt() == courseTypeCode) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid)
                System.out.println("Invalid school number");
        }
        return courseTypeCode;
    }

    public void printStudentListByIndex() {

        // user input index no.
        System.out.println("Please input an index number:  ");
        String indexNumber = scanner.next();

        Integer classId = null;
        ArrayList<Class> classes = new ClassDAO().getAllValid();
        for (Class cls : classes) {
            if (cls.indexNumber.equals(indexNumber)) {
                classId = cls.classId;
            }
        }

        if (classId == null) {
            System.out.println("Index number not found!");
            return;
        }

        System.out.println("-----------------------------------");
        System.out.printf("%s %29s %n","Name", "Matric Number");
        System.out.println("-----------------------------------");
        UserService userService = new UserService();
        ArrayList<User> students = userService.getClassMatesById(classId);

        for (User student : students) {
            System.out.printf("%-20s %s %n",student.displayName, student.matricNumber);
        }
    }

    public void printStudentListByCourse() {
        System.out.println("Please input an course code: ");
        String courseCode = scanner.next();

        CourseService courseService = new CourseService();
        CourseSM course = courseService.getCourseByCourseCode(courseCode);

        if (course == null) {
            System.out.println("Course code not found!");
            return;
        }

        UserService userService = new UserService();


        for (ClassSM cls : course.classes) {

            System.out.print('\n');
            System.out.println(String.format("Index number: %s", cls.indexNumber));
            System.out.println("-----------------------------------");
            System.out.printf("%s %29s %n","Name", "Matric Number");
            System.out.println("-----------------------------------");

            ArrayList<User> students = userService.getClassMatesById(cls.classId);
            for (User student : students) {
                //System.out.println(String.format("Name: %s Matric Number: %s", student.userName, student.matricNumber));
//                System.out.print(student.matricNumber + "          "+student.displayName);
//                System.out.print('\n');
                System.out.printf("%-20s %s %n",student.displayName, student.matricNumber);

            }

        }

    }

    public void exitAdminPage() {

        System.out.println("Thank you for using MYSTARTS Planner. System is closed!!!!");
        System.exit(0);
    }
//
//    private void checkVancancy() {
//        // get data
//        ClassDAO classDAO = new ClassDAO();
//        ArrayList<Class> classes = classDAO.getAllValid();
//
//        // display and user input
//        System.out.println("Please enter index number: ");
//        String indexNumber = scanner.next();
//
//        for (Class cls : classes) {
//            if (cls.indexNumber.equals(indexNumber)) {
//                // print class info
//                System.out.println(String.format("Current available vacancy: %d", cls.totalVacancy - cls.vacancyTaken));
//                return;
//            }
//        }
//
//        System.out.println("Index number not found");
//    }

}
