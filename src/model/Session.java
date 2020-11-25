/**
 Class. A data model class to contains session info
 Same as the data file

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */
package model;

public class Session {
    public int sessionId;
    public int classId;
    public int day;
    public String time;
    public String venue;
    public int classType;// 1 Lec, 2 Tut, 3 Lab    not null
    public boolean isDeleted;

    // constructor
    public Session() {
    }

    public Session(int sessionId, int classId, int day, String time, String venue, int classType) {
        this.sessionId = sessionId;
        this.classId = classId;
        this.day = day;
        this.time = time;
        this.venue = venue;
        this.classType = classType;
    }
}
