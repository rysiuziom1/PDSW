package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Exercise {
    private final UUID exerciseID;
    private final String exerciseAbsolutePath;

    public Exercise(UUID exerciseID, String exerciseAbsolutePath) {
        this.exerciseID = exerciseID;
        this.exerciseAbsolutePath = exerciseAbsolutePath;
    }
    
    public Exercise(String exerciseAbsolutePath) {
        this.exerciseID = UUID.randomUUID();
        this.exerciseAbsolutePath = exerciseAbsolutePath;
    }

    public String getExerciseAbsolutePath() {
        return exerciseAbsolutePath;
    }

    public UUID getExerciseID() {
        return exerciseID;
    }
}
