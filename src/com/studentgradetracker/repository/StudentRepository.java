package com.studentgradetracker.repository;

import java.util.ArrayList;
import java.util.List;
import com.studentgradetracker.model.Student;

public class StudentRepository {
    private final ArrayList<Student> students = new ArrayList<>();

    public void add(Student student) {
        students.add(student);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Student findById(String id) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    public void delete(Student student) {
        students.remove(student);
    }
}
