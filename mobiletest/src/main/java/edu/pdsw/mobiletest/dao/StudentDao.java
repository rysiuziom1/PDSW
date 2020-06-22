package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.KnowledgeTest;
import edu.pdsw.mobiletest.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface StudentDao {
    int insertStudent(UUID id, Student student);
    int increaseTime(UUID uuid);
    int decreaseTime(UUID uuid);
    int finishTest(UUID uuid);
    void finishAllStudentsTest();
    default int insertStudent(Student student) {
        UUID id = UUID.randomUUID();
        return insertStudent(id, student);
    }
    
    Student selectStudent(UUID uuid);
    Student selectStudentByIndex(String studentIndex);
    List<Student> selectAllStudents();
    int updateTime(double seconds);
    void saveStudentFile(String index, MultipartFile file, String solutionDirectoryPath);
    void createStudentsDirectory(Student student, String solutionDirectoryPath);
    void deleteStudents();
}
