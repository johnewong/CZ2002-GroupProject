package model;

import java.util.Date;

public class Session {
    public int sessionId;
    public int classId;
    public int teachingType; //0: lec, 1:lab, 2:tut
    public int day;
    private String venue;
    private Date startTime;// not null
    private Date endTime;// not null
}
