package com.sms.models;

import java.util.*;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    private String department;
    private Map<String, Double> courseGrades = new HashMap<>(); // courseId -> grade points
    private Map<String, Integer> attendance = new HashMap<>(); // courseId -> attended days

    public Student(String id, String name, String email, String password, String department) {
        super(id, name, email, password);
        this.department = department;
    }

    public String getDepartment() { return department; }

    public void setGrade(String courseId, double gradePoint) { courseGrades.put(courseId, gradePoint); }
    public Double getGrade(String courseId) { return courseGrades.get(courseId); }
    public Map<String, Double> getAllGrades() { return courseGrades; }

    public void addAttendance(String courseId, int days) { attendance.put(courseId, attendance.getOrDefault(courseId, 0) + days); }
    public int getAttendance(String courseId) { return attendance.getOrDefault(courseId, 0); }

    public double calculateGPA(Map<String, Integer> courseCredits) {
        double totalPoints = 0; int totalCredits = 0;
        for (Map.Entry<String, Double> e : courseGrades.entrySet()) {
            String cId = e.getKey();
            double gp = e.getValue();
            int credit = courseCredits.getOrDefault(cId, 0);
            totalPoints += gp * credit;
            totalCredits += credit;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}