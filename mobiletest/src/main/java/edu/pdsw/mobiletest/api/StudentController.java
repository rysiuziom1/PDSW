package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.exceptions.NoTestException;
import edu.pdsw.mobiletest.exceptions.StudentAlreadyExistsException;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import edu.pdsw.mobiletest.model.Student;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import edu.pdsw.mobiletest.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
            studentService.addStudent(student, knowledgeTestService.getSolutionsPath());
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
        studentService.getStudent(studentID).setRequestTime(false);
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

    @PostMapping("/upload_file")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Object studentIndex = request.getSession().getAttribute("studentIndex");
        studentService.saveStudentFile((String) studentIndex, file, knowledgeTestService.getSolutionsPath());
        studentService.getStudentByIndex((String) studentIndex).setSolutionSent(true);
    }

    @GetMapping(
            value = "/get_file",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] getTestFile() {
        byte[] testFile;

        try {
            testFile = studentService.getTestFile(knowledgeTestService.getExercisesPath());
        } catch (NoTestException e) {
            logger.warn("Error while trying to get a test file, there are no files in test directory.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (IOException e) {
            logger.warn("Error while handling test file.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        return testFile;
    }

    @PostMapping("/request_time")
    public void setRequestedTimeTrue(@RequestBody UUID studentID){
        studentService.getStudent(studentID).setRequestTime(true);
    }

}
