package model;


public class Class {
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


    public Class() {
    }
    public Class(int classId, int courseId, int classType, int totalVacancy, int numberInWaitlist, int group) {
        this.courseId = courseId;
        this.classId = classId;
        this.classType = classType;
        this.totalVacancy = totalVacancy;
        this.numberInWaitlist = numberInWaitlist;
        this.group = group;

    }
}

// 1 class -> 3  session
