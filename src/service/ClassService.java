package service;


import dao.ClassDAO;
import dao.ClassUserDAO;
import dao.SessionDAO;
import entity.Class;
import entity.ClassUser;
import entity.User;
import utility.StatusEnum;

import java.util.ArrayList;

public class ClassService {

    public boolean validateIndexNumber(String indexNumber){
        ClassDAO classDAO = new ClassDAO();
        ArrayList<Class> allClasses = classDAO.getAllValid();

        for (Class cls : allClasses) {
            if (cls.indexNumber.equals(indexNumber)) {
                System.out.println("Index number is already existed ");
                return false;
            }
        }

        return true;
    }

    public Class saveClass(Class newClass){
        ClassDAO classDAO = new ClassDAO();
        classDAO.add(newClass);

        return newClass;
    }

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
                ClassSM classSM = new ClassSM(cls, userService.getClassMatesById(cls.classId), sessionDAO.getByClassId(cls.classId));
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
            ClassSM classSM = new ClassSM(cls, userService.getClassMatesById(cls.classId), sessionDAO.getByClassId(cls.classId));
            classSMs.add(classSM);
        }

        return classSMs;
    }

    public ArrayList<ClassSM> getWaitlistClasses(int userId) {
        ClassUserDAO classUserDAO = new ClassUserDAO();
        ClassDAO classDAO = new ClassDAO();
        SessionDAO sessionDAO = new SessionDAO();
        UserService userService = new UserService();
        ArrayList<Class> classes = classDAO.getAllValid();
        ArrayList<ClassUser> classUsers = classUserDAO.getAllValid();

        ArrayList<ClassSM> waitlistClassSMs = new ArrayList<>();
        ArrayList<Integer> waitlistClassIds = new ArrayList<>();


        for (ClassUser classUser : classUsers) {
            if (classUser.userId == userId && classUser.status == StatusEnum.INWAITLIST.toInt())
                waitlistClassIds.add(classUser.classId);
        }
        for (Class cls : classes) {
            if (waitlistClassIds.contains(cls.classId)) {
                ClassSM classSM = new ClassSM(cls, userService.getClassMatesById(cls.classId), sessionDAO.getByClassId(cls.classId));
                waitlistClassSMs.add(classSM);
            }

        }
        return waitlistClassSMs;
    }

    public ClassSM dropClass(User user, ClassSM selectedClass) {
        ClassUserDAO classUserDAO = new ClassUserDAO();
        ClassDAO classDAO = new ClassDAO();

        ClassUser deleteClassUser = classUserDAO.get(user.userId, selectedClass.classId);
        selectedClass.vacancyTaken--;

        classDAO.update(selectedClass);
        classUserDAO.delete(deleteClassUser.classUserId);

        return selectedClass;
    }

    public ClassSM changeClass(User user, ClassSM registeredClass, ClassSM selectedClass, boolean isSwap) {
        if (!isSwap && selectedClass.vacancyTaken >= selectedClass.totalVacancy) {
            return null;
        }

        ClassUserDAO classUserDAO = new ClassUserDAO();
        ClassDAO classDAO = new ClassDAO();

        ClassUser newClassUser = new ClassUser(user.userId, selectedClass.classId, StatusEnum.REGISTERED.toInt());
        ClassUser deleteClassUser = classUserDAO.get(user.userId, registeredClass.classId);

        registeredClass.vacancyTaken--;
        selectedClass.vacancyTaken++;

        classDAO.update(registeredClass);
        classDAO.update(selectedClass);
        classUserDAO.add(newClassUser);
        classUserDAO.delete(deleteClassUser.classUserId);

        return selectedClass;
    }

    public ClassSM registerClass(User user, ClassSM selectedClass) {
        ClassUserDAO classUserDAO = new ClassUserDAO();
        ClassDAO classDAO = new ClassDAO();
        ClassUser classUser = null;

        if (selectedClass.vacancyTaken >= selectedClass.totalVacancy) {
            classUser = new ClassUser(user.userId, selectedClass.classId, StatusEnum.INWAITLIST.toInt());
            selectedClass.numberInWaitlist++;

        } else {
            classUser = new ClassUser(user.userId, selectedClass.classId, StatusEnum.REGISTERED.toInt());
            selectedClass.vacancyTaken++;
        }
        classDAO.update(selectedClass);
        classUserDAO.add(classUser);

        return selectedClass;
    }
}
