package dao;

import entity.Session;
import utility.DataUtil;
import java.util.ArrayList;

public class SessionDAO implements IDAO<Session> {
    private ArrayList<Session> allSessions;
    private ArrayList<Session> allValidSessions;

    // constructor
    public SessionDAO() {
        this.allSessions = getAll();
        this.allValidSessions = getAllValid();
    }

    @Override
    public ArrayList<Session> getAll() {
        if(this.allSessions != null){
            return this.allSessions;
        }
        String dataString = DataUtil.loadFile("dataFiles/session.txt");
        String[] rows = dataString.split(";");
        ArrayList<Session> sessions = new ArrayList<>();
        for (int i = 1; i < rows.length; i++) {
            Session s = new Session();
            DataUtil.setObject(s, rows[0], rows[i]);
            sessions.add(s);
        }

        return sessions;
    }

    @Override
    public ArrayList<Session> getAllValid() {
        if(this.allValidSessions != null){
            return this.allValidSessions;
        }
        ArrayList<Session> session = new ArrayList<>();
        for (Session s : allSessions) {
            if (!s.isDeleted)
                session.add(s);
        }
        return session;
    }

    public ArrayList<Session> getByClassId(int classId) {
        ArrayList<Session> sessionList = new ArrayList<>();
        for(Session session: this.allValidSessions){
            if(session.classId == classId)
                sessionList.add(session);
        }
        return sessionList;
    }

    @Override
    public Session get(int id) {
        for (Session session : this.allValidSessions) {
            if (session.sessionId == id) {
                return session;
            }
        }
        return null;
    }

    @Override
    public void add(Session newSession) {
        try {
            ArrayList<Session> sessions = this.allSessions;
            // validation
            for (Session s : sessions) {
                if (s.sessionId == newSession.sessionId && !s.isDeleted) {
                    throw new Exception("The session is already existed");
                }
            }

            sessions.sort((a, b) -> a.sessionId - b.sessionId);
            newSession.sessionId = sessions.get(sessions.size() - 1).sessionId + 1;
            sessions.add(newSession);
            DataUtil.writeFile(sessions, "dataFiles/session.txt");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Session session) {
        Session existedSession = this.get(session.sessionId);
        if(existedSession !=null){
            existedSession.classId = session.classId;
            existedSession.day=session.day;
            existedSession.time=session.time;
            existedSession.venue=session.venue;
            existedSession.classType=session.classType;
            DataUtil.writeFile(this.allSessions, "dataFiles/session.txt");
        }
        else {
            System.out.println("Session not found");
        }

    }

    @Override
    public void delete(int sessionId) {
        Session existedSession = this.get(sessionId);
        if(existedSession != null){
            existedSession.isDeleted = true;
            DataUtil.writeFile(this.allSessions, "dataFiles/session.txt");
        }
        else {
            System.out.println("Session not found");
        }
    }
}
