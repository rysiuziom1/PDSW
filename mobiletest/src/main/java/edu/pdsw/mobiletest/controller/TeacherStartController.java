package edu.pdsw.mobiletest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherStartController {
    @GetMapping("/teacher")
    public String index() {
        return "teacher";
    }
}
