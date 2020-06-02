package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.dao.StudentDao;
import edu.pdsw.mobiletest.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDao studentDao;

    @Autowired
    public StudentService(@Qualifier("studentRep") StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public int addStudent(Student student) {
        return studentDao.insertStudent(student);
    }
    
    public int increaseTime(UUID studentID) {
        return studentDao.increaseTime(studentID);
    }
    
    public int decreaseTime(UUID studentID) {
        return studentDao.decreaseTime(studentID);
    }
    
    public int finishTest(UUID studentID) {
        return studentDao.finishTest(studentID);
    }

    public List<Student> getAllStudents() {
        return studentDao.selectAllStudents();
    }
}
