/**
 Course service provide complex data manipulation methods.

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */


package service;

import dao.*;
import model.*;


import java.util.ArrayList;

public class CourseService {

    /**
     * Method to add or update a course
     *
     * @param newCourse
     * @return return course
     */
    public Course saveCourse(Course newCourse) {
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> allCourses = courseDAO.getAllValid();

        for (Course course : allCourses) {
            if (course.courseCode.equals(newCourse.courseCode)) {
                courseDAO.update(newCourse);
                return newCourse;
            }
        }

        courseDAO.add(newCourse);
        return newCourse;
    }

    /**
     * Method to validate course code duplcation
     *
     * @param courseCode
     * @return isValid
     */
    public boolean validateCourseCode(String courseCode) {
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> allCourses = courseDAO.getAllValid();

        for (Course course : allCourses) {
            if (course.courseCode.equals(courseCode)) {
                System.out.println("CourseCode is already existed ");
                return false;
            }
        }

        return true;
    }

    /**
     * Method to get all courses
     *
     * @return return list of classes
     */
    public ArrayList<CourseSM> getAllCourses() {
        CourseDAO courseDAO = new CourseDAO();
        ClassService classService = new ClassService();

        ArrayList<Course> allCourses = courseDAO.getAllValid();
        ArrayList<CourseSM> allCourseSMs = new ArrayList<>();
        for (Course course : allCourses) {
            CourseSM courseSM = new CourseSM(course, classService.getCourseClasses(course.courseId));
            allCourseSMs.add(courseSM);
        }

        return allCourseSMs;
    }

    /**
     * Method to get course by course code
     *
     * @param courseCode
     * @return return course
     */
    public CourseSM getCourseByCourseCode(String courseCode) {
        // to get all registered classes

        Course course = new CourseDAO().getByCode(courseCode);
        if (course == null) {
            return null;
        }

        ClassService classService = new ClassService();
        ArrayList<ClassSM> classSMS = classService.getCourseClasses(course.courseId);

        return new CourseSM(course, classSMS);
    }

    /**
     * Method to get unregistered courses by user
     *
     * @param user
     * @return return list of courses
     */
    public ArrayList<CourseSM> getUnregisteredCourses(User user) {
        CourseDAO courseDAO = new CourseDAO();
        ClassService classService = new ClassService();

        // to get all registered classes
        ArrayList<CourseSM> unregisteredCourseSMs = new ArrayList<>();
        ArrayList<ClassSM> waitlistClassSMs = classService.getWaitlistClasses(user.userId);
        ArrayList<ClassSM> registeredClassSMs = classService.getRegisteredClasses(user.userId);

        ArrayList<Integer> unavailableCourseIds = new ArrayList<>();
        for (ClassSM classSM : registeredClassSMs) {
            unavailableCourseIds.add(classSM.courseId);
        }

        for (ClassSM classSM : waitlistClassSMs) {
            unavailableCourseIds.add(classSM.courseId);
        }

        ArrayList<Course> allCourses = courseDAO.getAllValid();
        for (Course course : allCourses) {
            if (!unavailableCourseIds.contains(course.courseId)) {
                CourseSM newCourseSM = new CourseSM(course, classService.getCourseClasses(course.courseId));
                unregisteredCourseSMs.add(new CourseSM(course, classService.getCourseClasses(course.courseId)));
            }
        }

        return unregisteredCourseSMs;
    }

    /**
     * Method to get registered courses by user
     *
     * @param user
     * @return return list of courses
     */
    public ArrayList<CourseSM> getRegisteredCourses(User user) {
        CourseDAO courseDAO = new CourseDAO();
        ClassService classService = new ClassService();

        // to get all registered classes
        ArrayList<ClassSM> registeredClassSMs = classService.getRegisteredClasses(user.userId);

        // to get all registered courses and their classes
        ArrayList<CourseSM> registeredCourseSMs = new ArrayList<>();
        for (ClassSM cls : registeredClassSMs) {
            Course course = courseDAO.get(cls.courseId);
            CourseSM courseSM = new CourseSM(course, classService.getCourseClasses(cls.courseId));
            courseSM.registeredClass = cls;
            registeredCourseSMs.add(courseSM);
        }

        return registeredCourseSMs;
    }

    /**
     * Method to get courses in waitlist by user
     *
     * @param user
     * @return return list of courses
     */
    public ArrayList<CourseSM> getWaitlistCourses(User user) {
        CourseDAO courseDAO = new CourseDAO();
        ClassService classService = new ClassService();

        ArrayList<ClassSM> waitlistClassSMs = classService.getWaitlistClasses(user.userId);

        ArrayList<CourseSM> waitlistCourseSMs = new ArrayList<>();
        for (ClassSM waitlistClass : waitlistClassSMs) {
            Course course = courseDAO.get(waitlistClass.courseId);
            CourseSM courseSM = new CourseSM(course, classService.getCourseClasses(waitlistClass.courseId));
            waitlistCourseSMs.add(courseSM);
        }

        return waitlistCourseSMs;
    }


}
