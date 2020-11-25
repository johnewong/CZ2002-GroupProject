package dao;

import entity.Course;
import utility.DataUtil;

import java.util.ArrayList;


public class CourseDAO implements IDAO<Course> {
    private ArrayList<Course> allCourses;
    private ArrayList<Course> allValidCourses;

    public CourseDAO() {
        this.allCourses = getAll();
        this.allValidCourses = getAllValid();
    }

    @Override
    public ArrayList<Course> getAll() {
        if (this.allCourses != null) {
            return this.allCourses;
        }

        String dataString = DataUtil.loadFile("dataFiles/course.txt");
        String[] rows = dataString.split(";");
        ArrayList<Course> courseList = new ArrayList<>();

        for (int i = 1; i < rows.length; i++) {
            Course course = new Course();
            DataUtil.setObject(course, rows[0], rows[i]);
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public ArrayList<Course> getAllValid() {
        if (this.allValidCourses != null) {
            return this.allValidCourses;
        }

        ArrayList<Course> courseList = new ArrayList<>();
        for (Course course : allCourses) {
            if (!course.isDeleted)
                courseList.add(course);
        }
        return courseList;
    }

    @Override
    public Course get(int courseId) {
        for (Course course : this.allCourses) {
            if (course.courseId == courseId && !course.isDeleted) {
                return course;
            }
        }
        return null;
    }

    public Course getByCode(String courseCode) {
        for (Course course : this.allCourses) {
            if (course.courseCode.equals(courseCode) && !course.isDeleted) {
                return course;
            }
        }
        return null;
    }

    @Override
    public void add(Course newCourse) {
        try {
            ArrayList<Course> courseList = this.allCourses;
            for (Course c : courseList) {
                if (c.courseId == newCourse.courseId && !c.isDeleted) {
                    throw new Exception("The Course is already existed");
                }
            }
            courseList.sort((a, b) -> a.courseId - b.courseId);
            newCourse.courseId = courseList.get(courseList.size() - 1).courseId + 1;
            courseList.add(newCourse);
            DataUtil.writeFile(courseList, "dataFiles/course.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Course item) {
        Course existCourse = this.get(item.courseId);
        if (existCourse != null) {
            existCourse.courseCode = item.courseCode;
            existCourse.courseName = item.courseName;
            existCourse.school = item.school;
            existCourse.courseType = item.courseType;
            existCourse.au = item.au;
            DataUtil.writeFile(this.allCourses, "dataFiles/course.txt");
        } else {
            System.out.println("Course not found");
        }

    }

    @Override
    public void delete(int courseId) {
        Course existCourse = this.get(courseId);
        if (existCourse != null) {
            existCourse.isDeleted = true;
            DataUtil.writeFile(this.allCourses, "dataFiles/course.txt");
        } else {
            System.out.println("Course not found");
        }
    }
}


