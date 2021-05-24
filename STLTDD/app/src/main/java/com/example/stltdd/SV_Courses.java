package com.example.stltdd;

public class SV_Courses {
    private String course_id;
    //private String course_name;
    private String userId;

    public SV_Courses() {
    }

    /*public SV_Courses(String course_id, String course_name, String userId) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.userId = userId;
    }*/

    public SV_Courses(String course_id, String userId) {
        this.course_id = course_id;
        this.userId = userId;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    /*public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }*/

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
