package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Student {
    private final UUID studentID;
    private final String studentIndex;
    private final String firstName;
    private final String lastName;
    private double remainingTime;
    private UUID exerciseID;
    private boolean requestTime;
    private boolean solutionSent;
    private boolean finishedTest;
    private boolean logged;

    public Student(@JsonProperty("id") UUID id,
                   @JsonProperty("studentIndex") String studentIndex,
                   @JsonProperty("firstName") String firstName,
                   @JsonProperty("lastName") String lastName) {
        this.studentID = id;
        this.studentIndex = studentIndex;
        this.firstName = firstName;
        this.lastName = lastName;
        this.remainingTime = 90.0;
        this.requestTime = false;
        this.solutionSent = false;
        this.finishedTest = false;
        this.logged = true;
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

    public UUID getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(UUID exerciseID) {
        this.exerciseID = exerciseID;
    }

    public void setRequestTime(boolean requestTime) {
        this.requestTime = requestTime;
    }

    public boolean getRequestTime() {
        return requestTime;
    }

    public boolean getSolutionSent() {
        return solutionSent;
    }

    public void setSolutionSent(boolean solutionSended) {
        this.solutionSent = solutionSended;
    }

    public boolean isFinishedTest() {
        return finishedTest;
    }

    public void setFinishedTest(boolean finishedTest) {
        this.finishedTest = finishedTest;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
