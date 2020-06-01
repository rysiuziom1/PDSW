package edu.pdsw.mobiletest.dao;

import edu.pdsw.mobiletest.model.Exercise;
import edu.pdsw.mobiletest.model.KnowledgeTest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Repository("testRep")
public class KnowledgeTestDataAccessService implements KnowledgeTestDao {
    private static KnowledgeTest KT = null;
    private static final Random rand = new Random();
    
    @Override
    public int setKnowledgeTest(KnowledgeTest knowledgeTest) {
        KT = knowledgeTest;
        return 1;
    }

    @Override
    public KnowledgeTest getKnowledgeTest() {
        return KT;
    }

    @Override
    public Exercise getExercise(UUID exerciseID) {
        return KT.getExercises().stream().filter(ex -> exerciseID.equals(ex.getExerciseID())).findAny().orElse(null);
    }

    @Override
    public Exercise getRandomExercise() {
        return KT.getExercises().get(rand.nextInt(KT.getExercises().size()));
    }

    @Override
    public List<Exercise> getAllExercises() {
        return KT.getExercises();
    }
}
