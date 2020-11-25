/**
 ClassUser. A data model class to contains registration info
 Same as the data file

 @author Weng Yifei
 @version 1.0
 @since Nov-2020
 */

package model;

public class ClassUser {
    public int classUserId;
    public int userId;  // 1
    public int classId; // 2
    public int status; //0:rejected 1:registered 2:inWaitlist
    public boolean isDeleted; // not null

    public ClassUser(){};
    public ClassUser(int userId, int classId, int status) {
        this.userId = userId;
        this.classId = classId;
        this.status = status;
    }
}

