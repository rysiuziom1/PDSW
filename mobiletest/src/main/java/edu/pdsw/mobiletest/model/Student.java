package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Student {
    private final UUID studentID;
    private final String studentIndex;
    private final String firstName;
    private final String lastName;
    private double remainingTime;
    private Exercise exercise;

    public Student(@JsonProperty("id") UUID id,
                   @JsonProperty("studentIndex") String studentIndex,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName) {
        this.studentID = id;
        this.studentIndex = studentIndex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.remainingTime = 90.0;
    }

    public UUID getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
