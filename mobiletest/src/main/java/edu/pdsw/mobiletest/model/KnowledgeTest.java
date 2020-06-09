package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnowledgeTest {
    private final double totalTestTime;
    private final String solutionsAbsolutePath;
    private final String exercisesAbsolutePath;
    private List<Exercise> exercises;

    private Logger logger = LoggerFactory.getLogger(KnowledgeTest.class);

    public KnowledgeTest(@JsonProperty("totalTestTime") double totalTestTime,
                         @JsonProperty("solutionsAbsolutePath") String solutionsAbsolutePath,
                         @JsonProperty("exercisesAbsolutePath") String exercisesAbsolutePath) {
        this.totalTestTime = totalTestTime;
        this.solutionsAbsolutePath = solutionsAbsolutePath;
        this.exercisesAbsolutePath = exercisesAbsolutePath;
        createExerciseList();
    }

    private void createExerciseList() {
        File directory = new File(this.exercisesAbsolutePath);
        if (!directory.isDirectory()) {
//           logger.warn(directory.getAbsolutePath() + " is not directory");
            return;
        }
        var arrayOfFiles = directory.listFiles();
        if (arrayOfFiles == null) {
//            logger.warn(directory.getAbsolutePath() + " is empty directory");
            return;
        }
        exercises = new ArrayList<>();
        var listOfFiles = Arrays.asList(arrayOfFiles);
        listOfFiles.forEach(ex -> {
            if (ex.isFile()) {
                exercises.add(new Exercise(ex.getAbsolutePath()));
            }
        });
//        logger.info("Exercises directory path: " + directory.getAbsolutePath());
    }

    public double getTotalTestTime() {
        return totalTestTime;
    }

    public String getSolutionsAbsolutePath() {
        return solutionsAbsolutePath;
    }

    public String getExercisesAbsolutePath() {
        return exercisesAbsolutePath;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
