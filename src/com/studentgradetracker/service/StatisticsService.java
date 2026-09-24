package com.studentgradetracker.service;

import java.util.List;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.model.StudentStatistics;

public class StatisticsService {
    private final GradeService gradeService;

    public StatisticsService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public StudentStatistics calculate(List<Student> students) {
        if (students.isEmpty()) {
            return new StudentStatistics(0, 0, null, null, 0, 0);
        }

        double sum = 0;
        int pass = 0;
        Student highest = students.get(0);
        Student lowest = students.get(0);

        for (Student s : students) {
            sum += s.getAverage();
            if (s.getAverage() > highest.getAverage()) highest = s;
            if (s.getAverage() < lowest.getAverage()) lowest = s;
            if (s.isPass()) pass++;
        }

        return new StudentStatistics(
                students.size(),
                sum / students.size(),
                highest,
                lowest,
                pass,
                students.size() - pass
        );
    }

    public int countGrade(List<Student> students, String grade) {
        int count = 0;
        for (Student s : students) {
            if (grade.equals(s.getGrade())) count++;
        }
        return count;
    }

    public String gradeFor(Student student) {
        return gradeService.calculateGrade(student.getAverage());
    }
}
