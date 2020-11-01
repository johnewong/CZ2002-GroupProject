package dao;

import model.Class;
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

    @Override
    public ArrayList<Session> getAll() {
        String dataString = DataUtil.loadFile("session.txt");
        String[] rows = dataString.split(";");
        ArrayList<Session> sessions = new ArrayList<>();
        for (int i = 1; i < rows.length; i++) {
            Session s = new Session();
            DataUtil.setObject(s, rows[0], rows[i]);
            Session.add(s);
        }

        return sessions;
    }

    @Override
    public ArrayList<Session> getAllValid() {
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
        return null;
    }

    @Override
    public void add(Session item) {

    }

    @Override
    public void update(Session session) {
        Session existedSession = this.get(session.sessionId);
        existedSession.classId = session.classId;
    }

    @Override
    public void delete(int sessionId) {
        Session existedSession = this.get(sessionId);
        existedSession.isDeleted = true;
    }
}
