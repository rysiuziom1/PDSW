package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("studentRep")
public class StudentDataAccessService implements StudentDao {

    private static List<Student> DB = new ArrayList<>();

    @Override
    public int insertStudent(UUID id, Student student) {
        DB.add(new Student(id, student.getStudentIndex(), student.getFirstName(), student.getLastName()));
        return 1;
    }

    @Override
    public int increaseTime(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student == null) {
            return 0;
        } else {
            student.setRemainingTime(student.getRemainingTime() + 5.0);
        }
        return 1;
    }

    @Override
    public int decreaseTime(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student == null) {
            return 0;
        } else {
            student.setRemainingTime(student.getRemainingTime() - 5.0);
        }
        return 1;
    }

    @Override
    public int finishTest(UUID studentID) {
        var student = DB.stream().filter(s -> studentID.equals(s.getStudentID())).findAny().orElse(null);
        if (student == null) {
            return 0;
        } else {
            student.setRemainingTime(0.0);
        }
        return 1;
    }

    @Override
    public List<Student> selectAllStudents() {
        return DB;
    }
}
