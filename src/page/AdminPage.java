package page;

import java.util.Scanner;
import dao.ClassDAO;
import dao.CourseDAO;
import dao.IDAO;
import model.Class;
import model.Course;
import model.User;
import service.UserService;
import dao.IDAO;
import model.User;

public class AdminPage<sel> extends Page {
    private User user;
    Scanner scanner = new Scanner(System.in);
    public AdminPage(User user) {
        this.user = user;
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

            sel = scanner.nextInt();
            switch (sel) {
                case 1:
                    //todo
                    break;
                case 2:
                    //todo
                    break;
                case 3:
                    //todo
                    break;
                case 4:
                    //todo
                    break;
                case 5:
                    //todo
                    printStudentListByIndex();
                    break;
                case 6:
                    //todo
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
    
    public void printStudentListByIndex() {
    	
    	//user input index no.
        System.out.println("Please input an index number:  ");
        String indexNumber = scanner.next();
    	//convert index to classId
        //UserService.getClassMates();
    	//call user service getClassMates(int classId)
    	//print classUsers
    	//print error msg if index not found
    	
    	//UserDAO userDAO = new UserDAO();
    	//ArrayList<User> users = userDAO.getAllValid();
    	//for (User userList : users)
    		//System.out.println();
    	
    }
    public void printStudentListByCourse() {
    	
    	//ClassService
    	
    	
    }
    
}
