package edu.pdsw.mobiletest.scheduler;

import edu.pdsw.mobiletest.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateStudentsRemainingTime {
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UpdateStudentsRemainingTime(StudentService studentService) {
        this.studentService = studentService;
    }

    @Scheduled(fixedRate = 1000)
    public void updateTime() {
        studentService.updateTime(-1);
    }
}
