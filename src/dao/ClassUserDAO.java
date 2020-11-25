package dao;

import model.ClassUser;
import utility.DataUtil;
import utility.StatusEnum;

import java.util.ArrayList;

public class ClassUserDAO implements IDAO<ClassUser> {
    private ArrayList<ClassUser> allClassUsers;
    private ArrayList<ClassUser> allValidClassUsers;

    // constructor
    public ClassUserDAO() {
        this.allClassUsers = getAll();
        this.allValidClassUsers = getAllValid();
    }

    @Override
    public ArrayList<ClassUser> getAll() {
        if(this.allClassUsers != null){
            return this.allClassUsers;
        }

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

    @Override
    public ArrayList<ClassUser> getAllValid() {
        if(this.allValidClassUsers != null){
            return this.allValidClassUsers;
        }

        ArrayList<ClassUser> classUsers = new ArrayList<>();
        for (ClassUser user : allClassUsers) {
            if (!user.isDeleted)
                classUsers.add(user);
        }

        return classUsers;
    }

    public ArrayList<ClassUser> getByClassId(int classId){
        ArrayList<ClassUser> classUsers = new ArrayList<>();
        for (ClassUser user : allValidClassUsers) {
            if(user.classId == classId && user.status == StatusEnum.REGISTERED.toInt())
                classUsers.add(user);
        }

        return classUsers;
    }

    @Override
    public ClassUser get(int classUserId) {
        for (ClassUser user : this.allValidClassUsers) {
            if (user.classUserId == classUserId) {
                return user;
            }
        }
        return null;
    }

    public ClassUser get(int userId, int classId) {
        for (ClassUser user : this.allValidClassUsers) {
            if (user.userId == userId && user.classId == classId) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(ClassUser newClassUser) {
        try {
            ArrayList<ClassUser> classUsers = this.allClassUsers;
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

    @Override
    public void update(ClassUser classUser) {
        ClassUser existedClassUser = this.get(classUser.classUserId);
        if(existedClassUser !=null){
            existedClassUser.status = classUser.status;
            DataUtil.writeFile(this.allClassUsers, "dataFiles/classUser.txt");
        }
        else {
            System.out.println("Class user not found");
        }
    }

    @Override
    public void delete(int classUserId) {
        ClassUser existedClassUser = this.get(classUserId);
        if(existedClassUser != null){
            existedClassUser.isDeleted = true;
            DataUtil.writeFile(this.allClassUsers, "dataFiles/classUser.txt");
        }
        else {
            System.out.println("Class user not found");
        }
    }
}
