package com.example.stltdd;

public class Courses {
    private String name;
    private String course_id;
    private String falcuty;
    private String specilization;
    private String timetable;
    private String startdate;
    private String enddate;
    private int credits;
    private int fee;

    public Courses() {
    }

    public Courses(String name, String course_id, String falcuty, String specilization, String timetable, String startdate, String enddate, int credits, int fee) {
        this.name = name;
        this.course_id = course_id;
        this.falcuty = falcuty;
        this.specilization = specilization;
        this.timetable = timetable;
        this.startdate = startdate;
        this.enddate = enddate;
        this.credits = credits;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getFalcuty() {
        return falcuty;
    }

    public void setFalcuty(String falcuty) {
        this.falcuty = falcuty;
    }

    public String getSpecilization() {
        return specilization;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
