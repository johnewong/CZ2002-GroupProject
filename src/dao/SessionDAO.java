package dao;

import model.Session;
import utility.DataUtil;
import java.util.ArrayList;

public class SessionDAO {

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
}
