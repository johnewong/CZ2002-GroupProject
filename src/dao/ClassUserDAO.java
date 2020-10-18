package dao;

import model.ClassUser;
import utility.DataUtil;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClassUserDAO {
    public ArrayList<ClassUser> _allClassUsers;

    public ClassUserDAO() {
        this._allClassUsers = getAllClassUsers();
    }

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
        for (ClassUser user : this._allClassUsers) {
            if (user.userId == userId && user.classId == classId && !user.isDeleted) {
                return user;
            }
        }
        return null;
    }

    public void addClassUser(ClassUser newClassUser) {
        try {
            ArrayList<ClassUser> classUsers = this._allClassUsers;
            // validation
            for (ClassUser u : classUsers) {
                if (u.classId == newClassUser.classId && u.userId == newClassUser.userId && !u.isDeleted) {
                    throw new Exception("The classUser is already existed");
                }
            }

            classUsers.sort((a, b) -> a.classUserId - b.classUserId);
            newClassUser.classUserId = classUsers.get(classUsers.size() - 1).classUserId + 1;
            classUsers.add(newClassUser);
            DataUtil.writeFile(classUsers, "dataFiles/classUser.txt");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateClassUser(ArrayList<ClassUser> classUsers, ClassUser classUser) {

    }
}
