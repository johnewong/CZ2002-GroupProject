package model;


public class Class {
    private int classId; // not null
    private int courseId;// not null
    private String indexNumber; //primary key
    private int vacancyTaken; // not null   40/50
    private int totalVacancy;
    private int numberInWaitlist;
    private int group;
    private int classType;// not null
    private String remark;
    private boolean isDeleted; // not null
}

// 1 class -> 3  session
