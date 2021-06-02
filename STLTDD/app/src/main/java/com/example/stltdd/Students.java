package com.example.stltdd;

public class Students {
    private String userId;
    private String email;
    private String name;
    private String school;
    private String gender;
    private String role;

    public Students() {
    }

    public Students(String userId, String email, String name, String school, String gender, String role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.school = school;
        this.gender = gender;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String student_id) {
        this.userId = student_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
