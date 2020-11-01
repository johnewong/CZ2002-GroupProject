package model;

public class Session {
    public int sessionId;
    public int classId;
    public  int day;
    public String time;
    public String venue;
    public boolean isDeleted;
    
    // constructor
    public Session() {}

    public Session(int sessionId, int classId, int day, String time, String venue) {
        this.sessionId = sessionId;
        this.classId = classId;
    	this.day = day;
        this.time = time;
        this.venue = venue;
    }

    public static void add(Session session) {
    }

    // setter method
    public void setSessionId(int sessionId) {
    	this.sessionId = sessionId;
    }
  
    public void setClassId(int classId) {
    	this.classId = classId;
    }
    
    public void setDay(int day) {
    	this.day = day;
    }
  
    public void setTime(String time) {
    	this.time = time;
    }
    
    public void setVenue(String venue) {
    	this.venue = venue;
    }
    
    //getter method 
    public int getSessionId() {
    	return sessionId;
    }
    
    public int getClassId() {
    	return classId;
    }
    
    public int getDay() {
    	return day;
    }
    
    public String getTime() {
    	return time;
    }
    
    public String getVenue() {
    	return venue;
    }
    
}
