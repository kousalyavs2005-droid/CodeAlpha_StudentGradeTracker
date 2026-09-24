package com.studentgradetracker.service;

import com.studentgradetracker.model.Student;

public class GradeService {

    public int calculateTotal(Student s) {
        return s.getJava() + s.getPython() + s.getSql()
                + s.getHtml() + s.getMachineLearning();
    }

    public double calculateAverage(Student s) {
        return calculateTotal(s) / 5.0;
    }

    public String calculateGrade(double average) {
        if (average >= 90) return "A+";
        if (average >= 80) return "A";
        if (average >= 70) return "B";
        if (average >= 60) return "C";
        if (average >= 50) return "D";
        return "F";
    }

    public void calculateAndApply(Student s) {
        int total = calculateTotal(s);
        double average = calculateAverage(s);
        s.setTotal(total);
        s.setAverage(average);
        s.setGrade(calculateGrade(average));
    }
}
