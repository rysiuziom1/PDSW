package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.model.Student;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import edu.pdsw.mobiletest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        student.setExercise(knowledgeTestService.getRandomExercise());
        studentService.addStudent(student);
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
}
