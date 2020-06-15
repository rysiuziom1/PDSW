package edu.pdsw.mobiletest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/")
    public String login(HttpServletRequest request) {
        if (request.getSession().getAttribute("studentIndex") != null) {
            return "redirect:/student";
        }
        return "login";
    }
}
