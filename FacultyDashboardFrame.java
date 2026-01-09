package com.sms.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import com.sms.models.*;
import com.sms.persistence.DataStore;

class FacultyDashboardFrame extends JFrame {
    private Faculty faculty;
    private JTextArea txtArea;

    public FacultyDashboardFrame(Faculty faculty) {
        this.faculty = faculty;
        setTitle("Faculty Dashboard - " + faculty.getName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblInfo = new JLabel("Logged in as: " + faculty.getName() + " (" + faculty.getDepartment() + ")");
        topPanel.add(lblInfo);

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);

        JButton btnViewAssignedCourses = new JButton("View My Courses");
        btnViewAssignedCourses.addActionListener(e -> showMyCourses());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(btnViewAssignedCourses);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void showMyCourses() {
        Map<String, Course> courses = DataStore.loadCourses();
        String fId = faculty.getId();

        StringBuilder sb = new StringBuilder();
        sb.append("=== My Assigned Courses ===\n");
        for (Course c : courses.values()) {
            if (fId.equals(c.getFacultyId())) {
                sb.append(c.toString()).append("\n");
            }
        }
        txtArea.setText(sb.toString());
    }
}
