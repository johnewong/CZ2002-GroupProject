package dao;

import java.util.ArrayList;

import model.Class;

import model.ClassUser;
import utility.DataUtil;

public class ClassDAO implements IDAO<Class> {

    private ArrayList<Class> allClass;
    private ArrayList<Class> allValidClass;


//
//    public ArrayList<Class> getAl() {
//        String dataString = DataUtil.loadFile("class.txt");
//        String[] rows = dataString.split(";");
//        ArrayList<Class> Class = new ArrayList<>();
//        for (int i = 1; i < rows.length; i++) {
//            Class c = new Class();
//            DataUtil.setObject(c, rows[0], rows[i]);
//            Class.add(c);
//        }
//
//        return Class;
//    }
    public ClassDAO() {
    this.allClass = getAll();
    this.allValidClass = getAllValid();
}

    public Class getClass(int classId, int courseId) {
        String dataString = DataUtil.loadFile("class.txt");
        String[] rows = dataString.split(";");
        Class c = new Class();
        DataUtil.setObject(c, rows[0], rows[1]);

        return c;
    }


    @Override
    public ArrayList<Class> getAll() {
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

    @Override
    public ArrayList<Class> getAllValid() {
        ArrayList<Class> cls = new ArrayList<>();
        for (Class c : allClass) {
            if (!c.isDeleted)
                cls.add(c);
        }

        return cls;
    }

    public ArrayList<Class> getByCourseId(int courseId) {
		ArrayList<Class> classList = new ArrayList<>();
		for(Class cls: this.allValidClass){
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
    public void update(Class cls) {
        Class existedClass = this.get(cls.classId);
        existedClass.classType = cls.classType;

    }

    @Override
    public void delete(int classId) {
        Class existedClass = this.get(classId);
        existedClass.isDeleted = true;

    }
}
