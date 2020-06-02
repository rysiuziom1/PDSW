package edu.pdsw.mobiletest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Teacher {
    private final String firstName;
    private final String lastName;

    public Teacher(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
