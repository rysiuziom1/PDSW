package edu.pdsw.mobiletest.service;

import edu.pdsw.mobiletest.dao.KnowledgeTestDao;
import edu.pdsw.mobiletest.model.Exercise;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KnowledgeTestService {
    private final KnowledgeTestDao knowledgeTestDao;

    public KnowledgeTestService(@Qualifier("testRep") KnowledgeTestDao knowledgeTestDao) {
        this.knowledgeTestDao = knowledgeTestDao;
    }

    public void setTest(KnowledgeTest knowledgeTest) throws EmptyDirectoryException {
        if (knowledgeTest.getExercises() == null) {
            throw new EmptyDirectoryException("Exercises directory cannot be empty");
        }
        this.knowledgeTestDao.setKnowledgeTest(knowledgeTest);
    }

    public KnowledgeTest getTest() {
        return this.knowledgeTestDao.getKnowledgeTest();
    }

    public Exercise getExercise(UUID exerciseID) {
        return this.knowledgeTestDao.getExercise(exerciseID);
    }

    public Exercise getRandomExercise() {
        return this.knowledgeTestDao.getRandomExercise();
    }

    public List<Exercise> getAllExercises() {
        return this.knowledgeTestDao.getAllExercises();
    }
    
    public double getTotalTime() {
        return this.knowledgeTestDao.getTotalTime();
    }

    public static class EmptyDirectoryException extends Exception {
        public EmptyDirectoryException(String message) {
            super(message);
        }
    }
}
