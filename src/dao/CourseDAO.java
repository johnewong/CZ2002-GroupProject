package dao;

<<<<<<< HEAD
import model.Course;
import utility.DataUtil;
import java.util.ArrayList;


=======
>>>>>>> b9ac62f77e1a1a0b8b908a721b436567e49c283c
public class CourseDAO {
	
	public ArrayList<Course> getAllCourse(){
		
		String dataString = DataUtil.loadFile("dataFiles/course.txt");
		String[] rows = dataString.split(";");
		ArrayList<Course> courseList = new ArrayList<>();
		
		for(int i=1; i<rows.length; i++) {
			
			Course course = new Course();
			DataUtil.setObject(course, rows[0], rows[i]);
			courseList.add(course);
			
		}
		
		return courseList;	
	}
	
    public Course getCourse(int courseId) {

        String dataString = DataUtil.loadFile("dataFiles/course.txt");
        String[] rows = dataString.split(";");
        Course course = new Course();
        DataUtil.setObject(course, rows[0], rows[1]);

        return course;
    }

    public Course getCourse(ArrayList<Course> courseList, int courseId) {

    	Course course = null;
        for (Course courses : courseList) 
        {
           if(courses.courseId == courseId)
           {
        	   course = courses;
               break;
           }
        }

        return course;
    }
	
	
}
