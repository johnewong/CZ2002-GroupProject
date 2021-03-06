/**
 Course. To contains all information relevant to the course
 Inherit model.Course
 Contains course info, all classes in the course

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */
package service;

import model.Course;
import java.util.ArrayList;

public class CourseSM extends Course {
//    public int courseId;
//    public String courseCode; //primary key, different course code to deferential core or elective
//    public String courseName;
//    public int school; //1 eee 2 scse 3 nbs
//    public int courseType;
//    public int au;
//    public boolean isDeleted; // not null
    public ClassSM registeredClass;
    public ArrayList<ClassSM> classes;

    public CourseSM(){};
    public CourseSM(Course course, ArrayList<ClassSM> classes){
        this.courseId = course.courseId;
        this.courseCode = course.courseCode;
        this.courseName = course.courseName;
        this.school = course.school;
        this.courseType = course.courseType;
        this.au = course.au;
        this.isDeleted = course.isDeleted;
        this.classes = classes;
    }
}
