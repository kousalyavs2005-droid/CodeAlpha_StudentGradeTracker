package com.studentgradetracker.model;

public class StudentStatistics {
    private final int totalStudents;
    private final double classAverage;
    private final Student highestStudent;
    private final Student lowestStudent;
    private final int passCount;
    private final int failCount;

    public StudentStatistics(int totalStudents, double classAverage,
                             Student highestStudent, Student lowestStudent,
                             int passCount, int failCount) {
        this.totalStudents = totalStudents;
        this.classAverage = classAverage;
        this.highestStudent = highestStudent;
        this.lowestStudent = lowestStudent;
        this.passCount = passCount;
        this.failCount = failCount;
    }

    public int getTotalStudents() { return totalStudents; }
    public double getClassAverage() { return classAverage; }
    public Student getHighestStudent() { return highestStudent; }
    public Student getLowestStudent() { return lowestStudent; }
    public int getPassCount() { return passCount; }
    public int getFailCount() { return failCount; }
}
