package service;

import dao.*;
import model.*;


import java.util.ArrayList;

public class CourseService {

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
            registeredCourseSMs.add(courseSM);
        }

        return registeredCourseSMs;
    }



}
