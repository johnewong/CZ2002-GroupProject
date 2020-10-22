package service;

import model.Session;
import model.User;
import model.Class;

import java.util.ArrayList;

public class ClassSM {
    public int classId; // not null
    public int courseId;// not null
    public String indexNumber; //primary key
    public int vacancyTaken; // not null   40/50
    public int totalVacancy;
    public int numberInWaitlist;
    public int group;
    public int classType;// not null
    public String remark;
    public boolean isDeleted; // not null
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
        this.classType = cls.classType;
        this.remark = cls.remark;
        this.isDeleted = cls.isDeleted;
        this.users = users;
        this.sessions = sessions;
    }
}
