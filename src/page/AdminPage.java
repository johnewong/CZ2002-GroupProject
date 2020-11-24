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
import utility.DataUtil;

public class AdminPage extends Page {
    private User user;
    private Scanner scanner = new Scanner(System.in);
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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

            try {
                sel = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            switch (sel) {
                case 1:
                    try {
                        changeAccessPeriod();
                    } catch (Exception e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        addStudent();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        addCourses();
                        //courseAddedTime();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        updateCourses();
                        //courseUpdateTime();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    break;
                case 5:
                    // todo
				try {
					checkVancancy();
				} catch (Exception e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;

                case 6:
                    // todo
                    try {
                        printStudentListByIndex();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    // todo
                    printStudentListByCourse();
                    break;
                case 8:
                    try {
                        exitAdminPage();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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

        Date startDate = null;
        Date endDate = null;
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
                if(startDate.compareTo(endDate)>0){
                    throw new Exception("Error: Start date cannot be later than end date");
                }
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error: Invalid date input. Please input again!");
                isValid = false;
                //e.printStackTrace();
            }
        }

        selectedUser.periodStartTime = startDate;
        selectedUser.periodEndTime = endDate;

        // call StudentDAO.Update()
        userDAO.update(selectedUser);
    }

    // Add student
    public void addStudent() throws Exception {
        User user = new User();
        System.out.println("Enter user id  ");
        user.userId = Integer.parseInt(reader.readLine());
        System.out.println("Enter user name ");
        user.userName = reader.readLine();
        System.out.println("Enter display name ");
        user.displayName = reader.readLine();
        System.out.println("Enter password ");
        user.password = DataUtil.encryptPassword(reader.readLine());
        System.out.println("Enter matric number ");
        user.matricNumber = reader.readLine();
        System.out.println("Enter nationality ");
        user.nationality = reader.readLine();
        System.out.println("Enter school e.g 1.EEE  2.SCSE  3.CEE ");
        user.school = reader.readLine();
        System.out.println("Enter gender e.g 0:Male 1:Female 2:Other");
        user.gender = Integer.parseInt(reader.readLine());
        System.out.println("Enter role e.g  0: student 1:admin");
        user.role = Integer.parseInt(reader.readLine());
        System.out.println("Enter start Period e.g format 2020-10-01");
        user.periodStartTime = DATE_FORMATTER.parse(reader.readLine());

        System.out.println("Enter End Period e.g format 2020-10-01");
        user.periodEndTime = DATE_FORMATTER.parse(reader.readLine());

        try {
//            user.periodStartTime = str;
//            user.periodEndTime = end;
        } catch (Exception e) {
            System.out.println("Parse Exception");

        }
        IDAO dao = new UserDAO();
        dao.add(user);
        System.out.println("Student data is added successfully!!!!!");
    }

    // Add Courses
    public void addCourses() throws Exception, IOException {
        Course course = new Course();
        System.out.println("Enter course id  ");
        course.courseId = Integer.parseInt(reader.readLine());
        System.out.println("Enter course code ");
        course.courseCode = reader.readLine();
        System.out.println("Enter course name  ");
        course.courseName = reader.readLine();
        System.out.println("Enter school e.g 1.EEE   2.SCSSE    3.CEE  ");
        course.school = Integer.parseInt(reader.readLine());
        System.out.println("Enter course type e.g 0 Core 1 Elective  ");
        course.courseType = Integer.parseInt(reader.readLine());
        IDAO dao = new CourseDAO();
        dao.add(course);
        System.out.println("Courses added successfully !!!!!");
    }

    // Update Courses
    public void updateCourses() throws Exception, IOException {
        Course course = new Course();
        System.out.println("Enter course id  ");
        course.courseId = Integer.parseInt(reader.readLine());
        System.out.println("Enter course code ");
        course.courseCode = reader.readLine();
        System.out.println("Enter course name  ");
        course.courseName = reader.readLine();
        System.out.println("Enter school e.g 1 eee 2 scse 3 nbs  ");
        course.school = Integer.parseInt(reader.readLine());
        System.out.println("Enter course type e.g 0 Core 1 Elective  ");
        course.courseType = Integer.parseInt(reader.readLine());
        IDAO dao = new CourseDAO();
        dao.update(course);
        System.out.println("Courses updated successfully !!!!!");
    }



    public void printStudentListByIndex() throws Exception {

        // user input index no.
        System.out.println("Please input an index number:  ");
        String indexNumber = reader.readLine();

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

    public void exitAdminPage() throws Exception {

        System.out.println("Thank you for using MYSTARTS Planner. System is closed!!!!");
        System.exit(0);
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

}
