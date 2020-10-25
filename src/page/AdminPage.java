package page;
import java.util.Scanner;

import dao.IDAO;

public class AdminPage extends Page{ 
    private AdminUser user;
    
    public AdminPage(AdminUser user) {
    	this.AdminUser = user;
    }
    public void showPage() {
    	Scanner scanner = new Scanner(System.in);
    	int sel=0;
    	do
    	{
    		System.out.println(String.format("\n Hi %s Welcome to Admin Page!", AdminUser.displayName));
    		System.out.println("1. Edit Student Access Period");
            System.out.println("2. Add Sudent Information");
            System.out.println("3. Add Courses");
            System.out.println("4. Update Courses ");
            System.out.println("5. Exit");
            System.out.println("Please choose an option:  ");
    	}
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
    		System.out.println("Exit Admin Page.");
    		break;
    	 default:
             System.out.println("Invalid input. Please try again!");
    	}
    } while (sel !=5);
    
}
