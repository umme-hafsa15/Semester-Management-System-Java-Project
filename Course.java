package com.sms.models;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private int credits;
    private String facultyId; // assigned faculty
    private int capacity;
    private Set<String> enrolledStudentIds = new HashSet<>();

    public Course(String id, String title, int credits, int capacity) {
        this.id = id; this.title = title; this.credits = credits; this.capacity = capacity;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String fId) { facultyId = fId; }
    public int getCapacity() { return capacity; }

    public boolean enroll(String studentId) {
        if (enrolledStudentIds.size() >= capacity) return false;
        return enrolledStudentIds.add(studentId);
    }

    public boolean drop(String studentId) {
        return enrolledStudentIds.remove(studentId);
    }

    public Set<String> getEnrolledStudentIds() {
        return Collections.unmodifiableSet(enrolledStudentIds);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d credits) [Capacity: %d, Enrolled: %d]", id, title, credits, capacity, enrolledStudentIds.size());
    }
}