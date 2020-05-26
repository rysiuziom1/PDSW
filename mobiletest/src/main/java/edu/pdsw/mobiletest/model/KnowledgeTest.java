package edu.pdsw.mobiletest.model;

public class KnowledgeTest {
    private final int totalTestTime;
    private final String solutionsPath;
    private final String tasksPath;

    public KnowledgeTest(int totalTestTime, String solutionsPath, String tasksPath) {
        this.totalTestTime = totalTestTime;
        this.solutionsPath = solutionsPath;
        this.tasksPath = tasksPath;
    }

    public int getTotalTestTime() {
        return totalTestTime;
    }

    public String getSolutionsPath() {
        return solutionsPath;
    }

    public String getTasksPath() {
        return tasksPath;
    }
}
