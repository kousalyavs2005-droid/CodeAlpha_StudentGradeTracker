package com.studentgradetracker.model;

public class Student {
    private String studentId;
    private String name;
    private int java;
    private int python;
    private int sql;
    private int html;
    private int machineLearning;
    private int total;
    private double average;
    private String grade;

    public Student(String studentId, String name, int java, int python,
                   int sql, int html, int machineLearning) {
        this.studentId = studentId;
        this.name = name;
        this.java = java;
        this.python = python;
        this.sql = sql;
        this.html = html;
        this.machineLearning = machineLearning;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getJava() { return java; }
    public void setJava(int value) { this.java = value; }

    public int getPython() { return python; }
    public void setPython(int value) { this.python = value; }

    public int getSql() { return sql; }
    public void setSql(int value) { this.sql = value; }

    public int getHtml() { return html; }
    public void setHtml(int value) { this.html = value; }

    public int getMachineLearning() { return machineLearning; }
    public void setMachineLearning(int value) { this.machineLearning = value; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public double getAverage() { return average; }
    public void setAverage(double average) { this.average = average; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public boolean isPass() {
        return !"F".equals(grade);
    }
}
