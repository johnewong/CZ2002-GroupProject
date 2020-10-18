# CZ2002-GroupProject

Install source tree from https://www.sourcetreeapp.com/

Clone code from remote with this url: https://github.com/johnewong/CZ2002-GroupProject.git


model module: The data model.

dao module: The module is to retrieve, add, update, delete data from source. There is no business logic in this module.

service module: The business logic and program features are achieved in this module. 

utility module: The utility functions



Main -> login -> Stu/Admin -> logout

Login			LoginService  (XYan 笑妍) 
Change password 
Logout
Check/Print Courses Registered StudentService

Add Course -> validation  vancancy,duplicated register(same index, same course diff index) 		 StudentService (Jeremy) 
Drop Course 

Change Index Number of Course    StudentService （逸飞）
Swop Index Number with Another Student

Edit student access period   AdminService (XYE 蔡钰)
Add a student (name, matric number, gender, nationality, etc)  
Add/Update a course (course code, school, its index numbers and vacancy) -> validate duplicated courseCode index.

Check Vacancies Available 
Check available slot for an index number (vacancy in a class).  AdminService (智勇)
Print student list by index number.
Print student list by course (all students registered for the selected course).
