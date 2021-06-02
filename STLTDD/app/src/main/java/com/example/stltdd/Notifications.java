package com.example.stltdd;

import java.util.Date;

public class Notifications {
    private String noti_id;
    private String noti_name;
    private String noti_content;
    private String course_id;
    private String noti_date;

    public Notifications() {
    }

    public Notifications(String noti_id, String noti_name, String noti_content, String course_id, String noti_date) {
        this.noti_id = noti_id;
        this.noti_name = noti_name;
        this.noti_content = noti_content;
        this.course_id = course_id;
        this.noti_date = noti_date;
    }

    public Notifications(String noti_name, String noti_content, String course_id, String noti_date) {
        this.noti_name = noti_name;
        this.noti_content = noti_content;
        this.course_id = course_id;
        this.noti_date = noti_date;
    }

    public String getNoti_id() {
        return noti_id;
    }

    public void setNoti_id(String noti_id) {
        this.noti_id = noti_id;
    }

    public String getNoti_name() {
        return noti_name;
    }

    public void setNoti_name(String noti_name) {
        this.noti_name = noti_name;
    }

    public String getNoti_content() {
        return noti_content;
    }

    public void setNoti_content(String noti_content) {
        this.noti_content = noti_content;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getNoti_date() {
        return noti_date;
    }

    public void setNoti_date(String noti_date) {
        this.noti_date = noti_date;
    }
}
