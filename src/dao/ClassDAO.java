package dao;

import java.util.ArrayList;

import model.Class;

import model.ClassUser;
import utility.DataUtil;

public class ClassDAO implements IDAO<Class> {

    private ArrayList<Class> allClass;
    private ArrayList<Class> allValidClass;
    
    public ClassDAO() {
    this.allClass = getAll();
    this.allValidClass = getAllValid();
}


    @Override
    public ArrayList<Class> getAll() {
        String dataString = DataUtil.loadFile("dataFiles/class.txt");
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
    public Class get(int classId ) {
        for (Class cls : this.allValidClass){
            if (cls.classId == classId){
                return cls;
            }
        }
        return null;
    }

    @Override
    public void add(Class newCls) {
        try {
            ArrayList<Class> cls = this.allClass;
            // validation
            for (Class u : cls) {
                if (u.classId == newCls.classId  && !u.isDeleted) {
                    throw new Exception("The class is already existed");
                }
            }

            cls.sort((a, b) -> a.classId - b.classId);
            newCls.classId = cls.get(cls.size() - 1).classId + 1;
            cls.add(newCls);
            DataUtil.writeFile(cls, "dataFiles/class.txt");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Class cls) {
        Class existedClass = this.get(cls.classId);
    }

    @Override
    public void delete(int classId) {
        Class existedClass = this.get(classId);
        existedClass.isDeleted = true;

    }
}
