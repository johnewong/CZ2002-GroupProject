package dao;

import java.util.ArrayList;

import model.Class;


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
		ArrayList<Class> classValid = new ArrayList<>();
		for (Class c : allClass) {
			if (!c.isDeleted)
				classValid.add(c);
		}

		return classValid;
	}

	@Override
	public Class get(int classId) {
		for (Class c : this.allClass) {
			if (c.classId == classId && !c.isDeleted) {
				return c;
			}
		}
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


	public Class get(int classId, int courseId) {
		for (Class c : this.allClass) {
			if (c.classId == classId && c.courseId == courseId && !c.isDeleted) {
				return c;
			}
		}
		return null;
	}

}
	
	
	






