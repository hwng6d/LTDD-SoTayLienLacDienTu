package com.example.stltdd;

public class Parents {
    private String userId;
    private String name;
    private String email;
    private String gender;
    private String student_userId;
    private String student_name;
    private String role;

    public Parents() {
    }

    public Parents(String userId, String name, String email, String gender, String student_userId, String student_name, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.student_userId = student_userId;
        this.student_name = student_name;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStudent_userId() {
        return student_userId;
    }

    public void setStudent_userId(String student_userId) {
        this.student_userId = student_userId;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
