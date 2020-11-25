/**
 Course. A data model class to contains course info
 Same as the data file

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */

package model;

public class Course {
    public int courseId;
    public String courseCode; //primary key, different course code to deferential core or elective
    public String courseName;
    public int school; //1 eee 2 scse 3 nbs
    public int courseType;//0 Core 1 Elective
    public int au;
    public boolean isDeleted; // not null



}

