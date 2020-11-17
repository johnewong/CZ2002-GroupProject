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
import service.UserService;
import dao.IDAO;
import model.User;

public class AdminPage extends Page {
	private User user;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public AdminPage(User user) {
		this.user = user;
	}

	public AdminPage() {

	}

	public void showPage() {
		int sel = 0;
		do {
			System.out.println(String.format("\n Hi %s Welcome to Admin Page!", user.displayName));
			System.out.println("1. Edit Student Access Period");
			System.out.println("2. Add Sudent Information");
			System.out.println("3. Add Courses");
			System.out.println("4. Update Courses ");
			System.out.println("5. Exit");
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
					getDateAndTime();
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
					courseAddedTime();
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
					courseUpdateTime();
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
					printStudentListByIndex();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
				// todo
				printStudentListByCourse();
				break;
			case 7:
				System.out.println("Exit Admin Page.");
				break;
			default:
				System.out.println("Invalid input. Please try again!");
			}

		} while (sel != 7);
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
		user.password = reader.readLine();
		System.out.println("Enter matric number ");
		user.matricNumber = reader.readLine();
		System.out.println("Enter nationality ");
		user.nationality = reader.readLine();
		System.out.println("Enter gender e.g 0:Male 1:Female 2:Other");
		user.gender = Integer.parseInt(reader.readLine());
		System.out.println("Enter role e.g  0: student 1:admin");
		user.role = Integer.parseInt(reader.readLine());
		System.out.println("Enter start Period e.g format 22-10-2020");
		String str = reader.readLine();
		System.out.println("Enter End Period e.g format 22-10-2020");
		String end = reader.readLine();
		try {
			user.periodStartTime = str;
			user.periodEndTime = end;
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
		System.out.println("Enter school e.g 1 eee 2 scse 3 nbs  ");
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

	// access period
	public void courseAddedTime() throws Exception {
		User user = new User();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		PrintWriter pw = new PrintWriter("dataFiles/accessPeriod.txt");
		pw.write("Last Added Time"+" "+dtf.format(now));
		pw.close();
	}

	public void courseUpdateTime() throws Exception {
		User user = new User();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		PrintWriter pw = new PrintWriter("dataFiles/accessPeriod.txt");
		pw.write("Last Updated Time "+" "+dtf.format(now));
		pw.close();
	}

	// Get Time for access period
	public void getDateAndTime() throws Exception {
		FileReader reader = new FileReader("dataFiles/accessPeriod.txt");
		BufferedReader br = new BufferedReader(reader);
		List list = new ArrayList();
		String line=br.readLine();
		while ( line!= null) {
			list.add(line);
			line=br.readLine();

		}
		if(list.size()==0) {
			System.out.println("No course is added recently");
		}
		else {
			
			System.out.println( list.get(0));
//			System.out.println("Last course updated " + list.get(1));
		}
		

	}

	public void printStudentListByIndex() throws Exception {

		// user input index no.
		System.out.println("Please input an index number:  ");
		String indexNumber = reader.readLine();

		// index -> classId -> call getClassMates(classId) ->

		// convert index to classId
		// UserService.getClassMates();
		// call user service getClassMates(int classId)
		// print classUsers
		// print error msg if index not found

		// UserDAO userDAO = new UserDAO();
		// ArrayList<User> users = userDAO.getAllValid();
		// for (User userList : users)
		// System.out.println();

	}

	public void printStudentListByCourse() {

		// ClassService

	}

}
