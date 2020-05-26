package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentDao {
    int insertStudent(UUID id, Student student);

    default int insertStudent(Student student) {
        UUID id = UUID.randomUUID();
        return insertStudent(id, student);
    }

    List<Student> selectAllStudents();
}
