package com.sms.models;

public class Admin extends User {
    private static final long serialVersionUID = 1L;
    public Admin(String id, String name, String email, String password) {
        super(id, name, email, password);
    }
}