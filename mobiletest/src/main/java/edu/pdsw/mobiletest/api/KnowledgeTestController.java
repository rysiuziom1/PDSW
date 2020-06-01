package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.model.Exercise;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/teacher/test")
@RestController
public class KnowledgeTestController {
    private final KnowledgeTestService knowledgeTestService;
    
    @Autowired
    public KnowledgeTestController(KnowledgeTestService knowledgeTestService) {
        this.knowledgeTestService = knowledgeTestService;
    }
    
    @PostMapping("/set")
    public void setTest(@RequestBody KnowledgeTest knowledgeTest) {
        this.knowledgeTestService.setTest(knowledgeTest);
    }
    
    @GetMapping("/get")
    public KnowledgeTest getTest() {
        return this.knowledgeTestService.getTest();
    }
    
    @GetMapping("/get_exercise")
    public Exercise getExercise(@RequestBody UUID exerciseID) {
        return this.knowledgeTestService.getExercise(exerciseID);
    }

    @GetMapping("/get_all_exercises")
    public List<Exercise> getAllExercises() {
        return this.knowledgeTestService.getAllExercises();
    }
}
