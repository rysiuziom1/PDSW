package edu.pdsw.mobiletest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class StudentTestController {
    @GetMapping("/student")
    public String test(@RequestParam Map<String, String> parameters, HttpServletRequest request) {
        if (parameters.get("index") == null && request.getSession().getAttribute("studentIndex") == null) {
            return "redirect:/";
        } else if (request.getSession().getAttribute("studentIndex") == null) {
            var index = parameters.get("index");
            request.getSession().setAttribute("studentIndex", index);
            return "student";
        } else {
            return "student";
        }
    }
}
