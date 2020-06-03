package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.model.Student;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import edu.pdsw.mobiletest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService studentService;
    private final KnowledgeTestService knowledgeTestService;
    
    @Autowired
    public StudentController(StudentService studentService, KnowledgeTestService knowledgeTestService) {
        this.studentService = studentService;
        this.knowledgeTestService = knowledgeTestService;
    }

    @PostMapping("/add")
    public void addStudent(@RequestBody Student student) {
        try {
            student.setExercise(knowledgeTestService.getRandomExercise());
            student.setRemainingTime(knowledgeTestService.getTotalTime());
            studentService.addStudent(student);
        } catch (StudentService.StudentAlreadyExistsException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }
    
    @PostMapping("/increase_time")
    public void increaseTime(@RequestBody UUID studentID) {
        studentService.increaseTime(studentID);
    }
    
    @PostMapping("/decrease_time")
    public void decreaseTime(@RequestBody UUID studentID) {
        studentService.decreaseTime(studentID);
    }
    
    @PostMapping("/finish_test")
    public void finishTest(@RequestBody UUID studentID) {
        studentService.finishTest(studentID);
    }
    
    @GetMapping("/get")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    
    @GetMapping("/get_student")
    public Student getStudentByIndex(@RequestParam("index") String studentIndex) {
        return studentService.getStudentByIndex(studentIndex);
    }
}
