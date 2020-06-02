package edu.pdsw.mobiletest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentTestController {
    @GetMapping("/login/student")
    public String test() {
        return "student";
    }
}
