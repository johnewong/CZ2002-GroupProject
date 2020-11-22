package service;

import dao.*;
import model.*;


import java.util.ArrayList;

public class CourseService {

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
