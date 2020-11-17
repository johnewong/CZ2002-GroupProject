package dao;

import model.Course;
import utility.DataUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class CourseDAO implements IDAO<Course> {
    private ArrayList<Course> allCourses;
    private ArrayList<Course> allValidCourses;

    public CourseDAO(){
        this.allCourses = getAll();
        this.allValidCourses = getAllValid();
    }
    @Override
    public ArrayList<Course> getAll() {

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

    @Override
    public ArrayList<Course> getAllValid() {
        ArrayList<Course> courseList = new ArrayList<>();
        for (Course course: allCourses){
            if(!course.isDeleted)
                courseList.add(course);
        }
        return courseList;
    }

    @Override
    public Course get(int courseId) {
        for (Course course : this.allCourses){
            if(course.courseId == courseId && !course.isDeleted) {
                return course;
            }
        }
        return null;
    }

    @Override
    public void add(Course newCourse) {
        try{
            ArrayList<Course> courseList = this.allCourses;
            for (Course c : courseList) {
                if (c.courseId == newCourse.courseId && !c.isDeleted) {
                    throw new Exception("The Course is already existed");
                }
            }
            courseList.sort((a, b) -> a.courseId - b.courseId);
            newCourse.courseId = courseList.get(courseList.size()-1).courseId +1;
            courseList.add(newCourse);
            DataUtil.writeFile(courseList, "dataFiles/course.txt");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Course item) {
        Course existCourse = this.get(item.courseId);
        if(existCourse != null){
            String courseLine=item.courseId+","+item.courseCode+","+item.courseName+","+item.school+","+item.courseType+","+item.au;
            existCourse.courseCode = item.courseCode;
            existCourse.courseName = item.courseName;
            existCourse.school = item.school;
            existCourse.courseType = item.courseType;
            existCourse.au = item.au;
            File file=new File("dataFiles/course.txt");
            BufferedReader br=null;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            BufferedReader input=null;
            try {
                input = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String header="";
            try {
                header =input.readLine();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            FileReader reader=null;
            try {
                reader = new FileReader(file);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            br=new BufferedReader(reader);
            List list=new ArrayList();
            try {
                while(br.readLine()!=null) {
                    list.add(br.readLine());
                    System.out.println(br.readLine());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if(file.exists()) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                file.delete();
                System.out.println("Deleted");
                System.out.println("Size of list "+list.size());
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                PrintWriter pw=null;
                try {
                    pw = new PrintWriter(file);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                pw.write(header+"\n");
                for(int i=0; i<list.size(); i++) {
                    String str=list.get(i)+"";
                    String ch=str.charAt(i)+"";
                    String match=item.courseId+"";

                    if(ch.equals(match)) {
                        pw.write(courseLine+"\n");
                        System.out.println("working "+item.courseId);
                        System.out.println(courseLine);
                    }
                    else {
                        pw.write(list.get(i)+"\n");
                    }
                }
                pw.close();
            }
            else {
                System.out.println("File does not exit");
            }

        }

        else {
            System.out.println("Course not found");
        }

    }

    @Override
    public void delete(int courseId) {
        Course existCourse = this.get(courseId);
        if(existCourse != null){
            existCourse.isDeleted = true;
        }
        else {
            System.out.println("Course not found");
        }

    }
}
