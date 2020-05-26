package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("inMemoryDB")
public class StudentDataAccessService implements StudentDao {

    private static List<Student> DB = new ArrayList<>();

    @Override
    public int insertStudent(UUID id, Student student) {
        DB.add(new Student(id, student.getStudentIndex(), student.getFirstName(), student.getLastName()));
        return 1;
    }

    @Override
    public List<Student> selectAllStudents() {
        return DB;
    }
}
