package edu.pdsw.mobiletest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pdsw.mobiletest.exceptions.NoTestException;
import edu.pdsw.mobiletest.exceptions.StudentAlreadyExistsException;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import edu.pdsw.mobiletest.model.Student;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import edu.pdsw.mobiletest.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<String> addStudent(@RequestBody Student student, HttpServletRequest httpServletRequest) {
        try {
            var exercise = knowledgeTestService.getRandomExercise();
            if (exercise == null)
                throw new NoTestException("Test isn't already set");

            String studentIp = httpServletRequest.getRemoteAddr();

            String testIp = knowledgeTestService.getIp();

            String [] studentsOctets = studentIp.split("\\.");
            String [] testsOctets = testIp.split("\\.");
            int lastStudentsOctet = 1;
            if(!studentsOctets[studentsOctets.length-1].contains(":1"))
                    lastStudentsOctet = Integer.parseInt(studentsOctets[studentsOctets.length-1]);

            int lastTestsOctet = Integer.parseInt(testsOctets[testsOctets.length-1]);
            var exercises = knowledgeTestService.getAllExercises();
            int numberOfExercise = (Math.abs(lastStudentsOctet - lastTestsOctet)%exercises.size());

            exercise = exercises.get(numberOfExercise);
            
            student.setExerciseID(exercise.getExerciseID());
            student.setRemainingTime(knowledgeTestService.getTotalTime());
            studentService.addStudent(student, knowledgeTestService.getSolutionsPath());
            logger.info(String.format("Student [%s, %s, %s] successfully added.", student.getFirstName(), student.getLastName(), student.getStudentIndex()));
        } catch (StudentAlreadyExistsException ex) {
            logger.warn(String.format("Student [%s, %s, %s] already exists.", student.getFirstName(), student.getLastName(), student.getStudentIndex()));
//            throw new ResponseStatusException(HttpStatus.OK, ex.getMessage(), ex);
            var dbStudent = studentService.getStudentByIndex(student.getStudentIndex());
            if (dbStudent.isLogged()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
            }
            dbStudent.setLogged(true);
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
        studentService.logout(studentID);
        request.getSession().removeAttribute("studentIndex");
    }

    @PostMapping("/finish_test_as_teacher")
    public void finishTest(@RequestBody UUID studentID) {
        studentService.finishTest(studentID);
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
        Student student = getStudentByIndex((String) studentIndex);
        if (!student.isFinishedTest()) {
            studentService.saveStudentFile((String) studentIndex, file, knowledgeTestService.getSolutionsPath());
            student.setSolutionSent(true);
        } else {
            logger.warn(String.format("Student [%s, %s, %s] already finished test, can't sent solution", student.getFirstName(), student.getLastName(), student.getStudentIndex()));
        }
    }


    @GetMapping("/get_file")
    public Map<String, String> getTestFile() {
        Map<String, String> objectMap;

        try {
            objectMap = studentService.getTestFile(knowledgeTestService.getExercisesPath());
        } catch (NoTestException e) {
            logger.warn("Error while trying to get a test file, there are no files in test directory.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (IOException e) {
            logger.warn("Error while handling test file.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        return objectMap;
    }

    @PostMapping("/request_time")
    public void setRequestedTimeTrue(@RequestBody UUID studentID){
        studentService.getStudent(studentID).setRequestTime(true);
    }

    @GetMapping("/get_exercise")
    public ResponseEntity getExercise(@RequestParam("index") String studentIndex) {
        UUID exerciseID = studentService.getExerciseID(studentIndex);
        Path exercisePath = Paths.get(knowledgeTestService.getExercise(exerciseID).getExerciseAbsolutePath());
        Resource resource = null;
        try {
            resource = new UrlResource(exercisePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .body(resource);
    }
}
