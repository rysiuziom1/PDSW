package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.exceptions.NoTestException;
import edu.pdsw.mobiletest.exceptions.StudentAlreadyExistsException;
import edu.pdsw.mobiletest.model.Student;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import edu.pdsw.mobiletest.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService studentService;
    private final KnowledgeTestService knowledgeTestService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StudentController(StudentService studentService, KnowledgeTestService knowledgeTestService) {
        this.studentService = studentService;
        this.knowledgeTestService = knowledgeTestService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            var exercise = knowledgeTestService.getRandomExercise();
            if (exercise == null)
                throw new NoTestException("Test isn't already set");
            student.setExerciseID(exercise.getExerciseID());
            student.setRemainingTime(knowledgeTestService.getTotalTime());
            studentService.addStudent(student);
            logger.info(String.format("Student [%s, %s, %s] successfully added.", student.getFirstName(), student.getLastName(), student.getStudentIndex()));
        } catch (StudentAlreadyExistsException ex) {
            logger.warn(String.format("Student [%s, %s, %s] already exists.", student.getFirstName(), student.getLastName(), student.getStudentIndex()));
//            throw new ResponseStatusException(HttpStatus.OK, ex.getMessage(), ex);
            return new ResponseEntity<>("student?index=" + student.getStudentIndex(), HttpStatus.OK);
        } catch (NoTestException e) {
            logger.warn(String.format("Student [%s, %s, %s] can't be added, because test isn't set yet.",
                    student.getFirstName(),
                    student.getLastName(),
                    student.getStudentIndex()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        return new ResponseEntity<>("student?index=" + student.getStudentIndex(), HttpStatus.OK);
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
    public void finishTest(@RequestBody UUID studentID, HttpServletRequest request) {
        studentService.finishTest(studentID);
        request.getSession().removeAttribute("studentIndex");
    }
    @PostMapping("/finish_tests")
    public void finishAllStudentsTests(){
        studentService.finishAllStudentsTests();
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
