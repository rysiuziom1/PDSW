package edu.pdsw.mobiletest.api;

import edu.pdsw.mobiletest.exceptions.DirectoryException;
import edu.pdsw.mobiletest.model.Exercise;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import edu.pdsw.mobiletest.service.KnowledgeTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/teacher/test")
@RestController
public class KnowledgeTestController {
    private final KnowledgeTestService knowledgeTestService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public KnowledgeTestController(KnowledgeTestService knowledgeTestService) {
        this.knowledgeTestService = knowledgeTestService;
    }
    
    @PostMapping("/set")
    public void setTest(@RequestBody KnowledgeTest knowledgeTest) {
        try {
            this.knowledgeTestService.setTest(knowledgeTest);
            logger.info(String.format("%s added successfully.", this.knowledgeTestService.getExercisesPath()));
        } catch (DirectoryException ex) {
            logger.info(String.format("%s is not a directory or is empty.", this.knowledgeTestService.getExercisesPath()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }
    
    @GetMapping("/get")
    public KnowledgeTest getTest() {
        return this.knowledgeTestService.getTest();
    }

    @GetMapping("/get_ip")
    public String getIp(){try(final DatagramSocket socket = new DatagramSocket()){
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        String ip=socket.getLocalAddress().getHostAddress();
        this.knowledgeTestService.setIp(ip);
        return ip;
    } catch (SocketException | UnknownHostException e) {
        e.printStackTrace();
    }
    return "Nie można pobrać adresu";
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
