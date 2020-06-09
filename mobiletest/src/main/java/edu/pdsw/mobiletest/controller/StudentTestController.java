package edu.pdsw.mobiletest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class StudentTestController {
    @GetMapping("/login/student")
    public String test(@RequestParam Map<String, String> parameters) {
        return "student";
    }
}
