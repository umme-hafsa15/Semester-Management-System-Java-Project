package com.sms.models;

public class Faculty extends User {
    private static final long serialVersionUID = 1L;
    private String department;

    public Faculty(String id, String name, String email, String password, String department) {
        super(id, name, email, password);
        this.department = department;
    }

    public String getDepartment() { return department; }
}