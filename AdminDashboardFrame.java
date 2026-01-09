package com.sms.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

import com.sms.models.*;
import com.sms.persistence.DataStore;

public class AdminDashboardFrame extends JFrame {
    private Admin admin;

    private JTextArea txtArea;
    private JButton btnViewStudents;
    private JButton btnViewCourses;
    private JButton btnViewSemesters;

    public AdminDashboardFrame(Admin admin) {
        this.admin = admin;
        setTitle("Admin Dashboard - " + admin.getName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewStudents = new JButton("View Students");
        btnViewCourses = new JButton("View Courses");
        btnViewSemesters = new JButton("View Semesters");

        btnViewStudents.addActionListener(this::onViewStudents);
        btnViewCourses.addActionListener(this::onViewCourses);
        btnViewSemesters.addActionListener(this::onViewSemesters);

        topPanel.add(btnViewStudents);
        topPanel.add(btnViewCourses);
        topPanel.add(btnViewSemesters);

        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void onViewStudents(ActionEvent e) {
        Map<String, Student> students = DataStore.loadStudents();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Students ===\n");
        for (Student s : students.values()) {
            sb.append(s.toString()).append(" | Dept: ").append(s.getDepartment()).append("\n");
        }
        txtArea.setText(sb.toString());
    }

    private void onViewCourses(ActionEvent e) {
        Map<String, Course> courses = DataStore.loadCourses();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Courses ===\n");
        for (Course c : courses.values()) {
            sb.append(c.toString()).append("\n");
        }
        txtArea.setText(sb.toString());
    }

    private void onViewSemesters(ActionEvent e) {
        Map<String, Semester> sems = DataStore.loadSems();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Semesters ===\n");
        for (Semester s : sems.values()) {
            sb.append(s.getId())
              .append(" [Courses: ").append(s.getCourseIds().size()).append("]\n");
        }
        txtArea.setText(sb.toString());
    }
}
