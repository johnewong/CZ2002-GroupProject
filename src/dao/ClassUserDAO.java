package dao;

import model.ClassUser;
import utility.DataUtility;

import java.util.ArrayList;

public class ClassUserDAO {

    private ArrayList<ClassUser> classUsers = new ArrayList<>();

    public ArrayList<ClassUser> getAllClassUsers(){

        String dataString = DataUtility.loadData("dataFiles/classUser.txt");

        return null;
    }

    public ClassUser getClassUsers(){

        String dataString = DataUtility.loadData("dataFiles/classUser.txt");

        return null;
    }

}
