package edu.pdsw.mobiletest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.pdsw.mobiletest.model.Teacher;

public class TeacherContext {
    private final Teacher teacher;
    private final String password;

    public TeacherContext(@JsonProperty("teacher") Teacher teacher, @JsonProperty("password") String password) {
        this.teacher = teacher;
        this.password = password;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getPassword() {
        return password;
    }
}
