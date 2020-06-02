package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.model.Teacher;
import edu.pdsw.mobiletest.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("api/v1/teacher")
@RestController
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/set")
    public void setTeacher(@RequestBody TeacherContext teacherContext) {
        try {
            this.teacherService.setTeacher(teacherContext);
        } catch (TeacherService.WrongPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        }
    }

    @GetMapping("/get")
    public Teacher getTeacher() {
        return this.teacherService.getTeacher();
    }
}
