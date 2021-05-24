package com.example.stltdd;

public class Admins {
    private String admin_id;
    private String email;
    private String gender;
    private String name;
    private String role;

    public Admins() {
    }

    public Admins(String admin_id, String email, String gender, String name, String role) {
        this.admin_id = admin_id;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.role = role;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
