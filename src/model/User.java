package model;

import java.util.Date;

public class User {

    public User() {
    }

    public User(String userName, String displayName) {
        this.userName = userName;
        this.displayName = displayName;
    }

    public int userId; // not null,     alex-1
    public String userName; //not null, primary key
    public String displayName; // first name,last name
    public String password; //not null
    public String matricNumber; //nullable
    public String nationality; //not null
    public int school;
    public int gender; //not null 0:Male 1:Female 2:Other
    public int role; //not null 0: student 1:admin
    public Date periodStartTime; //nullable
    public Date periodEndTime; //nullable
    public boolean hasFirstLogin;
    public boolean isDeleted; // not null

    public String getUserName() {

        return this.userName;
    }
}


