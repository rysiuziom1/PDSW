package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Teacher;

public interface TeacherDao {
    int setTeacher(Teacher teacher);
    
    Teacher getTeacher();
}
