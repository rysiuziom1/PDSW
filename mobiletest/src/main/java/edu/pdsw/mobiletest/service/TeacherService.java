package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.api.TeacherContext;
import edu.pdsw.mobiletest.dao.TeacherDao;
import edu.pdsw.mobiletest.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherDao teacherDao;
    private final String password = "password";

    @Autowired
    public TeacherService(@Qualifier("teacherRep") TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public int setTeacher(TeacherContext teacherContext) throws WrongPasswordException {
        if (!teacherContext.getPassword().equalsIgnoreCase(password)) {
            throw new WrongPasswordException("Wrong password");
        }
        return this.teacherDao.setTeacher(teacherContext.getTeacher());
    }

    public Teacher getTeacher() {
        return this.teacherDao.getTeacher();
    }

    public static class WrongPasswordException extends Exception {
        public WrongPasswordException(String message) {
            super(message);
        }
    }
}
