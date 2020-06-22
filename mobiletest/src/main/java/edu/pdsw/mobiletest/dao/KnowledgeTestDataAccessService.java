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
        if (KT == null)
            return null;
        return KT.getExercises().stream().filter(ex -> exerciseID.equals(ex.getExerciseID())).findAny().orElse(null);
    }

    @Override
    public Exercise getRandomExercise() {
        if (KT == null)
            return null;
        return KT.getExercises().get(rand.nextInt(KT.getExercises().size()));
    }

    @Override
    public double getTotalTime() {
        if (KT == null)
            return 0.0;
        return KT.getTotalTestTime();
    }

    @Override
    public String getExercisesDirectoryPath() {
        if (KT == null)
            return null;
        return KT.getExercisesAbsolutePath();
    }

    @Override
    public String getSolutionDirectoryPath() {
        if (KT == null)
            return null;
        return KT.getSolutionsAbsolutePath();
    }

    @Override
    public List<Exercise> getAllExercises() {
        if (KT == null)
            return null;
        return KT.getExercises();
    }
}
