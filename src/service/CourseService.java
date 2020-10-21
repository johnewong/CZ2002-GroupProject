package service;

import dao.ClassDAO;
import dao.ClassUserDAO;
import dao.CourseDAO;
import dao.SessionDAO;
import model.*;
import model.Class;
import utility.StatusEnum;

import java.util.ArrayList;

public class CourseService {

    public ArrayList<CourseSM> getRegisteredCourses(User user) {
        CourseDAO courseDAO = new CourseDAO();
        ClassDAO classDAO = new ClassDAO();
        SessionDAO sessionDAO = new SessionDAO();
        ClassUserDAO classUserDAO = new ClassUserDAO();

        ArrayList<Course> courses = courseDAO.getAllValid();
        ArrayList<Class> classes = classDAO.getAllValid();
        ArrayList<Session> sessions = sessionDAO.getAllValid();
        ArrayList<ClassUser> classUsers = classUserDAO.getAllValid();

        // to get all registered classes
        ArrayList<CourseSM> registeredCourses = null;
        ArrayList<ClassSM> registeredClasses = null;
        ArrayList<Integer> classIdList = null;
        for (ClassUser classUser : classUsers) {
            if (classUser.userId == user.userId && classUser.status == StatusEnum.REGISTERED.toInt())
                classIdList.add(classUser.classId);
        }

        for (Class cls : classes) {
            if (classIdList.contains(cls.classId)) {

                ClassSM classSM = new ClassSM(cls, new ClassService().getClassMates(cls.classId), sessionDAO.getClassSessions(cls.classId));
                registeredClasses.add(classSM);
            }

        }

        // to get all registered courses
        for (ClassSM cls : registeredClasses) {
            //registeredCourses.add(courseDAO.get(cls.courseId));
        }

        return registeredCourses;
    }
}
