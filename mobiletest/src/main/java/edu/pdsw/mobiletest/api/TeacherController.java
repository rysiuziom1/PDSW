package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.exceptions.WrongPasswordException;
import edu.pdsw.mobiletest.model.Teacher;
import edu.pdsw.mobiletest.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("api/v1/teacher")
@RestController
public class TeacherController {
    private final TeacherService teacherService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/set")
    public void setTeacher(@RequestBody TeacherContext teacherContext) {
        try {
            this.teacherService.setTeacher(teacherContext);
            logger.info(String.format("Teacher [%s, %s] set successfully.", teacherContext.getTeacher(), teacherContext.getTeacher().getLastName()));
        } catch (WrongPasswordException ex) {
            logger.info(String.format("Teacher [%s, %s] can't be send due to passing wrong password.",
		            teacherContext.getTeacher(),
		            teacherContext.getTeacher().getLastName()));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        }
    }

    @GetMapping("/get")
    public Teacher getTeacher() {
        return this.teacherService.getTeacher();
    }
}
