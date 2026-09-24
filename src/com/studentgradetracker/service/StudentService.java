package com.studentgradetracker.service;

import java.util.List;
import com.studentgradetracker.model.Student;
import com.studentgradetracker.repository.StudentRepository;

public class StudentService {
    private final StudentRepository repository;
    private final GradeService gradeService;

    public StudentService(StudentRepository repository, GradeService gradeService) {
        this.repository = repository;
        this.gradeService = gradeService;
        seedDemoData();
    }

    public void addStudent(Student student) {
        gradeService.calculateAndApply(student);
        repository.add(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student findById(String id) {
        return repository.findById(id);
    }

    public void updateStudent(Student student, String name,
                              int java, int python, int sql,
                              int html, int machineLearning) {
        student.setName(name);
        student.setJava(java);
        student.setPython(python);
        student.setSql(sql);
        student.setHtml(html);
        student.setMachineLearning(machineLearning);
        gradeService.calculateAndApply(student);
    }

    public void deleteStudent(Student student) {
        repository.delete(student);
    }

    public List<Student> search(String query) {
        java.util.ArrayList<Student> result = new java.util.ArrayList<>();
        String q = query.trim().toLowerCase();
        if (q.isEmpty()) return result;

        for (Student s : repository.findAll()) {
            if (s.getStudentId().toLowerCase().contains(q)
                    || s.getName().toLowerCase().contains(q)) {
                result.add(s);
            }
        }
        return result;
    }

    private void seedDemoData() {
        addStudent(new Student("STU001", "Kousalya", 85, 90, 88, 92, 86));
        addStudent(new Student("STU002", "Arun Kumar", 78, 82, 75, 80, 85));
        addStudent(new Student("STU003", "Priya Sharma", 72, 68, 81, 74, 65));
        addStudent(new Student("STU004", "Karthik Raja", 95, 98, 92, 97, 96));
        addStudent(new Student("STU005", "Divya Nair", 55, 60, 48, 62, 50));
        addStudent(new Student("STU006", "Rahul Verma", 76, 82, 85, 79, 86));
        addStudent(new Student("STU007", "Sneha Patel", 40, 45, 38, 50, 42));
        addStudent(new Student("STU008", "Vijay Mohan", 91, 87, 93, 89, 94));
        addStudent(new Student("STU009", "Anitha Raj", 63, 70, 67, 72, 58));
        addStudent(new Student("STU010", "Suresh Babu", 85, 78, 90, 83, 87));
        addStudent(new Student("STU011", "Meghana", 69, 74, 71, 66, 73));
        addStudent(new Student("STU012", "Ravi Teja", 58, 62, 55, 60, 64));
        addStudent(new Student("STU013", "Pooja Rao", 88, 84, 91, 86, 89));
    }
}
