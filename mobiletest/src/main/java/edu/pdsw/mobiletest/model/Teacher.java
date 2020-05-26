package edu.pdsw.mobiletest.model;

public class Teacher {
    private final String firstName;
    private final String lastName;

    public Teacher(String name, String lastName) {
        this.firstName = name;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
