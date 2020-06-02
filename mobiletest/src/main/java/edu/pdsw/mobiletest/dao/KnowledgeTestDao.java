package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Exercise;
import edu.pdsw.mobiletest.model.KnowledgeTest;

import java.util.List;
import java.util.UUID;

public interface KnowledgeTestDao {
    int setKnowledgeTest(KnowledgeTest knowledgeTest);
    KnowledgeTest getKnowledgeTest();
    Exercise getExercise(UUID exerciseID);
    Exercise getRandomExercise();

    List<Exercise> getAllExercises();
}
