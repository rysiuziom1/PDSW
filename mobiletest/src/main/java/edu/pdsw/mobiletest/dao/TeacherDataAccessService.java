package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository("teacherRep")
public class TeacherDataAccessService implements TeacherDao {
    private static Teacher tr = null;
    
    @Override
    public int setTeacher(Teacher teacher) {
        tr = teacher;
        return 1;
    }

    @Override
    public Teacher getTeacher() {
        return tr;
    }
}
