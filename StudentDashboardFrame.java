package com.sms.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import com.sms.models.*;
import com.sms.persistence.DataStore;

public class StudentDashboardFrame extends JFrame {
    private Student student;
    private JTextArea txtArea;

    public StudentDashboardFrame(Student student) {
        this.student = student;
        setTitle("Student Dashboard - " + student.getName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblInfo = new JLabel("Logged in as: " + student.getName() + " (" + student.getDepartment() + ")");
        topPanel.add(lblInfo);

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);

        JButton btnViewCourses = new JButton("View My Courses & Grades");
        btnViewCourses.addActionListener(e -> showCoursesAndGPA());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(btnViewCourses);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showCoursesAndGPA() {
        Map<String, Course> courses = DataStore.loadCourses();

        // Build a simple credit map for GPA calculation
        java.util.Map<String, Integer> creditMap = new java.util.HashMap<>();
        for (Course c : courses.values()) {
            creditMap.put(c.getId(), c.getCredits());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== My Courses & Grades ===\n");
        for (Map.Entry<String, Double> e : student.getAllGrades().entrySet()) {
            String courseId = e.getKey();
            Double grade = e.getValue();
            Course course = courses.get(courseId);
            sb.append(courseId);
            if (course != null) {
                sb.append(" - ").append(course.getTitle()).append(" (").append(course.getCredits()).append(" cr)");
            }
            sb.append(" | Grade: ").append(grade).append("\n");
        }

        double gpa = student.calculateGPA(creditMap);
        sb.append("\nGPA: ").append(String.format("%.2f", gpa));
        txtArea.setText(sb.toString());
    }
}
