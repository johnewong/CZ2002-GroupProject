package model;

public class Session {
    public int sessionId;
    public int classId;
    public  int day;
    public String time;
    public String venue;
    
    // constructor
    public Session() {}

    public Session(int day, String time, String venue) {
        this.day = day;
        this.time = time;
        this.venue = venue;
    }
    
    // setter method
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
    public int getDay() {
    	return day;
    }
    
    public String gettime() {
    	return time;
    }
    
    public String getVenue() {
    	return venue;
    }
    
}
