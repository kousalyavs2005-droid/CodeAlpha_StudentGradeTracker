package com.studentgradetracker.controller;

import java.util.List;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.model.StudentStatistics;
import com.studentgradetracker.service.StatisticsService;
import com.studentgradetracker.service.StudentService;
import com.studentgradetracker.view.DashboardFrame;

public class AppController {
    private final StudentService studentService;
    private final StatisticsService statisticsService;

    public AppController(StudentService studentService,
                         StatisticsService statisticsService) {
        this.studentService = studentService;
        this.statisticsService = statisticsService;
    }

    public boolean login(String username, String password) {
        return "admin".equals(username) && "admin123".equals(password);
    }

    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    public List<Student> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getStudents();
        }
        return studentService.search(query);
    }

    public Student findById(String id) {
        return studentService.findById(id);
    }

    public void addStudent(Student student) {
        studentService.addStudent(student);
    }

    public void updateStudent(Student student, String name,
                              int java, int python, int sql,
                              int html, int ml) {
        studentService.updateStudent(student, name, java, python, sql, html, ml);
    }

    public void deleteStudent(Student student) {
        studentService.deleteStudent(student);
    }

    public StudentStatistics getStatistics() {
        return statisticsService.calculate(getStudents());
    }

    public int countGrade(String grade) {
        return statisticsService.countGrade(getStudents(), grade);
    }

    public void showDashboard() {
        new DashboardFrame(this).setVisible(true);
    }
}
