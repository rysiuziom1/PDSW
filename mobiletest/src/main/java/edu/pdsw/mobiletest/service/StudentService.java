package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.dao.StudentDao;
import edu.pdsw.mobiletest.exceptions.NoTestException;
import edu.pdsw.mobiletest.exceptions.StudentAlreadyExistsException;
import edu.pdsw.mobiletest.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDao studentDao;

    @Autowired
    public StudentService(@Qualifier("studentRep") StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public int addStudent(Student student, String solutionDirectoryPath) throws StudentAlreadyExistsException {
        if (getStudent(student.getStudentID()) == null && getStudentByIndex(student.getStudentIndex()) == null) {
            studentDao.createStudentsDirectory(student, solutionDirectoryPath);
            return studentDao.insertStudent(student);
        }
        else {
            throw new StudentAlreadyExistsException("Student already exist");
        }
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
    public void finishAllStudentsTests(){studentDao.finishAllStudentsTest();}
    public Student getStudent(UUID studentID) {
        return studentDao.selectStudent(studentID);
    }

    public Student getStudentByIndex(String studentIndex) {
        return studentDao.selectStudentByIndex(studentIndex);
    }

    public List<Student> getAllStudents() {
        return studentDao.selectAllStudents();
    }
    
    public int updateTime(double seconds) {
        return studentDao.updateTime(seconds);
    }

    public void saveStudentFile(String studentIndex, MultipartFile file, String solutionDirectoryPath) { studentDao.saveStudentFile(studentIndex, file, solutionDirectoryPath);}

    public void deleteStudents() {
        studentDao.deleteStudents();
    }

    public Map<String, String> getTestFile(String testFileDirectory) throws IOException, NoTestException {
        return studentDao.getTestFile(testFileDirectory);
    }

    public UUID getExerciseID(String studentIndex) {
        return studentDao.getExerciseID(studentIndex);
    }

    public void logout(UUID studentID) {
        studentDao.logout(studentID);
    }
}
