package edu.pdsw.mobiletest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentListController {
    @GetMapping("/students_list")
    public String login() {
        return "students_list";
    }
}
