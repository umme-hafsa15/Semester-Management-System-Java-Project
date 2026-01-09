package com.sms.models;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String email;
    protected String password; // simple plaintext for demo (not secure)

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public boolean checkPassword(String pw) { return password != null && password.equals(pw); }

    @Override
    public String toString() {
        return String.format("%s (ID: %s, email: %s)", name, id, email);
    }
}