package dao;

import model.Session;
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

    public ArrayList<Session> getAllSession() {

        String dataString = DataUtil.loadFile("dataFiles/session.txt");
        String[] rows = dataString.split(";");
        ArrayList<Session> sessions = new ArrayList<>();
        for (int i = 1; i < rows.length; i++) {
            Session session = new Session();
            DataUtil.setObject(session, rows[0], rows[i]);
            sessions.add(session);
        }
        return sessions;
    }	
    
    public Session getSession(int sessionId) {

        String dataString = DataUtil.loadFile("dataFiles/session.txt");
        String[] rows = dataString.split(";");
        Session session = new Session();
        DataUtil.setObject(session, rows[0], rows[1]);

        return session;

    }

    @Override
    public ArrayList<Session> getAll() {
        return null;
    }

    @Override
    public ArrayList<Session> getAllValid() {
        return null;
    }

    public ArrayList<Session> getByClassId(int classId) {
        ArrayList<Session> sessionList = new ArrayList<>();
        for (Session session : this.allValidSessions) {
            if (session.classId == classId)
                sessionList.add(session);
        }
        return sessionList;
    }

    @Override
    public Session get(int id) {
        return null;
    }

    @Override
    public void add(Session item) {

    }

    @Override
    public void update(Session item) {

    }

    @Override
    public void delete(int id) {

    }
}
