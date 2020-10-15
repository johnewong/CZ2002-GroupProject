package dao;

import java.util.ArrayList;

import model.Class;

import utility.DataUtil;

public class ClassDAO {

	public ArrayList<Class> getAllClass(){
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
	
	public Class getClass(int classId,String courseId) {
		String dataString = DataUtil.loadFile("class.txt");
		String[] rows = dataString.split(";");
		Class c = new Class();
		DataUtil.setObject(c, rows[0], rows[1]);
		
		return c;
	}
	
	
	

}
