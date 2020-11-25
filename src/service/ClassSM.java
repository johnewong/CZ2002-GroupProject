/**
 ClassSM. To contains all information relevant to the class
 Inherit model.Class
 Contains class info, class students info, sessions info

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */

package service;

import model.Session;
import model.User;
import model.Class;

import java.util.ArrayList;

public class ClassSM extends Class {

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
}
