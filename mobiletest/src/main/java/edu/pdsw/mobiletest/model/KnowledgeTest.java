package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class KnowledgeTest {
    private final int totalTestTime;
    private final String solutionsAbsolutePath;
    private final String exercisesAbsolutePath;
    private List<Exercise> exercises;

    public KnowledgeTest(@JsonProperty("totalTestTime") int totalTestTime, 
                         @JsonProperty("solutionsAbsolutePath") String solutionsAbsolutePath, 
                         @JsonProperty("exercisesAbsolutePath") String exercisesAbsolutePath) {
        this.totalTestTime = totalTestTime;
        this.solutionsAbsolutePath = solutionsAbsolutePath;
        this.exercisesAbsolutePath = exercisesAbsolutePath;
        createExerciseList();
    }
    
    private void createExerciseList() {
        File directory = new File(this.exercisesAbsolutePath);
        System.out.println(directory.getAbsolutePath());
        if (!directory.isDirectory()) {
            System.out.println(directory.getAbsolutePath() + " is not directory");
            return;
        }
        var arrayOfFiles = directory.listFiles();
        if(arrayOfFiles == null) {
            System.out.println("Empty directory");
            return;
        }
        exercises = new ArrayList<>();
        var listOfFiles = Arrays.asList(arrayOfFiles);
        listOfFiles.forEach(ex -> {
            if (ex.isFile()) {
                exercises.add(new Exercise(ex.getAbsolutePath()));
            }
        });
    }

    public int getTotalTestTime() {
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
