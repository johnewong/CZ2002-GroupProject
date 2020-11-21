package service;


import dao.ClassDAO;
import dao.ClassUserDAO;
import dao.SessionDAO;
import model.Class;
import model.ClassUser;
import utility.StatusEnum;

import java.util.ArrayList;

public class ClassService {

    public ArrayList<ClassSM> getRegisteredClasses(int userId) {
        ClassUserDAO classUserDAO = new ClassUserDAO();
        ClassDAO classDAO = new ClassDAO();
        SessionDAO sessionDAO = new SessionDAO();
        UserService userService = new UserService();
        ArrayList<Class> classes = classDAO.getAllValid();
        ArrayList<ClassUser> classUsers = classUserDAO.getAllValid();

        ArrayList<ClassSM> registeredClassSMs = new ArrayList<>();
        ArrayList<Integer> registeredClassIds = new ArrayList<>();
        for (ClassUser classUser : classUsers) {
            if (classUser.userId == userId && classUser.status == StatusEnum.REGISTERED.toInt())
                registeredClassIds.add(classUser.classId);
        }

        for (Class cls : classes) {
            if (registeredClassIds.contains(cls.classId)) {
                ClassSM classSM = new ClassSM(cls, userService.getClassMates(cls.classId), sessionDAO.getByClassId(cls.classId));
                registeredClassSMs.add(classSM);
            }
        }

        return registeredClassSMs;
    }

    public ArrayList<ClassSM> getCourseClasses(int courseId) {
        ClassDAO classDAO = new ClassDAO();
        SessionDAO sessionDAO = new SessionDAO();
        UserService userService = new UserService();

        ArrayList<Class> classes = classDAO.getByCourseId(courseId);
        ArrayList<ClassSM> classSMs = new ArrayList<>();
        for (Class cls : classes) {
            ClassSM classSM = new ClassSM(cls, userService.getClassMates(cls.classId), sessionDAO.getByClassId(cls.classId));
            classSMs.add(classSM);
        }

        return classSMs;
    }

    public ArrayList<ClassSM> getWaitlistClasses(int userId){
        ClassUserDAO classUserDAO = new ClassUserDAO();
        ClassDAO classDAO = new ClassDAO();
        SessionDAO sessionDAO = new SessionDAO();
        UserService userService = new UserService();
        ArrayList<Class> classes = classDAO.getAllValid();
        ArrayList<ClassUser> classUsers = classUserDAO.getAllValid();

        ArrayList<ClassSM> waitlistClassSMs = new ArrayList<>();
        ArrayList<Integer> waitlistClassIds = new ArrayList<>();


        for(ClassUser waitlistClassUser : classUsers){
            if(waitlistClassUser.userId == userId && waitlistClassUser.status == StatusEnum.INWAITLIST.toInt())
                waitlistClassIds.add(waitlistClassUser.classId);
        }
        for(Class cls : classes){
            if(waitlistClassIds.contains(cls.classId)){
                ClassSM classSM = new ClassSM(cls, userService.getClassMates(cls.classId), sessionDAO.getByClassId(cls.classId));
                waitlistClassSMs.add(classSM);
            }

        }
        return waitlistClassSMs;

    }
}
