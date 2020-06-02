package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.dao.TeacherDao;
import edu.pdsw.mobiletest.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(@Qualifier("teacherRep") TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }
    
    public int setTeacher(Teacher teacher) {
        return this.teacherDao.setTeacher(teacher);
    }
    
    public Teacher getTeacher() {
        return this.teacherDao.getTeacher();
    }
}
