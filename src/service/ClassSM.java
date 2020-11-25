package service;

import model.Session;
import model.User;
import model.Class;

import java.util.ArrayList;

public class ClassSM extends Class {
//    public int classId; // not null
//    public int courseId;// not null
//    public String indexNumber; //primary key
//    public int vacancyTaken; // not null   40/50
//    public int totalVacancy;
//    public int numberInWaitlist;
//    public int group;
//    public String remark;
//    public boolean isDeleted; // not null
    public ArrayList<User> users;
    public ArrayList<Session> sessions;


    public ClassSM(Class cls, ArrayList<User> users, ArrayList<Session> sessions) {
        this.classId = cls.classId;
        this.courseId = cls.courseId;
        this.indexNumber = cls.indexNumber;
        this.vacancyTaken = cls.vacancyTaken;
        this.totalVacancy = cls.totalVacancy;
        this.numberInWaitlist = cls.numberInWaitlist;
        this.group = cls.group;
        this.remark = cls.remark;
        this.isDeleted = cls.isDeleted;
        this.users = users;
        this.sessions = sessions;
    }

//    public Class toClass(ClassSM classSM){
//        Class cls = new Class();
//        cls.classId = classSM.classId;
//        cls.courseId = classSM.courseId;
//        cls.indexNumber = classSM.indexNumber;
//        cls.vacancyTaken = classSM.vacancyTaken;
//        cls.totalVacancy = classSM.totalVacancy;
//        cls.numberInWaitlist = classSM.numberInWaitlist;
//        cls.group = classSM.group;
//        cls.remark = classSM.remark;
//        cls.isDeleted = classSM.isDeleted;
//        return cls;
//    }
}
