package dao;

import java.util.ArrayList;

import model.Class;

import utility.DataUtil;

public class ClassDAO implements IDAO<Class> {

    private ArrayList<Class> allClasses;
    private ArrayList<Class> allValidClasses;



    public ArrayList<Class> getAllClass() {
        String dataString = DataUtil.loadFile("class.txt");
        String[] rows = dataString.split(";");
        ArrayList<Class> Class = new ArrayList<>();
        for (int i = 1; i < rows.length; i++) {
            Class c = new Class();
            DataUtil.setObject(c, rows[0], rows[i]);
            Class.add(c);
        }

        return Class;
    }

    public Class getClass(int classId, String courseId) {
        String dataString = DataUtil.loadFile("class.txt");
        String[] rows = dataString.split(";");
        Class c = new Class();
        DataUtil.setObject(c, rows[0], rows[1]);

        return c;
    }


    @Override
    public ArrayList<Class> getAll() {
        return null;
    }

    @Override
    public ArrayList<Class> getAllValid() {
        return null;
    }

    public ArrayList<Class> getByCourseId(int courseId) {
		ArrayList<Class> classList = new ArrayList<>();
		for(Class cls: this.allValidClasses){
		    if(cls.courseId == courseId)
		        classList.add(cls);
        }

        return classList;
    }

    @Override
    public Class get(int id) {
        return null;
    }

    @Override
    public void add(Class item) {

    }

    @Override
    public void update(Class item) {

    }

    @Override
    public void delete(int id) {

    }
}
