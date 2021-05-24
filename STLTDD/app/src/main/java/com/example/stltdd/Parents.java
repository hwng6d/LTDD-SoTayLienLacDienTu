package com.example.stltdd;

public class Parents {
    private String parent_id;
    private String name;
    private String email;
    private String gender;
    private String student_id;
    private String student_name;
    private String role;

    public Parents() {
    }

    public Parents(String parent_id, String name, String email, String gender, String student_id, String student_name, String role) {
        this.parent_id = parent_id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.student_id = student_id;
        this.student_name = student_name;
        this.role = role;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
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

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
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
