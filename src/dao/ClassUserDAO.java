package dao;

import model.ClassUser;
import utility.DataUtil;

import java.util.ArrayList;

public class ClassUserDAO {

    public ArrayList<ClassUser> getAllClassUsers() {

        String dataString = DataUtil.loadFile("dataFiles/classUser.txt");
        String[] rows = dataString.split(";");
        ArrayList<ClassUser> classUsers = new ArrayList<>();
        for (int i = 1; i < rows.length; i++) {
            ClassUser classUser = new ClassUser();
            DataUtil.setObject(classUser, rows[0], rows[i]);
            classUsers.add(classUser);
        }

        return classUsers;
    }

    public ClassUser getClassUser(int userId, int classId) {

        String dataString = DataUtil.loadFile("dataFiles/classUser.txt");
        String[] rows = dataString.split(";");
        ClassUser classUser = new ClassUser();
        DataUtil.setObject(classUser, rows[0], rows[1]);

        return classUser;
    }

    public ClassUser getClassUser(ArrayList<ClassUser> classUsers, int userId, int classId) {

        ClassUser classUser = null;
        for (ClassUser user : classUsers) {
           if(user.userId == userId && user.classId == classId){
               classUser = user;
               break;
           }
        }

        return classUser;
    }


}
