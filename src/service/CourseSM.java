package service;

import model.Class;

import java.util.ArrayList;

public class CourseSM {
    public int courseId;
    public String courseCode; //primary key, different course code to deferential core or elective
    public String courseName;
    public int school; //1 eee 2 scse 3 nbs
    public int courseType;
    public int au;
    public boolean isDeleted; // not null
    public ArrayList<ClassSM> classes;
}
