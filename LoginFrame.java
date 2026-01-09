package com.sms.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

import com.sms.models.*;
import com.sms.persistence.DataStore;

public class LoginFrame extends JFrame {
    private JTextField txtId;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Semester Management System");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRole = new JLabel("Role:");
        JLabel lblId = new JLabel("User ID:");
        JLabel lblPassword = new JLabel("Password:");

        String[] roles = {"Admin", "Faculty", "Student"};
        cmbRole = new JComboBox<>(roles);
        txtId = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Login");

        btnLogin.addActionListener(this::onLogin);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblRole, gbc);
        gbc.gridx = 1;
        panel.add(cmbRole, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblId, gbc);
        gbc.gridx = 1;
        panel.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        add(panel);
    }

    private void onLogin(ActionEvent e) {
        String role = (String) cmbRole.getSelectedItem();
        String id = txtId.getText().trim();
        String pw = new String(txtPassword.getPassword());

        if (id.isEmpty() || pw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (role) {
            case "Admin":
                handleAdminLogin(id, pw);
                break;
            case "Faculty":
                handleFacultyLogin(id, pw);
                break;
            case "Student":
                handleStudentLogin(id, pw);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown role.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAdminLogin(String id, String pw) {
        Map<String, Admin> admins = DataStore.loadAdmins();
        Admin a = admins.get(id);
        if (a != null && a.checkPassword(pw)) {
            JOptionPane.showMessageDialog(this, "Welcome, " + a.getName());
            new AdminDashboardFrame(a).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFacultyLogin(String id, String pw) {
        Map<String, Faculty> facultyMap = DataStore.loadFaculty();
        Faculty f = facultyMap.get(id);
        if (f != null && f.checkPassword(pw)) {
            JOptionPane.showMessageDialog(this, "Welcome, " + f.getName());
            new FacultyDashboardFrame(f).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid faculty credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleStudentLogin(String id, String pw) {
        Map<String, Student> students = DataStore.loadStudents();
        Student s = students.get(id);
        if (s != null && s.checkPassword(pw)) {
            JOptionPane.showMessageDialog(this, "Welcome, " + s.getName());
            new StudentDashboardFrame(s).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid student credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
